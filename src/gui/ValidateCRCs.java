package gui;

import gui.models.Parameters;
import models.DatFile;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.CRC32;

public class ValidateCRCs extends SwingWorker<Void, String> {

    private final File romsDir;
    private final File dat;
    private final CrcCheckView view;
    private final Parameters params;

    public ValidateCRCs(File romsDir, File dat, Parameters params, CrcCheckView view) {
        this.romsDir = romsDir;
        this.dat = dat;
        this.view = view;
        this.params = params;

        File[] roms = romsDir.listFiles();
        if (roms != null)
            view.progressBar.setMaximum(roms.length);
        else
            view.progressBar.setMaximum(0);
    }

    @Override
    protected Void doInBackground() {

        try {
            DatFile datFile = new DatFile(dat);
            File[] roms = romsDir.listFiles();

            if (roms != null) {
                for (File rom : roms) {

                    if (isCancelled()) {
                        publish("Interrupted!");
                        return null;
                    }

                    if (rom.isDirectory()) {
                        publish("skip directory\t\\" + rom.getName() + "\n");
                        continue;
                    }

                    if (rom.isFile() && !rom.getName().endsWith(".nds")) {
                        publish("skip file\t" + rom.getName() + "\n");
                        continue;
                    }

                    long crc32 = getCrc32(rom);
                    if (datFile.validateCrc(crc32)) {
                        String newName = datFile.getDatEntryByCrc(crc32).getName();

                        boolean renamed = false;
                        if (params.fixRomsNames()) {
                            renamed = fixRomName(rom, newName);
                        }
                        if (renamed)
                            publish("[OK]\t" + newName + "\n");
                        else
                            publish("[OK]\t" + rom.getName() + "\n");
                    } else {
                        publish("[bad CRC]\t" + rom.getName() + "\n");
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
        if (!isCancelled())
            view.textArea1.append("Completed!\n");
    }

    private static long getCrc32(File file) throws IOException {
        CRC32 crc32 = new CRC32();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
        int cnt;
        while ((cnt = stream.read()) != -1) {
            crc32.update(cnt);
        }
        stream.close();
        return crc32.getValue();
    }

    private boolean fixRomName(File rom, String newName) {

        if (params.trimRegion()) {
            newName = newName.substring(0, newName.indexOf('(')).trim()
                    + newName.substring(newName.lastIndexOf('.'));
        }

        if (!rom.getName().equals(newName)) {
            Path source = rom.toPath();
            try {
                Files.move(source, source.resolveSibling(newName));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}