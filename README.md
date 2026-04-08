# Restful Booker API Test Suite

This repository contains a Java-based automated API test project for the [Restful Booker demo API](https://restful-booker.herokuapp.com).

The suite is built with Gradle and uses Rest Assured for HTTP requests and JUnit 5 for test execution.

## Technology Stack

- Java 21
- Gradle
- JUnit 5
- Rest Assured
- AssertJ
- Jackson
- Lombok

## Prerequisites

- Java 21 SDK installed
- Internet access to reach `https://restful-booker.herokuapp.com`

## Running the Tests

From the project root directory, run:

```bash
./gradlew test
```

This will download dependencies, compile the test sources, and execute the JUnit test suite.

## Project Structure

- `build.gradle` – Gradle configuration and dependency definitions.
- `gradle.properties` – Gradle property settings.
- `gradlew`, `gradlew.bat` – Gradle wrapper scripts.
- `settings.gradle` – Gradle project settings.
- `src/test/java/com/restfulbooker/api/tests/` – Test suite.
- `src/test/java/com/restfulbooker/api/setup/` – Rest Assured setup and shared test configuration.
- `src/test/java/com/restfulbooker/api/dto/` – Data transfer object classes for JSON deserialization.
- `src/test/resources/` – Test resources and fixtures.
- `README.md` – Project documentation.

## Key Features

- Validate status code
- Validate response content
- Validate schema response
- Use builder for request and response
- Use given - when - then structure
- Parametized tests

## Notes

- The project currently contains only test code under `src/test/java`.
- Test configuration is centralized in `BaseTest`.

## References

- [Let's build API checking framework - MoT](https://www.ministryoftesting.com/courses/let-s-build-an-api-checking-framework-mark-winteringham)
- [Common mistakes in REST API Testing with Rest Assured](https://medium.com/@pacacierdogan/common-mistakes-in-rest-api-testing-with-rest-assured-a41a475b4879)
- [How to test with Rest-Assured - Series](https://medium.com/javarevisited/how-to-test-post-requests-with-rest-assured-for-api-testing-part-i-d697efab5875)
