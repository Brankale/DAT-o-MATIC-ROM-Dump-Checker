package models;

import java.util.Objects;

public class Rom {

    private final String name;
    private final long crc;

    public Rom(String name, long crc) {
        this.name = name;
        this.crc = crc;
    }

    public String getName() {
        return name;
    }

    public long getCrc() {
        return crc;
    }

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
