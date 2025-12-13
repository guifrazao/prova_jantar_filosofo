package test.tarefa2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class FilosofoTeste {

    @Test
    public void testeJantarFIlosofo() throws InterruptedException {

        int NUM_FILOSOFOS = 5;
        long TEMPO_TESTE_MS = 120000; // 2 minutos

        List<Garfo> garfos = new ArrayList<>();
        List<FilosofoTestavel> filosofos = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        // Criação dos garfos
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            garfos.add(new Garfo("G" + (i + 1)));
        }

        FilosofoTestavel primeiro = null;
        FilosofoTestavel ultimo = null;

        // Criação dos filosofos
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            FilosofoTestavel f = new FilosofoTestavel(i + 1, garfos.get(i),garfos.get((i + 1) % NUM_FILOSOFOS));

            if (primeiro == null) 
                primeiro = f;
            if (ultimo != null) 
                ultimo.setProximoFilosofo(f);

            filosofos.add(f);
            threads.add(new Thread(f));
            ultimo = f;
        }

        ultimo.setProximoFilosofo(primeiro);

        threads.forEach(Thread::start);

        //Execução do teste
        Thread.sleep(TEMPO_TESTE_MS);

        //Encerrar a execução
        filosofos.forEach(FilosofoTestavel::parar);
        threads.forEach(Thread::interrupt);

        //Estatisticas
        for (FilosofoTestavel f : filosofos) {
            System.out.println("Filósofo " + f.getId() + " jantou " +f.getVezesQueJantou() + " vezes");

            assertTrue("Filósofo " + f.getId() + " nunca jantou (starvation detectada)", f.getVezesQueJantou() > 0);
        }
    }
}