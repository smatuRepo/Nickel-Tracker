import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Intro extends JPanel implements ActionListener{
  
  boolean exit = false;
  double threshhold;

  /*
  'constructor' for intro frame 
  inseparate file b/c many commands cant be referenced from a static context
  @param main - any JFrame, needs some attributes defined
  (set visiblity, size, closeOperation, etc)
  */
  public Intro(JFrame main){
    super();

    //HAVE TO CREATE JPANEL, THEN ADD JPANEL TO JFRAME 
    //allows you to easily clear frame w/o destroying it
    
    main.setLayout(new GridLayout(0,2));
 
    JButton done = new JButton("apply");
    JTextArea instruct = new JTextArea("asjdvhrebieur");
    done.setActionCommand("next");
    
    done.addActionListener(this);

    main.add(done);
    main.add(new JTextField());
    main.add(instruct);

    while(exit==false){
      System.out.print("");
    }
    main.removeAll();
  }

  /*
  actionperformed - does something based on what button pressed
  @param e - actionevent, with string
  */
  public void actionPerformed(ActionEvent e){
    // if button select 
    if ((e.getActionCommand()).equals("next")){
      exit = true;
      System.out.println("boop");
    }
    //mark done (can exit method)
    
    
  }//end actionPerformed
  
  public double getThresh(){return threshhold;}


}
