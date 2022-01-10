import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


class Menu extends JFrame implements ActionListener{

  private boolean done;
  private String button;

  /*
  setting up the JFrame, constructor
  no params, void
  */
  public Menu(){//still invis
    super("Nickel Tracker: Tracker of Nickel");
    setBounds(0,0,450,300);
    setLayout(new GridLayout(3,0,3,3)); //2 rows
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }//end constructor

  /*
  waits for a button input, specify which one pressed
  no params
  @return string (1 letter), what button pressed
  */
  public String buttonWait(){
    this.requestFocus();
    
    done = false; //reset
    while(!(done)){
      System.out.print("");
    }
    return button;
  }//end buttonWait()

  /*
  main menu, lets user select which meal they want to add to 
  NEEDs buttonWait
  1 label, 5 buttons, 1 internal frame(textfield+label)
  no params, void
  */
  public void mealSelect(){
    
    //init buttons, assign string to them (give fnx)
    JButton b = new JButton("Breakfast");
    JButton l = new JButton("Lunch");
    JButton d = new JButton("Dinner");
    JButton s = new JButton("Snack");
    JButton e = new JButton("EXIT");
    b.setActionCommand("b");
    l.setActionCommand("l");
    d.setActionCommand("d");
    s.setActionCommand("s");
    e.setActionCommand("e");
    b.addActionListener(this);
    l.addActionListener(this);
    d.addActionListener(this);
    s.addActionListener(this);
    e.addActionListener(this);

    //set up 2nd row - add buttons
    JPanel r2 = new JPanel();
    r2.add(b);
    r2.add(l);
    r2.add(d);
    r2.add(s);
    r2.add(e);

    JLabel r1 = new JLabel("[here is thing, enter what meal you ate blah]"); //how align
    //r2.setHorizontalAlignment(SwingConstants.CENTER);

    //add to frame
    add(r1);
    add(r2);
    
    //needs buttonwait to do smth
  }//end mealSelect()

  /*
  makes JInternalFrame, has textfield and confirm button
  does NOT need buttonWait
  @param name - what meal, will be title of mini frame
  @return string - what's in textfield
  */
  public String bld(String name){
    
    //set up mini frame
    JInternalFrame j1 = new JInternalFrame(name,false,false,true);
    j1.setLayout(new GridLayout(1,2));
    j1.setBounds(0,0,200,200);

    //make button, textfield, assign fnx
    JButton jb1 = new JButton("ENTER");
    jb1.setActionCommand("enter");
    jb1.addActionListener(this);

    JTextField jt1 = new JTextField();

    //add to frame
    j1.add(jt1);
    j1.add(jb1);
    add(j1);

    j1.setVisible(true);
    j1.grabFocus();
    jt1.requestFocus();
    
    //does not need buttonwait
    done = false;
    while(!(done)){
      System.out.print("");
    }

    //j1.setVisible(false);
    j1.dispose(); //clear out
    remove(j1);

    return jt1.getText(); //whats in the baaax
  }//end BLD()

  /*
  makes outro screen, many rows of foods, ouputs total nickel, user confirmation
  does not need buttonwait
  @param numMeals - should be length of meals ArrayList
  @param meals - arraylist of meals
  @param totNik - total nickel in all meals
  @param advice - string, should call advice()
  void.
  */
  public void outro(int numMeals, ArrayList<Meal> meals, double totNik, String advice){
    //setText("SUMMARY");

    //set up 1st row
    JPanel o1 = new JPanel(new GridLayout(numMeals,0,1,5));

    //adding foods to o1
    for(Meal i: meals){

      JPanel newMeal = new JPanel();//new row
      for(Food x: i.mealFoods){ 
        JPanel newFood = new JPanel();
        newFood.setBackground(x.getColour());
        newFood.add(new JLabel(x.name));
        newMeal.add(newFood);
      }//end nested for
      o1.add(newMeal); //adds new row
    }//end for
    //o1.setvisible(true);
    
    //2nd/3rd row, jlabels, button
    JPanel o2 = new JPanel();
    o2.add(new JLabel("In total, you ate " + totNik + "ug of nickel."));
    o2.add(new JLabel(advice));

    //add fnx
    JButton o3 = new JButton("Bye!"); //img?
    o3.setActionCommand("b");
    o3.addActionListener(this);
    
    //add to frame
    add(o1);
    add(o2);
    add(o3);

    
    //does not need buttonwait
    done = false; //reset var
    while(!(done)){
      System.out.print("");
    }//end wait
  }//end outro()



  /*
  chenges local vars, lets stuff wait for input
  @param e - actionevent, assiciated with a button
  void.
  */
  public void actionPerformed(ActionEvent e){
    done = true;
    button = e.getActionCommand();//lets buttonWait choose button
  }//end actionPerformed()
}//end Menu