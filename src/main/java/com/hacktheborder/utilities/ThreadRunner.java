package com.hacktheborder.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadRunner extends Thread {
    private ProcessBuilder processBuilder;
    private Process process;
    private String output;

    public ThreadRunner(String file) {
        try {
            ProcessBuilder processBuilder2 = new ProcessBuilder("javac", file);
            processBuilder2.redirectErrorStream(true);
            processBuilder2.directory(new File(file));
    
            Process process2 = processBuilder2.start();
            process2.waitFor();

            process2.getInputStream().close();
            process2.getErrorStream().close();
            process2.getOutputStream().close();

      
            processBuilder = new ProcessBuilder("java", "-cp", file, "Test.java");
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new File(file));
        } catch (Exception e) {

        }
    }


    public String getOutput() {

        return output;
    }


    @Override
    public void run() {
        try {
            process = processBuilder.start();
   
            if (!process.waitFor(2, java.util.concurrent.TimeUnit.SECONDS)) {
                // Timeout exceeded: terminate the process and interrupt this thread
                process.destroy();
                this.interrupt();
                System.out.println("Process took too long and was terminated.\n");
            } else {

            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder sb = new StringBuilder();
            
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");     
            }

            
            output = sb.toString();


            
            
        } catch (Exception e) {

        } finally {
            // Ensure the process is terminated and resources are freed
            if (process != null && process.isAlive()) {
                process.destroy();
            }

            // Close any lingering streams to prevent resource leaks
            try {
                
                process.getInputStream().close();
                process.getErrorStream().close();
                process.getOutputStream().close();
            } catch (IOException e) {
                System.err.println("Failed to close streams: " + e.getMessage());
            }
  
        }
    }
}