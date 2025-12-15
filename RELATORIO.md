
# Relatório Final

## Introdução

O jantar dos filósofos é um problema clássico de programação distribuída e paralela. Esse exercício consiste em uma simulação de alguns filósofos jantando, sendo que um filósofo pega dois garfos, janta por um determinado período, solta os garfos e pensa por um determinado período. O problema surge ao perceber que a quantidade de garfos é igual à quantidade de filósofos, fazendo com que nunca seja possível que todos os filósofos jantem ao mesmo tempo, assim gerando a necessidade de controlar o acesso aos garfos, ou seja, ao recurso compartilhado pelas threads, que são representadas pelos filósofos. Esse exercício abre brecha para a introdução e solução de diversos problemas relacionados à programação paralela, como deadlocks e starvation.

## Metodologia

Para observar o comportamento das tarefas realizadas, foram implementados testes utilizando JUnit, a fim de detectar starvation, deadlocks e coletar diversas estatísticas relevantes à medição da performance do sistema e para determinar se o sistema soluciona o problema do jantar dos filósofos de maneira eficaz. Vale destacar que a implementação dos testes e suas classes varia para cada tarefa. Para implementar de fato estes testes, foi criada uma classe FilosofoTestavel para adicionar atributos pertinentes ao teste, como atributos que guardam o tempo médio e total de espera, por exemplo. Implementada a classe FilosofoTestavel, implementou-se também a classe FilosofoTeste, que é onde o JUnit foi utilizado. Essa classe funciona de maneira semelhante à classe Main, criando os garfos e filósofos e iniciando o jantar, e, como mencionado anteriormente, detecta se há starvation ou deadlock no programa e coleta estatísticas. Ao fim da execução, as estatísticas são impressas. Aonde elas são impressas varia de acordo com a IDE utilizada, geralmente sendo impressas no terminal.

## Resultados

As tabelas abaixo trazem uma comparação entre as métricas coletadas de todas as tarefas durante uma execução que durou 5 minutos:

| Tarefa | Total de jantas | Coeficiente de variação (aproximado) | Taxa de utilização dos garfos (aproximado) | Deadlock
| :--- | :--- | :--- | :--- | :--- |
| Tarefa 2 | 111 | 0,17 | 0,15 | Ausente |
| Tarefa 3 | 354 | 0,03 | 0,49 | Ausente |
| Tarefa 4 | 190 | 0,01 | 0,21 | Ausente |

## Análise

Cada solução tem suas vantagens e desvantagens. A tarefa 2 traz uma solução que faz com que um dos filósofos pegue o garfo na direção oposta dos outros filósofos (direito antes do esquerdo enquantos os outros pegam o esquerdo primeiro, por exemplo). Essa solução garante que não haverá deadlock por conta da espera circular, mas não apresenta prevenção contra starvation já que qualquer filósofo pronto pode acessar os garfos a qualquer momento, não importando quantas vezes ele jantou ou não. Comparadas às outras soluções, esta é mais simples de entender porém possui desempenho significativamente inferior à tarefa 3, como observado nos resultados acima, e possui a possibilidade de starvation, que foi eliminada na tarefa 4.

Já a tarefa 3 apresenta uma solução utilizando semáforos. Estes semáforos controlam quantos filósofos podem pegar garfos ao mesmo tempo. A limitação implementada foi de que no máximo quatro filósofos podem tentar pegar garfos ao mesmo tempo e apenas um filósofo pode acessar um determinado garfo por vez. Com essa limitação, o deadlock é eliminado visto que, por conta de pelo menos um filósofo não poder tentar pegar garfos ao mesmo tempo que os outros, sempre haverá garfos o bastante para que um filósofo comece a jantar. A utilização de semáforos resultou em um total de jantares três vezes maior do que na tarefa 2 e um pouco maior que 50% em comparação à tarefa 4, demonstrando o desempenho superior dessa tarefa em comparação às outras. Apesar disso, a possibilidade de starvation ainda existe nessa tarefa, pois, assim como na tarefa 2, qualquer filósofo pronto pode acessar os garfos mesmo que tenha jantado mais que outros filósofos. Vale mencionar que a complexidade de implementação desta solução é maior do que as outras, visto que, caso seja implementada de maneira errada, o deadlock não será eliminado, além de ser mais difícil de reproduzir bugs graças à essa complexidade maior.

A tarefa 4 traz uma solução com monitor. O monitor, representado pela classe Mesa, controla o acesso dos filósofos aos garfos, fazendo com que um filósofo acesse os garfos apenas se dois garfos estiverem disponíveis. Esse monitor conta com uma fila, garantindo que todos os filósofos acessem os garfos de maneira justa. O deadlock é eliminado uma vez que os filósofos só tem acesso aos garfos se dois garfos estiverem disponíveis. Já o starvation é eliminado graças à fila implementada no monitor. Essa solução com monitor, apesar de possuir um desempenho inferior ao da tarefa 3, elimina o starvation.

## Conclusão

Caso queira uma eliminar o deadlock de maneira simples ao custo do desempenho e com a possibilidade de starvation, use a solução da tarefa 2. 

Caso não se importe com a complexidade de implementação maior para ter uma performance significativamente mais elevada do que as outras soluções, utilize a solução 3. Ela elimina o deadlock assim como as outras soluções, porém ainda possui a possibilidade de starvation presente na tarefa 2.

Caso não queira nenhum problema presente na solução apesar de um desempenho mediano, utilize a solução da tarefa 4. Enquanto ela não possui a simplicidade da tarefa 2, mesmo que também não possua a complexidade da tarefa 3, ou o desempenho da tarefa 3, esta solução elimina tanto o deadlock quanto a possibilidade de starvation.