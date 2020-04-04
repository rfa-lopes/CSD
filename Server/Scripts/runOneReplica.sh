if [[ $# -eq 0 ]] ; then
    echo "./runOneReplica <client id>"
    exit 1
fi

cd ..
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=1100$1"
