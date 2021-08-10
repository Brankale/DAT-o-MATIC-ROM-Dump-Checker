package gui;

import gui.models.Parameters;
import models.DatEntry;
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

    private final Parameters params;
    private final CrcCheckView view;

    public ValidateCRCs(Parameters params, CrcCheckView view) {
        this.params = params;
        this.view = view;


        File[] roms = params.getRomsDirectory().listFiles();
        if (roms != null)
            view.progressBar.setMaximum(roms.length);
        else
            view.progressBar.setMaximum(0);
    }

    @Override
    protected Void doInBackground() {

        try {
            DatFile datFile = new DatFile(params.getDat());
            File[] roms = params.getRomsDirectory().listFiles();

            if (roms != null) {
                for (File rom : roms) {

                    if (isCancelled()) {
                        publish("Interrupted!");
                        return null;
                    }

                    if (!isValidFile(rom, datFile.getAcceptedExtensions())) {
                        if (!params.hideWarnings()) {
                            publish("skip invalid file\t" + rom.getPath() + "\n");
                        } else {
                            publish("");
                        }
                        continue;
                    }

                    long crc32 = getCrc32(rom);
                    if (datFile.validateCrc(crc32)) {
                        DatEntry datEntry = datFile.getDatEntryByCrc(crc32);
                        // this thing is necessary to have a readable name with the extension
                        // because romName can be a long string of numbers instead of game name
                        // but it has the rom extension
                        String newName = datEntry.getName() ;
                        String extension = getFileExtension(datEntry.getRom().getName());
                        newName = newName + "." + extension;

                        boolean renamed = false;
                        if (params.noIntroNameConvention()) {
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

    private boolean isValidFile(File rom, List<String> acceptedExtensions) {
        if (!rom.isDirectory()) {
            String extension = getFileExtension(rom.getName());
            return acceptedExtensions.contains(extension);
        }
        return false;
    }

    private String getFileExtension(String fileName) {
        int dotPos = fileName.lastIndexOf('.');
        if (dotPos != -1) {
            return fileName.substring(dotPos + 1);
        }
        return null;
    }


}