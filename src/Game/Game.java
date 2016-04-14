package Game;

import javax.microedition.lcdui.*;//rita p sk�rmen mm, bibl.
import javax.microedition.lcdui.game.*;//spel-bibl.

//Shrimp-packages
import Core.Shrimplet;
import Core.SWindowHandler;
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

import Math.Math;
import Math.Vector3f;



/* saker som b�r g�ras :
 * -> enklare att skapa menyer
 * -> transparence vid zoomning
 * -> softkeys i menyn, site:http://www.j2meforums.com/forum/index.php?topic=5147.0
 * -> spara d�r man �r (alla object som beh�ver sparas m�ste kunna g�ra dom viktiga variablerna till byteBuffer och sedan l�sas av + att man m�ste ha ett system f�r hur man l�ser av flera i en lista...)

 
 */

//------------------------------------------------------------------------------
public class Game extends SWindow implements Runnable{
//------------------------------------------------------------------------------     
    //avancerade saker
    public GameEngine gE = new GameEngine(); //spelmotorn
    public Graphics g = getGraphics(); //h�mntar graphics'n
    public Thread thread;        //tr�den
    
    //h�jd bredd
    public int height = 0,
                width = 0; //public h�jd/bredd p sk�rmen

   
    SMenu me;
    
    public int tester = 0;
    public boolean flasher = false;
    
//------------------------------------------------------------------------------
 public Game() {//konstruktorn
    super(); //st�nger av h�ndelse-hanteringen p knapparna //�rver fr�n gamecanvas  <--ska man ha knapp h�ndelse hantering?? kanske funkar mkt b�ttre �n utan : P
 }
//------------------------------------------------------------------------------
 public void run() {//tr�den b�rjar k�ras h�r
    
 }
//------------------------------------------------------------------------------
 public void init(){//allt som ska laddas in fr�n b�rjan, laddas h�r...
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

     add(aimg);
     add(img);
     add(sb);
     add(lbl);
     add(me);
        
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

    paint(g);
    
    System.out.println("asking");
    
    ask("Continue without saving?",new String[]{"Yes","No","Maybe"});
    
    
    if(flasher){
        g.setColor(0xFF00FFFF);
        flasher = false;
   
    }else{
        g.setColor(0xFFFF00FF);
        flasher = true;
    }
    SGraphics.fillRect(0.9f, 0.9f, 0.1f, 0.1f);
    
  }  
 //------------------------------------------------------------------------------
 private void CheckCollision(){//tittar om n�gon av spritsen kolliderar
    
 }
 //------------------------------------------------------------------------------
 private void Physics(){//updaterar fysiken i spelet
    
 }
 //------------------------------------------------------------------------------       
 private void restart(){//restarts level

 }
 

}