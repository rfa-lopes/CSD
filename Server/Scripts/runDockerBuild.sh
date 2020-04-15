docker-compose down

docker network rm  appnet
docker network create appnet

docker container prune -f
#docker image rm -f appimage

#docker build ../. -t=appimage
docker-compose up -d
sleep 20
./startDockerApps.sh




