# RHEL 8.5
FROM openjdk:17

# Application Settings
ADD week1/target/week1-1.0.jar /apps/demo/week1-1.0.jar
ADD week1/lib /apps/demo/lib
ADD week1/ext /apps/demo/ext

WORKDIR /apps/demo

CMD ["java", "-jar", "week1-1.0.jar"]