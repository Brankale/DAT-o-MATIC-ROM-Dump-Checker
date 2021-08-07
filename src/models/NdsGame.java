package models;

import java.util.Objects;

public class NdsGame {

    private String name;
    private String description;
    private RomInfo romInfo;

    public String getName() {
        return name;
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
        NdsGame ndsGame = (NdsGame) o;
        return romInfo.equals(ndsGame.romInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(romInfo);
    }

}
