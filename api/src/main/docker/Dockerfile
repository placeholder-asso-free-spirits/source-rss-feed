FROM adoptopenjdk/openjdk11-openj9

WORKDIR /opt/app

COPY ["@project.artifactId@-@project.version@.jar", "app.jar"]

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]