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

    public void setProximoFilosofo(Filosofo next) {
        this.proximo = next;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("Filosofo " + this.nome + " tentou pegar o garfo " + garfoEsquerdo.getNome()); //log da tentativa de pegar o garfo esquerdo
            synchronized (garfoEsquerdo){ //tentativa de fato de pegar o garfo esquerdo (acesso ao recurso compartilhado)
                System.out.println("*Filosofo " + this.nome + " pegou o garfo " + garfoEsquerdo.getNome());
                System.out.println("Filosofo " + this.nome + " tentou pegar o garfo " + garfoDireito.getNome()); //log da tentativa de pegar o garfo direito

                synchronized (garfoDireito){
                    System.out.println("*Filosofo " + this.nome + " pegou o garfo " + garfoDireito.getNome());
                    System.out.println("Filosofo " + this.nome + " pegou os garfos " + 
                                        this.garfoEsquerdo.getNome() + " e " + this.garfoDireito.getNome() + " e começou a jantar"); //log de quando o filosofo começa a jantar

                    this.jantar();
        
                    synchronized(this.proximo) {
                        this.proximo.notify();
                    }
        
                    this.pensar();
                }
            }
        }
    }


    public void jantar() {

        final long tempoComendo = this.random.nextLong(1000, 5000); //comendo de 1 a 5 segundos
        final long tempoInicial = System.currentTimeMillis();

        System.out.println(
            "Filosofo " + this.nome + " está jantando por " +
            String.valueOf(tempoComendo / 1000.) + " segundos");

        while (System.currentTimeMillis() - tempoInicial < tempoComendo);

        System.out.println(
            "Filosofo " + this.nome + 
            " terminou de jantar!");
    }

    public void pensar() {
        final long tempoPensando = this.random.nextLong(1000, 3000);

        System.out.println(
                " *** Filosofo " + this.nome + 
                " está pensando por " + String.valueOf(tempoPensando / 1000.) + " segundos");

        try {

            synchronized(this) {
                this.wait(tempoPensando);
            }   

        } catch(InterruptedException e) {

        }
    }
}