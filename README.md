# Crime Reporting Application

A Spring Boot REST API that allows crime victims to report incidents. Reports are stored in a Google Cloud SQL (PostgreSQL) database. The application is designed to be deployed on Google Cloud Run as a Docker container.

## API

### Report a Crime

**POST** `/api/crimes`

**Request Body:**

```json
{
  "crimeType": "robbery",
  "dateOfCrime": "01-15-2026",
  "timeOfCrime": "14:30",
  "description": "A robbery occurred at the downtown bank."
}
```

**Fields:**

| Field | Type | Description |
|---|---|---|
| `crimeType` | String | Type of crime. Must be one of: `robbery`, `assault`, `theft`, `scam` |
| `dateOfCrime` | String | Date of the incident in `MM-DD-YYYY` format |
| `timeOfCrime` | String | Time of the incident in `HH:MM` format (`00:00` to `23:59`) |
| `description` | String | Full description of the incident |

**Response (201 Created):**

```json
{
  "incidentIdentifier": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "crimeType": "robbery",
  "dateOfCrime": "01-15-2026",
  "timeOfCrime": "14:30",
  "description": "A robbery occurred at the downtown bank."
}
```

The `incidentIdentifier` is a unique UUID generated automatically for each report.

## Database Schema

The application uses a `crime_reports` table with the following columns:

| Column | Type | Description |
|---|---|---|
| `incident_identifier` | UUID | Primary key, auto-generated |
| `crime_type` | VARCHAR | One of: ROBBERY, ASSAULT, THEFT, SCAM |
| `date_of_crime` | VARCHAR | Date in MM-DD-YYYY format |
| `time_of_crime` | VARCHAR | Time in HH:MM format |
| `description` | TEXT | Full incident description |

## Prerequisites

- Java 17+
- Google Cloud project with Cloud SQL (PostgreSQL) instance
- Docker (for containerized deployment)

## Configuration

Set the following environment variables:

| Variable | Description | Default |
|---|---|---|
| `INSTANCE_CONNECTION_NAME` | Cloud SQL instance connection name (`project:region:instance`) | - |
| `DB_NAME` | Database name | `crime_reports` |
| `DB_USER` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | - |
| `PORT` | Server port | `8080` |

## Building and Running Locally

```bash
# Build
./gradlew build

# Run tests
./gradlew test

# Run the application (requires Cloud SQL configuration)
./gradlew bootRun
```

## Docker

```bash
# Build the Docker image
docker build -t crime-reporting-application .

# Run the container
docker run -p 8080:8080 \
  -e INSTANCE_CONNECTION_NAME=project:region:instance \
  -e DB_NAME=crime_reports \
  -e DB_USER=postgres \
  -e DB_PASSWORD=yourpassword \
  crime-reporting-application
```

## Deploying to Google Cloud Run

```bash
# Build and push to Google Container Registry
gcloud builds submit --tag gcr.io/PROJECT_ID/crime-reporting-application

# Deploy to Cloud Run
gcloud run deploy crime-reporting-application \
  --image gcr.io/PROJECT_ID/crime-reporting-application \
  --platform managed \
  --region REGION \
  --add-cloudsql-instances PROJECT_ID:REGION:INSTANCE_NAME \
  --set-env-vars INSTANCE_CONNECTION_NAME=PROJECT_ID:REGION:INSTANCE_NAME,DB_NAME=crime_reports,DB_USER=postgres,DB_PASSWORD=yourpassword \
  --allow-unauthenticated
```
