import java.util.concurrent.Semaphore;

public class Garfo{
    private final String nome;
    private final Semaphore semaforo = new Semaphore(4);

    public Garfo(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public void pegar() throws InterruptedException{
        semaforo.acquire();
    }

    public void soltar(){
        semaforo.release();
    }
}
