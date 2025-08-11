# Makefile for the Shop Inventory System

ifeq ($(OS), Windows_NT)
    MVNW = .\mvnw
    JAR_PATH = target\shop-inventory-0.0.1-SNAPSHOT.jar
else
    MVNW = ./mvnw
	JAR_PATH = target/shop-inventory-0.0.1-SNAPSHOT.jar
endif

# Build the Spring Boot application
build:
	./mvnw clean package

# Run the docker-compose services (MongoDB and Redis)
up:
	docker-compose up -d

# Stop and remove the docker-compose services
down:
	docker-compose down

# Run the Spring Boot application
run:
	java -jar $(JAR_PATH)

# A command to build and run everything
start: build up run

# A command to stop everything
stop: down

# Clean the project
clean:
	$(MVNW) clean
