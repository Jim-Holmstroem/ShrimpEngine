import javax.microedition.lcdui.*;

public class CommercialBreak {
    public int timer = 0;
    private Image comImage; 
    private BarGraph bargraphInit = null;
    private Shrimp evilShrimp = null;
    private Game game = null;
  //  private ReadWrite comProgress = new ReadWrite("comProgress"); //<--lägg till den sen :P
    
    
    private final static int LIMIT = 200; 
    
    
    private int hexCounter;
    
    //ändra så att man måste skicka med rW variabeln ist, eller om man inte gör det så funkar inte just den funktionen
    
    public CommercialBreak(Game theGame) {
    game = theGame;
    
    comImage = game.gE.loadImage("logo.png");//utifall den inte hamnar precis där den ska från början, så blir det inget fel.    
    
    bargraphInit = new BarGraph();
    
     evilShrimp = new Shrimp(game.gE.loadImage("shrimp.png"),15,15);
     evilShrimp.freeFalling = false;
     evilShrimp.x = 10.0f;
     evilShrimp.y = 10.0f;
     evilShrimp.animSpeed = 20;
   
     
    }
    
    public void draw(int duration){

    duration  = duration * 25;
    
    hexCounter = duration;
    
    
    bargraphInit.width = game.width-20;
    bargraphInit.height = 20;
    
    bargraphInit.y = game.height-bargraphInit.height-35;
    bargraphInit.x = game.width-bargraphInit.width-10;
 
    bargraphInit.max = duration;
    
     evilShrimp.x = game.width-bargraphInit.width-10 + 10;
     evilShrimp.y = game.height-bargraphInit.height-35 + 10;
    
   for(int i = 0;i<duration;i++){
        switch(timer){
            case 0:
                comImage = game.gE.loadImage("reklamhar.png");    
            break;
            case 1*LIMIT/8:
                comImage = game.gE.loadImage("logo.png");
            break;
            case 2*LIMIT/8:
                comImage = game.gE.loadImage("reklamhar.png");
            break;
            case 3*LIMIT/8:
                comImage = game.gE.loadImage("logo.png");
            break;
            case 4*LIMIT/8:
                comImage = game.gE.loadImage("reklamhar.png");
            break;
            case 5*LIMIT/8:
                comImage = game.gE.loadImage("logo.png");
            break;
            case 6*LIMIT/8:
                comImage = game.gE.loadImage("reklamhar.png");
            break;
            case 7*LIMIT/8:
                comImage = game.gE.loadImage("logo.png");
            break;
            default:
            break;
        }
        
    timer++;
    timer%=LIMIT;
        
    game.gE.update(evilShrimp);
    
    game.gE.clear();
    game.gE.drawImage(comImage,game.width/2-comImage.getWidth()/2,game.height/2-comImage.getHeight()/2-35);
    bargraphInit.value = i;
    bargraphInit.draw(game.g);
    
    if(100*hexCounter<16){
        game.g.drawString("0x00000" + Integer.toHexString(100*hexCounter--) + "h",0,0,game.g.TOP|game.g.LEFT);
    }else if(100*hexCounter<16*16){
        game.g.drawString("0x0000" + Integer.toHexString(100*hexCounter--) + "h",0,0,game.g.TOP|game.g.LEFT);
    }else if(100*hexCounter<16*16*16){
        game.g.drawString("0x000" + Integer.toHexString(100*hexCounter--) + "h",0,0,game.g.TOP|game.g.LEFT);
    }else if(100*hexCounter<16*16*16*16){
        game.g.drawString("0x00" + Integer.toHexString(100*hexCounter--) + "h",0,0,game.g.TOP|game.g.LEFT);
    }else{
        game.g.drawString("0x0" + Integer.toHexString(100*hexCounter--) + "h",0,0,game.g.TOP|game.g.LEFT);
    }
    
    game.gE.displayRoller();
    
    evilShrimp.paint(game.g);
    
    game.flushGraphics();
    }

  }

}
