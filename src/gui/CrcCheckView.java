package gui;

import javax.swing.*;

public class CrcCheckView extends JFrame {

    private JPanel panel1;
    public JTextArea textArea1;
    public JProgressBar progressBar1;
    public JButton stopButton;

    public CrcCheckView() {
        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
