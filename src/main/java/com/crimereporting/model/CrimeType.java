package com.crimereporting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CrimeType {

    ROBBERY("robbery"),
    ASSAULT("assault"),
    THEFT("theft"),
    SCAM("scam");

    private final String value;

    CrimeType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CrimeType fromValue(String value) {
        for (CrimeType type : CrimeType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(
                "Invalid crime type: '" + value + "'. Must be one of: robbery, assault, theft, scam");
    }
}
