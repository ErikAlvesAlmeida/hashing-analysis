# Comparação de diferentes funções hash
## Sobre o Projeto
Este projeto apresenta uma análise comparativa de cinco funções de hash (Divisão, Multiplicação, Folding, Shift-XOR e TCR). O estudo detalha a metodologia, as características das funções, os tipos de chaves utilizadas (sequenciais, primos, aleatórios) e os resultados de desempenho em termos de colisões e variância.

O objetivo é explorar como cada função se comporta sob diferentes fatores de carga (30%, 50%, 80%) em uma tabela hash de endereçamento aberto, buscando eficiência e uma distribuição uniforme.

A análise completa e as conclusões estão publicadas nesta [_GitHub Page_](https://erikalvesalmeida.github.io/hashing-analysis/ ).

## Pré-requisitos
Antes de começar, garanta que você tenha as seguintes ferramentas instaladas em sua máquina:

- *Git*: Para clonar o repositório.

- *Java Development Kit (JDK)*: Versão 17 ou superior.

- *Gradle*: Versão 8.0 ou superior.

- *Python*: Versão 3.11 ou superior.

Você pode verificar as instalações com os seguintes comandos no seu terminal:

```bash
# comandos de verificação
git --version
java -version
gradle -version
python --version
# ou python3 --version
```
### Nota para os usuários de Linux:
A automação do projeto depende do módulo ```venv``` do Python. Se a execução do ```gradle automateAll``` falhar na tarefa ```:createVenv``` com um erro sobre ```ensurepip```, significa que este módulo não está instalado. Para corrigir, instale o pacote de desenvolvimento do Python para sua distribuição.

Geralmente, o pacote é chamado de ```python3-venv``` ou ```python3-devel```, e pode ser instalado com o gerenciador de pacotes apropriado (```apt```, ```dnf```, ```zypper```, etc.).

- Para Debian/Ubuntu: ```sudo apt install python3-venv```
- Para Fedora/RHEL: ```sudo dnf install python3-devel```

Se o erro persistir, a primeira tentativa pode ter deixado uma pasta ```.venv``` incompleta. Remova-a com o comando ```rm -rf .venv``` e execute o ```gradle automateAll``` novamente.

## Instalação e Configuração
Siga os passos abaixo para configurar o ambiente e instalar as dependências.

### Clone o Repositório
Use o comando abaixo para criar uma cópia local do projeto:

```bash
git clone https://github.com/ErikAlvesAlmeida/hashing-analysis.git
cd hashing-analysis
```

## Executando os Testes
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
