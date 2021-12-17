/*
Jonathan, Simon
Nickel Tracker: Tracker of Nickel
Nov. 15, 2021 - Jan. 26, 2021 part 2
*/

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
      System.out.println(i.name +", "+ i.nickel);
    }

    System.out.print("Welcome to Nickel Tracker\nDo you know how much nickel (in micrograms) you can have in a day before feeling sick? (0 if not) \n");
    double userThresh = getPosDouble(temp);

    if(userThresh == 0){
      userThresh = 150.0; //official
    }
     


    //main menu
    //init meals
    Breakfast brek = new Breakfast();
    Lunch lunc = new Lunch();
    Dinner din = new Dinner();
    ArrayList<Snack> snax = new ArrayList<Snack>();

    String decide;
    do{
      System.out.println("What meal you eat?\nB: Breakfast\nL: Lunch\nD: Dinner\nS: Snack\nE: Finished");
      decide = temp.nextLine();

      switch(decide){
        case("B"): //breakfast
          brek.addFood(foodDB);
          break;
        case("L"): //lunch
          lunc.addFood(foodDB);
          break;
        case("D"): //dinner
          din.addFood(foodDB);
          break;
        case("S"): //new snack, add to arraylist
          Snack snak = new Snack(validTime(temp));
          snak.addFood(foodDB);
          snax.add(snak);
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
    //parse snax, add
    for(Snack i: snax){
      allMeals.add(i);
    }

    Meal[] tempMeal = allMeals.toArray(new Meal[0]); //let it be sorted

    Arrays.sort(tempMeal, new Comparator<Meal>(){
      @Override //sort based on time
      public int compare(Meal m1, Meal m2){
        return m1.time.compareTo(m2.time);
      }
    });

    //set back to ArrayList
    allMeals = new ArrayList<Meal>(Arrays.asList(tempMeal));
    
    //output foods, tally nickel
    double allNickel = 0.0;

    for(Meal i: allMeals){
      System.out.println(i.time + ": " + i.title); //debug
        for(Food x: i.mealFoods){
          System.out.print(x.name + " "); 
        }
      System.out.print("\n");
      allNickel += i.tallyNickel(); //the tally
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

    temp.close();
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
  @param allFood - complete arraylist of meals for analysis
  @param threshhold - double, if over thresh, check what user could do
  @param fullTally - double, allFood.tallyNickel
  @return help - string of advice for next day
  */
  private static String advice(ArrayList<Meal> allFood, double threshhold, double fullTally){
    
    //init vars
    ArrayList<Food> foodList = new ArrayList<Food>();
    Meal mostNickel = new Meal("","0");
    Food modeFood;
    int snackCount = 0;


    for(Meal i: allFood){

      for(Food x:i.mealFoods){
        foodList.add(x); //make full food ArrayList from each meal
      }
      if(i.tallyNickel() > mostNickel.tallyNickel()){
        mostNickel = i; //find max
      }

      if(i.title.equals("Snacks")){
        snackCount++; //check if too many snacks
      }

    }

    if (fullTally <= threshhold){
      return "Everything's under control. Nice!";

    }else{//if over thresh

      if(mostNickel.title.equals("Snack") || snackCount > 3){//if largest meal was snack or lots of snacks
        return "Eat less nickel during snacks";

      }else if(foodMode(foodList).nickel > threshhold/10){//if most common food large
        return "Eat less " + foodMode(foodList).name;

      }else if(mostNickel.tallyNickel() > threshhold/2){//if largest meal big
        modeFood = foodMode(mostNickel.mealFoods);
        return "Eat less nickel during " + mostNickel.title + ", particularly " + modeFood.name + ".";

      }else{//replace?
        return "There's nothing obvious that stands out, consider reducing overall dood intake";
      }

    }
  }
  

  /*
  calculates most common food in arraylist
  @param theFoods - arraylist of foods, items will be counted
  @return most common Food times # servings
  */
  private static Food foodMode(ArrayList<Food> theFoods) {

    Food mode = new Food("",0);
    int maxCount = 0;
    int x;

    //loop thru array, check each mode
    for(x = 0; x < theFoods.size(); ++x) {
        int count = 0; //reset

        for (int y = 0; y < theFoods.size(); ++y) {
          if (theFoods.get(y).name.equals(theFoods.get(x).name)){ //if same food
          ++count; 
          }
        }

        if (count > maxCount) {
          maxCount = count; //save value if it mode so far
          mode = theFoods.get(x);
        }
    }
    return new Food(mode.name,mode.nickel * maxCount); //mode w/ serving size times nickel
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


  /*
  bulletproofing of time string for Snack constructor
  SHOULD USE GUI LATER - maybe pop-up window?
  @param keeb - scanner, is not modified
  @return valid time string - HH:MM, user input, 24-hr time
  */
  private static String validTime(Scanner keeb){
    
    while(true){
      try{
        System.out.println("Input time, 00:00-23:59:"); //input time
        String time = keeb.nextLine();
        
        if(time.length() != 5){//prevent index OOB except
          System.out.println("input is too long or short");
          continue;//retry
        }else if(time.charAt(2)!= ':'){
          System.out.println("3rd character must be a \':\'");
          continue;
        }else if(Integer.parseInt(time.substring(0,2)) < 0 || Integer.parseInt(time.substring(0,2)) > 23){
          System.out.println("invalid hours");
          continue;
        }else if(Integer.parseInt(time.substring(3,5)) < 0 || Integer.parseInt(time.substring(3,5)) > 59){
          System.out.println("invalid minutes");
          continue;
        }else if(time.charAt(0) == '-' || time.charAt(3) == '-'){
          System.out.println("Cannot be -0");
          continue;
        }


        return time; //basically else

      }catch(NumberFormatException numberFormatException){
        System.out.println("HH:MM, HH is integer between 00-23, MM between 00-59");
        continue;
      //}catch(IndexOutOfBoundsException oob){
      //  System.out.println("oob");
      }
    }

  }
}