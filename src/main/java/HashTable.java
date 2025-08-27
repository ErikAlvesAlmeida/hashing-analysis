package main.java;

public class HashTable {
    private Pair[] table;
    private HashFunction hashFunction;
    private int currSize;
    private double load_factor = 0.80;

    private final int SIZE_TABLE = 10007;
    private final Pair DELETED = new Pair(null, -1);

    public  HashTable(HashFunction hashFunction){
        this.table = new Pair[SIZE_TABLE];
        this.hashFunction = hashFunction;
        this.currSize = 0;
    }

    //ainda vou implementar
}

class Pair {
    Integer key;
    int value;

    public Pair(Integer key, int value){
        this.key = key;
        this.value = value;
    }
}
