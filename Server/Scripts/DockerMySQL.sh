docker run -itd --network=appnet -p 8443:8443  --name=myapp1 --env MYSQL_ROOT_HOST=127.0.0.1 -d appimage
docker logs myapp1 2>&1 | grep GENERATED
docker exec -it myapp1 mysql -uroot -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
ALTER USER 'root'@'127.0.0.1' IDENTIFIED BY 'password';
create database wallet;
(Dentro do container )
chmod +x /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=11000"

docker run  -itd --network=appnet  --name=myapp2 --env MYSQL_ROOT_HOST=127.0.0.1 -d appimage
docker logs myapp2 2>&1 | grep GENERATED
docker exec -it myapp2 mysql -uroot -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
ALTER USER 'root'@'127.0.0.1' IDENTIFIED BY 'password';
create database wallet;
(Dentro do container )
chmod +x /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=11001"

docker run -itd --network=appnet --name=myapp3 --env MYSQL_ROOT_HOST=127.0.0.1 -d appimage
docker logs myapp3 2>&1 | grep GENERATED
docker exec -it myapp3 mysql -uroot -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
ALTER USER 'root'@'127.0.0.1' IDENTIFIED BY 'password';
create database wallet;
(Dentro do container )
chmod +x /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=11002"

docker run -itd --network=appnet  --name=myapp4 --env MYSQL_ROOT_HOST=127.0.0.1 -d appimage
docker logs myapp4 2>&1 | grep GENERATED
docker exec -it myapp4 mysql -uroot -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
ALTER USER 'root'@'127.0.0.1' IDENTIFIED BY 'password';
create database wallet;
(Dentro do container )
chmod +x /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=11003"


