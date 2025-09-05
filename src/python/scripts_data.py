import csv
import random
import os

random.seed(7942)

pasta_destino = os.path.join("data")
os.makedirs(pasta_destino, exist_ok=True)

def gerar_dados_repetidos(num_elementos, minimo=1, maximo=1000000):
    # Gera poucos elementos únicos e repete cada um até chegar ao total
    elementos_base_para_repeticao = [random.randint(minimo, maximo)
                                     for _ in range(max(1, num_elementos // 100))]
    dados = []
    for elemento in elementos_base_para_repeticao:
        dados.extend([elemento] * 100)
    return dados[:num_elementos]

def gerar_dados_unicos(num_elementos, minimo=1, maximo=10000000):
    # Gera valores únicos aleatórios
    intervalo_possivel = maximo - minimo + 1
    if num_elementos > intervalo_possivel:
        raise ValueError(
            f"num_elementos ({num_elementos}) não pode ser maior que o intervalo [{minimo}, {maximo}]")
    return random.sample(range(minimo, maximo + 1), num_elementos)


def gerar_dados_sequenciais(num_elementos, minimo=1):
    # Gera valores sequenciais
    return list(range(minimo, minimo + num_elementos))


def salvar_csv(nome_arquivo, valores):
    caminho_completo = os.path.join(pasta_destino, nome_arquivo)
    with open(caminho_completo, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["Valor"])
        for valor in valores:
            writer.writerow([valor])
    return caminho_completo


# Tamanhos de carga
tamanhos = {

    # Entrada pequena (Load_Factor de 30% do tamanho da tabela)
    "pequena": 3000,
    # Entrada média (Load_Factor de 50% do tamanho da tabela)
    "media": 5000,
    # Entrada grande (Load_Factor de 80% do tamanho da tabela)
    "grande": 8000

}

lista_carga = []

for tamanho_nome, tamanho_valor in tamanhos.items():

    # Altamente repetidos
    lista_carga.append((
        f"repetidos_{tamanho_nome}.csv",
        gerar_dados_repetidos(num_elementos=tamanho_valor)
    ))

    # Totalmente únicos
    lista_carga.append((
        f"unicos_{tamanho_nome}.csv",
        gerar_dados_unicos(num_elementos=tamanho_valor)
    ))
    # Sequenciais
    lista_carga.append((
        f"sequenciais_{tamanho_nome}.csv",
        gerar_dados_sequenciais(num_elementos=tamanho_valor)
    ))

for nome_arquivo, valores in lista_carga:
    caminho_arquivo = salvar_csv(nome_arquivo, valores)
    print(f"Arquivo salvo em: {os.path.abspath(caminho_arquivo)}")
