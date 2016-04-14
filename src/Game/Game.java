package Game;

import javax.microedition.lcdui.*;//rita p skärmen mm, bibl.
import javax.microedition.lcdui.game.*;//spel-bibl.

//Shrimp-packages
import Core.Shrimplet;
import Core.GameEngine;




import GUI.SImage;
import GUI.SLabel;
import GUI.StatusBar;
import SGraphics.GUIObject;
import SGraphics.ReSizer;
import SGraphics.SGraphics;
import Core.SWindow;


/* saker som bör göras :
 * -> enklare att skapa menyer
 * -> transparence vid zoomning
 * -> softkeys i menyn, site:http://www.j2meforums.com/forum/index.php?topic=5147.0
 * -> spara där man är (alla object som behöver sparas måste kunna göra dom viktiga variablerna till byteBuffer och sedan läsas av + att man måste ha ett system för hur man läser av flera i en lista...)

 
 */

//------------------------------------------------------------------------------
public class Game extends GameCanvas implements Runnable{
//------------------------------------------------------------------------------     
    //avancerade saker
    public Shrimplet parent;
    public GameEngine gE = new GameEngine(); //spelmotorn
    public Graphics g = getGraphics(); //hämntar graphics'n
    public Thread thread;        //tråden
    
    //höjd bredd
    public int height = 0,
                width = 0; //public höjd/bredd p skärmen

    
    
    public SWindow win = new SWindow();
    
    
//------------------------------------------------------------------------------
 public Game(Shrimplet parent) {//kostruktorn
    super(true); //stänger av händelse-hanteringen p knapparna //ärver från gamecanvas  <--ska man ha knapp händelse hantering?? kanske funkar mkt bättre än utan : P
    this.parent = parent; //hämtar midleten
    setFullScreenMode(true); //sätter full-skärm
    thread = new Thread(this); //skapar en ny tråd
    thread.start(); //startar tråden
 }
//------------------------------------------------------------------------------
 public void run() {//tråden börjar köras här
    init(); //kör initsieringen ingenting före den om det inte måste, kontrollera flera gånger om så är faller            
    while(true){
        draw();
        System.gc();//<--rensar skräp //finns dt några nackdelar???(processorkraft?)
        flushGraphics(); //målar buffern p skärmen
        try {
            Thread.sleep(10);
        } catch (Exception e) {
        }
    }
 }
//------------------------------------------------------------------------------
 private void init(){//allt som ska laddas in från början, laddas här...
     //pre-init<--- dt hära ska ligga i spelhanteraren sen.. den som tar hand om alla SWindows
     ReSizer.getScreenResolution(getWidth(), getHeight());
     GUIObject.setGraphics(g);
     SGraphics.setGraphics(g);
     
     //init
     
     SImage img = new SImage("logo.png");
     SLabel lbl = new SLabel();double w=0;
     StatusBar sb = new StatusBar();
     
     
     sb.setPosition(0.15f, 0.8f);
     sb.setDimension(0.25f, 0.02f);
     sb.setSteps(1000);
     sb.setValue(0);
     sb.mark();
     
     lbl.setColor(0xFFF00FFF);
     lbl.mark();
     lbl.setPosition(0.0f, 0.0f);
     lbl.setMaxWidth(1.0f);
     lbl.setText("Bobbo babian bubbar blåa bollar, men inte gröna eftersom han är färgblind..getter äter gräs, ibland iaf");
     lbl.recalc();
     
     img.mark();
     img.setPosition(0.3f, 0.3f);
     
     win.add(img);
     win.add(sb);
     win.add(lbl);
     
     
     restart();
}  
//------------------------------------------------------------------------------
 public void handleInput() {//hanterar knapptryckningar
            
 }
 //------------------------------------------------------------------------------
 public void updateObjects() {//updaterar alla objecten    
            CheckCollision();//tittar vilka bollar som kolliderar
            Physics();//fysiken
 }
 //------------------------------------------------------------------------------
  public void draw() {//ritar ut allt
    //Clear screen
    SGraphics.setColor(0xFF000000);
    SGraphics.fillRect(0.0f, 0.0f, 1.0f, 1.0f);  
    
    win.paint(g);
      try {
          Thread.sleep(10);
      } catch (Exception e) {
      }
    
  }  
 //------------------------------------------------------------------------------
 private void CheckCollision(){//tittar om någon av spritsen kolliderar
    
 }

 //------------------------------------------------------------------------------
 private void Physics(){//updaterar fysiken i spelet
    
 }

//---[meny]---------------------------------------------------------------------  
 public void menuInput(){
   
 }
 //------------------------------------------------------------------------------
 public void menuUpdate(){//sätter vilka som inte är tillgängliga lr focuserade lr inte

 }
 //------------------------------------------------------------------------------
 public void menuDraw(){//ritar ut allt i menyn; logon, meny mm...   

 }  
 //------------------------------------------------------------------------------       
 private void restart(){//restarts level

 }


}