# Confiabilidade de Sistemas Distribuidos

Descrição.

[comment]: <> (========================================================================================)

## MySQL
[comment]: <> (........................................................)
### Instalação e configuração MySQL.
Referencia [aqui](https://www.digitalocean.com/community/tutorials/como-instalar-o-mysql-no-ubuntu-18-04-pt).
```bash
$ sudo apt update
$ sudo apt install mysql-server
$ mysql_secure_installation
$ systemctl status mysql.service
$ sudo mysql -u root -p
```
[comment]: <> (........................................................)
### Criação da base de dados
```SQL
mysql> create database wallet;
```
[comment]: <> (........................................................)
### Comandos MySQL
```SQL
show databases;
use wallet;
show tables;
SELECT * FROM wallet;
SELECT * FROM transfer;
INSERT INTO wallet VALUES (0, 'Nome', 9090)
```
Parar base de dados MySQL.
```bash
$sudo systemctl stop mysql
```
Iniciar base de dados MySQL.
```bash
$sudo systemctl start mysql
```
[comment]: <> (........................................................)

[comment]: <> (========================================================================================)


## Comandos Git
```bash
$ git clone https://github.com/rfa-lopes/CSD.git
```
```bash
$ git pull origin master
```

```bash
$ git add .
```

```bash
$ git commit -m "Initial commit"
```

```bash
$ git push
```
