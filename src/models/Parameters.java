package models;

public class Parameters {

    private boolean fixRomsNames;
    private boolean trimRegion;

    public boolean fixRomsNames() {
        return fixRomsNames;
    }

    public void setFixRomsNames(boolean fixRomsNames) {
        this.fixRomsNames = fixRomsNames;
    }

    public boolean trimRegion() {
        return trimRegion;
    }

    public void setTrimRegion(boolean trimRegion) {
        this.trimRegion = trimRegion;
    }

}
