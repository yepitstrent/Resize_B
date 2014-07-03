import objectdraw.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// This program demonstrates the use of the listener interface, and its use 
// with gui components.
public class DrawingProgram extends WindowController implements ActionListener{
    
    // Width of border around drawing area
    private static final int BORDER_X = 10;
    private static final int BORDER_Y = 10;
    
    // Size parameters
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 100;
    private static final int INIT_SIZE = 20;
    
    
    // Default size when we create a new shape
    private static final int DEFAULT_SIZE = 50;
    
    // Initial Location of shapes to be drawn
    private Location defaultLocation;
    
    // Size of newly created objects
    private JSlider sizeSlider;
    
    // The menu determining which kind of object is drawn
    private JComboBox figureMenu;
    
    // The menu determining which color the object should be
    private JComboBox colorMenu;
    
    // boundary of area drawing in
    private FramedRect drawingArea;
    
    // The variable to hold the last shape created
    private DrawableInterface newShape;
    
    // Used to support the usual dragging algorithm
    private boolean dragging;
    private Location lastMouse;
    
    /* 
     * Draws the program with the buttons and a blank drawing area
     */
    public void begin() {
        // Create menu for selecting figures
        figureMenu = new JComboBox();
        figureMenu.addItem("FramedSquare");
        figureMenu.addItem("FramedCircle");
        figureMenu.addItem("FilledSquare");
        
        // create menu for selecting colors
        colorMenu = new JComboBox();
        colorMenu.addItem("Red");
        colorMenu.addItem("Blue");
        colorMenu.addItem("Green");
        colorMenu.addItem("Yellow");
        
        colorMenu.addActionListener(this);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(figureMenu);
        bottomPanel.add(colorMenu);
        Container contentPane = getContentPane();
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        
        
        // create size slider
        sizeSlider = new JSlider(JSlider.VERTICAL, MIN_SIZE, MAX_SIZE, INIT_SIZE);
        contentPane.add(sizeSlider, BorderLayout.WEST);
        
        contentPane.validate();
        
        // Draw the drawing area
        drawingArea =
            new FramedRect(
                           BORDER_X,
                           BORDER_Y,
                           canvas.getWidth() - 2 * BORDER_X,
                           canvas.getHeight() - 2 * BORDER_Y,
                           canvas);
        
        defaultLocation = new Location((canvas.getWidth() - DEFAULT_SIZE) / 2, (canvas.getHeight() - DEFAULT_SIZE) / 2);
    }
    
    /* 
     * When the user clicks inside the drawing area, create the selected shape.
     */
    public void onMouseClick(Location loc) {
        // Handle shape buttons
        if (drawingArea.contains(loc)) {
            int size = sizeSlider.getValue();
            Object figureChoiceString = figureMenu.getSelectedItem();
            if (figureChoiceString.equals("FramedSquare")) {
                newShape = new FramedRect(defaultLocation, size, size, canvas);
            } else if (figureChoiceString.equals("FramedCircle")) {
                newShape = new FramedOval(defaultLocation, size, size, canvas);
            } else if (figureChoiceString.equals("FilledSquare")) {
                newShape = new FilledRect(defaultLocation, size, size, canvas);
            }
            setColor();
        }
    }
    
    
    /**
     * change newShape to color on color menu
     * Assumes newShape not null
     */
    private void setColor() {
        Object colorChoiceString = colorMenu.getSelectedItem();
        if (colorChoiceString.equals("Red")) {
            newShape.setColor(Color.red);
        } else if (colorChoiceString.equals("Blue")) {
            newShape.setColor(Color.blue);
        } else if (colorChoiceString.equals("Green")) {
            newShape.setColor(Color.green);
        } else if (colorChoiceString.equals("Yellow")) {
            newShape.setColor(Color.yellow);
        }
    }
    
    
    /**
     * Select a new color for the last object drawn
     */
    public void actionPerformed(ActionEvent evt) {
        if (newShape != null) {
            setColor();
        }
    }
    
    /* 
     * Drag the shape with the mouse.  Notice that it doesn't matter what
     * kind of shape (FramedRect, FilledOval, etc.) we have.
     */
    public void onMouseDrag(Location loc) {
        if (dragging) {
            newShape.move(loc.getX() - lastMouse.getX(), loc.getY() - lastMouse.getY());
            lastMouse = loc;
        }
    }
    
    /* 
     * Start a drag
     */
    public void onMousePress(Location loc) {
        if (newShape != null && newShape.contains(loc)) {
            dragging = true;
            lastMouse = loc;
        }
    }
    
    /* 
     * Stop a drag
     */
    public void onMouseRelease(Location arg0) {
        dragging = false;
    }
    
}
