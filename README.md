# Comparação de diferentes funções hash
## Sobre o Projeto
Este projeto apresenta uma análise comparativa de cinco funções de hash (Divisão, Multiplicação, Folding, Shift-XOR e TCR). O estudo detalha a metodologia, as características das funções, os tipos de chaves utilizadas (sequenciais, primos, aleatórios) e os resultados de desempenho em termos de colisões e variância.

O objetivo é explorar como cada função se comporta sob diferentes fatores de carga (30%, 50%, 80%) em uma tabela hash de endereçamento aberto, buscando eficiência e uma distribuição uniforme.

A análise completa e as conclusões estão publicadas nesta [_GitHub Page_](https://erikalvesalmeida.github.io/hashing-analysis/ ).

## Pré-requisitos
Antes de começar, garanta que você tenha as seguintes ferramentas instaladas em sua máquina:

- *Git*: Para clonar o repositório.

- *Java Development Kit (JDK)*: Versão 17 ou superior.

- *Gradle*: Para automação da compilação e execução dos testes.

- *Python*: Versão 3.11 ou superior.

Você pode verificar as instalações com os seguintes comandos no seu terminal:

```bash
git --version
java -version
gradle -version
python3 --version
```

## Instalação e Configuração
Siga os passos abaixo para configurar o ambiente e instalar as dependências.

### 1. Clone o Repositório
Use o comando abaixo para criar uma cópia local do projeto:

```bash
git clone https://github.com/ErikAlvesAlmeida/hashing-analysis.git
cd hashing-analysis
```

### 2. Instale as Dependências Python
Este projeto utiliza pandas e matplotlib para gerar as cargas de teste e os gráficos de resultado. Instale-as facilmente usando o arquivo requirements.txt:

```bash
pip install -r requirements.txt
```

````
(Nota: pode ser necessário usar pip3 dependendo da sua configuração de sistema).
````

## Executando os Testes
Com todo o ambiente configurado, utilize um único comando Gradle para executar todo o fluxo do projeto: gerar os arquivos de carga (chaves), rodar os benchmarks em Java e, por fim, gerar os gráficos de análise com os resultados.

Na pasta raiz do projeto, execute:

```bash
gradle automateAll
```

O processo pode levar alguns minutos para ser concluído.

## Estrutura do Repositório
- **docs/**: Contém os arquivos da GitHub Page (HTML, CSS, etc.).
- **results/**: Armazena os gráficos de saída dos testes em formato .pdf, separados por colisões e variância.
- **src/**: Todo o código-fonte do projeto.
  - **main/java/**: Implementação das funções de hash e do benchmark.
  - **python/**: Scripts em Python para geração das cargas e dos gráficos.
- **build.gradle**: Arquivo de configuração do Gradle que define as tarefas de automação.
- **requirements.txt**: Lista de dependências Python para fácil instalação.
