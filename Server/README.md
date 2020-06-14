[< Para trás](../README.md)
# Confiabilidade de Sistemas Distribuidos (Server)

**Tecnologias utilizadas**

* **Spring Boot 2.2.5** (WA1, WA2, WA3 e WA4)
* **Maven** (WA1, WA2, WA3 e WA4)
* **Java 8** (WA1, WA2, WA3 e WA4)
* **MySQL** (WA1, WA2, WA3 e WA4)
* **BFT-SMaRt** (WA2, WA3 e WA4)
* **Docker** (WA3 e WA4)
* **Docker Compose** (WA3 e WA4)
* **homomorphic encryption** (PROJ)

Spring initializer [aqui](https://start.spring.io/).

---

## Quick test
### 0 - Dar DROP às bases de dados (caso existem) e criar novas. (WA2)
```bash
./Scripts/create4databases.sh
$ usage: ./create4databases.sh <mysql_username> <mysql_password>
./Scripts/create4databases.sh root password
```

### 1 - Correr os servidores (pode demorar cerca de 60 segundos a arrancar) (WA2)

```bash
./Scripts/runFourReplicas.sh
```

**OU** em terminais diferentes

```bash
./Scripts/runOneReplica.sh 0
./Scripts/runOneReplica.sh 1
./Scripts/runOneReplica.sh 2
./Scripts/runOneReplica.sh 3
```
### 2 - Correr os testes automáticos (arg = número de requests por cada funcionalidade) (WA3)

```bash
./Scripts/testReplicas.sh 3
```

---
### Docker

Para correr as 4 réplicas alterar ficheiro host.config:
```
0 myapp1  11020 11021
1 myapp2  11022 11023
2 myapp3  11024 11025
3 myapp4  11026 11027
```

E correr script runDockerBuild.sh:

```bash
./Scripts/runDockerBuild.sh
```

## Funcionalidades (WA1 + WA4)
### Tests
* Documentação [Tests](Documentation/TESTS.md).
### Wallets
* Documentação [Wallets](Documentation/WALLETS.md).
### Transfers
* Documentação [Transfers](Documentation/TRANSFERS.md).
### Accounts
* Documentação [Accounts](Documentation/ACCOUNTS.md).
### Authentication
* Documentação [Authentication](Documentation/AUTHENTICATION.md).

---

## Arquitecturas

### Servidor não replicado (WA1)

![Servidor não replicado](Documentation/Images/WA1.png)

### Replicação (WA2)
BFT-SMaRt [aqui](https://github.com/bft-smart/library/wiki/Getting-Started-with-BFT-SMaRt).

![Replicação com BFT-SMaRt](Documentation/Images/WA2.png)

**NOTA IMPORTANTE:**
 Nos nossos testes ao sistema utilizamos uma base de dados comum a todas as replicas e verificaram-se várias escritas na base de dados na operação de criar uma nova wallet, o que demonstra que o sistema funciona.
 Porém em termos funcionamento, as operações create wallet e delete wallet não se encontram a funcionar corretamente enquanto não for implementada uma solução (Docker) onde cada replica tem a sua base de dados.
 
### Iteração (WA4)
 
## Smart Contracts.

A nossa implementação para o proof of concept dos smart contracts é muito básica, sendo apenas enviado para o servidor o código a executar em cada replica, sendo depois esse código executado sem qualquer verificação (Nesta fase inicial).

O Código que irá ser executado encontra-se presente num ficheiro denomido SmartContract dentro da pasta [SmartContract](../Client/SmartContract).

Consiste num ficheiro com uma classe Java que é transformado em Base 64 e enviado por um REST endpoint. Ao receber, cada réplica transforma o source code Base64 para texto e compila em RunTime através da classe JavaCompiler, e de seguida, a classe é inicializada (Executando assim o código presente no Construtor da Classe).


De Momento não está a ser enforced nenhum mecanismo de controlo, porém temos acesso ao Cliente que enviou o SmartContract através do wallet name, por exemplo. A JVM poderá ser configurada de modo a controlar as permissões das operações provenientes do SmartContract.

No futuro poderemos adaptar este SmartContract para fazer operações arbitrárias no sistema, tais como transferências num determinado horário automáticas.
 
## Garantias para tolerância a servidores bizantinos.

![Tolerância a servidores bizantinos](Documentation/Images/WA4.png)

## Receção das respostas assinadas das réplicas (Postman).

![Postman](Documentation/Images/WA4-Postman.png)

---

## Configurações TLS (WA1)

### Setup
* Criar par de chaves e metê-las na keystore
```bash
keytool -genkey -keyalg RSA -alias walletCert -keystore walletCert.jks -storepass wallet -validity 365 -keysize 4096 -storetype pkcs12
```

### Propriedades
* Algorithm: RSA
* init validity: 365 days
* Key size: 4096
* Store type: PKCS12

### Nomes e Segredos
* Alias key: walletCert
* Key store name: walletCert.jks
* Store password: wallet

---

## Utils (WA1, WA2 e WA3)
### Comandos MySQL
```SQL
create database wallet;
show databases;
use wallet;
show tables;

SELECT * FROM wallet;
SELECT * FROM transfer;
INSERT INTO wallet VALUES (0, 'John', 9041)
```
### Iniciar/Parar base de dados MySQL.
```bash
sudo systemctl start mysql
sudo systemctl stop mysql
```
---
## Testes (WA1, WA2 e WA3)

### Manuais com Postman
![Teste com replicação - Adicionar dinheiro a uma wallet](Documentation/Images/WA2_Working.png)

### Automáticos
![Testes Automáticos com replicação](Documentation/Images/WA3_AutomaticTests.png)

**NOTAS:** Mais testes feitos [aqui](Documentation/Images/).

---

## Informação adicional

### Comandos Git
```bash
git clone https://github.com/rfa-lopes/CSD.git
git pull origin master
git add .
git commit -m "Initial commit"
git push
```

### Autores
* Rodrigo Lopes - rfa.lopes@campus.fct.unl.pt
* João Santos - jmfd.santos@campus.fct.unl.pt
* João Ramalho - jl.ramalho@campus.fct.unl.pt

---

[< Para trás](../README.md)
