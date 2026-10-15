# Mestre da Redação — versão web ampliada

Recursos: temas, editor, salvamento local, histórico, comunidade pessoal, simulados de português importados do catálogo Kotlin, aulas em formato de trilha, upload e prévia de fotos, correção por Gemini via Netlify Functions.

## Deploy
Publicar o conteúdo desta pasta na branch `web` do GitHub. Netlify: base directory correspondente a esta pasta, build `npm run build`, publish `dist`. Configure a variável de ambiente `GEMINI_API_KEY` no painel do Netlify para habilitar correção por IA. Nunca coloque a chave no código web.

## Limitações
Não inclui login, comunidade online compartilhada, OCR real, vídeos hospedados ou sincronização entre dispositivos. Os dados de escrita ficam no localStorage do navegador. A avaliação por IA é estimativa, não nota oficial.

## Correção do botão Corrigir com IA
As mensagens de carregamento, sucesso e erro agora permanecem abaixo do botão. Se a função Netlify não estiver publicada, o usuário recebe erro explicativo. Configurar `GEMINI_API_KEY` como variável secreta no Netlify e executar novo deploy.


## Diagnóstico de falhas Gemini
A função `netlify/functions/corrigir.js` retorna mensagens por código de erro do Gemini (400, 401, 403, 404, 429 e 5xx) e registra detalhes seguros em Netlify > Logs > Functions. Nunca adicione a chave ao código ou ao GitHub.

## Modo escuro no celular
Na versão mobile, toque no botão de lua/sol no topo da tela para alternar entre tema claro e escuro. A preferência é salva no navegador (`mr_dark`) e também funciona no desktop, pelo menu lateral. O tema afeta a navegação, formulários, editor, resultados da IA e a cor da barra do navegador em dispositivos compatíveis.

## Scanner de redações manuscritas (versão web)

Na aba **Scanner**, os estudantes podem fotografar 1 a 3 páginas com o celular ou selecionar imagens JPG/PNG/WebP, conferir as páginas na ordem de envio, solicitar transcrição pelo Gemini, revisar o texto e obter correção estimada nas cinco competências do ENEM. Também podem enviar o texto reconhecido para o editor e salvar a redação.

- As fotos são reduzidas no navegador para respeitar os limites de requisição das Functions do Netlify; a qualidade da transcrição depende da nitidez e legibilidade da caligrafia.
- As fotos não são persistidas em localStorage. Elas são transmitidas ao serviço Gemini **apenas ao clicar em “Ler redação com IA”**. O texto pode ser enviado ao Gemini novamente para a avaliação.
- A chave `GEMINI_API_KEY` deve estar configurada no Netlify com escopo Functions; não coloque a chave nos arquivos enviados ao GitHub.
- A função `netlify/functions/ler-redacao.js` faz OCR por visão multimodal do Gemini; `netlify/functions/corrigir.js` avalia o texto conferido. As notas **não são oficiais**.
- `GEMINI_MODEL` é opcional e, se configurada, deve apontar para um modelo Gemini que aceite imagens e esteja disponível para a chave.
- A câmera é oferecida em navegadores mobile com suporte a `capture="environment"`; no desktop, o dispositivo normalmente exibe um seletor de arquivos.

Para publicar, mantenha a **branch `web`** com este conteúdo em `mestre-redacao-web/` e use no Netlify: Base directory `mestre-redacao-web`, Build command `npm run build`, Publish directory `dist` (caminho efetivo `mestre-redacao-web/dist`), Functions directory `netlify/functions` relativa à base. Publique e teste em um celular com rede HTTPS.
