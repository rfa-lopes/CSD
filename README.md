# Confiabilidade de Sistemas Distribuidos 2020

## Go to: [Server](Server)

## Go to: [Client](Client)

---

### Arquitectura: [WA1](Server#wa1---servidor-não-replicado)
### Arquitectura: [WA2](Server#wa2---replicação)
### Auditoria   : [WA3](Server/Documentation/Auditory/README.md)

---

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
cd Server/src/main/resources/
cat application.properties
#spring.jpa.hibernate.ddl-auto=update
#spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/wallet
#spring.datasource.username=root  <----------- verificar
#spring.datasource.password=password   <------ verificar
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
git rm -r --cached Path/to/directories
```

### Autores
* Rodrigo Lopes - rfa.lopes@campus.fct.unl.pt
* João Santos - jmfd.santos@campus.fct.unl.pt
* João Ramalho - jl.ramalho@campus.fct.unl.pt
