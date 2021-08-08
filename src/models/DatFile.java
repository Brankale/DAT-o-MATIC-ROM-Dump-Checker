package models;

import java.util.List;

public interface DatFile {

    public List<String> acceptedRomExtensions();
    public boolean validateCrc(long crc);
    public Rom getRomByCrc(long crc);

}
