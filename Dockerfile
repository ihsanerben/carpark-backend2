# 1. Aşama: Uygulamanızı derlemek için bir Java Derleme Ortamı kullanın.
# En kararlı ve yaygın Maven imajını kullanıyoruz.
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Kodunuzu kopyalayın
COPY pom.xml .
COPY src ./src
# Projeyi derle
RUN mvn clean package -DskipTests

# 2. Aşama: Daha küçük ve güvenli bir çalıştırma ortamı kullanın.
# Sadece JRE içeren minimalist Temurin imajı.
FROM eclipse-temurin:17-jre-focal
# Build aşamasında oluşturulan JAR dosyasını kopyala
# Buradaki JAR adı, Maven ayarlarınıza göre değişebilir. Lütfen pom.xml'i kontrol edin.
# Eğer pom.xml'de <artifactId>parkingapi</artifactId> ise, dosya adı:
COPY --from=build /app/target/*.jar app.jar
# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "/app.jar"]
