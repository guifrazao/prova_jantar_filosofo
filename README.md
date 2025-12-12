# Prova Final PGPD

Este repositório contém as 5 tarefas solicitadas na prova final de Programação Paralela e Distribuída, seguem abaixo as instruções de compilação e execução

---

## Compilação e execução

Acesse o diretório da tarefa que deseja executar por meio do seguinte comando:
```bash
cd src/<nome_da_pasta_da_tarefa_que_deseja_acessar>
```

Estando no diretório desejado, compile o código por meio do seguinte comando:
```bash
javac Main.java
```

Por fim, execute o programa por meio do seguinte comando:
```bash
java Main
```

---

## Documentação pedida nos requisitos das tarefas

### Tarefa 1

Essa implementação do jantar dos filosofos pode resultar em deadlock pois pode acontecer que todos os filosofos peguem o garfo à sua esquerda (ou direita) no início do programa. Como cada um dos cinco filósofos possui um dos cinco garfos, nenhum filósofo conseguirá pegar dois garfos e jantar, causando starvation. O log abaixo gerado pelo programa exemplifica isto:

```bash
Filosofo F4 tentou pegar o garfo G4
Filosofo F2 tentou pegar o garfo G2
Filosofo F5 tentou pegar o garfo G5
Filosofo F3 tentou pegar o garfo G3
Filosofo F1 tentou pegar o garfo G1
*Filosofo F4 pegou o garfo G4      
*Filosofo F2 pegou o garfo G2      
*Filosofo F5 pegou o garfo G5      
*Filosofo F3 pegou o garfo G3      
*Filosofo F1 pegou o garfo G1      
Filosofo F4 tentou pegar o garfo G5
Filosofo F2 tentou pegar o garfo G3
Filosofo F5 tentou pegar o garfo G1
Filosofo F3 tentou pegar o garfo G4
Filosofo F1 tentou pegar o garfo G2
Fim do programa
```

Cada filósofo pega um garfo, fazendo com que nenhum filósofo consiga pegar outro garfo.