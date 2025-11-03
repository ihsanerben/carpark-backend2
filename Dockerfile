# 1. Aşama: Uygulamanızı derlemek için bir Java Derleme Ortamı kullanın
# Eski imaj: maven:3.8.6-openjdk-17
# YENİ İMAJ: Maven'ın en son OpenJDK 17 ile gelen sürümünü kullanıyoruz.
FROM maven:3.9.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Projeyi derle
RUN mvn clean package -DskipTests

# 2. Aşama: Daha küçük ve güvenli bir çalıştırma ortamı kullanın
# Eski imaj: openjdk:17-jre-slim (Bu da bulunamamış olabilir)
# YENİ İMAJ: Sadece JRE içeren minimalist imaj
FROM eclipse-temurin:17-jre-focal
# Build aşamasında oluşturulan JAR dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar
# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "/app.jar"]
