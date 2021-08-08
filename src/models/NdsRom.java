package models;

import java.util.Objects;

public class NdsRom implements Rom {

    private String name;
    private String description;
    private RomInfo romInfo;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getCrc() {
        return romInfo.getCrc32();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RomInfo getRomInfo() {
        return romInfo;
    }

    public void setRomInfo(RomInfo romInfo) {
        this.romInfo = romInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NdsRom ndsRom = (NdsRom) o;
        return romInfo.equals(ndsRom.romInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(romInfo);
    }

}
