import objectdraw.*;
import java.awt.*;
/**
 * Construct one cell and modify one cell
 * 
 * Ching Ching Huang
 * 11/28/2016
 */
public class GridCell
{
    private double x; //the top location
    private double y;  //the top location
    private double width; //the width of one cell
    private double height; //the height of one cell
    private DrawingCanvas canvas; //the canvas
    private FilledRect cell; //the cell   
    private FilledOval oneMine; //the mine
    private boolean isMine = false; //check if the cell has mine 
    private Text mineNum; //the neighbor number
    private int neighbor; //the neughbors
    private boolean isFlag = false; //check if the cell is flagged
    private Color blueGray = new Color(130, 150, 200);//color of the cell
    private boolean expose = false;
    public GridCell(double x, double y, double width, double height, DrawingCanvas canvas){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.canvas = canvas;
        cell = new FilledRect(x, y, width, height, canvas); //one single cell
        cell.setColor(blueGray);//cahge the color of the cell
        neighbor = 0; //cell start with 0 neighbor
        expose = false;
    }

    public boolean isMine(){
        //check this cell has mine or not
        return isMine;    
    }

    public void setAMine(){
        isMine = true;
        //if this cell has mine, then put a mine in the cell
        if (isMine == true){
            //the location of the mine in the cell
            double mineSize = width/2.0;
            double mineX = x + width/2.0 - mineSize/2.0;
            double mineY = y + height/2.0 - mineSize/2.0;
            //the mine
            oneMine = new FilledOval (mineX, mineY, mineSize, mineSize, canvas);
            oneMine.hide();//hide the mine
        }
    }

    public void mineAround(int mineCount){
        neighbor = mineCount;
        mineNum = new Text(mineCount, 0, 0, canvas);
        mineNum.setFontSize(25);//increase the size of the number
        double xLoc = x + width/2 - mineNum.getWidth()/2; 
        double yLoc = y + height/2 - mineNum.getHeight()/2;
        mineNum.moveTo(xLoc, yLoc);     
        mineNum.hide();//hide the numbers
    }

    public void explodeAMine(){ 
        expose = true;
        oneMine.show(); //show the mine  
        cell.setColor(Color.RED); //set the background of the cell when the user clicks
    }

    public void showAMine(){
        oneMine.show();//show the mine
    }
    
    public void showTheCount(){
        expose = true;
        mineNum.show(); //show the number
        getNei(); //get the number 
        cell.setColor(Color.WHITE); //set the background
        isFlag = false; //it's not flagged
    }

    public boolean contains(Location pt){
        return cell.contains(pt);
        //check if the cell is clicked
    }

    public void setNeighbor(int num){
        neighbor = num;//keep track with numbers on each cell
    }

    public int getNei(){
        return neighbor; //get the number from each cell
    }

    public void showCell(){
        cell.setColor(Color.WHITE);//change the background to white
        isFlag = false; //not flagged
        expose = true;
    }

    public void setAFlag(){
        if(isFlag == true){//when the cell is flagged
           isFlag = false;//another click unflag the cell
           cell.setColor(blueGray); //change the background back
        }else if(isFlag != true){//when the cell is unflagged
            isFlag = true; //another click flag the cell
            cell.setColor(Color.BLUE); //set the background of the cell when the user flags the cell
        }               
    }

    public boolean isAFlag(){
        //check the cell is flagged or not
        return isFlag; 
    }
    
    public boolean exposeCell(){
       return expose;//check if the cell is exposed
    }
}