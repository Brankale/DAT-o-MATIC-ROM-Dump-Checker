package com.github.brankale.models.dat;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class DatFile {

    private final TreeMap<Long, DatEntry> entries;
    private final TreeSet<String> extensions;

    public DatFile() {
        entries = new TreeMap<>();
        extensions = new TreeSet<>();
    }

    public void addEntry(DatEntry entry) {
        entries.put(entry.getRom().crc(), entry);
        extensions.add(entry.getRom().getExtension());
    }

    public boolean validateCrc(long crc) {
        return entries.containsKey(crc);
    }

    public DatEntry getDatEntryByCrc(long crc) {
        return entries.get(crc);
    }

    public List<String> getAcceptedExtensions() {
        return new ArrayList<>(extensions);
    }

}
