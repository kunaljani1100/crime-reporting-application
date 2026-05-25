package com.crimereporting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CrimeReportRequest {

    @NotNull(message = "Crime type is required")
    @Pattern(regexp = "(?i)robbery|assault|theft|scam",
            message = "Crime type must be one of: robbery, assault, theft, scam")
    private String crimeType;

    @NotBlank(message = "Date of crime is required")
    @Pattern(regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])-\\d{4}$",
            message = "Date of crime must be in MM-DD-YYYY format")
    private String dateOfCrime;

    @NotBlank(message = "Time of crime is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$",
            message = "Time of crime must be in HH:MM format (00:00 to 23:59)")
    private String timeOfCrime;

    @NotBlank(message = "Description is required")
    private String description;

    public CrimeReportRequest() {}

    public CrimeReportRequest(String crimeType, String dateOfCrime, String timeOfCrime, String description) {
        this.crimeType = crimeType;
        this.dateOfCrime = dateOfCrime;
        this.timeOfCrime = timeOfCrime;
        this.description = description;
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
