FROM openjdk:11
EXPOSE 8000
RUN mkdir /app
COPY ./build/libs/*.jar /app/dicionarioinclusivo-backend.jar
ENTRYPOINT ["java","-jar","/app/dicionarioinclusivo-backend.jar"]
