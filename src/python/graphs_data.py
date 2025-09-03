import pandas as pd
import matplotlib.pyplot as plt
import re

# Esta função extrai os dados relevantes de cada linha de texto
def parse_line(line):
    # A linha pode estar quebrada, então concatenamos até encontrar todos os dados
    if "HashType" not in line:
        return None
    
    # Usa uma expressão regular para capturar os valores
    match = re.search(r"HashType=(\w+)\s*\|\s*Dataset=(.+?)\s*\|\s*Size=(\d+)\s*\|\s*Collisions=([\d\.]+)\s*\|\s*Variance=([\d\.]+)", line)
    
    if match:
        data = {
            'HashType': match.group(1),
            'Dataset': match.group(2),
            'Size': int(match.group(3)),
            'Collisions': int(match.group(4)),
            'Variance': float(match.group(5).replace(',', '.'))
        }
        return data
    return None

# ---
# Leitura e processamento dos dados
# ---

data_list = []
# Lê o arquivo de resultados gerado pelo Java
# O caminho do arquivo é 'results.txt' porque ele está na raiz do seu projeto
with open('results.txt', 'r', encoding='utf-16') as file:
    content = file.read()

# Divide o conteúdo por linhas para processar cada registro
lines = content.strip().split('\n')
current_line = ""

# Processa as linhas, unindo as quebras
for line in lines:
    current_line += line.strip()
    if 'Variance=' in current_line:
        # Se a linha contiver todos os dados, processa e reinicia a string
        parsed_data = parse_line(current_line)
        if parsed_data:
            data_list.append(parsed_data)
        current_line = ""

# Cria um DataFrame do Pandas para facilitar a manipulação
df = pd.DataFrame(data_list)

# ---
# Preparação dos dados para os gráficos
# ---

# Ordena os datasets para garantir uma ordem lógica nos gráficos
ordered_datasets = [
    "data/unicos_pequena.csv",
    "data/sequenciais_pequena.csv",
    "data/unicos_media.csv",
    "data/sequenciais_media.csv",
    "data/unicos_grande.csv",
    "data/sequenciais_grande.csv"
]
df['Dataset'] = pd.Categorical(df['Dataset'], categories=ordered_datasets, ordered=True)
df.sort_values(by=['Dataset', 'HashType'], inplace=True)

# Cria nomes mais curtos e legíveis para os eixos dos gráficos
df['Dataset_Name'] = df['Dataset'].astype(str).str.replace('data/', '').str.replace('.csv', '').str.replace('_', ' ').str.title()
unique_datasets = df['Dataset_Name'].unique()

# ---
# Geração dos gráficos
# ---

# Gráfico de Colisões
plt.figure(figsize=(12, 7))
for hash_type, group in df.groupby('HashType'):
    plt.plot(group['Dataset_Name'], group['Collisions'], label=hash_type, marker='o', linestyle='--')

plt.title('Colisões por Algoritmo de Hash', fontsize=16)
plt.xlabel('Conjunto de Dados', fontsize=12)
plt.ylabel('Número de Colisões', fontsize=12)
plt.xticks(rotation=45, ha='right')
plt.legend(title='Tipo de Hash')
plt.grid(True, linestyle=':', alpha=0.6)
plt.tight_layout()
plt.show()


# Gráfico de Variância
plt.figure(figsize=(12, 7))
for hash_type, group in df.groupby('HashType'):
    plt.plot(group['Dataset_Name'], group['Variance'], label=hash_type, marker='o', linestyle='-')

plt.title('Variância por Algoritmo de Hash', fontsize=16)
plt.xlabel('Conjunto de Dados', fontsize=12)
plt.ylabel('Variância', fontsize=12)
plt.xticks(rotation=45, ha='right')
plt.legend(title='Tipo de Hash')
plt.grid(True, linestyle=':', alpha=0.6)
plt.tight_layout()
plt.show()