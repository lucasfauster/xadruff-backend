<img align="right"  src="https://user-images.githubusercontent.com/50959073/172961331-f144bf54-3d6d-4e67-a63c-d55a44a649fc.png" width="300" height="300" />

# XadrUFF - Back-End
Esse projeto é responsável pela parte de Back-End do XadrUFF, ele foi desenvolvido em Kotlin com SpringBoot. 

[Visualize o Projeto de Front-End aqui!](https://github.com/lucasfauster/xadruff-frontend)


 - [Visualize nossa página no Notion](https://luamz.notion.site/XadrUFF-7e272f2c22a74ca9be39b6a00ae1c440)
 - [Visualize a Análise de Valor Agregado](https://docs.google.com/spreadsheets/d/1GNKjPhV9bs--8fbKKEkWH3g1j96xQNvAf8fyJ7w5q3w/edit#gid=692033079)
 - [Visualize o EAP](https://drive.google.com/file/d/1JPSLjALye_b2cY7z7KKQy7uQ3KSip3cZ/view)
 - [Visualize o Layout no Figma](https://www.figma.com/file/xH4MrdVl8TFi4ADzO0zdcs/XadrUFF?node-id=0%3A1)

## Subindo aplicação local
  
  - Executar comando `./gradlew clean build`.
  - Executar o `XadruffBackendApplication.kt` com o profile local.

## Endpoint de produção
https://xadruff-backend.herokuapp.com

## Endpoints
### POST /chess/new-game?start-by={{AI/PLAYER}}&level={{BEGINNER/INTERMEDIATE}}
#### Body: 
        positions: [
            [" ", " ", " ", " ", "K", " ", " ", " "],
            ["P", "P", "P", "P", " ", "P", "P", "P"],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            ["p", "p", "p", "p", " ", "p", "p", "p"],
            [" ", " ", " ", " ", "k", " ", " ", " "],
        ],
        turn_color: "WHITE"
}}`
  - curl de exemplo: ` curl --location --request GET 'localhost:8080/chess/new-game?start-by=AI&level=INTERMEDIATE' `
  - Body é opcional e pode alterar a posição inicial do jogo, as peças disponíves são:
    - p | P = peão
    - q | Q = rainha
    - k | K = rei
    - n | N = cavalo
    - b | B = bispo
    - r | R = torre
    - g | G = fantasma -- indicar que teve en passant na última jogada
  - Caso o campo start-by seja AI, o jogador será as peças pretas e será feito um movimento para a IA, caso seja PLAYER o jogador ser as peças brancas.
  - O campo level pode ser BEGINNER ou IMTERMEDIATE
  - Retorna: um board-id que representa o jogo internamente, um board que é uma matriz de posições, uma lista de movimentos legais e qual o movimento da ia, se houver movimento

### POST /chess/move?board-id={{board-id}}
 #### Body: "move": "x0y0"
 
 - curl de exemplo: `curl --location --request POST 'localhost:8080/chess/move?board-id=89783890-4854-437A-AA61-A1A0009C2024' \
--header 'Content-Type: application/json' \
--data-raw '{
    "move": "a7a6"
}'`

- Retorna: o mesmo retorno que o endpoint de inicializaço de tabuleiro, porém sempre há uma jogada da IA.

### GET /chess/SURRENDER?board-id={{board-id}}

- curl de exemplo: `curl --location --request GET 'localhost:8080/chess/surrender?board-id=89783890-4854-437A-AA61-A1A0009C2024' 
`

- Retorna: o mesmo retorno que o endpoint de inicializaço de tabuleiro, porém retornando que o jogo acabou por desistência do usuário.


## Ferramentas de qualidade

### [Jacoco](https://www.jacoco.org/jacoco/):
- Objetivo: Verificar cobertura de testes.
- Configurações de mínimo de coberturas descrito em `gradle/jacoco.gradle`.
- Comandos:
  - `./gradlew jacocoTestCoverageVerification` para criar report em `build/jacocoHtml`.
  - `./gradlew jacocoTestReport` para verificar se está passando nas porcentagens mínimas definidas.
- Impede o término do build se não passar na verificação.

### [Ktlint](https://ktlint.github.io/):
- Objetivo: Verificar e padronizar formatação do código.
- Comandos:
  - `./gradlew ktlintFormat` para formatar o código.
  - `./gradlew ktlintCheck` para verificar se há algo a ser formatado, o report é criado em `buid/reports/ktlint/ktlintMainSourceSetCheck`.
  - Impede o término do build se o código não estiver formatado.

### [Detekt](https://detekt.dev/):
- Objetivo: Analisar o código estaticamente, encontrando code smells e vulnerabilidades.
- Comandos:
  - `./gradlew detekt` para verificar se há algum code smell a ser removido.
- Configurações ficam no arquivo `config/detekt/detekt.yml`.
- Impede o término do build se existir code smells a serem removidos.
