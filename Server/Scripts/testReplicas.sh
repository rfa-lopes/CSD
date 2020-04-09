if [[ $# -eq 0 ]] ; then
    echo "./testReplicas.sh <nr tests> <stop? y/n>"
    exit 1
fi

#======================================================================================================================

if [ $# -eq 1 ]
then
	stop="n"
else
	stop=$2
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

tests=$1
start=`date +%s`
TIMEFORMAT=%R
replicas=4
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

echo;echo;

#======================================================================================================================

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Press any key to continue tests.' -n1 key
	echo;
fi

echo -e "\e[100m[INIT]\e[49m CREATING USER ROOT1"
curl --silent --output /dev/null --insecure -X POST ${server}0${create} --header 'Content-Type: application/json' --data-raw '{"name":"ROOT1"}';

echo -e "\e[100m[INIT]\e[49m CREATING USER ROOT2"
curl --silent --output /dev/null --insecure -X POST ${server}0${create} --header 'Content-Type: application/json' --data-raw '{"name":"ROOT2"}';

echo -e "\e[100m[INIT]\e[49m ADD MONEY TO ROOT1"
curl --silent --output /dev/null --insecure -X POST ${server}0${add}  --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":900000}';

echo -e "\e[100m[INIT]\e[49m ADD MONEY TO ROOT2"
curl --silent --output /dev/null --insecure -X POST ${server}0${add}  --header 'Content-Type: application/json' --data-raw '{"id":2,"amount":900000}';

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================
if [ "$stop" = "y" ]; then
	read -rsp $'Check database... after press any key to continue' -n1 key
	echo;
fi

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

echo;echo;

#======================================================================================================================

end=`date +%s`

runtime=$((end-start))

echo -e "\e[92mTESTS COMPLETED SUCCESSFULLY IN: $runtime s\e[39m";
echo;





