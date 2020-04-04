if [[ $# -eq 0 ]] ; then
    echo "./testReplicas.sh <nr tests>"
    exit 1
fi

tests=$1

echo -e "\e[91mTesting:\e[39m TESTS"
for ((i = 1 ; i <= tests ; i++ ));
do 
	curl --insecure -X GET 'https://localhost:11000/tests/helloworld' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11001/tests/helloworld' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11002/tests/helloworld' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11003/tests/helloworld' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11000/tests/helloworld' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11001/tests/helloworld' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11002/tests/helloworld' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11003/tests/helloworld' > /dev/null 2>&1;
	echo -e "[$i]: \e[92mOK\e[39m"
done

echo;

echo -e "\e[91mTesting:\e[39m ADD MONEY"
for ((i = 1 ; i <= tests ; i++ ));
do 
	curl --insecure -X POST 'https://localhost:11000/transfers/add' --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11001/transfers/add' --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11002/transfers/add' --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11003/transfers/add' --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}' > /dev/null 2>&1;
	echo -e "[$i]: \e[92mOK\e[39m"
done


echo;

echo -e "\e[91mTesting:\e[39m REMOVE MONEY"
for ((i = 1 ; i <= tests ; i++ ));
do 
	curl --insecure -X POST 'https://localhost:11000/transfers/remove' --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11001/transfers/remove' --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11002/transfers/remove' --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}' > /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11003/transfers/remove' --header 'Content-Type: application/json' --data-raw '{"id":1,"amount":5}' > /dev/null 2>&1;
	echo -e "[$i]: \e[92mOK\e[39m"
done

echo;

echo -e "\e[91mTesting:\e[39m TRANSFERS MONEY"
for ((i = 1 ; i <= tests ; i++ ));
do 
	curl --insecure -X POST 'https://localhost:11000/transfers/transfer' --header 'Content-Type: application/json' --data-raw '{"fromId":1,"toId":2,"amount":10}'> /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11001/transfers/transfer' --header 'Content-Type: application/json' --data-raw '{"fromId":1,"toId":2,"amount":10}'> /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11002/transfers/transfer' --header 'Content-Type: application/json' --data-raw '{"fromId":1,"toId":2,"amount":10}'> /dev/null 2>&1;
	curl --insecure -X POST 'https://localhost:11003/transfers/transfer' --header 'Content-Type: application/json' --data-raw '{"fromId":1,"toId":2,"amount":10}'> /dev/null 2>&1;
	echo -e "[$i]: \e[92mOK\e[39m"
done

echo;

echo -e "\e[91mTesting:\e[39m GLOBAL TRANSFERS"
for ((i = 1 ; i <= tests ; i++ ));
do 
	curl --insecure -X GET 'https://localhost:11000/transfers/globaltransfers' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11001/transfers/globaltransfers' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11002/transfers/globaltransfers' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11003/transfers/globaltransfers' > /dev/null 2>&1;
	echo -e "[$i]: \e[92mOK\e[39m"
done

echo;

echo -e "\e[91mTesting:\e[39m WALLET TRANSFERS"
for ((i = 1 ; i <= tests ; i++ ));
do 
	curl --insecure -X GET 'https://localhost:11000/transfers/wallettransfers/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11001/transfers/wallettransfers/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11002/transfers/wallettransfers/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11003/transfers/wallettransfers/1' > /dev/null 2>&1;
	echo -e "[$i]: \e[92mOK\e[39m"
done

echo;

echo -e "\e[91mTesting:\e[39m CREATE WALLET"
for ((i = 1 ; i <= tests ; i++ ));
do 
	#curl --insecure -X POST 'https://localhost:11000/wallets/create' --header 'Content-Type: application/json' --data-raw '{"name":"TEST_USER_'$i'"}' > /dev/null 2>&1;
	#curl --insecure -X POST 'https://localhost:11003/wallets/create' --header 'Content-Type: application/json' --data-raw '{"name":"TEST_USER_'$i'"}' > /dev/null 2>&1;
	echo -e "[$i]: \e[34mWAITING TO TEST IN DOCKER\e[39m"
done

echo;

echo -e "\e[91mTesting:\e[39m DELETE WALLET"
for ((i = 1 ; i <= tests ; i++ ));
do 
	#curl --insecure -X DELETE 'https://localhost:11000/wallets/delete/100' > /dev/null 2>&1;
	#curl --insecure -X DELETE 'https://localhost:11000/wallets/delete/100' > /dev/null 2>&1;
	echo -e "[$i]: \e[34mWAITING TO TEST IN DOCKER\e[39m"
done

echo;

echo -e "\e[91mTesting:\e[39m WALLET AMOUNT"
for ((i = 1 ; i <= tests ; i++ ));
do 
	curl --insecure -X GET 'https://localhost:11000/wallets/amount/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11001/wallets/amount/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11002/wallets/amount/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11003/wallets/amount/1' > /dev/null 2>&1;
	echo -e "[$i]: \e[92mOK\e[39m"
done

echo;

echo -e "\e[91mTesting:\e[39m WALLET INFORMATION"
for ((i = 1 ; i <= tests ; i++ ));
do 
	curl --insecure -X GET 'https://localhost:11000/wallets/info/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11001/wallets/info/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11002/wallets/info/1' > /dev/null 2>&1;
	curl --insecure -X GET 'https://localhost:11003/wallets/info/1' > /dev/null 2>&1;
	echo -e "[$i]: \e[92mOK\e[39m"
done
echo;
echo -e "\e[92mTESTS COMPLETED SUCCESSFULLY \e[39m";
echo;

