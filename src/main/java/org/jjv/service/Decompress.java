package org.jjv.service;

import org.jjv.Globals;
import org.jjv.instance.PathInstance;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Decompress {
    public static void apply() throws IOException {
        File output = new File(Globals.OUTPUT_PATH);

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(PathInstance.getTarget()));
        ZipEntry entry = zis.getNextEntry();

        while (entry != null) {
            File file = createFile(output, entry);
            //validate on linux
            if (entry.isDirectory()){
                if (!file.isDirectory() && !file.mkdirs())
                    throw new IOException("Failed to create dir " + file);
            } else {
                //validate on windows
                File parent = file.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs())
                    throw new IOException("Failed to create dir " + file);

                FileOutputStream fos = new FileOutputStream(file);
                int l;
                while ((l = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, l);
                }
                fos.close();
            }
            entry = zis.getNextEntry();
        }


        zis.closeEntry();
        zis.close();
    }

    private static File createFile(File destination, ZipEntry entry) throws IOException {
        File destinationDir = new File(destination, entry.getName());

        String destinationDirPath = destination.getCanonicalPath();
        String destinationFilePath = destinationDir.getCanonicalPath();

        //avoid zip slip
        if (!destinationFilePath.startsWith(destinationDirPath + File.separator))
            throw new IOException("Not allowed to write outside the target directory");

        return destinationDir;
    }
}
