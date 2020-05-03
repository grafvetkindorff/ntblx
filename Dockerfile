FROM openjdk:8-alpine

COPY target/uberjar/ntblx.jar /ntblx/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/ntblx/app.jar"]
