startContainer(){
	gnome-terminal -e " bash -c './runSingleContainer.sh "$1"; bash' "
}

for i in {1..4}
	do
		startContainer "${i}"
		sleep 30
	done


