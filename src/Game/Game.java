package Game;

import javax.microedition.lcdui.*;//rita p skärmen mm, bibl.
import javax.microedition.lcdui.game.*;//spel-bibl.

import java.util.Random;

//Shrimp-packages
import Core.Shrimplet;
import Core.SWindowHandler;
import GameCore.GameEngine;

import GUI.SImage;
import GUI.SImage2;
import GUI.SAnimatedImage;
import GUI.SAnimation;
import GUI.SLabel;
import GUI.StatusBar;
import GUI.GUIObject;

import GUI.Menu.SMenuObject;
import GUI.Menu.SMenu;

import SGraphics.ReSizer;
import SGraphics.SGraphics;
import SGraphics.SColor;
import Core.SWindow;

import Math.Math;
import Math.Vector2f;
import Math.Vector3f;



/* saker som bör göras :
 * -> enklare att skapa menyer
 * -> transparence vid zoomning
 * -> softkeys i menyn, site:http://www.j2meforums.com/forum/index.php?topic=5147.0
 * -> spara där man är (alla object som behöver sparas måste kunna göra dom viktiga variablerna till byteBuffer och sedan läsas av + att man måste ha ett system för hur man läser av flera i en lista...)

 
 */

//------------------------------------------------------------------------------
public class Game extends SWindow implements Runnable{
//------------------------------------------------------------------------------     
    //avancerade saker
    public GameEngine gE = new GameEngine(); //spelmotorn
    public Graphics g = getGraphics(); //hämntar graphics'n
    public Thread thread;        //tråden
    
    //höjd bredd
    public int height = 0,
                width = 0; //public höjd/bredd p skärmen

   
    SMenu me;
    
    public int tester = 0;
    public boolean flasher = false;
    public float testWidthAng = 0;
    public int[] imgdata;
    
    SLabel lbl = new SLabel();double w=0;
    SImage img;
    SImage2 img2;
    
    
//------------------------------------------------------------------------------
 public Game() {//konstruktorn
    super(); //stänger av händelse-hanteringen p knapparna //ärver från gamecanvas  <--ska man ha knapp händelse hantering?? kanske funkar mkt bättre än utan : P
 }
//------------------------------------------------------------------------------
 public void run() {//tråden börjar köras här
    
 }
//------------------------------------------------------------------------------
 public void init(){//allt som ska laddas in från början, laddas här...
     //pre-init<--- dt hära ska ligga i spelhanteraren sen.. den som tar hand om alla SWindows
     ReSizer.getScreenResolution(getWidth(), getHeight());
     GUIObject.setGraphics(g);
     SGraphics.setGraphics(g);
     
     
     //init
     
     img2 = new SImage2("menu.png");
     img2.setPosition(0.0f, 0.35f);
     img2.mark();
     
     img = new SImage("menu.png");     
     img.setPosition(0.0f, 0.0f);
     img.mark();
    /*
     imgdata = new int[img.getHeight()*img.getWidth()];
     try {
        img.getImage().getRGB(imgdata, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());    
     } catch (Exception e) {
         e.printStackTrace();
     }
    */ 
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
     lbl.setCutWords(false);
     lbl.setText("Bobbo babian bubbar blåa bollar, men inte gröna eftersom han är färgblind.. getter äter gräs, ibland iaf");
     lbl.recalc();
          
     SAnimation anim = new SAnimation(new int[]{1,4,7,10,7,4},5);
        
     aimg.setPosition(0.01f, 0.01f);
     aimg.mark();
     aimg.addAnimation(anim);
     aimg.setAnimation(0);
     aimg.setAnimitionType(SAnimatedImage.ANIMATIONS);

     Random rnd = new Random();
     
     for (int i = 0; i < 10; i++) {
         
         SColor a = new SColor(rnd.nextInt());
         SColor b = new SColor(rnd.nextInt());
         SColor c = new SColor(rnd.nextInt());
         SColor d = new SColor(rnd.nextInt());
         
         
       System.out.println(a + "&" + b + "&" + c + "&" + d + "=" + SColor.bilinearmix(a, b, c, d));
     //  System.out.println(new SColor(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
     }
     
//     add(aimg);
    add(img);
//     add(sb);
//     add(lbl);
//     add(me);
        add(img2);
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

    if(flasher){g.setColor(0xFF00FFFF);flasher = false;}else{g.setColor(0xFFFF00FF);flasher = true;}    
    SGraphics.fillRect((float)java.lang.Math.abs(0.9f*Math.cos((float)(w+=0.01d))-0.1f), (float)java.lang.Math.abs(0.9f*Math.cos((float)(w))-0.1f), 0.1f, 0.1f);
       
     paint(g);
    
    //int[] rgbrandomdata = new int[this.getHeight()*this.getWidth()];
    //Random rnd = new Random();
    
   // for(int i = 0;i<rgbrandomdata.length;i++){
     //   rgbrandomdata[i] = rnd.nextInt();
  //  }
    
    //g.drawRGB(rgbrandomdata,0,this.getWidth(),0,0,this.getWidth(),this.getHeight(),true);
    
   // int[] invimgdata = new int[imgdata.length];
    
   //   for (int i = 0; i < invimgdata.length; i++) {
   //       invimgdata[i] = imgdata[imgdata.length - i - 1];
   //   }
    
    
   // g.drawRGB(imgdata, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight(), true);
    
    
    //ask("Continue without saving?",new String[]{"Yes","No","Maybe"});
   
    lbl.setMaxWidth(0.3f + (float)java.lang.Math.abs(0.7f*Math.cos((float)(w+=0.01d))));
    lbl.recalc();
    
  
    
    System.gc();
  
  }  
 //------------------------------------------------------------------------------
 private void CheckCollision(){//tittar om någon av spritsen kolliderar
 }
 //------------------------------------------------------------------------------
 private void Physics(){//updaterar fysiken i spelet
 }
 //------------------------------------------------------------------------------       
 private void restart(){//restarts level
 }
}