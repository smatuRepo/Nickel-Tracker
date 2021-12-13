//special meal
import java.util.*;

class Snack extends Meal{
  Scanner keb = new Scanner(System.in);
  String snackTime;

  public Snack(String snackTime)
  {
    super("Snacks", snackTime);
  }

  
  private String whatTime()
  {
    System.out.print("What is the time? (Military time): ");
    return validTime(keb.nextLine());
  }
/*
  private String validTime ()
  {
    String str = snackTime;

    char[] timech = new char [str.length()];

    if (str.length().equals("4") && str[1,2] >=0 && str[1,2] <= 23 && str[3,4] > 60);
    {
      return "No snack time.";
    }
    else 
    {
      return 
    } 
  }
*/

}