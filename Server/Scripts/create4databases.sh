if [[ $# -eq 0 ]] ; then
    echo "./create4databases.sh <mysql_username> <mysql_password>"
    exit 1
fi

for i in {0..3}
do 	
	echo "Drop database: wallet$i";
	mysql -u${1} -p${2} -e "DROP DATABASE wallet$i;" > /dev/null 2>&1;
done

for i in {0..3}
do 	
	echo "Create database: wallet$i";	
	mysql -u${1} -p${2} -e "CREATE DATABASE wallet$i;" > /dev/null 2>&1;
done

mysql -u${1} -p${2} -e "show databases;";
mysql -u${1} -p${2}
