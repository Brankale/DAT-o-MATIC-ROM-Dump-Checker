package gui;

import models.NdsDat;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.CRC32;

public class SwingWorkerTest extends SwingWorker<Void, String> {

    private final File romsDir;
    private final File dat;
    private final CrcCheckView view;

    public SwingWorkerTest(File romsDir, File dat, CrcCheckView view) {
        this.romsDir = romsDir;
        this.dat = dat;
        this.view = view;

        File[] roms = romsDir.listFiles();
        if (roms != null)
            view.progressBar1.setMaximum(roms.length);
        else
            view.progressBar1.setMaximum(0);
    }

    @Override
    protected Void doInBackground() {

        try {
            NdsDat ndsDat = new NdsDat(dat);
            File[] roms = romsDir.listFiles();

            if (roms != null) {
                for (File rom : roms) {
                    long crc32 = getCrc32(rom);
                    if (ndsDat.contains(crc32)) {
                        publish("[OK]\t : " + rom.getName() + "\n");
                    } else {
                        publish("[bad CRC]\t : " + rom.getName() + "\n");
                    }
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        for (String str : chunks) {
            view.textArea1.append(str);
            view.progressBar1.setValue(view.progressBar1.getValue() + 1);
        }
    }

    @Override
    protected void done() {
        view.stopButton.setEnabled(false);
        view.textArea1.append("Completed!");
    }

    private static long getCrc32(File file) throws IOException {
        CRC32 crc32 = new CRC32();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
        int cnt;
        while ((cnt = stream.read()) != -1) {
            crc32.update(cnt);
        }
        return crc32.getValue();
    }

}


//
//    NdsDat ndsDat;
//            try {
//                    ndsDat = new NdsDat(new File(textField2.getText()));
//                    File romsDirectory = new File(textField1.getText());
//                    File[] roms = romsDirectory.listFiles();
//
//                    if (roms != null) {
//                    view.progressBar1.setMaximum(roms.length);
//                    for (int i = 0; i < roms.length; ++i) {
//        File rom = roms[i];
//        long crc32 = getCrc32(rom);
//
//
//
//
//
//        if (ndsDat.contains(crc32)) {
//
//
//
//        // rom should exist before doing rename
//        NdsGame ndsGame = ndsDat.getNdsGameByCrc32(crc32);
//        if (fixROMsNamesCheckBox.isSelected()) {
//        String path = rom.getParentFile().getAbsolutePath() + "\\" + ndsGame.getName() + ".nds";
////                                System.out.println(path);
//
//        File file2 = new File(path);
//        if (!file2.exists()) {
//        boolean res = rom.renameTo(new File(path));
//        if (res)
//        System.out.println("renamed successfully");
//        else
//        System.out.println("error during renaming");
//        }
//
//
//        }
//
//        view.textArea1.append("[OK] : " + rom.getName() + '\n');
//        System.out.println("[OK] : " + rom.getName());
//        } else {
//        view.textArea1.append("[bad CRC] : " + rom.getName() + '\n');
//        System.err.println("[bad CRC] : " + rom.getName());
//        }
//
//
//        view.progressBar1.setValue(i + 1);
//        }
//        }
//
//        view.textArea1.append("Completed\n");
//        } catch (Exception ex) {
//        ex.printStackTrace();
//        view.textArea1.append("Error!\n");
//        }