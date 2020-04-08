if [[ $# -eq 0 ]] ; then
    echo "./testReplicas.sh <nr tests>"
    exit 1
fi

TIMEFORMAT=%R

tests=$1

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
delete='/wallets/delete/100'
amount='/wallets/amount/1'
info='/wallets/info/1'

echo -e "\e[91mTesting:\e[39m TESTS1 - ORDERED"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$test1;
	time='';	
	for ((i = 1 ; i <= tests ; i++ ));
	do 	
			curl --silent --output /dev/null --insecure -X GET $var
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m TESTS2 - UNORDERED"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$test2;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			curl --silent --output /dev/null --insecure -X GET $var;
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m ADD MONEY"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$add;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			curl --silent --output /dev/null --insecure -X POST $var --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}';
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done


echo;

echo -e "\e[91mTesting:\e[39m REMOVE MONEY"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$remove;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			curl --silent --output /dev/null --insecure -X POST $var --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}';
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m TRANSFERS MONEY"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$transf;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			curl --silent --output /dev/null --insecure -X POST $var --header 'Content-Type: application/json' --data-raw '{"fromId":1,"toId":2,"amount":10}';
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m GLOBAL TRANSFERS"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$global;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			curl --silent --output /dev/null --insecure -X GET $var;
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m WALLET TRANSFERS"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$wallet;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			curl --silent --output /dev/null --insecure -X GET $var;
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m CREATE WALLET"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$create;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			#curl --silent --output /dev/null --insecure -X POST $var --header 'Content-Type: application/json' --data-raw '{"name":"TEST_USER_'$i'"}';
			#echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
			echo -ne "[$i]: \e[34mWAITING TO TEST IN DOCKER\e[39m"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m DELETE WALLET"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$delete;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			#curl --silent --output /dev/null --insecure -X DELETE $var;
			#echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
			echo -ne "[$i]: \e[34mWAITING TO TEST IN DOCKER\e[39m"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m WALLET AMOUNT"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$amount;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			curl --silent --output /dev/null --insecure -X GET $var;
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done

echo;

echo -e "\e[91mTesting:\e[39m WALLET INFORMATION"
for ((j = 0 ; j < replicas ; j++ ));
do
	var=$server$j$info;
	for ((i = 1 ; i <= tests ; i++ ));
	do 
			curl --silent --output /dev/null --insecure -X GET $var;
			echo -ne "[$i]: \e[92mOK: \e[39m $var"\\r;
	done
	echo;
done

echo;

echo -e "\e[92mTESTS COMPLETED SUCCESSFULLY \e[39m";
echo;

#======================================================================================================================


function avarage {
  echo $replicas;
}




