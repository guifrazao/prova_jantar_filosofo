package test.tarefa3;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class FilosofoTeste {
    @Test
    public void testeJantarFIlosofo() throws InterruptedException {

        int NUM_FILOSOFOS = 5;
        long TEMPO_TESTE_MS = 300000; //2 minutos

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
        double soma = 0;

        for (FilosofoTestavel f : filosofos) {
            int v = f.getVezesQueJantou();
            totalJantas += v;
            soma += v;

            System.out.println(
                "Filósofo " + f.getId() +
                " | Refeições: " + v +
                " | Tempo médio de espera: " +
                String.format("%.2f ms", f.getTempoMedioEspera())
            );

            assertTrue("Filósofo " + f.getId() + " nunca jantou (starvation detectada)", v > 0);
        }

        System.out.println("Total de jantas: " + totalJantas);

         //Taxa de coeficience de variação e utilização dos garfos

        double somaQuadrados = 0;
        double media = soma / NUM_FILOSOFOS;

        for (FilosofoTestavel f : filosofos) {
            double diff = f.getVezesQueJantou() - media; //(xi - μ) da fórmula da variância
            somaQuadrados += diff * diff; //(xi - μ)­­² da fórmula da variância
        }

        double variancia = somaQuadrados / NUM_FILOSOFOS;
        double desvioPadrao = Math.sqrt(variancia);
        double coeficienteVariacao = desvioPadrao / media;


        double TEMPO_TESTE_S = TEMPO_TESTE_MS/1000; //cálculo da taxa de utilização dos garfos deve ser em segundos
        double utilizacaoGarfos = (2.0 * totalJantas) / (NUM_FILOSOFOS * TEMPO_TESTE_S);

        System.out.println("Coeficiente de variação: " + String.format("%.4f", coeficienteVariacao));
        System.out.println("Taxa de utilização dos garfos: " + String.format("%.4f refeições/segundo/garfo", utilizacaoGarfos));
    }
}