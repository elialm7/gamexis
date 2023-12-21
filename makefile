# Makefile for Maven commands
MAVEN := mvn

.PHONY: all
all: build run

.PHONY: clean
clean:
	$(MAVEN) clean

.PHONY: build
build:
	$(MAVEN) -Pdesktop package -DskipTests

.PHONY: build-wiht-tests
build-with-tests:
	$(MAVEN) -Pdesktop package

.PHONY: run
run: build
	cd desktop/target && java -jar gamexis-desktop-1.0.0-jar-with-dependencies.jar

.PHONY: test
test:
	$(MAVEN) test

.PHONY: help
help:
	@echo "Available targets:"
	@echo "  all           : Build and run the project"
	@echo "  clean         : Clean the project"
	@echo "  build         : Build the project with skipping tests"
	@echo "  build-with-tests : Build the project with running tests"
	@echo "  run           : Run the project"
	@echo "  test          : Run tests"
	@echo "  help          : Show this help message"

# Default target
.DEFAULT_GOAL := help

