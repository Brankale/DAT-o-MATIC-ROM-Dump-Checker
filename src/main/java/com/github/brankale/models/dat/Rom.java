package com.github.brankale.models.dat;

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

        public void setName(String name) {
            this.name = name;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public void setCrc(long crc) {
            this.crc = crc;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public void setSha256(String sha256) {
            this.sha256 = sha256;
        }

        public void setStatus(RomStatus romStatus) {
            this.romStatus = romStatus;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public Rom build() {
            return new Rom(name, size, crc, md5, sha1, sha256, romStatus, serial);
        }
    }

}
