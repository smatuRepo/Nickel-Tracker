import java.util.*;
import java.io.*;


class Meal{

  //declare attributes

  Scanner keebs = new Scanner (System.in);

  //init vars
  int tempIndex; //for use b/n addfood, searchdb
  String title;
  String time;
  ArrayList<Food> mealFoods = new ArrayList <Food>();
  

  /*
  constructor for Meal, initiallize name, time
  @param title - name of meal
  @param time - formatted HH:MM, 24-hr clock
  void.
  */
  public Meal(String title, String time)
  {
    this.title = title;
    this.time = time;
  }



  /*
  adds food to Meal's food arraylist, should implement GUI later
  @param theDB - arraylist foods, database
  void. changes obj attributes 
  */
  public void addFood(Menu test, ArrayList<Food> theDB)
  {	
    //String serv;
	  //System.out.print ("Food?: ");
    String nameFood = test.textInput(title);
    


    //READ file, compare to input
    if(searchDB(nameFood,theDB)){//add to array
      //System.out.print ("");
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
  Finds total Nickel in the arraylist of Foods
  @return the count value, double
  */
  public double tallyNickel()
  {
    double count = 0.0;
    for (Food i: mealFoods)
      count += i.nickel;
    return count;
  }


  /*
  The name of the food is added to the txt file database
  Also adds the nickel amount to the database
  @param newName - user input name, not in DB already, LOWERCASE
  @param theDB - the database, arraylist foods
  @return theDB, modified (if user says Yes) - 1 new food added
  */
  private ArrayList<Food> addToDB(String newName, ArrayList<Food> theDB, Menu text)
  {
    //System.out.println ("Input Doesn't Compute");
    //System.out.println ("Would you like to add it to the database (y/n)");
    //String yn = keebs.nextLine();
    String yn = text.textInput("Would you like to add it to the database (y/n)");

     
    if (yn.toLowerCase().equals ("y"))
    {
      double foodInfo = -1;
      String help = "What is the nickel amount?";
      
      do {//loop until get double 
        
        String servSz = text.textInput(help); //gets input, should be double (print debug line on internal frame)
        help = Main.checkPosDouble(servSz); //returns debug line 
        
        
        if(help.equals("ok")){
          //System.out.print ("What is the nickel amount?: ");
          foodInfo = Double.parseDouble(servSz);
        }
      
      }while(foodInfo < 0);

      //modify arg, add to mealarray
      theDB.add(new Food(newName,foodInfo));
      mealFoods.add(new Food(newName,foodInfo));

      //REWRITE all db contents to file
      try {
      //connect to file, buffer
      FileWriter writer = new FileWriter("database.txt",true); //2nd arg lets it APPEND, not clear the file
      BufferedWriter buffer = new BufferedWriter(writer);

      //write new lines w/ buffer 
      buffer.write(newName);
      buffer.newLine();
      buffer.write("" + foodInfo);
      buffer.newLine();
      
     
      
      buffer.close(); //close
      writer.close();
    }catch(Exception ex){
      System.out.println(ex.getMessage());
    }//end write


    }
    return theDB; //regardless of if modified or not
  }



  /*
  linear search of DB txt file, 
  check if string is == to title of food in file
  creates new Food obj of what it finds
  @param input - string, to be compared
  @param theDB - arraylist foods, what the total is
  @return boolean - true if in file/DB
  */
  private boolean searchDB(String input, ArrayList<Food> theDB){

    for(Food i:theDB){
      if(input.equals(i.name)){
        tempIndex = theDB.indexOf(i);
        return true; //food name is in DB
      }
    }
    
    return false;//else
  }




  /*
  Asks for (+) int input, gets input
  @param keyboard - Scanner, will not be closed
  @return string, debug line (ok/pos int/not num)
  */
  private static String checkPosInt(String input){

    //System.out.print("Enter a positive integer: ");
    //int input;
	try{
	  //input = Integer.parseInt(keyboard.nextLine());        
	  if(Integer.parseInt(input) >= 0){ //if positive, end
	    return "ok";
	  }else{
	    //System.out.print("Value must be positive, retry: ");
	    return "Servings must be more than 0";
	    }
    }catch(NumberFormatException ex){
      //System.out.print("Invalid input, retry: ");
      return "Not an integer, retry"; //retry
    }
  }//end checkposint()

}//EOF