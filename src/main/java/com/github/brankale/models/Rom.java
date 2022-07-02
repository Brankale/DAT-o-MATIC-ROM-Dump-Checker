package com.github.brankale.models;

import java.util.Objects;

public record Rom(
        String name,
        long size,
        long crc,
        String md5,
        String sha1,
        String sha256,
        Status status,
        String serial
) {

    public String getExtension() {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rom rom = (Rom) o;
        return crc == rom.crc;
    }

    @Override
    public int hashCode() {
        return Objects.hash(crc);
    }

}
