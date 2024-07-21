# RHEL 8.5
FROM openjdk:17

# Application Settings
ADD week1-1.0.jar /apps/demo/week1-1.0.jar
ADD lib /apps/demo/lib
ADD ext /apps/demo/ext
WORKDIR /apps/demo

CMD ["java", "-jar", "week1-1.0.jar"]