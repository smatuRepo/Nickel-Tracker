import java.awt.Color;

class Food{

  String name;
  double nickel;
  //double high = 20.0; //micrograms/gram
  //double low = 10.0;

  /*
   * constructor for food class, should be instantiated
   * from a txt file
   * @param name - string, what food is called 
   * @param nickel - amount of nickel in food (micrograms)
   * void
   */
  public Food(String name, double nickel){
    this.name = name;
    this.nickel = nickel;
  }

  /*
   * finds if food has a lot/little nickel
   * red=high nickel
   * yellow=less, iffy
   * green=OK
   * @return colour - java.COLOR - what range the food is in, for JFrame
  */
  public Color getColour(){
    if(nickel > 20.0){
      return Color.RED;
    }else if(nickel < 10.0){
      return Color.GREEN;
    }
    //else, in middle
    return Color.YELLOW;
  }//end getColour()
  
}//end Food