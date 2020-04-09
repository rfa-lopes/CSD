runServer(){

	bash runOneReplica.sh "$1"
}


for i in {0..3}
	do 	
		runServer "${i}" &
		sleep 15
	done
