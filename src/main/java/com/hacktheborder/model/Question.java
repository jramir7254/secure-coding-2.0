package com.hacktheborder.model;

import java.io.Serializable;

public class Question implements Serializable {
    private String JAVA_CODE;
    private final String EXPECTED_OUTPUT;
    private final String QUESTION_TYPE;
    private final String NON_EDITABLE_LINES;

    public Question(String questionType, String expectedOutput, String javaCode, String nonEditableLines) {
        this.JAVA_CODE = javaCode;
        this.EXPECTED_OUTPUT = expectedOutput;
        this.QUESTION_TYPE = questionType;
        this.NON_EDITABLE_LINES= nonEditableLines;
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

    public String toString() {
        return "Question Type: " + QUESTION_TYPE +
               "\nExpected Output: " + EXPECTED_OUTPUT + 
               "\nNon-Editable Lines: " + NON_EDITABLE_LINES + 
               "\nJava Code:\n" + JAVA_CODE;
    }
}
