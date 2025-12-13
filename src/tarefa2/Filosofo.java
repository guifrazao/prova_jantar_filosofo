import java.util.Random;

public class Filosofo implements Runnable {

    private final Random random = new Random();
    private final int id; //O identificador foi mudado para um ID 
    private  Garfo garfoEsquerdo;
    private Garfo garfoDireito;

    private Filosofo proximo;

    public Filosofo(int id, Garfo garfoEsquerdo, Garfo garfoDireito) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
    }

    public int getId(){
        return this.id;
    }

    public void setProximoFilosofo(Filosofo next) {
        this.proximo = next;
    }

    @Override
    public void run() {
        while (true){
            if (this.id == 1){ //Filosofo 1 é escolhido arbitrariamente para pegar primeiro o garfo direito
                pegarGarfos(garfoDireito, garfoEsquerdo);
            }else{
                pegarGarfos(garfoEsquerdo, garfoDireito);
            }
        }
    }


    public void pegarGarfos(Garfo primeiro, Garfo segundo){ //função pegarGarfos implementada pra ajudar na legibilidade do código

        log("tentou pegar o garfo " + primeiro.getNome());
            synchronized (primeiro){ //tentativa de pegar o garfo esquerdo (acesso ao recurso compartilhado)
                log("pegou o garfo " + primeiro.getNome());
                log("tentou pegar o garfo " + segundo.getNome());

                synchronized (segundo){
                    log("pegou o garfo " + segundo.getNome());
                    log("pegou os garfos " + primeiro.getNome() + " e " + segundo.getNome() + " e começou a jantar"); //log de quando o filosofo começa a jantar

                    this.jantar();
        
                    synchronized(this.proximo) {
                        this.proximo.notify(); //acorda a próxima thread
                    }
        
                    this.pensar();
                }
            }
    }

    public void jantar() {

        final long tempoComendo = this.random.nextLong(1000, 5000); //comendo de 1 a 5 segundos
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

            synchronized(this) {
                this.wait(tempoPensando);
            }   

        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void log(String msg){
        System.out.println("Filosofo " + this.id + " " + msg);       
    }
}