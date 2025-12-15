# Prova Final PGPD

Este repositório contém as 5 tarefas solicitadas na prova final de Programação Paralela e Distribuída, seguem abaixo as instruções de compilação e execução

---

## Estrutura do Projeto

docs/  
lib/  
src/  
├── tarefa1/  
├── tarefa2/  
├── tarefa3/  
└── tarefa4/  
teste/  
├── tarefa1/  
├── tarefa2/  
├── tarefa3/  
└── tarefa4/  
README.md  
RELATORIO.md


## Compilação e execução

Acesse um dos seguintes diretórios por meio de um dos seguintes comandos:

Caso queira acessar os programas em si:
```bash
cd src
```

Caso queira acessar os testes destes programas (Caso esteja usando o VSCode como IDE, vá para Debug Console no terminal ao rodar o teste para ver as estatísticas dos testes ao final da execução):
```bash
cd test
```

Após selecionar um diretório, compile o código por meio do seguinte comando:
```bash
javac nome_da_pasta_da_tarefa_que_deseja_compilar/*.java
```

Por fim, execute o programa por meio do seguinte comando:
```bash
java nome_da_pasta_da_tarefa_que_deseja_executar/Main
```

Caso deseje executar um teste, vá para o diretório da tarefa que deseja executar (test/nome_da_tarefa), vá para o arquivo FilosofoTeste e clique no botão de executar ao lado esquerdo da declaração da classe (public FilosofoTeste {})

## Link para o relatório comparativo

https://github.com/guifrazao/prova_jantar_filosofo/blob/main/RELATORIO.md

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

### Tarefa 2

A causa do deadlock na tarefa anterior ocorre pois todos os filósofos sempre pegam o garfo na mesma direção (esquerda ou direita), fazendo com que, no final, todos os garfos à direita de cada filósofo estejam ocupados. No momento que um dos filósofos pega o garfo direito antes do esquerdo, um dos filósofos obrigatóriamente terá ambos os garfos adjacentes a ele ocupados, permitindo que pelo menos um filósofo pegue dois garfos e coma.

Existe sim a possibilidade de starvation nesta versão. Quando um garfo é liberado, qualquer filósofo pronto pode pegá-lo, não importando se ele já jantou várias vezes ou não, fazendo com que exista a possibilidade de starvation caso um filósofo tenha muito azar com os garfos.

Em comparação com a versão da tarefa 1, esta versão é uma melhoria direta pois elimina o deadlock presente na tarefa 1, porém ambas ainda possuem a possibilidade de starvation presente em suas soluções.

### Tarefa 3

A versão nessa tarefa inclui semáforos. Estes semáforos limitam quantos filósofos podem pegar garfos ao mesmo tempo. O semáforo incluso na classe Filosofo limita a quantidade de filósofos que podem pegar garfos simultaneamente para 4. Com essa limitação, sempre sobrará pelo menos um garfo livre para que um dos filósofos que já possuem um garfo pegue-o, assim eliminando a possibilidade de deadlock.

Em comparação com o desempenho da tarefa anterior, o número total de jantares da tarefa anterior em uma execução de teste foi de 84 jantares. Já na execução de teste desta tarefa, o número total de jantares foi de 137, aproximadamente 61,32% mais jantares, demonstrando o desempenho superior desta tarefa.

A principal vantagem da utilização de semáforos é poder controlar quantas threads podem acessar um recurso simultaneamente, além da prevenção de deadlock caso utilizados corretamente. Em termos de desvantagens, a possibilidade de starvation da tarefa anterior ainda está presente já que não há controle de qual thread irá acessar um recurso, tornando possível que uma thread acesse um recurso várias vezes enquanto outra quase nunca o acesse. 

### Tarefa 4

Esta versão utiliza um monitor representado pela classe Mesa. O monitor age como um mediador que decidirá qual filósofo poderá pegar os garfos. O monitor possui uma fila inclusa em sua implementação. Por ser uma fila, o filósofo que acabou de comer será mandado para o final dela, garantindo que o mesmo filósofo não possa comer mais vezes que outro, ou seja, garantindo fairness. 

Essa solução previne o deadlock pois apenas um filósofo tem acesso à mesa por vez, garantindo que sempre existam garfos o bastante para que o filósofo comece a jantar. Já o starvation é previnido graças à fila mencionada anteriormente, sendo que esta garante que todos os filósofos acessem a mesa aproximadamente a mesma quantidade de vezes, fazendo com que nenhum filósofo coma significativamente menos que outros.

Essa versão elimina todos os problemas observados em versões anteriores, como o deadlock nas versão da tarefa 2 e o starvation nas versões da tarefa 2 e tarefa 3. Apesar disso, essa versão possui um número total de refeições significativamente menor do que as outras versões, possuindo 76 refeições totais, sendo aproximadamente 9,52% a menos de refeições do que a versão da tarefa 2 e 44,52% a menos do que a versão da tarefa 3.

Levando em conta o parágrafo anterior, caso queira uma versão com menos problemas possíveis, porém ao custo do desempenho, a versão da tarefa 4 é a versão ideal. Caso queira uma versão com desempenho significativamente maior, mas com a possibilidade de starvation, utilize a versão da tarefa 3. Caso queira uma versão com menos complexidade, mas com desempenho menor que a versão da tarefa 3 e com starvation não solucionado, utilize a versão da tarefa 2.
