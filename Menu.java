import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


class Menu extends JFrame implements ActionListener{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  private boolean done;
  private String button;

  /*
  setting up the JFrame, constructor
  no params
  void
  */
  public Menu(){//still invis
    super("Nickel Tracker: Tracker of Nickel");
    setBounds(0,0,450,300);
    setLayout(new GridLayout(3,0,3,3)); //3 rows
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
  }//end constructor


  /*
  waits for a button input, specify which one pressed
  no params
  @return string, what button pressed
  */
  public String buttonWait(){
    //this.requestFocus();
    
    done = false; //reset
    //button = "";
    
    while(!(done)){
      System.out.print("");
    }
    
    //System.out.println(button); //debug
    
    return button;
  }//end buttonWait()


  /*
   * introduction panel - adds jlabel + 2 buttons - read or write a file (log)
   * does NOT need external buttonWait()
   * no param
   * @return character - r or w (read or write)
   */
  public void intro(){
        
    JButton read = new JButton("Load File");
    JButton create = new JButton("Make New Log");
    read.setActionCommand("r");
    create.setActionCommand("w");
    read.addActionListener(this);
    create.addActionListener(this);
    
    JLabel help = new JLabel("Welcome to Nickel Tracker");
    
    JPanel row2 = new JPanel();
    
    row2.add(read);
    row2.add(create);
    add(help);
    add(row2);
    
    //boolean i = create.requestFocusInWindow();
    repaint();
    setVisible(true);
    //return buttonWait().charAt(0); //string -> char, just easier to deal with
    
  }//end intro()


  /*
   * sets up frame, lets user input double - what their threshold is 
   * 2 buttons - custom/default (150ug) 
   * no params
   * void - needs buttonWait() to do anything with buttons
   */
  public void threshGet() {
    //init buttons, assign string to them (give fnx)
    setVisible(false);
    JButton c = new JButton("Custom");
    JButton def = new JButton("Default");
    
    c.setActionCommand("c");
    def.setActionCommand("def");

    c.addActionListener(this);
    def.addActionListener(this);

    //set up 2nd row - add buttons
    JPanel r2 = new JPanel();
    r2.add(c);
    r2.add(def);
    

    JLabel r1 = new JLabel("Do you know how much nickel (in micrograms) you can have in a day before feeling sick?"); //how align
    //r2.setHorizontalAlignment(SwingConstants.CENTER);

    //add to frame
    add(r1);
    add(r2);
    setVisible(true);
    
    //needs buttonwait to do smth
  }//end threshGet()


  
/*
  main menu, lets user select which meal they want to add to 
  NEEDs buttonWait
  1 label, 5 buttons, 1 internal frame(textfield+label)
  no params, void
  */
  public void mealSelect(){
    setVisible(false);
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
    e.setActionCommand("x");
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
    
    setVisible(true);
    
    //needs buttonwait to do smth
  }//end mealSelect()
  

  
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
    //setText("SUMMARY");\
    setVisible(false);
    setSize(450,150+(numMeals * 40)); //will always be at least 3 meals
    setLayout(new GridLayout(numMeals + 2,0,5,5));

    //set up 1st row
    //JPanel o1 = new JPanel(new GridLayout(0,numMeals,1,5));

    //adding foods to each row
    for(Meal i: meals){

      JPanel newMeal = new JPanel();//new row
      
      newMeal.add(new JLabel(i.time + " - " + i.title));
      
      for(Food x: i.mealFoods){
        JPanel newFood = new JPanel();
        newFood.setBackground(x.getColour());
        
        newFood.add(new JLabel(x.name));
        newMeal.add(newFood); //then food.getcolour, change colour of panel
      }//end nested for
      add(newMeal); //adds new row
    }//end for

    
    //2nd/3rd row, jlabels, button
    JPanel o2 = new JPanel();
    o2.add(new JLabel("In total, you ate " + totNik + " ug of nickel."));
    o2.add(new JLabel(advice));

    //add fnx
    JPanel o3 = new JPanel();
    JButton quit = new JButton("Save & Quit"); //img?
    JButton back = new JButton("Save & Continue"); 
    quit.setActionCommand("q");
    back.setActionCommand("b");
    quit.addActionListener(this);
    back.addActionListener(this);
    
    o3.add(back);
    o3.add(quit);
    
    //add to frame
    //add(o1);
    add(o2);
    add(o3);
    setVisible(true);
    
    //does not need buttonwait
    //done = false; //reset var
    //while(!(done)){
    //  System.out.print("");
    //}//end wait

    //o1.dispose();
    //o2.dispose();
    //o3.dispose();
    
    //remove(o1);
    //remove(o2);
    //remove(o3);
    
    
  }//end outro()
  
  
  
  /*
   * makes & adds JInternalFrame, has textfield and confirm button
   * does NOT need buttonWait
   * @param name - what meal, will be title of mini frame
   * @return string - what's in textfield
  */
  public String textInput(String name){
    //this.setVisible(false);
    
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
    jt1.grabFocus();
    //jt1.requestFocusInWindow();
    
    //does not need buttonwait, will only exit when enter pressed
    while(true) {
      if(buttonWait().equals("enter")){ //if enter pressed
        
        j1.dispose(); //clear out
        remove(j1);    
        return jt1.getText(); //whats in the baaax (Food name)
      }//end if 
    }//end while
  }//end textInput()
  

  
  /*
   * used to reset frame - removes all components
   * no params, 
   * void
   */
  public void reset() {
    getContentPane().removeAll();
    repaint();
  }//end reset
  
  
  
  /*
  chenges local vars, lets stuff wait for input
  @param e - actionevent, assiciated with a button
  void.
  */
  public void actionPerformed(ActionEvent e){
    done = true;
    button = "";
    button = e.getActionCommand();//lets buttonWait choose button
    System.out.println(button);
  }//end actionPerformed()
}//end Menu