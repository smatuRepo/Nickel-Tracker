import java.util.*;
import java.io.*;


class Meal{
  //declare attributes

  Scanner keebs = new Scanner (System.in);

  String nameFood;
  int numFoods;  
  String yn;
  int foodinfo;
  String title ;
  String time;
  ArrayList<Food> allFoods = new ArrayList <Food>();

  //constructor
  public Meal(String title, String time)
  {
    this.title = title;
    this.time = time;
  }



  /*
  adds food to Meal's food arraylist, should implement GUI later
  no param, void. changes obj attributes 
  */
  public void addFood()
  {
    System.out.print ("Food?: ");
    nameFood = keebs.nextLine();


    //READ file, compare to input
    if(searchDB(nameFood)){
      System.out.print ("Number of servings?: ");
      numFoods = getPosInt(keebs);

      if (numFoods == 0)
      {

      }//checks the readline from the database code comparing the number of servings
      else {

      }

    }else{



    }


    
  }

  //tallynickel
  public double tallyNickel()
  {
    double count = 0.0;
    for (Food i: allFoods)
      count += i.nickel;
    return count;
  }


  //addtodb
  private void addToDB(String newName)
  {
    
    System.out.println ("Input Doesn't Compute");
    System.out.println ("Would you like to add it to the database");
    yn = keebs.nextLine();

     
    if (yn.equals ("Yes"))
    {
      System.out.print ("What is the nickel amount?: ");
      foodinfo = Integer.parseInt(keebs.nextLine());


      //write newName, foodInfo to file

    }

  }



  /*
  linear search of DB txt file, 
  check if string is == to title of food in file
  @param input - string, to be compared
  @return boolean - true if in file/DB
  */
  private boolean searchDB(String input){

    try {
      //connect to file, buffer - INIT
      FileReader reader = new FileReader("database.txt");
      BufferedReader rBuffer = new BufferedReader(reader);

      String newLine1; //foodname
      String newLine2; //nickel content - blank, to be skipped

      do{
        //System.out.println(newLine); debug
        newLine1 = rBuffer.readLine(); //read name (1st line)
        newLine2 = rBuffer.readLine(); //read 2nd line, blank

        //main bit, check
        if(newLine1 != null){
          if(input.equals(newLine1)){
              reader.close(); //end
              rBuffer.close();
              return true; //IS IN FILE
          }
        } //check if not EoF
      }while (newLine1 != null);
      
      
    }catch(Exception ex){
      //System.out.println(ex.getMessage());
    }//end READ

    //rBuffer.close();
    return false;
  }




  /*
  Asks for (+) int input, gets input
  @param keyboard - Scanner, will not be closed
  @return input - int input (+)
  */
  public static int getPosInt(Scanner keyboard){

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

}