package com.github.brankale.gui;

import com.github.brankale.gui.models.Parameters;

import javax.swing.*;

public class CrcCheckView extends JFrame {

    private JPanel panel1;
    public JTextArea textArea1;
    public JProgressBar progressBar;
    public JButton stopBtn;

    public CrcCheckView(Parameters params) {
        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        ValidateCRCs worker = new ValidateCRCs(params, this);
        worker.execute();

        stopBtn.addActionListener(e -> worker.cancel(true));
    }

}
