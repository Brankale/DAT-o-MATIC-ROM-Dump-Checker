package models;

import java.util.Objects;

public class RomInfo {

    private String name;
    private long size;
    private long crc32;
    private String md5;
    private String sha1;
    private String serial;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSize(String size) {
        this.size = Long.decode(size);
    }

    public long getCrc32() {
        return crc32;
    }

    public void setCrc32(long crc32) {
        this.crc32 = crc32;
    }

    public void setCrc32(String crc32) {
        this.crc32 = Long.decode("0x" + crc32);
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RomInfo romInfo = (RomInfo) o;
        return sha1.equals(romInfo.sha1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha1);
    }

}
