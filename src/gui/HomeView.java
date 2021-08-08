package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class HomeView extends JFrame {
    private JTextField romFolder;
    private JButton romFolderBtn;
    private JButton datFileBtn;
    private JTextField datFile;
    private JButton checkCrcBtn;
    private JPanel home;
    private JCheckBox fixRomName;

    public HomeView() {
        romFolderBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                romFolder.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        datFileBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("DAT FILES", "dat");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                datFile.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        checkCrcBtn.addActionListener(e -> {
            if (canDoCrcCheck()) {
                File romsDir = new File(romFolder.getText());
                File datFile = new File(this.datFile.getText());
                CrcCheckView crcCheckView = new CrcCheckView(romsDir, datFile, fixRomName.isSelected());
            } else {
                String message = "Select both ROM folder and DAT file";
                JOptionPane.showMessageDialog(this, message);
            }
        });

        setContentPane(home);
        setTitle("NDS ROMs CRC Check");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    boolean canDoCrcCheck() {
        return !romFolder.getText().isEmpty() & !datFile.getText().isEmpty();
    }

}
