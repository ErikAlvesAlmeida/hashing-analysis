package benchmark;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hash.HashTable;
import hash.HashType;

public class CollisionCounter {

    public static void main(String[] args) {
        String[] hashTypes = {"DIVISION", "MULTIPLICATION", "TCR", "FOLDING", "SHIFT_XOR"};
        String[] datasetPaths = {
            "data/unicos_pequena.csv",
            "data/sequenciais_pequena.csv",
            "data/unicos_media.csv",
            "data/sequenciais_media.csv",
            "data/unicos_grande.csv",
            "data/sequenciais_grande.csv"
        };

        int count = 1;

        for (String hashType : hashTypes) {
            for (String datasetPath : datasetPaths) {
                try {
                    // 1. Carregar os dados
                    List<Integer> values = new ArrayList<>();
                    try (BufferedReader br = new BufferedReader(new FileReader(datasetPath))) {
                        String line = br.readLine(); // Descartar cabeçalho
                        while ((line = br.readLine()) != null) {
                            values.add(Integer.parseInt(line.trim()));
                        }
                    }
                    
                    int[] keys = values.stream().mapToInt(Integer::intValue).toArray();

                    // 2. Criar a tabela hash com o tipo de hash
                    HashTable table = new HashTable(HashType.valueOf(hashType));

                    // 3. Inserir todos os elementos e contar as colisões
                    for (int key : keys) {
                        table.put(key, key);
                    }

                    // 4. Imprimir os resultados
                    System.out.println("HashType=" + hashType + " | Dataset=" + datasetPath + " | Size=" + keys.length + " | Collisions=" + table.getCollisions() + "| Variance=" + table.variancia());
                } catch (IOException e) {
                    System.err.println("Erro ao ler o arquivo: " + datasetPath);
                    e.printStackTrace();
                }

                

            }
        }
    }
}