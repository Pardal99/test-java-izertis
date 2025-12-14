# JAVA TEST IZERTIS – Hexagonal Architecture Demo

## Project Overview

This project is a demonstration and test implementation using **Hexagonal Architecture**, **Clean Code** principles, and **Domain-Driven Design (DDD)** for **IZERTIS**.

The main goal of the project is to expose an API capable of returning the applicable price for a product and brand at a given application date, following the rules described in the technical exercise.

Sample data in the `PRICES` table used by the exercise (an explicit surrogate `id` column has been added):

| ID | BRAND_ID | START_DATE           | END_DATE             | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----:|----------:|:---------------------|:---------------------|:----------:|:----------:|:--------:|:-----:|:----:|
| 1  | 1         | 2020-06-14 00:00:00  | 2020-12-31 23:59:59  | 1          | 35455      | 0        | 35.50 | EUR  |
| 2  | 1         | 2020-06-14 15:00:00  | 2020-06-14 18:30:00  | 2          | 35455      | 1        | 25.45 | EUR  |
| 3  | 1         | 2020-06-15 00:00:00  | 2020-06-15 11:00:00  | 3          | 35455      | 1        | 30.50 | EUR  |
| 4  | 1         | 2020-06-15 16:00:00  | 2020-12-31 23:59:59  | 4          | 35455      | 1        | 38.95 | EUR  |

## Technical Assumptions

- All dates/times stored in the `prices` table are in UTC. Through the API we force that all timestamps provided in requests include timezone information in ISO-8601 format (for example: 2020-06-14T18:00:00+02:00). The application will convert those values to UTC for storage and validation.
- Price intervals for the same brand/product/priority may overlap because H2 does not support exclusion constraints or interval-based uniqueness. This means the database cannot enforce that prices with the same brand, product, and priority do not overlap in time.
- The rule about non-overlapping prices is treated as a domain assumption and is validated at the application level. The use of `LIMIT 2` in queries is a defensive measure to detect possible violations.
- Indexes are created to optimize queries by brand, product, priority and date range, avoiding full table scans.
- We have defined an `id` field on the `PRICES` table even though there is no natural single-column identifier. With Spring Data JPA it is necessary to declare a unique identifier (`@Id`) for each entity — even when the business key is composite — because JPA manages entity identity and lifecycle, not just raw database rows. Defining a surrogate `id` simplifies mapping, caching and entity lifecycle operations in JPA.

### Tests and Timezone Assumptions

- For the purpose of the provided test cases, it is assumed that the application dates specified in the technical exercise correspond to the **Madrid timezone (GMT+2)**.
- The original statement does not explicitly define a timezone for the example requests (e.g. “request at 16:00 on June 14th”), which makes them inherently ambiguous.
- To keep the tests deterministic and aligned with the expected outcomes, the example times are interpreted as Madrid local time and ared converted by the application to UTC before querying the database.
- This assumption is explicitly documented to avoid hidden or implicit timezone conversions and to ensure clarity and consistency in test execution.

## Running the Application

The application can be started using Maven:

```bash
mvn spring-boot:run
```

## Functioanl Testing

This project exposes a REST API documented using **Swagger UI** for functional testing. Once the application is running, you can access Swagger UI at:

http://localhost:8080/swagger-ui.html


Also, you can import the **Postman collection** for this API:

- **File:** [docs/postman/test-java-izertis.postman_collection.json](./docs/postman/test-java-izertis.postman_collection.json) — import this file in Postman (Import → File).

Quick steps:

1. Open Postman → Import → File.
2. Select `docs/postman/test-java-izertis.postman_collection.json`.
3. Run the requests from the imported collection.

There are 5 use cases based on the exercise specification. We assume **Madrid timezone (GMT+2)** for the dates.

- Test 1: request at 2020-06-14T10:00:00+02:00 (10:00 on June 14th, Madrid time GMT+2)
- Test 2: request at 2020-06-14T16:00:00+02:00 (16:00 on June 14th, Madrid time GMT+2)
- Test 3: request at 2020-06-14T21:00:00+02:00 (21:00 on June 14th, Madrid time GMT+2)
- Test 4: request at 2020-06-15T10:00:00+02:00 (10:00 on June 15th, Madrid time GMT+2)
- Test 5: request at 2020-06-16T21:00:00+02:00 (21:00 on June 16th, Madrid time GMT+2)

Extra test:

- Test 6: request at 2020-06-14T18:00:00+02:00 (18:00 on June 14th, Madrid time GMT+2)

Notes:

- Use the endpoint `POST /api/v1/prices/find-by-application-date` with a JSON body containing `brand_id`, `product_id` and `application_date`.
- As commented in **Tests and Timezone Assumptions**, take care about time zone alignments. For deterministic results, align the `application_date` timezone with Madrid (e.g. `2020-06-14T10:00:00+02:00`) or convert to UTC as required by your test setup. The application stores and processes all dates/times in UTC. All validations and data checks are performed against UTC values. Request supplies a date/time in Madrid local time, the application will convert that value to UTC before performing any lookup or validation. Ensure tests either provide UTC timestamps or include the Madrid timezone offset (e.g. `+02:00`).

Example request body:

```json
{
	"brand_id": 1,
	"product_id": 35455,
	"application_date": "2020-06-14T10:00:00+02:00"
}
```

## Code Coverage and Quality Report

This project uses **JaCoCo** to measure test coverage and enforce basic quality standards.

A full **HTML coverage report** is generated during the build process and published using **GitHub Pages**, allowing easy review without requiring local execution.

### 📊 Coverage Report

The coverage report is publicly available at:

https://pardal99.github.io/test-java-izertis/jacoco/index.html


## Continuous Integration (CI)

This project includes a **GitHub Actions CI pipeline** to automatically validate code quality on each relevant change.

The pipeline is responsible for:

- Compiling the project

```bash
mvn clean -U package -Dmaven.test.skip=true
```

- Executing unit tests

```bash
mvn test
```

- Generating a JaCoCo coverage report

```bash
mvn jacoco:report jacoco:check
```