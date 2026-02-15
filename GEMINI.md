# Gemini Code Assist Instructions

## Persona
- Act as a senior developer.
- Focus on clean, maintainable, and production-ready code.

## Project Context
This project is a Kafka Proof of Concept (POC).

## Coding Guidelines
- **Error Handling**: Focus on visibility. Use Producer callbacks and Consumer error handlers. Avoid complex retry logic for the POC, but ensure errors are logged.
- **Logging**: Use SLF4J (`Logger`) instead of `System.out`. This aids in debugging async Kafka events.
- **Configuration**: Prefer `application.yml` for configurable values (topics, brokers) over hardcoded constants.
- **Topic Management**: Define topics programmatically (e.g., `NewTopic` beans) rather than relying on auto-creation. This ensures reproducible configuration for advanced features like log compaction.
- **Serialization**: Use `StringSerializer` for keys and `JsonSerializer` for values to ensure compatibility between modules.
- Prefer modern idioms for the languages used.
- Use Java 21 and Maven.
- Create a multi-module project structure:
    - `common`: Shared data models (DTOs).
    - `producer`: Spring Boot Producer application.
    - `consumer`: Spring Boot Consumer application.
- Ensure all classes and public methods have comprehensive Javadoc.
- Update the `README.md` file as part of the code changes whenever new features, setup steps, or architectural decisions are made.

## Goals
- I want to test out some Kafka concepts in a few stages
    - First Setup a kafka cluster locally 
    - Second i want to setup a simple pub/sub to get the basics going
    - Third I'll want to modify to add topic compaction and I will want to prove to myself that the consumer sees incoming messages even if compression has happen. I will also want to restart Kafka and have consumer print out the log, indicate when its caught up and then continue printing new ones
    - Fourth I will want to explore Kafka State Store 
- Run Kafka locally use docker
   - ensure a standard setup which I think might include a docker file or possibly docker compose given eventually I probably want to startup kafka, producer and consumer and observe output
   - Include a web-based Kafka UI (like AKHQ or UI for Apache Kafka) in the docker-compose file to easily inspect topics and messages.
- General components I want to build, but open to ideas if there are more standard conventions
    - Create a Kafka Topic which a standard naming convention for FX rate
    - a Spring Boot Kafka producer that will fire FX Rate for various pairs
    - the pairs
        - Key: "USD_CAD", Value: JSON object containing USD/CAD and CAD/USD rates.
        - Key: "EUR_CAD", Value: JSON object containing EUR/CAD and CAD/EUR rates.
        - Key: "GBP_CAD", Value: JSON object containing GBP/CAD and CAD/GBP rates.
        - Key: "AUD_CAD", Value: JSON object containing AUD/CAD and CAD/AUD rates.
        - *Note: Using the Pair Name as the Kafka Record Key is critical for the Log Compaction step.*
        - JSON Structure Example:
            ```json
            {
              "pair": "USD_CAD",
              "rate": 1.3500,
              "inverseRate": 0.7407,
              "timestamp": "2024-02-13T10:00:00Z"
            }
            ```
    - it will print out the message sent including unique key and timestamp
    - just pick roughly reasonable FX rates for each pair and then every second increase or decrease each pair by a small random amount to simulate a live behaviour
    - Include a source timestamp in the FX message, but also consider using any inherent kafka concepts that might do the same thing or help me track lag
    - a Springboot Kafka consumer that will consume the FX Rate messages and print out the message contents including unique key and timestamp


    
