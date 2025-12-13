package test.tarefa2;

import java.util.concurrent.atomic.AtomicInteger;

public class FilosofoTestavel extends Filosofo { //Classe Filosofo adapdata para testes (contagem de quantas vezes jantou e mecanismo para parar o teste já que roda em loop infinito)

    private final AtomicInteger vezesQueJantou = new AtomicInteger(0);
    private volatile boolean rodando = true;

    public FilosofoTestavel(int id, Garfo esq, Garfo dir) {
        super(id, esq, dir);
    }

    @Override
    public void jantar() {
        super.jantar();
        vezesQueJantou.incrementAndGet();
    }

    public int getVezesQueJantou() {
        return vezesQueJantou.get();
    }

    public void parar() {
        rodando = false;
    }

    @Override
    public void run() {
        while (rodando) {
            super.run();
        }
    }
}