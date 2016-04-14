package Game;

import javax.microedition.lcdui.*;//rita p sk�rmen mm, bibl.
import javax.microedition.lcdui.game.*;//spel-bibl.

//Shrimp-packages
import Core.Shrimplet;
import GameCore.GameEngine;

import GUI.SImage;
import GUI.SAnimatedImage;
import GUI.SAnimation;
import GUI.SLabel;
import GUI.StatusBar;
import GUI.GUIObject;

import GUI.Menu.SMenuObject;
import GUI.Menu.SMenu;

import SGraphics.ReSizer;
import SGraphics.SGraphics;
import Core.SWindow;


/* saker som b�r g�ras :
 * -> enklare att skapa menyer
 * -> transparence vid zoomning
 * -> softkeys i menyn, site:http://www.j2meforums.com/forum/index.php?topic=5147.0
 * -> spara d�r man �r (alla object som beh�ver sparas m�ste kunna g�ra dom viktiga variablerna till byteBuffer och sedan l�sas av + att man m�ste ha ett system f�r hur man l�ser av flera i en lista...)

 
 */

//------------------------------------------------------------------------------
public class Game extends GameCanvas implements Runnable{
//------------------------------------------------------------------------------     
    //avancerade saker
    public Shrimplet parent;
    public GameEngine gE = new GameEngine(); //spelmotorn
    public Graphics g = getGraphics(); //h�mntar graphics'n
    public Thread thread;        //tr�den
    
    //h�jd bredd
    public int height = 0,
                width = 0; //public h�jd/bredd p sk�rmen

    public SWindow win = new SWindow();
    
    public SWindow win2 = new SWindow();
    
    SMenu me;
    
    public int tester = 0;
    public boolean flasher = false;
    
//------------------------------------------------------------------------------
 public Game(Shrimplet parent) {//kostruktorn
    super(true); //st�nger av h�ndelse-hanteringen p knapparna //�rver fr�n gamecanvas  <--ska man ha knapp h�ndelse hantering?? kanske funkar mkt b�ttre �n utan : P
    this.parent = parent; //h�mtar midleten
    setFullScreenMode(true); //s�tter full-sk�rm
    thread = new Thread(this); //skapar en ny tr�d
    thread.start(); //startar tr�den
 }
//------------------------------------------------------------------------------
 public void run() {//tr�den b�rjar k�ras h�r
    init(); //k�r initsieringen ingenting f�re den om det inte m�ste, kontrollera flera g�nger om s� �r faller            
    while(true){
        draw();
        flushGraphics(); //m�lar buffern p sk�rmen
        System.gc();//<--rensar skr�p //finns dt n�gra nackdelar???(processorkraft?)
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
    }
 }
//------------------------------------------------------------------------------
 private void init(){//allt som ska laddas in fr�n b�rjan, laddas h�r...
     //pre-init<--- dt h�ra ska ligga i spelhanteraren sen.. den som tar hand om alla SWindows
     ReSizer.getScreenResolution(getWidth(), getHeight());
     GUIObject.setGraphics(g);
     SGraphics.setGraphics(g);
     
     //init
     SImage img = new SImage("logo.png");
     SLabel lbl = new SLabel();double w=0;
     StatusBar sb = new StatusBar();
     SAnimatedImage aimg = new SAnimatedImage("menu.png",5,3);
     
     me = new SMenu("menu.png",
         new SMenuObject[]{
                           new SMenuObject(5,3,new SAnimation(new int[]{ 0, 1, 2},1)),
                           new SMenuObject(5,3,new SAnimation(new int[]{ 3, 4, 5},1)),
                           new SMenuObject(5,3,new SAnimation(new int[]{ 6, 7, 8},1)),
                           new SMenuObject(5,3,new SAnimation(new int[]{ 9,10,11},1)),
                           new SMenuObject(5,3,new SAnimation(new int[]{12,13,14},1))
                          }
     );
     
     me.setPosition(0.50f, 0.50f);
     
     sb.setPosition(0.15f, 0.8f);
     sb.setDimension(0.25f, 0.02f);
     sb.setSteps(1000);
     sb.setValue(0);
     sb.mark();
     
     lbl.setColor(0xFFF00FFF);
     lbl.mark();
     lbl.setPosition(0.0f, 0.0f);
     lbl.setMaxWidth(1.0f);
     lbl.setText("Bobbo babian bubbar bl�a bollar, men inte gr�na eftersom han �r f�rgblind..getter �ter gr�s, ibland iaf");
     lbl.recalc();
     
     img.setPosition(0.3f, 0.3f);
     
     SAnimation anim = new SAnimation(new int[]{1,4,7,10,7,4},5);
     
     aimg.setPosition(0.01f, 0.01f);
     aimg.mark();
     aimg.addAnimation(anim);
     aimg.setAnimation(0);
     aimg.setAnimitionType(SAnimatedImage.ANIMATIONS);

     win.add(aimg);
     win.add(img);
     win.add(sb);
     win.add(lbl);
     win.add(me);
     
     win2.add(me);
        
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

    
    me.select(tester++);

    
    if(flasher){
        g.setColor(0xFF00FFFF);
        flasher = false;
    win.paint(g);
   
    }else{
        g.setColor(0xFFFF00FF);
        flasher = true;
    win2.paint(g);
    }
    
    g.fillRect(200, 200, 10, 10);
    
  }  
 //------------------------------------------------------------------------------
 private void CheckCollision(){//tittar om n�gon av spritsen kolliderar
    
 }

 //------------------------------------------------------------------------------
 private void Physics(){//updaterar fysiken i spelet
    
 }

//---[meny]---------------------------------------------------------------------  
 public void menuInput(){
   
 }
 //------------------------------------------------------------------------------
 public void menuUpdate(){//s�tter vilka som inte �r tillg�ngliga lr focuserade lr inte

 }
 //------------------------------------------------------------------------------
 public void menuDraw(){//ritar ut allt i menyn; logon, meny mm...   

 }  
 //------------------------------------------------------------------------------       
 private void restart(){//restarts level

 }
}