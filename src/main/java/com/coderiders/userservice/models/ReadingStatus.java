package com.coderiders.userservice.models;

public enum ReadingStatus {
    NOT_STARTED("NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    public final String value;

    ReadingStatus(String value) {
        this.value = value;
    }
}
