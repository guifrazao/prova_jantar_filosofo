package test.tarefa4;

import java.util.LinkedList;
import java.util.Queue;

public class Mesa {

    private final int numGarfos;
    private final boolean[] garfosLivres;
    private final Queue<Integer> fila;

    public Mesa(int n) {
        this.numGarfos = n;
        this.garfosLivres = new boolean[n];
        this.fila = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            garfosLivres[i] = true;
        }
    }

    private int getGarfoEsq(int id) {
        return id;
    }

    private int getGarfoDir(int id) {
        return (id + 1) % numGarfos;
    }

    public synchronized void pegarGarfos(int id) throws InterruptedException {
        fila.add(id);

        
        while (fila.peek() != id || !garfosLivres[getGarfoEsq(id)] || !garfosLivres[getGarfoDir(id)]) {
            wait();
        }

        //vez do filósofo e os garfos estão livres
        fila.poll();
        garfosLivres[getGarfoEsq(id)] = false;
        garfosLivres[getGarfoDir(id)] = false;

        System.out.println("Filósofo " + (id + 1) + " pegou os garfos");
    }

    public synchronized void soltarGarfos(int id) {
        garfosLivres[getGarfoEsq(id)] = true;
        garfosLivres[getGarfoDir(id)] = true;

        System.out.println("Filósofo " + (id + 1) + " soltou os garfos");

        notifyAll(); //acorda todas as threads
    }
}
