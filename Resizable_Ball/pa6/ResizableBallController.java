/*
 * Name: Trent Russell
 * Login: cs8bafk
 * Date: 2-20-12
 * File: ResizableBallController.java
 * Sources of Help: 
 */

import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


//public static boolean running = true;
/**************************************************************************
* Name: class ResizableBallController
* Purpose: declairs variables that will be used and contains methods
***************************************************************************/

//public class ResizableBallController extends WindowController implements ActionListener
public class ResizableBallController extends WindowController implements ChangeListener, ActionListener, MouseListener, MouseMotionListener
{
  private Line verticalLine;
  private Line horizontalLine;

  private boolean hLineGrab = false;
  private boolean vLineGrab = false;
  private boolean bothLines = false;

  private double horzEndPointY,
                 vertEndPointX;

  private JSlider sizeSlider;
  private Dimension sliderDimension; 
  
  private JButton start;
  private JButton stop;
  private JButton clearAll;
  
  private JComboBox figureMenu;
  
  private int speed;
  private double vRatio, hRatio,newVlineX, newHlineY;

  private double currentX_V_Line = 250.0;
  private double currentY_H_Line = 250.0;

  private double SIZE = 50;
  private double boundry = 5;

  private JLabel ballControlLabel;
  private JLabel sliderControlLabel;

  public static boolean running = true;

  private ResizableBall currentBall;
 
 

  /*************************************************************************
  * Name: begin()
  * Purpose: settings for the begining of the program
  * Parameters:None.
  * Return: void
  *************************************************************************/
  public void begin()
  {
    //public static boolean running = true;
	  
    sizeSlider = new JSlider(SwingConstants.HORIZONTAL,1,100,50);
    sizeSlider.addChangeListener(this);
    //sizeSlider.addActionListener(this);

    sliderDimension = new Dimension(100 , 20);

    ballControlLabel = new JLabel("Resizable Ball Controller");
    sliderControlLabel = new JLabel("The Speed Is 50");   


    start = new JButton("Start");
    stop = new JButton("Stop");
    clearAll = new JButton("Clear All");

    //stop.addChangeListener(this);

    stop.addActionListener(this);
    start.addActionListener(this);
    clearAll.addActionListener(this);

    canvas.addMouseListener(this);
    canvas.addMouseMotionListener(this);
    
    JPanel myButtonPanel = new JPanel();
    JPanel myTopPanel = new JPanel(new GridLayout(2, 150));

    JPanel mySliderPanel = new JPanel();

    JPanel myWordPanel = new JPanel();
    
    myWordPanel.add(ballControlLabel);
    
    myButtonPanel.add(start);
    myButtonPanel.add(stop);
    myButtonPanel.add(clearAll);
    
    myTopPanel.add(myWordPanel);
    myTopPanel.add(myButtonPanel);
    mySliderPanel.setPreferredSize(sliderDimension);

    mySliderPanel.add(sliderControlLabel);
    mySliderPanel.add(sizeSlider);

    
    
    Container contentPane = getContentPane();
    Container TopContentPane = getContentPane();
    
    TopContentPane.add(myTopPanel, BorderLayout.NORTH);
   
    contentPane.add(mySliderPanel, BorderLayout.SOUTH);
   
   
    

    //vertical line starting position
    verticalLine = new Line(canvas.getWidth()/2, 0, 
      canvas.getWidth()/2, canvas.getHeight(), canvas);

    //horizontal line starting position
    horizontalLine = new Line(0, canvas.getHeight()/2, 
      canvas.getWidth(), canvas.getHeight()/2, canvas);

    //the ratio of the line
    vRatio = currentX_V_Line/canvas.getWidth();
    hRatio = currentY_H_Line/canvas.getHeight();

  }//end begin()



  /*************************************************************************
  * Name: onMousePress()
  * Purpose: boolean checks for line positions
  * Parameters: Location point
  * Return: void
  *************************************************************************/
  public void onMousePress(Location point)
  {
    //System.out.println("in on mouse press in RBC");
    /*************************************
    *check to see if both lines are grabed
    *************************************/
    if(verticalLine.contains(point) && horizontalLine.contains(point))
    {
      bothLines = true;
     
    }
    else
    {
      /*****************************
      *if H line has been grabbed
      *set flag to true
      *****************************/
      if(verticalLine.contains(point))
      {
        vLineGrab = true; 
      }

      /****************************
      *if V line has been grabbed
      *set flag to true
      ****************************/
      if(horizontalLine.contains(point))
      {
        hLineGrab = true;
      }
    }

    


  }//end onMousePress()

  /*************************************************************************
  * Name: onMouseDrag()
  * Purpose: if boolean flags are true, move the lines
  * Parameters: Location mousePosition
  * Return: void.
  *************************************************************************/
  public void onMouseDrag(Location mousePosition)
  { 
    /**************************
    *if true, move both lines.
    **************************/
    if(bothLines == true)
    {
      verticalLine.moveTo(mousePosition.getX(), 0);
      horizontalLine.moveTo(0,mousePosition.getY());

      vertEndPointX = mousePosition.getX();
      horzEndPointY = mousePosition.getY();
      
      //get the current vertical line position and get new ratio
      currentX_V_Line = mousePosition.getX();
      vRatio = currentX_V_Line/canvas.getWidth();

      //get the current horzontal line position and get new ratio
      currentY_H_Line = mousePosition.getY();
      hRatio = currentY_H_Line / canvas.getHeight();

      /*********************************************
       *check to see if the vertical lines have been 
       *moved beyond the set boundries
       ********************************************/
      if(verticalLine.getStart().getX() < boundry)
      {
        verticalLine.moveTo(boundry, 0);
      }
      if(verticalLine.getStart().getX() > canvas.getWidth() -boundry)
      {
        verticalLine.moveTo(canvas.getWidth()-boundry, 0);
      }

      /**********************************************
       *check to see if the horzontal lines have been 
       *moved beyond the set boundries
       *********************************************/
      if(horizontalLine.getStart().getY() < boundry)
      {
        horizontalLine.moveTo(0,boundry);
      }
      if(horizontalLine.getStart().getY() > canvas.getHeight() - boundry)
      {
        horizontalLine.moveTo(0,canvas.getHeight() - boundry);
      }

    }//end if
    else
    {
      /****************************
      *if true, move vertical line.
      ****************************/
      if(vLineGrab == true)
      {
        verticalLine.moveTo(mousePosition.getX(), 0);
        vertEndPointX = mousePosition.getX();

        currentX_V_Line = mousePosition.getX();
        vRatio = currentX_V_Line/canvas.getWidth();

         /********************************************
         *check to see if the vertical lines have been 
         *moved beyond the set boundries
         ********************************************/
        if(verticalLine.getStart().getX() < boundry)
        {
          verticalLine.moveTo(boundry, 0);
        }

        if(verticalLine.getStart().getX() > canvas.getWidth() -boundry)
        {
          verticalLine.moveTo(canvas.getWidth()-boundry, 0);
        }
  
      }
      
      /*****************************
      *if true, move horzontal line.
      *****************************/
      if(hLineGrab == true)
      {
        horizontalLine.moveTo(0,mousePosition.getY());
        horzEndPointY = mousePosition.getY();

        currentY_H_Line = mousePosition.getY();
        hRatio = currentY_H_Line / canvas.getHeight();

        /**********************************************
         *check to see if the horzontal lines have been 
         *moved beyond the set boundries
         *********************************************/
        if(horizontalLine.getStart().getY() < boundry)
        {
          horizontalLine.moveTo(0,boundry);
        }
        if(horizontalLine.getStart().getY() > canvas.getHeight() - boundry)
        {
          horizontalLine.moveTo(0,canvas.getHeight() - boundry);
        }
      }      
    }//end else

    

  }//end onMouseDrag()

  /*************************************************************************
  * Name: onMouseRelease()
  * Purpose: resets the boolean variables after release
  * Parameters: Location point
  * Return: void.
  *************************************************************************/
  public void onMouseRelease(Location point)
  {
    //reset boolean variables
    hLineGrab = false;
    vLineGrab = false;
    bothLines = false;

  }//end onMouseRelease()

  /************************************************************************
  * Name: paint()
  * Purpose: controls the setting for the canvas window
  * Parameters: java.awt.Graphics g 
  * Return: void
  ************************************************************************/
  public void paint( java.awt.Graphics g )
  {
    super.paint( g );

    /*********************************************************************
     *calculates the position for the new vertical line, then readjusts it
     *********************************************************************/
    newVlineX = vRatio*canvas.getWidth();
    verticalLine.moveTo(newVlineX, 0);
    verticalLine.setEnd(newVlineX, canvas.getHeight());    

    /***********************************************************************
     *calculates the position for the new horazontal line, then readjusts it
     **********************************************************************/
    newHlineY = hRatio*canvas.getHeight();
    horizontalLine.moveTo(0, newHlineY);
    horizontalLine.setEnd(canvas.getWidth(), newHlineY);
    
    /*********************************************
     *Test to see if vertical line is moved beyond 
     *the boundry after resizing the window
     ********************************************/
    if(verticalLine.getStart().getX() < boundry)
    {
      verticalLine.moveTo(boundry, 0);
      verticalLine.setEnd(boundry, canvas.getHeight());
    }
    if(verticalLine.getStart().getX() > canvas.getWidth() -boundry)
    {
      verticalLine.moveTo(canvas.getWidth()-boundry, 0);
      verticalLine.setEnd(canvas.getWidth()-boundry, canvas.getHeight());
    }

    /***********************************************
     *Test to see if horizontal line is moved beyond 
     *the boundry after resizing the window
     **********************************************/
    if(horizontalLine.getStart().getY() < boundry)
    {
      horizontalLine.moveTo(0,boundry);
      horizontalLine.setEnd(canvas.getWidth(), boundry);
    }
    if(horizontalLine.getStart().getY() > canvas.getHeight()-boundry)
    {
      horizontalLine.moveTo(0,canvas.getHeight()-boundry);
      horizontalLine.setEnd(canvas.getWidth(),canvas.getHeight()-boundry);
    }
  }//end paint()

  /************************************************************************
  * Name: onMouseClick()
  * Purpose: create new ball
  * Parameters: Location point
  * Return: void.
  ************************************************************************/
  
  public void stateChanged(ChangeEvent evt)
  {
    

    speed = sizeSlider.getValue();
    
    sliderControlLabel.setText("The Speed Is " + speed);


  }//end stateChanged()

   public void actionPerformed( ActionEvent evt ) 
   {

     if(evt.getSource() == stop)
     {
       
       running = false;
     }
     if(evt.getSource() == start)
     {
       running = true;
     }
     
     //if(evt.getSource() == clearAll)
     {

     }
     
     
   }//end actionPerformed()

  public void mouseClicked(MouseEvent event)
  {

    currentBall = new ResizableBall(event.getX(), event.getY(), SIZE,
                      canvas, horizontalLine, verticalLine, running);

    canvas.addMouseListener(currentBall);
    canvas.addMouseMotionListener(currentBall);
    stop.addActionListener(currentBall);
    start.addActionListener(currentBall);
    clearAll.addActionListener(currentBall);

    sizeSlider.addChangeListener(currentBall);
  }

  public void mouseEntered(MouseEvent event)
  {}

  public void mouseExited(MouseEvent event)
  {}

  public void mousePressed(MouseEvent event)
  {}

  public void mouseReleased(MouseEvent event)
  {}
  
  public void mouseMoved(MouseEvent event)
  {}

  public void mouseDragged(MouseEvent event)
  {}

}//end class ResizableBallController
