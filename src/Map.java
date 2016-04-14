/**
 *
 * @author SlimJim
 */
import javax.microedition.lcdui.*;

//Ska man använda sig av den hära`???



public class Map {
    public static float x = 0.0f,y = 0.0f; //är en och samma oavsätt hur många mappar du har !!!
    public int tileHeight, tileWidth;
    public int row,col;
    
    
    public int[][] array = null;

    public ShrimpList tiles = new ShrimpList();
    private Game game = null;

    
    public Map(Game game, Image tileImage,int[][] array, int tileHeight, int tileWidth, int row, int col) {
        this.game = game;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.row = row;
        this.col = col;
        this.array = new int[col][row];
        
        this.array = array;

        for(int x = 0;x<col;x++){
            for(int y = 0;y<row;y++){
                if(array[x][y]!=-1){//om det ska ritas något
                    Shrimp tile = new Shrimp(tileImage, tileHeight, tileWidth);//gör en ny tilebit
                    tile.setFrame(array[x][y]); //sätt vilken frame dt ska vara
                    tile.x = tileWidth * y;tile.y = tileHeight * x;//sätt positionen //varför är dom bakvända???
                    tile.isTile = true;
                    tiles.addElement(tile);
                }
            }
        }
        
        
        
    }
    
    public void draw(){
       game.gE.paint(tiles);
      
    }
     public void debugDraw(Graphics g){
       tiles.paintDebug(g);
    }
 
}
