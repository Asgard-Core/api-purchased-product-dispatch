FROM amazoncorretto:17
ARG JAR_FILE =. /target/api-purchased-product-dispatch-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} demo.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar", "/demo.jar"," -- server. address=0.0.0.0"]