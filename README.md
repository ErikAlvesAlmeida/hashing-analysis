## GitHub Page

Este [_GitHub Page_](https://erikalvesalmeida.github.io/hashing-analysis/ ) apresenta uma análise das cinco funções de hash escolhidas (Divisão, Multiplicação, Folding, Shift-XOR e TCR).O conteúdo detalha a metodologia empregada, as características das funções hash analisadas, os tipos de cargas e padrões de chaves utilizados (sequenciais, primos, aleatórios), e apresenta resultados de desempenho em termos de número de colisões e variância das inserções. O estudo explora como cada função se comporta sob diferentes fatores de carga em uma tabela hash por endereçamento aberto, com foco em eficiência e estabilidade (minimização de colisões e distribuição uniforme).

## Como executar os testes:
1. Para executar os testes verifique se o Gradle,Python3,matplotlib,pandas e o java estão instalados na sua máquina. Execute os comandos abaixo no terminal para a verificação:
```
python3 --version
```
```
gradle -version
```
```
java -version
```
```
pip show matplotlib
```
```
pip show pandas
```


2. Na pasta raiz do projeto use o comando abaixo para gerar as cargas e rodar os testes:
```
gradle automateAll
```
## Estrutura do Repositório

- `docs/`:  Arquivos do GitHub Pages como HTML,CSS e gráficos em JPG
- `results/`: Contém os resultados dos benchmark em `.pdf`.
- `src/`: Códigos utilizados no projeto.  
  - Na pasta `python/`, Possui script para geração de cargas e gráficos.
  - Na pasta `main/` do Java, comporta os códigos para implementação do benchmark e das funções Hash 
  - `build.gradle`: Configuração da automação dos testes do projeto  
