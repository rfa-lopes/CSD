if [ $# -eq 0 ] || [ $# -eq 2 ]; then
    echo "./testReplicas.sh <nr tests> <stop?y/[n]> <all?[all]/1/2/3/.../11> <nr replicas[4]>"
    echo "[1] :  TESTS1"
    echo "[2] :  TESTS2"
    echo "[3] :  CREATE WALLET"
    echo "[4] :  DELETE WALLET"
    echo "[5] :  ADD MONEY"
    echo "[6] :  REMOVE MONEY"
    echo "[7] :  TRANSFERS MONEY"
    echo "[8] :  GLOBAL TRANSFERS"
    echo "[9] :  WALLET TRANSFERS"
    echo "[10]:  WALLET AMOUNT"
    echo "[11]:  WALLET INFO"
    exit 1
fi

#======================================================================================================================
tests=$1

if [ $# -eq 1 ]
then
	stop="n"
	all="all"
	replicas=4
else
	stop=$2
	all=$3
	replicas=$4
fi

#======================================================================================================================

if [ "$stop" = "n" ]
then
	echo "Starting non stop test."
else
	echo "Starting stop test."
fi

#======================================================================================================================

function avarage {
	local array=("$@")
	total=0
	sum=0
	for i in "${array[@]}"
	do
		sum=$(echo "$sum + $i" | bc)
		((total++))
	done
	if [ "$total" -eq "0" ]
	then
		printf "0";
	else
		avg=`echo "$sum / $total" | bc -l`
		printf '%0.5f' "$avg"
	fi
}

#======================================================================================================================
start=`date +%s`
TIMEFORMAT=%R
server='https://localhost:1100'

test1='/tests/test1'
test2='/tests/test2'

add='/transfers/add'
remove='/transfers/remove'
transf='/transfers/transfer'
global='/transfers/globaltransfers'
wallet='/transfers/wallettransfers/1'

create='/wallets/create'
delete='/wallets/delete/'
amount='/wallets/amount/1'
info='/wallets/info/1'

#======================================================================================================================

if [ "$all" = "1" ] || [ "$all" = "all" ]
then
	echo -e "\e[91mTesting:\e[39m TESTS1 - ORDERED"
	replicasAVG=();
	avg='';
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$test1;
		time='';
		times=();	
		for ((i = 1 ; i <= tests ; i++ ));
		do 	
				time=$( { time curl --silent --output /dev/null --insecure -X GET $var; } 2>&1 )
				times+=($time);
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 1]: \e[39m TESTS1";echo;
fi

#======================================================================================================================

if [ "$all" = "2" ] || [ "$all" = "all" ]
then
	echo -e "\e[91mTesting:\e[39m TESTS2 - UNORDERED"
	replicasAVG=();
	avg='';
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$test2;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X GET $var; } 2>&1 )
				times+=($time);
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 2]: \e[39m TESTS2";echo;
fi

#======================================================================================================================
if [ "$all" = "all" ]
then

	echo -e "\e[100m[INIT]\e[49m CREATING USER ROOT1"
	curl --silent --output /dev/null --insecure -X POST ${server}0${create} --header 'Content-Type: application/json' --data-raw '{"name":"ROOT1"}';

	echo -e "\e[100m[INIT]\e[49m CREATING USER ROOT2"
	curl --silent --output /dev/null --insecure -X POST ${server}0${create} --header 'Content-Type: application/json' --data-raw '{"name":"ROOT2"}';

	echo -e "\e[100m[INIT]\e[49m ADD MONEY TO ROOT1"
	curl --silent --output /dev/null --insecure -X POST ${server}0${add}  --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":900000}';

	echo -e "\e[100m[INIT]\e[49m ADD MONEY TO ROOT2"
	curl --silent --output /dev/null --insecure -X POST ${server}0${add}  --header 'Content-Type: application/json' --data-raw '{"id":2,"amount":900000}';
	
	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
fi
#======================================================================================================================
if [ "$all" = "3" ] || [ "$all" = "all" ]
then

	echo -e "\e[91mTesting:\e[39m CREATE WALLET - ORDERED"
	replicasAVG=();
	avg='';
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$create;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X POST $var --header 'Content-Type: application/json' --data-raw '{"name":"TEST_USER_'$j$i'"}'; } 2>&1 )
				times+=($time);	
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
				#echo -ne "[$i]: \e[34mWAITING TO TEST IN DOCKER\e[39m"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 3]: \e[39m CREATE WALLET";echo;
fi
#======================================================================================================================
if [ "$all" = "4" ] || [ "$all" = "all" ]
then
	echo -e "\e[91mTesting:\e[39m DELETE WALLET - ORDERED"
	replicasAVG=();
	avg='';
	id=3
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$delete;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X DELETE $var$id; } 2>&1 )
				times+=($time);	
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
				id=$((id+1))
				#echo -ne "[$i]: \e[34mWAITING TO TEST IN DOCKER\e[39m"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 4]: \e[39m DELETE WALLET";echo;
fi

#======================================================================================================================
if [ "$all" = "5" ] || [ "$all" = "all" ]
then

	echo -e "\e[91mTesting:\e[39m ADD MONEY - ORDERED"
	replicasAVG=();
	avg='';
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$add;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X POST $var --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":1}'; } 2>&1 )
				times+=($time);			
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 5]: \e[39m ADD MONEY";echo;
fi

#======================================================================================================================
if [ "$all" = "6" ] || [ "$all" = "all" ]
then

	echo -e "\e[91mTesting:\e[39m REMOVE MONEY - ORDERED"
	replicasAVG=();
	avg='';
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$remove;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X POST $var --header 'Content-Type: application/json' --data-raw '{"id":2,"amount":1}'; } 2>&1 )
				times+=($time);	
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 6]: \e[39m REMOVE MONEY";echo;
fi

#======================================================================================================================
if [ "$all" = "7" ] || [ "$all" = "all" ]
then

	echo -e "\e[91mTesting:\e[39m TRANSFERS MONEY - ORDERED"
	replicasAVG=();
	avg='';
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$transf;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X POST $var --header 'Content-Type: application/json' --data-raw '{"fromId":1,"toId":2,"amount":1}'; } 2>&1 )
				times+=($time);	
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 7]: \e[39m TRANSFERS MONEY";echo;
fi

#======================================================================================================================
if [ "$all" = "8" ] || [ "$all" = "all" ]
then

	echo -e "\e[91mTesting:\e[39m GLOBAL TRANSFERS - UNORDERED"
	replicasAVG=();
	avg='';
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$global;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X GET $var; } 2>&1 )
				times+=($time);	
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 8]: \e[39m GLOBAL TRANSFERS";echo;
fi

#======================================================================================================================
if [ "$all" = "9" ] || [ "$all" = "all" ]
then

	echo -e "\e[91mTesting:\e[39m WALLET TRANSFERS - UNORDERED"
	replicasAVG=();
	avg='';
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$wallet;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X GET $var; } 2>&1 )
				times+=($time);	
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 9]: \e[39m WALLET TRANSFERS";echo;
fi

#======================================================================================================================
if [ "$all" = "10" ] || [ "$all" = "all" ]
then

	echo -e "\e[91mTesting:\e[39m WALLET AMOUNT - UNORDERED"
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$amount;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X GET $var; } 2>&1 )
				times+=($time);	
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi

	echo;echo;
else
	echo -ne "\e[93m[SKIPING 10]: \e[39m WALLET AMOUNT";echo;
fi

#======================================================================================================================
if [ "$all" = "11" ] || [ "$all" = "all" ]
then

	echo -e "\e[91mTesting:\e[39m WALLET INFORMATION - UNORDERED"
	for ((j = 0 ; j < replicas ; j++ ));
	do
		var=$server$j$info;
		for ((i = 1 ; i <= tests ; i++ ));
		do 
				time=$( { time curl --silent --output /dev/null --insecure -X GET $var; } 2>&1 )
				times+=($time);	
				echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
		done
		time=$(avarage "${times[@]}")
		replicasAVG+=($time);
		echo -ne "[$tests]: \e[92mOK: \e[39m $var :: Avarage time=$time s"\\r;
		echo;
	done
	avg=$(avarage "${replicasAVG[@]}")
	echo -ne "\e[93m[Replicas AVG]: \e[39m $avg s"\\r;

	if [ "$stop" = "y" ]; then
		read -rsp $'Check database... after press any key to continue' -n1 key
		echo;
	fi
else
	echo -ne "\e[93m[SKIPING 11]: \e[39m WALLET INFO";echo;
fi

echo;echo;

#======================================================================================================================

end=`date +%s`

runtime=$((end-start))

echo -e "\e[92mTESTS COMPLETED SUCCESSFULLY IN: $runtime s\e[39m";
echo;





