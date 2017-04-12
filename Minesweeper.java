import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import objectdraw.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * Display the game in this class
 * 
 * Ching Ching Huang 
 * 11/28/2016
 */
public class Minesweeper extends WindowController implements ActionListener, MouseListener{
    private JPanel bigPan, timerPan;//panel for mine count and new game button
    private Grid grid; //the grid
    private GridCell gridCell; //one cell
    private int gridSize = 450; //the size of the grid
    private int space = 20; //the spaces
    private int flagCount;//the number of the cell that's flagged
    private JLabel mineInfo = new JLabel("Mines Found: " + flagCount + "/10");//info of mine found
    private JButton newGameButton = new JButton ("New Game"); // new game button
    public void begin(){
        canvas.addMouseListener(this);
        setSize(gridSize + space*2, gridSize + space*8);//reset the size of the canvas
        panel();//show the button and mine count
        add(bigPan, BorderLayout.SOUTH);//palce panel           
        newGame();//show the game 
    }

    public JPanel panel(){
        bigPan = new JPanel();        
        JPanel infoPan = new JPanel();//panel for the button and mine count        

        newGameButton.addActionListener (this);//to create a new game when user clicks        
        infoPan.add(newGameButton);//add button to the panel         
        infoPan.add(mineInfo);//add text on the panel                

        infoPan.setLayout(new BoxLayout(infoPan, BoxLayout.Y_AXIS));//make them vertical
        bigPan.add(infoPan);//put them in the center of the panel
        return bigPan;
    }    

    public void mousePressed(MouseEvent event){
        Location loc = new Location (event.getX(), event.getY());//the location
        if(event.getButton() == MouseEvent.BUTTON3){
            grid.flagCells(loc);
            flagCount = grid.getFlagCount();//update the flag count
            mineInfo.setText("Mines Found: " + flagCount + "/10");//print it out
            grid.winTheGame();
        }else if(event.getButton() == MouseEvent.BUTTON1){
            grid.checkGrid(loc);
            flagCount = grid.getFlagCount();//update the flag count
            mineInfo.setText("Mines Found: " + flagCount + "/10"); //print it out
            grid.winTheGame();
        }
    }

    public void newGame(){                
        grid = new Grid(space, space, gridSize, gridSize, canvas);//the grid
        grid.placeAllMines(); //show the mines that have been placed 
        mineInfo.setText("Mines Found: " + flagCount + "/10");//set the count back to 0;        
    }

    public void actionPerformed (ActionEvent event){
        newGame();//show the new game 
    }

    public void mouseClicked(MouseEvent event){
    }    

    public void mouseReleased(MouseEvent event){    
    }

    public void mouseEntered(MouseEvent event){
    }

    public void mouseExited(MouseEvent event){
    }
}