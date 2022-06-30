package com.github.brankale;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.brankale.gui.HomeView;

public class Main {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new HomeView();
    }

}
