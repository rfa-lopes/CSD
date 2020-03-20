# Confiabilidade de Sistemas Distribuidos

## MySQL
Instalação e configuração MySQL.

Referencia [aqui](https://www.digitalocean.com/community/tutorials/como-instalar-o-mysql-no-ubuntu-18-04-pt).

```bash
$ sudo apt update
$ sudo apt install mysql-server
$ mysql_secure_installation
$ systemctl status mysql.service
```

Criação da base de dados.
```
$ sudo mysql
```
```SQL
mysql> create database wallet;
```

## Git Commands
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
