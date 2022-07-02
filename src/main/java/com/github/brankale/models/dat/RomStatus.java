package com.github.brankale.models.dat;

public enum RomStatus {
    NOT_VERIFIED(""),
    VERIFIED("verified"),
    BAD_DUMP("baddump");

    private final String string;

    RomStatus(String string) {
        this.string = string;
    }

    public static RomStatus parse(String statusString) {
        for (RomStatus romStatus : RomStatus.values())
            if (romStatus.string.equals(statusString))
                return romStatus;
        throw new IllegalArgumentException("Illegal status: " + statusString);
    }
}
