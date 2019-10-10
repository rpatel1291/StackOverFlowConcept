FROM openjdk
COPY "./build/libs/stockoverflow-1.0-SNAPSHOT.jar" app/stackoverflow/
WORKDIR app/stackoverflow/
EXPOSE 8080
ENTRYPOINT ["java","-jar","stockoverflow-1.0-SNAPSHOT.jar"]