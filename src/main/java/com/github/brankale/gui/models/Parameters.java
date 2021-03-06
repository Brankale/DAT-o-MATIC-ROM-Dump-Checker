package com.github.brankale.gui.models;

import java.io.File;

public class Parameters {

    private final File romsDirectory;
    private final File dat;
    private final boolean noIntroNameConvention;
    private final boolean trimRegion;
    private final boolean hideWarnings;

    public static class Builder {

        private final File romsDirectory;
        private final File dat;

        // optional parameters
        private boolean noIntroNameConvention = false;
        private boolean trimRegion = false;
        private boolean hideWarnings = true;

        public Builder(File romsDirectory, File dat) {
            this.romsDirectory = romsDirectory;
            this.dat = dat;
        }

        public Builder noIntroNameConvention(boolean val) {
            noIntroNameConvention = val;
            return this;
        }

        public Builder trimRegion(boolean val) {
            trimRegion = val;
            return this;
        }

        public Builder hideWarnings(boolean val) {
            hideWarnings = val;
            return this;
        }

        public Parameters build() {
            return new Parameters(this);
        }

    }

    private Parameters(Builder builder) {
        romsDirectory = builder.romsDirectory;
        dat = builder.dat;
        noIntroNameConvention = builder.noIntroNameConvention;
        trimRegion = builder.trimRegion;
        hideWarnings = builder.hideWarnings;
    }

    public File getRomsDirectory() {
        return romsDirectory;
    }

    public File getDat() {
        return dat;
    }

    public boolean noIntroNameConvention() {
        return noIntroNameConvention;
    }

    public boolean trimRegion() {
        return trimRegion;
    }

    public boolean hideWarnings() {
        return hideWarnings;
    }

}
