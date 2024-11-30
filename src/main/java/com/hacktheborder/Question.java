package com.hacktheborder;

import java.io.Serializable;

public class Question implements Serializable {
    private String JAVA_CODE;
    private final String EDITABLE_SECTION;
    private final String EXPECTED_OUTPUT;
    private final String QUESTION_TYPE;
    private final String JS_SCRIPT;

    public Question(String questionType, String expectedOutput, String javaCode, String editableSection) {
        this.JAVA_CODE = javaCode;
        this.EXPECTED_OUTPUT = expectedOutput;
        this.EDITABLE_SECTION = editableSection;
        this.QUESTION_TYPE = questionType;
        this.JS_SCRIPT = "line";
    }

    public String getJavaCode() {
        return this.JAVA_CODE;
    }

    public String getQuestionType() {
        return this.QUESTION_TYPE;
    }

    public String getEditableSection() {
        return this.EDITABLE_SECTION;
    }
}
