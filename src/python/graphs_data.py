import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import re
import os

# --- Função para parsing ---
def parse_line(line):
    if "HashType" not in line:
        return None

    match = re.search(
        r"HashType=(\w+)\s*\|\s*Dataset=(.+?)\s*\|\s*Size=(\d+)\s*\|\s*Collisions=(\d+)\s*\|\s*Variance=([\d\.,]+)", line
    )

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


# --- Leitura do arquivo ---
data_list = []
with open('data/output/output.txt', 'r', encoding='utf-8') as file:
    content = file.read()

lines = content.strip().split('\n')
current_line = ""
for line in lines:
    current_line += line.strip()
    if 'Variance=' in current_line:
        parsed_data = parse_line(current_line)
        if parsed_data:
            data_list.append(parsed_data)
        current_line = ""

df = pd.DataFrame(data_list)

# --- Preparação ---
df['Dataset_Name'] = df['Dataset'].astype(str).str.replace(
    'data/keys/', '').str.replace('.csv', '').str.replace('_', ' ').str.title()

# Load factors
load_factors = {3000: "30%", 5000: "50%", 8000: "80%"}
df['LoadFactor'] = df['Size'].map(load_factors)

# --- Criação da pasta de resultados ---
os.makedirs("results", exist_ok=True)

# --- 3 HISTOGRAMAS (COLISÕES) ---
for size, lf in load_factors.items():
    subset = df[df['Size'] == size]

    plt.figure(figsize=(10, 6))

    hash_types = subset['HashType'].unique()
    datasets = subset['Dataset_Name'].unique()
    x = np.arange(len(hash_types))  # posições no eixo X

    bar_width = 0.2  # largura de cada barra
    offsets = np.linspace(-bar_width, bar_width, len(datasets))  # deslocamento das barras

    for i, dataset in enumerate(datasets):
        data = subset[subset['Dataset_Name'] == dataset]
        plt.bar(x + offsets[i], data['Collisions'], width=bar_width, label=dataset)

    plt.xticks(x, hash_types)
    plt.title(f"Número de colisões por funções hash - Load Factor {lf}")
    plt.xlabel("Funções")
    plt.ylabel("Número de Colisões")
    plt.legend(title="Dataset")
    plt.grid(axis='y', linestyle=':', alpha=0.6)
    plt.tight_layout()
    plt.savefig(f"results/hist_colisoes_{lf}.pdf")
    plt.close()

# --- 3 DISPERSÕES (VARIÂNCIA) ---
for size, lf in load_factors.items():
    subset = df[df['Size'] == size]

    plt.figure(figsize=(10, 6))
    for dataset in subset['Dataset_Name'].unique():
        data = subset[subset['Dataset_Name'] == dataset]
        plt.scatter(data['HashType'], data['Variance'], label=dataset, s=100)

    # Reta da média
    mean_var = subset['Variance'].mean()
    plt.axhline(y=mean_var, color='red', linestyle='--', label=f"Média ({mean_var:.2f})")

    plt.title(f"Variância por HashType - Load Factor {lf}")
    plt.xlabel("HashType")
    plt.ylabel("Variância")
    plt.legend(title="Dataset")
    plt.grid(True, linestyle=':', alpha=0.6)
    plt.tight_layout()
    plt.savefig(f"results/scatter_variancia_{lf}.pdf")
    plt.close()
