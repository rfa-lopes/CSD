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

## Trabalhos futuros
* TODO:
