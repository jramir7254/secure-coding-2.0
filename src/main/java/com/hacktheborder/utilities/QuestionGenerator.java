package com.hacktheborder.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.hacktheborder.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class QuestionGenerator {

    public static void main(String[] args) throws FileNotFoundException {
        m();
    }

    public static void m() throws FileNotFoundException {
        File file = new File("secure-coding\\src\\main\\resources\\com\\hacktheborder\\Quest.txt");
        Scanner scan = new Scanner(file);

        ArrayList<Question> questions = new ArrayList<>();

        for(int i = 0; i < 1; i++) {
            questions.add(readQuestion(scan));
        }

        System.out.println(questions.get(0).getJavaCode());

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("secure-coding\\src\\main\\resources\\Question.ser")))) {
            for(Question q : questions) {
                    oos.writeObject(q);
            }
            System.out.println("done");
        } catch (Exception e) {
            System.out.println("Problems serializing object");
            e.printStackTrace();
        }
      
        scan.close();
    }

    public static Question readQuestion(Scanner scan) {
        try {
            StringBuilder code = new StringBuilder();
            StringBuilder edit = new StringBuilder();
            String line;
            String[] arr = new String[4];
            boolean editable = false;

            while(!(line = scan.nextLine()).equals("STOP")) {
             
                //System.out.println("line: " + line);

                if(line.startsWith("QUESTION_TYPE:")) {
                    //System.out.println("At questionType");
                    arr[0] = line.substring(15);
                 


                } else if(line.startsWith("EXPECTED_OUTPUT:")) {
                   // System.out.println("At ExpectedOutput");
                    arr[1] = line.substring(17);


                } else if(line.startsWith("EDITABLE")) {
                    //System.out.println("At editable");
                    editable = !editable;


                } else if(line.startsWith("END_EDITABLE")) {
                    //System.out.println("At end editable");
                    editable = !editable;


                } else {
                    //System.out.println("At other");
                    code.append(line).append("\n");
                    if(editable) {
                        edit.append(line).append("\n");
                    }
                    //System.out.println("end loop");
                }
                //System.out.println("end loop");
            }

            arr[2] = code.toString();
            arr[3] = edit.toString();

            return new Question(arr[0], arr[1], arr[2], arr[3]);
        } catch (Exception e) {
            return null;
        }
    }
}
