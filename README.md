# JC Kafka POC

A Proof of Concept project to explore Apache Kafka capabilities using Spring Boot 3 and Java 21.

## Purpose & Goals

This project serves as a playground to test and validate Kafka concepts, specifically:
1.  **Basic Pub/Sub**: Setting up a Producer and Consumer.
2.  **Log Compaction**: Verifying how consumers handle compacted topics and state recovery.
3.  **Kafka State Store**: Exploring stateful processing.

## Prerequisites

*   **Java 21**: Required for the Spring Boot applications.
*   **Docker & Docker Compose**: Required for running the Kafka infrastructure.
*   **Maven**: (Optional) The project includes a Maven Wrapper (`mvnw`), so a local installation is not strictly required.

### Setup on Arch Linux (Example)

```bash
# Install Java 21 and Docker tools
sudo pacman -S jdk21-openjdk docker docker-compose

# Ensure Docker is running
sudo systemctl start docker
# (Optional) Enable Docker to start on boot
sudo systemctl enable docker

# Setup Docker for local user (avoids using sudo for docker commands)
sudo usermod -aG docker $USER
# Note: You must log out and log back in for this group change to take effect.

# Verify Java version
java -version
# Should output: openjdk version "21..."
```

## Getting Started

### 1. Start the Infrastructure
We use Docker Compose to run a single-node Kafka cluster (KRaft mode) and the AKHQ UI.

```bash
docker compose up -d
```

*   **Kafka**: Listening on `localhost:9094` (external) and `kafka:9092` (internal).
*   **AKHQ UI**: Accessible at http://localhost:8080.

### 2. Run the Producer
The Producer service generates random FX rates for pairs (e.g., USD_CAD, EUR_CAD) and sends them to the `fx-rates` topic.

Use the Maven Wrapper to build and run the producer module. We use the `-am` (also make) flag to ensure the `common` module is built first.

```bash
./mvnw clean install -pl producer -am -DskipTests && ./mvnw spring-boot:run -pl producer
```

You should see output indicating rates are being sent:
```text
Sent: FxRate[pair=USD_CAD, rate=1.3505, inverseRate=0.7405, timestamp=...]
```

## TODO / Roadmap

- [x] Setup Kafka Cluster (KRaft) & AKHQ locally.
- [x] Define standard FX Rate DTO and Topic naming.
- [x] Create Producer to generate random market data.
- [ ] **Create Consumer**: Implement a Spring Boot consumer to listen for FX rates.
- [ ] **Log Compaction**: Configure the topic for compaction and verify consumer catch-up behavior on restart.
- [ ] **State Store**: Explore Kafka Streams or state store concepts for aggregating data.