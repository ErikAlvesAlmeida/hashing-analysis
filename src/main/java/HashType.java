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
            int size = 3;
            int sum = 0;
            String keyStr = String.valueOf(key);

            for(int i = keyStr.length(); i > 0; i -= size) {
                int start = Integer.max(i - size, 0);
                sum += Integer.parseInt(keyStr.substring(start, i));
            }
            
            return sum % tableSize;
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
