/**
 * Main class that contains the only main method.
 * Start main method and paste in the console the full path to your text file.
 * You should include .txt extension in the end of it.
 *
 * @author Vladimir Vladimirov
 */

package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("\n--------- Find employees that had worked the most time on common projects ---------\n");

        while(true){

            System.out.println("\nEnter the full path to your text file or type EXIT" +
                               "\nexample: (C:\\directory\\file.txt)\n");

            Scanner sc = new Scanner(System.in);
            String path = sc.nextLine().toLowerCase();

            if(path.equals("exit")){
                break;
            }

            try {
                System.out.println(EmployeesAnalyzer.printEmployeesWorkedMostTogether(path));
            }catch (FileAnalyzerException e){
                e.printStackTrace();
            }
        }
    }
}
