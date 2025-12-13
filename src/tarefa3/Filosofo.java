import java.util.Random;
import java.util.concurrent.Semaphore;

public class Filosofo implements Runnable {

    private final Random random = new Random();
    private final int id; //O identificador foi mudado para um ID 
    private  Garfo garfoEsquerdo;
    private Garfo garfoDireito;

    private static Semaphore semaforoGeral = new Semaphore(4); //semáforo que controla o número de filósofos que tentam pegar os garfos ao mesmo tempo

    public Filosofo(int id, Garfo garfoEsquerdo, Garfo garfoDireito) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
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
                this.pegarGarfos();
                this.jantar();
                this.soltarGarfos();
                this.pensar();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }


    public void pegarGarfos() throws InterruptedException{ //função pegarGarfos implementada pra ajudar na legibilidade do código
        semaforoGeral.acquire(); //filosofo pede acesso aos garfos disponíveis

        log("tentou pegar o garfo " + this.garfoEsquerdo.getNome());
        garfoEsquerdo.pegar(); //filosofo pede acesso ao garfo esquerdo
        log("pegou o garfo " + this.garfoEsquerdo.getNome());

        log("tentou pegar o garfo " + this.garfoDireito.getNome());
        garfoDireito.pegar(); //filosofo pede acesso ao garfo direito
        log("pegou o garfo " + this.garfoDireito.getNome());

        log("pegou os garfos " + this.garfoEsquerdo.getNome() + " e " + this.garfoDireito.getNome() + " e começou a jantar"); //log de quando o filosofo começa a jantar
    }  
    
    public void soltarGarfos(){
        garfoEsquerdo.soltar();
        garfoDireito.soltar();
        semaforoGeral.release(); //filosofo libera acesso aos garfos esquerdo e direito e os torna disponíveis para outros filósofos

        log("soltou os garfos " + garfoEsquerdo.getNome() + " e " + garfoDireito.getNome());
    }

    public void jantar() {

        final long tempoComendo = this.random.nextLong(1000, 5000); //comendo de 1 a 5 garfoDireitos
        final long tempoInicial = System.currentTimeMillis();

        System.out.println(
            "Filosofo " + this.id + " está jantando por " +
            String.valueOf(tempoComendo / 1000.) + " segundos");

        while (System.currentTimeMillis() - tempoInicial < tempoComendo); //loop para simular o filosofo jantando

        System.out.println(
            "Filosofo " + this.id + 
            " terminou de jantar!");
    }

    public void pensar() {
        final long tempoPensando = this.random.nextLong(1000, 3000);

        System.out.println(
                " *** Filosofo " + this.id + 
                " está pensando por " + String.valueOf(tempoPensando / 1000.) + " segundos");

        try {
            Thread.sleep(tempoPensando);

        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void log(String msg){
        System.out.println("Filosofo " + this.id + " " + msg);       
    }
}