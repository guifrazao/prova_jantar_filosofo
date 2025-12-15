package test.tarefa1;

import java.util.Random;

public class Filosofo implements Runnable {

    private final Random random = new Random();
    private final String nome;
    private  Garfo garfoEsquerdo;
    private Garfo garfoDireito;

    private Filosofo proximo;

    public Filosofo(String nome, Garfo garfoEsquerdo, Garfo garfoDireito) {
        this.nome = nome;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
    }

    public String getNome(){
        return this.nome;
    }

    public void setProximoFilosofo(Filosofo next) {
        this.proximo = next;
    }

    @Override
    public void run() {
        while (true){
            log("tentou pegar o garfo " + garfoEsquerdo.getNome());
            synchronized (garfoEsquerdo){ //tentativa de pegar o garfo esquerdo (acesso ao recurso compartilhado)
                log("pegou o garfo " + garfoEsquerdo.getNome());
                log("tentou pegar o garfo " + garfoDireito.getNome());

                synchronized (garfoDireito){
                    log("pegou o garfo " + garfoDireito.getNome());
                    log("pegou os garfos " + garfoEsquerdo.getNome() + " e " + garfoDireito.getNome() + " e começou a jantar"); //log de quando o filosofo começa a jantar

                    this.jantar();
        
                    synchronized(this.proximo) {
                        this.proximo.notify(); //acorda a próxima thread
                    }
        
                    this.pensar();
                }
            }
        }
    }


    public void jantar() {

        final long tempoComendo = this.random.nextLong(1000, 3000); //comendo de 1 a 3 segundos
        final long tempoInicial = System.currentTimeMillis();

        log("esta jantando por " + String.valueOf(tempoComendo / 1000.) + " segundos");

        while (System.currentTimeMillis() - tempoInicial < tempoComendo);

        log("terminou de jantar!");
    }

    public void pensar() {
        final long tempoPensando = this.random.nextLong(1000, 3000);

        log("esta jantando por " + String.valueOf(tempoPensando / 1000.) + " segundos");

        try {

            synchronized(this) {
                this.wait(tempoPensando);
            }   

        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void log(String msg){
        System.out.println("Filosofo " + this.nome + " " + msg);       
    }
}