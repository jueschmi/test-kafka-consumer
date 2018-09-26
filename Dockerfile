FROM openjdk:8-jre
EXPOSE 8080 8081

COPY build/install/test-kafka-consumer /test-kafka-consumer/
COPY config.yml  /test-kafka-consumer/config.yml

# mount keys directory
RUN mkdir -p /application/keys
VOLUME /application/keys

CMD test-kafka-consumer/bin/test-kafka-consumer server /test-kafka-consumer/config.yml
