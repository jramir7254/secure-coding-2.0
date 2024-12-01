package com.hacktheborder.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ThreadRunner extends Thread {
    private ProcessBuilder runProcessBuilder;
    private Process runProcess;
    private String runProcessOutput;




    public ThreadRunner(String file) {
        try {

            compileJavaCode(file);

            runProcessBuilder = new ProcessBuilder("java", "-cp", file, "Main.java");
            runProcessBuilder.redirectErrorStream(true);
            runProcessBuilder.directory(new File(file));

        } catch (Exception e) {
            System.err.println("Exception message from ThreadRunner(String file) @ThreadRunner: " + e.getMessage());
        }
    }




    public String getRunProcessOutput() {
        return runProcessOutput;
    }



    private void compileJavaCode(String javaFile) {
        try {
            ProcessBuilder compileProcessBuilder = new ProcessBuilder("javac", javaFile);
            compileProcessBuilder.redirectErrorStream(true);
            compileProcessBuilder.directory(new File(javaFile));

            Process compileProcess = compileProcessBuilder.start();

            if(compileProcess.waitFor(2, java.util.concurrent.TimeUnit.SECONDS)) {
                compileProcess.destroy();
                System.out.println("Process took too long and was terminated.\n");
            }
        } catch (Exception e) {
            System.err.println("Exception message from CompileJavaCode(String javaFile) @ThreadRunner: " + e.getMessage());
        }
    }


    


    @Override
    public void run() {
        try {
            runProcess = runProcessBuilder.start();
   
            if (!runProcess.waitFor(2, java.util.concurrent.TimeUnit.SECONDS)) {
                runProcess.destroy();
                System.err.println("Process took too long and was terminated.\n");
            } 
            
            BufferedReader runProccessBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            StringBuilder runProcessOutputStringBuilder = new StringBuilder();
            

            String line;
            while ((line = runProccessBufferedReader.readLine()) != null) {
                runProcessOutputStringBuilder.append(line).append("\n");     
            }

            runProcessOutput = runProcessOutputStringBuilder.toString();
            
        } catch (Exception e) {
            System.err.println("Exception message from run() @ThreadRunner: " + e.getMessage());
        } 
    }
}

// finally {
//     // Ensure the process is terminated and resources are freed
//     if (runProcess != null && runProcess.isAlive()) {
//         runProcess.destroy();
//     }

//     // Close any lingering streams to prevent resource leaks
//     try {
        
//         process.getInputStream().close();
//         process.getErrorStream().close();
//         process.getOutputStream().close();
//     } catch (IOException e) {
//         System.err.println("Failed to close streams: " + e.getMessage());
//     }

// }