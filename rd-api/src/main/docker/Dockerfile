FROM amazoncorretto:25-alpine

EXPOSE 8080

ADD maven/@project.build.finalName@.jar app.jar
ADD entrypoint.sh /entrypoint.sh

RUN chmod 750 /entrypoint.sh

ENTRYPOINT ["sh", "entrypoint.sh"]
