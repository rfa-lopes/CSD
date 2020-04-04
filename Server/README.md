[< Para trás](../README.md)
# Confiabilidade de Sistemas Distribuidos (Server)

**Tecnologias utilizadas**

* Spring Boot 2.2.5
* Maven
* Java 8
* MySQL (WA1 e WA2)

Initializer [aqui](https://start.spring.io/).

---

## Funcionalidades
### Tests
* Documentação [Tests](Documentation/TESTS.md).
### Wallets
* Documentação [Wallets](Documentation/WALLETS.md).
### Transfers
* Documentação [Transfers](Documentation/TRANSFERS.md).

---

## Arquitecturas

### WA1 - Servidor não replicado

![Servidor não replicado](Documentation/Images/WA1.png)

### WA2 - Replicação
BFT-SMaRt [aqui](https://github.com/bft-smart/library/wiki/Getting-Started-with-BFT-SMaRt).

![Replicação com BFT-SMaRt](Documentation/Images/WA2.png)

**NOTA IMPORTANTE**
Nos nossos testes ao sistema utilizamos uma base de dados comum a todas as replicas e verificaram-se várias escritas na base de dados, o que demonstra que o sistema funciona.

---

## Configurações TLS

### BFT-SMaRt
//TODO WA3

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

## Utils
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
