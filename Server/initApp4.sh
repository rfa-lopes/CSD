mysql -uroot -ppassword -Bse "CREATE DATABASE \`wallet\`;ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';ALTER USER 'root'@'127.0.0.1' IDENTIFIED BY 'password';"
chmod +x /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=11003"