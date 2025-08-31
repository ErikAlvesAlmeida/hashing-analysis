package main.java;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;

public class DivisaoHash {

    public static class HashTable {

        private int chave;
        private int valor;

        public HashTable(int chave, int valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public int getChave() {
            return this.chave;
        }

        public int getValor() { 
            return this.valor;
        }

        public void set(int chave, int valor) {
            if (chave == this.chave)
                this.valor = valor;
        }
    }

    private ArrayList<HashTable>[] tabela;
    private static final int TAMANHO_TABELA = 53;
    
    @SuppressWarnings("unchecked")
    public DivisaoHash() {
        this.tabela = new ArrayList[TAMANHO_TABELA];
        for (int i = 0; i < this.TAMANHO_TABELA; i++)
            this.tabela[i] = new ArrayList<>();
    }

    private int hash(int chave) {
        return chave % this.tabela.length;
    }

    public HashTable get(int chave) {
        int hash = hash(chave);
        ArrayList<HashTable> hashes = this.tabela[hash];

        if (hashes == null)
            return null;
        
        for (HashTable h : hashes) {
            if (h.getChave() == chave)
                return h;
        }
        return null;
    }

    public void put(int chave, HashTable valor) {
        int hash = hash(chave);
        ArrayList<HashTable> hashes = this.tabela[hash];
        
        for (int i = 0; i < hashes.size(); i++) {
            if (hashes.get(i).getChave() == chave) {
                hashes.get(i).set(chave, valor.getValor());
                return;
            }
        }
        hashes.add(valor);
    }

    public HashTable remove(int chave) {
        int hash = hash(chave);
        ArrayList<HashTable> hashes = this.tabela[hash];

        Iterator<HashTable> it = hashes.iterator();
        HashTable h = null;

        while (it.hasNext()) {
            h = it.next();
            if (h.getChave() == chave) {
                it.remove();
                return h;
            }
        }
        return h;
    }

    private int colisaoDiv = 0;
    public void adicionaNaDiv(int chave, ArrayList<Integer>[] tabela) {
        int hash = hash(chave);
        if (tabela[hash] == null)
            tabela[hash] = new ArrayList<>();
        if (!tabela[hash].isEmpty()) colisaoDiv++;
        tabela[hash].add(chave);
    }

    private int colisaoMult = 0;
    public int adicionaNaMult(int chave) {
        double a = 0.617648934;
        double hash = chave * a;
        hash = (hash % 1) * this.tabela.length;
        return (int)hash;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ArrayList<Integer>[] listaDiv = new ArrayList[53];
        ArrayList<Integer>[] listaMult = new ArrayList[53];
        DivisaoHash dh = new DivisaoHash();

        try {
            File arquivo = new File("data/keys.txt");
            Scanner sc = new Scanner(arquivo);
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                int valor = Integer.parseInt(linha);
                dh.adicionaNaDiv(valor, listaDiv);
                int hashMult = dh.adicionaNaMult(valor);
                if (listaMult[hashMult] == null)
                    listaMult[hashMult] = new ArrayList<>();
                if (!listaMult[hashMult].isEmpty())
                    dh.colisaoMult++;
                listaMult[hashMult].add(valor);
            }
        } catch(Exception e) {
            System.out.println("arquivo não encontrado!");
        }

        for (int i = 0; i < listaDiv.length; i++) {
            System.out.print(i + ", ");
            if (listaDiv[i] != null)
                System.out.print(listaDiv[i]);
            else System.out.println("[] ");
        }

        for (int i = 0; i < listaMult.length; i++) {
            System.out.print(i + ", ");
            if (listaMult[i] != null)
                System.out.print(listaMult[i]);
            else System.out.println("[] ");
        }
    }
}