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

        for(int i = 0; i < 2; i++) {
            questions.add(readQuestion(scan));
            System.out.println(questions.get(i));
        }

        

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("secure-coding\\src\\main\\resources\\com\\hacktheborder\\Question.ser")))) {
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

            String line;
            String[] arr = new String[4];
     

            while(!(line = scan.nextLine()).equals("STOP")) {
             
                //System.out.println("line: " + line);

                if(line.startsWith("QUE:")) {
                    //System.out.println("At questionType");
                    arr[0] = line.substring(5);
                 


                } else if(line.startsWith("EXP:")) {
                   // System.out.println("At ExpectedOutput");
                    arr[1] = line.substring(5);


                } else if(line.startsWith("NON: ")) {
                    //System.out.println("At editable");
                    arr[3] = line.substring(5);


                } else {
                
                    code.append(line).append("\n");

                }
            }

            arr[2] = code.toString();

            return new Question(arr[0], arr[1], arr[2], arr[3]);
        } catch (Exception e) {
            return null;
        }
    }
}
