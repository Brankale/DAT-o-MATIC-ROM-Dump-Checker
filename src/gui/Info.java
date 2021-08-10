package gui;

import javax.swing.*;
import java.io.File;

public class Info extends JFrame {

    private final String message =
            "Some notes about CRC check\n" +
            "\n" +
            "CRC check algorithm:\n" +
            "1. The CRC of the ROM is calculated\n" +
            "2. The CRC is searched in the DAT file\n" +
            "3. If the CRC is not present \"bad CRC\" message is shown\n" +
            "\n" +
            "This means that if the DAT file contains for example only\n" +
            "european games but you have the US version, \"bad CRC\"\n" +
            "is shown anyway";

    public Info() {
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea(message);
        textArea.setDragEnabled(false);
        panel.add(textArea);

        setContentPane(panel);
        setTitle("Info");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

}
