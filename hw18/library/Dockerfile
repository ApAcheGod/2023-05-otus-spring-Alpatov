FROM bellsoft/liberica-openjdk-alpine-musl:17
ARG JAR_FILE=target/hw17-1.0.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]