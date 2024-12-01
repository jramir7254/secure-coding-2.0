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
        String javaFilePath = tempDir + "/Test.java";
        className = "Test"; //QuestionManager.getQuestionClassName();
        javaFile = new File(tempDir, "Test.java");
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

        }

    }

    
    public static String readFile() {
        try(Scanner scan = new Scanner(FileManager.class.getResourceAsStream("/com/hacktheborder/text.txt"))) {
            StringBuilder sb = new StringBuilder();
            while(scan.hasNextLine()) {
                sb.append(scan.nextLine()).append("\n");
            }
            System.out.println("could read file");
            return sb.toString().replace("\\", "\\\\")  // Escape backslashes
            .replace("'", "\\'")    // Escape single quotes (if necessary)
            .replace("\n", "\\n")    // Escape newline characters
            .replace("\r", "");      // Remove carriage returns (optional);
        } catch (Exception e) {
            System.out.println("could not read file");
            return null;
        }
    }
}
