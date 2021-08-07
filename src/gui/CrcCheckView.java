package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CrcCheckView extends JFrame {

    private JPanel panel1;
    public JTextArea textArea1;
    public JProgressBar progressBar;
    public JButton stopBtn;

    SwingWorkerTest worker;

    public CrcCheckView(File romsDir, File datFile) {
        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        SwingWorkerTest worker = new SwingWorkerTest(romsDir, datFile, this);
        worker.execute();
    }

}
