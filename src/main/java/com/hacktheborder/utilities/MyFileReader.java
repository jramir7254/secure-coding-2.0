package com.hacktheborder.utilities;

import java.util.Scanner;
import java.io.File;

public class MyFileReader {
    public static String readFile() {
        try(Scanner scan = new Scanner(MyFileReader.class.getResourceAsStream("/com/hacktheborder/text.txt"))) {
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
