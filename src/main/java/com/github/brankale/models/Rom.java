package com.github.brankale.models;

import java.util.Objects;

public record Rom(
        String name,
        long size,
        long crc,
        String md5,
        String sha1,
        String sha256,
        RomStatus romStatus,
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

    public static class Builder {
        private String name;
        private long size;
        private long crc;
        private String md5;
        private String sha1;
        private String sha256;
        private RomStatus romStatus;
        private String serial;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSize(long size) {
            this.size = size;
            return this;
        }

        public Builder setCrc(long crc) {
            this.crc = crc;
            return this;
        }

        public Builder setMd5(String md5) {
            this.md5 = md5;
            return this;
        }

        public Builder setSha1(String sha1) {
            this.sha1 = sha1;
            return this;
        }

        public Builder setSha256(String sha256) {
            this.sha256 = sha256;
            return this;
        }

        public Builder setStatus(RomStatus romStatus) {
            this.romStatus = romStatus;
            return this;
        }

        public Builder setSerial(String serial) {
            this.serial = serial;
            return this;
        }

        public Rom build() {
            return new Rom(name, size, crc, md5, sha1, sha256, romStatus, serial);
        }
    }

}
