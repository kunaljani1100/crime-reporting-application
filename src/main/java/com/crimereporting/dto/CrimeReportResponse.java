package com.crimereporting.dto;

import java.util.UUID;

public class CrimeReportResponse {

    private UUID incidentIdentifier;
    private String crimeType;
    private String dateOfCrime;
    private String timeOfCrime;
    private String description;

    public CrimeReportResponse() {}

    public CrimeReportResponse(UUID incidentIdentifier, String crimeType, String dateOfCrime,
                               String timeOfCrime, String description) {
        this.incidentIdentifier = incidentIdentifier;
        this.crimeType = crimeType;
        this.dateOfCrime = dateOfCrime;
        this.timeOfCrime = timeOfCrime;
        this.description = description;
    }

    public UUID getIncidentIdentifier() {
        return incidentIdentifier;
    }

    public void setIncidentIdentifier(UUID incidentIdentifier) {
        this.incidentIdentifier = incidentIdentifier;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public String getDateOfCrime() {
        return dateOfCrime;
    }

    public void setDateOfCrime(String dateOfCrime) {
        this.dateOfCrime = dateOfCrime;
    }

    public String getTimeOfCrime() {
        return timeOfCrime;
    }

    public void setTimeOfCrime(String timeOfCrime) {
        this.timeOfCrime = timeOfCrime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
