package main.java;

/**
 * Enum {@code HashType} define diferentes estratégias de funções hash
 * para mapeamento de chaves inteiras em uma tabela hash de tamanho fixo [10007].
 *
 * Cada constante implementa a interface {@link HashFunction} e 
 * possui sua própria lógica de dispersão.
 */
public enum HashType implements HashFunction {
    
    /**
     * Método da Divisão.
     * <p>
     * Calcula o índice como:
     * <pre>
     *     h(k) = k % tableSize
     * </pre>
     * É simples e eficiente, mas pode gerar muitas colisões se
     * {@code tableSize} não for um número primo distante de potências de 2.
     */
    DIVISION {
        @Override
        public int hash(int key, int tableSize) {
            // Implementação pendente: return key % tableSize;
            return -1;
        }
    },
    /**
     * Método da Multiplicação.
     * <p>
     * Usa uma constante A no intervalo (0,1), geralmente derivada
     * da razão áurea, para calcular:
     * <pre>
     *     h(k) = floor( tableSize * ( (k * A) % 1 ) )
     * </pre>
     * A constante A deve ser cuidadosamente escolhida para garantir
     * boa dispersão.
     */
    MULTIPLICATION {
        @Override
        public int hash(int key, int tableSize) {
		double frac = (key * A) % 1;
		return (int) (tableSize * frac);
        }
    },

    /**
     * Método baseado no Teorema do Resto Chinês (TCR).
     * <p>
     * Utiliza módulos fixos (15, 26, 77), seus respectivos
     * produtos parciais e inversos modulares para reconstruir
     * um número único congruente a cada resto simultaneamente.
     * <p>
     * Fórmula:
     * <pre>
     *     X ≡ a1 (mod 15)
     *     X ≡ a2 (mod 26)
     *     X ≡ a3 (mod 77)
     *     h(k) = (X % M) % tableSize
     * </pre>
     * onde M = 30030.
     */
    TCR{
        @Override
        public int hash(int key, int tableSize) {    
            int result = 0;
            for (int i = 0; i < MODULOS_FIXOS.length; i++) {
                int resto = key % MODULOS_FIXOS[i];
                result += resto * MI[i] * YI[i];
            }
            result %= M;
            return result % tableSize;
        }
    },

    /**
     * Método do Folding (dobra).
     * <p>
     * Converte a chave em string, divide em blocos de 3 dígitos
     * (do final para o começo) e soma esses blocos.
     * <p>
     * Fórmula:
     * <pre>
     *     h(k) = (soma dos blocos) % tableSize
     * </pre>
     */
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

    /**
     * Método Shift-XOR.
     * <p>
     * Percorre cada dígito da chave, aplica um deslocamento
     * de bits e combina os resultados usando a operação XOR.
     * <p>
     * Fórmula simplificada:
     * <pre>
     *     h(k) = (XOR de todos os dígitos deslocados) % tableSize
     * </pre>
     */
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


    // ==================================
    // Constante do método multiplicação
    // ==================================
    private static final double A  = 0.6180339887;
    /* Constante A vem do inverso da razão aurea*/

    // =========================
    // Constantes do método TCR
    // =========================

    /** Módulos fixos utilizados pelo TCR (todos coprimos entre si). */
    private static final int[] MODULOS_FIXOS = {15, 26, 77}; 
    /** Produto total dos módulos fixos (15 * 26 * 77 = 30030). */
    private static final int M = 15 * 26 * 77; 
    /** Produtos parciais (M / mi) para cada módulo. */
    private static final int[] MI = {2002, 1155, 390};
    /** Inversos modulares correspondentes aos produtos parciais. */
    private static final int[] YI = {13, 19, 31}; 
}
