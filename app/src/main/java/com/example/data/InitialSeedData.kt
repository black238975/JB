package com.example.data

data class MedalInfo(
    val id: String,
    val title: String,
    val description: String,
    val xpRequired: Int,
    val iconName: String
)

data class RankingStudent(
    val rank: Int,
    val name: String,
    val stateCode: String,
    val avgScore: Int,
    val xp: Int,
    val badge: String
)

data class CertificateInfo(
    val id: String,
    val title: String,
    val hours: Int,
    val requirementDescription: String,
    val isUnlocked: Boolean
)

object InitialSeedData {

    val themeCategories = listOf(
        "Educação",
        "Tecnologia",
        "Saúde",
        "Meio Ambiente",
        "Política",
        "Cultura",
        "Redes Sociais",
        "Economia",
        "Segurança",
        "Inclusão Social",
        "Direitos Humanos",
        "Trabalho & Previdência",
        "Mobilidade Urbana",
        "Ciência & Bioética",
        "Infância & Envelhecimento",
        "Esporte & Lazer",
        "Segurança Alimentar",
        "Cidadania Digital"
    )

    val initialThemes = listOf(
        EssayThemeEntity(
            title = "Desafios para a valorização do magistério e combate à evasão escolar no Brasil",
            category = "Educação",
            examType = "ENEM",
            motivatingText = "TEXTO I: Segundo o Censo Escolar do INEP, mais de 480 mil estudantes abandonaram o Ensino Médio na rede pública no último biênio. Paralelamente, pesquisas da OCDE apontam que o piso salarial e as condições de infraestrutura docente no Brasil permanecem abaixo da média internacional.\nTEXTO II: Para Paulo Freire, a educação não transforma o mundo isoladamente, mas muda as pessoas que vão transformar o mundo.",
            proposalPrompt = "A partir da leitura dos textos motivadores e com base nos conhecimentos construídos ao longo de sua formação, redija texto dissertativo-argumentativo em modalidade escrita formal da língua portuguesa sobre o tema 'Desafios para a valorização do magistério e combate à evasão escolar no Brasil', apresentando proposta de intervenção que respeite os direitos humanos.",
            statisticalData = "• Censo Escolar (INEP): 5,9% de taxa de abandono no Ensino Médio público.\n• Dados Todos Pela Educação: Apenas 33% dos jovens de baixa renda concluem a educação básica na idade adequada.",
            repertoireSuggestions = "• Paulo Freire ('Pedagogia da Autonomia')\n• Art. 205 da Constituição Federal de 1988 (Educação como direito de todos e dever do Estado)\n• Pierre Bourdieu (Capital Cultural e reprodução das desigualdades)",
            guidingQuestions = "1. De que modo a precarização da carreira docente impacta o engajamento dos estudantes?\n2. Como fatores socioeconômicos forçam jovens ao trabalho precoce em detrimento da escola?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Impactos da Inteligência Artificial Generativa na ética profissional e no mercado de trabalho",
            category = "Tecnologia",
            examType = "ENEM",
            motivatingText = "TEXTO I: O avanço acelerado dos modelos de Inteligência Artificial generativa reconfigurou setores criativos, jurídicos e industriais. Enquanto a produtividade cresce, ampliam-se os debates sobre substituição de postos formais e desinformação sintética.\nTEXTO II: Relatório do Fórum Econômico Mundial estima que 44% das habilidades essenciais dos trabalhadores serão transformadas nos próximos cinco anos.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'Impactos da Inteligência Artificial Generativa na ética profissional e no mercado de trabalho'.",
            statisticalData = "• Fórum Econômico Mundial: 85 milhões de empregos podem ser remodelados pela automação.\n• TIC Domicílios: 28% dos trabalhadores brasileiros ainda carecem de qualificação digital básica.",
            repertoireSuggestions = "• Klaus Schwab ('A Quarta Revolução Industrial')\n• Hans Jonas ('O Princípio Responsabilidade' — ética voltada para o futuro tecnológico)\n• Milton Santos ('Técnica, Espaço, Tempo')",
            guidingQuestions = "1. Como equilibrar inovação tecnológica e proteção social ao trabalhador?\n2. Qual o papel do Estado e das universidades na requalificação digital?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "O estigma associado à saúde mental e ao esgotamento laboral na sociedade contemporânea",
            category = "Saúde",
            examType = "ENEM",
            motivatingText = "TEXTO I: A Organização Mundial da Saúde (OMS) incluiu a Síndrome de Burnout na Classificação Internacional de Doenças como fenômeno ocupacional resultante do estresse crônico.\nTEXTO II: No Brasil, os afastamentos por transtornos ansiosos e depressivos cresceram 38% segundo o Ministério da Previdência Social.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'O estigma associado à saúde mental e ao esgotamento laboral na sociedade contemporânea'.",
            statisticalData = "• OMS: O Brasil possui a maior prevalência de transtornos de ansiedade da América Latina (9,3% da população).\n• Previdência Social: Mais de 288 mil benefícios concedidos por transtornos mentais no último ano.",
            repertoireSuggestions = "• Byung-Chul Han ('Sociedade do Cansaço' — autocoerção pela produtividade)\n• Conceito biopsicossocial de saúde da OMS\n• Lei 10.216/2001 (Reforma Psiquiátrica no Brasil)",
            guidingQuestions = "1. Por que o sofrimento psíquico ainda é visto como fraqueza individual nas empresas?\n2. Como fortalecer a Rede de Atenção Psicossocial (RAPS/CAPS) no SUS?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Justiça climática e proteção às populações vulneráveis diante de eventos extremos no Brasil",
            category = "Meio Ambiente",
            examType = "ENEM",
            motivatingText = "TEXTO I: Enchentes severas no Sul e secas históricas na Amazônia evidenciam que as mudanças climáticas atingem de maneira desproporcional moradores de periferias, comunidades ribeirinhas e povos originários.\nTEXTO II: O conceito de racismo ambiental denuncia que a falta de saneamento e moradia segura expõe grupos vulneráveis aos maiores riscos ecológicos.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'Justiça climática e proteção às populações vulneráveis diante de eventos extremos no Brasil'.",
            statisticalData = "• CEMADEN: Mais de 8,2 milhões de brasileiros vivem em áreas de risco geológico ou hidrológico.\n• Painel IPCC/ONU: Eventos climáticos extremos triplicaram em frequência nas últimas três décadas.",
            repertoireSuggestions = "• Ailton Krenak ('Ideias para Adiar o Fim do Mundo')\n• Artigo 225 da Constituição de 1988 (Direito ao meio ambiente ecologicamente equilibrado)\n• Ulrich Beck ('Sociedade de Risco')",
            guidingQuestions = "1. Por que os impactos ambientais não afetam todas as classes sociais de modo igual?\n2. Quais políticas urbanas de prevenção e adaptação climática são urgentes?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "O fortalecimento da cidadania participativa e o combate à desinformação eleitoral",
            category = "Política",
            examType = "Concurso",
            motivatingText = "TEXTO I: A democracia contemporânea enfrenta o desafio das câmaras de eco digitais e da disseminação massiva de notícias fraudulentas que corroem a confiança nas instituições republicanas.",
            proposalPrompt = "Disserte sobre 'O fortalecimento da cidadania participativa e o combate à desinformação eleitoral'.",
            statisticalData = "• Instituto DataSenado: 79% dos brasileiros afirmam já ter recebido notícias falsas sobre processos políticos.",
            repertoireSuggestions = "• Jürgen Habermas ('Esfera Pública' e agir comunicativo)\n• Hannah Arendt ('Verdade e Política')\n• Art. 1º, parágrafo único da CF/88 (Todo o poder emana do povo)",
            guidingQuestions = "1. Como promover educação midiática e política sem ferir a liberdade de expressão?\n2. De que modo a participação cidadã vai além do voto periódico?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Preservação do patrimônio cultural imaterial e valorização das matrizes afro-brasileiras e indígenas",
            category = "Cultura",
            examType = "ENEM",
            motivatingText = "TEXTO I: O patrimônio imaterial compreende saberes, celebrações, formas de expressão e tradições orais que forjam a identidade nacional. Contudo, o apagamento histórico ameaça mestres da cultura popular.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'Preservação do patrimônio cultural imaterial e valorização das matrizes afro-brasileiras e indígenas'.",
            statisticalData = "• IPHAN: Mais de 50 bens culturais imateriais registrados no Brasil demandam planos contínuos de salvaguarda.\n• IBGE: Mais de 65% dos municípios brasileiros carecem de museus ou centros culturais ativos.",
            repertoireSuggestions = "• Darcy Ribeiro ('O Povo Brasileiro')\n• Stuart Hall ('A Identidade Cultural na Pós-Modernidade')\n• Lei 10.639/03 e Lei 11.645/08 (Ensino de história e cultura afro-brasileira e indígena)",
            guidingQuestions = "1. Por que a memória cultural é essencial para o sentimento de pertencimento?\n2. Como escolas e o IPHAN podem integrar mestres populares ao currículo?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "A ditadura dos algoritmos e a adultização precoce de crianças nas redes sociais",
            category = "Redes Sociais",
            examType = "Vestibular",
            motivatingText = "TEXTO I: Crianças e adolescentes passam em média mais de 5 horas diárias diante de telas, expostos a padrões estéticos irreais, hiperconsumo e algoritmos desenhados para retenção máxima de atenção.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'A ditadura dos algoritmos e a adultização precoce de crianças nas redes sociais'.",
            statisticalData = "• Pesquisa TIC Kids Online Brasil: 93% da população entre 9 e 17 anos é usuária de internet no país.",
            repertoireSuggestions = "• Neil Postman ('O Desaparecimento da Infância')\n• Estatuto da Criança e do Adolescente (ECA - Lei 8.069/1990)\n• Shoshana Zuboff ('A Era do Capitalismo de Vigilância')",
            guidingQuestions = "1. Qual a corresponsabilidade das plataformas digitais e das famílias na proteção infantil?\n2. Como a exposição precoce afeta o desenvolvimento cognitivo e emocional?",
            isExclusivePremium = true
        ),
        EssayThemeEntity(
            title = "Educação financeira e o combate ao superendividamento das famílias brasileiras",
            category = "Economia",
            examType = "ENEM",
            motivatingText = "TEXTO I: A facilidade de acesso ao crédito digital, somada às apostas online e à ausência de formação financeira básica, levou milhões de lares brasileiros à inadimplência crônica.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'Educação financeira e o combate ao superendividamento das famílias brasileiras'.",
            statisticalData = "• CNC (Pesquisa de Endividamento): Cerca de 78% das famílias brasileiras possuem dívidas ativas, e quase 30% estão com contas em atraso.",
            repertoireSuggestions = "• Zygmunt Bauman ('Vida para Consumo')\n• Lei do Superendividamento (Lei 14.181/2021 - atualização do Código de Defesa do Consumidor)\n• Amartya Sen ('Desenvolvimento como Liberdade')",
            guidingQuestions = "1. Como a publicidade predatória de crédito fácil afeta cidadãos vulneráveis?\n2. Qual a importância da educação financeira crítica na BNCC?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Caminhos para a redução da letalidade e humanização do sistema socioeducativo e prisional",
            category = "Segurança",
            examType = "Concurso",
            motivatingText = "TEXTO I: A segurança pública eficiente no Estado Democrático de Direito exige inteligência investigativa, prevenção primária nas periferias e políticas efetivas de ressocialização.",
            proposalPrompt = "Disserte sobre 'Caminhos para a redução da letalidade e humanização do sistema socioeducativo e prisional'.",
            statisticalData = "• Anuário Brasileiro de Segurança Pública: O Brasil possui a terceira maior população carcerária do mundo, com déficit de vagas e baixa oferta de estudo prisional.",
            repertoireSuggestions = "• Michel Foucault ('Vigiar e Punir')\n• Cesare Beccaria ('Dos Delitos e das Penas')\n• ADPF 347 do STF (Reconhecimento do 'Estado de Coisas Inconstitucional' no sistema carcerário)",
            guidingQuestions = "1. Por que o encarceramento sem educação e trabalho falha em reduzir a reincidência?\n2. Como aliar inteligência policial à garantia dos direitos fundamentais?",
            isExclusivePremium = true
        ),
        EssayThemeEntity(
            title = "A inclusão plena de pessoas com deficiência (PcD) e neurodivergentes na educação e no trabalho",
            category = "Inclusão Social",
            examType = "ENEM",
            motivatingText = "TEXTO I: O capacitismo estrutural manifesta-se tanto na ausência de rampas, intérpretes de Libras e materiais em Braille quanto na subestimação intelectual de cidadãos com deficiência ou neurodivergentes.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'A inclusão plena de pessoas com deficiência (PcD) e neurodivergentes na educação e no trabalho'.",
            statisticalData = "• IBGE (PNAD Contínua): OBrasil tem 18,6 milhões de pessoas com deficiência; a taxa de participação desse grupo no mercado formal é menos da metade da registrada entre pessoas sem deficiência.",
            repertoireSuggestions = "• Estatuto da Pessoa com Deficiência (Lei Brasileira de Inclusão - Lei 13.146/2015)\n• Boaventura de Sousa Santos (Imperativo intercultural da igualdade e diferença)\n• Axel Honneth ('Luta por Reconhecimento')",
            guidingQuestions = "1. Qual a diferença entre mera integração física e inclusão efetiva?\n2. Como combater o preconceito capacitista nas escolas e empresas?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "O combate ao trabalho análogo à escravidão e a garantia da dignidade humana no século XXI",
            category = "Direitos Humanos",
            examType = "ENEM",
            motivatingText = "TEXTO I: Mais de 3 mil trabalhadores foram resgatados em condições análogas à escravidão no último ano no Brasil, especialmente em cadeias agrícolas, têxteis e de serviços domésticos.\nTEXTO II: A Declaração Universal dos Direitos Humanos (ONU, 1948) proíbe em seu Artigo 4º qualquer forma de servidão degradante.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'O combate ao trabalho análogo à escravidão e a garantia da dignidade humana no século XXI'.",
            statisticalData = "• Ministério do Trabalho e Emprego: Mais de 63 mil trabalhadores resgatados desde 1995 pelos grupos móveis de fiscalização.",
            repertoireSuggestions = "• Declaração Universal dos Direitos Humanos (Art. 4º)\n• Hannah Arendt ('A Condição Humana')\n• Música 'A Carne' (Elza Soares) e 'Construção' (Chico Buarque)",
            guidingQuestions = "1. Como a vulnerabilidade socioeconômica facilita o aliciamento de trabalhadores?\n2. Qual o papel da fiscalização trabalhista e da responsabilização das cadeias produtivas?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Os impactos da uberização e da informalidade na proteção previdenciária do trabalhador brasileiro",
            category = "Trabalho & Previdência",
            examType = "ENEM",
            motivatingText = "TEXTO I: O crescimento do trabalho mediado por plataformas digitais (entregadores e motoristas de aplicativo) trouxe flexibilidade aparente, mas expôs milhões de brasileiros a jornadas superiores a 12 horas sem cobertura previdenciária ou descanso remunerado.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'Os impactos da uberização e da informalidade na proteção previdenciária do trabalhador brasileiro'.",
            statisticalData = "• IBGE: Mais de 38 milhões de brasileiros atuam na informalidade, e mais de 1,6 milhão trabalham diretamente por aplicativos.",
            repertoireSuggestions = "• Ricardo Antunes ('O Privilégio da Servidão')\n• Filme 'Tempos Modernos' (Charlie Chaplin)\n• Art. 7º da Constituição Federal de 1988 (Direitos dos trabalhadores urbanos e rurais)",
            guidingQuestions = "1. Como conciliar a inovação dos aplicativos com a proteção social e previdenciária?\n2. Quais os riscos do esgotamento físico de trabalhadores de plataformas?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Desafios da mobilidade urbana sustentável e o direito à cidade nas metrópoles brasileiras",
            category = "Mobilidade Urbana",
            examType = "ENEM",
            motivatingText = "TEXTO I: O modelo rodoviarista centrado no transporte individual motorizado resultou em congestionamentos crônicos, tarifas elevadas de ônibus e poluição atmosférica que penalizam sobretudo os moradores das periferias.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'Desafios da mobilidade urbana sustentável e o direito à cidade nas metrópoles brasileiras'.",
            statisticalData = "• IPEA: Trabalhadores de baixa renda gastam em média mais de 2 horas diárias no deslocamento casa-trabalho nas capitais.",
            repertoireSuggestions = "• Milton Santos ('O Espaço do Cidadão' e 'Cidadanias Mutiladas')\n• David Harvey ('O Direito à Cidade')\n• Lei da Política Nacional de Mobilidade Urbana (Lei 12.587/2012)",
            guidingQuestions = "1. De que forma a precariedade do transporte público segrega a população periférica?\n2. Como incentivar o transporte coletivo limpo e ciclovias integradas?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Limites bioéticos da manipulação genética e democratização dos avanços biomédicos",
            category = "Ciência & Bioética",
            examType = "Vestibular",
            motivatingText = "TEXTO I: Tecnologias de edição genética como o CRISPR e terapias gênicas abrem caminho para a cura de doenças raras, mas despertam debates éticos sobre eugenia e elitização do acesso biotecnológico.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'Limites bioéticos da manipulação genética e democratização dos avanços biomédicos'.",
            statisticalData = "• OMS: Terapias gênicas de ponta ainda possuem custos milionários, inacessíveis a sistemas públicos de países em desenvolvimento.",
            repertoireSuggestions = "• Hans Jonas ('O Princípio Responsabilidade')\n• Aldous Huxley ('Admirável Mundo Novo')\n• Jürgen Habermas ('O Futuro da Natureza Humana')",
            guidingQuestions = "1. Onde termina a medicina terapêutica e começa o risco de eugenia social?\n2. Como garantir que a ciência beneficie toda a população no SUS?",
            isExclusivePremium = true
        ),
        EssayThemeEntity(
            title = "O envelhecimento populacional acelerado e o combate ao etarismo no Brasil",
            category = "Infância & Envelhecimento",
            examType = "ENEM",
            motivatingText = "TEXTO I: Dados do Censo Demográfico do IBGE demonstram que a população com 60 anos ou mais cresceu 56% em doze anos, exigindo adaptação urgente nas cidades, na saúde e no mercado de trabalho.\nTEXTO II: O etarismo (preconceito por idade) marginaliza idosos e desvaloriza sua experiência social.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'O envelhecimento populacional acelerado e o combate ao etarismo no Brasil'.",
            statisticalData = "• IBGE Censo: Mais de 32 milhões de brasileiros são idosos (15,8% da população nacional).",
            repertoireSuggestions = "• Simone de Beauvoir ('A Velhice')\n• Estatuto da Pessoa Idosa (Lei 10.741/2003)\n• Art. 230 da Constituição Federal de 1988",
            guidingQuestions = "1. Por que a sociedade capitalista tende a descartar o cidadão idoso?\n2. Como preparar o sistema de saúde e o espaço urbano para a longevidade ativa?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "O esporte e o lazer como ferramentas de inclusão social e formação cidadã da juventude",
            category = "Esporte & Lazer",
            examType = "ENEM",
            motivatingText = "TEXTO I: Embora o Brasil seja reconhecido mundialmente por talentos esportivos, a maioria das escolas públicas carece de quadras cobertas e programas contínuos de iniciação desportiva comunitária.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'O esporte e o lazer como ferramentas de inclusão social e formação cidadã da juventude'.",
            statisticalData = "• Censo Escolar INEP: Mais de 40% das escolas públicas brasileiras não possuem infraestrutura esportiva adequada.",
            repertoireSuggestions = "• Artigo 6º e Artigo 217 da Constituição Federal de 1988 (Dever do Estado de fomentar práticas desportivas)\n• Nelson Mandela ('O esporte tem o poder de transformar o mundo')\n• Pierre Bourdieu (Sociologia do esporte)",
            guidingQuestions = "1. Como o acesso gratuito ao esporte previne a evasão escolar e a vulnerabilidade juvenil?\n2. Qual a importância de democratizar o lazer nas periferias?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Caminhos para erradicar a insegurança alimentar e o desperdício de alimentos no Brasil",
            category = "Segurança Alimentar",
            examType = "ENEM",
            motivatingText = "TEXTO I: O paradoxo de um dos maiores exportadores agrícolas do planeta conviver com milhões de cidadãos em situação de insegurança alimentar revela falhas logísticas, desperdício na distribuição e desigualdade de renda.",
            proposalPrompt = "Redija texto dissertativo-argumentativo sobre 'Caminhos para erradicar a insegurança alimentar e o desperdício de alimentos no Brasil'.",
            statisticalData = "• Rede PENSSAN / IBGE: Milhões de domicílios brasileiros ainda enfrentam algum grau de insegurança alimentar, enquanto cerca de 30% da produção hortifrúti é desperdiçada.",
            repertoireSuggestions = "• Josué de Castro ('Geografia da Fome')\n• Documentário 'Ilha das Flores' (Jorge Furtado)\n• Carolina Maria de Jesus ('Quarto de Despejo')",
            guidingQuestions = "1. Por que a fome no Brasil é um problema de acesso e distribuição, e não de falta de produção?\n2. Como fortalecer a agricultura familiar e os bancos públicos de alimentos?",
            isExclusivePremium = false
        ),
        EssayThemeEntity(
            title = "Proteção de dados pessoais, golpes virtuais e educação para a cidadania digital no Brasil",
            category = "Cidadania Digital",
            examType = "Concurso",
            motivatingText = "TEXTO I: A digitalização dos serviços bancários e governamentais ampliou a comodidade, mas expôs milhões de brasileiros — especialmente idosos e cidadãos com baixo letramento digital — a vazamentos de dados, deepfakes e estelionato eletrônico.",
            proposalPrompt = "Disserte sobre 'Proteção de dados pessoais, golpes virtuais e educação para a cidadania digital no Brasil'.",
            statisticalData = "• Anuário Brasileiro de Segurança Pública: Os crimes de estelionato por meio virtual cresceram mais de 65% nos últimos anos.",
            repertoireSuggestions = "• Lei Geral de Proteção de Dados (LGPD - Lei 13.709/2018)\n• Marco Civil da Internet (Lei 12.965/2014)\n• George Orwell ('1984')",
            guidingQuestions = "1. Como fiscalizar empresas e plataformas contra o vazamento de dados sensíveis?\n2. Qual a urgência do letramento digital para proteger cidadãos vulneráveis?",
            isExclusivePremium = false
        )
    )

    val initialEssays = listOf(
        EssaySubmissionEntity(
            id = 1,
            themeId = 1,
            themeTitle = "Desafios para a valorização do magistério e combate à evasão escolar no Brasil",
            category = "Educação",
            examType = "ENEM",
            title = "A Educação entre a Norma e a Evasão",
            content = "Promulgada em 1988, a Constituição Cidadã assegura, em seu artigo 205, a educação como direito de todos e dever do Estado. Contudo, na realidade brasileira contemporânea, observa-se um grave entrave para a plenitude desse preceito: a desvalorização do magistério somada à persistente evasão escolar. Esse cenário adverso decorre tanto da negligência governamental na infraestrutura de ensino quanto da vulnerabilidade socioeconômica que afasta jovens das salas de aula.\n\nEm primeira análise, cabe pontuar que o baixo investimento na carreira docente fragiliza a qualidade pedagógica. Sob a ótica de Gilberto Dimenstein em 'Cidadãos de Papel', o Brasil possui leis avançadas que muitas vezes não se concretizam na prática cotidiana. Nesse sentido, salários defasados e escolas sem laboratórios desestimulam professores e alunos.\n\nAdemais, a desigualdade social empurra milhares de adolescentes para o trabalho informal precoce. Conforme o sociólogo Pierre Bourdieu, a escola frequentemente reproduz privilégios quando ignora as carências materiais dos estudantes de baixa renda. Assim, sem auxílio estudantil efetivo, o abandono torna-se inevitável.\n\nPortanto, cabe ao Ministério da Educação — órgão máximo do ensino nacional — ampliar o piso salarial docente e expandir programas de bolsa-permanência no Ensino Médio, por meio de repasses diretos do FUNDEB e acompanhamento psicopedagógico nas escolas, a fim de erradicar a evasão e concretizar o ideal da Constituição de 1988.",
            wordCount = 212,
            timeSpentMinutes = 52,
            isSimulatedExam = false,
            timestamp = System.currentTimeMillis() - 86_400_000L * 5,
            totalScore = 880,
            c1Score = 160,
            c2Score = 200,
            c3Score = 160,
            c4Score = 160,
            c5Score = 200,
            errorsFound = "Período final do 2º parágrafo um pouco curto; pode-se aprofundar o desdobramento crítico.|Repetição sutil da estrutura 'observa-se / cabe pontuar' que pode ser enriquecida.",
            strengths = "Repertórios socioculturais altamente legítimos e produtivos (CF/88, Dimenstein e Bourdieu).|Proposta de intervenção impecável com os 5 elementos válidos e circuito fechado com a introdução.",
            weaknesses = "O fechamento do D1 e do D2 pode explorar mais o vínculo explícito entre valorização docente e retenção do aluno.",
            improvements = "Inserir mais um conectivo intraparágrafo no D1 (ex.: 'Por conseguinte') para garantir 200 pontos na Competência 4.|Expandir a explicação causal no final do D2 para blindar os 200 pontos na Competência 3.",
            highlightedExcerpts = "Promulgada em 1988, a Constituição Cidadã assegura::FORTE::Excelente abertura histórica e constitucional.|Nesse sentido, salários defasados e escolas sem laboratórios desestimulam professores e alunos.::MELHORIA::Expanda o impacto direto dessa precarização no índice de abandono escolar.|Portanto, cabe ao Ministério da Educação — órgão máximo do ensino nacional —::FORTE::Agente e detalhamento entre travessões perfeitos na C5.",
            evolutionPlan = "1. Praticar conectivos intraparágrafos de consequência (C4).|2. Aprofundar o período de fechamento crítico do D1 e D2 (C3).|3. Reescrever esta redação buscando a nota 960+.",
            teacherFeedback = "Excelente projeto de texto, Eduarda! Na reescrita, aprofunde o período conclusivo do D1 e do D2. — Prof. João Batista",
            status = "REVISADA_PROFESSOR",
            versionNumber = 1,
            parentEssayId = null
        ),
        EssaySubmissionEntity(
            id = 2,
            themeId = 1,
            themeTitle = "Desafios para a valorização do magistério e combate à evasão escolar no Brasil",
            category = "Educação",
            examType = "ENEM",
            title = "A Educação entre a Norma e a Evasão (Versão Reescrita)",
            content = "Promulgada em 1988, a Constituição Cidadã assegura, em seu artigo 205, a educação como direito universal e dever do Estado. Todavia, no Brasil hodierno, verifica-se um abismo entre a garantia formal e a realidade escolar, haja vista os desafios para a valorização do magistério e o combate à evasão estudantil. Essa problemática consolida-se não apenas pela insuficiência de investimentos estatais na carreira docente, mas também pela vulnerabilidade socioeconômica que expulsa jovens do ensino básico.\n\nEm primeira análise, é imperioso destacar que a precarização do trabalho pedagógico compromete o vínculo do estudante com a escola. Sob a ótica de Gilberto Dimenstein, na obra 'Cidadãos de Papel', a legislação brasileira frequentemente restringe-se à dimensão teórica. Sob esse viés, remunerações defasadas, jornadas exaustivas e carência de infraestrutura laboratorial reduzem o potencial dinâmico das aulas; por conseguinte, a escola perde atratividade e agrava os índices de abandono.\n\nOutrossim, a desigualdade material impõe a necessidade de ingresso precoce no mercado informal. Conforme postula o sociólogo Pierre Bourdieu, o sistema de ensino reproduz exclusões quando desconsidera a bagagem socioeconômica discente. Dessa maneira, milhares de adolescentes periféricos veem-se forçados a abandonar os estudos para complementar a renda familiar, perpetuando um ciclo intergeracional de pobreza.\n\nPortanto, urge que o Ministério da Educação — pasta responsável pela diretriz educacional do país — efetive o piso salarial docente com planos de carreira atrativos e amplie o programa de auxílio financeiro estudantil, mediante verbas vinculadas do FUNDEB e busca ativa escolar com apoio psicopedagógico, a fim de extinguir a evasão e consolidar a cidadania prevista na Carta Magna de 1988.",
            wordCount = 268,
            timeSpentMinutes = 40,
            isSimulatedExam = false,
            timestamp = System.currentTimeMillis() - 86_400_000L * 2,
            totalScore = 960,
            c1Score = 200,
            c2Score = 200,
            c3Score = 180,
            c4Score = 180,
            c5Score = 200,
            errorsFound = "Nenhum desvio gramatical relevante detectado.",
            strengths = "Evolução expressiva (+80 pontos) em relação à versão 1!|Uso exemplar de operadores argumentativos ('Todavia', 'Sob esse viés', 'por conseguinte', 'Outrossim', 'Dessa maneira', 'Portanto').|Argumentação madura e fechamento de parágrafos sem lacunas.",
            weaknesses = "Pequena margem de refinamento autoral no D2 para atingir 1000 pontos.",
            improvements = "Manter o padrão de períodos articulados por subordinação nos próximos simulados.",
            highlightedExcerpts = "por conseguinte, a escola perde atratividade e agrava os índices de abandono.::FORTE::Perfeito acréscimo de conectivo conclusivo intraparágrafo e desfecho crítico!|Outrossim, a desigualdade material impõe a necessidade::FORTE::Transição interparágrafo de alto nível.",
            evolutionPlan = "1. Realizar um simulado cronometrado sobre Saúde ou Meio Ambiente.|2. Manter a média acima de 920 pontos.",
            teacherFeedback = "Parabéns pela reescrita! Você corrigiu todas as lacunas apontadas na versão 1 e subiu de 880 para 960 pontos. — Prof. João Batista",
            status = "REVISADA_PROFESSOR",
            versionNumber = 2,
            parentEssayId = 1
        ),
        EssaySubmissionEntity(
            id = 3,
            themeId = 3,
            themeTitle = "O estigma associado à saúde mental e ao esgotamento laboral na sociedade contemporânea",
            category = "Saúde",
            examType = "ENEM",
            title = "A Sociedade do Desempenho e a Invisibilidade Psíquica",
            content = "Na obra 'Sociedade do Cansaço', o filósofo sul-coreano Byung-Chul Han analisa como o sujeito contemporâneo tornou-se refém de uma cobrança incessante por produtividade, gerando esgotamento psíquico generalizado. Fora da teoria filosófica, a realidade brasileira reflete esse diagnóstico, uma vez que o estigma associado à saúde mental e ao Burnout laboral permanece alarmante. Tal conjuntura deve-se à cultura corporativa predatória e à insuficiência da rede pública de assistência psicossocial.\n\nEm primeira análise, cumpre ressaltar que a romantização do excesso de trabalho silencia o sofrimento mental. Segundo a Organização Mundial da Saúde, o bem-estar humano exige equilíbrio físico, mental e social; entretanto, o mercado frequentemente associa pausas terapêuticas à improdutividade. Consequentemente, trabalhadores ocultam quadros de ansiedade e depressão por receio de demissão ou preconceito.\n\nAdemais, a escassez de investimentos na Rede de Atenção Psicossocial dificulta o tratamento precoce. Embora a Lei 10.216/2001 tenha instituído a Reforma Psiquiátrica pautada no atendimento humanizado nos CAPS, muitas regiões brasileiras carecem de psicólogos e psiquiatras no SUS. Desse modo, a população vulnerável fica desassistida diante do adoecimento emocional.\n\nPortanto, cabe ao Ministério da Saúde, em ação conjunta com o Ministério do Trabalho e Emprego — órgãos gestores do bem-estar sanitário e laboral —, expandir os Centros de Atenção Psicossocial e fiscalizar programas de saúde emocional nas empresas, por meio de repasses orçamentários federais e campanhas educativas na mídia, com o intuito de desconstruir o estigma mental e superar a 'Sociedade do Cansaço' denunciada por Han.",
            wordCount = 246,
            timeSpentMinutes = 48,
            isSimulatedExam = true,
            timestamp = System.currentTimeMillis() - 36_000_000L,
            totalScore = 920,
            c1Score = 180,
            c2Score = 200,
            c3Score = 180,
            c4Score = 160,
            c5Score = 200,
            errorsFound = "Apenas 1 oscilação leve de paralelismo nominal no 3º parágrafo.",
            strengths = "Circuito fechado brilhante com Byung-Chul Han na introdução e na conclusão.|Excelente uso de repertório institucional (OMS e Lei 10.216/2001).",
            weaknesses = "Competência 4 pode atingir 200 pts variando mais o conectivo 'Desse modo'.",
            improvements = "Substituir 'Desse modo' por 'Sob essa ótica' ou 'Em decorrência dessa lacuna' para maior precisão semântica.",
            highlightedExcerpts = "Na obra 'Sociedade do Cansaço', o filósofo sul-coreano Byung-Chul Han::FORTE::Repertório filosófico contemporâneo de altíssimo impacto.|com o intuito de desconstruir o estigma mental e superar a 'Sociedade do Cansaço'::FORTE::Fechamento cíclico nota 1000.",
            evolutionPlan = "1. Revisar tabela de conectivos intraparágrafos da Competência 4.|2. Praticar mais um exercício de regência e paralelismo (C1).",
            teacherFeedback = "",
            status = "CORRIGIDA_IA",
            versionNumber = 1,
            parentEssayId = null
        )
    )

    val initialStudyTasks = listOf(
        StudyTaskEntity(
            title = "Dominar Conectivos Intraparágrafos (C4)",
            description = "IA detectou que sua Competência 4 está em 160-180 pts. Estude a tabela de operadores de consequência e concessão.",
            category = "Competência 4",
            competenciaFocus = "C4 • Coesão",
            durationMinutes = 15,
            xpReward = 80,
            isCompleted = true,
            isAiRecommended = true,
            dayLabel = "Hoje • Manhã"
        ),
        StudyTaskEntity(
            title = "Resolver 3 Exercícios de Crase e Regência (C1)",
            description = "Treino rápido de gramática aplicada para blindar os 200 pontos na modalidade formal.",
            category = "Exercício",
            competenciaFocus = "C1 • Norma Padrão",
            durationMinutes = 12,
            xpReward = 90,
            isCompleted = false,
            isAiRecommended = true,
            dayLabel = "Hoje • Tarde"
        ),
        StudyTaskEntity(
            title = "Aprofundamento de Fechamento Crítico no D1 e D2",
            description = "Revise a aula de Projeto de Texto para elevar sua Competência 3 de 180 para 200 pontos.",
            category = "Competência 3",
            competenciaFocus = "C3 • Argumentação",
            durationMinutes = 20,
            xpReward = 100,
            isCompleted = false,
            isAiRecommended = true,
            dayLabel = "Hoje • Noite"
        ),
        StudyTaskEntity(
            title = "Simulado Cronometrado: Eixo Meio Ambiente ou Tecnologia",
            description = "Escreva uma redação em até 50 minutos usando o cronômetro integrado do app.",
            category = "Simulado",
            competenciaFocus = "Geral • 1000 pts",
            durationMinutes = 50,
            xpReward = 200,
            isCompleted = false,
            isAiRecommended = true,
            dayLabel = "Meta da Semana"
        )
    )

    val initialCommunityPosts = listOf(
        CommunityPostEntity(
            authorName = "Lucas Oliveira",
            authorLevel = 9,
            authorBadge = "Mestre 960+",
            category = "Repertório",
            title = "Repertório Coringa para Meio Ambiente e Inclusão: Ailton Krenak",
            content = "Pessoal, usei 'Ideias para Adiar o Fim do Mundo' do Ailton Krenak no último simulado sobre Justiça Climática e tirei 200 na C2 e C3! Ele critica a separação artificial entre humanidade e natureza.",
            repertoireQuote = "\"A ideia de que os brancos europeus podiam sair colonizando o resto do mundo estava sustentada na premissa de que havia uma humanidade esclarecida que precisava ir ao encontro da humanidade obscurecida.\" — Ailton Krenak",
            likesCount = 42,
            isLikedByMe = true,
            commentsRaw = "Mariana Costa::Sensacional! Dá para relacionar com o Artigo 225 da Constituição no D2.|Prof. João Batista::Excelente articulação, Lucas! Krenak é um dos autores mais valorizados na banca atual.",
            timestamp = System.currentTimeMillis() - 7_200_000L
        ),
        CommunityPostEntity(
            authorName = "Beatriz Santos",
            authorLevel = 8,
            authorBadge = "Top 5 Semanal",
            category = "Tese & Argumento",
            title = "Como fazer o Circuito Fechado perfeito entre Introdução e Conclusão?",
            content = "Compartilhando a técnica que me fez subir de 840 para 940: se você abre a introdução com Gilberto Dimenstein ('Cidadãos de Papel'), termine a última linha da conclusão dizendo 'a fim de superar a condição de cidadãos de papel denunciada por Dimenstein'.",
            repertoireQuote = "Dica de Ouro C3 + C5: Retome o autor da introdução nas últimas 8 palavras da sua proposta de intervenção!",
            likesCount = 37,
            isLikedByMe = false,
            commentsRaw = "Gabriel Souza::Testei isso na área de Reescrita e minha nota subiu 80 pontos!|Eduarda Andrade::Muito obrigada pela dica, Bia!",
            timestamp = System.currentTimeMillis() - 18_000_000L
        ),
        CommunityPostEntity(
            authorName = "Prof. João Batista",
            authorLevel = 15,
            authorBadge = "Professor Corretor",
            category = "Discussão",
            title = "Atenção ao uso de verbos vagos na Competência 5!",
            content = "Turma, evitem usar apenas 'conscientizar a população' como AÇÃO única da proposta de intervenção. Prefiram verbos administrativos concretos: 'instituir', 'ampliar', 'fiscalizar', 'implementar', e deixem a conscientização como meio ou efeito.",
            repertoireQuote = "Fórmula C5 Segura: Agente Ministerial + Ação Administrativa Concreta + Meio + Detalhamento com travessões + Efeito Social.",
            likesCount = 68,
            isLikedByMe = true,
            commentsRaw = "Eduarda Andrade::Professor, posso usar dois agentes na mesma proposta?|Prof. João Batista::Pode sim, Eduarda! O segundo agente pode inclusive contar como detalhamento se estiver bem articulado.",
            timestamp = System.currentTimeMillis() - 45_000_000L
        )
    )

    val initialNotifications = listOf(
        AppNotificationEntity(
            title = "Resultado da Correção Disponível! 🎯",
            message = "Sua redação 'A Sociedade do Desempenho e a Invisibilidade Psíquica' atingiu 920 pontos na correção inteligente.",
            type = "CORRECAO",
            isRead = false,
            timestamp = System.currentTimeMillis() - 3_600_000L
        ),
        AppNotificationEntity(
            title = "Conquista Desbloqueada: Mestre da Reescrita 🏆",
            message = "Você elevou sua nota em +80 pontos (de 880 para 960) na Área de Reescrita e ganhou +150 XP!",
            type = "CONQUISTA",
            isRead = false,
            timestamp = System.currentTimeMillis() - 14_400_000L
        ),
        AppNotificationEntity(
            title = "Meta Diária de Estudos (12 dias seguidos! 🔥)",
            message = "Faltam apenas 2 tarefas rápidas do seu Plano de Estudos com IA para manter sua ofensiva.",
            type = "META",
            isRead = true,
            timestamp = System.currentTimeMillis() - 28_800_000L
        ),
        AppNotificationEntity(
            title = "Novo Tema Gerado no Eixo Tecnologia 💡",
            message = "Confira a proposta inédita sobre Inteligência Artificial Generativa e Ética Profissional.",
            type = "TEMA",
            isRead = true,
            timestamp = System.currentTimeMillis() - 64_000_000L
        )
    )

    val medals = listOf(
        MedalInfo("med_1", "Primeira Pena de Ouro", "Concluiu e corrigiu a 1ª redação completa na plataforma.", 500, "Star"),
        MedalInfo("med_2", "Mestre da Reescrita", "Reescreveu uma redação corrigida e aumentou a nota em pelo menos 60 pontos.", 1200, "AutoFixHigh"),
        MedalInfo("med_3", "Guardião da Competência 5", "Conquistou 200 pontos na Proposta de Intervenção em 2 redações seguidas.", 1800, "Verified"),
        MedalInfo("med_4", "Clube dos 900+", "Alcançou nota igual ou superior a 900 pontos no padrão ENEM.", 2200, "EmojiEvents"),
        MedalInfo("med_5", "Lenda Nota 1000", "Atingiu a nota máxima 1000 ou completou 3000 XP de domínio textual.", 3000, "WorkspacePremium")
    )

    val studentRanking = listOf(
        RankingStudent(1, "Lucas Oliveira", "SP", 968, 3420, "Diamante 1000"),
        RankingStudent(2, "Beatriz Santos", "MG", 952, 2910, "Ouro 960+"),
        RankingStudent(3, "Eduarda Andrade (Você)", "PB", 920, 2480, "Ouro 960+"),
        RankingStudent(4, "Gabriel Souza", "PE", 905, 2190, "Prata 900+"),
        RankingStudent(5, "Mariana Costa", "RJ", 890, 1950, "Prata 900+"),
        RankingStudent(6, "Pedro Henrique", "RS", 875, 1740, "Bronze 850+")
    )

    val certificates = listOf(
        CertificateInfo(
            id = "cert_1",
            title = "Certificado em Fundamentos da Dissertação-Argumentativa",
            hours = 20,
            requirementDescription = "Domínio de Introdução, Tese, Desenvolvimento e Conclusão.",
            isUnlocked = true
        ),
        CertificateInfo(
            id = "cert_2",
            title = "Certificado de Excelência na Competência 5 (200 Pontos)",
            hours = 15,
            requirementDescription = "Obter nota máxima na Proposta de Intervenção do ENEM.",
            isUnlocked = true
        ),
        CertificateInfo(
            id = "cert_3",
            title = "Certificado Mestre 900+ em Redação ENEM & Vestibulares",
            hours = 40,
            requirementDescription = "Superar a barreira dos 900 pontos em redações corrigidas.",
            isUnlocked = true
        ),
        CertificateInfo(
            id = "cert_4",
            title = "Certificado Especialista em Repertório Sociocultural Legitimado",
            hours = 30,
            requirementDescription = "Atingir Nível 10 (3.000 XP) e dominar os 10 eixos temáticos.",
            isUnlocked = false
        )
    )

    val initialProgressHistory = listOf(
        ProgressHistoryEntity(
            dateLabel = "15 Set",
            timestamp = System.currentTimeMillis() - 86_400_000L * 12,
            essayId = null,
            essayTitle = "Avaliação Diagnóstica Inicial",
            category = "Inclusão Social",
            examType = "ENEM",
            totalScore = 820,
            c1Score = 160,
            c2Score = 160,
            c3Score = 160,
            c4Score = 160,
            c5Score = 180,
            rollingAverageScore = 820,
            studyMinutesCumulative = 120,
            xpCumulative = 850,
            milestoneNote = "Diagnóstico inicial: foco recomendado nas Competências 3 e 4."
        ),
        ProgressHistoryEntity(
            dateLabel = "22 Set",
            timestamp = System.currentTimeMillis() - 86_400_000L * 5,
            essayId = 1,
            essayTitle = "A Educação entre a Norma e a Evasão",
            category = "Educação",
            examType = "ENEM",
            totalScore = 880,
            c1Score = 160,
            c2Score = 200,
            c3Score = 160,
            c4Score = 160,
            c5Score = 200,
            rollingAverageScore = 850,
            studyMinutesCumulative = 240,
            xpCumulative = 1480,
            milestoneNote = "Nota máxima atingida nas Competências 2 e 5 (+60 pts)."
        ),
        ProgressHistoryEntity(
            dateLabel = "25 Set",
            timestamp = System.currentTimeMillis() - 86_400_000L * 2,
            essayId = 2,
            essayTitle = "A Educação entre a Norma e a Evasão (Versão Reescrita)",
            category = "Educação",
            examType = "ENEM",
            totalScore = 960,
            c1Score = 200,
            c2Score = 200,
            c3Score = 180,
            c4Score = 180,
            c5Score = 200,
            rollingAverageScore = 887,
            studyMinutesCumulative = 360,
            xpCumulative = 2100,
            milestoneNote = "Reescrita guiada elevou a nota de 880 para 960 (+80 pts)."
        ),
        ProgressHistoryEntity(
            dateLabel = "Hoje",
            timestamp = System.currentTimeMillis() - 36_000_000L,
            essayId = 3,
            essayTitle = "A Sociedade do Desempenho e a Invisibilidade Psíquica",
            category = "Saúde",
            examType = "ENEM",
            totalScore = 920,
            c1Score = 180,
            c2Score = 200,
            c3Score = 180,
            c4Score = 160,
            c5Score = 200,
            rollingAverageScore = 920,
            studyMinutesCumulative = 460,
            xpCumulative = 2480,
            milestoneNote = "Simulado cronometrado concluído acima da barreira dos 900+."
        )
    )

    val initialVideoLessons = listOf(
        VideoLessonEntity(
            title = "AULÃO AO VIVO: Correção Comentada de Folhas Escaneadas & Segredos dos 960+",
            description = "Transmissão interativa ao vivo com análise linha a linha de redações feitas no papel, diagnóstico das 5 competências do ENEM e plantão de dúvidas em tempo real.",
            instructorName = "Prof. João Batista",
            category = "Aulão Ao Vivo",
            isLive = true,
            liveStatus = "AO_VIVO_AGORA",
            scheduledDateTime = "AO VIVO AGORA • Interação Aberta",
            durationMinutes = 60,
            videoUrl = "https://youtube.com/live/mestre-redacao-enem-ao-vivo",
            keyTopics = "00:00 Abertura e Critérios Oficiais INEP|12:30 Erros ocultos na Competência 1 no papel|28:00 Como transformar Tópico Frasal em 200 na C3|45:00 Correção ao vivo das redações enviadas no Scanner",
            supportMaterial = "Checklist de Revisão de Folha Oficial (30 linhas): 1) Margem respeitada; 2) Ausência de rasuras sem parênteses; 3) Conectivos interparágrafos no início do D1, D2 e Conclusão; 4) Proposta de intervenção com 5 elementos explícitos.",
            viewsCount = 342,
            isWatched = false,
            chatMessagesRaw = "Eduarda Andrade::Professor, se eu errar uma palavra na folha oficial, como corrigir sem perder ponto?|Prof. João Batista::Excelente pergunta, Eduarda! Basta passar um traço simples sobre a palavra (ex.: palavra) e escrever a correta logo adiante, sem usar corretivo!|Lucas Oliveira::Essa dica do tópico frasal no D1 salvou meu simulado!"
        ),
        VideoLessonEntity(
            title = "LIVE AGENDADA: Previsão de Eixos Temáticos ENEM & Repertórios Coringa",
            description = "Aula ao vivo especial sobre os 18 eixos temáticos mais quentes para o próximo exame, com repertórios filosóficos, sociológicos e constitucionais prontos para aplicar.",
            instructorName = "Prof. João Batista",
            category = "Aulão Ao Vivo",
            isLive = true,
            liveStatus = "AGENDADA",
            scheduledDateTime = "Quinta-feira às 19:30 (Horário de Brasília)",
            durationMinutes = 55,
            videoUrl = "https://youtube.com/live/mestre-redacao-eixos-enem",
            keyTopics = "00:00 Boas-vindas e Termômetro do INEP|10:00 Eixos de Tecnologia, Clima e Saúde Mental|25:00 Citações Coringa de Hannah Arendt, Bauman e Krenak|40:00 Sorteio de temas para treino no Scanner",
            supportMaterial = "Apostila Digital da Live: Quadro comparativo de 15 filósofos e pensadores divididos por eixo temático + Fórmulas de Introdução com a Constituição de 1988.",
            viewsCount = 189,
            isWatched = false,
            chatMessagesRaw = "Beatriz Santos::Já ativei o lembrete! Vai ter análise de texto motivador?|Prof. João Batista::Com certeza, Beatriz! Vamos destrinchar textos motivadores inéditos ao vivo."
        ),
        VideoLessonEntity(
            title = "Aula 01 (Gravada): Passo a Passo da Estrutura Nota 1000 e Tópico Frasal Perfeito",
            description = "Aprenda a anatomia completa dos 4 parágrafos dissertativo-argumentativos: contextualização, tese bipartida (A1 e A2), tópico frasal de D1 e D2 e fechamento crítico.",
            instructorName = "Prof. João Batista",
            category = "Passo a Passo",
            isLive = false,
            liveStatus = "GRAVADA",
            scheduledDateTime = "Aula Gravada • Módulo 1",
            durationMinutes = 32,
            videoUrl = "https://youtube.com/watch?v=mestre-redacao-aula01",
            keyTopics = "00:00 As 3 partes obrigatórias da Introdução|08:15 Como formular Tese Bipartida (Omissão Estatal + Invisibilidade Social)|16:40 O que é Tópico Frasal e como abrir o D1 e D2|24:50 Exemplo prático em redação nota 1000",
            supportMaterial = "Template Estrutural de 30 Linhas: Introdução (7 linhas) • Desenvolvimento 1 (8 linhas) • Desenvolvimento 2 (8 linhas) • Conclusão (7 linhas).",
            viewsCount = 845,
            isWatched = true,
            chatMessagesRaw = "Gabriel Souza::Melhor explicação sobre tópico frasal que já assisti!|Mariana Costa::Anotei todos os esqueletos de introdução."
        ),
        VideoLessonEntity(
            title = "Aula 02 (Gravada): Dominando Conectivos Inter e Intraparágrafos (Competência 4)",
            description = "Guia definitivo sobre onde usar cada conectivo de abertura, adição, oposição, causa, consequência e conclusão sem repetir palavras na redação.",
            instructorName = "Prof. João Batista",
            category = "Competência 4",
            isLive = false,
            liveStatus = "GRAVADA",
            scheduledDateTime = "Aula Gravada • Módulo 2",
            durationMinutes = 28,
            videoUrl = "https://youtube.com/watch?v=mestre-redacao-aula02",
            keyTopics = "00:00 Diferença entre Coesão Referencial e Sequencial|07:20 Conectivos obrigatórios interparágrafos (D1, D2 e Conclusão)|15:10 Operadores intraparágrafos para encadear períodos|22:00 Erros fatais que derrubam a C4 de 200 para 160",
            supportMaterial = "Tabela de Ouro da C4: 'Em primeira análise' (D1), 'Ademais / Outrossim' (D2), 'Portanto / Desse modo' (Conclusão), 'Sob esse viés / Consequentemente' (Intraparágrafos).",
            viewsCount = 612,
            isWatched = true,
            chatMessagesRaw = "Pedro Henrique::Parei de repetir 'além disso' graças a essa aula!"
        ),
        VideoLessonEntity(
            title = "Aula 03 (Gravada): Proposta de Intervenção Blindada com os 5 Elementos (Competência 5)",
            description = "Como garantir 200 pontos na Competência 5 em qualquer tema do ENEM articulando Agente, Ação, Meio/Modo, Efeito e Detalhamento.",
            instructorName = "Prof. João Batista",
            category = "Competência 5",
            isLive = false,
            liveStatus = "GRAVADA",
            scheduledDateTime = "Aula Gravada • Módulo 3",
            durationMinutes = 36,
            videoUrl = "https://youtube.com/watch?v=mestre-redacao-aula03",
            keyTopics = "00:00 Os 5 Elementos oficiais da Matriz do ENEM|09:40 Os melhores Agentes Governamentais e Sociais (GOMIFES)|18:30 Como criar o Detalhamento perfeito entre travessões|28:10 Retomada do repertório da introdução no efeito final",
            supportMaterial = "Fórmula dos 5 Elementos: 1. Agente (Ministério / Governo) + 2. Detalhamento (— órgão responsável por... —) + 3. Ação (deve instituir/ampliar...) + 4. Meio (por meio de...) + 5. Efeito (a fim de...).",
            viewsCount = 730,
            isWatched = false,
            chatMessagesRaw = "Eduarda Andrade::Sempre uso o detalhamento entre travessões depois de assistir a essa aula!"
        )
    )
}
