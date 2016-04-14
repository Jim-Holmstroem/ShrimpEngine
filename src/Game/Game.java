package Game;

import javax.microedition.lcdui.*;//rita p skärmen mm, bibl.
import javax.microedition.lcdui.game.*;//spel-bibl.
import javax.microedition.lcdui.game.Sprite;//sprites bibl.
import java.util.Vector;//vector-arrayer
import java.lang.Math;//matte-bibl.
import javax.microedition.media.*;//ljud
import java.io.*;//läs och skriv

//Shrimp-packages
import GUI.*;
import SIO.ReadWrite;
import GameCore.Shrimp;
import GameCore.ShrimpList;
import GameCore.GameEngine;
import Core.Shrimplet;



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
    public GameEngine gE = new GameEngine(); //spelmotorn
    public Graphics g = getGraphics(); //hämntar graphics'n
    public Shrimplet parent;
    public Thread thread;        //tråden
    public int graphicSetting;
    
    private MenuList menu = new MenuList("menu.png",100,20);
    private CommercialBreak cB = new CommercialBreak(this); //Commercialbreak R bortagen från compileringen
    
    Image pearlImg = gE.loadImage("pearl.png");
    Image appleImg = gE.loadImage("shrimplogo.png");
    
    ShrimpList snake = new ShrimpList();
    ShrimpList fruits = new ShrimpList();
    
    //höjd bredd
    public int height = 0,
                width = 0; //public höjd/bredd p skärmen

    //-----------------player-----------------------------------
    private long highscore;
    private ReadWrite hs = new ReadWrite("highscore");
    
    private long[] test = new long[5];//highscore lista med 5st highscore
    
//------------------------------------------------------------------------------
    
    Image logo = null;
    
    //vilket håll masken R p väg åt
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
    
    long score; //totalpoängen
    int level;
    
 int counterA;
 
double w = 1.0f;
 
 float snakeX,snakeY;
 
 
//------------------------------------------------------------------------------
 public Game(Shrimplet parent) {//kostruktorn
                super(true); //stänger av händelse-hanteringen p knapparna //ärver från gamecanvas  
                this.parent = parent; //hämtar midleten
                setFullScreenMode(true); //sätter full-skärm
                thread = new Thread(this); //skapar en ny tråd
                thread.start(); //startar tråden
 }
//------------------------------------------------------------------------------
 public void run() {//tråden börjar köras här
     
            init(); //kör initsieringen ingenting före den om det inte måste, kontrollera flera gånger om så är faller

            cB.draw(3);
            
            gameStatus = INSTRUCTIONS;
                while (true) {  //huvud loopen i spelet (kommer att köras ända tills prgmet avslutas lr störs
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
                            g.drawString("Jim Holmström (VD, Production)",0,15,g.TOP|g.LEFT);
                            g.drawString("Peter Ribacke (Communications)",0,30,g.TOP|g.LEFT);
                            g.drawString("Patrik Östansjö (Economy)",0,45,g.TOP|g.LEFT);
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
                            //lägg en plut på den som R std (hur löser man dt?)
                            
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
                                gameStatus = MENU;     //gå till menyn
                            }
                        level = 0;
                        g.setFont(gE.SYSTEM_LARGE_FONT);
                        g.setColor(255,255,255);
                        g.drawString("GameOver!",0,0,g.TOP|g.LEFT);
                        g.setFont(gE.SYSTEM_MEDIUM_FONT);
                        g.drawString("Score: " + score,0,20,g.TOP|g.LEFT);
                        
                        if(score > highscore){//om dt R större än highscore
                            highscore = score;//sätt den nya highscore
                            hs.writeRecord(1,highscore);//första gången skriver du in highscoret i minnet :P
                            
                        }
                        if(score == highscore){// om det R highscore den här gången
                            g.drawString("Highscore!!!",0,40,g.TOP|g.LEFT);
                        }
                        
                        
                        //setHighscore(highscore);//sätt listan till highscore
                        
                        //TEMP!!
                        for(int i= 0;i<1000;i++){
                            setHighscore(gE.rndi(1000));//sätt listan till highscore            
                            ask = new Question(this,"Highscores",new String[]{"1." + test[0],"2." + test[1],"3." + test[2],"4." + test[3],"5." + test[4]});//rita ut highscore
                            ask.draw();
                        }
                        
                        gameStatus = MENU;
                        
                        break;
                        default:
                        break;
                    }    
                   if(gameStatus != RUN){//om statusen är allt annat än RUN visa rollern
                        gE.displayRoller();
                        
                   }
                   
                   if(gameStatus == INSTRUCTIONS){//i instructionerna i början finns push 5 to continue blinkaren
                       if(++counterA>100){//blinkande "push 5 to continue"

                                g.setColor(255,120,120);//bg rectangeln
                                g.fillRect(0,height/2-10,width,20);

                                g.setColor(40,0,0);//texten
                                g.drawString("PUSH 5 TO CONTINUE",width/2,height/2,g.BASELINE|g.HCENTER);
                                if(counterA>150)//nollställ den om den är större än hundra
                                    counterA = 0;
                      }
                  }
                   
                    gE.fps(); //riar ut FPS'n
                System.gc();//<--rensar skräp //finns dt några nackdelar???(processorkraft?)
                flushGraphics(); //målar buffern p skärmen
                gE.fpsStopper();
               }//END LOOP
 }
//------------------------------------------------------------------------------
 private void init(){//allt som ska laddas in från början, laddas här...
     //spelmotorn
     gE.rollerText = "www.ShrimpGaming.se";
     gE.game = this;
     gE.fpsLimit = 1000/10; //1000/fps
     gE.clear();//bara för att få bort blinkandet när man laddar in spelet
     
     long startTime = System.currentTimeMillis();//starta tidtagning för att se hur lång tid dt tar att ladda allt
     
     //spelet
     width = getWidth();
     height = getHeight();
 
     //ladda in en logo med rätt storlek utifrån hur hög skärmen är
     logo = gE.loadImage("ship.png");
             
             //gE.resizeImage(gE.loadImage("logo.png"),(float)height/320.0f);

     restart();
     
     
     //menyn
     for(int i = 0; i<5; i++){//räkna up alla objecten
        MenuObject m = new MenuObject(width/2-50,30*i+height/2-50);//sätter dom på rätt ställe
        menu.add(m);//lägger in dom i listan
     }//END FOR
     
     if(hs.length() == 0){//om det inte finns något record alls så skapas det
                hs.newRecord(0);
    }

     highscore = hs.readRecord(1);//läser av dt som finns i minnet till highscore
     
     g.setFont(gE.SYSTEM_MEDIUM_FONT);
      restart();//Startar om spelet
     
      long endTime = System.currentTimeMillis();//stoppa tidtagningen
      timeToInit = (int)(endTime-startTime);//räkna ut tiden
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
            
            //gör så att den tittar fram på andra sidan skärmen
            if(snakeX<0){
            snakeX=pearlImg.getWidth()*(int)(width/pearlImg.getWidth())-pearlImg.getWidth()/2; //höjden till den sista hela rutan - halva bredden på huvudet
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
private void CheckCollision(){//tittar om någon av spritsen kolliderar
    
        for(int j = 0;j<fruits.size();j++){//titta om den koliderar med någon av frukterna
            Shrimp fruit = fruits.getShrimp(j);
            if(snake.getShrimp(snake.size()-1).collidesWithShrimp(fruit)){
                score++;
                fruits.removeShrimp(j);
                addToSnake = true;
                    //gör N ny frukt
                newFruit();
                return;//hoppa urloppen om den träffar nån(den kan ju inte träffa 2st samtidigt iaf
            }
        }
      for(int i = 0;i<snake.size();i++){
      Shrimp snakeComp = snake.getShrimp(i);    
       for(int k = 0;k<snake.size();k++){
           Shrimp snakeOtherComp = snake.getShrimp(k);
           if(k!=i && snakeComp.collidesWithShrimp(snakeOtherComp)){
            gameStatus = GAMEOVER;
            //så att man ser hur man dog :P
                draw();
                g.setColor(255,255,255);//skriver ut gameover på skärmen
                g.drawString("GAME OVER!",width/2,height/2,g.BASELINE|g.HCENTER);
                flushGraphics();
                
                //pause så att man ser vart man krockade i sig själv
            while(!gE.inputReleased(FIRE_PRESSED)){//om man trycker på fire släpper den for-loopen
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
        if(menu.focused > 0){//om den är större än noll 
            menu.focused--;//minska med ett
        }
        w-=0.05f;
       }
       if(gE.inputReleased(DOWN_PRESSED)){
        if(menu.focused < menu.size() - 1){ //om den är mindre än den största
            menu.focused++; //addera med ett
        }
        w+=0.05f;
       }
     
       if(gE.inputReleased(FIRE_PRESSED)){ // om man skjuter i menyn :P
           switch(menu.focused){
               case 0:
                   gameStatus = RUN;
                   Question ask = new Question(this,"Restart?", new String[]{"Yes","No"});
                   if(ask.draw() == 0){//om man vill börja om
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
public void menuUpdate(){//sätter vilka som inte är tillgängliga lr focuserade lr inte

    for(int i = 0; i<menu.size();i++){//gå igenom hela menyn
        if(i == menu.focused){//om det är den som är i focus
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
   gE.update(snake);// sätt den på rätt plats.     
   
   for(int i = 0;i<25;i++)
    newFruit();
   
   gE.update(fruits);//sätter alla på rätt plats så att dt inte uppstår nå konstiga situationer
   gE.update(snake);
   
 score = 0;
}
//--------------[egna funktioner]-----------------------------------------------
private void newFruit(){
    Shrimp newFruit = new Shrimp(appleImg);
        do{//försök med att sätta ut nya frukter ända tills den inte träffar snaken
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
            for(int j = test.length;j<i-1;j--){//flytta ner dom som är ivägen, ERROR, funkar inte som den ska, vda är det som jag har tänkt fel?
                test[j] = test[j - 1];
            }
            test[i] = score;
            break;
        }
    }
    
    
}

}