package gui;

import javax.swing.*;
import java.io.File;

public class HomeView extends JFrame {
    private JTextField textField1;
    private JButton button1;
    private JButton browseButton;
    private JTextField textField2;
    private JButton checkCRCsButton;
    private JPanel home;
    private JCheckBox fixROMsNamesCheckBox;

    public HomeView() {
        button1.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                textField1.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        browseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                textField2.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        checkCRCsButton.addActionListener(e -> {
            if (canDoCrcCheck()) {
                CrcCheckView view = new CrcCheckView();
                File romsDir = new File(textField1.getText());
                File datFile = new File(textField2.getText());
                SwingWorkerTest test = new SwingWorkerTest(romsDir, datFile, view);
                test.execute();
            } else {
                String message = "Select both ROM folder and DAT file";
                JOptionPane.showMessageDialog(this, message);
            }
        });

        setContentPane(home);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    boolean canDoCrcCheck() {
        return !textField1.getText().isEmpty() & !textField2.getText().isEmpty();
    }

}
