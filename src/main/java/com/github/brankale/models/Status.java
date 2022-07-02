package com.github.brankale.models;

public enum Status {
    NOT_VERIFIED,
    VERIFIED("verified"),
    BAD_DUMP("baddump");

    private final String string;

    Status() {
        string = "";
    }

    Status(String string) {
        this.string = string;
    }

    public static Status parse(String statusString) {
        for (Status status : Status.values())
            if (status.string.equals(statusString))
                return status;
        throw new IllegalArgumentException("Illegal status: " + statusString);
    }
}
