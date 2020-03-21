[< Para trás](../README.md)
# Confiabilidade de Sistemas Distribuidos (Server)

Descrição.

---

## Funcionalidades
### Tests
* Documentação [Tests](Lib/TESTS.md).
### Wallets
* Documentação [Wallets](Lib/WALLETS.md).
### Transfers
* Documentação [Transfers](Lib/TRANSFERS.md).

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
