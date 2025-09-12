package benchmark;
import hash.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionCounter {

    public static void main(String[] args) {
        String[] hashTypes = {"DIVISION", "MULTIPLICATION", "TCR", "FOLDING", "SHIFT_XOR"};
        String[] datasetPaths = {
            "data/keys/unicos_pequena.csv",
            "data/keys/sequenciais_pequena.csv",
            "data/keys/primos_pequena.csv",
            "data/keys/unicos_media.csv",
            "data/keys/sequenciais_media.csv",
            "data/keys/primos_media.csv",
            "data/keys/unicos_grande.csv",
            "data/keys/sequenciais_grande.csv",
            "data/keys/primos_grande.csv"
        };

        // Garantir que a pasta 'data/output' exista
        File outputDir = new File("data/output");
        if (!outputDir.exists()) outputDir.mkdirs();

        String outputPath = "data/output/output.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

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

                        // 4. Escrever os resultados no arquivo
                        String resultLine = "HashType=" + hashType + " | Dataset=" + datasetPath + " | Size=" + keys.length + " | Collisions=" + table.getCollisions() + "| Variance=" + table.getVariancia();
                        System.out.println(resultLine); // ainda imprime no console
                        writer.write(resultLine);
                        writer.newLine();

                    } catch (IOException e) {
                        System.err.println("Erro ao ler o arquivo: " + datasetPath);
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Analise concluida. Saida salva em: " + outputPath);

        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo de saída.");
            e.printStackTrace();
        }
    }
}
