/*
Jonathan, Simon
Nickel Tracker: Tracker of Nickel
Nov. 15, 2021 - Jan. 26, 2021 part 2
*/

import java.util.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter; 
//import javax.swing.*;
//import java.awt.*;
import java.io.*;

class Main{

  public static void main(String[] args) {
    
    //Scanner temp = new Scanner(System.in);
    String decision;
    double userThresh = -1;
    boolean exit = false;
    ArrayList<Food> dayFood = new ArrayList<Food>(); //for saving and loading logs

    //read current file contents
    //create place to store Food objects
    ArrayList<Food> foodDB = readDB(new ArrayList<Food>());
    for(Food i: foodDB){
      System.out.println(i.name +", "+ i.nickel);
    }

    Menu screen = new Menu();
    
    
    do{ //big loop

      screen.intro();

      decision = screen.buttonWait();
      screen.reset();

      if(decision.equals("w")){//make new log

        screen.threshGet(); 

        decision = screen.buttonWait();
        switch(decision){//either skip input or give nickel threshold
    
          case("c"):
            
            String userHelp = "Enter how much of nickel is your limit (mcg)"; //user directions
            do{
              String tempDbl = screen.textInput(userHelp); //get string input
              String valid = checkPosDouble(tempDbl); //check if valid

              if(valid.equals("ok")){
                userThresh = Double.parseDouble(tempDbl);   
              }else{
                userHelp = valid; //to help user
              }
            }while(userThresh < 0);
            break; //done

          case("def"):
            userThresh = 150.0;
          default:
            System.out.println("skip " + decision);
        }//end switch/case
        

        //main menu
        //init meals
        Meal breakfast = new Meal("Breakfast", "07:30");
        Meal lunch = new Meal("Lunch", "12:30");
        Meal dinner = new Meal("Dinner", "18:00");
        ArrayList<Meal> snax = new ArrayList<Meal>();

        String decide;
        screen.reset();
        screen.mealSelect();
        do{
           
          decide = screen.buttonWait();

          switch(decide){
            case("b"): //breakfast
              breakfast.addFood(screen, foodDB);
              break;
            case("l"): //lunch
              lunch.addFood(screen, foodDB);
              break;
            case("d"): //dinner
              dinner.addFood(screen, foodDB);
              break;
            case("s"): //new snack, add to arraylist

              String time;
              String userHelp = "Enter a time in of HH:MM, 24-hour time";
              
              do{
                time = screen.textInput(userHelp);
                userHelp = validTime(time);

              }while(!(userHelp.equals("ok")));

              
              Meal snak = new Meal("Snack", time);
              snak.addFood(screen, foodDB);
              snax.add(snak);
              break;

            default:
              System.out.println("Not a meal");
          }//end switch/case

        }while(!(decide.equals("x")));
        screen.reset(); //now to outro()

        //put meals together, add snacks too.
        ArrayList<Meal> allMeals = new ArrayList<Meal>();
        allMeals.add(breakfast);
        allMeals.add(lunch);
        allMeals.add(dinner);
        //parse snax, add
        for(Meal i: snax){
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
        
        //tally nickel, tally foods
        //for writing file 
        double allNickel = 0.0;
        

        //for writing file 

        for(Meal i: allMeals){
          for(Food x: i.mealFoods){
            dayFood.add(x); //the food
          }
          allNickel += i.tallyNickel(); //the tally
        }

        String userAdv = advice(allMeals, userThresh, allNickel); 

        screen.outro(allMeals.size(),allMeals, allNickel, userAdv);

        
        
        //WRITE NEW FILE
        newLog(userAdv, userThresh, allNickel, dayFood);
        

        }else{
        //READ FILE LOG, NEED NEW PANEL IN CLASS MENU - for decision

        int logNum = Integer.parseInt(readFirstLine("numDays.txt"));

        //so Menu should create x amnt of buttons given logNum, assign, 
        //read dates, give buttons proper titles


        decision = screen.buttonWait();

        String theFile = "day" + decision + ".txt";

        //then read file contents
        //remember: read date-advice-totnik-allfoods

        //own method?

        //read date, discard?
        //read advice, save
        //read totnik, save

        //read foods:
        //while string!=null, x=4, x++
        //read food name
        //read food nickel.parseDouble()
        //init Food object
        //dayFood.add([food]);

        //how to convert arraylist food to arraylist meal?
        //

        //call screen.outro(1,new ArrayList<Meal>(theonlymeal), totalNickel, advice)





        screen.reset();

        //screen.outro(???);

        //DO OUTRO(), BUT ONLY 1 MEAL
        //
        }



        //both final screens use outro(),
        //same buttons, so fine

        String getOut = screen.buttonWait(); //get button input

        if(getOut.charAt(0) == 'q'){//save&exit
          exit = true;
        }else if(getOut.charAt(0) == 'b'){ //save&continue
          exit = false;
        }//no else

        
        screen.reset();
        
      }while(!(exit));//loop back to intro(), reset vars - only exit when button pressed
    
    screen.dispose();
    System.out.println("-EOF-");
    
  }//end main()




  //HELPER METHODS

  /*
  reads first line of text file, returns it
  @param theFileName - string, should be .txt
  @return String - first line of file
  */
  private static String readFirstLine(String theFileName){
    String stLine = "";
    try {
      //connect to file, buffer
      FileReader reader = new FileReader(theFileName);
      BufferedReader rBuffer = new BufferedReader(reader);

      //for i in x-1; readline, discard
      //to read Nth line

      //do{
        //System.out.println(newLine); debug
        stLine = rBuffer.readLine(); //read only the 1st line
      //}while (fileNum != null);
      System.out.println("1st line: " + stLine); //debug

      reader.close();
      rBuffer.close();

    }catch(Exception ex){
      System.out.println(ex.getMessage());
    }
    return stLine;
  }


  /*
  write new food log file
  updates numDays.txt
  @param userAdvice - string given from advice()
  @param thresh - double, user nickel thresh
  @param totNik - double, total nickel consumed
  @param eachFood - list of foods eaten in the day
  void
  */
  private static void newLog(String userAdvice, double thresh, double totNik, ArrayList<Food> eachFood){
    //READ INT TO MAKE FILE NAME
    int fileNum = Integer.parseInt(readFirstLine("numDays.txt"));
    
    try {
      
      //format: date-advice-totnik-userthresh-allfoods

      //WRITE
      //File name
      String logName = "day" + fileNum + ".txt";
      //connect to file, buffer
      FileWriter writer = new FileWriter(logName);
      BufferedWriter buffer = new BufferedWriter(writer);

      //find date
      String today = java.time.LocalDate.now() + "";

      //write date
      buffer.write(today);
      buffer.newLine();

      //write advice (userAdv)
      buffer.write(userAdvice);
      buffer.newLine();

      //write total nickel
      buffer.write(totNik+"");
      buffer.newLine();

      //write foods|nickel.. until none left
      for(Food i:eachFood){
        buffer.write(i.name);
        buffer.newLine();
        buffer.write(i.nickel + "");
        buffer.newLine();
      }
      
      
      //rewrite to numDays, save num of files

      writer.close(); //reset
      writer = new FileWriter("numDays.txt");
      //buffer = new BufferedWriter(writer);

      buffer.write(++fileNum + "");

      buffer.close(); //close
      writer.close();

    }catch(Exception ex){
      System.out.println(ex.getMessage());
    }//end write
  }


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
  @return help - string of advice from given data
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
  @param input - string, to chekc if double
  @return string - "ok"/not num/not pos (debug lines)
  */
  public static String checkPosDouble(String input){
    try{
	  //input = Integer.parseInt(keyboard.nextLine());        
	  if(Double.parseDouble(input) >= 0){ //if positive, end
	    return "ok";
	  }else{
	    //System.out.print("Value must be positive, retry: ");
	    return "Must be more than 0";
	    }
    }catch(NumberFormatException ex){
      //System.out.print("Invalid input, retry: ");
      return "Not an number, retry"; //retry
    }
  }//end checkposdouble()
    


  /*
  bulletproofing of time string for Snack constructor
  //SHOULD USE GUI LATER - maybe pop-up window?
  @param time - String, to check if in valid format
  @return debug string -"ok" means valid - HH:MM, 24-hr time
  */
  private static String validTime(String time){
    
    //while(true){
      try{
        //System.out.println("Input time, 00:00-23:59:"); //input time
        //String time = keeb.nextLine();
        
        if(time.length() != 5){//prevent index OOB except
          return "input is too long or short";
          //continue;//retry
        }else if(time.charAt(2)!= ':'){
          return "3rd character must be a ':'";
          //continue;
        }else if(Integer.parseInt(time.substring(0,2)) < 0 || Integer.parseInt(time.substring(0,2)) > 23){
          return "invalid hours";
          //continue;
        }else if(Integer.parseInt(time.substring(3,5)) < 0 || Integer.parseInt(time.substring(3,5)) > 59){
          return "invalid minutes";
          //continue;
        }else if(time.charAt(0) == '-' || time.charAt(3) == '-'){
          return "Cannot be -0";
          //continue;
        }


        return "ok"; //basically else, is valid

      }catch(NumberFormatException numberFormatException){
        return "HH:MM, HH is integer between 00-23, MM between 00-59";
        //continue;
      //}catch(IndexOutOfBoundsException oob){
      //  System.out.println("oob");
      }
    //}

  }

}