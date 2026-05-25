# Crime Reporting Application

A minimal Spring Boot application with a health check endpoint. No database is required.

## Health Check

**GET** `/actuator/health`

Example response:

```json
{
  "status": "UP"
}
```

## Prerequisites

- Java 17+
- Docker (optional, for containerized deployment)

## Configuration

| Variable | Description | Default |
|---|---|---|
| `PORT` | Server port | `8080` |

## Building and Running Locally

```bash
./gradlew build
./gradlew test
./gradlew bootRun
curl http://localhost:8080/actuator/health
```

## Docker

```bash
docker build -t crime-reporting-application .
docker run -p 8080:8080 crime-reporting-application
```

## Deploying to Google Cloud Run

```bash
gcloud builds submit --tag gcr.io/PROJECT_ID/crime-reporting-application

gcloud run deploy crime-reporting \
  --image gcr.io/PROJECT_ID/crime-reporting-application \
  --platform managed \
  --region REGION \
  --allow-unauthenticated
```
