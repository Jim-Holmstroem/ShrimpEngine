package Game;

import javax.microedition.lcdui.*;//rita p sk�rmen mm, bibl.
import javax.microedition.lcdui.game.*;//spel-bibl.
import javax.microedition.lcdui.game.Sprite;//sprites bibl.
import java.util.Vector;//vector-arrayer
import java.lang.Math;//matte-bibl.
import javax.microedition.media.*;//ljud
import java.io.*;//l�s och skriv

//Shrimp-packages
import GUI.*;
import SIO.ReadWrite;
import GameCore.Shrimp;
import GameCore.ShrimpList;
import GameCore.GameEngine;
import Core.Shrimplet;



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
    public GameEngine gE = new GameEngine(); //spelmotorn
    public Graphics g = getGraphics(); //h�mntar graphics'n
    public Shrimplet parent;
    public Thread thread;        //tr�den
    public int graphicSetting;
    
    private MenuList menu = new MenuList("menu.png",100,20);
    private CommercialBreak cB = new CommercialBreak(this); //Commercialbreak R bortagen fr�n compileringen
    
    Image pearlImg = gE.loadImage("pearl.png");
    Image appleImg = gE.loadImage("shrimplogo.png");
    
    ShrimpList snake = new ShrimpList();
    ShrimpList fruits = new ShrimpList();
    
    //h�jd bredd
    public int height = 0,
                width = 0; //public h�jd/bredd p sk�rmen

    //-----------------player-----------------------------------
    private long highscore;
    private ReadWrite hs = new ReadWrite("highscore");
    
    private long[] test = new long[5];//highscore lista med 5st highscore
    
//------------------------------------------------------------------------------
    
    Image logo = null;
    
    //vilket h�ll masken R p v�g �t
    public int dirSnake = RIGHT;
    public final static int LEFT = 0,RIGHT = 1,UP = 2,DOWN = 3;
    public boolean addToSnake = false;
    
    //spelets status
    public int gameStatus = 0;
                            
    public final static int START = 0,
                            RUN = 1,
                            MENU = 2,
                            CREDITS = 3,
                            OPTIONS = 4,
                            HELP = 5,
                            ERROR = 6,
                            EXITING = 7,
                            INSTRUCTIONS = 8,
                            GAMEOVER = 9,
                            GAMEOVER2 = 10,
                            COMPLETED_LEVEL = 11,
                            USERDEF_A = 12,
                            USERDEF_B = 13,
                            USERDEF_C = 14,
                            USERDEF_D = 15,
                            PAUSE = 16,
                            PAUSE2 = 17;
    
    private int pauseCounter;
    
    private int timeToInit = 0;
    
    long score; //totalpo�ngen
    int level;
    
 int counterA;
 
double w = 1.0f;
 
 float snakeX,snakeY;
 
 
//------------------------------------------------------------------------------
 public Game(Shrimplet parent) {//kostruktorn
                super(true); //st�nger av h�ndelse-hanteringen p knapparna //�rver fr�n gamecanvas  
                this.parent = parent; //h�mtar midleten
                setFullScreenMode(true); //s�tter full-sk�rm
                thread = new Thread(this); //skapar en ny tr�d
                thread.start(); //startar tr�den
 }
//------------------------------------------------------------------------------
 public void run() {//tr�den b�rjar k�ras h�r
     
            init(); //k�r initsieringen ingenting f�re den om det inte m�ste, kontrollera flera g�nger om s� �r faller

            cB.draw(3);
            
            gameStatus = INSTRUCTIONS;
                while (true) {  //huvud loopen i spelet (kommer att k�ras �nda tills prgmet avslutas lr st�rs
                 gE.startFpsStopper();
                 gE.clear();   
                   switch(gameStatus){    
                        case RUN:    
                            handleInput();//tangent-tryckningar
                            updateObjects();    //updaterar object
                            draw();    //ritar ut allt till buffern
                        break;
                        case MENU:
                            menuInput();
                            menuUpdate();
                            menuDraw();
                        break;
                        case CREDITS:
                            gE.updateKeys();
                            if(gE.inputReleased(FIRE_PRESSED)){
                                gameStatus = MENU;
                            }
                            g.setColor(255,255,255);
                            g.drawString("ShrimpGaming UF",0,0,g.TOP|g.LEFT);
                            g.setColor(163,0,192);
                            g.drawString("Jim Holmstr�m (VD, Production)",0,15,g.TOP|g.LEFT);
                            g.drawString("Peter Ribacke (Communications)",0,30,g.TOP|g.LEFT);
                            g.drawString("Patrik �stansj� (Economy)",0,45,g.TOP|g.LEFT);
                            g.drawString("Daniel Bodinson (Economy)",0,60,g.TOP|g.LEFT);
                            g.drawString("Patrik Tegner (Marketing)",0,75,g.TOP|g.LEFT);
                            g.drawString("Johan Deutsch (Salesman)",0,90,g.TOP|g.LEFT);
                            g.drawString("Joacim Larsson (WebDesign)",0,105,g.TOP|g.LEFT);
                            g.drawString("Jose Chilo (SpriteDesign)",0,120,g.TOP|g.LEFT);
                            g.drawString("Andre Gustavsson (Flash)",0,135,g.TOP|g.LEFT);

                        break;
                        case PAUSE:
                            gE.updateKeys();
                            if(gE.inputReleased(FIRE_PRESSED)){
                                pauseCounter = 3;
                                gameStatus = PAUSE2;     
                            }
                        g.setFont(gE.SYSTEM_LARGE_FONT);
                        g.setColor(255,255,255);
                        g.drawString("Pause, push 5 to continue",0,0,g.TOP|g.LEFT);

                        break;
                        case PAUSE2:
                            gE.updateKeys();
                            if(gE.inputReleased(FIRE_PRESSED)){
                                gameStatus = RUN;     
                            }
                        g.setFont(gE.SYSTEM_LARGE_FONT);
                        g.setColor(255,255,255);
                        
                        g.drawString("Pause, push 5 (again) to continue..." + pauseCounter,0,0,g.TOP|g.LEFT);
                        
                        if(--pauseCounter<0){
                            gameStatus = PAUSE;
                        }
                        break;
                        case HELP:
                            g.setColor(255,255,255);

                            g.drawString("4 or joystick left:",0,0,g.TOP|g.LEFT);
                            g.drawString("move shrimp left",0,15,g.TOP|g.LEFT);
                            g.drawString("6 or joystick right:",0,40,g.TOP|g.LEFT);
                            g.drawString("move shrimp right",0,55,g.TOP|g.LEFT);
                            g.drawString("5 or joystick fire:",0,80,g.TOP|g.LEFT);
                            g.drawString("Pause and menu",0,95,g.TOP|g.LEFT);


                            gE.updateKeys();
                            if(gE.inputReleased(FIRE_PRESSED)){
                                gameStatus = MENU;
                            }
 
                        break;

                        case INSTRUCTIONS:
                            g.setColor(255,255,255);


                            g.drawString("2, 4, 6 or joystick:",0,0,g.TOP|g.LEFT);
                            g.drawString("move submarine",0,15,g.TOP|g.LEFT);
                            g.drawString("5 or joystick fire:",0,30,g.TOP|g.LEFT);
                            g.drawString("Pause and menu",0,45,g.TOP|g.LEFT);
                            
                            g.drawString("Mission Objectives:",0,80,g.TOP|g.LEFT);
                            g.drawString("1. Find all pearls",0,95,g.TOP|g.LEFT);
                            g.drawString("2. Land Carefully on platform",0,110,g.TOP|g.LEFT);

                            gE.updateKeys();
                            if(gE.inputReleased(FIRE_PRESSED)){
                                gameStatus = MENU;
                            }
 
                        break;
                        case OPTIONS:
                            //l�gg en plut p� den som R std (hur l�ser man dt?)
                            
                        Question ask = new Question(this,"Options", new String[]{"Reset Highscore...","Choose level...","Watch Highscore...","Back to Menu"});
                        switch(ask.draw()){
                            case 0://Reset Highscore
                                ask = new Question(this,"Reset HighScore", new String[]{"No","Yes"});
                                if(ask.draw() == 1){//resets highscore
                                    highscore = 0;
                                    hs.writeRecord(1,0);
                                }
                            break;
                            case 1://Choose level
                                ask = new Question(this,"Choose Level?", new String[]{"One","Two","Three","Four"});
                                level = ask.draw();
                                gE.fpsLimit = 1000/((level+1)*5); //1000/fps = 1000/((level + 1)*5), level + 1 eftersom ask.draw() returnerar 0 vid "one"
                            break;
                            case 3:
                                gameStatus = MENU;
                            break;
                            case 2:
                                ask = new Question(this,"Highscores",new String[]{"1." + test[0],"2." + test[1],"3." + test[2],"4." + test[3],"5." + test[4]});//rita ut highscore
                                ask.draw();
                            break;
                            default:
                            break;
                        }        

                        gameStatus = MENU;

                        break;
                        case GAMEOVER:
                            gE.updateKeys();
                            if(gE.inputReleased(FIRE_PRESSED)){
                                restart(); //Starta om snaken
                                gameStatus = MENU;     //g� till menyn
                            }
                        level = 0;
                        g.setFont(gE.SYSTEM_LARGE_FONT);
                        g.setColor(255,255,255);
                        g.drawString("GameOver!",0,0,g.TOP|g.LEFT);
                        g.setFont(gE.SYSTEM_MEDIUM_FONT);
                        g.drawString("Score: " + score,0,20,g.TOP|g.LEFT);
                        
                        if(score > highscore){//om dt R st�rre �n highscore
                            highscore = score;//s�tt den nya highscore
                            hs.writeRecord(1,highscore);//f�rsta g�ngen skriver du in highscoret i minnet :P
                            
                        }
                        if(score == highscore){// om det R highscore den h�r g�ngen
                            g.drawString("Highscore!!!",0,40,g.TOP|g.LEFT);
                        }
                        
                        
                        //setHighscore(highscore);//s�tt listan till highscore
                        
                        //TEMP!!
                        for(int i= 0;i<1000;i++){
                            setHighscore(gE.rndi(1000));//s�tt listan till highscore            
                            ask = new Question(this,"Highscores",new String[]{"1." + test[0],"2." + test[1],"3." + test[2],"4." + test[3],"5." + test[4]});//rita ut highscore
                            ask.draw();
                        }
                        
                        gameStatus = MENU;
                        
                        break;
                        default:
                        break;
                    }    
                   if(gameStatus != RUN){//om statusen �r allt annat �n RUN visa rollern
                        gE.displayRoller();
                        
                   }
                   
                   if(gameStatus == INSTRUCTIONS){//i instructionerna i b�rjan finns push 5 to continue blinkaren
                       if(++counterA>100){//blinkande "push 5 to continue"

                                g.setColor(255,120,120);//bg rectangeln
                                g.fillRect(0,height/2-10,width,20);

                                g.setColor(40,0,0);//texten
                                g.drawString("PUSH 5 TO CONTINUE",width/2,height/2,g.BASELINE|g.HCENTER);
                                if(counterA>150)//nollst�ll den om den �r st�rre �n hundra
                                    counterA = 0;
                      }
                  }
                   
                    gE.fps(); //riar ut FPS'n
                System.gc();//<--rensar skr�p //finns dt n�gra nackdelar???(processorkraft?)
                flushGraphics(); //m�lar buffern p sk�rmen
                gE.fpsStopper();
               }//END LOOP
 }
//------------------------------------------------------------------------------
 private void init(){//allt som ska laddas in fr�n b�rjan, laddas h�r...
     //spelmotorn
     gE.rollerText = "www.ShrimpGaming.se";
     gE.game = this;
     gE.fpsLimit = 1000/10; //1000/fps
     gE.clear();//bara f�r att f� bort blinkandet n�r man laddar in spelet
     
     long startTime = System.currentTimeMillis();//starta tidtagning f�r att se hur l�ng tid dt tar att ladda allt
     
     //spelet
     width = getWidth();
     height = getHeight();
 
     //ladda in en logo med r�tt storlek utifr�n hur h�g sk�rmen �r
     logo = gE.loadImage("ship.png");
             
             //gE.resizeImage(gE.loadImage("logo.png"),(float)height/320.0f);

     restart();
     
     
     //menyn
     for(int i = 0; i<5; i++){//r�kna up alla objecten
        MenuObject m = new MenuObject(width/2-50,30*i+height/2-50);//s�tter dom p� r�tt st�lle
        menu.add(m);//l�gger in dom i listan
     }//END FOR
     
     if(hs.length() == 0){//om det inte finns n�got record alls s� skapas det
                hs.newRecord(0);
    }

     highscore = hs.readRecord(1);//l�ser av dt som finns i minnet till highscore
     
     g.setFont(gE.SYSTEM_MEDIUM_FONT);
      restart();//Startar om spelet
     
      long endTime = System.currentTimeMillis();//stoppa tidtagningen
      timeToInit = (int)(endTime-startTime);//r�kna ut tiden
}  
//------------------------------------------------------------------------------
 public void handleInput() {//hanterar knapptryckningar
           gE.updateKeys();
           
            switch(gE.keyState){
               case LEFT_PRESSED:
                   if(dirSnake != RIGHT)
                    dirSnake = LEFT;
               break;
               case RIGHT_PRESSED:
                   if(dirSnake != LEFT)
                    dirSnake = RIGHT;
               break;
               case UP_PRESSED:
                   if(dirSnake != DOWN)
                    dirSnake = UP;
               break;
               case DOWN_PRESSED:
                   if(dirSnake != UP)
                    dirSnake = DOWN;
               break;
               default:
               break;
           }//END SWITCH
            
            
            if(gE.inputReleased(FIRE_PRESSED)){
                gameStatus = MENU;  
            }
            
}
//------------------------------------------------------------------------------
 public void updateObjects() {//updaterar alla objecten    
            CheckCollision();//tittar vilka bollar som kolliderar
            Physics();//fysiken
            
            
            switch(dirSnake){
                case DOWN:
                    snakeY+=pearlImg.getWidth();
                break;
                case UP:
                    snakeY-=pearlImg.getWidth();
                break;
                case LEFT:
                    snakeX-=pearlImg.getWidth();
                break;
                case RIGHT:
                    snakeX+=pearlImg.getWidth();
                break;
                default:break;
            }
            
            //g�r s� att den tittar fram p� andra sidan sk�rmen
            if(snakeX<0){
            snakeX=pearlImg.getWidth()*(int)(width/pearlImg.getWidth())-pearlImg.getWidth()/2; //h�jden till den sista hela rutan - halva bredden p� huvudet
            }
            if(snakeY<0){
            snakeY=pearlImg.getHeight()*(int)(height/pearlImg.getHeight())-pearlImg.getHeight()/2;
            }
            if(snakeX>pearlImg.getWidth()*(int)(width/pearlImg.getWidth())){
            snakeX=pearlImg.getWidth()/2;
            }
            if(snakeY>pearlImg.getHeight()*(int)(height/pearlImg.getHeight())){
            snakeY=pearlImg.getHeight()/2;
            }
            
            
            
            
                 
            moveSnake();
       
            gE.update(snake);
            gE.update(fruits);

}
//------------------------------------------------------------------------------
  public void draw() {//ritar ut allt
   g.setColor(255,0,0);
   
      
   gE.paint(snake);   
   //snake.paintDebug(g);  
   
   /*
  for(int i = 0;i<snake.size();i++){   
    g.drawString("" + i,(int)snake.getShrimp(i).x,(int)snake.getShrimp(i).y,g.TOP|g.LEFT);
  
  }  
   */
   
   gE.paint(fruits);
   //fruits.paintDebug(g);
      
   g.fillArc((int)snakeX-3,(int)snakeY-3,5,5,0,360);
      
   
   g.drawString("Score: " + score, 0,0,g.TOP|g.LEFT);
   

   for(int x=0;x<width;x+=pearlImg.getWidth()){
       g.drawLine(x,0,x,height);
   }
   for(int y=0;y<height;y+=pearlImg.getHeight()){
       g.drawLine(0,y,width,y);
   }
   
  }  
 //------------------------------------------------------------------------------
private void CheckCollision(){//tittar om n�gon av spritsen kolliderar
    
        for(int j = 0;j<fruits.size();j++){//titta om den koliderar med n�gon av frukterna
            Shrimp fruit = fruits.getShrimp(j);
            if(snake.getShrimp(snake.size()-1).collidesWithShrimp(fruit)){
                score++;
                fruits.removeShrimp(j);
                addToSnake = true;
                    //g�r N ny frukt
                newFruit();
                return;//hoppa urloppen om den tr�ffar n�n(den kan ju inte tr�ffa 2st samtidigt iaf
            }
        }
      for(int i = 0;i<snake.size();i++){
      Shrimp snakeComp = snake.getShrimp(i);    
       for(int k = 0;k<snake.size();k++){
           Shrimp snakeOtherComp = snake.getShrimp(k);
           if(k!=i && snakeComp.collidesWithShrimp(snakeOtherComp)){
            gameStatus = GAMEOVER;
            //s� att man ser hur man dog :P
                draw();
                g.setColor(255,255,255);//skriver ut gameover p� sk�rmen
                g.drawString("GAME OVER!",width/2,height/2,g.BASELINE|g.HCENTER);
                flushGraphics();
                
                //pause s� att man ser vart man krockade i sig sj�lv
            while(!gE.inputReleased(FIRE_PRESSED)){//om man trycker p� fire sl�pper den for-loopen
                gE.updateKeys();
            }
           }
       }
      
      
    }
    
}

//------------------------------------------------------------------------------
private void Physics(){//updaterar fysiken i spelet
    
}

//---[meny]---------------------------------------------------------------------  
public void menuInput(){
       gE.updateKeys();
       
       //ner lr upp i menyn
       if(gE.inputReleased(UP_PRESSED)){
        if(menu.focused > 0){//om den �r st�rre �n noll 
            menu.focused--;//minska med ett
        }
        w-=0.05f;
       }
       if(gE.inputReleased(DOWN_PRESSED)){
        if(menu.focused < menu.size() - 1){ //om den �r mindre �n den st�rsta
            menu.focused++; //addera med ett
        }
        w+=0.05f;
       }
     
       if(gE.inputReleased(FIRE_PRESSED)){ // om man skjuter i menyn :P
           switch(menu.focused){
               case 0:
                   gameStatus = RUN;
                   Question ask = new Question(this,"Restart?", new String[]{"Yes","No"});
                   if(ask.draw() == 0){//om man vill b�rja om
                    restart();
                    score = 0;
                   }
               break;
               case 1:
                   gameStatus =  OPTIONS;
               break;
               case 2:
                   gameStatus = HELP;
               break;
               case 3:
                   gameStatus = CREDITS;
               break;
               case 4: //exiting
                   hs.writeRecord(1,highscore);
                   gE.msg(new String[]{"More old school cool?","visit www.shrimpGaming.se"});
                   parent.terminateGame();
               break;
               default:
               break;
           }
       }
 
}
//------------------------------------------------------------------------------
public void menuUpdate(){//s�tter vilka som inte �r tillg�ngliga lr focuserade lr inte

    for(int i = 0; i<menu.size();i++){//g� igenom hela menyn
        if(i == menu.focused){//om det �r den som �r i focus
            menu.getMenuObj(i).status = MenuList.FOCUS;    
        }else{
            menu.getMenuObj(i).status = MenuList.UNFOCUS;
        }    
    }
   
}
//------------------------------------------------------------------------------
public void menuDraw(){//ritar ut allt i menyn; logon, meny mm...   
    g.drawImage(logo,width/2-logo.getWidth()/2,-logo.getHeight()/5,g.TOP|g.LEFT);

    menu.draw(g);

    g.drawImage(gE.test(logo,(float)w),width/2 -logo.getWidth()/2,height/2 - logo.getHeight()/2,g.TOP|g.LEFT);
    g.setColor(255,255,255);
    g.drawString("HighScore: " + highscore,0,height - 40,g.TOP|g.LEFT); 
    
    g.drawString("ratio: " + w,0,20,g.TOP|g.LEFT);
}  
//------------------------------------------------------------------------------       
private void restart(){
   Shrimp.worldX = 0;
   Shrimp.worldY = 0;
   
   
   dirSnake = RIGHT;
   snake.removeAllElements();
   fruits.removeAllShrimps();
   
   
     for(int i = 0;i<5;i++){
       Shrimp snakeComp = new Shrimp(pearlImg);
        snakeX = pearlImg.getWidth()*i + pearlImg.getWidth()/2;
        snakeY = pearlImg.getHeight()/2;
        
        snakeComp.x = snakeX;
        snakeComp.y = snakeY;
        
        snake.addShrimp(snakeComp);
        
     }
   gE.update(snake);// s�tt den p� r�tt plats.     
   
   for(int i = 0;i<25;i++)
    newFruit();
   
   gE.update(fruits);//s�tter alla p� r�tt plats s� att dt inte uppst�r n� konstiga situationer
   gE.update(snake);
   
 score = 0;
}
//--------------[egna funktioner]-----------------------------------------------
private void newFruit(){
    Shrimp newFruit = new Shrimp(appleImg);
        do{//f�rs�k med att s�tta ut nya frukter �nda tills den inte tr�ffar snaken
            newFruit.x = (float)(pearlImg.getWidth()*gE.rndi(width/pearlImg.getWidth()) + newFruit.width/2);
            newFruit.y =(float)(pearlImg.getWidth()*gE.rndi(height/pearlImg.getHeight()) + newFruit.height/2);
            gE.update(newFruit);
       }while(newFruit.collidesWithShrimp(snake)||newFruit.collidesWithShrimp(fruits));                        
    fruits.addShrimp(newFruit);    
}

private void moveSnake(){
            if(!addToSnake){
                snake.removeShrimp(0);
            }
            addToSnake = false;
            Shrimp snakeComp = new Shrimp(pearlImg);
            snakeComp.x = snakeX;
            snakeComp.y = snakeY;

            snake.addShrimp(snakeComp);
}

private void setHighscore(long score){
    
    for(int i = 0;i<test.length;i++){
        if(score>test[i]){
            for(int j = test.length;j<i-1;j--){//flytta ner dom som �r iv�gen, ERROR, funkar inte som den ska, vda �r det som jag har t�nkt fel?
                test[j] = test[j - 1];
            }
            test[i] = score;
            break;
        }
    }
    
    
}

}