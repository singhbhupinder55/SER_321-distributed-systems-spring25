import java.io.*;
/**
 * Purpose: demonstrate simple Java Muliply class with command line,
 * jdb debugging, and Gradle build file.
 *
 * Ser321 Foundations of Distributed Applications
 * @author Aman Kaushik akaush13@asu.edu
 *         Software Engineering, CIDSE, ASU Poly
 * @version March 2020
  * @version August 2020 Alexandra Mehlhase changs for Gradle
 */
public class Multiply {
   public static void main (String args[]) {
	int num1 = 1; //Default value
	int num2 = 1; //Defauly value


    if (args.length == 2) {
        try {
          num1 = Integer.parseInt(args[0]);
          num2 = Integer.parseInt(args[1]);
        } catch (Exception e) {
          System.out.println("Arguments: " + args[0] + ", " + args[1] + " must be integers.");
          System.exit(1);
        }
      } else if (System.getProperty("num1") !=null && System.getProperty("num2") != null){
          num1 = Integer.parseInt(System.getProperty("num1"));
	  num2 = Integer.parseInt(System.getProperty("num2"));
      }
	System.out.println(num1 + " * " + num2 + " = " + num1 * num2);
    }

}
