docker-compose down
docker volume prune -f
docker network rm appnet
docker network create appnet

docker container prune -f

#Para testar (depois de ja ter feito o download da imagem) comentar a seguinte linha
docker image rm -f appimage
#Para testar (depois de ja ter feito o download da imagem) comentar a seguinte linha
docker build ../. -t=appimage

docker-compose up -d
sleep 60

echo "Starting containers............"
docker exec -d myapp1 bash -c "./initApp1.sh"
docker exec -d myapp2 bash -c "./initApp2.sh"
docker exec -d myapp3 bash -c "./initApp3.sh"
docker exec -d myapp4 bash -c "./initApp4.sh"

sleep 300

echo "The replicas are running!"




