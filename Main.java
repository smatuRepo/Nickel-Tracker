import java.util.*;
import javax.swing.*;

class Main {


  public static void main(String[] args) {
    
    JFrame main




  }









  //HELPER METHODS

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