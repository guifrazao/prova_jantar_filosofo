package tarefa4;

import java.util.Random;

public class Filosofo implements Runnable {

    private final Random random = new Random();
    private final int id; //O identificador foi mudado para um ID 

    private final Mesa mesa;

    public Filosofo(int id, Mesa mesa) {
        this.id = id;
        this.mesa = mesa;
    }

    public int getId(){
        return this.id;
    }

    // public void setProximoFilosofo(Filosofo next) { //Não é mais necessário com a implementação de semáforos
    //     this.proximo = next;
    // }

    @Override
    public void run() {
        while (true){
            try{
                mesa.pegarGarfos(id);
                this.jantar();
                mesa.soltarGarfos(id);
                this.pensar();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public void jantar() {

        final long tempoComendo = this.random.nextLong(1000, 3000); //comendo de 1 a 3 segundos
        final long tempoInicial = System.currentTimeMillis();

        log(" está jantando por " + String.valueOf(tempoComendo / 1000.) + " segundos");

        while (System.currentTimeMillis() - tempoInicial < tempoComendo); //loop para simular o filosofo jantando

        log("terminou de jantar!");
    }

    public void pensar() {
        final long tempoPensando = this.random.nextLong(1000, 3000);

        log("está pensando por " + String.valueOf(tempoPensando / 1000.) + " segundos");

        try {
            Thread.sleep(tempoPensando);

        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void log(String msg){
        System.out.println("Filosofo " + String.valueOf(this.id+1) + " " + msg);       
    }
}