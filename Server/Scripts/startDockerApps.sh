runServer(){
   gnome-terminal -- bash -c "docker exec -it myapp$1 bash -c "./initApp$1.sh" ; bash"
}


for i in {1..4}
	do
	  sleep 30
		runServer "${i}"
	done