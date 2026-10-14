# Mestre da Redação — versão web inicial

Reimplementação web inicial baseada no projeto Android Kotlin recebido. Inclui temas de redação extraídos do catálogo original, editor com salvamento local, biblioteca de estudo, desempenho e painel comunitário demonstrativo. Não contém integração de IA, autenticação, sincronização entre dispositivos ou scanner.

## Executar
`npm install` e `npm run dev`

## Publicar no Netlify
Build command: `npm run build`; Publish directory: `dist`. Arquivo netlify.toml incluído.

**Não exponha chaves de API no frontend.** IA requer função server-side e configuração segura.
