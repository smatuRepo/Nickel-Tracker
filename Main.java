/*
 * Ver. 2.0.1
 * Jonathan, Simon
 * Nickel Tracker: Tracker of Nickel
 * Nov. 15, 2021 - Jan. 26, (2021, part II: Electric Boogaloo)
*/

import java.util.*;
import java.io.*;

class Main{

  public static void main(String[] args) {
    
    String decision; //init vars that do not reset
    boolean exit = false;
    int logNum = Integer.parseInt(readNthLine("numDays.txt",1)); //is updated throughout loop
    
    

    //read current file contents
    //create place to store all possible Food objects
    ArrayList<Food> foodDB = readDB();
    //for(Food i: foodDB){
      //System.out.println(i.name +", "+ i.nickel);
    //}

    Menu screen = new Menu(); //the only jframe
    
    do{ //big loop

      //variables to reset
      //for saving and loading logs
      ArrayList<Food> dayFood = new ArrayList<Food>(); 
      double userThresh = -1;
      
      screen.intro();
      
      //get button input
      decision = screen.buttonWait();
      screen.reset();

      if(decision.equals("w")){//make NEW log

        screen.threshGet(); 
        //ask for userThresh,wait
        decision = screen.buttonWait();

        switch(decision){//either skip input or give nickel threshold
    
          case("c"): //user input thresh
            userThresh = -1;
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

          case("def"): //skip thresh
            userThresh = 150.0;
          default:
            userThresh = 150.0;
            System.out.println("skip " + decision);
        }//end switch/case
        

        //MAIN MENU
        //init meals
        Meal breakfast = new Meal("Breakfast", "07:30");
        Meal lunch = new Meal("Lunch", "12:30");
        Meal dinner = new Meal("Dinner", "18:00");
        //variable amount of snacks
        ArrayList<Meal> snax = new ArrayList<Meal>();


        //String decide;
        screen.reset();
        screen.mealSelect(); //main menu

        do{
           
          decision = screen.buttonWait();

          switch(decision){
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
              //System.out.println("Not a meal");
          }//end switch/case

        }while(!(decision.equals("x")));

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
          @Override //sort based on TIME
          public int compare(Meal m1, Meal m2){
            return m1.time.compareTo(m2.time);
          }
        });

        //set back to ArrayList
        allMeals = new ArrayList<Meal>(Arrays.asList(tempMeal));
        
        //tally nickel, tally foods
        //for writing file 
        double allNickel = 0.0;

        for(Meal i: allMeals){
          for(Food x: i.mealFoods){
            dayFood.add(x); //add the food
          }
          allNickel += i.tallyNickel(); //add to tally
        }

        //what should user do based on given info
        String userAdv = advice(allMeals, userThresh, allNickel); 


        screen.outro(allMeals.size(),allMeals, allNickel, userAdv);

        
        
        //WRITE NEW FILE
        newLog(userAdv, userThresh, allNickel, dayFood, logNum++);

        
        
        }else{
        //READ FILE LOG, NEED NEW PANEL IN CLASS MENU - for decision

        //make new frame to output all available files
        screen.fileSelect(logNum);

        //try {
          decision = "0";
          while(Integer.parseInt(decision) < 1){
            //wait, get input 
            decision = screen.buttonWait();
            //break;
          }
        //}catch(NumberFormatException ex) {
          //continue; //make sure they don't press wrong button
        //}
        
        //what file to read
        String theFile = "day" + decision + ".txt";
        
        //System.out.println(theFile+" out of " + logNum + " files"); //debug

        //then read file contents, remember file format
        //date-advice-totnik-allfoods

        //read advice, save
        String advice = readNthLine(theFile,2);
        //read totnik, save
        double pastNik = Double.parseDouble(readNthLine(theFile,3));
        
        //read foods, start at line 4
        int line = 4;
        String foodName = "";//readNthLine(theFile, line++);
        double foodNickel = 0; //Double.parseDouble(readNthLine(theFile,line++));
        
        while(true){
          try {
            foodName = readNthLine(theFile, line++);
            foodNickel = Double.parseDouble(readNthLine(theFile,line++));
            dayFood.add(new Food(foodName,foodNickel));
          }catch(NullPointerException NPE){
            //System.out.println(line);
            break;
          }
        }
        

        //convert arrayList food to arrayList meal (size 1)
        //
        Meal theOnlyMeal = new Meal("All The Foods", "Today");
        theOnlyMeal.mealFoods = dayFood;
        ArrayList<Meal> oneMeal = new ArrayList<Meal>(1);
        oneMeal.add(theOnlyMeal);
        
        screen.reset();
        screen.outro(1,oneMeal, pastNik, advice);
        } //end large if



        //both final screens use outro(),
        //same buttons, so fine

        String getOut = screen.buttonWait(); //get button input

        if(getOut.charAt(0) == 'q'){//save&exit
          exit = true;
        //}else if(getOut.charAt(0) == 'b'){ //save&continue
          //exit = false;
        }//no else, exit changed nowhere else

        
        screen.reset();
        
      }while(!(exit));//loop back to intro(), reset vars - only exit when button pressed
    //OUT OF MAIN LOOP, CLOSING
    
    
    screen.dispose();
    //System.out.println("-EOF-");
    
    
    //UPDATE FILES
    try {
      //NUMDAYS.TXT
      FileWriter writer1 = new FileWriter("numDays.txt",false);
      BufferedWriter buffer1 = new BufferedWriter(writer1);
      
      buffer1.write("" + logNum);
      buffer1.newLine();
      
      buffer1.close(); //close
      writer1.close();
      
      //DATABASE.TXT
      FileWriter writer = new FileWriter("database.txt",false);
      BufferedWriter buffer = new BufferedWriter(writer);

      //write foods|nickel.. until none left
      for(Food i:foodDB){
        buffer.write(i.name);
        buffer.newLine();
        buffer.write(i.nickel + "");
        buffer.newLine();
      }
      
      buffer.close();
      writer.close(); 
      
    }catch(Exception ex){
      //System.out.println(ex.getMessage()+", ok?");
    }
    //System.out.println("----updated?----");
    //for(Food i: foodDB){
      //System.out.println(i.name +", "+ i.nickel);
    //}
    
  }//end main()




  //HELPER METHODS

  /*
   * reads line of text file at line N, returns it
   * @param theFileName - string, should be .txt
   * @return String - nth line of file (returns null if file DNE)
   */
  public static String readNthLine(String theFileName, int line){
    String nthLine = "";
    try {
      //connect to file
      FileReader reader = new FileReader(theFileName);
      BufferedReader rBuffer = new BufferedReader(reader);

      //to read Nth line, skip others
      for(int x = 0; x < line-1;x++) {
        rBuffer.readLine(); //don't need to save to var, faster to skip
      }

      nthLine = rBuffer.readLine(); //read only the one line
      //System.out.println("line " + line + ": " + nthLine); //debug

      reader.close();
      rBuffer.close();

    }catch(Exception ex){
      //System.out.println(ex.getMessage());
    }
    return nthLine; //string or null
  }


  /*
   * writes new food log file, day[x].txt
   * pRoCeDuRaLlY gEnErAtEd
   * Format:
   * [date]
   * [advice]
   * [total nickel]
   * [food1 name]
   * [food1 nickel]
   * etc.
   * @param userAdvice - string given from advice()
   * @param thresh - double, user nickel thresh
   * @param totNik - double, total nickel consumed
   * @param eachFood - list of foods eaten in the day
   * void
   */
  private static void newLog(String userAdvice, double thresh, double totNik, ArrayList<Food> eachFood, int fileNum){
    
    try {
      
      //format: date-advice-totnik-userthresh-allfoods
      //File name
      String logName = "day" + (fileNum+1) + ".txt"; //the NEXT file
      
      //connect/generate file
      FileWriter writer = new FileWriter(logName);
      BufferedWriter buffer = new BufferedWriter(writer);

      //find date
      String today = java.time.LocalDate.now() + "";
      //System.out.println(today+" "+ logName + fileNum); //debug
      
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
      
      buffer.close();
      writer.close(); 

    }catch(Exception ex){
      //System.out.println("error: "+ex.getMessage());
    }//end write
  }


  /*
   * add previous file contents to ArrayList 
   * (READ database.txt)
   * @return arraylist<food> - all from database.txt
   */
  private static ArrayList<Food> readDB(){

    ArrayList<Food> allFood = new ArrayList<Food>();
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
   * gives advice String based on what foods eaten
   * @param allFood - complete arraylist of meals for analysis
   * @param threshhold - double, if over thresh, check what user could do better
   * @param fullTally - double, allFood.tallyNickel
   * @return help - string of advice from given data
   */
  private static String advice(ArrayList<Meal> allFood, double threshhold, double fullTally){
    
    //init vars
    ArrayList<Food> foodList = new ArrayList<Food>();
    Meal mostNickel = new Meal("","0");
    Food modeFood;
    int snackCount = 0;


    for(Meal i: allFood){

      //make full food ArrayList from each meal
      for(Food x:i.mealFoods){
        foodList.add(x); 
      }

      //find max-highest nickel meal
      if(i.tallyNickel() > mostNickel.tallyNickel()){
        mostNickel = i; 
      }

      if(i.title.equals("Snacks")){
        snackCount++; //check if too many snacks
      }

    }

    //under thresh
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
  }//end advice()
  

  /*
   * calculates most common food in arraylist
   * @param theFoods - arraylist of foods, items will be counted
   * @return most common Food - name, nickel * servings
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
   * checks if input is positive decimal, returns debug lines
   * @param input - string, to check if double
   * @return string - "ok"/not num/not pos (debug lines)
   */
  public static String checkPosDouble(String input){

    try{      
      if(Double.parseDouble(input) >= 0){ //if positive, end
        return "ok";
      }else{
        return "Must be more than 0";
	    }
    }catch(NumberFormatException ex){
      return "Not an number, retry"; 
    }
  }//end checkposdouble()
    


  /*
   * bulletproofing of time string for Snack constructor
   * checks if input is correct format
   * @param time - String, user input? - to check if in valid format
   * @return debug string -"ok" means valid - HH:MM, 24-hr time
   */
  private static String validTime(String time){
    
    try{
      
      if(time.length() != 5){//prevent index OOB except
        return "Input is too long or short";

      }else if(time.charAt(2)!= ':'){
        return "3rd character must be a ':'";

      }else if(Integer.parseInt(time.substring(0,2)) < 0 || Integer.parseInt(time.substring(0,2)) > 23){
        return "Invalid hours (00-23)";

      }else if(Integer.parseInt(time.substring(3,5)) < 0 || Integer.parseInt(time.substring(3,5)) > 59){
        return "Invalid minutes (00-59)";

      }else if(time.charAt(0) == '-' || time.charAt(3) == '-'){
        return "Cannot be '-0'";
      }

      return "ok"; //basically else, is valid

    }catch(NumberFormatException numberFormatException){
      return "HH:MM, HH is integer from 00-23, MM from 00-59";
    }//end try..catch

  }//end validTime()

}//--EOF--