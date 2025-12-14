package tarefa4;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        int n = 5;
        Mesa mesa = new Mesa(n);
        List<Thread> threadsFilosofos = new ArrayList<>();

        for (int i = 0; i < n; i++){
            threadsFilosofos.add(new Thread(new Filosofo(i, mesa)));
        }

        for (Thread t : threadsFilosofos){
            t.start();
        }

        try {   
            Thread.sleep(120000); //execução do programa por pelo menos 2 minutos
        } catch(InterruptedException e) {

        }

        System.out.println("Programa encerrado");
        for (Thread t : threadsFilosofos){
            if (t.isAlive()){
                t.interrupt();
            }
        }
        System.exit(0);
    }
}
