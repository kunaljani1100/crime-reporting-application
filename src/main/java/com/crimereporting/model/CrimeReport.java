package com.crimereporting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "crime_reports")
public class CrimeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "incident_identifier", updatable = false, nullable = false)
    private UUID incidentIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "crime_type", nullable = false)
    private CrimeType crimeType;

    @Column(name = "date_of_crime", nullable = false)
    private String dateOfCrime;

    @Column(name = "time_of_crime", nullable = false)
    private String timeOfCrime;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    public CrimeReport() {}

    public CrimeReport(CrimeType crimeType, String dateOfCrime, String timeOfCrime, String description) {
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

    public CrimeType getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(CrimeType crimeType) {
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
