package test.tarefa3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FilosofoTestavel extends Filosofo { //Classe Filosofo adapdata para testes (contagem de quantas vezes jantou e mecanismo para parar o teste já que roda em loop infinito)

    private final AtomicInteger vezesQueJantou = new AtomicInteger(0);
    private final AtomicLong tempoTotalEspera = new AtomicLong(0);

    private long inicioEspera; //guarda o horário em que o filósofo tenta pegar garfos

    private volatile boolean rodando = true;

    public FilosofoTestavel(int id, Garfo esq, Garfo dir) {
        super(id, esq, dir);
    }

    @Override
    public void jantar() {
        super.jantar();
        vezesQueJantou.incrementAndGet();
    }

    @Override
    public void pegarGarfos() throws InterruptedException{
        inicioEspera = System.currentTimeMillis();
        super.pegarGarfos();
        long fimEspera = System.currentTimeMillis();
        tempoTotalEspera.addAndGet(fimEspera - inicioEspera);
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