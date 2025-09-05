package benchmark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import hash.HashTable;
import hash.HashType;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations=0)
@Measurement(iterations=1)
@Fork(value=1)

public class HashTableCollisionBenchmark {

    @Param({"DIVISION", "MULTIPLICATION", "TCR", "FOLDING", "SHIFT_XOR"})
    private String hashType;

    @Param({
            "data/repetidos_pequena.csv",
            "data/unicos_pequena.csv",
            "data/sequenciais_pequena.csv",
            "data/repetidos_media.csv",
            "data/unicos_media.csv",
            "data/sequenciais_media.csv",
            "data/repetidos_grande.csv",
            "data/unicos_grande.csv",
            "data/sequenciais_grande.csv"
    })
    private String datasetPath;

    private int[] keys;
    private HashTable table;

    @Setup(Level.Invocation)
    public void setup() throws IOException {
        // carregar dados do CSV
        try (BufferedReader br = new BufferedReader(new FileReader(datasetPath))) {
            List<Integer> values = new ArrayList<>();
            String line = br.readLine(); // descartar cabeçalho
            while ((line = br.readLine()) != null) {
                values.add(Integer.parseInt(line.trim()));
            }
            keys = values.stream().mapToInt(Integer::intValue).toArray();
        }

        // criar tabela com função hash escolhida
        table = new HashTable(HashType.valueOf(hashType));
    }

    @Benchmark
    public void insertAllKeys() {
        for (int key : keys) {
            table.put(key, key);
        }
    }

    @TearDown(Level.Trial)
    public void reportCollisions() {
        int i = 1;
        System.out.println("HashType=" + hashType +
                " | Dataset=" + datasetPath +
                " | Size=" + table.getSize() +
                " | Collisions=" + table.getCollisions() +
                " | vezes = " + i++);
    }
}