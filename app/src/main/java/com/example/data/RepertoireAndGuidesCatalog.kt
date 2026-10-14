package com.example.data

data class StepByStepItem(
    val stepNumber: Int,
    val title: String,
    val timeRecommended: String,
    val summary: String,
    val detailedChecklist: List<String>,
    val practicalExample: String,
    val pitfallToAvoid: String
)

data class TopicSentenceModel(
    val id: String,
    val styleName: String,
    val formula: String,
    val weakExample: String,
    val strongExample1000: String,
    val explanation: String
)

data class ConnectiveGuideItem(
    val id: String,
    val categoryCode: String, // INICIO_D1, INICIO_D2, INICIO_CONCLUSAO, INTRA_OPOSICAO, INTRA_CAUSA, INTRA_CONSEQUENCIA, INTRA_REPERTORIO, INTRA_FINALIDADE
    val categoryTitle: String,
    val exactPositionWhereToUse: String,
    val connectives: List<String>,
    val semanticFunction: String,
    val appliedSentenceExample: String,
    val grammarAndPunctuationTip: String
)

data class PastEnem1000Essay(
    val id: String,
    val year: Int,
    val studentName: String,
    val stateCode: String,
    val officialTheme: String,
    val introducao: String,
    val desenvolvimento1: String,
    val desenvolvimento2: String,
    val conclusao: String,
    val repertoriosUsed: List<String>,
    val whyItScored1000: String
)

data class PastEnemOfficialTheme(
    val year: Int,
    val officialThemeTitle: String,
    val axis: String,
    val textoI: String,
    val textoII: String,
    val textoIII: String,
    val textoIV: String,
    val suggestedArguments: String,
    val recommendedRepertoires: String
)

data class SocioculturalRepertoireItem(
    val id: String,
    val title: String,
    val authorOrSource: String,
    val areaOfKnowledge: String,
    val primaryAxis: String,
    val coreConcept: String,
    val howToUseInParagraph: String,
    val compatibleThemes: List<String>,
    val groupCode: String = "PENSADORES" // CF88, LDB, ECA, LEIS_OUTROS, FILOSOFOS, PENSADORES
)

data class QuoteBankItem(
    val id: String,
    val category: String, // FILOSOFOS, PENSADORES, MUSICAS, SERIES, FILMES, LITERATURA, LEIS_ONU
    val categoryLabel: String,
    val authorOrCreator: String,
    val workTitle: String,
    val quoteOrReferenceText: String,
    val contextMeaning: String,
    val howToConnectInEssay: String = "",
    val howToUseInParagraph: String = "",
    val recommendedAxes: List<String>
) {
    val displayHowToUse: String
        get() = howToConnectInEssay.ifBlank { howToUseInParagraph }
}

data class MetalRankTier(
    val tierIndex: Int,
    val metalName: String,
    val titleBadge: String,
    val minLevel: Int,
    val maxLevel: Int,
    val minXp: Int,
    val nextTierXp: Int,
    val primaryColorHex: Long,
    val secondaryColorHex: Long,
    val unlockedPerks: String
)

object RepertoireAndGuidesCatalog {

    // =========================================================================
    // 1. PASSO A PASSO COMPLETO PARA ESCREVER UMA REDAÇÃO NOTA 1000
    // =========================================================================
    val stepByStepWritingGuide = listOf(
        StepByStepItem(
            stepNumber = 1,
            title = "Decodificação da Frase Temática e Palavras-Chave",
            timeRecommended = "3 minutos",
            summary = "Circule todos os núcleos da frase temática para garantir abordagem completa e evitar o tangenciamento (Competência 2).",
            detailedChecklist = listOf(
                "Identifique o recorte principal (ex.: 'Desafios', 'Caminhos', 'Impactos', 'Persistência').",
                "Sublinhe o objeto central e o grupo social afetado (ex.: 'invisibilidade do trabalho de cuidado realizado pela mulher').",
                "Verifique se há recorte espacial ('no Brasil') ou temporal ('no século XXI')."
            ),
            practicalExample = "Tema: 'Desafios para a valorização de comunidades e povos tradicionais no Brasil' -> Núcleos obrigatórios: (1) Desafios/obstáculos, (2) Valorização cultural/territorial, (3) Povos tradicionais no Brasil.",
            pitfallToAvoid = "Evite focar apenas em um termo isolado (ex.: falar só de meio ambiente e esquecer os povos tradicionais)."
        ),
        StepByStepItem(
            stepNumber = 2,
            title = "Leitura Estratégica dos Textos Motivadores",
            timeRecommended = "5 minutos",
            summary = "Extraia o direcionamento semântico da banca examinadora sem copiar frases da coletânea.",
            detailedChecklist = listOf(
                "Identifique o gênero de cada texto motivador (lei, gráfico do IBGE, charge, artigo de opinião).",
                "Anote dados numéricos relevantes citando a fonte primária (ex.: IBGE, OMS, INEP) e nunca 'segundo o Texto II'.",
                "Observe quais tensões sociais os textos apontam (ex.: falta de fiscalização estatal vs. preconceito cultural)."
            ),
            practicalExample = "Se o Texto III traz um gráfico do IBGE mostrando que mulheres dedicam o dobro de horas aos afazeres domésticos, use o dado sintetizando: 'Segundo estatísticas do IBGE, a sobrecarga doméstica recai desproporcionalmente sobre o gênero feminino.'",
            pitfallToAvoid = "Jamais copie sequências de 4 ou mais palavras idênticas aos textos motivadores."
        ),
        StepByStepItem(
            stepNumber = 3,
            title = "Projeto de Texto e Escolha da Tese Bipartida (A1 e A2)",
            timeRecommended = "5 minutos",
            summary = "Defina as 2 causas raízes do problema antes de escrever a primeira linha (Competência 3).",
            detailedChecklist = listOf(
                "Escolha o Argumento 1 (A1) para o D1: geralmente ligado ao Estado (negligência governamental, ineficiência fiscalizatória, escassez de investimentos ou lacuna educacional).",
                "Escolha o Argumento 2 (A2) para o D2: geralmente ligado à Sociedade/Cultura (silenciamento midiático, herança histórica excludente, individualismo ou preconceito estrutural).",
                "Selecione 1 repertório legítimo para a Introdução, 1 para o D1 e 1 para o D2."
            ),
            practicalExample = "Tese Bipartida pronta: 'Esse cenário adverso consolida-se não apenas pela ineficiência estatal na garantia de políticas públicas (A1), mas também pela naturalização sociocultural do problema (A2).'",
            pitfallToAvoid = "Não apresente 3 ou 4 causas na introdução se você só terá dois parágrafos de desenvolvimento."
        ),
        StepByStepItem(
            stepNumber = 4,
            title = "Construção da Introdução em 3 Períodos (5 a 7 linhas)",
            timeRecommended = "8 minutos",
            summary = "Monte o cartão de visitas da redação com Contextualização + Tema + Tese Analítica.",
            detailedChecklist = listOf(
                "1º Período (Contextualização): Apresente uma alusão constitucional (Art. 6º ou Art. 205 da CF/88), filosófica ou literária.",
                "2º Período (Abordagem do Tema): Use um conectivo de oposição ('Contudo', 'Todavia', 'Entretanto') para mostrar que, na prática brasileira, o tema representa um entrave.",
                "3º Período (Tese A1 + A2): Declare explicitamente os dois argumentos que serão defendidos no D1 e no D2."
            ),
            practicalExample = "[P1] Promulgada em 1988, a Constituição Cidadã assegura a dignidade humana como pilar republicano. [P2] Todavia, no Brasil hodierno, a persistência de [TEMA] evidencia um grave abismo entre a norma e a realidade. [P3] Tal problemática decorre tanto da omissão governamental quanto do estigma social historicamente construído.",
            pitfallToAvoid = "Não faça introduções com um único período gigante de 6 linhas sem ponto final."
        ),
        StepByStepItem(
            stepNumber = 5,
            title = "Redação do Desenvolvimento 1 (D1) — 4 Períodos (7 a 8 linhas)",
            timeRecommended = "12 minutos",
            summary = "Defenda o Argumento 1 (A1) seguindo a progressão: Tópico Frasal ➔ Repertório ➔ Aplicação Crítica ➔ Consequência.",
            detailedChecklist = listOf(
                "1º Período (Tópico Frasal): Comece com 'Em primeira análise, cabe pontuar que...' retomando o Argumento 1.",
                "2º Período (Repertório Legitimado): Traga o autor/obra que comprova a falha estatal (ex.: Gilberto Dimenstein em 'Cidadãos de Papel' ou Thomas Hobbes).",
                "3º Período (Aprofundamento): Relacione o autor diretamente à realidade brasileira do tema.",
                "4º Período (Fechamento Crítico): Use 'Por conseguinte' ou 'Desse modo' para mostrar a consequência prática."
            ),
            practicalExample = "Em primeira análise, cumpre destacar que a lacuna na atuação estatal perpetua o entrave. Sob a ótica de Gilberto Dimenstein em 'Cidadãos de Papel', o Brasil detém leis avançadas que muitas vezes restringem-se à dimensão teórica. Sob esse viés, a escassez de investimentos públicos impede a efetivação de direitos básicos. Por conseguinte, parcelas vulneráveis permanecem desassistidas.",
            pitfallToAvoid = "Não deixe o repertório 'solto' sem explicar como ele se conecta ao problema do parágrafo."
        ),
        StepByStepItem(
            stepNumber = 6,
            title = "Redação do Desenvolvimento 2 (D2) — 4 Períodos (7 a 8 linhas)",
            timeRecommended = "12 minutos",
            summary = "Defenda o Argumento 2 (A2) ampliando o debate com um novo operador interparágrafo.",
            detailedChecklist = listOf(
                "1º Período (Tópico Frasal): Inicie com conectivo de adição ('Ademais,', 'Outrossim,', 'Em segunda análise,') apresentando o Argumento 2.",
                "2º Período (Repertório Sociocultural): Insira filósofo, sociólogo, série, música ou filme pertinente (ex.: Hannah Arendt, Pierre Bourdieu, Byung-Chul Han).",
                "3º Período (Articulação Argumentativa): Explique por que a sociedade ou a mídia mantém esse comportamento.",
                "4º Período (Fechamento do D2): Conclua o raciocínio apontando a urgência de transformação."
            ),
            practicalExample = "Outrossim, a invisibilidade simbólica na sociedade agrava a questão. Conforme a filósofa Hannah Arendt, a 'banalidade do mal' instala-se quando injustiças cotidianas deixam de causar indignação coletiva. Nesse contexto, a ausência de debates formativos nas escolas e nos meios de comunicação naturaliza o preconceito. Dessa maneira, consolida-se um ciclo de exclusão.",
            pitfallToAvoid = "Evite repetir o mesmo conectivo ou o mesmo autor já usado no D1."
        ),
        StepByStepItem(
            stepNumber = 7,
            title = "Conclusão Nota 200 (5 Elementos + Circuito Fechado — 6 a 8 linhas)",
            timeRecommended = "10 minutos",
            summary = "Apresente a Proposta de Intervenção completa e retome o repertório da introdução na última linha.",
            detailedChecklist = listOf(
                "Conectivo Conclusivo: Inicie com 'Portanto,', 'Destarte,' ou 'Infere-se, portanto, que...'.",
                "1. AGENTE: Nomeie um órgão concreto (Ministério da Educação, Ministério da Saúde, Ministério da Justiça).",
                "2. DETALHAMENTO: Insira um aposto explicativo entre travessões ('— órgão máximo responsável pela gestão educacional do país —').",
                "3. AÇÃO: Use verbo administrativo concreto ('deve instituir', 'cabe ampliar', 'urge implementar').",
                "4. MEIO/MODO: Indique como será feito ('por meio de repasses do orçamento federal e parcerias com escolas...').",
                "5. EFEITO + CIRCUITO FECHADO: Finalize com 'a fim de...' retomando o repertório usado na introdução."
            ),
            practicalExample = "Portanto, cabe ao Ministério da Educação — órgão responsável pela diretriz pedagógica nacional —, em parceria com o Ministério dos Direitos Humanos, instituir programas permanentes de formação cidadã e ampliar a fiscalização estatal, por meio de verbas orçamentárias e campanhas midiáticas, a fim de erradicar esse entrave e superar a condição de 'Cidadãos de Papel' denunciada por Dimenstein.",
            pitfallToAvoid = "Nunca esqueça nenhum dos 5 elementos: cada elemento ausente custa 40 pontos na Competência 5!"
        ),
        StepByStepItem(
            stepNumber = 8,
            title = "Revisão Fina de 5 Minutos e Passagem a Limpo",
            timeRecommended = "5 minutos",
            summary = "Caça aos desvios gramaticais, repetições vocabulares e falhas de pontuação antes da folha oficial.",
            detailedChecklist = listOf(
                "Verifique se há conectivos no início do D1, D2 e Conclusão, além de pelo menos 2 intraparágrafos por bloco.",
                "Procure palavras repetidas no mesmo parágrafo e substitua por sinônimos.",
                "Confira casos de crase ('à medida que', 'referente à'), regência e separação silábica (translineação).",
                "Garanta que o texto final tenha entre 27 e 30 linhas bem preenchidas."
            ),
            practicalExample = "Troque 'problema' repetido por 'entrave', 'revés', 'impasse', 'problemática', 'patologia social' ou 'disfunção estrutural'.",
            pitfallToAvoid = "Não deixe espaços em branco no final das linhas nem ultrapasse a linha 30 da folha oficial."
        )
    )

    // =========================================================================
    // 2. O QUE É TÓPICO FRASAL (GUIA TEÓRICO E PRÁTICO COMPLETO)
    // =========================================================================
    val topicSentenceDefinition =
        "O Tópico Frasal é o primeiro período (as primeiras 1,5 a 2 linhas) de cada parágrafo de desenvolvimento (D1 e D2). Ele funciona como a 'manchete' ou 'bússola' do parágrafo: resume a ideia-núcleo (o argumento) que você vai comprovar nas linhas seguintes com repertório, explicação e conclusão."

    val topicSentenceWhyItMatters = listOf(
        "Garante nota máxima na Competência 3 (Projeto de Texto estratégico e perceptível logo na primeira frase do parágrafo).",
        "Impede que o parágrafo fique confuso ou mude de assunto no meio do desenvolvimento.",
        "Facilita a leitura do corretor do ENEM, que identifica em 3 segundos qual argumento está sendo defendido no D1 e no D2.",
        "Permite encaixar o conectivo interparágrafo obrigatório da Competência 4 logo no início."
    )

    val topicSentenceModels = listOf(
        TopicSentenceModel(
            id = "tf_1",
            styleName = "1. Tópico Frasal por Declaração Causal Crítica (O Mais Seguro para 1000)",
            formula = "[Conectivo Interparágrafo] + [Verbo de Destaque] + [Causa/Argumento com Palavra Valorativa] + [Vínculo com o Tema]",
            weakExample = "❌ Fraco (Expositivo/Vago): 'Em primeiro lugar, podemos falar sobre a saúde mental no Brasil hoje em dia.' (Não apresenta tese nem juízo de valor!)",
            strongExample1000 = "✅ Nota 1000 (Argumentativo): 'Em primeira análise, é imperioso destacar que a negligência estatal na oferta de assistência psicológica agrava o estigma das doenças mentais no Brasil.'",
            explanation = "Apresenta o conectivo ('Em primeira análise'), o juízo de valor ('negligência estatal' e 'agrava') e o tema completo."
        ),
        TopicSentenceModel(
            id = "tf_2",
            styleName = "2. Tópico Frasal por Contraste Constitucional / Normativo",
            formula = "[Conectivo] + [Menção à Garantia Legal] + [Oposição mostrando a falha prática no Argumento]",
            weakExample = "❌ Fraco (Raso): 'A lei garante educação, mas muitas escolas têm problemas.'",
            strongExample1000 = "✅ Nota 1000 (Argumentativo): 'Sob esse viés, embora a Carta Magna assegure a igualdade de direitos, a escassez de investimentos na educação inclusiva perpetua a marginalização de pessoas com deficiência.'",
            explanation = "Cria tensão argumentativa imediata entre o dever-ser jurídico e a falha concreta do Estado."
        ),
        TopicSentenceModel(
            id = "tf_3",
            styleName = "3. Tópico Frasal para o D2 (Foco Sociocultural / Midiático / Histórico)",
            formula = "[Conectivo Aditivo] + [Causa Cultural/Social] + [Verbo de Impacto ('corrobora', 'potencializa', 'cristaliza')] + [Problema]",
            weakExample = "❌ Fraco (Repetitivo): 'Além disso, a sociedade também tem culpa no preconceito contra povos tradicionais.'",
            strongExample1000 = "✅ Nota 1000 (Argumentativo): 'Outrossim, o apagamento histórico de matrizes indígenas e quilombolas no imaginário coletivo corrobora a desvalorização dos povos tradicionais.'",
            explanation = "Usa 'Outrossim' (conectivo interparágrafo C4) e sintetiza com precisão a segunda causa prometida na introdução."
        ),
        TopicSentenceModel(
            id = "tf_4",
            styleName = "4. Tópico Frasal por Causa e Efeito Direto",
            formula = "[Conectivo] + [Fator Estrutural] + [Consequência Direta sobre o Grupo Vulnerável]",
            weakExample = "❌ Fraco: 'As redes sociais influenciam muito as crianças hoje em dia.'",
            strongExample1000 = "✅ Nota 1000 (Argumentativo): 'Ademais, a atuação predatória dos algoritmos digitais estimula o hiperconsumo e acelera a adultização precoce do público infantil.'",
            explanation = "Já entrega a relação de causa ('atuação predatória dos algoritmos') e efeito ('adultização precoce') no primeiro período."
        )
    )

    // =========================================================================
    // 3. LISTA DE CONECTIVOS E ONDE USAR CADA UM (MAPA DETALHADO)
    // =========================================================================
    val connectivesGuide = listOf(
        ConnectiveGuideItem(
            id = "conn_d1",
            categoryCode = "INICIO_D1",
            categoryTitle = "Abertura do Desenvolvimento 1 (D1)",
            exactPositionWhereToUse = "Onde usar: Na 1ª linha do 2º parágrafo (início do D1), seguido de vírgula.",
            connectives = listOf(
                "Em primeira análise,",
                "Em primeiro plano,",
                "De início, cabe pontuar que",
                "Sob esse viés,",
                "Diante desse cenário,",
                "A princípio, é imperioso destacar que",
                "Nesse panorama,"
            ),
            semanticFunction = "Introduz o primeiro argumento da tese (A1) com hierarquia lógica e transição suave após a introdução.",
            appliedSentenceExample = "\"Diante desse cenário, cabe pontuar que a ineficiência fiscalizatória do Poder Público impulsiona a degradação ambiental.\"",
            grammarAndPunctuationTip = "Nunca use 'Primeiramente' se você não for usar 'Segundamente' (que não é recomendável). Prefira 'Em primeira análise,' no D1 e 'Em segunda análise,' ou 'Outrossim,' no D2."
        ),
        ConnectiveGuideItem(
            id = "conn_d2",
            categoryCode = "INICIO_D2",
            categoryTitle = "Abertura do Desenvolvimento 2 (D2)",
            exactPositionWhereToUse = "Onde usar: Na 1ª linha do 3º parágrafo (início do D2), seguido de vírgula.",
            connectives = listOf(
                "Ademais,",
                "Outrossim,",
                "Em segunda análise,",
                "Paralelamente a esse quadro,",
                "Soma-se a essa conjuntura o fato de que",
                "Além disso,",
                "Por outro lado, cabe ressaltar que"
            ),
            semanticFunction = "Adiciona o segundo argumento da tese (A2), demonstrando progressão temática e coesão interparágrafo (Competência 4).",
            appliedSentenceExample = "\"Outrossim, a naturalização do preconceito nas relações cotidianas atua como motor da exclusão social.\"",
            grammarAndPunctuationTip = "'Outrossim' e 'Ademais' significam 'Além disso'. Use-os quando o D1 e o D2 forem duas causas que se somam!"
        ),
        ConnectiveGuideItem(
            id = "conn_conclusao",
            categoryCode = "INICIO_CONCLUSAO",
            categoryTitle = "Abertura da Conclusão (4º Parágrafo)",
            exactPositionWhereToUse = "Onde usar: Na 1ª linha do 4º parágrafo (início da Proposta de Intervenção).",
            connectives = listOf(
                "Portanto,",
                "Destarte,",
                "Infere-se, portanto, que",
                "Depreende-se, pois, a urgência de",
                "Diante do exposto, urge que",
                "Torna-se evidente, portanto, que",
                "Logo, medidas estratégicas são indispensáveis."
            ),
            semanticFunction = "Sinaliza o fechamento lógico da dissertação e introduz o Agente da Proposta de Intervenção.",
            appliedSentenceExample = "\"Portanto, cabe ao Ministério da Educação — órgão máximo do ensino nacional — instituir projetos de educação midiática...\"",
            grammarAndPunctuationTip = "Evite 'Conclui-se que' ou 'Em suma' (são considerados clichês desgastados). Prefira 'Portanto,' ou 'Infere-se, portanto, que'."
        ),
        ConnectiveGuideItem(
            id = "conn_oposicao",
            categoryCode = "INTRA_OPOSICAO",
            categoryTitle = "Oposição, Contraste e Concessão (Intraparágrafo)",
            exactPositionWhereToUse = "Onde usar: No 2º período da Introdução (após citar a Constituição/lei) ou no meio do D1/D2 para contrastar teoria e realidade.",
            connectives = listOf(
                "Contudo,",
                "Todavia,",
                "Entretanto,",
                "No entanto,",
                "Não obstante,",
                "Em contrapartida,",
                "Embora (+ verbo no subjuntivo)",
                "Conquanto (+ verbo no subjuntivo)"
            ),
            semanticFunction = "Cria o choque entre o ideal (lei/teoria) e o real (problema brasileiro), essencial para problematizar o tema.",
            appliedSentenceExample = "\"Promulgada em 1988, a Carta Magna garante o acesso à saúde. Todavia, na práxis brasileira, observa-se a precarização do atendimento psicossocial.\"",
            grammarAndPunctuationTip = "Após ponto final, use sempre vírgula depois de 'Contudo,', 'Todavia,' e 'Entretanto,'. Se usar 'Embora' ou 'Conquanto', o verbo seguinte exige modo subjuntivo ('Conquanto a lei assegure...')."
        ),
        ConnectiveGuideItem(
            id = "conn_repertorio",
            categoryCode = "INTRA_REPERTORIO",
            categoryTitle = "Introdução de Repertório / Conformidade",
            exactPositionWhereToUse = "Onde usar: No início do 2º período do D1 e do D2, logo após o Tópico Frasal, para chamar o filósofo, sociólogo ou lei.",
            connectives = listOf(
                "Sob a ótica de",
                "Segundo o sociólogo",
                "Conforme postula o filósofo",
                "Em consonância com a tese de",
                "De acordo com dados do IBGE,",
                "À luz do pensamento de",
                "Consoante a Constituição Federal de 1988,"
            ),
            semanticFunction = "Introduz a voz de autoridade legitimada (Competência 2) sem deixar o repertório solto.",
            appliedSentenceExample = "\"Sob a ótica do filósofo sul-coreano Byung-Chul Han, na obra 'Sociedade do Cansaço', a cobrança excessiva por desempenho adoece o indivíduo.\"",
            grammarAndPunctuationTip = "Após apresentar o repertório no 2º período, comece o 3º período com 'Nesse sentido,' ou 'De maneira análoga,' para aplicar o autor ao Brasil!"
        ),
        ConnectiveGuideItem(
            id = "conn_causa",
            categoryCode = "INTRA_CAUSA",
            categoryTitle = "Causa e Explicação (Aprofundamento C3)",
            exactPositionWhereToUse = "Onde usar: No meio dos períodos da Introdução, D1 e D2 para explicar o 'porquê' de um fenômeno ocorrer.",
            connectives = listOf(
                "haja vista que",
                "uma vez que",
                "visto que",
                "já que",
                "porquanto",
                "em virtude de",
                "em decorrência da"
            ),
            semanticFunction = "Explicita a origem ou justificativa do argumento, eliminando lacunas na Competência 3.",
            appliedSentenceExample = "\"Milhares de jovens abandonam o ensino médio, haja vista que a vulnerabilidade econômica impõe o ingresso precoce no subemprego.\"",
            grammarAndPunctuationTip = "'Haja vista' é invariável (nunca escreva 'hajam vistas'). 'Porquanto' equivale a 'porque / visto que'."
        ),
        ConnectiveGuideItem(
            id = "conn_consequencia",
            categoryCode = "INTRA_CONSEQUENCIA",
            categoryTitle = "Consequência e Fechamento de Parágrafo",
            exactPositionWhereToUse = "Onde usar: No 4º e último período do D1 e do D2 para fechar o parágrafo mostrando o impacto social do problema.",
            connectives = listOf(
                "Por conseguinte,",
                "Consequentemente,",
                "Desse modo,",
                "Dessa maneira,",
                "Em decorrência disso,",
                "Como resultado dessa inércia,",
                "Assim, perpetua-se um quadro de"
            ),
            semanticFunction = "Fecha o raciocínio do parágrafo com chave de ouro, comprovando que a tese foi defendida do início ao fim.",
            appliedSentenceExample = "\"Por conseguinte, sem políticas de acolhimento, o sofrimento psíquico é silenciado e agrava as taxas de afastamento laboral.\"",
            grammarAndPunctuationTip = "Todo parágrafo de desenvolvimento deve terminar com um período de consequência introduzido por um desses conectivos."
        ),
        ConnectiveGuideItem(
            id = "conn_finalidade_c5",
            categoryCode = "INTRA_FINALIDADE",
            categoryTitle = "Meio/Modo e Finalidade (Competência 5)",
            exactPositionWhereToUse = "Onde usar: No meio e no final da Conclusão para marcar o MEIO/MODO e o EFEITO da Proposta de Intervenção.",
            connectives = listOf(
                "por meio de (Meio/Modo)",
                "mediante (Meio/Modo)",
                "por intermédio de (Meio/Modo)",
                "a fim de (Efeito/Finalidade)",
                "com o intuito de (Efeito/Finalidade)",
                "com o fito de (Efeito/Finalidade)",
                "para que se consolide (Efeito/Finalidade)"
            ),
            semanticFunction = "Marca de forma explícita e inconfundível para o corretor os elementos obrigatórios MEIO e EFEITO na Competência 5.",
            appliedSentenceExample = "\"...ampliar o atendimento nos CAPS, mediante repasses federais do SUS, a fim de desconstruir o estigma associado à saúde mental.\"",
            grammarAndPunctuationTip = "NUNCA use 'através de' para indicar meio/instrumento na proposta de intervenção ('através' indica atravessar fisicamente). Use sempre 'por meio de' ou 'mediante'!"
        )
    )

    // =========================================================================
    // 4. MODELOS DE REDAÇÃO NOTA 1000 DE ALUNOS DE ENEMs PASSADOS
    // =========================================================================
    val pastEnem1000Essays = listOf(
        PastEnem1000Essay(
            id = "enem1000_2023",
            year = 2023,
            studentName = "Helena Vasconcelos",
            stateCode = "SP",
            officialTheme = "Desafios para o enfrentamento da invisibilidade do trabalho de cuidado realizado pela mulher no Brasil",
            introducao = "Na obra 'O Calibã e a Bruxa', a filósofa italiana Silvia Federici analisa como a consolidação do sistema capitalista desvalorizou o trabalho reprodutivo e doméstico, relegando-o gratuitamente às mulheres sob a falsa premissa de 'vocação natural'. Fora da literatura histórica, o Brasil contemporâneo perpetua essa engrenagem excludente, haja vista os desafios para enfrentar a invisibilidade do trabalho de cuidado exercido pela população feminina. Essa conjuntura adversa consolida-se não apenas pela negligência estatal na oferta de suporte público de assistência, mas também pela persistência de uma mentalidade patriarcal que naturaliza a sobrecarga materna e familiar.",
            desenvolvimento1 = "Em primeira análise, cumpre destacar que a omissão governamental em compartilhar a responsabilidade pelo cuidado fragiliza a autonomia socioeconômica das mulheres. Consoante o artigo 6º da Constituição Federal de 1988, a proteção à maternidade, à infância e à assistência aos desamparados são direitos sociais deveres do Estado. Todavia, conforme aponta Gilberto Dimenstein em 'Cidadãos de Papel', tais garantias permanecem restritas à dimensão formal quando faltam creches em tempo integral e centros públicos de acolhimento para idosos e pessoas com deficiência. Por conseguinte, milhões de brasileiras, sobretudo negras e periféricas, são forçadas a abandonar o mercado de trabalho formal para assumir solitariamente a rotina de cuidado.",
            desenvolvimento2 = "Outrossim, a herança cultural machista corrobora o apagamento simbólico e financeiro dessa atividade essencial. Sob a ótica do sociólogo francês Pierre Bourdieu, o conceito de 'Violência Simbólica' explica como estruturas de dominação são impostas de modo sutil até serem aceitas como naturais pela coletividade. Sob esse viés, a sociedade brasileira historicamente dissocia o afeto familiar do conceito de trabalho produtivo, negando reconhecimento salarial e previdenciário às cuidadoras. Dessa maneira, enquanto a riqueza nacional depende diretamente da manutenção da vida no lar, as mulheres sofrem com o esgotamento físico e psíquico invisibilizado.",
            conclusao = "Portanto, medidas estruturais são urgentes para reverter esse cenário de injustiça de gênero. Para tanto, cabe ao Ministério das Mulheres, em ação articulada com o Ministério da Educação e o Ministério do Desenvolvimento Social — órgãos federais responsáveis pela equidade de gênero e proteção social —, instituir a Política Nacional de Economia do Cuidado, por meio da ampliação de creches públicas em período integral, criação de centros-dia para idosos e campanhas educativas em rede nacional sobre corresponsabilidade parental, a fim de valorizar o trabalho de cuidado e superar a invisibilidade histórica denunciada por Silvia Federici.",
            repertoriosUsed = listOf(
                "Silvia Federici ('O Calibã e a Bruxa' — divisão sexual do trabalho)",
                "Artigo 6º da Constituição Federal de 1988",
                "Gilberto Dimenstein ('Cidadãos de Papel')",
                "Pierre Bourdieu ('Violência Simbólica')"
            ),
            whyItScored1000 = "Projeto de texto impecável com tese bipartida (Estado no D1 + Patriarcado no D2), uso cirúrgico de 4 repertórios legitimados, conectivos de alto nível ('Fora da literatura', 'Consoante', 'Todavia', 'Por conseguinte', 'Outrossim', 'Sob esse viés', 'Portanto') e proposta de intervenção com 5 elementos e circuito fechado retomando Federici."
        ),
        PastEnem1000Essay(
            id = "enem1000_2022",
            year = 2022,
            studentName = "Matheus Albuquerque",
            stateCode = "PE",
            officialTheme = "Desafios para a valorização de comunidades e povos tradicionais no Brasil",
            introducao = "Promulgada em 1988, a Constituição Cidadã assegura, em seus artigos 215 e 216, a proteção às manifestações das culturas populares, indígenas e afro-brasileiras como patrimônio formador da identidade nacional. Contudo, na realidade brasileira hodierna, observa-se um abismo entre a diretriz constitucional e a vivência concreta, haja vista os persistentes desafios para a valorização de comunidades e povos tradicionais no país. Esse quadro alarmante decorre tanto da cobiça econômica predatória sobre territórios ancestrais quanto do apagamento eurocêntrico que marginaliza esses saberes.",
            desenvolvimento1 = "Em primeira análise, é imperioso pontuar que o avanço ilegal de atividades extrativistas e a morosidade estatal na demarcação de terras ameaçam a sobrevivência física e cultural desses grupos. Sob a ótica do pensador indígena Ailton Krenak, na obra 'Ideias para Adiar o Fim do Mundo', a lógica utilitarista moderna trata a natureza apenas como mercadoria, ignorando os povos que mantêm relação de pertencimento sagrado com a floresta. Nesse sentido, a ineficiência na fiscalização ambiental expõe ribeirinhos, seringueiros, quilombolas e indígenas à violência no campo; por conseguinte, perde-se o maior escudo vivo de preservação da biodiversidade brasileira.",
            desenvolvimento2 = "Ademais, o desconhecimento social alimentado por uma visão histórica eurocêntrica perpetua o estigma contra as comunidades tradicionais. Segundo o antropólogo Darcy Ribeiro, em 'O Povo Brasileiro', a formação nacional foi marcada pela subjugação das matrizes originárias e africanas em favor de um ideal excludente de modernidade. Sob essa perspectiva, escolas e meios de comunicação ainda retratam povos tradicionais de maneira folclorizada ou anacrônica, ignorando sua ciência milenar de manejo sustentável. Desse modo, a ausência de reconhecimento simbólico legitima a indiferença coletiva diante da violação de seus direitos.",
            conclusao = "Portanto, urge que o Estado e a sociedade civil promovam a salvaguarda integral dessas populações. Para isso, cabe ao Ministério dos Povos Indígenas, em parceria com o Ministério do Meio Ambiente e o Ministério da Educação — pastas gestoras da proteção territorial e da formação cidadã —, acelerar a titulação e fiscalização de terras tradicionais e inserir o estudo vivo dos saberes ancestrais nos currículos escolares, mediante monitoramento por satélite do IBAMA e projetos pedagógicos com mestres tradicionais, a fim de efetivar a Constituição de 1988 e adiar o colapso socioambiental denunciado por Ailton Krenak.",
            repertoriosUsed = listOf(
                "Artigos 215 e 216 da Constituição Federal de 1988",
                "Ailton Krenak ('Ideias para Adiar o Fim do Mundo')",
                "Darcy Ribeiro ('O Povo Brasileiro')"
            ),
            whyItScored1000 = "Abordou integralmente a valorização cultural e territorial (indígenas, quilombolas, ribeirinhos), articulou Krenak e Darcy Ribeiro de forma altamente produtiva e construiu proposta dupla para resolver o D1 (fiscalização/demarcação) e o D2 (educação intercultural)."
        ),
        PastEnem1000Essay(
            id = "enem1000_2021",
            year = 2021,
            studentName = "Clara Mendonça",
            stateCode = "MG",
            officialTheme = "Invisibilidade e registro civil: garantia de acesso à cidadania no Brasil",
            introducao = "Na obra modernista 'Vidas Secas', de Graciliano Ramos, os filhos do casal Fabiano e Sinhá Vitória são chamados apenas de 'menino mais velho' e 'menino mais novo', evidenciando como a ausência de um nome próprio simboliza a destituição da dignidade humana. Fora da ficção literária, milhões de brasileiros enfrentam drama semelhante no século XXI, uma vez que a falta de registro civil perpetua a invisibilidade e bloqueia o acesso à cidadania no Brasil. Essa problemática consolida-se em razão da burocracia estatal excludente e da vulnerabilidade socioeducacional nas regiões periféricas e rurais.",
            desenvolvimento1 = "Em primeira análise, cabe ressaltar que o distanciamento físico e administrativo dos cartórios em relação às populações mais pobres impede a efetivação do direito ao documento básico. Sob a ótica do geógrafo Milton Santos, o fenômeno das 'Cidadanias Mutiladas' ocorre quando o Estado distribui infraestrutura e serviços essenciais de forma desigual pelo território. Nesse contexto, moradores de zonas rurais remotas, ribeirinhos e pessoas em situação de rua esbarram na falta de postos de identificação civil em suas comunidades. Por conseguinte, sem a Certidão de Nascimento, esses indivíduos tornam-se fantasmas estatísticos, privados de matrícula escolar, atendimento no SUS e programas de transferência de renda.",
            desenvolvimento2 = "Outrossim, a desinformação decorrente da exclusão educacional intergeracional agrava o sub-registro no país. Conforme o jornalista Gilberto Dimenstein, na obra 'Cidadãos de Papel', embora a legislação brasileira assegure a gratuidade da primeira via do registro civil (Lei nº 9.534/1997), o desconhecimento desse direito impede sua fruição prática. Sob esse viés, famílias em extrema pobreza frequentemente deixam de registrar seus filhos no prazo legal por receio de custos financeiros ou por desconhecerem a importância jurídica da documentação. Dessa maneira, perpetua-se um ciclo hereditário de marginalização civil.",
            conclusao = "Portanto, torna-se imperativa a atuação estatal para erradicar o sub-registro no território nacional. Para tanto, cabe ao Ministério dos Direitos Humanos e da Cidadania, em cooperação com o Conselho Nacional de Justiça (CNJ) — órgãos responsáveis pela garantia de direitos civis e regulamentação cartorária —, expandir unidades móveis de registro civil em maternidades públicas, periferias e áreas rurais, além de promover campanhas informativas sobre a gratuidade documental, por meio de mutirões nacionais e orientação de agentes comunitários de saúde, com o intuito de assegurar plena cidadania a todos e impedir que brasileiros vivam a anulação identitária retratada em 'Vidas Secas'.",
            repertoriosUsed = listOf(
                "Graciliano Ramos ('Vidas Secas' — meninos sem nome)",
                "Milton Santos ('Cidadanias Mutiladas')",
                "Gilberto Dimenstein ('Cidadãos de Papel')",
                "Lei Federal nº 9.534/1997 (Gratuidade do Registro Civil)"
            ),
            whyItScored1000 = "Conexão literária brilhante com 'Vidas Secas' na abertura e no fechamento, domínio conceitual de Milton Santos para explicar a desigualdade territorial de acesso aos cartórios e precisão vocabular exemplar."
        ),
        PastEnem1000Essay(
            id = "enem1000_2020",
            year = 2020,
            studentName = "Rafael Tavares",
            stateCode = "RJ",
            officialTheme = "O estigma associado às doenças mentais na sociedade brasileira",
            introducao = "No filme 'Coringa', o protagonista Arthur Fleck sofre com um transtorno psiquiátrico severo, mas encontra apenas hostilidade social e cortes no serviço público de assistência médica, o que culmina em sua completa exclusão. Fora das telas do cinema, a realidade brasileira apresenta contornos igualmente preocupantes, haja vista a persistência do estigma associado às doenças mentais no país. Tal conjuntura deletéria deve-se tanto à desinformação histórica alimentada pela lógica da produtividade extrema quanto à precarização da rede pública de saúde mental.",
            desenvolvimento1 = "Em primeira análise, cumpre destacar que a sociedade contemporânea frequentemente associa o sofrimento psíquico à fraqueza moral ou à incapacidade produtiva. Sob a ótica do filósofo sul-coreano Byung-Chul Han, na obra 'Sociedade do Cansaço', o século XXI é marcado por uma cobrança incessante por desempenho, na qual indivíduos com depressão ou ansiedade são rotulados como fracassados. Nesse viés, no Brasil, a banalização de termos clínicos nas redes sociais e a falta de educação emocional nas escolas reforçam tabus que levam o paciente a ocultar seus sintomas. Consequentemente, o medo do julgamento social impede a busca por diagnóstico precoce.",
            desenvolvimento2 = "Ademais, a insuficiência de investimentos estatais na atenção psicossocial perpetua a marginalização dos cidadãos acometidos por transtornos mentais. Embora a Organização Mundial da Saúde (OMS) defina a saúde como o completo bem-estar físico, mental e social, o Sistema Único de Saúde (SUS) ainda enfrenta grave déficit de Centros de Atenção Psicossocial (CAPS) em inúmeros municípios brasileiros. Desse modo, sem acompanhamento multidisciplinar gratuito e contínuo, a população de baixa renda permanece desassistida, perpetuando a herança excludente dos antigos manicômios.",
            conclusao = "Portanto, ações integradas são indispensáveis para desconstruir o preconceito e democratizar o cuidado psíquico. Para isso, cabe ao Ministério da Saúde, em parceria com o Ministério da Educação — órgãos gestores do bem-estar sanitário e da formação estudantil —, ampliar a rede de atendimento dos CAPS em todo o território nacional e instituir projetos de letramento em saúde mental nas escolas e na mídia, mediante verbas vinculadas da União e rodas de conversa com psicólogos, a fim de extinguir o estigma social e evitar que cidadãos brasileiros sofram o abandono retratado na obra 'Coringa'.",
            repertoriosUsed = listOf(
                "Filme 'Coringa' (Todd Phillips, 2019)",
                "Byung-Chul Han ('Sociedade do Cansaço')",
                "Conceito de Saúde da Organização Mundial da Saúde (OMS)"
            ),
            whyItScored1000 = "Demonstrou como articular cultura pop (filme Coringa), filosofia contemporânea (Byung-Chul Han) e repertório institucional (OMS/SUS/CAPS) com clareza sintática e sem nenhum desvio gramatical."
        ),
        PastEnem1000Essay(
            id = "enem1000_2019",
            year = 2019,
            studentName = "Letícia Nogueira",
            stateCode = "CE",
            officialTheme = "Democratização do acesso ao cinema no Brasil",
            introducao = "No filme nacional 'Cine Holliúdy', o protagonista Francisgleydisson luta para manter viva uma sala de cinema popular no interior do Ceará, revelando o poder transformador da sétima arte para comunidades afastadas das metrópoles. Fora da ficção, entretanto, o acesso ao cinema no Brasil contemporâneo permanece profundamente elitizado e desigual. Essa exclusão cultural decorre não apenas da concentração geográfica das salas de exibição nos grandes centros comerciais, mas também da insuficiência de políticas públicas de incentivo à fruição audiovisual nas periferias.",
            desenvolvimento1 = "Em primeira análise, cabe pontuar que a lógica mercadológica do espaço urbano restringe o cinema às classes de maior poder aquisitivo. Sob a perspectiva do geógrafo crítico David Harvey, em 'Cidades Rebeldes', o espaço urbano capitalista é frequentemente moldado para o consumo de elites, segregando populações periféricas do direito à cidade e ao lazer. Nesse sentido, a transferência das antigas salas de rua para shoppings centers com ingressos de alto custo afasta trabalhadores de baixa renda e moradores de pequenos municípios. Por conseguinte, o direito constitucional à cultura, previsto no artigo 215 da Carta Magna, torna-se um privilégio restrito.",
            desenvolvimento2 = "Outrossim, a carência de programas governamentais de difusão cinematográfica nas escolas públicas fragiliza a formação de plateias críticas. Conforme a Lei nº 13.006/2014, as instituições de ensino básico deveriam exibir pelo menos duas horas mensais de cinema nacional; todavia, a falta de infraestrutura multimídia e de fiscalização estatal impede o cumprimento efetivo dessa norma. Dessa maneira, milhões de estudantes crescem sem contato com a produção audiovisual brasileira, perdendo uma ferramenta essencial de empatia e reflexão cidadã.",
            conclusao = "Portanto, urge democratizar o alcance da sétima arte em todo o território nacional. Para tanto, cabe ao Ministério da Cultura, em ação conjunta com a Agência Nacional do Cinema (ANCINE) e o Ministério da Educação — órgãos responsáveis pelo fomento audiovisual e pela gestão escolar —, criar projetos de cinema itinerante em municípios desassistidos e equipar escolas públicas para o cumprimento da Lei 13.006/2014, por meio de editais do Fundo Setorial do Audiovisual e parcerias com prefeituras, a fim de universalizar o acesso à cultura e multiplicar experiências democráticas como a retratada em 'Cine Holliúdy'.",
            repertoriosUsed = listOf(
                "Filme brasileiro 'Cine Holliúdy' (Halder Gomes)",
                "David Harvey ('Cidades Rebeldes' — segregação socioespacial)",
                "Artigo 215 da Constituição Federal de 1988",
                "Lei Federal nº 13.006/2014 (Exibição de cinema nacional nas escolas)"
            ),
            whyItScored1000 = "Articulou repertório genuinamente brasileiro ('Cine Holliúdy' e Lei 13.006/14) com geografia urbana (David Harvey), abordando tanto o recorte espacial quanto o socioeconômico."
        )
    )

    // =========================================================================
    // 5. LISTA DE TEMAS DE ENEMs PASSADOS COM SEUS TEXTOS MOTIVADORES
    // =========================================================================
    val pastEnemOfficialThemes = listOf(
        PastEnemOfficialTheme(
            year = 2024,
            officialThemeTitle = "Desafios para a valorização da herança africana no Brasil",
            axis = "Cultura & Direitos Humanos",
            textoI = "TEXTO I: A palavra 'herança' remete ao legado cultural, histórico, linguístico, religioso, artístico e tecnológico transmitido pelos povos africanos que formaram a base demográfica e civilizatória do Brasil.",
            textoII = "TEXTO II: Embora a Lei nº 10.639/2003 torne obrigatório o ensino da História e Cultura Afro-Brasileira nas escolas públicas e privadas, pesquisas apontam que mais de 60% das redes municipais ainda enfrentam lacunas na formação continuada de professores e na aplicação efetiva da lei durante todo o ano letivo.",
            textoIII = "TEXTO III: A gastronomia, o vocabulário cotidiano, a música, a capoeira e os saberes agrícolas afro-brasileiros constituem pilares da identidade nacional, mas frequentemente sofrem apagamento simbólico, intolerância religiosa ou redução folclórica.",
            textoIV = "TEXTO IV: Fotografias e gravuras de sítios arqueológicos como o Cais do Valongo (Patrimônio Mundial da UNESCO no Rio de Janeiro) reforçam a necessidade de políticas de memória histórica e educação antirracista.",
            suggestedArguments = "A1 (Estado/Educação): Implementação superficial da Lei 10.639/2003 nas escolas e baixo investimento em museus e patrimônios afro-brasileiros.\nA2 (Sociedade): Racismo estrutural e herança colonial eurocêntrica que invisibiliza ou estigmatiza matrizes africanas.",
            recommendedRepertoires = "• Djamila Ribeiro ('Pequeno Manual Antirracista') • Silvio Almeida ('Racismo Estrutural') • Lei 10.639/2003 • Darcy Ribeiro ('O Povo Brasileiro') • Art. 215 da CF/88."
        ),
        PastEnemOfficialTheme(
            year = 2023,
            officialThemeTitle = "Desafios para o enfrentamento da invisibilidade do trabalho de cuidado realizado pela mulher no Brasil",
            axis = "Inclusão Social & Trabalho",
            textoI = "TEXTO I: O trabalho de cuidado engloba todas as atividades cotidianas essenciais para a manutenção da vida: cuidar de crianças, idosos, pessoas com deficiência, cozinhar, limpar e gerir o ambiente doméstico, sendo realizado majoritariamente por mulheres sem remuneração.",
            textoII = "TEXTO II: Dados da PNAD Contínua (IBGE) revelam que as mulheres brasileiras dedicam, em média, 21,3 horas semanais aos afazeres domésticos e cuidado de pessoas, quase o dobro do tempo gasto pelos homens (11,7 horas).",
            textoIII = "TEXTO III: O trabalho de cuidado não remunerado sustenta toda a economia produtiva formal; contudo, por não ser contabilizado no PIB nem reconhecido socialmente, penaliza as mulheres com a pobreza de tempo e barreiras na carreira profissional.",
            textoIV = "TEXTO IV: Capa de revista ilustrando a sobrecarga mental e física invisível que recai sobre mães e cuidadoras na estrutura familiar tradicional.",
            suggestedArguments = "A1 (Estado): Escassez de creches públicas em tempo integral e centros de acolhimento para idosos/PcD.\nA2 (Sociedade): Cultura patriarcal e divisão sexual do trabalho que naturaliza o cuidado como obrigação exclusivamente feminina.",
            recommendedRepertoires = "• Silvia Federici ('O Calibã e a Bruxa') • Simone de Beauvoir ('O Segundo Sexo') • Pierre Bourdieu ('A Dominação Masculina') • Art. 6º da CF/88."
        ),
        PastEnemOfficialTheme(
            year = 2022,
            officialThemeTitle = "Desafios para a valorização de comunidades e povos tradicionais no Brasil",
            axis = "Meio Ambiente & Cultura",
            textoI = "TEXTO I: O Decreto nº 6.040/2007 institui a Política Nacional de Desenvolvimento Sustentável dos Povos e Comunidades Tradicionais, reconhecendo indígenas, quilombolas, ribeirinhos, seringueiros, quebradeiras de coco babaçu, caiçaras, ciganos e pescadores artesanais.",
            textoII = "TEXTO II: Mapeamentos do IBGE demonstram a presença de localidades indígenas e quilombolas em todas as macrorregiões brasileiras, evidenciando sua importância para a conservação dos biomas nacionais.",
            textoIII = "TEXTO III: Declaração da Cúpula do Clima (COP-26) destaca que os povos originários e tradicionais detêm conhecimentos milenares indispensáveis para frear o aquecimento global e preservar a biodiversidade.",
            textoIV = "TEXTO IV: Carta da Amazônia assinada por entidades de seringueiros, ribeirinhos e quilombolas denunciando invasões territoriais, desmatamento e falta de consulta prévia sobre grandes obras.",
            suggestedArguments = "A1 (Estado): Ineficiência na proteção territorial e demarcação de terras diante de interesses econômicos predatórios.\nA2 (Sociedade): Visão etnocêntrica e apagamento escolar dos saberes ancestrais.",
            recommendedRepertoires = "• Ailton Krenak ('Ideias para Adiar o Fim do Mundo') • Artigos 215 e 231 da CF/88 • Darcy Ribeiro ('O Povo Brasileiro')."
        ),
        PastEnemOfficialTheme(
            year = 2021,
            officialThemeTitle = "Invisibilidade e registro civil: garantia de acesso à cidadania no Brasil",
            axis = "Direitos Humanos & Cidadania",
            textoI = "TEXTO I: A Certidão de Nascimento é o primeiro documento jurídico que oficializa a existência de um indivíduo perante o Estado. Sem ela, o cidadão fica impedido de obter CPF, RG, título de eleitor, matrícula escolar e benefícios sociais.",
            textoII = "TEXTO II: Dados do IBGE e do Ministério dos Direitos Humanos apontam que cerca de 3 milhões de brasileiros não possuem registro civil de nascimento, afetando sobretudo populações rurais, ribeirinhas e periféricas.",
            textoIII = "TEXTO III: A Lei Federal nº 9.534/1997 garante a gratuidade da primeira via da certidão de nascimento e de óbito para todos os cidadãos brasileiros, independentemente da renda.",
            textoIV = "TEXTO IV: Peça publicitária da Defensoria Pública com a frase: 'Quem não é registrado não é visto pelo Estado e não existe nas políticas públicas'.",
            suggestedArguments = "A1 (Estado): Desigualdade territorial na distribuição de cartórios e postos de identificação civil.\nA2 (Sociedade/Educação): Desinformação sobre a gratuidade legal e vulnerabilidade socioeconômica.",
            recommendedRepertoires = "• Graciliano Ramos ('Vidas Secas' — meninos sem nome) • Gilberto Dimenstein ('Cidadãos de Papel') • Milton Santos ('Cidadanias Mutiladas')."
        ),
        PastEnemOfficialTheme(
            year = 2020,
            officialThemeTitle = "O estigma associado às doenças mentais na sociedade brasileira",
            axis = "Saúde & Bem-Estar",
            textoI = "TEXTO I: Estigma social consiste na marca de desaprovação ou preconceito atribuída a um grupo de indivíduos, gerando discriminação, isolamento e vergonha em buscar auxílio médico.",
            textoII = "TEXTO II: Segundo a Organização Mundial da Saúde (OMS), o Brasil é o país com maior índice de transtornos de ansiedade na América Latina e um dos líderes em casos de depressão, que juntos afastam milhares de trabalhadores anualmente.",
            textoIII = "TEXTO III: A idealização de uma vida perfeita nas redes sociais e a cobrança por produtividade constante fazem com que o adoecimento psíquico seja visto erroneamente como 'frescura' ou falta de força de vontade.",
            textoIV = "TEXTO IV: Campanha educativa destacando que transtornos mentais possuem bases neurobiológicas e psicossociais, exigindo tratamento médico e psicológico sem preconceitos.",
            suggestedArguments = "A1 (Sociedade): Cultura do desempenho e desinformação que rotula o sofrimento psíquico como fraqueza.\nA2 (Estado): Subfinanciamento da Rede de Atenção Psicossocial (CAPS/SUS).",
            recommendedRepertoires = "• Byung-Chul Han ('Sociedade do Cansaço') • Filme 'Coringa' • Lei 10.216/2001 (Reforma Psiquiátrica) • Conceito de Saúde da OMS."
        ),
        PastEnemOfficialTheme(
            year = 2019,
            officialThemeTitle = "Democratização do acesso ao cinema no Brasil",
            axis = "Cultura & Questão Urbana",
            textoI = "TEXTO I: A história do cinema mostra sua transição de feiras populares para grandes salas urbanas e, nas últimas décadas, sua concentração em complexos de shoppings centers.",
            textoII = "TEXTO II: Edgar Morin analisa o cinema como uma máquina de empatia e projeção simbólica fundamental para a formação cultural do indivíduo contemporâneo.",
            textoIII = "TEXTO III: Infográfico da ANCINE revela que mais de 90% dos municípios brasileiros não possuem sequer uma sala de cinema, e que apenas 17% da população frequenta salas de exibição regularmente.",
            textoIV = "TEXTO IV: Programa 'Cinema Perto de Você', voltado à descentralização de salas de exibição em cidades de médio porte e periferias.",
            suggestedArguments = "A1 (Espaço Urbano/Economia): Elitização tarifária e concentração geográfica das salas em bairros nobres e capitais.\nA2 (Estado/Educação): Baixo incentivo estatal a cinemas públicos itinerantes e descumprimento da Lei 13.006/2014 nas escolas.",
            recommendedRepertoires = "• Art. 215 da CF/88 • Filme 'Cine Holliúdy' • David Harvey ('Direito à Cidade') • Lei 13.006/2014."
        ),
        PastEnemOfficialTheme(
            year = 2018,
            officialThemeTitle = "Manipulação do comportamento do usuário pelo controle de dados na internet",
            axis = "Tecnologia & Redes Sociais",
            textoI = "TEXTO I: Os algoritmos de recomendação filtram músicas, notícias e produtos com base nos cliques anteriores do usuário, criando uma falsa sensação de escolha autônoma.",
            textoII = "TEXTO II: A coleta massiva de dados pessoais (Big Data) permite que corporações digitais prevejam comportamentos emocionais, direcionem propagandas políticas e influenciem decisões de compra.",
            textoIII = "TEXTO III: O filósofo e sociólogo Habermas alerta que a fragmentação da esfera pública em 'bolhas digitais' (câmaras de eco) empobrece o debate democrático e alimenta a polarização.",
            textoIV = "TEXTO IV: Tirinha humorística mostrando um usuário espantado ao perceber que a internet conhece todos os seus hábitos privados.",
            suggestedArguments = "A1 (Tecnologia/Mercado): Capitalismo de vigilância e opacidade dos algoritmos que criam bolhas ideológicas.\nA2 (Educação): Déficit de letramento digital crítico da população brasileira.",
            recommendedRepertoires = "• George Orwell ('1984') • Shoshana Zuboff ('A Era do Capitalismo de Vigilância') • Documentário 'O Dilema das Redes' • Marco Civil da Internet (Lei 12.965/2014)."
        ),
        PastEnemOfficialTheme(
            year = 2017,
            officialThemeTitle = "Desafios para a formação educacional de surdos no Brasil",
            axis = "Educação & Inclusão Social",
            textoI = "TEXTO I: A Lei nº 10.436/2002 reconhece a Língua Brasileira de Sinais (Libras) como meio legal de comunicação e expressão da comunidade surda no Brasil, sendo o português escrito sua segunda língua.",
            textoII = "TEXTO II: Gráfico do INEP demonstra queda nas matrículas em classes exclusivas e aumento na rede regular, mas aponta grave carência de intérpretes de Libras e salas de recursos multifuncionais.",
            textoIII = "TEXTO III: Cartaz publicitário evidenciando a dificuldade de um jovem surdo em ingressar no ensino superior e no mercado de trabalho por barreiras comunicacionais.",
            textoIV = "TEXTO IV: Relato histórico sobre o primeiro estudante surdo no Brasil Imperial e a fundação do INES (Instituto Nacional de Educação de Surdos).",
            suggestedArguments = "A1 (Estado): Escassez de escolas bilíngues (Libras/Português) e falta de capacitação docente na rede pública.\nA2 (Sociedade): Capacitismo estrutural e preconceito linguístico das famílias e instituições.",
            recommendedRepertoires = "• Estatuto da Pessoa com Deficiência (Lei 13.146/2015) • Lei de Libras (Lei 10.436/2002) • Art. 205 e 208 da CF/88 • Axel Honneth ('Luta por Reconhecimento')."
        )
    )

    // =========================================================================
    // 6. BANCO DE REPERTÓRIOS SOCIOCULTURAIS LEGITIMADOS (POR ÁREA E EIXO)
    // =========================================================================
    val socioculturalRepertoireBank = listOf(
        // ================= CONSTITUIÇÃO FEDERAL DE 1988 (ARTIGOS ESPECÍFICOS) =================
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88",
            title = "CF/88 • Constituição Federal de 1988 (Carta Magna & Constituição Cidadã)",
            authorOrSource = "Assembleia Nacional Constituinte (Promulgada em 5 de outubro de 1988)",
            areaOfKnowledge = "Direito Constitucional & Cidadania",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Lei fundamental e suprema do Brasil. Consagra a Dignidade da Pessoa Humana (Art. 1º, III), a Igualdade perante a lei (Art. 5º) e os Direitos Sociais no Art. 6º (educação, saúde, alimentação, trabalho, moradia, transporte, lazer, segurança, previdência social e proteção à maternidade e à infância), além de deveres específicos na Educação (Art. 205), Cultura (Art. 215), Meio Ambiente (Art. 225) e Criança/Adolescente (Art. 227).",
            howToUseInParagraph = "Repertório coringa nº 1 do ENEM: use na abertura da Introdução para apresentar o dever constitucional e, logo em seguida, empregue 'Contudo,' ou 'Todavia,' para denunciar o hiato entre a norma jurídica e a realidade prática brasileira.",
            compatibleThemes = listOf("Educação", "Saúde & Bem-Estar", "Direitos Humanos & Minorias", "Cidadania", "Meio Ambiente & Clima", "Cultura & Identidade", "Trabalho & Previdência", "Segurança Pública"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art1",
            title = "CF/88 • Art. 1º, III: Princípio da Dignidade da Pessoa Humana",
            authorOrSource = "Constituição Federal de 1988 (Fundamento da República)",
            areaOfKnowledge = "Direito Constitucional & Teoria dos Direitos Fundamentais",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Espinha dorsal de toda a ordem jurídica brasileira. Determina que o ser humano não pode ser instrumentalizado, descartado ou reduzido a objeto pelo Estado ou pelo mercado, sendo titular inviolável de respeito, subsistência digna e integridade.",
            howToUseInParagraph = "Use na Introdução ou D1 para combater a desumanização: moradores de rua, trabalho análogo à escravidão, crise prisional, violência obstétrica, desnutrição e abandono social.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Trabalho & Previdência", "Saúde & Bem-Estar", "Segurança Pública", "Inclusão Social & PcD"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art3",
            title = "CF/88 • Art. 3º: Objetivos Fundamentais da República",
            authorOrSource = "Constituição Federal de 1988 (Metas do Estado Brasileiro)",
            areaOfKnowledge = "Direito Constitucional & Política Pública",
            primaryAxis = "Cidadania",
            coreConcept = "Estabelece como metas inegociáveis do Brasil: I - construir uma sociedade livre, justa e solidária; II - erradicar a pobreza e a marginalização; III - reduzir as desigualdades sociais e regionais; IV - promover o bem de todos, sem preconceitos de origem, raça, sexo, cor, idade e quaisquer outras formas de discriminação.",
            howToUseInParagraph = "Excelente para confrontar no D1 quando demonstrar a inércia do Poder Público diante da pobreza extrema, desigualdade regional Norte-Nordeste ou discriminação contra minorias.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Cultura & Identidade", "Educação", "Economia & Consumo", "Saúde & Bem-Estar"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art5",
            title = "CF/88 • Art. 5º: Princípio da Isonomia e Liberdades Fundamentais",
            authorOrSource = "Constituição Federal de 1988 (Direitos Individuais e Coletivos)",
            areaOfKnowledge = "Direito Constitucional & Garantias Cívicas",
            primaryAxis = "Justiça, Leis & Cidadania Digital",
            coreConcept = "Garante que 'todos são iguais perante a lei, sem distinção de qualquer natureza', assegurando a inviolabilidade do direito à vida, à liberdade, à igualdade, à segurança e à propriedade, além da liberdade de expressão, crença, livre manifestação e privacidade.",
            howToUseInParagraph = "Indispensável em temas sobre liberdade de expressão versus discurso de ódio nas redes, intolerância religiosa, direito ao registro civil, invasão de privacidade de dados e violência arbitrária.",
            compatibleThemes = listOf("Justiça, Leis & Cidadania Digital", "Redes Sociais & Mídia", "Cultura & Identidade", "Segurança Pública", "Tecnologia & IA"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art6",
            title = "CF/88 • Art. 6º: Direitos Sociais Fundamentais",
            authorOrSource = "Constituição Federal de 1988 (Cláusula Pétrea de Proteção Social)",
            areaOfKnowledge = "Direito Constitucional & Cidadania",
            primaryAxis = "Cidadania",
            coreConcept = "Consagra expressamente: 'São direitos sociais a educação, a saúde, a alimentação, o trabalho, a moradia, o transporte, o lazer, a segurança, a previdência social, a proteção à maternidade e à infância, e a assistência aos desamparados'.",
            howToUseInParagraph = "Abertura coringa para quase 90% dos temas do ENEM. Nomeie o direito social do tema no início da redação e mostre que sua precarização impede a vivência plena da cidadania.",
            compatibleThemes = listOf("Educação", "Saúde & Bem-Estar", "Trabalho & Previdência", "Alimentação & Segurança Alimentar", "Mobilidade & Questão Urbana", "Segurança Pública"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art196",
            title = "CF/88 • Art. 196: A Saúde como Direito Universal e Dever do Estado",
            authorOrSource = "Constituição Federal de 1988 (Base do SUS)",
            areaOfKnowledge = "Direito Sanitário & Saúde Coletiva",
            primaryAxis = "Saúde & Bem-Estar",
            coreConcept = "Postula que a saúde é 'direito de todos e dever do Estado', garantido mediante políticas sociais e econômicas que visem à redução do risco de doenças e ao acesso universal e igualitário às ações e serviços de promoção, proteção e recuperação.",
            howToUseInParagraph = "Essencial para temas sobre SUS, saúde mental dos jovens, doação de órgãos, vacinação, dependência química, saneamento básico e prevenção a epidemias.",
            compatibleThemes = listOf("Saúde & Bem-Estar", "Infância, Juventude & Envelhecimento", "Ciência & Bioética", "Alimentação & Segurança Alimentar"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art205",
            title = "CF/88 • Art. 205 e 206: A Educação como Direito de Todos e Valorização Docente",
            authorOrSource = "Constituição Federal de 1988 (Capítulo da Educação)",
            areaOfKnowledge = "Direito Educacional",
            primaryAxis = "Educação",
            coreConcept = "O Art. 205 define a educação como 'direito de todos e dever do Estado e da família', visando ao pleno desenvolvimento da pessoa, seu preparo para o exercício da cidadania e sua qualificação para o trabalho. O Art. 206 preconiza igualdade de condições para acesso e permanência, pluralismo pedagógico e valorização dos profissionais da educação.",
            howToUseInParagraph = "Base obrigatória para qualquer tema educacional: evasão escolar, valorização dos professores, ensino em tempo integral, letramento digital e infraestrutura de escolas públicas.",
            compatibleThemes = listOf("Educação", "Inclusão Social & PcD", "Infância, Juventude & Envelhecimento", "Tecnologia & IA", "Trabalho & Previdência"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art215",
            title = "CF/88 • Art. 215 e 216: Proteção ao Patrimônio Cultural e Diversidade",
            authorOrSource = "Constituição Federal de 1988 (Direitos Culturais)",
            areaOfKnowledge = "Direito Cultural & Antropologia",
            primaryAxis = "Cultura & Identidade",
            coreConcept = "Determina que o Estado garantirá a todos o pleno exercício dos direitos culturais e acesso às fontes da cultura nacional, apoiando e valorizando a difusão das manifestações culturais populares, indígenas, afro-brasileiras e de outros grupos participantes do processo civilizatório nacional.",
            howToUseInParagraph = "Excelente para temas sobre democratização do cinema e museus, preservação de tradições orais, memória nacional, línguas indígenas e combate à intolerância contra religiões de matriz africana.",
            compatibleThemes = listOf("Cultura & Identidade", "Direitos Humanos & Minorias", "Educação", "Comunicação & Artes"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art225",
            title = "CF/88 • Art. 225: Meio Ambiente Ecologicamente Equilibrado",
            authorOrSource = "Constituição Federal de 1988 (Dever Socioambiental)",
            areaOfKnowledge = "Direito Ambiental & Ecologia",
            primaryAxis = "Meio Ambiente & Clima",
            coreConcept = "Consagra que 'todos têm direito ao meio ambiente ecologicamente equilibrado, bem de uso comum do povo e essencial à sadia qualidade de vida, impondo-se ao Poder Público e à coletividade o dever de defendê-lo e preservá-lo para as presentes e futuras gerações'.",
            howToUseInParagraph = "Repertório indispensável para temas sobre crise climática, desmatamento na Amazônia e Cerrado, poluição de rios e oceanos, racismo ambiental, queimadas e matriz energética sustentável.",
            compatibleThemes = listOf("Meio Ambiente & Clima", "Ciência & Bioética", "Alimentação & Segurança Alimentar", "Economia & Consumo"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art227",
            title = "CF/88 • Art. 227: Prioridade Absoluta da Criança, Adolescente e Jovem",
            authorOrSource = "Constituição Federal de 1988 (Doutrina da Proteção Integral)",
            areaOfKnowledge = "Direito da Infância e Juventude",
            primaryAxis = "Infância, Juventude & Envelhecimento",
            coreConcept = "Fixa o dever inarredável da família, da sociedade e do Estado de assegurar à criança, ao adolescente e ao jovem, com absoluta prioridade, os direitos à vida, saúde, alimentação, educação, lazer, cultura, dignidade e respeito, salvaguardando-os de toda negligência, violência, exploração e crueldade.",
            howToUseInParagraph = "Aplique na Introdução, D1 ou D2 em temas de trabalho infantil, evasão escolar na juventude, saúde mental de adolescentes, vício em apostas online (bets), telas e abuso infantojuvenil.",
            compatibleThemes = listOf("Infância, Juventude & Envelhecimento", "Educação", "Redes Sociais & Mídia", "Saúde & Bem-Estar", "Segurança Pública"),
            groupCode = "CF88"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_cf88_art231",
            title = "CF/88 • Art. 231: Direitos Originários dos Povos Indígenas",
            authorOrSource = "Constituição Federal de 1988 (Demarcação e Identidade Indígena)",
            areaOfKnowledge = "Direito Indigenista & Antropologia",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Reconhece aos indígenas sua organização social, costumes, línguas, crenças e tradições, e os direitos originários sobre as terras que tradicionalmente ocupam, competindo à União demarcá-las, proteger e fazer respeitar todos os seus bens e modos de viver.",
            howToUseInParagraph = "Perfeito para temas sobre demarcação de terras indígenas, proteção da Amazônia, valorização das culturas ancestrais, preservação da biodiversidade e combate ao garimpo ilegal.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Meio Ambiente & Clima", "Cultura & Identidade", "Segurança Pública"),
            groupCode = "CF88"
        ),

        // ================= LDB — LEI DE DIRETRIZES E BASES (LEI Nº 9.394/1996) =================
        SocioculturalRepertoireItem(
            id = "rep_soc_ldb",
            title = "LDB • Lei de Diretrizes e Bases da Educação Nacional (Visão Geral)",
            authorOrSource = "Lei Federal nº 9.394/1996 (Lei Darcy Ribeiro)",
            areaOfKnowledge = "Legislação Educacional & Pedagogia",
            primaryAxis = "Educação",
            coreConcept = "Principal diploma legal da educação escolar no Brasil. Estrutura a Educação Básica e Superior, assegurando padrão unitário de qualidade, gratuidade do ensino público e a indissociabilidade entre teoria, prática e vida cidadã.",
            howToUseInParagraph = "Indispensável no D1, D2 ou Proposta de Intervenção em temas sobre educação pública, formação de professores, ensino integral e democratização do conhecimento.",
            compatibleThemes = listOf("Educação", "Inclusão Social & PcD", "Infância, Juventude & Envelhecimento", "Cultura & Identidade", "Tecnologia & IA", "Trabalho & Previdência"),
            groupCode = "LDB"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_ldb_art2",
            title = "LDB • Art. 2º: A Finalidade Tríplice da Educação Nacional",
            authorOrSource = "LDB — Lei nº 9.394/1996 (Princípio Teleológico)",
            areaOfKnowledge = "Legislação Educacional",
            primaryAxis = "Educação",
            coreConcept = "Determina que a educação tem três finalidades essenciais e integradas: 1) o pleno desenvolvimento do educando como pessoa humana; 2) seu preparo para o exercício consciente da cidadania; 3) sua qualificação para o mundo do trabalho.",
            howToUseInParagraph = "Use no D2 para argumentar que uma escola que foca apenas em testes conteudistas e ignora a formação ética e o pensamento crítico falha em cumprir a finalidade do Art. 2º da LDB.",
            compatibleThemes = listOf("Educação", "Cidadania", "Trabalho & Previdência", "Tecnologia & IA"),
            groupCode = "LDB"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_ldb_art3",
            title = "LDB • Art. 3º: Igualdade de Acesso, Pluralismo e Valorização Docente",
            authorOrSource = "LDB — Lei nº 9.394/1996 (Princípios do Ensino)",
            areaOfKnowledge = "Legislação Educacional",
            primaryAxis = "Educação",
            coreConcept = "Estipula princípios inegociáveis: igualdade de condições para o acesso e permanência na escola; liberdade de aprender, ensinar e pesquisar; pluralismo de ideias e de concepções pedagógicas; valorização dos profissionais da educação e gestão democrática.",
            howToUseInParagraph = "Excelente para denunciar a precarização salarial dos professores, a falta de laboratórios e livros didáticos ou tentativas de censura ideológica e cerceamento do pensamento livre nas escolas.",
            compatibleThemes = listOf("Educação", "Trabalho & Previdência", "Cultura & Identidade", "Política & Democracia"),
            groupCode = "LDB"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_ldb_art4",
            title = "LDB • Art. 4º: Dever do Estado e Atendimento Educacional Especializado (AEE)",
            authorOrSource = "LDB — Lei nº 9.394/1996 (Garantias do Ensino Público)",
            areaOfKnowledge = "Educação Inclusiva & Direito Educacional",
            primaryAxis = "Inclusão Social & PcD",
            coreConcept = "Obriga o Estado a oferecer educação básica gratuita dos 4 aos 17 anos e atendimento educacional especializado gratuito aos estudantes com deficiência, transtornos globais do desenvolvimento e altas habilidades, preferencialmente na rede regular.",
            howToUseInParagraph = "Fundamental para temas de inclusão de autistas (TEA), surdos, cegos e pessoas com deficiência intelectual nas salas de aula regulares e falta de mediadores capacitados.",
            compatibleThemes = listOf("Inclusão Social & PcD", "Educação", "Infância, Juventude & Envelhecimento", "Saúde & Bem-Estar"),
            groupCode = "LDB"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_ldb_art26a",
            title = "LDB • Art. 26-A: Ensino Obrigatório de História e Cultura Afro-Brasileira e Indígena",
            authorOrSource = "LDB — Leis nº 10.639/2003 e 11.645/2008",
            areaOfKnowledge = "Educação das Relações Étnico-Raciais",
            primaryAxis = "Cultura & Identidade",
            coreConcept = "Torna obrigatório o ensino da história e cultura afro-brasileira e dos povos indígenas no ensino fundamental e médio, resgatando suas contribuições fundamentais nas áreas social, econômica e política pertinentes à História do Brasil.",
            howToUseInParagraph = "Perfeito para justificar no D1 ou D2 a necessidade de erradicar o racismo estrutural nas escolas e combater o epistemicídio que apagou narrativas ancestrais dos currículos.",
            compatibleThemes = listOf("Cultura & Identidade", "Direitos Humanos & Minorias", "Educação", "História & Memória"),
            groupCode = "LDB"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_ldb_art35a",
            title = "LDB • Art. 35-A: Letramento Digital Crítico e Formação Integral no Ensino Médio",
            authorOrSource = "LDB — Diretrizes Curriculares Nacionais do Ensino Médio",
            areaOfKnowledge = "Tecnologia Educacional & BNCC",
            primaryAxis = "Tecnologia & IA",
            coreConcept = "Orienta a Base Nacional Comum Curricular (BNCC) para que o ensino desenvolva competências cognitivas e socioemocionais, bem como a compreensão crítica das tecnologias digitais de informação e comunicação.",
            howToUseInParagraph = "Excelente para temas sobre fake news, inteligência artificial nas escolas, inclusão digital de estudantes de baixa renda e educação midiática contra golpes na internet.",
            compatibleThemes = listOf("Tecnologia & IA", "Educação", "Redes Sociais & Mídia", "Justiça, Leis & Cidadania Digital"),
            groupCode = "LDB"
        ),

        // ================= ECA — ESTATUTO DA CRIANÇA E DO ADOLESCENTE (LEI Nº 8.069/1990) =================
        SocioculturalRepertoireItem(
            id = "rep_soc_eca",
            title = "ECA • Estatuto da Criança e do Adolescente (Doutrina da Proteção Integral)",
            authorOrSource = "Lei Federal nº 8.069/1990 (Regulamenta o Art. 227 da CF/88)",
            areaOfKnowledge = "Direito da Criança e do Adolescente",
            primaryAxis = "Infância, Juventude & Envelhecimento",
            coreConcept = "Substituiu o antigo Código de Menores pela Doutrina da Proteção Integral: crianças e adolescentes não são 'menores infratores ou objetos da tutela do Estado', mas sujeitos plenos de direitos em condição peculiar de desenvolvimento biopsicossocial.",
            howToUseInParagraph = "Abertura ou fundamentação em temas sobre trabalho infantil, evasão escolar, exposição precoce a telas, violência doméstica, bullying e carência nutricional de crianças.",
            compatibleThemes = listOf("Infância, Juventude & Envelhecimento", "Educação", "Redes Sociais & Mídia", "Saúde & Bem-Estar", "Direitos Humanos & Minorias", "Segurança Pública"),
            groupCode = "ECA"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_eca_art4",
            title = "ECA • Art. 4º: O Princípio da Prioridade Absoluta",
            authorOrSource = "ECA — Lei nº 8.069/1990 (Regra Orçamentária e Social)",
            areaOfKnowledge = "Direito da Criança e do Adolescente",
            primaryAxis = "Infância, Juventude & Envelhecimento",
            coreConcept = "Determina que a garantia de prioridade compreende: primazia de receber socorro em quaisquer circunstâncias; precedência de atendimento nos serviços públicos; preferência na formulação e execução de políticas sociais públicas; e destinação privilegiada de recursos públicos nas áreas de proteção à infância.",
            howToUseInParagraph = "Excelente no D1 para comprovar omissão estatal: quando o governo corta verbas de creches, merenda ou hospitais pediátricos, viola a prioridade orçamentária do Art. 4º do ECA.",
            compatibleThemes = listOf("Infância, Juventude & Envelhecimento", "Educação", "Saúde & Bem-Estar", "Economia & Consumo"),
            groupCode = "ECA"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_eca_art18",
            title = "ECA • Art. 18 e 18-A: Lei Menino Bernardo (Proteção contra Castigos Físicos)",
            authorOrSource = "ECA — Lei Federal nº 13.010/2014 (Lei da Palmada)",
            areaOfKnowledge = "Direito de Família & Direitos Humanos",
            primaryAxis = "Infância, Juventude & Envelhecimento",
            coreConcept = "Assegura à criança e ao adolescente o direito de serem educados e cuidados sem o uso de castigo físico ou de tratamento cruel ou degradante como forma de correção ou disciplina.",
            howToUseInParagraph = "Perfeito para temas sobre cultura de violência doméstica, disciplina autoritária, trauma infantil, saúde mental juvenil e combate aos abusos intrafamiliares.",
            compatibleThemes = listOf("Infância, Juventude & Envelhecimento", "Saúde & Bem-Estar", "Segurança Pública", "Educação"),
            groupCode = "ECA"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_eca_art53",
            title = "ECA • Art. 53: Direito à Educação, Respeito e Contestação Escolar",
            authorOrSource = "ECA — Lei nº 8.069/1990 (Educação Básica e Cidadã)",
            areaOfKnowledge = "Legislação da Infância & Educação",
            primaryAxis = "Educação",
            coreConcept = "A criança e o adolescente têm direito à educação voltada ao pleno desenvolvimento de sua pessoa e cidadania, garantindo-lhes igualdade de condições para acesso e permanência, direito de ser respeitado por seus educadores e direito de contestar critérios avaliativos.",
            howToUseInParagraph = "Use para defender o combate ao assédio moral e ao bullying nas escolas, promoção de acolhimento psicológico a alunos vulneráveis e redução das reprovações injustas.",
            compatibleThemes = listOf("Educação", "Infância, Juventude & Envelhecimento", "Saúde & Bem-Estar"),
            groupCode = "ECA"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_eca_art60",
            title = "ECA • Art. 60: Proibição Rígida do Trabalho Infantil",
            authorOrSource = "ECA — Lei nº 8.069/1990 (Proteção ao Trabalho Proibido)",
            areaOfKnowledge = "Direito do Trabalho & Infância",
            primaryAxis = "Trabalho & Previdência",
            coreConcept = "É expressamente proibido qualquer trabalho a menores de 14 anos de idade, salvo na condição de aprendiz a partir dos 14 anos, vedando trabalho noturno, perigoso, insalubre ou prejudicial à formação moral e escolar.",
            howToUseInParagraph = "Arma argumentativa central em temas sobre exploração do trabalho infantil no campo e nos semáforos urbanos, evasão escolar precoce e precarização geracional.",
            compatibleThemes = listOf("Trabalho & Previdência", "Infância, Juventude & Envelhecimento", "Educação", "Direitos Humanos & Minorias"),
            groupCode = "ECA"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_eca_art70",
            title = "ECA • Art. 70 e 70-A: Prevenção de Violações e Segurança no Meio Digital",
            authorOrSource = "ECA — Lei Federal nº 8.069/1990 e atualizações de proteção digital",
            areaOfKnowledge = "Direito Digital & Proteção da Criança",
            primaryAxis = "Redes Sociais & Mídia",
            coreConcept = "Obriga o Poder Público, pais e provedores de serviços tecnológicos a prevenir ameaças e violações de direitos de crianças e adolescentes no ambiente virtual (ciberpedofilia, vazamento de dados, cyberbullying, apostas online e exploração comercial abusiva).",
            howToUseInParagraph = "Essencial para temas sobre impactos de celulares nas salas de aula, algoritmos predatórios, proteção de dados de crianças e regulação de redes sociais.",
            compatibleThemes = listOf("Redes Sociais & Mídia", "Tecnologia & IA", "Infância, Juventude & Envelhecimento", "Segurança Pública"),
            groupCode = "ECA"
        ),

        // ================= OUTROS MARCOS LEGAIS DE GRANDE VALOR NO ENEM ("ENTRE OUTROS") =================
        SocioculturalRepertoireItem(
            id = "rep_soc_lbi",
            title = "LBI • Lei Brasileira de Inclusão da Pessoa com Deficiência (Lei nº 13.146/2015)",
            authorOrSource = "Estatuto da Pessoa com Deficiência (Lei Federal nº 13.146/2015)",
            areaOfKnowledge = "Direito da Pessoa com Deficiência & Acessibilidade",
            primaryAxis = "Inclusão Social & PcD",
            coreConcept = "Define a deficiência sob o modelo biopsicossocial: o impedimento reside na interação das barreiras sociais e ambientais que obstruem a participação plena. Assegura acessibilidade universal, proíbe cobrança de mensalidades mais altas em escolas particulares e criminaliza a discriminação.",
            howToUseInParagraph = "O melhor repertório legal para temas sobre inclusão de PcD, capacitismo, acessibilidade urbana, neurodiversidade e inclusão de pessoas com deficiência no mercado de trabalho.",
            compatibleThemes = listOf("Inclusão Social & PcD", "Educação", "Mobilidade & Questão Urbana", "Trabalho & Previdência", "Direitos Humanos & Minorias"),
            groupCode = "LEIS_OUTROS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_idoso",
            title = "Estatuto da Pessoa Idosa (Lei nº 10.741/2003)",
            authorOrSource = "Legislação Federal de Proteção aos 60+ (Lei nº 10.741/2003)",
            areaOfKnowledge = "Direito Geriátrico & Direitos Sociais",
            primaryAxis = "Infância, Juventude & Envelhecimento",
            coreConcept = "Assegura prioridade absoluta aos cidadãos com idade igual ou superior a 60 anos, tornando crime o abandono moral e material, a violência física e o etarismo (discriminação por idade), além de garantir transporte gratuito e vagas prioritárias.",
            howToUseInParagraph = "Indispensável para temas sobre envelhecimento populacional no Brasil, mercado de trabalho para maiores de 50 anos, solidão e abandono na terceira idade e previdência social.",
            compatibleThemes = listOf("Infância, Juventude & Envelhecimento", "Saúde & Bem-Estar", "Trabalho & Previdência", "Cidadania"),
            groupCode = "LEIS_OUTROS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_internet",
            title = "Marco Civil da Internet (Lei 12.965/14) & LGPD (Lei 13.709/18)",
            authorOrSource = "Legislação Brasileira de Direitos Digitais e Proteção de Dados",
            areaOfKnowledge = "Direito Digital & Tecnologia",
            primaryAxis = "Tecnologia & IA",
            coreConcept = "O Marco Civil consagra a neutralidade da rede, liberdade de expressão e privacidade; a LGPD garante que todo titular tenha controle e consentimento explícito sobre a coleta, uso e tratamento de seus dados pessoais por empresas e pelo governo.",
            howToUseInParagraph = "Use para fundamentar discussões sobre vigilância de dados na internet, vazamento de registros médicos, inteligência artificial generativa, golpes online e regulação das Big Techs.",
            compatibleThemes = listOf("Tecnologia & IA", "Redes Sociais & Mídia", "Justiça, Leis & Cidadania Digital", "Economia & Consumo"),
            groupCode = "LEIS_OUTROS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_maria_penha",
            title = "Lei Maria da Penha (Lei nº 11.340/2006)",
            authorOrSource = "Legislação Federal de Combate à Violência de Gênero",
            areaOfKnowledge = "Direito Penal & Direitos das Mulheres",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Reconhecida pela ONU como uma das 3 leis mais avançadas do mundo contra violência de gênero. Tipifica cinco formas de agressão contra a mulher: física, psicológica, sexual, patrimonial e moral, instituindo medidas protetivas de urgência.",
            howToUseInParagraph = "Repertório incontestável para temas sobre feminicídio, violência doméstica, misoginia na internet, assédio moral no ambiente corporativo e invisibilidade feminina.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Segurança Pública", "Saúde & Bem-Estar", "Cultura & Identidade"),
            groupCode = "LEIS_OUTROS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_dudh",
            title = "Declaração Universal dos Direitos Humanos — DUDH (ONU, 1948)",
            authorOrSource = "Organização das Nações Unidas (Adotada em 10 de dezembro de 1948)",
            areaOfKnowledge = "Direito Internacional Humanitário",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Marco civilizatório mundial pós-Segunda Guerra. No Art. 1º, proclama: 'Todos os seres humanos nascem livres e iguais em dignidade e direitos'. No Art. 26, preconiza o direito à educação gratuita e orientada ao pleno desenvolvimento humano e à tolerância entre nações.",
            howToUseInParagraph = "Contextualização histórica universal para temas globais e nacionais: crise de refugiados, combate a preconceitos, xenofobia, erradicação da tortura e acesso a direitos essenciais.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Cidadania", "Educação", "Política & Democracia"),
            groupCode = "LEIS_OUTROS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_igualdade_racial",
            title = "Estatuto da Igualdade Racial (Lei nº 12.288/2010)",
            authorOrSource = "Legislação Federal Antidiscriminatória",
            areaOfKnowledge = "Direito Antidiscriminatório & Relações Raciais",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Destinado a garantir à população negra a efetivação da igualdade de oportunidades, a defesa dos direitos étnicos individuais, coletivos e difusos e o combate à discriminação e às demais formas de intolerância étnica.",
            howToUseInParagraph = "Base legal perfeita para temas sobre cotas raciais, mercado de trabalho inclusivo, intolerância religiosa contra terreiros de candomblé/umbanda e genocídio da juventude negra periférica.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Cultura & Identidade", "Educação", "Segurança Pública"),
            groupCode = "LEIS_OUTROS"
        ),

        // ================= FILÓSOFOS AMPLIADOS =================
        SocioculturalRepertoireItem(
            id = "rep_soc_kant",
            title = "O Papel Transformador da Educação & Imperativo Categórico",
            authorOrSource = "Immanuel Kant (Filósofo Iluminista Alemão)",
            areaOfKnowledge = "Filosofia Moderna & Ética",
            primaryAxis = "Educação",
            coreConcept = "Kant afirma que 'o homem é aquilo que a educação faz dele'. Defende também o Imperativo Categórico: agir de maneira que a máxima da sua conduta possa se transformar em lei universal para toda a humanidade.",
            howToUseInParagraph = "Ideal para o D1 ou D2: demonstra que a ausência de educação cidadã nas escolas gera indivíduos desprovidos de empatia coletiva e respeito às normas de convívio social.",
            compatibleThemes = listOf("Educação", "Cidadania", "Meio Ambiente & Clima", "Redes Sociais & Mídia"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_locke",
            title = "Teoria da Tábula Rasa e Garantia dos Direitos Naturais",
            authorOrSource = "John Locke (Filósofo Empirista Inglês)",
            areaOfKnowledge = "Filosofia Política & Epistemologia",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Locke defende que a mente humana nasce como uma 'folha em branco' (tábula rasa), moldada pelas vivências, e que o Estado é criado por um pacto para garantir direitos naturais invioláveis: vida, liberdade e bens.",
            howToUseInParagraph = "Use no D1 para comprovar que comportamentos preconceituosos não nascem com o indivíduo, mas são aprendidos em uma sociedade preconceituosa, cabendo à escola educar para a tolerância.",
            compatibleThemes = listOf("Educação", "Direitos Humanos & Minorias", "Cultura & Identidade", "Política & Democracia"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_rousseau",
            title = "O Contrato Social e a Corrupção pela Sociedade Desigual",
            authorOrSource = "Jean-Jacques Rousseau (Filósofo Iluminista Suíço-Francês)",
            areaOfKnowledge = "Filosofia Política",
            primaryAxis = "Política & Democracia",
            coreConcept = "Na célebre formulação: 'O homem nasce livre, e por toda parte encontra-se a ferros'. Para Rousseau, as desigualdades artificiais e a propriedade desregrada corrompem a harmonia humana, devendo o Estado expressar a Vontade Geral voltada ao bem comum.",
            howToUseInParagraph = "Excelente para demonstrar que o Estado negligencia o bem coletivo em favor de interesses de elites econômicas, gerando exclusão e miséria social.",
            compatibleThemes = listOf("Política & Democracia", "Educação", "Direitos Humanos & Minorias", "Economia & Consumo"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_platao",
            title = "O Mito da Caverna e as Sombras da Desinformação",
            authorOrSource = "Platão (Filósofo da Grécia Antiga — Diálogo 'A República')",
            areaOfKnowledge = "Filosofia Clássica & Teoria do Conhecimento",
            primaryAxis = "Tecnologia & IA",
            coreConcept = "Alegoria em que prisioneiros acorrentados no fundo da caverna acreditam que as sombras projetadas na parede são a realidade absoluta; sair da caverna em direção à luz do Sol exige esforço racional e busca pela verdade.",
            howToUseInParagraph = "Repertório brilhante para temas sobre fake news, bolhas ideológicas algorítmicas, manipulação digital e negligência em relação a problemas reais invisibilizados.",
            compatibleThemes = listOf("Tecnologia & IA", "Redes Sociais & Mídia", "Educação", "Política & Democracia"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_hobbes",
            title = "O Leviatã e a Ruptura do Contrato Social",
            authorOrSource = "Thomas Hobbes (Filósofo Contratualista Inglês)",
            areaOfKnowledge = "Filosofia Política",
            primaryAxis = "Segurança Pública",
            coreConcept = "Para Hobbes, no estado de natureza o 'homem é o lobo do próprio homem'; a sociedade cria o Estado (o Leviatã) para garantir a segurança, a ordem e o bem-estar coletivo. Quando o Estado é ineficiente, a sociedade retorna à barbárie e à insegurança.",
            howToUseInParagraph = "Abertura clássica para temas sobre segurança pública, crise carcerária, violência contra minorias e ausência de saneamento ou serviços essenciais.",
            compatibleThemes = listOf("Segurança Pública", "Política & Democracia", "Saúde & Bem-Estar", "Justiça, Leis & Cidadania Digital"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_aristoteles",
            title = "Justiça Distributiva e Princípio da Equidade",
            authorOrSource = "Aristóteles (Filósofo Grego — 'Ética a Nicômaco')",
            areaOfKnowledge = "Filosofia Ética e Política",
            primaryAxis = "Inclusão Social & PcD",
            coreConcept = "Conceito de Equidade: tratar igualmente os iguais e desigualmente os desiguais, na exata medida de suas desigualdades. A igualdade material exige que grupos vulneráveis recebam apoio proporcional diferenciado do Estado.",
            howToUseInParagraph = "Indispensável em temas sobre cotas universitárias, atendimento prioritário a PcD, políticas afirmativas para mulheres e quilombolas e justiça fiscal redistributiva.",
            compatibleThemes = listOf("Inclusão Social & PcD", "Direitos Humanos & Minorias", "Educação", "Trabalho & Previdência"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_beauvoir",
            title = "Construção Social do Gênero e Marginalização dos Idosos",
            authorOrSource = "Simone de Beauvoir (Filósofa Existencialista Francesa)",
            areaOfKnowledge = "Filosofia Feminista & Existencialismo",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Em 'O Segundo Sexo', desmistifica o determinismo biológico: 'Não se nasce mulher, torna-se mulher'. Em 'A Velhice', denuncia que a sociedade tecnocrática descarta os idosos quando eles perdem a capacidade de gerar lucro imediato.",
            howToUseInParagraph = "Use a primeira obra para desigualdade salarial, trabalho invisível do cuidado e machismo; use a segunda obra para etarismo e solidão de pessoas idosas.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Infância, Juventude & Envelhecimento", "Trabalho & Previdência", "Cultura & Identidade"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_foucault",
            title = "Microfísica do Poder e Sociedade Disciplinar",
            authorOrSource = "Michel Foucault (Filósofo e Historiador Francês)",
            areaOfKnowledge = "Filosofia Contemporânea",
            primaryAxis = "Segurança Pública",
            coreConcept = "Em 'Vigiar e Punir', Foucault mostra como as instituições disciplinares (prisões, escolas, manicômios, hospitais) operam para docilizar corpos e vigiar comportamentos, excluindo quem diverge dos padrões utilitários do sistema.",
            howToUseInParagraph = "Perfeito para temas sobre sistema prisional brasileiro, estigma em torno de doenças mentais, punitivismo estéril e vigilância cibernética.",
            compatibleThemes = listOf("Segurança Pública", "Saúde & Bem-Estar", "Justiça, Leis & Cidadania Digital", "Educação"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_arendt",
            title = "A Banalidade do Mal e a Ausência de Pensamento Crítico",
            authorOrSource = "Hannah Arendt (Filósofa Política Alemã — 'Eichmann em Jerusalém')",
            areaOfKnowledge = "Filosofia Política",
            primaryAxis = "Política & Democracia",
            coreConcept = "O mal sistemático se perpetua não por indivíduos monstruosos extraordinários, mas quando pessoas comuns deixam de refletir criticamente sobre as ordens recebidas e naturalizam violências e preconceitos cotidianos como mera rotina burocrática.",
            howToUseInParagraph = "Repertório coringa para o D2 (causa cultural): explica por que a sociedade assiste passivamente à fome, à violência policial, ao racismo ou ao assédio sem intervir.",
            compatibleThemes = listOf("Segurança Pública", "Direitos Humanos & Minorias", "Política & Democracia", "Inclusão Social & PcD", "Trabalho & Previdência"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_bauman",
            title = "Modernidade Líquida e Cegueira Moral",
            authorOrSource = "Zygmunt Bauman (Sociólogo e Filósofo Polonês)",
            areaOfKnowledge = "Sociologia e Filosofia Contemporânea",
            primaryAxis = "Economia & Consumo",
            coreConcept = "Na pós-modernidade, as instituições, laços de afeto e compromissos éticos tornaram-se fluidos e descartáveis ('relações líquidas'). Na era do consumo, indivíduos que não têm poder de compra tornam-se 'lixo humano' invisibilizado.",
            howToUseInParagraph = "Use para explicar a falta de empatia nas redes sociais, o consumismo desenfreado, a obsolescência programada de eletrônicos e a indiferença com populações em situação de rua.",
            compatibleThemes = listOf("Economia & Consumo", "Meio Ambiente & Clima", "Redes Sociais & Mídia", "Infância, Juventude & Envelhecimento"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_byung_chul_han",
            title = "Sociedade do Cansaço e a Tirania do Rendimento",
            authorOrSource = "Byung-Chul Han (Filósofo Sul-Coreano radicado na Alemanha)",
            areaOfKnowledge = "Filosofia Contemporânea",
            primaryAxis = "Saúde & Bem-Estar",
            coreConcept = "No século XXI, a opressão externa deu lugar à autocoerção pela hiperprodutividade ininterrupta ('Sociedade do Desempenho'). O indivíduo torna-se o seu próprio feitor, gerando uma epidemia de Burnout, depressão e futilidade digital.",
            howToUseInParagraph = "Fundamental para temas sobre saúde mental de estudantes e trabalhadores, vício em redes sociais, pressão estética e esgotamento profissional (uberização).",
            compatibleThemes = listOf("Saúde & Bem-Estar", "Trabalho & Previdência", "Redes Sociais & Mídia", "Tecnologia & IA"),
            groupCode = "FILOSOFOS"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_habermas",
            title = "Teoria do Agir Comunicativo e a Esfera Pública Democrática",
            authorOrSource = "Jürgen Habermas (Filósofo Alemão da Escola de Frankfurt)",
            areaOfKnowledge = "Filosofia Política e Comunicação",
            primaryAxis = "Comunicação & Artes",
            coreConcept = "A democracia legítima requer uma 'esfera pública' desobstruída, em que o diálogo racional livre de coerção e desinformação seja o critério condutor das decisões coletivas e do bem comum.",
            howToUseInParagraph = "Excelente para discutir polarização política, fake news, desinformação eleitoral, linchamento virtual e necessidade de debate público ético e embasado cientificamente.",
            compatibleThemes = listOf("Comunicação & Artes", "Política & Democracia", "Redes Sociais & Mídia", "Tecnologia & IA"),
            groupCode = "FILOSOFOS"
        ),

        // ================= PENSADORES, SOCIÓLOGOS E INTELECTUAIS =================
        SocioculturalRepertoireItem(
            id = "rep_soc_dimenstein",
            title = "Cidadãos de Papel e Inefetividade Constitucional",
            authorOrSource = "Gilberto Dimenstein (Jornalista, Escritor e Educador)",
            areaOfKnowledge = "Sociologia & Direitos Humanos",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "O Brasil possui no papel leis exemplares (CF/88, ECA, LDB), mas na prática milhões de brasileiros são reduzidos a 'cidadãos de papel' porque o Poder Público é ineficiente na entrega dos serviços fundamentais.",
            howToUseInParagraph = "Repertório coringa número 1 para o D1 (omissão estatal): utilize em qualquer tema em que haja direitos previstos na lei sendo violados na prática cotidiana.",
            compatibleThemes = listOf("Educação", "Saúde & Bem-Estar", "Segurança Pública", "Direitos Humanos & Minorias", "Inclusão Social & PcD"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_freire",
            title = "Pedagogia do Oprimido e Educação como Prática da Liberdade",
            authorOrSource = "Paulo Freire (Patrono da Educação Brasileira)",
            areaOfKnowledge = "Pedagogia Crítica e Filosofia da Educação",
            primaryAxis = "Educação",
            coreConcept = "Critica a 'educação bancária' (mera transmissão passiva de conteúdos para alunos domesticados) e propõe a pedagogia libertadora, dialógica e problematizadora: 'Se a educação sozinha não transforma a sociedade, sem ela tampouco a sociedade muda'.",
            howToUseInParagraph = "Versátil para o D1, D2 ou Conclusão (C5): demonstra que o letramento crítico dos estudantes é o antídoto contra a alienação e a perpetuação de opressões sociais.",
            compatibleThemes = listOf("Educação", "Cultura & Identidade", "Política & Democracia", "Cidadania"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_bourdieu",
            title = "Violência Simbólica e Reprodução Social",
            authorOrSource = "Pierre Bourdieu (Sociólogo Francês)",
            areaOfKnowledge = "Sociologia da Educação e Cultura",
            primaryAxis = "Educação",
            coreConcept = "As instituições sociais (em especial a escola tradicional) reproduzem as hierarquias de classe por meio do Capital Cultural das famílias ricas, impondo uma 'violência simbólica' sutil que faz privilégios parecerem mérito individual.",
            howToUseInParagraph = "Altamente produtivo para temas de evasão escolar, elitização dos vestibulares, preconceito linguístico e desigualdade salarial de gênero e raça.",
            compatibleThemes = listOf("Educação", "Cultura & Identidade", "Inclusão Social & PcD", "Trabalho & Previdência"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_darcy_ribeiro",
            title = "A Crise da Educação como Projeto Político Estruturado",
            authorOrSource = "Darcy Ribeiro (Antropólogo, Educador e Idealizador dos CIEPs)",
            areaOfKnowledge = "Antropologia e Sociologia Brasileira",
            primaryAxis = "Educação",
            coreConcept = "Célebre tese de 'O Povo Brasileiro': 'A crise da educação no Brasil não é uma crise; é um projeto'. As elites dirigentes mantiveram historicamente a educação pública precarizada para conter a mobilidade social das massas.",
            howToUseInParagraph = "Argumento devastador no D1 para demonstrar por que faltam investimentos contínuos em escolas de periferia e na remuneração dos docentes no Brasil.",
            compatibleThemes = listOf("Educação", "Política & Democracia", "Cultura & Identidade", "Trabalho & Previdência"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_durkheim",
            title = "Fato Social e a Teoria da Anomia Social",
            authorOrSource = "Émile Durkheim (Pai da Sociologia Funcionalista)",
            areaOfKnowledge = "Sociologia Clássica",
            primaryAxis = "Segurança Pública",
            coreConcept = "O conceito de 'Anomia Social' designa o colapso moral e institucional em que as leis e regras deixam de orientar e proteger os cidadãos, enfraquecendo a solidariedade e a coesão coletiva.",
            howToUseInParagraph = "Use para explicar por que leis sem fiscalização prática geram impunidade no trânsito, crimes virtuais, desrespeito às normas ambientais e violência urbana.",
            compatibleThemes = listOf("Segurança Pública", "Mobilidade & Questão Urbana", "Justiça, Leis & Cidadania Digital", "Meio Ambiente & Clima"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_milton_santos",
            title = "Cidadanias Mutiladas e Segregação Socioespacial",
            authorOrSource = "Milton Santos (Maior Geógrafo Brasileiro — Prêmio Vautrin Lud)",
            areaOfKnowledge = "Geografia Urbana e Sociologia do Espaço",
            primaryAxis = "Mobilidade & Questão Urbana",
            coreConcept = "No Brasil, o acesso a direitos civis e serviços fundamentais (hospitais, teatros, praças, esgoto, transporte rápido) depende diretamente do endereço onde a pessoa reside: periferias e favelas vivenciam uma 'cidadania mutilada'.",
            howToUseInParagraph = "Perfeito para temas sobre saneamento básico, gentrificação urbana, desertos alimentares e culturais nas periferias e transporte público deficiente.",
            compatibleThemes = listOf("Mobilidade & Questão Urbana", "Saúde & Bem-Estar", "Cultura & Identidade", "Alimentação & Segurança Alimentar"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_djamila",
            title = "Lugar de Fala e Urgência do Antirracismo Ativo",
            authorOrSource = "Djamila Ribeiro (Filósofa e Escritora Brasileira)",
            areaOfKnowledge = "Filosofia Política e Relações Étnico-Raciais",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "Em 'Pequeno Manual Antirracista', ensina que não basta não ser racista, é indispensável ser ativamente antirracista, tirando os privilégios da invisibilidade e assegurando o 'lugar de fala' aos grupos silenciados.",
            howToUseInParagraph = "Indispensável para temas de representatividade negra e indígena, combate ao capacitismo, invisibilidade do trabalho feminino e letramento racial nas escolas.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Cultura & Identidade", "Inclusão Social & PcD", "Educação"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_silvio_almeida",
            title = "O Conceito de Racismo Estrutural",
            authorOrSource = "Silvio Almeida (Filósofo do Direito e Jurista Brasileiro)",
            areaOfKnowledge = "Direito e Teoria Crítica Racial",
            primaryAxis = "Direitos Humanos & Minorias",
            coreConcept = "O racismo não é uma anomalia comportamental isolada, mas um elemento estrutural e cotidiano que organiza as relações econômicas, políticas e judiciais no Brasil desde a colonização e a escravidão.",
            howToUseInParagraph = "Aplique para demonstrar por que homens negros são maioria nos presídios e desempregados, enquanto são minoria nos cargos de liderança pública e privada.",
            compatibleThemes = listOf("Direitos Humanos & Minorias", "Segurança Pública", "Trabalho & Previdência", "Política & Democracia"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_krenak",
            title = "Ideias para Adiar o Fim do Mundo e Cosmovisão Indígena",
            authorOrSource = "Ailton Krenak (Líder Indígena, Ambientalista e Imortal da ABL)",
            areaOfKnowledge = "Ecologia Política e Antropologia",
            primaryAxis = "Meio Ambiente & Clima",
            coreConcept = "Critica a ilusão da civilização ocidental de que os seres humanos são donos da natureza e que o planeta é apenas uma mercadoria extrativista a ser explorada até a exaustão; propõe reconectar a vida à sensibilidade biológica.",
            howToUseInParagraph = "Perfeito para temas sobre crise climática, demarcação de terras ancestrais, preservação da biodiversidade e respeito aos saberes de povos tradicionais.",
            compatibleThemes = listOf("Meio Ambiente & Clima", "Cultura & Identidade", "Economia & Consumo", "Direitos Humanos & Minorias"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_zuboff",
            title = "A Era do Capitalismo de Vigilância",
            authorOrSource = "Shoshana Zuboff (Professora Emérita da Universidade Harvard)",
            areaOfKnowledge = "Sociologia da Tecnologia e Economia Digital",
            primaryAxis = "Tecnologia & IA",
            coreConcept = "As Big Techs extraem secretamente os rastros de comportamento pessoal dos usuários na internet como matéria-prima comportamental gratuita para prever, manipular e leiloar decisões humanas a anunciantes e governos.",
            howToUseInParagraph = "Use no D1 ou D2 para discutir algoritmos viciantes, vazamento de dados, inteligência artificial opaca, monetização da atenção de crianças e erosão da autonomia democrática.",
            compatibleThemes = listOf("Tecnologia & IA", "Redes Sociais & Mídia", "Política & Democracia", "Infância, Juventude & Envelhecimento"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_josue_de_castro",
            title = "Geografia da Fome e Insegurança Alimentar no Brasil",
            authorOrSource = "Josué de Castro (Médico, Geógrafo Brasileiro e ex-Presidente da FAO)",
            areaOfKnowledge = "Geografia da Saúde e Nutrição",
            primaryAxis = "Alimentação & Segurança Alimentar",
            coreConcept = "Desmontou o mito biológico e climático da fome: demonstrou pioneiramente que a desnutrição é uma chaga social provocada por escolhas políticas, salários aviltantes e concentração de terras.",
            howToUseInParagraph = "Indispensável para temas sobre fome, desperdício de comida, ultraprocessados, agricultura familiar e má distribuição de renda no Brasil.",
            compatibleThemes = listOf("Alimentação & Segurança Alimentar", "Saúde & Bem-Estar", "Economia & Consumo", "Direitos Humanos & Minorias"),
            groupCode = "PENSADORES"
        ),
        SocioculturalRepertoireItem(
            id = "rep_soc_sergio_buarque",
            title = "O 'Homem Cordial' e o Patrimonialismo Brasileiro",
            authorOrSource = "Sérgio Buarque de Holanda (Historiador e Sociólogo — 'Raízes do Brasil')",
            areaOfKnowledge = "Historiografia e Sociologia Brasileira",
            primaryAxis = "Política & Democracia",
            coreConcept = "O conceito de 'Homem Cordial' não significa bondade, mas o agir pelo 'coração' (emoções e laços familiares) em vez da razão legal, gerando o patrimonialismo (confusão perversa entre bens públicos e interesses particulares).",
            howToUseInParagraph = "Excelente para explicar a cultura de corrupção, favoritismo no setor público, leniência diante de irregularidades fiscais e fraqueza das instituições republicanas.",
            compatibleThemes = listOf("Política & Democracia", "Economia & Consumo", "Cidadania", "Justiça, Leis & Cidadania Digital"),
            groupCode = "PENSADORES"
        )
    )

    // =========================================================================
    // 7. BANCO DE CITAÇÕES POR CATEGORIA (FILÓSOFOS, PENSADORES, MÚSICAS, SÉRIES, FILMES, LITERATURA, LEIS)
    // =========================================================================
    val quoteBankCategories = listOf(
        "TODOS" to "Todos os Tópicos",
        "CF88" to "Constituição 1988",
        "LDB" to "LDB",
        "ECA" to "ECA",
        "LEIS_ONU" to "Leis & ONU",
        "FILOSOFOS" to "Filósofos",
        "PENSADORES" to "Pensadores & Sociólogos",
        "MUSICAS" to "Músicas",
        "SERIES" to "Séries",
        "FILMES" to "Filmes & Docs",
        "LITERATURA" to "Literatura"
    )

    val quoteBankItems = listOf(
        // MARCOS LEGAIS ESSENCIAIS: CONSTITUIÇÃO FEDERAL DE 1988, LDB E ECA
        // ================= CONSTITUIÇÃO FEDERAL DE 1988 (ARTIGOS ESPECÍFICOS) =================
        QuoteBankItem(
            id = "q_lei_cf88",
            category = "CF88",
            categoryLabel = "Tópico • Constituição 1988 (Geral)",
            authorOrCreator = "Assembleia Nacional Constituinte (1988)",
            workTitle = "Constituição Federal de 1988 (Art. 1º, Art. 5º, Art. 6º, Art. 205, Art. 215, Art. 225 e Art. 227)",
            quoteOrReferenceText = "\"Art. 6º: São direitos sociais a educação, a saúde, a alimentação, o trabalho, a moradia, o transporte, o lazer, a segurança, a previdência social, a proteção à maternidade e à infância, e a assistência aos desamparados.\"",
            contextMeaning = "Conhecida como 'Constituição Cidadã', a Carta Magna de 1988 fundamenta a República na Dignidade da Pessoa Humana (Art. 1º, III) e obriga o Estado a garantir direitos fundamentais a todos os brasileiros sem distinção (Art. 5º).",
            howToConnectInEssay = "Abertura coringa infalível para qualquer tema do ENEM: apresente o direito constitucional no 1º período e inicie o 2º período com 'Todavia,' ou 'Contudo,' para evidenciar a falha estatal na prática.",
            recommendedAxes = listOf("Educação", "Saúde & Bem-Estar", "Direitos Humanos & Minorias", "Cidadania", "Trabalho & Previdência", "Meio Ambiente & Clima")
        ),
        QuoteBankItem(
            id = "q_cf88_art1",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 1º, III",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 1º, Inciso III — Princípio da Dignidade da Pessoa Humana",
            quoteOrReferenceText = "\"A República Federativa do Brasil tem como fundamentos: (...) III - a dignidade da pessoa humana.\"",
            contextMeaning = "Valor supremo e cláusula orientadora de toda a ordem constitucional brasileira, que proíbe a coisificação do ser humano e impõe condições dignas de existência.",
            howToUseInParagraph = "Ideal para abrir o D1 contra violações graves: pessoas em situação de rua, trabalho escravo contemporâneo, violência obstétrica, desnutrição e falta de saneamento.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Trabalho & Previdência", "Saúde & Bem-Estar", "Cidadania")
        ),
        QuoteBankItem(
            id = "q_cf88_art3",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 3º",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 3º — Objetivos Fundamentais da República",
            quoteOrReferenceText = "\"Constituem objetivos fundamentais da República Federativa do Brasil: I - construir uma sociedade livre, justa e solidária; II - erradicar a pobreza e a marginalização e reduzir as desigualdades sociais e regionais; IV - promover o bem de todos, sem preconceitos de origem, raça, sexo, cor, idade e quaisquer outras formas de discriminação.\"",
            contextMeaning = "Compromisso programático inegociável do Estado brasileiro de erradicar desigualdades e combater qualquer manifestação de discriminação.",
            howToUseInParagraph = "Use no D1 para comprovar omissão governamental diante da perpetuação da pobreza extrema, desigualdade regional ou intolerância a minorias.",
            recommendedAxes = listOf("Cidadania", "Direitos Humanos & Minorias", "Educação", "Economia & Consumo")
        ),
        QuoteBankItem(
            id = "q_cf88_art5",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 5º",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 5º — Princípio da Isonomia e Direitos Fundamentais",
            quoteOrReferenceText = "\"Todos são iguais perante a lei, sem distinção de qualquer natureza, garantindo-se aos brasileiros e aos estrangeiros residentes no País a inviolabilidade do direito à vida, à liberdade, à igualdade, à segurança e à propriedade.\"",
            contextMeaning = "Consagra a igualdade perante a lei, a livre manifestação do pensamento, a liberdade de culto e a inviolabilidade da intimidade e da vida privada.",
            howToUseInParagraph = "Perfeito para temas sobre liberdade de expressão nas redes sociais, preconceito religioso, direito à privacidade de dados e igualdade salarial.",
            recommendedAxes = listOf("Justiça, Leis & Cidadania Digital", "Redes Sociais & Mídia", "Cultura & Identidade", "Segurança Pública")
        ),
        QuoteBankItem(
            id = "q_cf88_art196",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 196",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 196 — Direito Universal à Saúde",
            quoteOrReferenceText = "\"A saúde é direito de todos e dever do Estado, garantido mediante políticas sociais e econômicas que visem à redução do risco de doença e de outros agravos e ao acesso universal e igualitário às ações e serviços para sua promoção, proteção e recuperação.\"",
            contextMeaning = "Fundamento jurídico do Sistema Único de Saúde (SUS), garantindo atendimento médico, hospitalar, vacinal e psicológico a qualquer pessoa sem custos.",
            howToUseInParagraph = "Abertura direta para temas sobre saúde mental de adolescentes, doação de medula/órgãos, saneamento básico, combate a epidemias e atenção farmacêutica.",
            recommendedAxes = listOf("Saúde & Bem-Estar", "Infância, Juventude & Envelhecimento", "Ciência & Bioética", "Alimentação & Segurança Alimentar")
        ),
        QuoteBankItem(
            id = "q_cf88_art205",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 205",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 205 e 206 — Dever do Estado e da Família com a Educação",
            quoteOrReferenceText = "\"Art. 205: A educação, direito de todos e dever do Estado e da família, será promovida e incentivada com a colaboração da sociedade, visando ao pleno desenvolvimento da pessoa, seu preparo para o exercício da cidadania e sua qualificação para o trabalho.\"",
            contextMeaning = "Garante padrão de qualidade pedagógica, gestão democrática, gratuidade em estabelecimentos oficiais e valorização remuneratória do corpo docente.",
            howToUseInParagraph = "Citação clássica para abrir o parágrafo de introdução ou D1 em temas de evasão escolar, letramento digital, valorização de professores e escolas técnicas.",
            recommendedAxes = listOf("Educação", "Inclusão Social & PcD", "Tecnologia & IA", "Trabalho & Previdência")
        ),
        QuoteBankItem(
            id = "q_cf88_art215",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 215",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 215 — Pleno Exercício dos Direitos Culturais",
            quoteOrReferenceText = "\"O Estado garantirá a todos o pleno exercício dos direitos culturais e acesso às fontes da cultura nacional, e apoiará e incentivará a valorização e a difusão das manifestações culturais.\"",
            contextMeaning = "Protege manifestações de culturas populares, indígenas e afro-brasileiras e impõe proteção ao patrimônio histórico e artístico nacional.",
            howToUseInParagraph = "Excelente para democratização de cinemas, feiras literárias, preservação de tradições quilombolas e línguas nativas e descentralização de centros culturais.",
            recommendedAxes = listOf("Cultura & Identidade", "Direitos Humanos & Minorias", "Educação", "Comunicação & Artes")
        ),
        QuoteBankItem(
            id = "q_cf88_art225",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 225",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 225 — Meio Ambiente Ecologicamente Equilibrado",
            quoteOrReferenceText = "\"Todos têm direito ao meio ambiente ecologicamente equilibrado, bem de uso comum do povo e essencial à sadia qualidade de vida, impondo-se ao Poder Público e à coletividade o dever de defendê-lo e preservá-lo para as presentes e futuras gerações.\"",
            contextMeaning = "Consagra o princípio da solidariedade intergeracional e a obrigação de recuperar ecossistemas degradados e controlar atividades poluentes.",
            howToUseInParagraph = "Abertura primordial em temas sobre transição energética, queimadas ilegais, poluição hídrica, descarte de lixo eletrônico e justiça climática.",
            recommendedAxes = listOf("Meio Ambiente & Clima", "Ciência & Bioética", "Alimentação & Segurança Alimentar", "Economia & Consumo")
        ),
        QuoteBankItem(
            id = "q_cf88_art227",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 227",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 227 — Prioridade Absoluta da Infância e Juventude",
            quoteOrReferenceText = "\"É dever da família, da sociedade e do Estado assegurar à criança, ao adolescente e ao jovem, com absoluta prioridade, o direito à vida, à saúde, à alimentação, à educação, ao lazer, à profissionalização, à cultura, à dignidade, ao respeito, à liberdade e à convivência familiar e comunitária, além de colocá-los a salvo de toda forma de negligência, discriminação, exploração, violência, crueldade e opressão.\"",
            contextMeaning = "Consagra a Doutrina da Proteção Integral com prioridade orçamentária e operacional na formulação e execução de políticas públicas infantojuvenis.",
            howToUseInParagraph = "Use na abertura ou D1 para temas sobre exploração de trabalho infantil, exposição a apostas virtuais, saúde mental nas escolas e proteção contra abusos.",
            recommendedAxes = listOf("Infância, Juventude & Envelhecimento", "Educação", "Redes Sociais & Mídia", "Segurança Pública")
        ),
        QuoteBankItem(
            id = "q_cf88_art231",
            category = "CF88",
            categoryLabel = "Tópico • CF/88 Art. 231",
            authorOrCreator = "Constituição Federal de 1988",
            workTitle = "CF/88 Artigo 231 — Direitos Originários dos Povos Indígenas",
            quoteOrReferenceText = "\"São reconhecidos aos índios sua organização social, costumes, línguas, crenças e tradições, e os direitos originários sobre as terras que tradicionalmente ocupam, competindo à União demarcá-las, proteger e fazer respeitar todos os seus bens.\"",
            contextMeaning = "Proteção jurídica irrestrita à posse permanente e usufruto exclusivo das terras tradicionais como garantia da sobrevivência física e cultural originária.",
            howToUseInParagraph = "Essencial para temas de demarcação territorial, combate a invasões e garimpos ilegais, preservação de línguas indígenas e proteção da Amazônia.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Meio Ambiente & Clima", "Cultura & Identidade")
        ),

        // ================= LDB — LEI DE DIRETRIZES E BASES (LEI Nº 9.394/1996) =================
        QuoteBankItem(
            id = "q_lei_ldb",
            category = "LDB",
            categoryLabel = "Tópico • LDB Art. 2º (Finalidade)",
            authorOrCreator = "Lei Federal nº 9.394/1996 (Lei Darcy Ribeiro)",
            workTitle = "LDB — Lei de Diretrizes e Bases da Educação Nacional (Art. 2º)",
            quoteOrReferenceText = "\"Art. 2º: A educação, dever da família e do Estado, inspirada nos princípios de liberdade e nos ideais de solidariedade humana, tem por finalidade o pleno desenvolvimento do educando, seu preparo para o exercício da cidadania e sua qualificação para o trabalho.\"",
            contextMeaning = "A LDB organiza toda a estrutura da educação escolar brasileira, preconizando que a escola não pode ser mero depósito conteudista, mas formadora do cidadão pleno.",
            howToUseInParagraph = "Use no D1 ou D2 para defender que a escola contemporânea deve conciliar rigor acadêmico com ética cívica, combate a preconceitos e preparo crítico para o trabalho.",
            recommendedAxes = listOf("Educação", "Inclusão Social & PcD", "Infância, Juventude & Envelhecimento", "Tecnologia & IA", "Trabalho & Previdência")
        ),
        QuoteBankItem(
            id = "q_ldb_art3",
            category = "LDB",
            categoryLabel = "Tópico • LDB Art. 3º (Princípios & Docência)",
            authorOrCreator = "LDB — Lei nº 9.394/1996",
            workTitle = "LDB Artigo 3º — Princípios do Ensino e Valorização dos Profissionais",
            quoteOrReferenceText = "\"O ensino será ministrado com base nos seguintes princípios: I - igualdade de condições para o acesso e permanência na escola; II - liberdade de aprender, ensinar, pesquisar e divulgar a cultura, o pensamento, a arte e o saber; VII - valorização do profissional da educação escolar; VIII - gestão democrática do ensino público.\"",
            contextMeaning = "Estabelece os pilares democráticos da escola pública republicana: permanência estudantil, pluralidade pedagógica e dignidade salarial docente.",
            howToUseInParagraph = "Repertório imbatível no D1 para discutir os baixos salários dos professores, a precariedade de salas de aula e a necessidade de permanência de alunos pobres no colégio.",
            recommendedAxes = listOf("Educação", "Trabalho & Previdência", "Cultura & Identidade", "Política & Democracia")
        ),
        QuoteBankItem(
            id = "q_ldb_art4",
            category = "LDB",
            categoryLabel = "Tópico • LDB Art. 4º (Educação Inclusiva & AEE)",
            authorOrCreator = "LDB — Lei nº 9.394/1996",
            workTitle = "LDB Artigo 4º, III — Atendimento Educacional Especializado (AEE)",
            quoteOrReferenceText = "\"O dever do Estado com educação escolar pública será efetivado mediante a garantia de: (...) III - atendimento educacional especializado gratuito aos educandos com deficiência, transtornos globais do desenvolvimento e altas habilidades ou superdotação, transversal a todos os níveis, etapas e modalidades, preferencialmente na rede regular de ensino.\"",
            contextMeaning = "Consagra o paradigma da educação inclusiva: a escola regular deve adaptar seus métodos e prover profissionais mediadores para garantir o aprendizado de estudantes com deficiência.",
            howToUseInParagraph = "Fundamental para temas sobre inclusão de crianças autistas (TEA), estudantes surdos ou com síndrome de Down nas escolas regulares e carência de profissionais de apoio.",
            recommendedAxes = listOf("Inclusão Social & PcD", "Educação", "Infância, Juventude & Envelhecimento", "Saúde & Bem-Estar")
        ),
        QuoteBankItem(
            id = "q_ldb_art26a",
            category = "LDB",
            categoryLabel = "Tópico • LDB Art. 26-A (Cultura Afro e Indígena)",
            authorOrCreator = "LDB — Leis nº 10.639/2003 e 11.645/2008",
            workTitle = "LDB Artigo 26-A — Obrigatoriedade do Ensino da História e Cultura Afro-Brasileira e Indígena",
            quoteOrReferenceText = "\"Nos estabelecimentos de ensino fundamental e de ensino médio, públicos e privados, torna-se obrigatório o estudo da história e cultura afro-brasileira e indígena.\"",
            contextMeaning = "Reparação histórica e epistêmica que visa descolonizar o currículo escolar tradicional eurocêntrico e valorizar as matrizes civilizatórias que fundaram o Brasil.",
            howToUseInParagraph = "Use no D1 ou D2 para temas de combate ao racismo estrutural, respeito a comunidades quilombolas e valorização de literaturas e saberes ancestrais.",
            recommendedAxes = listOf("Cultura & Identidade", "Direitos Humanos & Minorias", "Educação", "História & Memória")
        ),
        QuoteBankItem(
            id = "q_ldb_art35a",
            category = "LDB",
            categoryLabel = "Tópico • LDB Art. 35-A (Letramento Digital)",
            authorOrCreator = "LDB — Diretrizes Curriculares da BNCC",
            workTitle = "LDB Artigo 35-A — Competências Digitais e Pensamento Crítico no Ensino Médio",
            quoteOrReferenceText = "\"A Base Nacional Comum Curricular definirá direitos e objetivos de aprendizagem do ensino médio (...) incluindo a compreensão crítica do significado das ciências, das letras e das artes e o domínio dos princípios da tecnologia da informação e comunicação.\"",
            contextMeaning = "Determina que a escola forme alunos capacitados a lidar criticamente com algoritmos, redes sociais, inteligência artificial e desinformação na era digital.",
            howToUseInParagraph = "Perfeito para temas sobre educação midiática, fake news nas eleições, inclusão digital nas periferias e uso ético de tecnologias em sala de aula.",
            recommendedAxes = listOf("Tecnologia & IA", "Educação", "Redes Sociais & Mídia", "Justiça, Leis & Cidadania Digital")
        ),

        // ================= ECA — ESTATUTO DA CRIANÇA E DO ADOLESCENTE (LEI Nº 8.069/1990) =================
        QuoteBankItem(
            id = "q_lei_eca",
            category = "ECA",
            categoryLabel = "Tópico • ECA Art. 4º (Prioridade Absoluta)",
            authorOrCreator = "Lei Federal nº 8.069/1990",
            workTitle = "ECA — Estatuto da Criança e do Adolescente (Art. 4º)",
            quoteOrReferenceText = "\"Art. 4º: É dever da família, da comunidade, da sociedade em geral e do poder público assegurar, com absoluta prioridade, a efetivação dos direitos referentes à vida, à saúde, à alimentação, à educação, ao esporte, ao lazer, à profissionalização, à cultura, à dignidade, ao respeito, à liberdade e à convivência familiar e comunitária.\"",
            contextMeaning = "O ECA regulamenta o Artigo 227 da CF/88, estabelecendo preferência de crianças e jovens em socorros, políticas governamentais e dotações orçamentárias prioritárias.",
            howToUseInParagraph = "Cite na Introdução, D1 ou D2 sempre que o tema envolver jovens: evasão escolar, trabalho infantil, cyberbullying, vício em telas, saúde mental e violência escolar.",
            recommendedAxes = listOf("Infância, Juventude & Envelhecimento", "Educação", "Redes Sociais & Mídia", "Saúde & Bem-Estar", "Segurança Pública")
        ),
        QuoteBankItem(
            id = "q_eca_art18",
            category = "ECA",
            categoryLabel = "Tópico • ECA Art. 18-A (Lei Menino Bernardo)",
            authorOrCreator = "ECA — Lei Federal nº 13.010/2014",
            workTitle = "ECA Artigo 18-A — Direito de ser Educado sem Castigos Físicos",
            quoteOrReferenceText = "\"A criança e o adolescente têm o direito de ser educados e cuidados sem o uso de castigo físico ou de tratamento cruel ou degradante, como formas de correção, disciplina, educação ou qualquer outro pretexto.\"",
            contextMeaning = "Marco civilizatório que rompe com a violência intrafamiliar e pedagógica como método educativo, estimulando o diálogo e a parentalidade positiva.",
            howToUseInParagraph = "Essencial para temas sobre violência doméstica contra crianças, saúde mental na infância, traumas psicológicos e desconstrução do autoritarismo familiar.",
            recommendedAxes = listOf("Infância, Juventude & Envelhecimento", "Saúde & Bem-Estar", "Segurança Pública", "Educação")
        ),
        QuoteBankItem(
            id = "q_eca_art53",
            category = "ECA",
            categoryLabel = "Tópico • ECA Art. 53 (Direito à Educação)",
            authorOrCreator = "ECA — Lei nº 8.069/1990",
            workTitle = "ECA Artigo 53 — Pleno Desenvolvimento e Respeito no Ambiente Escolar",
            quoteOrReferenceText = "\"A criança e o adolescente têm direito à educação, visando ao pleno desenvolvimento de sua pessoa, preparo para o exercício da cidadania e qualificação para o trabalho, assegurando-se-lhes: I - igualdade de condições para o acesso e permanência na escola; II - direito de ser respeitado por seus educadores.\"",
            contextMeaning = "Garante que o ambiente escolar seja um território seguro, acolhedor e promotor de cidadania contra abusos morais e discriminações.",
            howToUseInParagraph = "Use no D2 para cobrar programas de combate ao bullying, suporte psicossocial nas escolas públicas e prevenção de transtornos de ansiedade nos estudantes.",
            recommendedAxes = listOf("Educação", "Infância, Juventude & Envelhecimento", "Saúde & Bem-Estar")
        ),
        QuoteBankItem(
            id = "q_eca_art60",
            category = "ECA",
            categoryLabel = "Tópico • ECA Art. 60 (Combate ao Trabalho Infantil)",
            authorOrCreator = "ECA — Lei nº 8.069/1990",
            workTitle = "ECA Artigo 60 — Vedação ao Trabalho de Menores",
            quoteOrReferenceText = "\"É proibido qualquer trabalho a menores de quatorze anos de idade, salvo na condição de aprendiz.\"",
            contextMeaning = "Protege o desenvolvimento biológico e intelectual precoce, impedindo que a carência econômica familiar force crianças a abandonar a escola para trabalhar.",
            howToUseInParagraph = "Argumento definitivo para temas sobre erradicação do trabalho infantil no campo e nas cidades, semáforos, carvoarias e exploração doméstica.",
            recommendedAxes = listOf("Trabalho & Previdência", "Infância, Juventude & Envelhecimento", "Educação", "Direitos Humanos & Minorias")
        ),
        QuoteBankItem(
            id = "q_eca_art70",
            category = "ECA",
            categoryLabel = "Tópico • ECA Art. 70 (Proteção no Meio Digital)",
            authorOrCreator = "ECA — Lei nº 8.069/1990 e Marcos de Proteção Digital",
            workTitle = "ECA Artigo 70 — Prevenção a Ameaças e Violações Digitais",
            quoteOrReferenceText = "\"É dever de todos prevenir a ocorrência de ameaça ou violação dos direitos da criança e do adolescente.\"",
            contextMeaning = "Estende a responsabilidade protetiva a plataformas digitais, jogos eletrônicos e redes sociais que expõem crianças a riscos de exploração ou assédio.",
            howToUseInParagraph = "Perfeito para temas sobre vício em apostas online (bets), algoritmos que induzem anorexia ou automutilação e regulação das Big Techs.",
            recommendedAxes = listOf("Redes Sociais & Mídia", "Tecnologia & IA", "Infância, Juventude & Envelhecimento", "Segurança Pública")
        ),

        // ================= OUTROS MARCOS LEGAIS DE GRANDE VALOR NO ENEM ("ENTRE OUTROS") =================
        QuoteBankItem(
            id = "q_lei_lbi",
            category = "LEIS_ONU",
            categoryLabel = "Outras Leis • LBI (Lei 13.146/15)",
            authorOrCreator = "Lei Federal nº 13.146/2015 (Estatuto da Pessoa com Deficiência)",
            workTitle = "LBI — Lei Brasileira de Inclusão da Pessoa com Deficiência (Art. 1º e Art. 28)",
            quoteOrReferenceText = "\"Art. 1º: É instituída a Lei Brasileira de Inclusão da Pessoa com Deficiência, destinada a assegurar e a promover, em condições de igualdade, o exercício dos direitos e das liberdades fundamentais por pessoa com deficiência, visando à sua inclusão social e cidadania.\"",
            contextMeaning = "Adota a definição da Convenção da ONU: a deficiência não é um defeito individual, mas o resultado das barreiras que a sociedade impõe à sua plena inclusão.",
            howToUseInParagraph = "A melhor referência legislativa para redações sobre capacitismo, acessibilidade urbana, inclusão no mercado de trabalho e educação bilíngue de surdos.",
            recommendedAxes = listOf("Inclusão Social & PcD", "Educação", "Mobilidade & Questão Urbana", "Trabalho & Previdência")
        ),
        QuoteBankItem(
            id = "q_lei_idoso",
            category = "LEIS_ONU",
            categoryLabel = "Outras Leis • Estatuto da Pessoa Idosa",
            authorOrCreator = "Lei Federal nº 10.741/2003",
            workTitle = "Estatuto da Pessoa Idosa (Art. 2º e Art. 3º)",
            quoteOrReferenceText = "\"Art. 2º: A pessoa idosa goza de todos os direitos fundamentais inerentes à pessoa humana (...) assegurando-se-lhe, por lei ou por outros meios, todas as oportunidades e facilidades para preservação de sua saúde física e mental e seu aperfeiçoamento moral, intelectual, espiritual e social.\"",
            contextMeaning = "Garante proteção integral contra o etarismo, abandono afetivo em asilos, golpes financeiros e precarização de planos de saúde para a terceira idade.",
            howToUseInParagraph = "Indispensável em temas sobre transição demográfica no Brasil, mercado de trabalho para 60+, solidão urbana e valorização da sabedoria dos mais velhos.",
            recommendedAxes = listOf("Infância, Juventude & Envelhecimento", "Saúde & Bem-Estar", "Trabalho & Previdência", "Cidadania")
        ),
        QuoteBankItem(
            id = "q_lei_lgpd",
            category = "LEIS_ONU",
            categoryLabel = "Outras Leis • Marco Civil & LGPD",
            authorOrCreator = "Leis Federais nº 12.965/2014 e nº 13.709/2018",
            workTitle = "Marco Civil da Internet & Lei Geral de Proteção de Dados Pessoais (LGPD)",
            quoteOrReferenceText = "\"A disciplina do uso da internet e dos dados pessoais tem como fundamentos o respeito à privacidade, a autodeterminação informativa, a liberdade de expressão e a defesa do consumidor.\"",
            contextMeaning = "Regula o ecossistema digital no Brasil, garantindo neutralidade de tráfego na rede e exigindo consentimento transparente para a coleta de dados de usuários.",
            howToUseInParagraph = "Use para discutir vigilância digital, vazamento de registros biométricos, algoritmos de reconhecimento facial falhos e publicidade abusiva online.",
            recommendedAxes = listOf("Tecnologia & IA", "Redes Sociais & Mídia", "Justiça, Leis & Cidadania Digital")
        ),
        QuoteBankItem(
            id = "q_lei_maria_penha",
            category = "LEIS_ONU",
            categoryLabel = "Outras Leis • Lei Maria da Penha",
            authorOrCreator = "Lei Federal nº 11.340/2006",
            workTitle = "Lei Maria da Penha (Art. 1º e Art. 7º)",
            quoteOrReferenceText = "\"Cria mecanismos para coibir e prevenir a violência doméstica e familiar contra a mulher (...) e tipifica a violência em física, psicológica, sexual, patrimonial e moral.\"",
            contextMeaning = "Marco pioneiro que transformou agressões domésticas em crimes de ordem pública com medidas protetivas emergenciais e atendimento multidisciplinar.",
            howToUseInParagraph = "Fundamental para temas sobre feminicídio, misoginia, autonomia financeira da mulher, combate a relacionamentos abusivos e cultura patriarcal.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Segurança Pública", "Saúde & Bem-Estar", "Cultura & Identidade")
        ),
        QuoteBankItem(
            id = "q_lei_dudh",
            category = "LEIS_ONU",
            categoryLabel = "Outras Leis • Declaração Universal (DUDH)",
            authorOrCreator = "Organização das Nações Unidas (ONU, 1948)",
            workTitle = "Declaração Universal dos Direitos Humanos (Art. 1º e Art. 26)",
            quoteOrReferenceText = "\"Art. 1º: Todos os seres humanos nascem livres e iguais em dignidade e em direitos. Dotados de razão e de consciência, devem agir uns para com os outros em espírito de fraternidade.\"",
            contextMeaning = "Documento fundante dos Direitos Humanos globais proclamado após as barbáries da 2ª Guerra Mundial, universalizando garantias de vida, liberdade e educação.",
            howToUseInParagraph = "Excelente repertório para alusão histórica internacional na introdução de qualquer tema de direitos fundamentais, refugiados ou combate a preconceitos.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Cidadania", "Educação", "Política & Democracia")
        ),
        QuoteBankItem(
            id = "q_lei_igualdade_racial",
            category = "LEIS_ONU",
            categoryLabel = "Outras Leis • Estatuto da Igualdade Racial",
            authorOrCreator = "Lei Federal nº 12.288/2010",
            workTitle = "Estatuto da Igualdade Racial (Art. 1º e Art. 4º)",
            quoteOrReferenceText = "\"Destinado a garantir à população negra a efetivação da igualdade de oportunidades, a defesa dos direitos étnicos individuais, coletivos e difusos e o combate à discriminação e às demais formas de intolerância étnica.\"",
            contextMeaning = "Obriga o Poder Público a adotar ações afirmativas reparatórias para erradicar as assimetrias históricas herdadas de quase 4 séculos de escravidão.",
            howToUseInParagraph = "Ideal para o D1 ou D2 em temas sobre cotas no serviço público, intolerância religiosa contra o candomblé, desigualdade salarial e valorização da cultura negra.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Cultura & Identidade", "Educação", "Segurança Pública")
        ),

        // ================= FILÓSOFOS AMPLIADOS =================
        QuoteBankItem(
            id = "q_fil_1",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Immanuel Kant",
            workTitle = "Sobre a Pedagogia / Crítica da Razão Prática",
            quoteOrReferenceText = "\"O ser humano é aquilo que a educação faz dele.\"",
            contextMeaning = "Para o filósofo iluminista alemão, a formação ética e racional por meio da educação é o que desenvolve a autonomia moral e civilizada do cidadão.",
            howToUseInParagraph = "Use no D1 ou D2 para defender que a ausência de educação crítica (financeira, digital, ambiental ou antirracista) nas escolas perpetua comportamentos nocivos na sociedade.",
            recommendedAxes = listOf("Educação", "Cidadania", "Meio Ambiente & Clima", "Redes Sociais & Mídia")
        ),
        QuoteBankItem(
            id = "q_fil_locke",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "John Locke",
            workTitle = "Ensaio Acerca do Entendimento Humano (Teoria da Tábula Rasa)",
            quoteOrReferenceText = "\"A mente humana é como uma folha em branco, desprovida de quaisquer caracteres, que adquire todo o seu conhecimento por meio da experiência.\"",
            contextMeaning = "O filósofo empirista inglês demonstra que comportamentos, preconceitos e virtudes são aprendidos no convívio social, e não inatos ao nascimento.",
            howToUseInParagraph = "Use no D1 para comprovar que se o preconceito é aprendido nas relações sociais, cabe à escola e à cultura formar novas gerações sob o prisma da empatia.",
            recommendedAxes = listOf("Educação", "Direitos Humanos & Minorias", "Cultura & Identidade", "Política & Democracia")
        ),
        QuoteBankItem(
            id = "q_fil_rousseau",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Jean-Jacques Rousseau",
            workTitle = "Do Contrato Social / Discurso sobre a Origem da Desigualdade",
            quoteOrReferenceText = "\"O homem nasce livre, e por toda parte encontra-se a ferros.\"",
            contextMeaning = "Para Rousseau, as instituições sociais corrompem a liberdade e a harmonia humana quando priorizam a ganância e a propriedade desigual sobre o bem comum.",
            howToUseInParagraph = "Excelente para comprovar omissão estatal e desequilíbrio social: o governo deve agir para libertar o cidadão das correntes da miséria e do abandono.",
            recommendedAxes = listOf("Política & Democracia", "Educação", "Direitos Humanos & Minorias", "Economia & Consumo")
        ),
        QuoteBankItem(
            id = "q_fil_platao",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Platão",
            workTitle = "A República (Livro VII — O Mito da Caverna)",
            quoteOrReferenceText = "\"Os homens acorrentados na caverna tomam as sombras projetadas na parede pela verdade única.\"",
            contextMeaning = "Alegoria que ilustra como a alienação e a ignorância aprisionam os indivíduos, que confundem aparências enganosas com a realidade autêntica.",
            howToUseInParagraph = "Perfeito para temas sobre bolhas algorítmicas, fake news, manipulação da opinião pública pelas redes sociais e indiferença com problemas sociais ocultados.",
            recommendedAxes = listOf("Tecnologia & IA", "Redes Sociais & Mídia", "Educação", "Política & Democracia")
        ),
        QuoteBankItem(
            id = "q_fil_2",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Thomas Hobbes",
            workTitle = "Leviatã (Teoria do Contrato Social)",
            quoteOrReferenceText = "\"O homem é o lobo do próprio homem no estado de natureza; cabe ao Estado assegurar a paz, a ordem e o bem-estar coletivo.\"",
            contextMeaning = "Os indivíduos abdicam da violência primitiva em favor do Estado soberano em troca de segurança; se o Estado é negligente, o pacto social desmorona.",
            howToUseInParagraph = "Repertório clássico para abrir a Introdução ou iniciar o D1 em qualquer tema de segurança pública, violência urbana, saúde precária e abandono estatal.",
            recommendedAxes = listOf("Segurança Pública", "Política & Democracia", "Saúde & Bem-Estar", "Justiça, Leis & Cidadania Digital")
        ),
        QuoteBankItem(
            id = "q_fil_3",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Aristóteles",
            workTitle = "Ética a Nicômaco / A Política",
            quoteOrReferenceText = "\"Devemos tratar igualmente os iguais e desigualmente os desiguais, na medida exata de sua desigualdade.\"",
            contextMeaning = "Conceito de Justiça Distributiva (Equidade): a verdadeira igualdade exige ações afirmativas para reparar as desvantagens acumuladas por grupos vulneráveis.",
            howToUseInParagraph = "Repertório de ouro para temas sobre cotas raciais e sociais, acessibilidade de PcD, políticas públicas para mulheres e justiça socioambiental.",
            recommendedAxes = listOf("Inclusão Social & PcD", "Direitos Humanos & Minorias", "Educação", "Justiça, Leis & Cidadania Digital")
        ),
        QuoteBankItem(
            id = "q_fil_4",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Simone de Beauvoir",
            workTitle = "O Segundo Sexo / A Velhice",
            quoteOrReferenceText = "\"Não se nasce mulher, torna-se mulher.\" / \"A sociedade tecnocrática não vê com bons olhos os idosos porque eles já não produzem lucro.\"",
            contextMeaning = "Analisa a opressão de gênero como construção sociocultural e a descartabilidade das pessoas idosas no modelo utilitarista de produção capitalista.",
            howToUseInParagraph = "Use a primeira obra para desigualdade de gênero e sobrecarga feminina; use a segunda para etarismo, preconceito contra a terceira idade e abandono.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Infância, Juventude & Envelhecimento", "Trabalho & Previdência")
        ),
        QuoteBankItem(
            id = "q_fil_5",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Michel Foucault",
            workTitle = "Vigiar e Punir / Microfísica do Poder",
            quoteOrReferenceText = "\"As instituições disciplinares docilizam corpos e segregam aqueles considerados 'anormais' pela ordem vigente.\"",
            contextMeaning = "Investiga como prisões, manicômios, fábricas e escolas operam para disciplinar corpos e vigiar comportamentos, excluindo quem diverge da norma padrão.",
            howToUseInParagraph = "Perfeito para temas sobre sistema prisional, segurança pública, estigma das doenças mentais, punitivismo estéril e vigilância cibernética.",
            recommendedAxes = listOf("Segurança Pública", "Saúde & Bem-Estar", "Justiça, Leis & Cidadania Digital")
        ),
        QuoteBankItem(
            id = "q_fil_arendt",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Hannah Arendt",
            workTitle = "Eichmann em Jerusalém (A Banalidade do Mal)",
            quoteOrReferenceText = "\"O mal sistemático se dissemina não pela crueldade deliberada, mas pela incapacidade ou recusa de pensar criticamente sobre os atos cometidos.\"",
            contextMeaning = "Mostra como burocratas e cidadãos comuns naturalizam graves injustiças e preconceitos como mera rotina aceitável.",
            howToUseInParagraph = "Use no D2 para denunciar a apatia da sociedade brasileira diante da miséria, racismo, mortes no trânsito ou violência policial.",
            recommendedAxes = listOf("Segurança Pública", "Direitos Humanos & Minorias", "Política & Democracia", "Inclusão Social & PcD")
        ),
        QuoteBankItem(
            id = "q_fil_bauman",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Zygmunt Bauman",
            workTitle = "Modernidade Líquida / Cegueira Moral",
            quoteOrReferenceText = "\"Na era do consumo e da informação veloz, os laços humanos tornam-se fluidos e a indiferença diante da dor do outro torna-se um hábito normalizado.\"",
            contextMeaning = "Analisa a fragilidade das relações contemporâneas, o individualismo pós-moderno e o descarte de indivíduos marginalizados considerados 'refugo humano'.",
            howToUseInParagraph = "Aplique para explicar o consumismo excessivo, obsolescência programada, linchamento nas redes sociais e descaso com moradores de rua.",
            recommendedAxes = listOf("Economia & Consumo", "Meio Ambiente & Clima", "Redes Sociais & Mídia", "Infância, Juventude & Envelhecimento")
        ),
        QuoteBankItem(
            id = "q_fil_byung_chul_han",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Byung-Chul Han",
            workTitle = "A Sociedade do Cansaço",
            quoteOrReferenceText = "\"A sociedade do século XXI já não é disciplinar, mas uma sociedade de desempenho; seus habitantes já não são 'obedientes', mas 'empresários de si mesmos' até o esgotamento total.\"",
            contextMeaning = "Denuncia a autoexploração em que o indivíduo vira carrasco de si mesmo pela busca obstinada de produtividade, gerando burnout e ansiedade.",
            howToUseInParagraph = "Indispensável em temas sobre saúde mental de estudantes, uberização do trabalho, dependência de dopamina nas redes e culto à alta performance.",
            recommendedAxes = listOf("Saúde & Bem-Estar", "Trabalho & Previdência", "Redes Sociais & Mídia", "Tecnologia & IA")
        ),
        QuoteBankItem(
            id = "q_fil_habermas",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Jürgen Habermas",
            workTitle = "Teoria do Agir Comunicativo",
            quoteOrReferenceText = "\"A legitimidade de uma decisão democrática depende da existência de uma esfera pública livre, onde o melhor argumento racional prevaleça sobre a coerção.\"",
            contextMeaning = "A democracia deliberativa exige um debate público ético e plural, sem manipulação algorítmica ou censura dos fatos verificáveis.",
            howToUseInParagraph = "Excelente para combater fake news, polarização extremista, desinformação eleitoral e decadência da imprensa profissional.",
            recommendedAxes = listOf("Comunicação & Artes", "Política & Democracia", "Redes Sociais & Mídia", "Tecnologia & IA")
        ),
        QuoteBankItem(
            id = "q_fil_descartes",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "René Descartes",
            workTitle = "Discurso do Método",
            quoteOrReferenceText = "\"Penso, logo existo. A razão e a dúvida metódica são os instrumentos necessários para rejeitar dogmas infundados e alcançar o conhecimento seguro.\"",
            contextMeaning = "Pioneiro da ciência moderna, defende o método racional rigoroso para não se aceitar como verdade nada que não seja claro e comprovado.",
            howToUseInParagraph = "Use em temas de negacionismo científico, combate a movimentos antivacina, desinformação médica e importância da pesquisa científica nacional.",
            recommendedAxes = listOf("Ciência & Bioética", "Educação", "Tecnologia & IA", "Saúde & Bem-Estar")
        ),
        QuoteBankItem(
            id = "q_fil_schopenhauer",
            category = "FILOSOFOS",
            categoryLabel = "Filósofos",
            authorOrCreator = "Arthur Schopenhauer",
            workTitle = "O Fundamento da Moral",
            quoteOrReferenceText = "\"A compaixão sem limites por todos os seres vivos é o mais seguro e incontestável testemunho da conduta moral autêntica.\"",
            contextMeaning = "Para Schopenhauer, a verdadeira moralidade não nasce de leis formais impostas, mas da capacidade instintiva de sentir a dor do outro e agir para aliviá-la.",
            howToUseInParagraph = "Perfeito para temas sobre proteção animal, empatia com povos indígenas, acolhimento de pessoas em situação de refúgio e solidariedade social.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Meio Ambiente & Clima", "Saúde & Bem-Estar")
        ),

        // ================= PENSADORES, SOCIÓLOGOS E INTELECTUAIS =================
        QuoteBankItem(
            id = "q_pen_1",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Paulo Freire",
            workTitle = "Pedagogia do Oprimido / Pedagogia da Autonomia",
            quoteOrReferenceText = "\"Se a educação sozinha não transforma a sociedade, sem ela tampouco a sociedade muda.\"",
            contextMeaning = "O patrono da educação brasileira defende uma educação libertadora, dialógica e crítica, oposta ao ensino meramente bancário e mecânico.",
            howToUseInParagraph = "Citação versátil para o D1, D2 ou Proposta de Intervenção em temas educacionais, valorização docente, formação cidadã e leitura.",
            recommendedAxes = listOf("Educação", "Cultura & Identidade", "Política & Democracia")
        ),
        QuoteBankItem(
            id = "q_pen_darcy_ribeiro",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Darcy Ribeiro",
            workTitle = "O Povo Brasileiro (Teoria da Educação Pública)",
            quoteOrReferenceText = "\"A crise da educação no Brasil não é uma crise; é um projeto deliberado de manutenção das desigualdades sociais.\"",
            contextMeaning = "Denuncia as elites históricas brasileiras que mantiveram o ensino público desfinanciado para evitar que a população pobre tivesse acesso ao poder.",
            howToUseInParagraph = "Impacto retórico imediato no D1: use para demonstrar a negligência planejada do Estado na carreira dos professores e na infraestrutura escolar.",
            recommendedAxes = listOf("Educação", "Política & Democracia", "Cultura & Identidade", "Trabalho & Previdência")
        ),
        QuoteBankItem(
            id = "q_pen_dimenstein",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Gilberto Dimenstein",
            workTitle = "O Cidadão de Papel",
            quoteOrReferenceText = "\"O Brasil possui uma das legislações mais avançadas do planeta, mas na prática milhões de brasileiros continuam sendo apenas 'cidadãos de papel'.\"",
            contextMeaning = "Mostra o paradoxo entre a teoria legal perfeita da CF/88 e a realidade cotidiana em que hospitais, escolas e delegacias não atendem à população.",
            howToUseInParagraph = "Repertório coringa mais utilizado na nota 1000 do ENEM: encaixa perfeitamente no D1 para sustentar o argumento da ineficácia ou omissão estatal.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Cidadania", "Saúde & Bem-Estar", "Educação", "Segurança Pública")
        ),
        QuoteBankItem(
            id = "q_pen_bourdieu",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Pierre Bourdieu",
            workTitle = "A Reprodução / A Violência Simbólica",
            quoteOrReferenceText = "\"A escola frequentemente reproduz as desigualdades sociais de origem ao legitimar como 'mérito pessoal' o capital cultural herdado pelas famílias abastadas.\"",
            contextMeaning = "Mostra como sistemas meritocráticos ignoram as abismais carências materiais de estudantes da periferia, transformando desigualdade em exclusão aceita.",
            howToUseInParagraph = "Altamente produtivo para discutir evasão escolar, preconceito linguístico, elitização do ensino superior e desigualdade de oportunidades.",
            recommendedAxes = listOf("Educação", "Cultura & Identidade", "Inclusão Social & PcD", "Trabalho & Previdência")
        ),
        QuoteBankItem(
            id = "q_pen_2",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Djamila Ribeiro",
            workTitle = "Pequeno Manual Antirracista",
            quoteOrReferenceText = "\"O primeiro passo para superar um problema estrutural é tirá-lo da invisibilidade e reconhecer sua existência histórica.\"",
            contextMeaning = "Para a filósofa brasileira, problemas como o racismo, o machismo e a exclusão social não se resolvem com silêncio, mas com letramento racial e políticas ativas.",
            howToUseInParagraph = "Ideal para temas sobre herança africana, combate ao preconceito, representatividade e invisibilidade de grupos vulneráveis.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Cultura & Identidade", "Inclusão Social & PcD")
        ),
        QuoteBankItem(
            id = "q_pen_silvio_almeida",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Silvio Almeida",
            workTitle = "Racismo Estrutural",
            quoteOrReferenceText = "\"O racismo é uma decorrência da própria estrutura social, ou seja, do modo com que se organizam as relações políticas, econômicas, jurídicas e familiares.\"",
            contextMeaning = "Comprova que atos preconceituosos não são meros desvios individuais isolados, mas produtos de uma estrutura social moldada pela desigualdade racial histórica.",
            howToUseInParagraph = "Perfeito para temas sobre disparidade salarial entre negros e brancos, letalidade policial, baixa representatividade política e encarceramento em massa.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Segurança Pública", "Trabalho & Previdência", "Política & Democracia")
        ),
        QuoteBankItem(
            id = "q_pen_milton_santos",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Milton Santos",
            workTitle = "Por uma Outra Globalização / O Espaço do Cidadão",
            quoteOrReferenceText = "\"No Brasil, o acesso aos direitos fundamentais é mutilado pela localização geográfica do indivíduo: quem vive nas periferias vivencia uma cidadania mutilada.\"",
            contextMeaning = "Conceito de 'Cidadania Mutilada': o cidadão pobre é segregado espacialmente e privado de saneamento, hospitais, segurança e espaços culturais.",
            howToUseInParagraph = "Indispensável para temas sobre mobilidade urbana, transporte público ineficiente, saneamento básico, habitação popular e desertos culturais.",
            recommendedAxes = listOf("Mobilidade & Questão Urbana", "Saúde & Bem-Estar", "Cultura & Identidade", "Alimentação & Segurança Alimentar")
        ),
        QuoteBankItem(
            id = "q_pen_josue_de_castro",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Josué de Castro",
            workTitle = "Geografia da Fome",
            quoteOrReferenceText = "\"A fome no Brasil não é um fenômeno decorrente do clima ou da natureza, mas o produto direto da inércia política e da concentração desumana de terras e rendas.\"",
            contextMeaning = "Obra pioneira mundial que provou que a fome é um flagelo socioeconômico e político fabricado pelo egoísmo das elites dominantes.",
            howToUseInParagraph = "Essencial para temas sobre insegurança alimentar, agricultura familiar, desperdício de comida e desnutrição infantil.",
            recommendedAxes = listOf("Alimentação & Segurança Alimentar", "Saúde & Bem-Estar", "Economia & Consumo", "Direitos Humanos & Minorias")
        ),
        QuoteBankItem(
            id = "q_pen_krenak",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Ailton Krenak",
            workTitle = "Ideias para Adiar o Fim do Mundo",
            quoteOrReferenceText = "\"A modernidade nos alienou da Terra ao nos convencer de que somos uma humanidade separada da natureza, e não parte inseparável de seu organismo vivo.\"",
            contextMeaning = "Defende a sabedoria indígena milenar contra a fúria extrativista predatória que devasta florestas e rios em nome do lucro imediato.",
            howToUseInParagraph = "Use no D1 ou D2 em temas de crise climática, queimadas, preservação da Amazônia e valorização dos povos originários e tradicionais.",
            recommendedAxes = listOf("Meio Ambiente & Clima", "Cultura & Identidade", "Economia & Consumo", "Direitos Humanos & Minorias")
        ),
        QuoteBankItem(
            id = "q_pen_zuboff",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Shoshana Zuboff",
            workTitle = "A Era do Capitalismo de Vigilância",
            quoteOrReferenceText = "\"O capitalismo de vigilância reivindica unilateralmente a experiência humana privada como matéria-prima comportamental gratuita para predição e manipulação de condutas.\"",
            contextMeaning = "Mostra como grandes corporações de tecnologia transformam a privacidade e as emoções dos usuários em ativos econômicos leiloados.",
            howToUseInParagraph = "Excelente para discutir vigilância digital, inteligência artificial opaca, monetização da atenção de crianças e vulnerabilidade eleitoral.",
            recommendedAxes = listOf("Tecnologia & IA", "Redes Sociais & Mídia", "Política & Democracia", "Infância, Juventude & Envelhecimento")
        ),
        QuoteBankItem(
            id = "q_pen_3",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Sérgio Buarque de Holanda",
            workTitle = "Raízes do Brasil",
            quoteOrReferenceText = "\"O 'Homem Cordial' brasileiro confunde a esfera pública com os interesses privados e patrimoniais.\"",
            contextMeaning = "O historiador explica como a herança colonial patrimonialista faz com que privilégios de grupos dominantes se sobreponham ao interesse público.",
            howToUseInParagraph = "Use para fundamentar por que o Estado brasileiro muitas vezes negligencia políticas sociais ou favorece interesses privados em detrimento do coletivo.",
            recommendedAxes = listOf("Política & Democracia", "Economia & Consumo", "Meio Ambiente & Clima")
        ),
        QuoteBankItem(
            id = "q_pen_4",
            category = "PENSADORES",
            categoryLabel = "Pensadores & Sociólogos",
            authorOrCreator = "Émile Durkheim",
            workTitle = "Da Divisão do Trabalho Social (Fato Social e Anomia)",
            quoteOrReferenceText = "\"O estado de 'Anomia Social' instala-se quando as normas e instituições perdem a capacidade de garantir a coesão e a proteção dos indivíduos.\"",
            contextMeaning = "Para o pai da Sociologia moderna, quando o Estado e as leis falham em regular a vida coletiva, instala-se um caos social ('anomia').",
            howToUseInParagraph = "Repertório coringa para comprovar falhas de fiscalização na internet, trânsito, meio ambiente, segurança e leis trabalhistas.",
            recommendedAxes = listOf("Segurança Pública", "Mobilidade & Questão Urbana", "Justiça, Leis & Cidadania Digital")
        ),

        // MÚSICAS
        QuoteBankItem(
            id = "q_mus_1",
            category = "MUSICAS",
            categoryLabel = "Músicas",
            authorOrCreator = "Chico Buarque",
            workTitle = "Canção 'Construção' (1971)",
            quoteOrReferenceText = "\"Morreu na contramão atrapalhando o tráfego / Morreu na contramão atrapalhando o público.\"",
            contextMeaning = "A obra-prima de Chico Buarque retrata a desumanização do trabalhador brasileiro, cuja vida e morte são vistas apenas como um estorvo mecânico para a cidade.",
            howToConnectInEssay = "Alusão musical poderosa para abrir redações sobre precarização do trabalho (uberização), esgotamento laboral, mobilidade urbana e invisibilidade de trabalhadores.",
            recommendedAxes = listOf("Trabalho & Previdência", "Mobilidade & Questão Urbana", "Saúde & Bem-Estar")
        ),
        QuoteBankItem(
            id = "q_mus_2",
            category = "MUSICAS",
            categoryLabel = "Músicas",
            authorOrCreator = "Elza Soares (Compositor: Seu Jorge, Marcelo Yuka e Ulisses Cappelletti)",
            workTitle = "Canção 'A Carne'",
            quoteOrReferenceText = "\"A carne mais barata do mercado é a carne negra.\"",
            contextMeaning = "Denúncia contundente sobre o racismo estrutural, a marginalização socioeconômica e a vulnerabilidade da população negra no Brasil pós-abolição.",
            howToConnectInEssay = "Excelente repertório legitimado pela MPB para temas sobre valorização da herança africana, racismo, desigualdade salarial e violência nas periferias.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Cultura & Identidade", "Segurança Pública")
        ),
        QuoteBankItem(
            id = "q_mus_3",
            category = "MUSICAS",
            categoryLabel = "Músicas",
            authorOrCreator = "Racionais MC's",
            workTitle = "Álbum 'Sobrevivendo no Inferno' (Obra de leitura obrigatória da UNICAMP)",
            quoteOrReferenceText = "\"A periferia carece de oportunidades educacionais e culturais, sendo frequentemente vista pelo Estado apenas pelo viés repressivo.\"",
            contextMeaning = "Obra canônica do rap nacional que radiografa a exclusão socioespacial, o racismo e a falta de políticas públicas para a juventude periférica.",
            howToConnectInEssay = "Use em temas de segurança pública, sistema carcerário, evasão escolar, juventude e democratização da cultura/esporte.",
            recommendedAxes = listOf("Segurança Pública", "Educação", "Cultura & Identidade", "Esporte & Lazer")
        ),
        QuoteBankItem(
            id = "q_mus_4",
            category = "MUSICAS",
            categoryLabel = "Músicas",
            authorOrCreator = "Gilberto Gil",
            workTitle = "Canção 'Pela Internet' e 'Parabolicamará'",
            quoteOrReferenceText = "\"Criar meu web site, fazer minha home-page / Com quantos gigabytes se faz uma jangada, um barco que veleje.\"",
            contextMeaning = "Gilberto Gil reflete sobre a revolução digital e a necessidade de unir avanço tecnológico à inclusão social dos brasileiros tradicionais.",
            howToConnectInEssay = "Ótimo para abrir redações sobre exclusão digital, inteligência artificial, tecnologia na educação e democratização da internet.",
            recommendedAxes = listOf("Tecnologia & IA", "Redes Sociais & Mídia", "Educação")
        ),
        QuoteBankItem(
            id = "q_mus_5",
            category = "MUSICAS",
            categoryLabel = "Músicas",
            authorOrCreator = "Emicida (part. Majur e Pabllo Vittar)",
            workTitle = "Canção e Documentário 'AmarElo'",
            quoteOrReferenceText = "\"Permita que eu fale, não as minhas cicatrizes / Tudo o que nós tem é nós.\"",
            contextMeaning = "Aborda o acolhimento comunitário, a valorização da saúde mental e a resistência histórica de populações marginalizadas.",
            howToConnectInEssay = "Conecte em temas de saúde mental, prevenção ao adoecimento emocional, empatia coletiva e inclusão de minorias.",
            recommendedAxes = listOf("Saúde & Bem-Estar", "Direitos Humanos & Minorias", "Cultura & Identidade")
        ),

        // SÉRIES
        QuoteBankItem(
            id = "q_ser_1",
            category = "SERIES",
            categoryLabel = "Séries",
            authorOrCreator = "Charlie Brooker (Série Antológica)",
            workTitle = "Série 'Black Mirror' (Episódio 'Queda Livre / Nosedive')",
            quoteOrReferenceText = "\"Na trama distópica, os cidadãos avaliam uns aos outros por estrelas no celular, condicionando o valor humano às aparências digitais.\"",
            contextMeaning = "Retrata como a busca obsessiva por validação e curtidas nas redes sociais gera ansiedade, superficialidade e exclusão algorítmica.",
            howToConnectInEssay = "Use na Introdução ou D2 para temas sobre impacto das redes sociais na saúde mental, cultura do cancelamento, adultização infantil e algoritmos.",
            recommendedAxes = listOf("Redes Sociais & Mídia", "Tecnologia & IA", "Saúde & Bem-Estar")
        ),
        QuoteBankItem(
            id = "q_ser_2",
            category = "SERIES",
            categoryLabel = "Séries",
            authorOrCreator = "Moira Walley-Beckett (Baseada em 'Anne de Green Gables')",
            workTitle = "Série 'Anne with an E'",
            quoteOrReferenceText = "\"A jovem órfã Anne supera o preconceito social e transforma sua comunidade por meio do acesso à escola, à leitura e ao pensamento crítico.\"",
            contextMeaning = "A série aborda educação transformadora, igualdade de gênero, liberdade de imprensa, inclusão de indígenas e combate ao preconceito.",
            howToConnectInEssay = "Excelente repertório para abrir redações sobre educação, incentivo à leitura, valorização do professor, adoção e combate ao bullying.",
            recommendedAxes = listOf("Educação", "Infância, Juventude & Envelhecimento", "Cultura & Identidade")
        ),
        QuoteBankItem(
            id = "q_ser_3",
            category = "SERIES",
            categoryLabel = "Séries",
            authorOrCreator = "Ava DuVernay",
            workTitle = "Minissérie 'Olhos que Condenam' (When They See Us)",
            quoteOrReferenceText = "\"A obra retrata a história real de cinco adolescentes negros injustamente condenados nos EUA devido ao racismo institucional e falhas judiciais.\"",
            contextMeaning = "Denuncia a seletividade penal, o preconceito racial nas abordagens policiais e os traumas do encarceramento injusto.",
            howToConnectInEssay = "Use em temas de segurança pública, racismo estrutural, sistema socioeducativo/prisional e acesso à justiça.",
            recommendedAxes = listOf("Segurança Pública", "Direitos Humanos & Minorias", "Justiça, Leis & Cidadania Digital")
        ),
        QuoteBankItem(
            id = "q_ser_4",
            category = "SERIES",
            categoryLabel = "Séries",
            authorOrCreator = "Estela Renner e Marcos Nisti",
            workTitle = "Série Brasileira 'Aruanas'",
            quoteOrReferenceText = "\"Três ativistas investigam crimes ambientais, garimpo ilegal e contaminação de águas na Amazônia, expondo a violência contra defensores da floresta.\"",
            contextMeaning = "Ficção nacional baseada em fatos reais sobre desmatamento, justiça climática e proteção aos povos da floresta.",
            howToConnectInEssay = "Perfeita para temas de meio ambiente, preservação da Amazônia, recursos hídricos e povos tradicionais.",
            recommendedAxes = listOf("Meio Ambiente & Clima", "Segurança Pública", "Cultura & Identidade")
        ),

        // FILMES & DOCUMENTÁRIOS
        QuoteBankItem(
            id = "q_fil_doc_1",
            category = "FILMES",
            categoryLabel = "Filmes & Docs",
            authorOrCreator = "Jeff Orlowski (Documentário)",
            workTitle = "Documentário 'O Dilema das Redes' (The Social Dilemma)",
            quoteOrReferenceText = "\"Se você não está pagando pelo produto, então você é o produto.\"",
            contextMeaning = "Ex-executivos do Vale do Silício revelam como plataformas digitais são projetadas psicologicamente para viciar usuários, polarizar a política e lucrar com o tempo de atenção.",
            howToConnectInEssay = "Repertório número 1 para temas sobre vício em telas, saúde mental de jovens, desinformação/fake news e algoritmos.",
            recommendedAxes = listOf("Redes Sociais & Mídia", "Tecnologia & IA", "Política & Democracia", "Infância, Juventude & Envelhecimento")
        ),
        QuoteBankItem(
            id = "q_fil_doc_2",
            category = "FILMES",
            categoryLabel = "Filmes & Docs",
            authorOrCreator = "Anna Muylaert (Diretora Brasileira)",
            workTitle = "Filme 'Que Horas Ela Volta?'",
            quoteOrReferenceText = "\"A trajetória de Val e de sua filha Jéssica expõe a herança escravocrata invisível que ainda demarca lugares sociais desiguais no Brasil.\"",
            contextMeaning = "Critica a desigualdade de classes, a desvalorização do trabalho doméstico/cuidado e mostra a universidade como caminho de emancipação.",
            howToConnectInEssay = "Use em temas sobre trabalho doméstico/cuidado, desigualdade social, acesso ao ensino superior e mobilidade social.",
            recommendedAxes = listOf("Trabalho & Previdência", "Educação", "Inclusão Social & PcD", "Direitos Humanos & Minorias")
        ),
        QuoteBankItem(
            id = "q_fil_doc_3",
            category = "FILMES",
            categoryLabel = "Filmes & Docs",
            authorOrCreator = "Jorge Furtado (Curta-metragem Brasileiro)",
            workTitle = "Documentário 'Ilha das Flores'",
            quoteOrReferenceText = "\"Seres humanos sem dinheiro e sem dono recebem os restos de alimentos considerados inadequados até para os porcos.\"",
            contextMeaning = "Curta-metragem premiado que denuncia a lógica perversa do desperdício de alimentos, do lixo urbano e da fome extrema no Brasil.",
            howToConnectInEssay = "Imbatível para temas sobre insegurança alimentar, combate à fome, gestão de resíduos sólidos (lixo) e desigualdade econômica.",
            recommendedAxes = listOf("Alimentação & Segurança Alimentar", "Meio Ambiente & Clima", "Economia & Consumo")
        ),
        QuoteBankItem(
            id = "q_fil_doc_4",
            category = "FILMES",
            categoryLabel = "Filmes & Docs",
            authorOrCreator = "Charlie Chaplin",
            workTitle = "Filme Clássico 'Tempos Modernos' (1936)",
            quoteOrReferenceText = "\"O operário Carlitos é engolido pelas engrenagens da fábrica, simbolizando a alienação e o esgotamento humano diante do lucro mecânico.\"",
            contextMeaning = "Crítica atemporal à exploração do trabalhador, à substituição da mão de obra pela máquina e ao adoecimento ocupacional.",
            howToConnectInEssay = "Conecte em temas sobre Inteligência Artificial e automação do trabalho, Síndrome de Burnout, uberização e direitos trabalhistas.",
            recommendedAxes = listOf("Trabalho & Previdência", "Tecnologia & IA", "Saúde & Bem-Estar", "Economia & Consumo")
        ),
        QuoteBankItem(
            id = "q_fil_doc_5",
            category = "FILMES",
            categoryLabel = "Filmes & Docs",
            authorOrCreator = "Andrew Stanton (Pixar)",
            workTitle = "Animação 'Wall-E'",
            quoteOrReferenceText = "\"No futuro retratado na obra, a Terra torna-se inabitável por montanhas de lixo resultantes do hiperconsumo desenfreado e do sedentarismo digital.\"",
            contextMeaning = "Alerta sobre obsolescência programada, poluição ambiental, sedentarismo e dependência extrema de telas.",
            howToConnectInEssay = "Muito versátil para eixos de meio ambiente, lixo eletrônico, consumismo infantil, saúde/obesidade e esporte.",
            recommendedAxes = listOf("Meio Ambiente & Clima", "Economia & Consumo", "Esporte & Lazer", "Saúde & Bem-Estar")
        ),

        // LITERATURA
        QuoteBankItem(
            id = "q_lit_1",
            category = "LITERATURA",
            categoryLabel = "Literatura",
            authorOrCreator = "Carolina Maria de Jesus",
            workTitle = "Livro 'Quarto de Despejo: Diário de uma Favelada'",
            quoteOrReferenceText = "\"A favela é o 'quarto de despejo' da cidade, onde o poder público esconde a fome, a falta de saneamento e a exclusão social.\"",
            contextMeaning = "Diário real da escritora negra e catadora de papel que denunciou a fome, o déficit habitacional e o abandono das periferias brasileiras.",
            howToConnectInEssay = "Repertório literário nacional de altíssimo impacto para temas de moradia, saneamento básico, segurança alimentar, mulheres negras e leitura.",
            recommendedAxes = listOf("Alimentação & Segurança Alimentar", "Mobilidade & Questão Urbana", "Direitos Humanos & Minorias")
        ),
        QuoteBankItem(
            id = "q_lit_2",
            category = "LITERATURA",
            categoryLabel = "Literatura",
            authorOrCreator = "Graciliano Ramos",
            workTitle = "Romance 'Vidas Secas'",
            quoteOrReferenceText = "\"Fabiano via-se como um bicho, privado do domínio da linguagem formal e da proteção do Estado diante da seca e da opressão.\"",
            contextMeaning = "Obra-prima da 2ª geração modernista que retrata o êxodo climático, o preconceito linguístico, a miséria rural e a falta de cidadania.",
            howToConnectInEssay = "Use em temas de justiça climática/seca, preconceito linguístico, educação, invisibilidade civil e desigualdade regional.",
            recommendedAxes = listOf("Educação", "Meio Ambiente & Clima", "Direitos Humanos & Minorias")
        ),
        QuoteBankItem(
            id = "q_lit_3",
            category = "LITERATURA",
            categoryLabel = "Literatura",
            authorOrCreator = "George Orwell",
            workTitle = "Romance Distópico '1984'",
            quoteOrReferenceText = "\"O 'Grande Irmão' vigia cada gesto dos cidadãos por meio das teletelas, manipulando informações históricas e suprimindo o pensamento crítico.\"",
            contextMeaning = "Clássico sobre vigilância estatal/corporativa, manipulação da verdade (fake news) e controle comportamental pela tecnologia.",
            howToConnectInEssay = "Ideal para temas de privacidade de dados na internet, inteligência artificial, pós-verdade, desinformação e democracia.",
            recommendedAxes = listOf("Tecnologia & IA", "Redes Sociais & Mídia", "Política & Democracia", "Justiça, Leis & Cidadania Digital")
        ),
        QuoteBankItem(
            id = "q_lit_4",
            category = "LITERATURA",
            categoryLabel = "Literatura",
            authorOrCreator = "José Saramago (Prêmio Nobel de Literatura)",
            workTitle = "Romance 'Ensaio sobre a Cegueira'",
            quoteOrReferenceText = "\"Penso que não cegamos, penso que estamos cegos; cegos que veem, cegos que, vendo, não veem.\"",
            contextMeaning = "Metáfora sobre a 'cegueira moral' e a indiferença egoísta da sociedade moderna diante da dor e da vulnerabilidade do próximo.",
            howToConnectInEssay = "Repertório universal para o D2: explica por que a população e o Estado 'fecham os olhos' para qualquer problema social brasileiro.",
            recommendedAxes = listOf("Direitos Humanos & Minorias", "Inclusão Social & PcD", "Saúde & Bem-Estar", "Segurança Pública")
        ),

        // LEIS, CONSTITUIÇÃO & ONU
        QuoteBankItem(
            id = "q_lei_1",
            category = "LEIS_ONU",
            categoryLabel = "Leis & ONU",
            authorOrCreator = "Assembleia Nacional Constituinte",
            workTitle = "Artigo 6º e Artigo 205 da Constituição Federal de 1988",
            quoteOrReferenceText = "\"Art. 6º: São direitos sociais a educação, a saúde, a alimentação, o trabalho, a moradia, o transporte, o lazer, a segurança, a previdência social e a proteção à maternidade e à infância.\"",
            contextMeaning = "A Carta Magna de 1988 estabelece os direitos fundamentais que vinculam obrigatoriamente a atuação do Estado brasileiro.",
            howToConnectInEssay = "Abertura coringa infalível para 100% dos temas do ENEM: cite o direito previsto no Art. 6º e use 'Todavia,' no período seguinte para mostrar a falha prática.",
            recommendedAxes = listOf("Educação", "Saúde & Bem-Estar", "Trabalho & Previdência", "Mobilidade & Questão Urbana", "Esporte & Lazer", "Alimentação & Segurança Alimentar")
        ),
        QuoteBankItem(
            id = "q_lei_2",
            category = "LEIS_ONU",
            categoryLabel = "Leis & ONU",
            authorOrCreator = "Organização das Nações Unidas (ONU)",
            workTitle = "Agenda 2030 — 17 Objetivos de Desenvolvimento Sustentável (ODS)",
            quoteOrReferenceText = "\"A Agenda 2030 da ONU estabelece metas globais para erradicar a fome (ODS 2), garantir saúde (ODS 3), educação de qualidade (ODS 4), igualdade de gênero (ODS 5) e ação climática (ODS 13).\"",
            contextMeaning = "Pacto internacional assinado pelo Brasil para promover o desenvolvimento humano sustentável sem deixar ninguém para trás.",
            howToConnectInEssay = "Excelente para contextualizar o tema na Introdução ou reforçar o EFEITO da Proposta de Intervenção na Conclusão.",
            recommendedAxes = listOf("Meio Ambiente & Clima", "Alimentação & Segurança Alimentar", "Educação", "Saúde & Bem-Estar")
        )
    )

    // =========================================================================
    // 8. PATENTES DE METAIS NOBRES DE EVOLUÇÃO (DE ACORDO COM O NÍVEL / XP DO ALUNO)
    // =========================================================================
    val metalRankTiers = listOf(
        MetalRankTier(
            tierIndex = 1,
            metalName = "Bronze Forjado",
            titleBadge = "Aprendiz da Dissertação",
            minLevel = 1,
            maxLevel = 2,
            minXp = 0,
            nextTierXp = 700,
            primaryColorHex = 0xFFCD7F32,
            secondaryColorHex = 0xFF92400E,
            unlockedPerks = "Domínio da estrutura básica de 4 parágrafos e leitura estratégica do tema."
        ),
        MetalRankTier(
            tierIndex = 2,
            metalName = "Prata Acadêmica",
            titleBadge = "Articulador de Argumentos",
            minLevel = 3,
            maxLevel = 4,
            minXp = 700,
            nextTierXp = 1400,
            primaryColorHex = 0xFF94A3B8,
            secondaryColorHex = 0xFF475569,
            unlockedPerks = "Construção de Tópico Frasal forte e aplicação de conectivos interparágrafos."
        ),
        MetalRankTier(
            tierIndex = 3,
            metalName = "Ouro Imperial",
            titleBadge = "Estrategista 900+",
            minLevel = 5,
            maxLevel = 6,
            minXp = 1400,
            nextTierXp = 2100,
            primaryColorHex = 0xFFF59E0B,
            secondaryColorHex = 0xFFB45309,
            unlockedPerks = "Domínio dos 5 Elementos da Competência 5 e repertório sociocultural produtivo."
        ),
        MetalRankTier(
            tierIndex = 4,
            metalName = "Platina Cristalina",
            titleBadge = "Mestre Crítico 940+",
            minLevel = 7,
            maxLevel = 8,
            minXp = 2100,
            nextTierXp = 2800,
            primaryColorHex = 0xFF06B6D4,
            secondaryColorHex = 0xFF0E7490,
            unlockedPerks = "Circuito fechado entre Introdução e Conclusão + vocabulário erudito sem falhas."
        ),
        MetalRankTier(
            tierIndex = 5,
            metalName = "Titânio Blindado",
            titleBadge = "Autoridade Sociocultural 960+",
            minLevel = 9,
            maxLevel = 10,
            minXp = 2800,
            nextTierXp = 3500,
            primaryColorHex = 0xFF6366F1,
            secondaryColorHex = 0xFF3730A3,
            unlockedPerks = "Articulação interdisciplinar de filósofos, cinema, música e Constituição em qualquer eixo."
        ),
        MetalRankTier(
            tierIndex = 6,
            metalName = "Paládio Real",
            titleBadge = "Virtuose da Escrita 980+",
            minLevel = 11,
            maxLevel = 12,
            minXp = 3500,
            nextTierXp = 4300,
            primaryColorHex = 0xFFEC4899,
            secondaryColorHex = 0xFF9D174D,
            unlockedPerks = "Precisão sintática absoluta (200 pts na C1, C2, C4 e C5) com autoria exemplar."
        ),
        MetalRankTier(
            tierIndex = 7,
            metalName = "Ródio & Diamante Supremo",
            titleBadge = "Lenda Nota 1000 ENEM",
            minLevel = 13,
            maxLevel = 99,
            minXp = 4300,
            nextTierXp = 6000,
            primaryColorHex = 0xFF10B981,
            secondaryColorHex = 0xFF047857,
            unlockedPerks = "Patente máxima da plataforma: excelência integral nas 5 competências do ENEM."
        )
    )

    fun getMetalTierForUser(level: Int, xp: Int): MetalRankTier {
        return metalRankTiers.lastOrNull { xp >= it.minXp || level >= it.minLevel }
            ?: metalRankTiers.first()
    }
}
