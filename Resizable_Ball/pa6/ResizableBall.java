/**************************************************************************
* Name: Trent Russell
* Login: cs8bafk
* Date: 1-31-12
* File: ResizableBall.java
* Sources of Help: Bob, Michael, Meera, Rick Ord...
* This program is the driver to create ResizableBall objects
**************************************************************************/

import objectdraw.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;


/*************************************************************************
* Name: class ResizableBall
* Purpose: declairs variables that will be used and contains methods
**************************************************************************/
public class ResizableBall extends ActiveObject implements ActionListener, ChangeListener, MouseListener, MouseMotionListener
{
  private int ballSize = 50;

  private int ballMax = ballSize * 2;
  private int ballMin = ballSize/2;

  private int growthSize = 50;

  private FilledOval ballGraphic;

  private DrawingCanvas canvas;

  private Location centerPoint;
 
  private Line horzLine;
  private Line vertLine;
  
  private boolean runProgram = true;

  private boolean isGrowing = true;
  private static boolean isClear = false;
  private static int pauseTime = 50;
  private boolean dragging = false;

  private int growth = 2;
  
  private Location lastMouse;
  

  /***********************************************************************
  * Name: Constructor ResizableBall
  * Purpose: defines what a ResizableBall is.
  * Parameters: double xLoc, double yLoc, double size,
  *             DrawingCanvas aCanvas, Line hLine, Line vLine
  ************************************************************************/
  public ResizableBall(double xLoc, double yLoc, double size,
          DrawingCanvas aCanvas, Line hLine, Line vLine, boolean growing)
  {
    isClear = false;

    horzLine = hLine;
    vertLine = vLine;

    canvas = aCanvas;
    //pauseTime = ballSpeed;
    ballGraphic = new FilledOval(xLoc - (ballSize/2), 
          yLoc - (ballSize/2), ballSize, ballSize, canvas);
    aCanvas.addMouseListener(this);
    setColor();
    runProgram = growing;
    start();

  }

  //public static void runningCheck(boolean runCheck)
  {
    
    //runProgram = runCheck;

  }//end runningCheck()



  /***********************************************************************
  * Name: setColor()
  * Purpose: checks for line positions and set color
  * Parameters: None.
  * Return: void.
  ***********************************************************************/
  public void setColor()
  { 
    //first quadrent
    if(ballGraphic.getX() + (growthSize/2) < vertLine.getStart().getX() 
        && ballGraphic.getY() + (growthSize/2) < horzLine.getStart().getY())
    {
      ballGraphic.setColor(Color.CYAN);
    }
    //second quadrent
    if(ballGraphic.getX() + (growthSize/2) >= vertLine.getStart().getX() 
        && ballGraphic.getY()+ (growthSize/2) < horzLine.getStart().getY())
    {
      ballGraphic.setColor(Color.MAGENTA);
    } 
    //third quadrent
    if(ballGraphic.getX() + (growthSize/2) < vertLine.getStart().getX() 
        && ballGraphic.getY() + (growthSize/2) > horzLine.getStart().getY())
    {
      ballGraphic.setColor(Color.YELLOW);
    }
    //fourth quadrent
    if(ballGraphic.getX() + (growthSize/2) >= vertLine.getStart().getX() 
        && ballGraphic.getY()+ (growthSize/2) >= horzLine.getStart().getY())
    {
      ballGraphic.setColor(Color.BLACK);
    }
  }

/**************************************************************************
* Name: run()
* Purpose: runs the balls
* Parameters: None.
* Return: void.
**************************************************************************/
  public void run()
  {
    while(true)
    {
       setColor();

       if(runProgram == true)
       {
         ballGraphic.setSize(ballGraphic.getWidth() + growth, ballGraphic.getHeight() + growth);
         ballGraphic.move(-growth/2, -growth/2);

         if(ballGraphic.getWidth() >= ballMax || ballGraphic.getWidth() <= ballMin)
         {
            growth = -growth;
         }

       }//end if(runProgram == true)

       if(isClear == true)
       {  
         ballGraphic.removeFromCanvas();
         break;
       }//end if(isClear == true) 

       pause(pauseTime);

    }//end while(true)

  }//end run()

  public void actionPerformed( ActionEvent evt ) 
  {  //System.out.println("in action performed RB");
    if(((JButton)evt.getSource()).getText().equals("Stop"))
      {
        runProgram = false;
        
      }
    if(((JButton)evt.getSource()).getText().equals("Start"))
      {
        runProgram = true;
      }
    if(((JButton)evt.getSource()).getText().equals("Clear All"))
    {
      isClear = true;
    }
  }//end actionPerformed()

  public void stateChanged(ChangeEvent evt)
  {
    
    pauseTime = ((JSlider)evt.getSource()).getValue();
    pauseTime = 101 - pauseTime;
    //System.out.println("in state Changed RB");


  }//end stateChanged


  public void mouseClicked(MouseEvent event)
  {}

  public void mouseEntered(MouseEvent event)
  {}

  public void mouseExited(MouseEvent event)
  {}

  public void mousePressed(MouseEvent event)
  {
    //System.out.println("in on mouse pressed in RB");
    Location loc = new Location(event.getX(), event.getY() );
    if (ballGraphic != null && ballGraphic.contains(loc)) 
    {
            dragging = true;
            lastMouse = loc;
    }

  }

  public void mouseReleased(MouseEvent event)
  {
    //System.out.println("in on mouse Released in RB");

     dragging = false;


  }


  public void mouseDragged(MouseEvent event)
  {
     //System.out.println("in on mouse dragged in RB");
     if (dragging) 
     {
        if(event.getX() > 5 && event.getY() > 5 && 
          event.getX() < canvas.getWidth() - 5 && 
          event.getY() < canvas.getHeight() - 5)
        {
            ballGraphic.move(event.getX() - lastMouse.getX(), 
              event.getY() - lastMouse.getY());
            lastMouse = new Location(event.getX(), event.getY());
        }
     }


  }//end onMouseDrag()

 public void onMouseRelease(Location loc) 
 {
   dragging = false;
 }

 public void mouseMoved(MouseEvent event)
 {
   //System.out.println("in mouse moved");
 }

}//end class ResizableBall
