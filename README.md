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
$ sudo mysql
```
### Criação da base de dados.
```SQL
mysql> create database wallet;
```
### Comandos MySQL
Mostrar bases de dados.
```bash
mysql> show databases;
```
Usar base de dados 'wallet'
```bash
mysql> use wallet;
```
Mostrar tabelas da base de dados escolhida.
```bash
mysql> show tables;
```

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
