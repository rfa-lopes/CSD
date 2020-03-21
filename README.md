# Confiabilidade de Sistemas Distribuidos 2020

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
