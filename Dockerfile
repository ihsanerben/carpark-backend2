# 1. Aşama: Uygulamanızı derlemek için bir Java Derleme Ortamı kullanın
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Projeyi derle
RUN mvn clean package -DskipTests

# 2. Aşama: Daha küçük ve güvenli bir çalıştırma ortamı kullanın
FROM openjdk:17-jre-slim
COPY --from=build /app/target/*.jar app.jar
# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "/app.jar"]
