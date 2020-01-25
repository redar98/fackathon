package com.fackathon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class FileManager {

    public static String getFileContents(final String filePath) {
        File file = new File(filePath);
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            return new String(data);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Failed to get file content!");
        }
    }

}
