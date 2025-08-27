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
            // implementar
            return -1;
        }
    };
}
