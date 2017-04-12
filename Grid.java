import objectdraw.*;
import java.awt.*;
/**
 * Write the game logic in this class.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Grid 
{
    private GridCell[][] gridArray = new GridCell[10][10];//10*10 grid
    private DrawingCanvas canvas; //the canvas
    private int clickedCells = 0;
    private int[] mineArray = new int[10];//the mine array
    private int flagCount;
    public Grid(double x, double y, double width, double height, DrawingCanvas canvas){
        this.canvas = canvas;
        double cellWdith = width/10.0; //the width of one cell
        double cellHeight = height/10.0; //the height of one cell
        //create the grid
        double curY = y; //the starting x location       
        double curX = x; //the starting y locstion
        for(int i = 0; i < gridArray.length; i ++){
            for (int j = 0; j < gridArray[i].length; j ++){
                //contructing 10*10 gird
                gridArray[i][j] = new GridCell (curX + i*cellWdith, curY + j*cellHeight, 
                    cellWdith, cellHeight, canvas);
            }
        }
        //create the lines
        for(int n = 1; n < gridArray.length; n ++){
            //vertical line
            new Line(curX + n*cellWdith, y, curX + n*cellWdith, y + height, canvas);
            //horizontal line
            new Line(x, curY + n*cellHeight, x + width, curY + n*cellHeight, canvas);
        }        
    }

    public void placeAllMines(){
        placeMine();//place the mines on the grid
        //show ten mines on the grid
        for(int row = 0; row < gridArray.length; row ++){
            for(int col = 0; col < gridArray[row].length; col ++){
                if(gridArray[row][col].isMine()){
                    gridArray[row][col].setAMine();//show the mine
                }
            }
        }           
        checkMineOnGrid();
    }

    public void placeMine(){
        //add ten mines on grid
        for (int i = 0; i < mineArray.length; i ++){
            addOneMine();  //set one mine ten times
        }
    }
    //add one mine on the grid 
    public void addOneMine(){        
        //generate one random cell
        GridCell curCell;
        RandomIntGenerator randomMineGen = new RandomIntGenerator (0, 9);
        int row, col;
        do{  
            row = randomMineGen.nextValue();//the row
            col = randomMineGen.nextValue();//the col
            curCell = gridArray[row][col];
        }while(curCell.isMine());        
        curCell.setAMine();//put the mine on the cell
    }

    public int neighborMines(int row, int col, GridCell selected){
        int rowStart = 0;
        int colStart = 0;
        int neighbor = 0;
        if(row > 0){
            //if it's not the first row, go up one row
            rowStart = row -1;
        }
        if(col > 0){
            //if it's not the first col, go left one row
            colStart = col - 1;
        }
        //use for loop to check boundary and check eight neighbors
        for(int i = rowStart; i <= row + 1 && i < gridArray.length; i++){
            for(int k = colStart; k <= col + 1 && k < gridArray[i].length; k++){
                if(gridArray[i][k].isMine() && gridArray[i][k] != gridArray[row][col]){
                    neighbor++;
                }
            }
        }
        return neighbor;//return the neighbor mine number
    }

    public void checkMineOnGrid(){
        //check every cell on the grid
        for(int row = 0; row < gridArray.length; row ++){
            for(int col = 0; col < gridArray[row].length; col ++){  
                int x = neighborMines(row, col, gridArray[row][col]);
                gridArray[row][col].setNeighbor(x);
            }
        }
    }

    public void placeMineNum(){
        //show ten mines on the grid
        int  neighbor = 0;
        for(int row = 0; row < gridArray.length; row ++){
            for(int col = 0; col < gridArray[row].length; col ++){
                neighbor = neighborMines(row, col, gridArray[row][col]);
                if(neighbor != 0 && gridArray[row][col].isMine() == false){
                    //don't show 0 and if the cell has mine don't show the number
                    gridArray[row][col].mineAround(neighbor);
                }
            }
        }        
    }

    public void checkGrid(Location loc){
        placeMineNum();
        for(int row = 0; row < gridArray.length; row++){
            for(int col = 0; col < gridArray[row].length; col ++){
                if(gridArray[row][col].exposeCell() == false){
                    if(gridArray[row][col].contains(loc)){                    
                        if (gridArray[row][col].isMine() == true){
                            //if the cell is mine
                            gridArray[row][col].explodeAMine();//explode the mine
                            loseGame();//show lost the game
                            showAllMines();//show all the mines
                        }else if (gridArray[row][col].getNei() != 0){
                            //if the cell has number but not 0
                            gridArray[row][col].showTheCount();//show the count
                            clickedCells ++;//exposed cell plus one
                        }else{
                            //if the cell has no mine and it's 0
                            gridArray[row][col].showCell();//change the background
                            clickedCells ++;//exposed cell plus one
                        }
                    }
                }
            }
        }
    }

    public void showAllMines(){
        //show every mine
        for(int row = 0; row < gridArray.length; row ++){
            for(int col = 0; col < gridArray[row].length; col ++){
                if(gridArray[row][col].isMine() == true){//go through every cell to chaeck if it has mine
                    gridArray[row][col].showAMine();//if the cell has mine, show the rest of the mines
                }
            }
        }       
    }

    public void loseGame(){
        //the text of losing the game
        Text result = new Text("YOU LOSE", 0, 0, canvas);
        result.setFontSize(80);//the size
        result.setColor(Color.ORANGE);//the color of the text
        double xLoc = canvas.getWidth()/2 - result.getWidth()/2;//the X location
        double yLoc = canvas.getHeight()/2 - result.getHeight()/2;//the Y location
        result.moveTo(xLoc, yLoc);//move to the right location
    }

    public void winGame(){
        //the text of winning the game
        Text result = new Text("YOU WIN", 0, 0, canvas);
        result.setFontSize(80);//the size
        result.setColor(Color.YELLOW);//the color of the text
        double xLoc = canvas.getWidth()/2 - result.getWidth()/2;//the X location
        double yLoc = canvas.getHeight()/2 - result.getHeight()/2;//the Y location
        result.moveTo(xLoc, yLoc);//move to the right location
    }

    public void flagCells(Location loc){
        for(int row = 0; row < gridArray.length; row ++){
            for(int col = 0; col < gridArray[row].length; col ++){
                if(gridArray[row][col].exposeCell() == false){//to check if the cell is exposed
                    if(gridArray[row][col].contains(loc)){//to check if the cell is clicked
                        gridArray[row][col].setAFlag(); //flage the cell                       
                    }
                }
            }
        }    
    }

    public int getFlagCount(){
        int flagCount = 0;
        for(int row = 0; row < gridArray.length; row ++){
            for(int col = 0; col < gridArray[row].length; col ++){
                if(gridArray[row][col].isAFlag() == true){//if the cell is flagged
                    flagCount ++; //+1
                }
            }
        }       
        return flagCount;
    }

    public void winTheGame(){
        int minesFound = 0;
        for(int row = 0; row < gridArray.length; row ++){
            for(int col = 0; col < gridArray[row].length; col ++){               
                if(gridArray[row][col].isAFlag() == true && gridArray[row][col].isMine() == true){  
                    //if the cell is mine and is flagged
                    minesFound++;//+1
                }
                if(clickedCells == gridArray.length*gridArray.length - mineArray.length ){ 
                    //if all the cells are exposed except the mines
                    if(minesFound == mineArray.length){
                        //if the flagged mines are the actual mines
                        winGame();//then win the game
                    }
                }
            }
        }    
    }
}