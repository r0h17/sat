#
# Docker that hosts a microservice based on Dropwizard
# that checks which servers are up & running
#

FROM java:8

ADD target/sat-*-SNAPSHOT.jar /app/sat.jar
ADD configuration.yml /app/configuration.yml

WORKDIR /app

EXPOSE 8080

#RUN java -jar sat.jar db migrate configuration.yml
#CMD ["java", "-jar", "sat.jar", "db", "migrate", "configuration.yml"]
#CMD ["java", "-jar", "sat.jar", "server", "configuration.yml"]

CMD java -jar sat.jar db migrate configuration.yml && java -jar sat.jar server configuration.yml
