/* deleted for now; will use for gui later
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Intro extends JPanel implements ActionListener{
  
  boolean exit = false;
  double threshhold;
  JFrame fram;
*/
  /*
  'constructor' for intro frame 
  in separate file b/c many commands cant be referenced from a static context
  @param main - any JFrame, needs some attributes defined
  (set visiblity, size, closeOperation, etc)
  */
  /*
  public Intro(JFrame main){
    super(new FlowLayout());
    fram = main;


    //HAVE TO CREATE JPANEL, THEN ADD JPANEL TO JFRAME 
    //allows you to easily clear frame w/o destroying it
    
    
 
    JButton done = new JButton("apply");
    //JTextArea instruct = new JTextArea("asjdvhrebieur");
    done.setActionCommand("next");
    
    done.addActionListener(this);

    add(done);
    add(new JTextField(""));
    //add(instruct);

    main.add(this);

    while(!(exit)){
      System.out.print("");
    }

    main.remove(this);
  }
*/
  /*
  actionperformed - does something based on what button pressed
  @param e - actionevent, with string
  */
  /*
  public void actionPerformed(ActionEvent e){
    // if button select 
    if ((e.getActionCommand()).equals("next")){
      exit = true;
      System.out.println("boop");
      fram.remove(this);
    }
    //mark done (can exit method)
    
    
  }//end actionPerformed
  
  public double getThresh(){return threshhold;}


} 
*/
