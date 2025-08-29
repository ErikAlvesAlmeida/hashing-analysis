package main.java;

public enum HashType implements HashFunction {
    DIVISION {
        @Override
        public int hash(int key, int tableSize) {
            // implementar
            return -1;
        }
    },
    MULTIPLICATION {
        @Override
        public int hash(int key, int tableSize) {
            // implementar
            return -1;
        }
    },
    TCR{
        @Override
        public int hash(int key, int tableSize) {
            // implementar
            return -1;
        }
    },
    FOLDING{
        @Override
        public int hash(int key, int tableSize) {
            // implementar
            return -1;
        }
    },
    SHIFT_XOR{
        @Override
        public int hash(int key, int tableSize) {
            int h = 0;
            while (key > 0) {
               int digit = key % 10;
                int desloca = digit << 1;
                h ^= desloca;
             key /= 10;
        }
        return Math.abs(h) % tableSize;
        }
    };
}
