if [[ $# -eq 0 ]] ; then
    echo "./runOneReplica <client id>"
    exit 1
fi

cd ../src/main/resources
sed -i -e "s/wallet[0-3]*?use/wallet$1?use/" application.properties
sed -i -e "s/bftsmart.id=[0-3]*/bftsmart.id=$1/" application.properties
#sed -i -e "s/server.port=1100[0-3]*/server.port=1100$1/" application.properties
cd ../../..
#mvn spring-boot:run
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=1100$1"

