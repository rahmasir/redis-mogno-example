# Makefile for the Shop Inventory System

ifeq ($(OS), Windows_NT)
    MVNW = .\mvnw
else
    MVNW = ./mvnw
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

# Run the Spring Boot application [just for linux]
run:
	java -jar target/shop-inventory-0.0.1-SNAPSHOT.jar

# A command to build and run everything [just for linux]
start: build up run

# A command to stop everything
stop: down

# Clean the project
clean:
	$(MVNW) clean
