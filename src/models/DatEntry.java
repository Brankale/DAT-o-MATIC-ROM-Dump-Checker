package models;

public class DatEntry {

    private final String name;
    private final Rom rom;

    public DatEntry(String name, Rom rom) {
        this.name = name;
        this.rom = rom;
    }

    public String getName() {
        return name;
    }

    public Rom getRom() {
        return rom;
    }

}
