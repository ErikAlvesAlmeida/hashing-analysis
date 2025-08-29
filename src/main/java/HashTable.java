package main.java;

import java.util.Objects;

/**
 * Implementação de uma tabela hash usando sondagem linear.
 * Permite inserção, busca e remoção de pares chave-valor (Integer, int).
 * Também mantém um contador de colisões para análise de dispersão.
 */
public class HashTable {
    private Pair[] table;
    private HashFunction hashFunction;
    private int currSize;
    private int collisions = 0;
    private double load_factor = 0.80; // quantidade de colisões até atingir 80% da capacidade, não está sendo usado pra nada, mas é só a título de curiosidade

    private final int SIZE_TABLE = 10007;
    private final Pair DELETED = new Pair(null, -1);

    /**
     * Construtor da HashTable.
     * @param hashFunction Função de hash que será usada para calcular os índices.
     */
    public  HashTable(HashFunction hashFunction){
        this.table = new Pair[SIZE_TABLE];
        this.hashFunction = hashFunction;
        this.currSize = 0;
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
        int i = 0;

        while(true){
            int pos = (hash + i) % SIZE_TABLE;
            if(this.table[pos] == null || this.table[pos] == DELETED){
                this.table[pos] = new Pair(key, value);
                this.currSize++;
                return;
            } 
            else if(Objects.equals(table[pos].key, key)){
                this.table[pos].value = value;
                return;
            } else {
                this.collisions++;
            }
            i++;
            if(i == SIZE_TABLE) break;
        }
    }

    /**
     * Recupera o valor associado a uma chave.
     * @param key Chave a ser buscada.
     * @return Valor associado como String ou null se a chave não existir.
     */
    public String get(Integer key){
        int hash = this.hashFunction.hash(key, SIZE_TABLE);
        int i = 0;
        while (true) {
            int pos = (hash + i) % SIZE_TABLE;
            if (this.table[pos] == null) break;
            if (this.table[pos] != DELETED && Objects.equals(this.table[pos].key, key)) {
                return Integer.toString(this.table[pos].value);
            }
            i++;
            if (i == SIZE_TABLE) break;
        }

        return null;
    }

    /**
     * Remove uma chave da tabela, marcando sua posição como deletada.
     * @param key Chave a ser removida.
     */
    public void remove(Integer key) {
        int hash = this.hashFunction.hash(key, SIZE_TABLE);
        int i = 0;

        while (true) {
            int pos = (hash + i) % SIZE_TABLE;
            if (this.table[pos] == null) break;
            if (this.table[pos] != DELETED && Objects.equals(this.table[pos].key, key)) {
                this.table[pos] = DELETED;
                this.currSize--;
                return;
            }
            i++;
            if (i == SIZE_TABLE) break;
        }
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
        return currSize;
    }

    /**
     * Retorna o array interno da tabela.
     * @return Array de pares (Pair[]).
     */
    public Pair[] getTable() {
        return table;
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
