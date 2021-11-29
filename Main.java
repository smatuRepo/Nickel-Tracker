import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Main{


  public static void main(String[] args) {

    JFrame main = new JFrame("[app name]");
    double userThresh; //TBD, what should default be

    //read current file contents
    //create place to store Food objects
    ArrayList<Food> foodDB = readDB(new ArrayList<Food>());
    for(Food i: foodDB){
      System.out.println(i.name + i.nickel);
    }

    //create main jframe, make starting frame
    //JFrame main = new JFrame("Intro");
    //jframe attriutes
    main.setSize(500,300);
    main.setVisible(true);
    main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    userThresh = new Intro(main).getThresh();
    
  }


  //HELPER METHODS


  /*
  add previous file contents to ArrayList (READ file)
  @param allFood - arraylist of foods, to be modified
  @return arraylist<food> - modified allFood
  */
  private static ArrayList<Food> readDB(ArrayList<Food> allFood){

    
    try {
      //connect to file, buffer
      FileReader reader = new FileReader("database.txt");
      BufferedReader rBuffer = new BufferedReader(reader);

      String newLine1; //foodname
      double newLine2; //nickel content

      do{
        //System.out.println(newLine); debug
        newLine1 = rBuffer.readLine(); //read name (1st line)
        newLine2 = Double.parseDouble(rBuffer.readLine()); //read 2nd line

        if(newLine1 != null)allFood.add(new Food(newLine1,newLine2)); //add to foodlist if not EoF
      }while (newLine1 != null);
      
      reader.close();
      rBuffer.close();
    }catch(Exception ex){
      //System.out.println(ex.getMessage());
    }//end READ

    return allFood;
  }
  /*
  Asks for (+) int input, gets input
  @param keyboard - Scanner, will not be closed
  @return input - int input (+)
  */
  private static int getPosInt(Scanner keyboard){

    System.out.print("Enter a positive integer: ");
    int input;
    do{
      try{
        input = Integer.parseInt(keyboard.nextLine());        
        if(input >= 0){ //if positive, end
          return input;
        }else{
          System.out.print("Value must be positive, retry: ");
        }
      }catch(NumberFormatException ex){
        System.out.print("Invalid input, retry: ");
        continue; //retry
      }
    }while(true); //does not exit until valid input reached
  }


  /*
  Asks for (+) decimal input, gets input
  @param keyboard - Scanner, will not be closed
  @return input - double input within a certain range of values
  */
  private static double getPosDouble(Scanner keyboard){

    System.out.print("Enter a positive decimal value: ");
    double input;
    do{
      try{
        input = Double.parseDouble(keyboard.nextLine());        
        if(input >= 0){ //if positive, end
          return input;
        }else{
          System.out.print("Value must be positive, retry: ");
        }
      }catch(NumberFormatException ex){
        System.out.print("Invalid input, retry: ");
        continue; //retry
      }
    }while(true); //does not exit until valid input reached
  }

}