import java.util.*;

class Meal{

  //declare attributes
  int tempIndex; //for use b/n addfood, searchdb
  String title;
  String time;
  ArrayList<Food> mealFoods = new ArrayList <Food>();
  

  /*
   * constructor for Meal, initiallize name, time
   * @param title - name of meal
   * @param time - formatted HH:MM, 24-hr clock
   * void.
  */
  public Meal(String title, String time)
  {
    this.title = title;
    this.time = time;
  }


  /*
   * adds food to Meal's food arraylist, should implement GUI later
   * @param theDB - arraylist foods, database
   * void. changes obj attributes 
  */
  public void addFood(Menu test, ArrayList<Food> theDB)
  {	

    String nameFood = test.textInput(title).toLowerCase();

    //READ file, compare to input
    if(searchDB(nameFood,theDB)){//add to array
    
      int numFoods = -1;
      String help = "Number of servings?";
      
      do {
	      String serv = test.textInput(help); //gets input, should be int 
	      help = checkPosInt(serv);
	      
	      if(help.equals("ok")){
	        numFoods = Integer.parseInt(serv);
	      }//no need for else, numfoods not modded anywhere else
	      
      }while(numFoods < 0);

      //for each serving, add 1 of food to mealFoods
      for(int i = 0;i<numFoods;i++){
        mealFoods.add(theDB.get(tempIndex));
      }

    }else{//add to DB instead, if doesnt exist
      addToDB(nameFood, theDB, test);
    }
    
  }
  

  /*
   * Finds total Nickel in the arraylist of Foods (object attribute)
   * @return the count value, double
   */
  public double tallyNickel()
  {
    double count = 0.0;
    for (Food i: mealFoods)
      count += i.nickel;
    return count;
  }


  /*
   * The name of the food is added to the txt file database
   * Also adds the nickel amount to the database
   * @param newName - user input name, not in DB already, LOWERCASE
   * @param theDB - the database, arraylist foods
   * @return theDB, modified (if user says Yes) - 1 new food added
   */
  private ArrayList<Food> addToDB(String newName, ArrayList<Food> theDB, Menu text)
  {
    String yn = text.textInput("Would you like to add it to the database (y/n)");

    if (yn.toLowerCase().equals ("y"))
    {
      double foodInfo = -1;
      String help = "What is the nickel amount?";
      
      do {//loop until get double 
        String servSz = text.textInput(help); //gets input, should be double 
        //(print debug line on internal frame)
        help = Main.checkPosDouble(servSz); //returns debug line 
        
        if(help.equals("ok")){
          foodInfo = Double.parseDouble(servSz);
        }
      
      }while(foodInfo < 0);

      //modify arg, add to meal arrays
      theDB.add(new Food(newName,foodInfo));
      mealFoods.add(new Food(newName,foodInfo));

    }
    return theDB; //regardless of if modified or not
  }


  /*
   * linear search of DB txt file, 
   * file is NOT sorted, cannot do binary search -> for later?
   * check if string is == to title of food in file
   * creates new Food obj of what it finds
   * @param input - string, to be compared
   * @param theDB - arraylist foods, what the total is
   * @return boolean - true if in file/DB
   */
  private boolean searchDB(String input, ArrayList<Food> theDB){

    for(Food i:theDB){//each food in DB

      if(input.equals(i.name)){
        tempIndex = theDB.indexOf(i);
        return true; //food name is in DB
      }
    } 
    return false;//else, if does not exist in DB
  }


  /*
   * checks passed input if is positive integer
   * @param input - string to be checked
   * @return string, debug line ('ok'/pos int/not num)
   */
  private static String checkPosInt(String input){

    try{      
      if(Integer.parseInt(input) >= 0){ //if positive, end
        return "ok";
      }else{
        return "Servings must be more than 0"; 
      }
    }catch(NumberFormatException ex){
      return "Not an integer, retry"; 
    }
  }//end checkposint()

}//End of Meal.java