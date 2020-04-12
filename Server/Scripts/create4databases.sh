if [[ $# -eq 0 ]] ; then
    echo "./create4databases.sh <nr db> <mysql_username[root]> <mysql_password[password]>"
    exit 1
fi

if [ $# -eq 1 ]
then
	username="root"
	password="password"
else
	username=$2
	password=$3
fi

nrdb=$1
dropall=$((nrdb + 20))

for ((i = 0 ; i < dropall ; i++ ));
do 	
	#echo "Drop database: wallet$i";
	mysql -u${username} -p${password} -e "DROP DATABASE wallet$i;" > /dev/null 2>&1;
done

for ((i = 0 ; i < nrdb ; i++ ));
do 	
	echo "Create database: wallet$i";	
	mysql -u${username} -p${password} -e "CREATE DATABASE wallet$i;" > /dev/null 2>&1;
done

mysql -u${username} -p${password} -e "show databases;";
mysql -u${username} -p${password}
