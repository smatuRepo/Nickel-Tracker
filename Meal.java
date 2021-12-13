import java.util.*;
//import java.util.*;


class Meal implements Comparable<Meal>{
  //declare attributes
  Scanner keebs = new Scanner (System.in);
  String namefood;
  int numfoods;  
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



  //addfood
  public void addFood ()
  {



    System.out.print ("Number of servings?: ");
    numfoods = Integer.parseInt(keebs.nextLine());

    if (numfoods == 0)
    {

    }

    //checks the readline from the database code comparing the number of servings
    else if (numfoods )
    {

    }
    else 
    {

    }
  }

  //tallynickel
  public double tallyNickel()
  {
    double count = 0.0;
    for (Food i: allFoods)
      count += i.nickels;
    return count;
  }


  //addtodb
  private void addtodb()
  {
    
    System.out.println ("Input Doesn't Compute");
    System.out.println ("Would you like to add it to the database");
    yn = keebs.nextLine();

     
    if (yn.equals ("Yes"))
    {
      System.out.print ("What is the nickel amount?: ");
      foodinfo = Integer.parseInt(keebs.nextLine());

    }

  }



  /*
  used to sort arraylist meals
  */
  public int compareTo(Meal other) {
    return time.compareTo(other.time);
  }
}