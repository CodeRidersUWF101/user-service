package com.coderiders.userservice.models;

public enum ReadingStatus {
    NOT_STARTED("NOT_STARTED"),
    STARTED("STARTED"),
    COMPLETED("COMPLETED");

    public final String value;

    ReadingStatus(String value) {
        this.value = value;
    }
}
