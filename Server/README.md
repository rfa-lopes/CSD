# Confiabilidade de Sistemas Distribuidos (SERVER)

Descrição.

## Setup
### Git Clone
```bash
git clone https://github.com/rfa-lopes/CSD.git
```

### Instalação e configuração MySQL.
```bash
sudo apt update
sudo apt install mysql-server
mysql_secure_installation
systemctl status mysql.service
sudo mysql -u root -p
```
### Configuração Spring
```bash
cd src/main/resources/
cat application.properties
#spring.jpa.hibernate.ddl-auto=update
#spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/wallet
#spring.datasource.username=root  <----------- verificar
#spring.datasource.password=password   <------ verificar
```

## Funcionalidades
### Tests
* Documentação [Tests](Lib/TESTS.md).
### Wallets
* Documentação [Wallets](Lib/WALLETS.md).
### Transfers
* Documentação [Transfers](Lib/TRANSFERS.md).

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

### Comandos Git
```bash
git clone https://github.com/rfa-lopes/CSD.git
git pull origin master
git add .
git commit -m "Initial commit"
git push
```
## Informação adicional
### Autores
* Rodrigo Lopes - rfa.lopes@campus.fct.unl.pt
* João Santos - jmfd.santos@campus.fct.unl.pt
* João Ramalho - jl.ramalho@campus.fct.unl.pt
