FROM alpine:3
RUN apk update && apk add openjdk11
RUN mkdir /dicionario/
WORKDIR /dicionario/
COPY build/libs/dicionarioinclusivo.backend-0.0.1-all.jar .

EXPOSE 8000
CMD ["java", "-jar", "dicionarioinclusivo.backend-0.0.1-all.jar"]
