import java.awt.Color;

class Food{

  String name;
  double nickel;
  //value of constants done
  double high = 20.0; //micrograms/gram
  double low = 10.0;

  /*
  constructor for food class, should be instantiated
  from a txt file
  @param name - string, what food is called 
  @param nickel - amount of nickel in food (micrograms)
  void
  */
  public Food(String name, double nickel){
    this.name = name;
    this.nickel = nickel;
  }

  /*
  finds if food has a lot/little nickel
  red=high nickel
  yellow=less
  green=OK
  @return colour - what range the food is in, for JFrame
  */
  public Color getColour(){
    if(nickel > high){
      return Color.RED;
    }else if(nickel < low){
      return Color.RED;
    }
    //else, in middle
    return Color.YELLOW;
  }
  


}