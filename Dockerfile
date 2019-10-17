FROM openjdk
COPY "./build/libs/stockoverflow-1.0-SNAPSHOT.jar" stockoverflow-1.0-SNAPSHOT.jar
ADD mongo-init.js /docker-entrypoint-initdb.d/
#WORKDIR app/stockoverflow-1.0-SNAPSHOT.jar
EXPOSE 8080
ENV MONGODB_DB_NAME stackoverflow
ENV MONGODB_DB_HOST mongo
ENV MONGODB_DB_PORT 27017
#ENTRYPOINT ["java","-jar","stockoverflow-1.0-SNAPSHOT.jar"]
CMD ["java", "-jar", "stockoverflow-1.0-SNAPSHOT.jar"]