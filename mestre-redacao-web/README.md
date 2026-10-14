# Mestre da Redação — versão web ampliada

Recursos: temas, editor, salvamento local, histórico, comunidade pessoal, simulados de português importados do catálogo Kotlin, aulas em formato de trilha, upload e prévia de fotos, correção por Gemini via Netlify Functions.

## Deploy
Publicar o conteúdo desta pasta na branch `web` do GitHub. Netlify: base directory correspondente a esta pasta, build `npm run build`, publish `dist`. Configure a variável de ambiente `GEMINI_API_KEY` no painel do Netlify para habilitar correção por IA. Nunca coloque a chave no código web.

## Limitações
Não inclui login, comunidade online compartilhada, OCR real, vídeos hospedados ou sincronização entre dispositivos. Os dados de escrita ficam no localStorage do navegador. A avaliação por IA é estimativa, não nota oficial.

## Correção do botão Corrigir com IA
As mensagens de carregamento, sucesso e erro agora permanecem abaixo do botão. Se a função Netlify não estiver publicada, o usuário recebe erro explicativo. Configurar `GEMINI_API_KEY` como variável secreta no Netlify e executar novo deploy.
