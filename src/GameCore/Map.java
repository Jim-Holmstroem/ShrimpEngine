/**
 *
 * @author SlimJim
 */
import javax.microedition.lcdui.*;

//Ska man anv�nda sig av den h�ra`???



public class Map {
    public static float x = 0.0f,y = 0.0f; //�r en och samma oavs�tt hur m�nga mappar du har !!!
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
                if(array[x][y]!=-1){//om det ska ritas n�got
                    Shrimp tile = new Shrimp(tileImage, tileHeight, tileWidth);//g�r en ny tilebit
                    tile.setFrame(array[x][y]); //s�tt vilken frame dt ska vara
                    tile.x = tileWidth * y;tile.y = tileHeight * x;//s�tt positionen //varf�r �r dom bakv�nda???
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
