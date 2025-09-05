package hash;

import java.util.Objects;

/**
 * Implementação de uma tabela hash usando sondagem linear.
 * Permite inserção, busca e remoção de pares chave-valor (Integer, int).
 * Também mantém um contador de colisões para análise de dispersão.
 */
public class HashTable {
    private Pair[] table;
    private Pair[] frequencia;
    private HashFunction hashFunction;
    private int size;
    private int collisions = 0;

    private final int SIZE_TABLE = 10007;

    /**
     * Construtor da HashTable.
     * @param hashFunction Função de hash que será usada para calcular os índices.
     */
    public HashTable(HashFunction hashFunction){
        this.table = new Pair[SIZE_TABLE];
        this.frequencia = new Pair[SIZE_TABLE];
        this.hashFunction = hashFunction;
        this.size = 0;
        this.collisions = 0;
    }

    /**
     * Insere um par chave-valor na tabela.
     * Se a chave já existir, o valor será atualizado.
     * @param key Chave a ser inserida.
     * @param value Valor associado à chave.
     */
    public void put(Integer key, int value){
        int hash = this.hashFunction.hash(key, SIZE_TABLE);
        int probing = 0;

        while(probing < this.table.length) {
            
            int pos = (hash + probing) % this.table.length;

            if(this.table[pos] == null) {
                this.table[pos] = new Pair(key, value);
                this.frequencia[pos] = new Pair(key, 1);
                this.size++;
                return;
            } else if (Objects.equals(this.table[pos].key, key)){
                this.table[pos].value = value;
                return;
            }

            if (probing == 0) {
                this.collisions++;
                this.frequencia[pos].value++;
            }

            probing++;
        }
    }

    public String getFrequencia() {
        String aux = "";

        for(int i = 0; i < this.frequencia.length; i++) {
            if(this.frequencia[i] != null) {
                aux += "frequencia: " + this.frequencia[i].value + " posicao: " + i + "\n";
            }
        }

        return aux;
    }

    public String variancia() {
        int sum = 0;
        int qntdElementos = this.size;

        for(int i = 0; i < this.frequencia.length; i++) {
            if(this.frequencia[i] != null) {
                sum += this.frequencia[i].value;
            }
        }

        double media = (double) sum / qntdElementos;
        double desvio = 0;

        for(int i = 0; i < this.frequencia.length; i++) {
            if(this.frequencia[i] != null) {
                desvio += Math.pow((this.frequencia[i].value - media), 2); 
            }
        }

        double variancia = desvio / qntdElementos;
        return String.format("%.2f", variancia);
    }

    /**
     * Retorna o número de colisões que ocorreram durante inserções.
     * @return Número de colisões.
     */
    public int getCollisions(){
        return this.collisions;
    }

    /**
     * Retorna o número de elementos atualmente na tabela.
     * @return Número de elementos.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Retorna o array interno da tabela.
     * @return Array de pares (Pair[]).
     */
    public Pair[] getTable() {
        return this.table;
    }

    /**
     * Retorna o tamanho total da tabela.
     * @return Tamanho da tabela.
     */
    public int getTableSize() {
        return SIZE_TABLE;
    }
}
/**
 * Representa um par chave-valor da HashTable.
 */
class Pair {
    Integer key;
    int value;

    /**
     * Construtor de Pair.
     * @param key Chave do par.
     * @param value Valor associado à chave.
     */
    public Pair(Integer key, int value){
        this.key = key;
        this.value = value;
    }
}
