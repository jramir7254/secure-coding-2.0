package com.hacktheborder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    private String JAVA_CODE;
    private final String EXPECTED_OUTPUT;
    private final String QUESTION_TYPE;
    private final String NON_EDITABLE_LINES;

    private int wrongAttemptsOnMultipleChoice;
    private int wrongAttemptsOnDebugging;
    private ArrayList<String> debuggingAttempts;

    public Question(String questionType, String expectedOutput, String javaCode, String nonEditableLines) {
        this.JAVA_CODE = javaCode;
        this.EXPECTED_OUTPUT = expectedOutput;
        this.QUESTION_TYPE = questionType;
        this.NON_EDITABLE_LINES= nonEditableLines;

        this.wrongAttemptsOnMultipleChoice = 0;
        this.wrongAttemptsOnDebugging = 0;
        this.debuggingAttempts = new ArrayList<>(1);
    }

    public String getJavaCode() {
 
        return this.JAVA_CODE;
    }


    public String getQuestionType() {
        return this.QUESTION_TYPE;
    }

    public String getExpectedOutput() {
        return this.EXPECTED_OUTPUT;
    }

    public String getNonEditableLines() {
        return this.NON_EDITABLE_LINES;
    }

    public int getMultipleChoiceWrongAttempts() {
        return this.wrongAttemptsOnMultipleChoice;
    }

    public int getDebuggingWrongAttempts() {
        return this.wrongAttemptsOnDebugging;
    }

    public List<String> getDebuggingAttempts() {
        return this.debuggingAttempts;
    }

    public void updateMultipleChoiceWrongAttempts() {
        this.wrongAttemptsOnMultipleChoice++;
    }

    public void updateDebuggingWrongAttempts() {
        this.wrongAttemptsOnDebugging++;
    }

    public void addDebugingAttempt(String debuggingAttempt) {
        debuggingAttempts.add(debuggingAttempt);
    }

    public String toString() {
        String s = "\n\t{" + 
                        "\n\t   Question Type: %s" + 
                        "\n\t   Expected Output: %s" + 
                        "\n\t   Multiple Choice Wrong Attempts: %d" + 
                        "\n\t   Debugging Wrong Attempts: %d" + 
                        "\n\t   Question Java Code: {" +
                        "\n\t\t   %s" +
                        "\n\t   }" +
                        "\n\t   Debugging Attempts: [" +
                        "\n   %s" +
                        "\t   ]" +
                    "\n\t},";
        s = String.format(s, QUESTION_TYPE, EXPECTED_OUTPUT, wrongAttemptsOnMultipleChoice, wrongAttemptsOnDebugging, JAVA_CODE.trim().replace("\n", "\n\t\t   "), getDebuggingAttemptsToString());
        return s;
    }

    private String getDebuggingAttemptsToString() {
        StringBuilder sb = new StringBuilder();
        for(String s : debuggingAttempts) {
            sb.append("\t      {\n\t\t");
            sb.append(s.trim().replace("\n", "\n\t\t   "));
            sb.append("\n\t      },\n");
        }
        return sb.toString();
    }
}
