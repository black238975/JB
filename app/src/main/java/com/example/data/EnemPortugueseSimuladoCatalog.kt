package com.example.data

data class EnemGrammarQuestion(
    val id: String,
    val enemYear: String,
    val topic: String, // ex: "Concordância & Regência", "Crase & Pontuação", "Coesão & Conectivos", "Variação Linguística", "Funções da Linguagem", "Figuras & Semântica", "Sintaxe & Morfologia", "Gêneros Textuais"
    val title: String,
    val baseText: String,
    val textSource: String,
    val prompt: String,
    val options: List<String>, // 5 alternativas (A, B, C, D, E)
    val correctIndex: Int, // 0=A, 1=B, 2=C, 3=D, 4=E
    val explanation: String
)

object EnemPortugueseSimuladoCatalog {

    val topics = listOf(
        "TODOS",
        "Concordância & Regência",
        "Crase & Pontuação",
        "Coesão & Conectivos",
        "Variação Linguística",
        "Funções da Linguagem",
        "Figuras & Semântica",
        "Sintaxe & Morfologia",
        "Gêneros Textuais"
    )

    val questions: List<EnemGrammarQuestion> = listOf(
        // QUESTÃO 1: Concordância Verbal e Voz Passiva Sintética
        EnemGrammarQuestion(
            id = "enem_port_1",
            enemYear = "ENEM 2024 • Aplicação Regular",
            topic = "Concordância & Regência",
            title = "Concordância com Partícula Apassivadora 'se' e Sujeito Paciente",
            baseText = "Trabalhadores e pesquisadores debatem os impactos da automação na vida urbana. Em recente audiência pública, declarou-se que 'precisa-se de novas diretrizes éticas para inteligência artificial', ao passo que um relatório técnico destacou: 'Tratam-se de dados sensíveis da população, os quais exigem cautela'. Na redação formal e na prova de linguagens, a adequação morfossintática das construções com a partícula 'se' orienta a clareza e a credibilidade argumentativa.",
            textSource = "Adaptado de debates contemporâneos sobre tecnologia e linguagem formal, ENEM 2024.",
            prompt = "Considerando as regras de concordância verbal da norma-padrão da Língua Portuguesa, avalie a correção das construções apresentadas no texto e assinale a alternativa correta:",
            options = listOf(
                "A frase 'precisa-se de novas diretrizes' está incorreta, pois o verbo deveria ir para o plural concordando com 'novas diretrizes'.",
                "A frase 'Tratam-se de dados sensíveis' infringe a norma-padrão, pois o verbo 'tratar-se', regido pela preposição 'de', tem sujeito indeterminado e deve permanecer na 3ª pessoa do singular ('Trata-se de dados sensíveis').",
                "Ambas as frases estão perfeitamente de acordo com a norma-padrão, pois admitem dupla concordância em registros cultos.",
                "O pronome 'se' em 'Tratam-se de dados' atua como partícula apassivadora, tornando 'dados sensíveis' o sujeito paciente da oração.",
                "A forma 'precisa-se' deveria receber ênclise obrigatória com flexão de plural caso viesse no início do parágrafo: 'Precisam-se de'."
            ),
            correctIndex = 1,
            explanation = "Comentário do Prof. João Batista: O verbo 'tratar' acompanhado da preposição 'de' ('tratar-se de') é transitivo indireto. Nesse caso, a partícula 'se' atua como Índice de Indeterminação do Sujeito (IIS), exigindo obrigatoriamente o verbo na 3ª pessoa do singular: 'Trata-se de dados sensíveis'. Já na frase 'precisa-se de novas diretrizes', a estrutura está correta no singular pelo mesmo motivo (verbo transitivo indireto + 'se'). Portanto, a alternativa B é a única correta."
        ),

        // QUESTÃO 2: Emprego do Acento Indicativo de Crase
        EnemGrammarQuestion(
            id = "enem_port_2",
            enemYear = "ENEM 2023 • Caderno Azul",
            topic = "Crase & Pontuação",
            title = "Regência e Condições de Ocorrência da Crase",
            baseText = "O acesso à cidadania plena não se limita à obtenção de documentos formais; estende-se a garantia de condições dignas de existência para todas as parcelas sociais. Quando o poder público se recusa a atender as demandas mais urgentes da periferia, nega a população o direito elementar de usufruir dos serviços básicos previstos na Carta Magna de 1988.",
            textSource = "Discurso sobre Políticas Públicas e Inclusão Social, Inep/ENEM 2023.",
            prompt = "Em relação ao emprego do acento indicativo de crase de acordo com a norma-padrão escrita, o segmento que apresenta desvio gramatical no texto é:",
            options = listOf(
                "'O acesso à cidadania plena', pois a palavra 'cidadania' dispensa artigo definido.",
                "'não se limita à obtenção', visto que o verbo 'limitar-se' não rege preposição 'a'.",
                "'estende-se a garantia', pois a regência do verbo 'estender-se' exige a preposição 'a', que, ao fundir-se com o artigo feminino de 'garantia', requer o acento grave ('estende-se à garantia').",
                "'recusa a atender', porque é obrigatória a crase antes de verbos no infinitivo.",
                "'previstos na Carta Magna', onde deveria ocorrer crase em substituição à contração 'na'."
            ),
            correctIndex = 2,
            explanation = "Comentário do Prof. João Batista: O verbo pronominal 'estender-se' rege a preposição 'a' (algo se estende a alguma coisa). Como o termo regido é o substantivo feminino determinado 'a garantia', ocorre a fusão da preposição 'a' com o artigo 'a', tornando obrigatório o acento grave indicativo de crase: 'estende-se à garantia'. A alternativa C identifica exatamente essa omissão indevida da crase no texto."
        ),

        // QUESTÃO 3: Pontuação e Efeitos de Sentido da Vírgula
        EnemGrammarQuestion(
            id = "enem_port_3",
            enemYear = "ENEM 2024 • Caderno Amarelo",
            topic = "Crase & Pontuação",
            title = "Uso da Vírgula com Orações Adjetivas e Apostos Explicativos",
            baseText = "Texto I: 'Os jovens brasileiros que utilizam redes sociais diariamente apresentam maior vulnerabilidade a transtornos de ansiedade.'\nTexto II: 'Os jovens brasileiros, que utilizam redes sociais diariamente, apresentam maior vulnerabilidade a transtornos de ansiedade.'\nA pontuação na língua escrita não serve meramente para marcar pausas respiratórias, mas para estruturar sintaticamente as relações de sentido pretendidas pelo autor.",
            textSource = "Análise linguística aplicada à redação nota 1000, ENEM 2024.",
            prompt = "Comparando os Textos I e II, o emprego das vírgulas no Texto II produz como efeito de sentido:",
            options = listOf(
                "Restringir a vulnerabilidade apenas ao subgrupo de jovens que são dependentes de tecnologia.",
                "Generalizar a informação, indicando que a totalidade dos jovens brasileiros utiliza redes sociais diariamente (oração subordinada adjetiva explicativa).",
                "Indicar uma incorreção sintática grave, pois é proibido separar o sujeito de seu predicado em qualquer circunstância.",
                "Transformar a oração em subordinada adverbial concessiva, indicando oposição entre juventude e redes.",
                "Enfatizar uma circunstância temporal equivalente a 'quando utilizam redes sociais'."
            ),
            correctIndex = 1,
            explanation = "Comentário do Prof. João Batista: No Texto I, sem vírgulas, a oração é adjetiva restritiva: apenas a parcela de jovens que usa redes é afetada. No Texto II, com vírgulas, ela se torna adjetiva explicativa: atribui a característica a toda a classe dos 'jovens brasileiros' (generalização). Esse domínio semântico da vírgula é essencial tanto na prova de Linguagens quanto na Competência 1 da Redação."
        ),

        // QUESTÃO 4: Variação Linguística e Preconceito Linguístico
        EnemGrammarQuestion(
            id = "enem_port_4",
            enemYear = "ENEM 2023 • Caderno Branco",
            topic = "Variação Linguística",
            title = "Adequação Vocabular e Registro Sociolinguístico",
            baseText = "Atum / tá cum / fome de que?\nCê qué pão?\nNão, quero água.\nÁgua do pote ou da torneira?\nDa bica, que corre ligeira!\n\nNa perspectiva da sociolinguística contemporânea, as variações fonéticas e sintáticas observadas na oralidade cotidiana expressam a riqueza e a vitalidade de um idioma dinâmico.",
            textSource = "Coletânea de tradição oral e poesia popular brasileira, ENEM 2023.",
            prompt = "A partir do reconhecimento das diferentes variedades da língua portuguesa, o uso de formas como 'tá cum' e 'cê qué':",
            options = listOf(
                "Demonstra incapacidade cognitiva dos falantes em dominar as estruturas lógicas do idioma.",
                "Constitui erro crasso que deve ser sumariamente banido de qualquer manifestação comunicativa no país.",
                "Evidencia marcas legítimas do registro informal oral, cuja eficácia comunicativa é plenamente realizada no contexto de interação interpessoal.",
                "Prova que o português brasileiro sofreu degeneração estrutural em relação ao português lusitano.",
                "Indica que as variedades regionais são incompatíveis com qualquer expressão artística ou poética."
            ),
            correctIndex = 2,
            explanation = "Comentário do Prof. João Batista: No ENEM, a variação linguística é tratada sob o viés científico: não existe variedade 'superior' ou 'inferior', mas sim registros adequados ou inadequados ao contexto comunicativo. 'Cê qué' e 'tá cum' são formas coloquiais orais perfeitamente legítimas na interação cotidiana e na literatura popular, refutando qualquer preconceito linguístico (Alternativa C)."
        ),

        // QUESTÃO 5: Coesão Sequencial e Operadores Argumentativos
        EnemGrammarQuestion(
            id = "enem_port_5",
            enemYear = "ENEM 2022 • Caderno Rosa",
            topic = "Coesão & Conectivos",
            title = "Valor Semântico das Conjunções Subordinativas",
            baseText = "O avanço da inteligência artificial promete otimizar diagnósticos clínicos e democratizar procedimentos médicos em regiões remotas. ________ a infraestrutura digital permaneça deficiente em comunidades isoladas, tais benefícios continuarão restritos a uma minoria privilegiada, aprofundando o abismo social.",
            textSource = "Artigo de Opinião sobre Tecnologia e Saúde Pública, ENEM 2022.",
            prompt = "Para preencher a lacuna estabelecendo uma relação de concessão lógica entre os períodos sintáticos, deve-se empregar o conectivo:",
            options = listOf(
                "Porquanto",
                "Conquanto",
                "Por conseguinte",
                "Na medida em que",
                "Outrossim"
            ),
            correctIndex = 1,
            explanation = "Comentário do Prof. João Batista: 'Conquanto' é uma conjunção subordinativa concessiva (sinônima de 'embora', 'ainda que', 'se bem que'), ideal para introduzir uma ideia que contrasta sem anular a oração principal. 'Porquanto' e 'Na medida em que' têm valor causal; 'Por conseguinte' tem valor conclusivo; e 'Outrossim' tem valor aditivo."
        ),

        // QUESTÃO 6: Funções da Linguagem (Metalinguagem e Poética)
        EnemGrammarQuestion(
            id = "enem_port_6",
            enemYear = "ENEM 2023 • Caderno Amarelo",
            topic = "Funções da Linguagem",
            title = "A Linguagem Falando sobre o Próprio Código Verbal",
            baseText = "Catar feijão se limita com escrever:\njoga-se os grãos na água do alguidar\ne as palavras na folha de papel;\ne depois, joga-se fora o que boiar.\nCerta palavra frecheira quer entrar\nno verso, e você a empurra com a mão;\nela insiste, ela quer, mas não se deixa:\ncatar feijão é que é escrever o verso.",
            textSource = "MELO NETO, João Cabral de. Educação pela Pedra. Rio de Janeiro: Nova Fronteira, 1966.",
            prompt = "No poema de João Cabral de Melo Neto, a comparação entre o ato de selecionar palavras e o gesto cotidiano de catar feijão evidencia o predomínio da função:",
            options = listOf(
                "Fática, pois visa apenas testar o canal de comunicação entre o poeta e o leitor.",
                "Referencial ou denotativa, visto que seu único objetivo é ensinar culinária de forma didática.",
                "Metalinguística, pois utiliza o código poético para refletir criticamente sobre o próprio processo de composição poética.",
                "Conativa ou apelativa, já que busca convencer o interlocutor a mudar de comportamento alimentar.",
                "Emotiva ou expressiva pura, pois o foco reside exclusivamente nas lamentações sentimentais do eu lírico."
            ),
            correctIndex = 2,
            explanation = "Comentário do Prof. João Batista: A função metalinguística ocorre quando o código (a poesia, a palavra) é utilizado para explicar ou refletir sobre o próprio código (o processo de escrita). João Cabral compara o ato mecânico de depurar o feijão com o trabalho artesanal de lapidação do verso, caracterizando clássica metalinguagem no ENEM."
        ),

        // QUESTÃO 7: Regência Verbal dos Verbos Clássicos
        EnemGrammarQuestion(
            id = "enem_port_7",
            enemYear = "ENEM 2024 • Caderno Azul",
            topic = "Concordância & Regência",
            title = "Transitividade e Regência dos Verbos Assistir, Visar e Implicar",
            baseText = "Em campanhas de conscientização social, a redação dos enunciados precisa conciliar clareza comunicativa e rigor com a norma-padrão. Analise as formulações propostas por uma agência governamental:\nI. O projeto visa à ampliação de vagas no ensino técnico.\nII. Assistimos ao documentário sobre direitos indígenas ontem à noite.\nIII. A inércia governamental implica em sérios prejuízos à juventude.",
            textSource = "Manual de Redação e Comunicação Governamental, ENEM 2024.",
            prompt = "Quanto à regência verbal de acordo com a norma culta consagrada pela gramática tradicional, estão inteiramente corretas:",
            options = listOf(
                "Apenas a I.",
                "Apenas a II.",
                "Apenas I e II.",
                "Apenas II e III.",
                "I, II e III."
            ),
            correctIndex = 2,
            explanation = "Comentário do Prof. João Batista: Na I, 'visar' com sentido de 'ter como objetivo/almejar' é transitivo indireto (rege preposição 'a'): 'visa à ampliação' (correto com crase). Na II, 'assistir' no sentido de 'presenciar/ver' é transitivo indireto: 'assistimos ao documentário' (correto). Na III, o verbo 'implicar' no sentido de 'acarretar/produzir como consequência' é TRANSITIVO DIRETO (não aceita a preposição 'em'): o correto é 'implica sérios prejuízos'. Logo, apenas I e II estão corretas (Alternativa C)."
        ),

        // QUESTÃO 8: Figuras de Linguagem e Efeitos Semânticos
        EnemGrammarQuestion(
            id = "enem_port_8",
            enemYear = "ENEM 2022 • Caderno Azul",
            topic = "Figuras & Semântica",
            title = "Construção de Sentido por Antítese e Paradoxo",
            baseText = "Amor é fogo que arde sem se ver;\nÉ ferida que dói e não se sente;\nÉ um contentamento descontente;\nDor que desatina sem doer.\nÉ um não querer mais que bem querer;\nÉ um andar solitário entre a gente;\nÉ nunca contentar-se de contente;\nÉ um cuidar que ganha em se perder.",
            textSource = "CAMÕES, Luís de. Sonetos. Século XVI.",
            prompt = "A união de termos conceitualmente incompatíveis no mesmo enunciado, como em 'ferida que dói e não se sente' e 'um contentamento descontente', configura o recurso estilístico conhecido como:",
            options = listOf(
                "Hipérbole, que consiste no exagero deliberado com intuito cômico.",
                "Paradoxo (ou oxímoro), em que ideias aparentemente contraditórias coexistem para traduzir a complexidade do sentimento amoroso.",
                "Eufemismo, cuja finalidade é suavizar uma expressão desagradável ou trágica.",
                "Pleonasmo vicioso, caracterizado pela repetição inútil de termos sinônimos.",
                "Metonímia, que se baseia na substituição da parte pelo todo."
            ),
            correctIndex = 1,
            explanation = "Comentário do Prof. João Batista: O paradoxo (ou oxímoro) vai além da simples oposição de palavras (antítese): ele funde ideias mutuamente excludentes em uma mesma realidade conceptual ('contentamento que não contenta', 'ferida que dói mas não se sente'), revelando a natureza indizível e contraditória do amor camoniano."
        ),

        // QUESTÃO 9: Coesão Referencial e Emprego dos Pronomes Demonstrativos
        EnemGrammarQuestion(
            id = "enem_port_9",
            enemYear = "ENEM 2023 • Caderno Amarelo",
            topic = "Coesão & Conectivos",
            title = "Emprego de Este / Esse / Aquele na Coesão Textual",
            baseText = "A preservação dos biomas brasileiros e a garantia da soberania alimentar constituem dois desafios indissociáveis da contemporaneidade. ________ exige fiscalização rigorosa contra o desmatamento ilegal; ________, por sua vez, depende de incentivos à agricultura familiar sustentável. Para costurar tais ideias, o autor deve recorrer aos mecanismos de coesão referencial previstos na Competência 4.",
            textSource = "Guia do Participante do ENEM • Práticas de Coesão Textual, Inep 2023.",
            prompt = "Para recuperar os dois antecedentes citados ('A preservação dos biomas' e 'a garantia da soberania alimentar'), na ordem correta segundo a norma-padrão, as lacunas devem ser preenchidas por:",
            options = listOf(
                "'Esta' (para o primeiro citado) e 'aquela' (para o segundo citado).",
                "'Aquela' (retomando o termo mais distante: a preservação dos biomas) e 'esta' (retomando o termo imediatamente mais próximo: a garantia da soberania alimentar).",
                "'Essa' e 'essa outra', indiferentemente.",
                "'Nesta' e 'naquela', indicando circunstância de tempo futuro.",
                "'Isso' e 'aquilo', pois substantivos abstratos exigem pronomes neutros."
            ),
            correctIndex = 1,
            explanation = "Comentário do Prof. João Batista: Na coesão referencial clássica entre dois elementos já mencionados no texto: 'ESTE/ESTA' retoma o termo imediatamente mais próximo (o último citado: 'a garantia da soberania alimentar'); 'AQUELE/AQUELA' retoma o termo cronologicamente mais distante no período (o primeiro citado: 'A preservação dos biomas'). Portanto, a alternativa correta é a B."
        ),

        // QUESTÃO 10: Colocação Pronominal (Próclise, Ênclise e Mesóclise)
        EnemGrammarQuestion(
            id = "enem_port_10",
            enemYear = "ENEM 2024 • Caderno Branco",
            topic = "Sintaxe & Morfologia",
            title = "Casos de Próclise Obrigatória na Norma-Padrão",
            baseText = "No universo digital, muitos internautas compartilham mensagens sem verificar a veracidade das fontes. Em campanhas educativas, especialistas recomendam: 'Nunca se precipite ao repassar conteúdos', reforçando que 'quem te disse que isso era verdade?' é a pergunta fundamental contra a desinformação.",
            textSource = "Educação Midiática e Cidadania Digital, ENEM 2024.",
            prompt = "No trecho 'Nunca se precipite ao repassar conteúdos', a posição do pronome oblíquo antes do verbo (próclise) justifica-se gramaticalmente pela:",
            options = listOf(
                "Presença da palavra de sentido negativo 'Nunca', que atua como fator atrativo obrigatório do pronome.",
                "Existência de verbo no imperativo afirmativo, que impõe próclise em frases curtas.",
                "Preferência estilística opcional do autor, que poderia livremente escrever 'Nunca precipite-se'.",
                "Ocorrência de oração coordenada sindética adversativa.",
                "Exigência de ênclise sempre que o verbo terminar em vogal temática."
            ),
            correctIndex = 0,
            explanation = "Comentário do Prof. João Batista: Palavras de sentido negativo (nunca, jamais, não, nada, ninguém) são fatores atrativos absolutos de próclise na norma culta. É vedada a ênclise após palavras negativas ('Nunca precipite-se' é desvio gramatical na Competência 1 do ENEM). Correta a alternativa A."
        ),

        // QUESTÃO 11: Ambiguidade e Estrutura Sintática
        EnemGrammarQuestion(
            id = "enem_port_11",
            enemYear = "ENEM 2022 • Caderno Amarelo",
            topic = "Figuras & Semântica",
            title = "Ambiguidade Sintática Provocada por Adjuntos Adnominais",
            baseText = "Manchete de jornal: 'O prefeito proibiu o tráfego de caminhões velhos na avenida central da cidade durante o feriado.'\nA organização dos termos em uma oração pode gerar duplo sentido (ambiguidade), prejudicando a clareza da mensagem pretendida.",
            textSource = "Exame Nacional do Ensino Médio • Linguagens e Códigos, 2022.",
            prompt = "A ambiguidade da manchete reside no fato de que o adjetivo 'velhos' pode se referir tanto a:",
            options = listOf(
                "'prefeito' quanto a 'tráfego'.",
                "'caminhões' quanto a 'tráfego de caminhões'.",
                "'caminhões' (apenas os veículos antigos estão proibidos) quanto a qualificar depreciativamente a frota geral de caminhões já existente na avenida.",
                "'feriado' quanto a 'avenida'.",
                "'cidade' quanto ao 'feriado prolongado'."
            ),
            correctIndex = 2,
            explanation = "Comentário do Prof. João Batista: A construção gera dúvida sobre a restrição: a proibição afeta apenas os caminhões que têm muito tempo de uso ('caminhões que são velhos') ou todos os caminhões foram proibidos e o redator os qualificou genericamente? No ENEM, identificar e corrigir ambiguidades sintáticas é competência basilar de leitura e produção textual."
        ),

        // QUESTÃO 12: Gêneros Textuais e Tipologia Dissertativa
        EnemGrammarQuestion(
            id = "enem_port_12",
            enemYear = "ENEM 2023 • Caderno Rosa",
            topic = "Gêneros Textuais",
            title = "Marcas Linguísticas da Argumentação no Artigo de Opinião",
            baseText = "É inadmissível que, em pleno século XXI, ainda convivamos com índices alarmantes de analfabetismo funcional nas periferias. Tal panorama não decorre do acaso, mas da renitente omissão das elites políticas em tratar a educação básica como prioridade orçamentária. Precisamos, com urgência, refundar o pacto civilizatório.",
            textSource = "Editorial de Jornal de Grande Circulação, ENEM 2023.",
            prompt = "O trecho estrutura-se predominantemente no tipo textual dissertativo-argumentativo porque:",
            options = listOf(
                "Relata uma sequência cronológica de eventos vividos por personagens fictícios em determinado espaço.",
                "Descreve detalhadamente os aspectos físicos e geográficos dos bairros periféricos brasileiros.",
                "Defende um ponto de vista crítico sustentado por juízos de valor ('inadmissível', 'renitente omissão') com vistas a persuadir o leitor.",
                "Instrui o leitor passo a passo sobre como realizar um procedimento burocrático (tipo injuntivo).",
                "Limita-se a expor dados estatísticos sem qualquer posicionamento ideológico ou marcas de autoria."
            ),
            correctIndex = 2,
            explanation = "Comentário do Prof. João Batista: O tipo dissertativo-argumentativo caracteriza-se pela defesa de uma tese (posicionamento crítico) por meio de argumentos lógicos e marcas de modalização apreciativa ('inadmissível', 'omissão', 'precisamos com urgência'), objetivando convencer e mobilizar o interlocutor. Alternativa C."
        ),

        // QUESTÃO 13: Concordância Nominal e Adjetivos Pós-postos
        EnemGrammarQuestion(
            id = "enem_port_13",
            enemYear = "ENEM 2024 • Caderno Amarelo",
            topic = "Concordância & Regência",
            title = "Concordância de Adjetivo com Substantivos de Gêneros Diferentes",
            baseText = "A escola contemporânea deve garantir ambiente e formação ________ para acolher as diversidades de seus estudantes. Ao redigir a frase, a comissão pedagógica debateu se o adjetivo 'adequado' deveria concordar com o termo mais próximo ou com a soma de ambos os substantivos.",
            textSource = "Diretrizes Nacionais de Convivência Escolar, ENEM 2024.",
            prompt = "De acordo com a norma-padrão, as formas corretas para preencher a lacuna são:",
            options = listOf(
                "Apenas 'adequada', concordando exclusivamente com 'formação'.",
                "Apenas 'adequados', concordando obrigatoriamente no plural masculino.",
                "Tanto 'adequada' (concordando por atração com o substantivo mais próximo) quanto 'adequados' (concordando no plural masculino com ambos os núcleos).",
                "Apenas 'adequado', ignorando a flexão de gênero do substantivo feminino.",
                "Nenhuma das formas acima, sendo necessária a substituição por locução adverbial."
            ),
            correctIndex = 2,
            explanation = "Comentário do Prof. João Batista: Quando um adjetivo se pospõe a dois ou mais substantivos de gêneros diferentes ('ambiente' [masculino] e 'formação' [feminino]), a norma-padrão admite duas construções: 1) concordância atrativa com o termo mais próximo ('ambiente e formação ADEQUADA'); 2) concordância gramatical com ambos os termos, indo para o plural masculino ('ambiente e formação ADEQUADOS'). Ambas são legítimas (Alternativa C)."
        ),

        // QUESTÃO 14: Polissemia e Significação Contextual
        EnemGrammarQuestion(
            id = "enem_port_14",
            enemYear = "ENEM 2023 • Caderno Branco",
            topic = "Figuras & Semântica",
            title = "Sentido Denotativo versus Conotativo",
            baseText = "O rio que fazia uma volta atrás de nossa casa era uma linha de água viva que bordava a terra. Hoje, o leito seco é uma cicatriz aberta na paisagem, denunciando a sede da terra e o descaso humano.",
            textSource = "Crônica ambiental e literária contemporânea, ENEM 2023.",
            prompt = "A expressão 'cicatriz aberta na paisagem' exemplifica o emprego da linguagem:",
            options = listOf(
                "Denotativa, pois refere-se a uma intervenção cirúrgica real realizada no solo da região.",
                "Conotativa ou figurada, na qual a palavra 'cicatriz' ganha dimensão metafórica para representar a marca destrutiva e indelével deixada pela seca e pela ação humana.",
                "Técnica e estritamente geomorfológica, desprovida de qualquer carga expressiva.",
                "Pleomórfica, pois altera a estrutura fonológica dos substantivos envolvidos.",
                "Eufemística, tendo sido escolhida unicamente para disfarçar a gravidade do problema."
            ),
            correctIndex = 1,
            explanation = "Comentário do Prof. João Batista: A linguagem conotativa atribui novos significados poéticos e metafóricos às palavras além de sua acepção dicionarizada exata. 'Cicatriz aberta' compara a ferida biológica ao dano ambiental duradouro no leito do rio, intensificando a crítica ecológica comovente (Alternativa B)."
        ),

        // QUESTÃO 15: Tempos e Modos Verbais no Texto
        EnemGrammarQuestion(
            id = "enem_port_15",
            enemYear = "ENEM 2021 • Caderno Azul",
            topic = "Sintaxe & Morfologia",
            title = "Emprego do Futuro do Pretérito e Hipótese Argumentativa",
            baseText = "Se houvesse investimento contínuo em saneamento básico nas favelas brasileiras, os gastos com internações por doenças infectocontagiosas no SUS seriam drasticamente reduzidos. Essa correlação comprova que cada real aplicado em saneamento gera economia quádrupla na saúde pública.",
            textSource = "Relatório da Organização Pan-Americana da Saúde, citado no ENEM 2021.",
            prompt = "A correlação verbal estabelecida entre a oração 'Se houvesse investimento...' e '...seriam drasticamente reduzidos' expressa:",
            options = listOf(
                "Uma certeza absoluta sobre fatos consumados no pretérito perfeito.",
                "Uma hipótese ou condição no pretérito imperfeito do subjuntivo articulada a uma consequência provável no futuro do pretérito do indicativo.",
                "Uma ordem expressa pelo modo imperativo afirmativo com intuito coercitivo.",
                "Um hábito rotineiro do presente do indicativo sem qualquer dependência condicional.",
                "Uma contradição sintática que invalida a coerência interna do raciocínio lógico."
            ),
            correctIndex = 1,
            explanation = "Comentário do Prof. João Batista: Trata-se da correlação clássica da norma culta entre o Pretérito Imperfeito do Subjuntivo ('Se houvesse') e o Futuro do Pretérito do Indicativo ('seriam'). Essa estrutura expressa uma hipótese/condição irreal ou potencial e sua decorrência lógica, muito valorizada na articulação argumentativa da redação e da prova de Linguagens do ENEM."
        )
    )
}
