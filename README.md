<img align="right"  src="https://user-images.githubusercontent.com/50959073/172961331-f144bf54-3d6d-4e67-a63c-d55a44a649fc.png" width="300" height="300" />

# XadrUFF - Backend
Esse projeto é responsável pela parte de Back-End do XadrUFF, ele foi desenvolvido em Kotlin com SpringBoot. 

[Visualize o Projeto de Front-End aqui!](https://github.com/lucasfauster/xadruff-frontend)

 - [Visualize nossa página no Notion](https://www.figma.com/file/xH4MrdVl8TFi4ADzO0zdcs/XadrUFF?node-id=0%3A1)
 - [Visualize a Análise de Valor Agregado](https://docs.google.com/spreadsheets/d/1GNKjPhV9bs--8fbKKEkWH3g1j96xQNvAf8fyJ7w5q3w/edit#gid=692033079)
 - [Visualize o EAP](https://docs.google.com/spreadsheets/d/1GNKjPhV9bs--8fbKKEkWH3g1j96xQNvAf8fyJ7w5q3w/edit#gid=692033079)
 - [Visualize o Layout no Figma](https://www.figma.com/file/xH4MrdVl8TFi4ADzO0zdcs/XadrUFF?node-id=0%3A1)

## Subindo aplicação local
  
  - Executar comando `./gradlew clean build`.
  - Executar o `XadruffBackendApplication.kt`.

## Endpoints
### GET /chess/new-game?start-by={{AI/PLAYER}} 
  - curl de exemplo: ` curl --location --request GET 'localhost:8080/chess/new-game?start-by=AI' `
    
 - Caso seja AI, o jogador será as peças pretas e será feito um movimento para a IA, caso seja PLAYER o jogador ser as peças brancas.
 - Retorna: um board-id que representa o jogo internamente, um board que é uma matriz de posições, uma lista de movimentos legais e qual o movimento da ia, se houver movimento

### POST /chess/move?board-id={{board-id}}
 #### Body: "move": "x0y0"
 
 - curl de exemplo: `curl --location --request POST 'localhost:8080/chess/move?board-id=89783890-4854-437A-AA61-A1A0009C2024' \
--header 'Content-Type: application/json' \
--data-raw '{
    "move": "a7a6"
}'`

- Retorna: o mesmo retorno que o endpoint de inicializaço de tabuleiro, porém sempre há uma jogada da IA.

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
