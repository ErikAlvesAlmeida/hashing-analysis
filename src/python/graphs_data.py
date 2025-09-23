import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import re
import os

# --- Função para parsing de cada linha ---


def parse_line(line):
    if "HashType" not in line:
        return None

    match = re.search(
        r"HashType=(\w+)\s*\|\s*Dataset=(.+?)\s*\|\s*Size=(\d+)\s*\|\s*Collisions=(\d+)\s*\|\s*Variance=([\d\.,]+)",
        line
    )
    if match:
        return {
            'HashType': match.group(1),
            'Dataset': match.group(2),
            'Size': int(match.group(3)),
            'Collisions': int(match.group(4)),
            'Variance': float(match.group(5).replace(',', '.'))
        }
    return None


# --- Leitura e parsing do arquivo ---
data_list = []
with open('data/output/output.txt', 'r', encoding='utf-8') as file:
    for line in file:
        parsed = parse_line(line.strip())
        if parsed:
            data_list.append(parsed)

# Verifica se encontrou dados
if not data_list:
    raise ValueError(
        "Nenhum dado válido encontrado. Verifique output.txt e o parse_line.")

df = pd.DataFrame(data_list)

# --- Preparação dos dados ---
df['Dataset_Name'] = (
    df['Dataset'].str.replace('data/keys/', '', regex=False)
                 .str.replace('.csv', '', regex=False)
                 .str.replace('_', ' ', regex=False)
                 .str.replace(r'\b(pequena|media|grande)\b', '', regex=True)
                 .str.strip()
                 .str.title()
)

# Load factors (tamanhos)
load_factors = {3000: "30%", 5000: "50%", 8000: "80%"}
df['LoadFactor'] = df['Size'].map(load_factors)

# --- Criação da pasta de resultados ---
os.makedirs("results", exist_ok=True)

# --- HISTOGRAMAS DE COLISÕES ---
for size, lf in load_factors.items():
    subset = df[df['Size'] == size]
    if subset.empty:
        continue  # pula se não houver dados para esse tamanho

    plt.figure(figsize=(10, 6))
    hash_types = subset['HashType'].unique()
    datasets = subset['Dataset_Name'].unique()
    x = np.arange(len(hash_types))
    bar_width = 0.2
    offsets = np.linspace(-bar_width, bar_width, len(datasets))

    for i, dataset in enumerate(datasets):
        data = subset[subset['Dataset_Name'] == dataset].set_index('HashType')
        heights = [data.at[ht, 'Collisions']
                   if ht in data.index else 0 for ht in hash_types]
        plt.bar(x + offsets[i], heights, width=bar_width, label=dataset)

    plt.xticks(x, hash_types)
    plt.title(f"Colisões - Load Factor {lf}\nTabela Hash com 10.007 posições")
    plt.xlabel("Funções Hash")
    plt.ylabel("Número de Colisões")
    plt.legend(title="Entradas", loc="upper left")
    plt.grid(axis='y', linestyle=':', alpha=0.6)
    plt.tight_layout()
    plt.savefig(f"results/collision/hist_colisoes_{lf}.pdf")
    plt.close()

# --- HISTOGRAMAS DE VARIÂNCIA ---
for size, lf in load_factors.items():
    subset = df[df['Size'] == size]
    if subset.empty:
        continue
    mean_variance = subset['Variance'].mean()
    plt.figure(figsize=(10, 6))
    hash_types = subset['HashType'].unique()
    datasets = subset['Dataset_Name'].unique()
    x = np.arange(len(hash_types))
    bar_width = 0.2
    offsets = np.linspace(-bar_width, bar_width, len(datasets))

    for i, dataset in enumerate(datasets):
        data = subset[subset['Dataset_Name'] == dataset].set_index('HashType')
        heights = [data.at[ht, 'Variance'] if ht in data.index else 0
                   for ht in hash_types]
        plt.bar(x + offsets[i], heights, width=bar_width, label=dataset)
    plt.axhline(y=mean_variance, color='r', linestyle='--',
                label=f'Média: {mean_variance:.2f}')
    plt.xticks(x, hash_types)
    plt.ylim(0, 550)
    plt.title(f"Variância - Load Factor {lf}\nTabela Hash com 10.007 posições")
    plt.xlabel("Funções Hash")
    plt.ylabel("Variância")
    plt.legend(title="Entradas", loc="upper left")
    plt.grid(axis='y', linestyle=':', alpha=0.6)
    plt.tight_layout()
    plt.savefig(f"results/variance/hist_variancia_{lf}.pdf")
    plt.close()

print("Gráficos gerados com sucesso na pasta 'results'.")
