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
            view.progressBar.setMaximum(roms.length);
        else
            view.progressBar.setMaximum(0);
    }

    @Override
    protected Void doInBackground() {

        try {
            NdsDat ndsDat = new NdsDat(dat);
            File[] roms = romsDir.listFiles();

            if (roms != null) {
                for (File rom : roms) {
                    // TODO: skip directories
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
            view.progressBar.setValue(view.progressBar.getValue() + 1);
        }
    }

    @Override
    protected void done() {
        view.stopBtn.setEnabled(false);
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