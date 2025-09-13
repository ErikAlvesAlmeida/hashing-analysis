package hash;

/**
 * Enum {@code HashType} define diferentes estratégias de funções hash
 * para mapeamento de chaves inteiras em uma tabela hash de tamanho fixo [10007].
 *
 * Cada constante implementa a interface {@link HashFunction} e 
 * possui sua própria lógica de dispersão.
 */
public enum HashType implements HashFunction {
    
    /**
     * **Método da Divisão**
     * <p>
     * Calcula o índice simplesmente tirando o resto da divisão da chave
     * pelo tamanho da tabela:
     * <pre>
     *     h(k) = key % tableSize
     * </pre>
     * É rápido e direto, mas pode gerar muitas colisões se o
     * {@code tableSize} não for primo ou estiver muito próximo
     * de uma potência de 2.
     */
    DIVISION {
        @Override
        public int hash(int key, int tableSize) {
            return key % tableSize;
        }
    },
    /**
     * **Método da Multiplicação**
     * <p>
     * Usa uma constante real A no intervalo (0,1) — normalmente
     * relacionada à razão áurea — para espalhar melhor as chaves.
     * A ideia é:
     * <pre>
     *     h(k) = floor( tableSize * ( (k * A) % 1 ) )
     * </pre>
     * A escolha de A é fundamental para evitar padrões ruins
     * na distribuição.
     */
    MULTIPLICATION {
        @Override
        public int hash(int key, int tableSize) {
		double frac = (key * A) % 1;
		return (int) (tableSize * frac);
        }
    },

    /**
     * **Método do Teorema do Resto Chinês (TCR)**
     * <p>
     * Baseia-se em módulos fixos (15, 26 e 77). Para cada chave,
     * calcula os restos nesses módulos, combina usando os
     * inversos modulares e reconstrói um valor único congruente
     * a todos ao mesmo tempo.
     * <p>
     * Exemplo do processo:
     * <pre>
     *     X ≡ a1 (mod 15)
     *     X ≡ a2 (mod 26)
     *     X ≡ a3 (mod 77)
     *     h(k) = (X % M) % tableSize
     * </pre>
     * com M = 30030.
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
     * **Método do Folding (Dobra)**
     * <p>
     * Transforma a chave em string, quebra em blocos de
     * até 3 dígitos (de trás para frente) e soma os blocos.
     * <p>
     * Fórmula final:
     * <pre>
     *     h(k) = (soma dos blocos) % tableSize
     * </pre>
     */
    FOLDING{
        @Override
        public int hash(int key, int tableSize) {
            int sum = 0;
            String keyStr = String.valueOf(key);

            for(int i = keyStr.length(); i > 0; i -= SIZE_PARTS) {
                int start = Integer.max(i - SIZE_PARTS, 0);
                sum += Integer.parseInt(keyStr.substring(start, i));
            }
            
            return sum % tableSize;
        }
    },

    /**
     * **Método Shift-XOR**
     * <p>
     * Analisa a chave dígito por dígito, desloca os bits
     * de cada parte e combina tudo usando XOR.
     * <p>
     * Em resumo:
     * <pre>
     *     h(k) = (XOR dos dígitos deslocados) % tableSize
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
    // Constante A vem do inverso da razão aurea

    //====================================
    // Constante do método Folding
    //====================================
    private static final int SIZE_PARTS = 3;
    // Constante SIZE_PARTS define o tamanho dos intervalo em que a chave será dividida

    // =========================
    // Constantes do método TCR
    // =========================
    // Módulos fixos utilizados pelo TCR (todos coprimos entre si). */
    private static final int[] MODULOS_FIXOS = {15, 26, 77}; 
    // Produto total dos módulos fixos (15 * 26 * 77 = 30030). */
    private static final int M = 15 * 26 * 77; 
    // Produtos parciais (M / mi) para cada módulo. */
    private static final int[] MI = {2002, 1155, 390};
    // Inversos modulares correspondentes aos produtos parciais. */
    private static final int[] YI = {13, 19, 31}; 
}
