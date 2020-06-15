FROM ubuntu:18.04

COPY ./target/guild-0.0.1-SNAPSHOT.jar /opt
RUN apt update -y \
    && apt install -y openjdk-11-jre

ENTRYPOINT java -jar /opt/guild-0.0.1-SNAPSHOT.jar -Djava.net.preferIPv4Stack=true
