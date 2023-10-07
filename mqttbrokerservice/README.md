# HTTP-API for interacting with MQTT brokers

# Run using Docker

docker build .
docker run -p 8080:8080 <image_id>

# Run from commandline

mvn clean install
mvn spring-boot:run