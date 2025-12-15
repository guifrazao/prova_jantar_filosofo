package test.tarefa1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FilosofoTestavel extends Filosofo { //Classe Filosofo adapdata para testes (contagem de quantas vezes jantou e mecanismo para parar o teste já que roda em loop infinito)

    private final AtomicInteger vezesQueJantou = new AtomicInteger(0);
    private final AtomicLong tempoTotalEspera = new AtomicLong(0);

    private long inicioEspera;

    private volatile boolean rodando = true;

    public FilosofoTestavel(String nome, Garfo esq, Garfo dir) {
        super(nome, esq, dir);
    }

    @Override
    public void jantar() {
        this.inicioEspera = System.currentTimeMillis();
        super.jantar();
        vezesQueJantou.incrementAndGet();
        long fimEspera = System.currentTimeMillis();
        tempoTotalEspera.addAndGet(fimEspera-inicioEspera);
    }

    public int getVezesQueJantou() {
        return vezesQueJantou.get();
    }

    public long getTempoTotalEspera(){
        return tempoTotalEspera.get();
    }

    public double getTempoMedioEspera() {
        int v = vezesQueJantou.get();
        return v == 0 ? 0 : (double) tempoTotalEspera.get() / v;
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