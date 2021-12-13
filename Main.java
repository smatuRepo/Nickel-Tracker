import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

class Main{


  public static void main(String[] args) {
    
    Scanner temp = new Scanner(System.in);
    //JFrame main = new JFrame("[app name]");

    //read current file contents
    //create place to store Food objects
    ArrayList<Food> foodDB = readDB(new ArrayList<Food>());
    for(Food i: foodDB){
      System.out.println(i.name + i.nickel);
    }

    System.out.print("Welcome to Nickel Tracker\nDo you know how much nickel you can have in a day before feeling sick? (-1 if not): ");
    double userThresh = getPosDouble(temp);


    //main menu
    //init meals
    Breakfast brek = new Breakfast();
    Lunch lunc = new Lunch();
    Dinner din = new Dinner();
    ArrayList<Snack> snaks = new ArrayList<Snack>();

    String decide;
    do{
      System.out.println("What meal you eat?\nB: Breakfast\nL: Lunch\nD: Dinner\nS: Snack\nE: Finished");
      decide = temp.nextLine();

      switch(decide){
        case("B"): //breakfast
          brek.addFood();
          break;
        case("L"): //lunch
          lunc.addFood();
          break;
        case("D"): //dinner
          din.addFood();
          break;
        case("S"): //new snack, add to arraylist
          Snack snak = new Snack();
          snak.addFood();
          snaks.add(snak);
          break;
        default:
          System.out.println("Not a meal");
      }
    }while(!(decide.equals("E")));

    //put meals together, add snacks too.
    ArrayList<Meal> allMeals = new ArrayList<Meal>();
    allMeals.add(brek);
    allMeals.add(lunc);
    allMeals.add(din);
    //parse snaks, add
    for(Snack i: snaks){
      allMeals.add(i);
    }
    allMeals.sort(); //sort by time

    //output foods, tally nickel
    double allNickel = 0.0;

    for(Meal i: allMeals){
      System.out.print(i.time + ": " + i.title + "\n\t");

        for(Food x: i.allFoods){
          System.out.print(x + " "); 
        }
      allNickel += i.tallyNickel();
    }

    System.out.println("You ate " + allNickel + " micrograms of nickel.");

    System.out.println(advice(allMeals, userThresh, allNickel));
    
    /*removed for NOW, gui too hard
    //create main jframe, make starting frame
    //JFrame main = new JFrame("Intro");
    //jframe attriutes
    main.setSize(500,300);
    main.setVisible(true);
    main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    main.setLayout(new FlowLayout());

    Intro introScreen = new Intro(main);
    
    System.out.println(userThresh);
    */
    
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
  gives advice based on what foods eaten
  @param foodList - complete arraylist of meals for analysis
  @return help - string of advice for next day
  */
  private static String advice(ArrayList<Meal> allFood, double threshhold, double fullTally){
    
    //ArrayList<double> mealNickels = new ArrayList<double>();

    for(Meal i: allFood){
      mealNickels.add(i.tallyNickel());
    }

    if (fullTally <= threshhold){
      return "Great!";
    }else{




    }
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