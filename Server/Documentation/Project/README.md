# Documentação do projeto final.

## Trabalho realizado (WA1, WA2, WA3, WA4)
* TLS - Autenticação do lado do servidor: [aqui](../../../Server#configurações-tls-wa1)
* Tolerância a falhas Bizantinas: [aqui](../../../Server#garantias-para-tolerância-a-servidores-bizantinos)
* Assinaturas das replicas: [aqui](../../../Server#receção-das-respostas-assinadas-das-réplicas-postman)
* Smart Contracts: [aqui](../../../Server#smart-contracts)

## Trabalho realizado (Projeto final)

### Autenticação: [aqui](../../../Server/Documentation/AUTHENTICATION.md)

Para a autenticação dos clientes foi criado um mecânismo de username:password, tendo sido necessário a implementação do conceito de Account no nosso sistema, tendo este acesso à criação de multiplas Wallets. 

O sistema de autenticação utiliza um mecânismo de tokens sendo este um JSON Web Token criado pelo servidor após o login do cliente. Este token tem um tempo de expiração de 10min, não havendo para já um mecanismo de refresh do token, tendo o cliente que, apo´s estes 10min, ter que refazer o login. A password é guardada na base de dados em formato de hash + salt de modo a mitigar a comparação de hashes iguais no sistema.

### Homomorphic encryption: 

A parte mais importante do projeto foi a utilização de cifras homomórficas que fornecem operações sobre dados cifrados. 

Começamos por estudar a nossa base de dados e entender os dados mais relevantes a serem cifrados e a forma como o iamos fazer para cada dado de acordo com as operações fornecidas. Visto que a arquitectura inicial do projeto não foi desenhada a pensar nesta fase final, tivemos que nos adaptar à medida que iamos iterando o projeto ao longo do semestre. 

Para o melhor entendimento da forma como estruturamos a nossa base de dados e cifras decidimos criar uma imagem para cada tabela com a informação de cada coluna e respetivas cifras.

#### Accounts table

Para a tabela de accounts pensamos em utilizar uma Onion Equality de modo a encontrarmos o username na tabela no ato de login. A password poderia ser ainda cifrada com mais uma camada RND para mitigar possiveis cracks à hash, mas achamos por bem não o fazer também para sobre carregar o sistema com tantas operações.

![Accounts table](../Images/ACCOUNTS.png)

---

#### Wallets table

Na tabela de wallets usamos a mesma estratégia do username do utilizador para a cifra do nome da wallet, sendo que para o atributo amount, dado que faziamos operações de soma e subtração neste campo, houve a necessidade de uma cifra com Onion Add para a continuidade das mesma operações sobre este campo.

![Accounts table](../Images/WALLETS.png)

---

#### Transfers table

Na tabela das transferências, a sua caractristica em relação às outras, é a utilização da Onion Search que possibilita a procura de uma transferência dada uma data sobre dados cifrados, isto permite na nossa aplicação a procura de todas as transferencias feitas dado um dia.

![Accounts table](../Images/TRANSFERS.png)

---

#### Deposits table

Esta tabela de depositos foi projetada a pensar em trabalhos futuros, dado que neste momento estamos a confiar nos clientes quando estes fazem um deposito, dado que precisamos da quantia cifrada com Onion Add e Onion Order, e aqui reside um problema... Então e se o cliente enviar valores encritados diferentes um do outro? Para tentar mitigar isso, tivemos a ideia de criar este registo para que este seja usado por outros clientes de modo a tentarem encontrar alguma incoeirência nos valores cifrados e talvez assim ganhar algum dinheiro extra ao avisarem o servidor sobre um potencial atacante. Este mecanismo não foi implementado dado o pouco tempo para a entrega do projeto, mas foi pensado e encaminhado para um possivel trabalho futuro.

![Accounts table](../Images/DEPOSITS.png)

---

#### Account - Wallets association table

Esta tabela é uma tabela auxiliar que associa o utilizador às suas wallets. Os dados nela contidos não são cifrados, dado que o servidor precisa de ter acesso a estes para poder fornecer o serviço ao cliente. Num trabalho futuro, todos os ID's na base de dados poderiam ser cifrados pelo próprio servidor, gerando este chaves simétricas random e mudando de chaves em horas "mortas" do sistema de modo a mitigar qualquer tipo de inferência a partir dos id's.


---
---

## Trabalhos futuros

### Melhoramentos da arquitectura do sistema.
Como já foi referido, a arquitectura atual do sistema não está otimizada para lidar com as atuais cifras homomorficas, pelo que seria interessante criar uma arquitectura melhorada, onde o cliente fazia pedidos normais a uma proxy que seria criada entre o cliente e o servidor, sendo esta proxy o principal responsável pela conversão da informação utilizando as cifras homomórficas. Esta proxy também ficaria com um cargo de autenticador do cliente sendo esta a responsável por fornecer as chaves ao cliente.

### Mudança de chaves.
Para uma maior confidencialidade dos dados, as chaves poderiam ser trocadas diáriamente numa altura de menor acesso aos dados.

### Incentivo de verificação de fraude.
Na atual arquitectura do sistema, há a necessidade de os clientes se verificarem entre si, mitigando o uso de fraudes ao sistema. Para tal, teria que haver um incentivo à verificação deste tipo de problema.

### Smart Contracts a executar operações do sistema.
Atualmente o sistema faz operações muito simples utilizando smart contracts, pelo que seria interessante no futuro, estes poderem utilizar código confiável do sistema.

### Smart Contracts com a noção de Gás.
Para evitar loops infinitos, a utilização de "gás" será necessária de modo a que o cliente pague as suas operações no sistema, mitigando assim o uso excessivo de recursos.
