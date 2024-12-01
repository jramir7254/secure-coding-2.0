package com.hacktheborder.utilities;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileManager {
    private File javaFile;
    private String tempDir;
    private String className;


    public FileManager() {
        tempDir = System.getProperty("java.io.tmpdir");

        className = "Main"; 
        javaFile = new File(tempDir, "Main.java");
    }

    public String getFile() {
        return this.tempDir;
    }


    public void deleteFile() {
        if (javaFile.exists() && javaFile.delete()) {
            System.out.println("Deleted file: " + javaFile);
        } else {
            System.out.println("Failed to delete file: " + javaFile);
        }
    }


    public void writeToFile(String output) {
        try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(javaFile))) {
            fileWriter.append(output);
        } catch (Exception e) {
            System.err.println("Exception message from writeToFile(String output) @FileManager: " + e.getMessage());
        }
    }
}
