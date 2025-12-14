package tarefa2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        
        List<Thread> threadsFilosofos = new ArrayList<>();
        List<Garfo> listaGarfos = new ArrayList<>();

        Filosofo primeiroFilosofo = null;
        Filosofo ultimoFilosofo = null;

        for (int i = 0; i < 5; i++){
            Garfo g = new Garfo("G"+ String.valueOf(i + 1));

            listaGarfos.add(g);
        }

        for (int i = 0; i < 5; i++) {
            Garfo garfoEsq = listaGarfos.get(i);
            Garfo garfoDir = listaGarfos.get((i + 1) % 5);        

            Filosofo f = new Filosofo(i + 1, garfoEsq, garfoDir);

            if (primeiroFilosofo == null)
                primeiroFilosofo = f;

            if (ultimoFilosofo != null)
                ultimoFilosofo.setProximoFilosofo(f);

            threadsFilosofos.add(new Thread(f));

            ultimoFilosofo = f;
        }

        ultimoFilosofo.setProximoFilosofo(primeiroFilosofo);

        for (Thread t : threadsFilosofos)
            t.start();
            

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
