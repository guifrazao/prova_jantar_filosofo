package test.tarefa3;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class FilosofoTeste {
    @Test
    public void testeJantarFIlosofo() throws InterruptedException {

        int NUM_FILOSOFOS = 5;
        long TEMPO_TESTE_MS = 120000; //2 minutos

        List<Garfo> garfos = new ArrayList<>();
        List<FilosofoTestavel> filosofos = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        //Criação dos garfos
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            garfos.add(new Garfo("G" + (i + 1)));
        }

        //Criação dos filosofos
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            FilosofoTestavel f = new FilosofoTestavel(i + 1, garfos.get(i), garfos.get((i + 1) % NUM_FILOSOFOS));

            filosofos.add(f);
            threads.add(new Thread(f));
        }

        threads.forEach(Thread::start);

        //Execução do teste
        Thread.sleep(TEMPO_TESTE_MS);

        //Encerrar a execução
        filosofos.forEach(FilosofoTestavel::parar);
        threads.forEach(Thread::interrupt);

        //Estatisticas
        int totalJantas = 0;

        for (FilosofoTestavel f : filosofos) {
            int v = f.getVezesQueJantou();
            totalJantas += v;

            System.out.println(
                "Filósofo " + f.getId() +
                " | Refeições: " + v +
                " | Tempo médio de espera: " +
                String.format("%.2f ms", f.getTempoMedioEspera())
            );

            assertTrue("Filósofo " + f.getId() + " nunca jantou (starvation detectada)", v > 0);
        }

        System.out.println("Total de jantas: " + totalJantas);
    }
}