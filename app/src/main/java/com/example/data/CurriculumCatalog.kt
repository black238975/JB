package com.example.data

data class LessonItem(
    val id: String,
    val moduleType: String, // FUNDAMENTOS, COMPETENCIAS, NOTA_1000
    val badge: String,
    val title: String,
    val subtitle: String,
    val durationMinutes: Int,
    val keyPoints: List<String>,
    val practicalFormula: String,
    val exampleSnippet: String
)

data class LibraryAsset(
    val id: String,
    val type: String, // VIDEOAULA, PDF_RESUMO, MAPA_MENTAL, MODELO_PRONTO, FRASE_CORINGA, REPERTORIO_TEMA
    val categoryTag: String,
    val title: String,
    val description: String,
    val contentBlocks: List<String>,
    val durationOrPages: String
)

data class ExerciseItem(
    val id: String,
    val type: String, // OBJETIVA, DISCURSIVA, ARGUMENTACAO, REPERTORIO, CONECTIVOS, GRAMATICA
    val competenciaTag: String,
    val title: String,
    val prompt: String,
    val options: List<String> = emptyList(), // Vazio se for discursiva
    val correctIndex: Int = 0,
    val explanation: String,
    val referenceKeywords: List<String> = emptyList(),
    val xpReward: Int = 50
)

object CurriculumCatalog {

    val fundamentosLessons = listOf(
        LessonItem(
            id = "fund_1",
            moduleType = "FUNDAMENTOS",
            badge = "Base Estrutural",
            title = "Estrutura Dissertativo-Argumentativa",
            subtitle = "Proporção ideal de 4 parágrafos e 30 linhas",
            durationMinutes = 15,
            keyPoints = listOf(
                "Introdução (5 a 7 linhas): Contextualização sociocultural + Tema + Tese bipartida (A1 e A2).",
                "Desenvolvimento 1 (7 a 8 linhas): Tópico frasal retomando A1 + Repertório produtivo + Aprofundamento crítico + Fechamento.",
                "Desenvolvimento 2 (7 a 8 linhas): Tópico frasal retomando A2 + Repertório legitimado + Articulação causa/consequência.",
                "Conclusão (6 a 8 linhas): Retomada da tese + Proposta de Intervenção com os 5 elementos obrigatórios (Agente, Ação, Meio/Modo, Efeito e Detalhamento)."
            ),
            practicalFormula = "[Repertório de Abertura] + [Conectivo + Tema] + [Tese: negligência estatal (A1) + silenciamento social (A2)]",
            exampleSnippet = "Sob a ótica de Thomas Hobbes, o Estado deve garantir o bem-estar coletivo. Contudo, no Brasil hodierno, observa-se um hiato entre a teoria contratualista e a prática..."
        ),
        LessonItem(
            id = "fund_2",
            moduleType = "FUNDAMENTOS",
            badge = "1º Parágrafo",
            title = "Introdução Perfeita e Projeto de Texto",
            subtitle = "Como conquistar o corretor nas primeiras 6 linhas",
            durationMinutes = 18,
            keyPoints = listOf(
                "Nunca inicie com clichês vagos ('Desde os primórdios da humanidade').",
                "Apresente uma alusão histórica, filosófica, literária ou constitucional logo no primeiro período.",
                "Vincule o repertório diretamente a todos os núcleos do recorte temático.",
                "Feche o parágrafo com dois argumentos nítidos que guiarão o D1 e o D2."
            ),
            practicalFormula = "Período 1 (Alusão) + Período 2 (Contraste com a realidade brasileira + Tema) + Período 3 (Encaminhamento argumentativo A1 e A2).",
            exampleSnippet = "Na obra 'Cidadãos de Papel', Gilberto Dimenstein denuncia que os direitos constitucionais no Brasil existem apenas no plano formal. Fora da ficção editorial, tal conjuntura reflete..."
        ),
        LessonItem(
            id = "fund_3",
            moduleType = "FUNDAMENTOS",
            badge = "2º e 3º Parágrafos",
            title = "Desenvolvimento e Tópico Frasal",
            subtitle = "Defesa de ponto de vista com progressão lógica",
            durationMinutes = 20,
            keyPoints = listOf(
                "Inicie o D1 com conectivo interparágrafo ('Em primeira análise', 'Diante desse cenário').",
                "Inicie o D2 com conectivo de soma ou prioridade ('Ademais', 'Outrossim', 'Em segunda análise').",
                "Use juízos de valor explícitos (palavras avaliativas: 'alarmante', 'inadmissível', 'arcaico', 'precário').",
                "Evite parágrafos expositivos: comente os dados ou citações apresentados."
            ),
            practicalFormula = "Tópico Frasal (Causa) -> Repertório Legitimado -> Aplicação Crítica na Realidade -> Consequência + Fechamento.",
            exampleSnippet = "Em primeira análise, cabe pontuar que a lacuna educacional perpetua o problema. Segundo Paulo Freire, a educação autêntica liberta o indivíduo..."
        ),
        LessonItem(
            id = "fund_4",
            moduleType = "FUNDAMENTOS",
            badge = "4º Parágrafo",
            title = "Conclusão e os 5 Elementos do ENEM",
            subtitle = "Fórmula blindada para 200 pontos na Competência 5",
            durationMinutes = 20,
            keyPoints = listOf(
                "1. Agente: Quem executa? (Ministério da Educação, Ministério da Saúde, Mídia, Escolas).",
                "2. Ação: O que será feito? (Deve ter verbo de ação concreta, nunca apenas 'conscientizar').",
                "3. Meio/Modo: Como será executado? ('por meio de...', 'mediante...').",
                "4. Detalhamento: Explicação, exemplificação ou justificativa de um dos elementos.",
                "5. Efeito: Para que serve? ('a fim de mitigar...', 'com o fito de assegurar...')."
            ),
            practicalFormula = "Portanto, cabe ao [AGENTE], em parceria com [DETALHAMENTO], promover [AÇÃO], por meio de [MEIO/MODO], a fim de [EFEITO].",
            exampleSnippet = "Portanto, urge que o Ministério da Educação — órgão máximo do ensino nacional — institua projetos de letramento digital nas escolas públicas, por meio de oficinas..."
        ),
        LessonItem(
            id = "fund_5",
            moduleType = "FUNDAMENTOS",
            badge = "Estratégia",
            title = "Tese, Argumentação, Coesão e Repertório",
            subtitle = "Articulação sintática e repertório legítimo e produtivo",
            durationMinutes = 25,
            keyPoints = listOf(
                "Tese Analítica vs. Sintética: prefira a tese analítica que já nomeia os dois argumentos.",
                "Coesão Referencial (pronomes, sinônimos, elipses) e Sequencial (conectivos inter e intraparágrafos).",
                "Repertório Legitimado: deve vir de área do saber reconhecida (Filosofia, Sociologia, História, Literatura, Estatística).",
                "Repertório Produtivo: precisa estar costurado à discussão, nunca 'solto' no parágrafo."
            ),
            practicalFormula = "Ideia Central -> Prova Concreta (Repertório) -> Raciocínio Crítico -> Vínculo com o Tema.",
            exampleSnippet = "Sob a perspectiva de Zygmunt Bauman em 'Modernidade Líquida', as relações contemporâneas tornaram-se frágeis e descartáveis..."
        )
    )

    val enemCompetencias = listOf(
        LessonItem(
            id = "comp_1",
            moduleType = "COMPETENCIAS",
            badge = "Competência 1 • 200 pts",
            title = "Domínio da Modalidade Escrita Formal",
            subtitle = "Gramática, regência, crase, pontuação e estrutura sintática",
            durationMinutes = 22,
            keyPoints = listOf(
                "Para atingir 200 pts, são permitidos no máximo 2 desvios gramaticais leves e nenhuma falha de estrutura sintática.",
                "Atenção máxima à vírgula: nunca separe sujeito de verbo e sempre isole adjuntos adverbiais deslocados longos.",
                "Domine a crase antes de locuções femininas ('à medida que', 'à vista de') e regência de verbos como 'visar' e 'implicar'.",
                "Evite truncamento de períodos (pontos finais precipitados em orações subordinadas)."
            ),
            practicalFormula = "Períodos compostos por subordinação + vocabulário formal + revisão de crase e concordância.",
            exampleSnippet = "Correto: 'À medida que a tecnologia avança, impõem-se novos desafios éticos à sociedade brasileira.'"
        ),
        LessonItem(
            id = "comp_2",
            moduleType = "COMPETENCIAS",
            badge = "Competência 2 • 200 pts",
            title = "Compreensão do Tema e Repertório Sociocultural",
            subtitle = "Abordagem completa do tema + repertório produtivo",
            durationMinutes = 20,
            keyPoints = listOf(
                "Sublinhe todas as palavras-chave da frase temática para evitar o tangenciamento.",
                "Apresente pelo menos 2 repertórios socioculturais legitimados ao longo do texto.",
                "Não copie trechos dos textos motivadores da coletânea."
            ),
            practicalFormula = "Abordagem integral do tema + Tipologia dissertativo-argumentativa + Repertório legitimado, pertinente e produtivo.",
            exampleSnippet = "Uso de 'A Invisibilidade Pública', de Fernando Braga da Costa, para discutir a marginalização de trabalhadores essenciais."
        ),
        LessonItem(
            id = "comp_3",
            moduleType = "COMPETENCIAS",
            badge = "Competência 3 • 200 pts",
            title = "Projeto de Texto e Defesa de Ponto de Vista",
            subtitle = "Seleção, organização e interpretação de argumentos",
            durationMinutes = 25,
            keyPoints = listOf(
                "O corretor busca um projeto estratégico sem lacunas: tudo o que é prometido na introdução deve ser desenvolvido no D1 e D2.",
                "Explique o 'porquê' e o 'como' de cada afirmação — não deixe pressupostos implícitos.",
                "Use operadores de causalidade e consequência para amarrar cada raciocínio."
            ),
            practicalFormula = "Afirmação Crítica + Justificativa Causal + Exemplo/Repertório + Desdobramento Social.",
            exampleSnippet = "Essa inércia governamental ocorre porque interesses econômicos imediatistas frequentemente sobrepõem-se às demandas socioambientais..."
        ),
        LessonItem(
            id = "comp_4",
            moduleType = "COMPETENCIAS",
            badge = "Competência 4 • 200 pts",
            title = "Mecanismos Linguísticos e Coesão Textual",
            subtitle = "Repertório diversificado de conectivos sem repetições",
            durationMinutes = 18,
            keyPoints = listOf(
                "Obrigatoriedade de conectivos interparágrafos em pelo menos 2 parágrafos (idealmente no D1, D2 e Conclusão).",
                "Presença constante de conectivos intraparágrafos (entre os períodos do mesmo parágrafo).",
                "Zero repetição vocabular próxima: substitua termos por sinônimos, hiperônimos ou expressões metafóricas."
            ),
            practicalFormula = "Mínimo de 2 conectivos interparágrafos + 2 a 3 conectivos intraparágrafos por parágrafo.",
            exampleSnippet = "Conectivos ouro: 'Sob esse viés', 'Outrossim', 'Todavia', 'Consequentemente', 'Em consonância com tal premissa', 'Portanto'."
        ),
        LessonItem(
            id = "comp_5",
            moduleType = "COMPETENCIAS",
            badge = "Competência 5 • 200 pts",
            title = "Proposta de Intervenção e Direitos Humanos",
            subtitle = "Solução concreta, detalhada e articulada aos argumentos",
            durationMinutes = 20,
            keyPoints = listOf(
                "Resolva ambos os problemas levantados no D1 e no D2 (com uma proposta abrangente ou duas propostas articuladas).",
                "Garanta os 5 elementos válidos: Agente, Ação, Meio/Modo, Efeito e Detalhamento.",
                "Feche a conclusão retomando o repertório usado na introdução (circuito fechado / efeito cíclico)."
            ),
            practicalFormula = "5 Elementos Obrigatórios + Retomada do Repertório da Introdução.",
            exampleSnippet = "Dessa forma, o Brasil deixará a condição de 'Cidadãos de Papel' descrita por Dimenstein e consolidará a dignidade constitucional."
        ),
        LessonItem(
            id = "nota_1000_1",
            moduleType = "NOTA_1000",
            badge = "Espelho Nota 1000",
            title = "Análise Comentada: Invisibilidade do Trabalho de Cuidado",
            subtitle = "Desconstrução linha por linha de uma redação nota máxima",
            durationMinutes = 30,
            keyPoints = listOf(
                "Introdução: Cita Silvia Federici ('O Calibã e a Bruxa') para historicizar a desvalorização do cuidado feminino.",
                "D1: Analisa a omissão estatal na oferta de creches e suporte público usando a Constituição de 1988.",
                "D2: Examina a mentalidade patriarcal sob a ótica de Pierre Bourdieu ('Violência Simbólica').",
                "Conclusão: Propõe Política Nacional de Economia do Cuidado pelo Ministério das Mulheres + MEC, fechando com Federici."
            ),
            practicalFormula = "Circuito Fechado: Federici na abertura -> CF/88 no D1 -> Bourdieu no D2 -> 5 Elementos + Federici no desfecho.",
            exampleSnippet = "\"Na obra 'O Calibã e a Bruxa', a filósofa Silvia Federici analisa como a transição para o capitalismo relegou o trabalho reprodutivo das mulheres à invisibilidade...\""
        )
    )

    val libraryAssets = listOf(
        LibraryAsset(
            id = "lib_vid_1",
            type = "VIDEOAULA",
            categoryTag = "Masterclass • 24 min",
            title = "Como Estruturar qualquer Tema em 10 Minutos de Rascunho",
            description = "Técnica de tempestade de ideias e seleção de repertório coringa para não travar na folha em branco.",
            contentBlocks = listOf(
                "00:00 - Decomposição da frase temática em 3 núcleos semânticos.",
                "05:30 - Escolha da dupla argumentativa clássica: Omissão Estatal (D1) + Invisibilidade Social/Educacional (D2).",
                "12:40 - Como encaixar a Constituição Federal de 1988 sem parecer decorado.",
                "18:15 - Checklist final de 4 minutos antes de passar a limpo."
            ),
            durationOrPages = "24 min • HD"
        ),
        LibraryAsset(
            id = "lib_pdf_1",
            type = "PDF_RESUMO",
            categoryTag = "Guia PDF • 12 págs",
            title = "Tabela Definitiva de 80 Conectivos por Função",
            description = "Conectivos de adição, oposição, causa, consequência, finalidade, conformidade e conclusão.",
            contentBlocks = listOf(
                "Prioridade / Relevância: Sob esse viés, Em primeira análise, Prima facie, É imperioso destacar que.",
                "Soma / Adição: Outrossim, Ademais, Paralelamente a isso, Em segunda análise, Soma-se a esse quadro.",
                "Oposição / Contraste: Contudo, Todavia, Entretanto, Em contrapartida, Não obstante.",
                "Conclusão / Síntese: Portanto, Destarte, Depreende-se, pois, que, Torna-se evidente, portanto."
            ),
            durationOrPages = "Resumo Interativo"
        ),
        LibraryAsset(
            id = "lib_mapa_1",
            type = "MAPA_MENTAL",
            categoryTag = "Mapa Mental Visual",
            title = "Árvore de Causas Coringas para Temas Sociais",
            description = "Mapeamento rápido de causas estruturais aplicáveis a 95% dos temas do ENEM e vestibulares.",
            contentBlocks = listOf(
                "Nó Central: Problemática Social no Brasil Contemporâneo",
                "Ramo 1 (Estado): Ineficiência fiscalizatória • Baixo investimento orçamentário • Lacuna legislativa",
                "Ramo 2 (Educação): Ausência de debate crítico escolar • Formação tecnicista acrítica",
                "Ramo 3 (Sociedade/Mídia): Banalização do mal (Hannah Arendt) • Individualismo líquido (Bauman) • Estigma histórico"
            ),
            durationOrPages = "Diagrama Estruturado"
        ),
        LibraryAsset(
            id = "lib_mod_1",
            type = "MODELO_PRONTO",
            categoryTag = "Esqueleto Adaptável",
            title = "Modelo Coringa 960+ com Constituição e Hannah Arendt",
            description = "Estrutura de alta coesão adaptável para eixos de Cidadania, Saúde, Educação, Tecnologia e Inclusão.",
            contentBlocks = listOf(
                "Introdução: Promulgada em 1988, a Constituição Cidadã assegura, em seu artigo 6º, direitos sociais plenos a todos os brasileiros. Contudo, no que tange a [TEMA], observa-se uma cidadania meramente formal, haja vista que [ARGUMENTO 1] e [ARGUMENTO 2] perpetuam esse entrave.",
                "D1: Em primeira análise, cumpre destacar que a insuficiência da atuação governamental agrava a problemática. Sob a ótica de Gilberto Dimenstein em 'Cidadãos de Papel', a legislação brasileira frequentemente carece de efetividade prática...",
                "D2: Ademais, a naturalização social do problema configura outro obstáculo severo. Segundo a filósofa Hannah Arendt, o conceito de 'Banalidade do Mal' ocorre quando a sociedade deixa de questionar injustiças cotidianas...",
                "Conclusão: Portanto, medidas urgentes são necessárias para reverter esse cenário. Para tanto, cabe ao [MINISTÉRIO], em parceria com as secretarias estaduais, implementar [AÇÃO], mediante [MEIO], a fim de efetivar a Carta Magna de 1988."
            ),
            durationOrPages = "Modelo 30 Linhas"
        ),
        LibraryAsset(
            id = "lib_frases_1",
            type = "FRASE_CORINGA",
            categoryTag = "Frases de Impacto",
            title = "30 Frases Coringa para Abertura e Fechamento de Parágrafos",
            description = "Estruturas sintáticas elegantes de nível avançado para elevar imediatamente as Competências 1, 3 e 4.",
            contentBlocks = listOf(
                "Abertura Crítica: 'Sob a égide da Constituição de 1988, a dignidade humana é erigida como pilar republicano; todavia, a persistência de [TEMA] evidencia um abismo entre a norma e a práxis.'",
                "Transição para Repertório: 'Essa conjuntura dialoga diretamente com a tese do sociólogo [AUTOR], segundo o qual...'",
                "Juízo de Valor no D1/D2: 'Tal disfunção não decorre do acaso, mas sim de uma negligência histórica que marginaliza parcelas vulneráveis da população.'",
                "Fechamento de Conclusão: 'Somente assim o Brasil superará a condição de inércia institucional e converterá garantias teóricas em realidade tangível.'"
            ),
            durationOrPages = "Guia Rápido"
        ),
        LibraryAsset(
            id = "lib_rep_1",
            type = "REPERTORIO_TEMA",
            categoryTag = "Banco de Repertórios & Leis",
            title = "Repertórios Legitimados e Marcos Legais (CF/88, LDB e ECA)",
            description = "Dispositivos da Constituição Federal de 1988, LDB (Lei 9.394/96), ECA (Lei 8.069/90), filósofos e sociólogos por eixo.",
            contentBlocks = listOf(
                "Marcos Legais Coringas: Constituição Federal de 1988 (Art. 5º, 6º, 205, 215, 225 e 227) • LDB - Lei de Diretrizes e Bases da Educação Nacional (Lei nº 9.394/1996, Art. 2º e 3º) • ECA - Estatuto da Criança e do Adolescente (Lei nº 8.069/1990, Art. 3º, 4º e 53).",
                "Educação & Infância: LDB (formação cidadã e igualdade de acesso) • ECA (Doutrina da Proteção Integral e prioridade absoluta) • Paulo Freire • Immanuel Kant.",
                "Tecnologia & Redes: Byung-Chul Han ('Sociedade do Cansaço') • Shoshana Zuboff ('Capitalismo de Vigilância') • Marco Civil da Internet.",
                "Saúde, Meio Ambiente & Inclusão: Conceito de Saúde da OMS • Ailton Krenak ('Ideias para Adiar o Fim do Mundo') • Art. 6º e 225 da CF/88 • Djamila Ribeiro • Milton Santos ('Cidadanias Mutiladas')."
            ),
            durationOrPages = "Leis + 10 Eixos"
        )
    )

    val exercises = listOf(
        ExerciseItem(
            id = "ex_obj_1",
            type = "OBJETIVA",
            competenciaTag = "Competência 5",
            title = "Identificação dos 5 Elementos na Proposta de Intervenção",
            prompt = "Analise o trecho: 'Portanto, cabe ao Ministério da Saúde — órgão federal responsável pela gestão sanitária — ampliar o atendimento psicológico no SUS, por meio da contratação de novos profissionais, a fim de reduzir os índices de ansiedade no país.' Qual segmento representa o DETALHAMENTO?",
            options = listOf(
                "cabe ao Ministério da Saúde",
                "— órgão federal responsável pela gestão sanitária —",
                "por meio da contratação de novos profissionais",
                "a fim de reduzir os índices de ansiedade no país"
            ),
            correctIndex = 1,
            explanation = "O trecho entre travessões ('— órgão federal responsável pela gestão sanitária —') funciona como um aposto explicativo que detalha o agente (Ministério da Saúde), garantindo o 5º elemento na Competência 5.",
            xpReward = 60
        ),
        ExerciseItem(
            id = "ex_con_1",
            type = "CONECTIVOS",
            competenciaTag = "Competência 4",
            title = "Uso de Operador Argumentativo de Concessão/Oposição",
            prompt = "Qual conectivo preenche corretamente a lacuna mantendo relação de concessão? '________ a legislação brasileira preveja punições severas contra crimes ambientais, o desmatamento ilegal ainda avança em biomas estratégicos.'",
            options = listOf(
                "Porquanto",
                "Conquanto",
                "Portanto",
                "Outrossim"
            ),
            correctIndex = 1,
            explanation = "'Conquanto' é uma conjunção subordinativa concessiva (equivalente a 'Embora' ou 'Ainda que'). 'Porquanto' é causal/explicativa e 'Outrossim' é aditiva.",
            xpReward = 50
        ),
        ExerciseItem(
            id = "ex_gram_1",
            type = "GRAMATICA",
            competenciaTag = "Competência 1",
            title = "Regência Verbal e Emprego do Acento Indicativo de Crase",
            prompt = "Assinale a alternativa que está integralmente de acordo com a norma-padrão exigida na Competência 1 do ENEM:",
            options = listOf(
                "A medida que o ensino remoto se expandiu, surgiram entraves referente à inclusão digital.",
                "À medida que o ensino remoto se expandiu, surgiram entraves referentes à inclusão digital.",
                "Na medida que o ensino remoto se expandiu, houve entraves referentes a inclusão digital.",
                "À medida em que o ensino remoto se expandiu, houveram entraves referentes à inclusão digital."
            ),
            correctIndex = 1,
            explanation = "A locução proporcional correta é 'À medida que' (com crase e sem 'em'), 'referentes' concorda no plural com 'entraves', e exige crase antes do substantivo feminino 'inclusão'.",
            xpReward = 60
        ),
        ExerciseItem(
            id = "ex_rep_1",
            type = "REPERTORIO",
            competenciaTag = "Competência 2",
            title = "Aplicação Produtiva de Repertório Sociocultural",
            prompt = "Para um tema sobre 'Efeitos da hiperconectividade e esgotamento mental dos jovens', qual repertório abaixo apresenta maior pertinência direta e legitimidade filosófica?",
            options = listOf(
                "A Revolução Industrial do século XVIII apenas como menção histórica genérica sem relação com saúde mental.",
                "O conceito de 'Sociedade do Cansaço', do filósofo Byung-Chul Han, que analisa a pressão por desempenho e hiperexposição digital.",
                "Um ditado popular sem autoria conhecida sobre o uso de celulares.",
                "A cópia literal de um parágrafo do texto motivador da prova."
            ),
            correctIndex = 1,
            explanation = "A obra 'Sociedade do Cansaço', de Byung-Chul Han, é um repertório filosófico legitimado, pertinente ao eixo de tecnologia/saúde mental e permite articulação produtiva.",
            xpReward = 60
        ),
        ExerciseItem(
            id = "ex_arg_1",
            type = "ARGUMENTACAO",
            competenciaTag = "Competência 3",
            title = "Eliminação de Lacunas Argumentativas",
            prompt = "Por que o trecho 'A evasão escolar ocorre porque muitos jovens deixam a escola precocemente' é penalizado na Competência 3?",
            options = listOf(
                "Porque utiliza vocabulário excessivamente formal.",
                "Porque apresenta raciocínio tautológico (circular), repetindo a mesma ideia sem explicar a causa real (ex.: necessidade de trabalho precoce ou déficit pedagógico).",
                "Porque está escrito na terceira pessoa do discurso.",
                "Porque contém mais de três linhas."
            ),
            correctIndex = 1,
            explanation = "Dizer que a evasão ocorre porque os jovens saem da escola é circular (tautologia). Para pontuar 200 na C3, é preciso explicitar causas reais como vulnerabilidade socioeconômica ou falta de infraestrutura.",
            xpReward = 65
        ),
        ExerciseItem(
            id = "ex_disc_1",
            type = "DISCURSIVA",
            competenciaTag = "Prática Discursiva • IA",
            title = "Redija um Parágrafo de Introdução ou Proposta de Intervenção",
            prompt = "Escreva um parágrafo de 4 a 7 linhas sobre o tema 'Democratização do acesso à leitura e bibliotecas no Brasil', contendo repertório sociocultural ou os 5 elementos da Competência 5. Nossa IA avaliará sua resposta na hora!",
            options = emptyList(),
            explanation = "Critérios avaliados: presença de conectivos, norma-padrão, repertório ou 5 elementos de intervenção e vocabulário crítico.",
            referenceKeywords = listOf("leitura", "educação", "Estado", "Ministério", "Constituição", "acesso", "escola", "cultura"),
            xpReward = 120
        )
    )
}
