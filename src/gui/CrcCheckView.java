package gui;

import gui.models.Parameters;

import javax.swing.*;
import java.io.File;

public class CrcCheckView extends JFrame {

    private JPanel panel1;
    public JTextArea textArea1;
    public JProgressBar progressBar;
    public JButton stopBtn;

    ValidateCRCs worker;

    public CrcCheckView(File romsDir, File datFile, Parameters params) {
        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        ValidateCRCs worker = new ValidateCRCs(romsDir, datFile, params, this);
        worker.execute();

        stopBtn.addActionListener(e -> worker.cancel(true));
    }

}
