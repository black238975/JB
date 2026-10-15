import React, { useRef, useState } from 'react';
import { Camera, Images, ScanText, Sparkles, Trash2, ArrowRight, AlertCircle, FileText, LoaderCircle } from 'lucide-react';
import themes from './themes.json';

const MAX_PAGES = 3;
const MAX_ORIGINAL_BYTES = 12 * 1024 * 1024;
const MAX_IMAGE_DATA = 1_550_000;

function fileToImage(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => {
      const img = new Image();
      img.onload = () => resolve(img);
      img.onerror = () => reject(new Error('Não foi possível abrir a imagem. Envie uma foto JPG, PNG ou WebP.'));
      img.src = reader.result;
    };
    reader.onerror = () => reject(new Error('Não foi possível ler a foto.'));
    reader.readAsDataURL(file);
  });
}

async function processPhoto(file) {
  if (!['image/jpeg', 'image/png', 'image/webp'].includes(file.type)) {
    throw new Error('Formato não aceito. Use fotos JPG, PNG ou WebP.');
  }
  if (file.size > MAX_ORIGINAL_BYTES) {
    throw new Error('A foto é muito grande. Selecione uma imagem com até 12 MB.');
  }
  const img = await fileToImage(file);
  let longestSide = 2000;
  for (let attempt = 0; attempt < 4; attempt++) {
    const scale = Math.min(1, longestSide / Math.max(img.naturalWidth, img.naturalHeight));
    const canvas = document.createElement('canvas');
    canvas.width = Math.max(1, Math.round(img.naturalWidth * scale));
    canvas.height = Math.max(1, Math.round(img.naturalHeight * scale));
    const ctx = canvas.getContext('2d');
    if (!ctx) throw new Error('Não foi possível preparar a imagem neste navegador.');
    ctx.fillStyle = '#ffffff';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
    const dataUrl = canvas.toDataURL('image/jpeg', attempt > 1 ? 0.73 : 0.84);
    canvas.width = 0; canvas.height = 0;
    if (dataUrl.length <= MAX_IMAGE_DATA) return { id: crypto.randomUUID(), name: file.name, dataUrl };
    longestSide = Math.floor(longestSide * 0.8);
  }
  throw new Error('A foto ficou grande demais para o scanner. Tente uma imagem mais leve.');
}

async function postJson(url, payload, timeout = 60000) {
  const controller = new AbortController();
  const timer = setTimeout(() => controller.abort(), timeout);
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
      signal: controller.signal,
    });
    const raw = await response.text();
    let result;
    try { result = JSON.parse(raw); } catch {
      throw new Error(response.status === 404
        ? 'Serviço não publicado no Netlify (404). Confira a pasta netlify/functions e o deploy.'
        : `Resposta inválida do servidor (HTTP ${response.status}). Confira os logs do Netlify.`);
    }
    if (!response.ok) throw new Error(result?.error || `O servidor retornou HTTP ${response.status}.`);
    return result;
  } catch (error) {
    if (error.name === 'AbortError') throw new Error('A análise demorou demais. Tente novamente com uma foto mais nítida.');
    throw error;
  } finally {
    clearTimeout(timer);
  }
}

export default function ScannerPanel({ selectedTheme, onThemeChange, onOpenEditor }) {
  const cameraRef = useRef(null);
  const galleryRef = useRef(null);
  const [photos, setPhotos] = useState([]);
  const [processing, setProcessing] = useState(false);
  const [transcribing, setTranscribing] = useState(false);
  const [correcting, setCorrecting] = useState(false);
  const [transcription, setTranscription] = useState('');
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');
  const [info, setInfo] = useState('');

  const busy = processing || transcribing || correcting;
  const resetOutput = () => {
    setTranscription('');
    setResult(null);
    setError('');
    setInfo('');
  };
  async function addPhotos(event) {
    const selected = Array.from(event.target.files || []);
    event.target.value = '';
    if (!selected.length) return;
    const remaining = MAX_PAGES - photos.length;
    if (selected.length > remaining) {
      setError(`Você pode enviar até ${MAX_PAGES} fotos. Remova uma página antes de adicionar outra.`);
      return;
    }
    setProcessing(true); setError(''); setInfo('Preparando as fotos para leitura...');
    try {
      const next = [];
      for (const file of selected) next.push(await processPhoto(file));
      setPhotos(prev => [...prev, ...next]);
      resetOutput();
      setInfo(`${next.length} ${next.length === 1 ? 'foto adicionada' : 'fotos adicionadas'}. Confira a ordem das páginas.`);
    } catch (e) {
      setError(e.message || 'Não foi possível preparar as fotos.');
      setInfo('');
    } finally {
      setProcessing(false);
    }
  }

  async function transcribe() {
    if (!photos.length) { setError('Adicione uma foto da redação antes de continuar.'); return; }
    setTranscribing(true); setError(''); setInfo('Lendo a letra e organizando os parágrafos...'); setResult(null);
    try {
      const response = await postJson('/.netlify/functions/ler-redacao', {
        images: photos.map(photo => photo.dataUrl),
      });
      if (!response.texto || response.texto.trim().length < 15) throw new Error('Não foi possível identificar texto suficiente na foto. Tire outra foto com mais iluminação.');
      setTranscription(response.texto);
      setInfo('Texto reconhecido. Confira e corrija qualquer palavra antes da avaliação.');
    } catch (e) { setError(e.message || 'Falha ao ler a redação.'); setInfo(''); }
    finally { setTranscribing(false); }
  }

  async function correct() {
    if (transcription.trim().length < 150) { setError('A redação precisa de pelo menos 150 caracteres para ser avaliada. Verifique a transcrição.'); return; }
    setCorrecting(true); setError(''); setInfo('Avaliando as cinco competências do ENEM...'); setResult(null);
    try {
      const data = await postJson('/.netlify/functions/corrigir', {
        theme: selectedTheme.title,
        essay: transcription,
      });
      if (!Number.isFinite(data.totalScore)) throw new Error('O serviço não retornou uma nota válida.');
      setResult(data);
      setInfo('Correção concluída! A nota é uma estimativa da IA, não uma nota oficial do ENEM.');
    } catch (e) { setError(e.message || 'Falha ao corrigir a redação.'); setInfo(''); }
    finally { setCorrecting(false); }
  }

  return <>
    <div className="pagetitle"><h1>Scanner de redações</h1><p>Fotografe sua redação escrita à mão, confira o texto reconhecido e receba uma correção estimada por inteligência artificial.</p></div>
    <section className="panel scanner-panel">
      <div className="scanner-step"><span>1</span><div><h2>Fotografe sua redação</h2><p className="hint">Use luz natural, mantenha a folha inteira visível e tire uma foto por página (até {MAX_PAGES}).</p></div></div>
      <div className="scan-actions">
        <input ref={cameraRef} type="file" accept="image/jpeg,image/png,image/webp" capture="environment" className="visually-hidden" onChange={addPhotos} aria-label="Tirar foto da redação" disabled={busy}/>
        <input ref={galleryRef} type="file" accept="image/jpeg,image/png,image/webp" multiple className="visually-hidden" onChange={addPhotos} aria-label="Escolher fotos da galeria" disabled={busy}/>
        <button className="primary" onClick={() => cameraRef.current?.click()} disabled={busy || photos.length >= MAX_PAGES}><Camera size={18}/> Tirar foto</button>
        <button className="outline scan-outline" onClick={() => galleryRef.current?.click()} disabled={busy || photos.length >= MAX_PAGES}><Images size={18}/> Escolher da galeria</button>
      </div>
      <p className="hint">No computador, “Tirar foto” pode abrir o seletor de arquivos. No celular, deve oferecer a câmera quando o navegador permitir.</p>
      {!!photos.length && <><div className="scan-photos">{photos.map((photo, index) => <div className="scan-photo" key={photo.id}><span className="scan-counter">Página {index + 1}</span><img src={photo.dataUrl} alt={`Foto da página ${index + 1} da redação`}/><button type="button" className="outline small" disabled={busy} onClick={() => {setPhotos(prev => prev.filter(p => p.id !== photo.id)); resetOutput();}}><Trash2 size={14}/> Remover</button></div>)}</div><p className="hint">As fotos são processadas apenas para a leitura e não são salvas no histórico do navegador. Elas são enviadas ao Gemini para transcrição.</p></>}
      <button className="primary scan-main-action" disabled={busy || !photos.length} onClick={transcribe}>{transcribing ? <><LoaderCircle className="scanner-spin" size={18}/> Lendo fotos...</> : <><ScanText size={18}/> {transcription ? 'Ler fotos novamente' : 'Ler redação com IA'}</>}</button>
    </section>

    {transcription && <section className="panel scanner-panel"><div className="scanner-step"><span>2</span><div><h2>Confira a transcrição</h2><p className="hint">A IA pode confundir letras manuscritas. Corrija palavras ou parágrafos antes de solicitar a nota.</p></div></div><label className="fieldlabel" htmlFor="scanner-texto">Texto identificado ({transcription.trim().split(/\s+/).filter(Boolean).length} palavras)</label><textarea id="scanner-texto" className="scanner-textarea" value={transcription} onChange={e => {setTranscription(e.target.value); setResult(null); setError('');}} rows={14} maxLength={15000}/><label className="fieldlabel" htmlFor="scanner-tema">Tema da redação</label><select id="scanner-tema" value={themes.indexOf(selectedTheme)} onChange={e => {onThemeChange(themes[Number(e.target.value)]); setResult(null);}}>{themes.map((theme, index) => <option key={theme.title} value={index}>{theme.title}</option>)}</select><div className="scan-actions"><button className="primary" disabled={busy} onClick={correct}>{correcting ? <><LoaderCircle className="scanner-spin" size={18}/> Corrigindo...</> : <><Sparkles size={18}/> Corrigir com IA</>}</button><button className="outline scan-outline" disabled={busy} onClick={() => onOpenEditor(transcription)}><ArrowRight size={18}/> Abrir no editor</button></div></section>}

    {(error || info) && <div role={error ? 'alert' : 'status'} aria-live="polite" className={'grading-message scan-message ' + (error ? 'error' : (result ? 'success' : 'loading'))}>{error ? <AlertCircle size={18}/> : <FileText size={18}/>}<span>{error || info}</span></div>}

    {result && <section className="panel scanner-result"><div className="scanner-step"><span>3</span><div><h2>Resultado da correção</h2><p className="hint">Estimativa gerada por IA conforme as competências do ENEM.</p></div></div><div className="scanner-score">{result.totalScore}<small> / 1.000 pontos</small></div><div className="scan-scores">{[1, 2, 3, 4, 5].map(n => <div key={n}><span>Competência {n}</span><strong>{result[`c${n}Score`] ?? '—'}/200</strong></div>)}</div><h3>Orientações para melhorar</h3><p className="scan-feedback">{result.feedback}</p><button className="outline scan-outline" onClick={() => onOpenEditor(transcription)}><ArrowRight size={18}/> Editar ou salvar a redação</button></section>}
  </>;
}
