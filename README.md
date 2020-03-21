# Confiabilidade de Sistemas Distribuidos

Descrição.

## MySQL
### Instalação e configuração MySQL.
Referencia [aqui](https://www.digitalocean.com/community/tutorials/como-instalar-o-mysql-no-ubuntu-18-04-pt).
```bash
$ sudo apt update
$ sudo apt install mysql-server
$ mysql_secure_installation
$ systemctl status mysql.service
$ sudo mysql -u root -p
```

### Comandos MySQL
```SQL
create database wallet;
show databases;
use wallet;
show tables;
SELECT * FROM wallet;
SELECT * FROM transfer;
INSERT INTO wallet VALUES (0, 'Nome', 9090)
```
Iniciar/Parar base de dados MySQL.
```bash
$sudo systemctl start mysql
$sudo systemctl stop mysql
```

## Comandos Git
```bash
git clone https://github.com/rfa-lopes/CSD.git
git pull origin master
git add .
git commit -m "Initial commit"
git push
```
