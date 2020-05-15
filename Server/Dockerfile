<<<<<<< Updated upstream
=======
FROM mysql/mysql-server
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app


#COPY pom.xml /usr/src/app
#COPY bin /usr/src/app/bin
#COPY src /usr/src/app/src
#COPY .idea /usr/src/app/.idea
#COPY config /usr/src/app/config
#COPY lib /usr/src/app/lib
#COPY Scripts /usr/src/app/Scripts
#COPY TLS /usr/src/app/TLS
#COPY initApp1.sh /usr/src/app
#COPY initApp2.sh /usr/src/app
#COPY initApp3.sh /usr/src/app
#COPY initApp4.sh /usr/src/app

WORKDIR /

RUN yum install java-1.8.0-openjdk-devel.x86_64 -y && \
    yum install wget -y && \
    wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz  -P /tmp && \
    tar xf /tmp/apache-maven-3.6.3-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.6.3 /opt/maven

COPY JVMDockerConfig /etc/profile.d/maven.sh

WORKDIR /usr/src/app
>>>>>>> Stashed changes
