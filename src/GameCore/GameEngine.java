/*            [GameEngine.java]
 *
 *
 *  Contains commanly used misc. metods and fields
 *
 *   fråga Johan Isaksson om det är bättre att ha shrimp.update ist gE.update(shrimp);
 *  @Author: Jim 'SlimJim' Holmström for Shrimp Gaming UF
 */

import javax.microedition.lcdui.*;
import java.lang.Math;
import java.util.*;

public class GameEngine{

//------------------------------------------------------------------------------  

   public float gravity = 0.04f; //har kommenterat bort den i update!
   private Random rndm = new Random();

   //fps-------------------------------------------------
    private long oldTimeMillis = 0;
    public float fps = 0.0f;
    private float[] cFps = new float[10]; //döper dom efter vad dom heter utan
    private int fpsDelay = 0;
    
    private long startTime,readyTime;//fpsstopparen
    public int fpsLimit;
    
   //---------------------------------------------- 
    //input
    public int keyState = 0, lastKeyState = 0;
    
    //spelet
    public Game game = null;
    
    //roller
    public String rollerText = "???";
    private int roller = 100;
    
    //autozoom-------------------------------------
    public int stdWidth=176,stdHeight=220;//höjd och bredd man ska räkna efter
    private int realWidth,realHeight;//höjd och bredd p skärmen //behövs dt ens???
    private float resizeAmount = 1.0f;
    
    //fonts
    public static final Font    SYSTEM_SMALL_FONT  = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL),
                                SYSTEM_MEDIUM_FONT  = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL),
                                SYSTEM_LARGE_FONT  = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL),
                                FONT  = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL),
                                DULL_FONT  = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
   
    public static final int TOP = 0,
                            BOTTOM = 1,
                            LEFT = 2,
                            RIGHT = 3;
    
    
//------------------------------------------------------------------------------
    public GameEngine() {
     
    }

//------------------------------------------------------------------------------
    public void init(){//ladda in och räkna ut allt
    
    
    }
   //-----------------------------------------------------------------------------  
    
    public Image loadImage(String filename){//ladda in en bild, när den ska ladda in bilden kan den ju passa på att göra den i rät storlek atomatiskt
    try{
        return Image.createImage("/pic/"+filename); //ladda in bilden
    }catch(Exception e){System.out.println("Error creating Image \""+ filename +   "\": "+e.getMessage());}//få ut ett error meddelande i output
        return null;//returnera null om dt blir fel, går dt att returnera en tom ruta ist, så slipper hela prgmet hänga sig
   }
//--------------------------------------------------
    
private void pop(int[] a){//töm hela arrayen = sätt full transparenta pixlar
    for(int i = 0;i<a.length;i++){
        a[i] = 0x00000000;
    }
}    
    
    
public Image test(Image src, float ratio){//nu R den transparent iaf :P

//ratio = 2.0f;

float aRatio = 1.0f/ratio;

int srcWidth = src.getWidth();
int srcHeight = src.getHeight();

int rszWidth = (int)((float)srcWidth*ratio);
int rszHeight = (int)((float)srcHeight*ratio);

int[] srcStream = new int[srcWidth*srcHeight]; //den man utgår ifrån
int[] tmpStream = new int[rszWidth*srcHeight]; //mellan lagringen
int[] rszStream = new int[rszWidth*rszHeight]; //den klara bilden

src.getRGB(srcStream,0,srcWidth,0,0,srcWidth,srcHeight);

/*
float pos = aRatio/2.0f;
for(int i = 0;i < srcStream.length; i++){
        
    tmpStream[(int)pos] = srcStream[i];
  
    pos+=aRatio;
//        pos+=1;

}
*/

/*
int x = 0,y = 0;
try{
    System.out.println("w." + srcWidth + "h." + srcHeight);
for(double w = 0.0f;w<2.0f*Math.PI;w+=Math.PI/256.0f){
   
        y = 10 + (int)(5.0f*Math.cos(w));x = 10 + (int)(5.0f*Math.sin(w));

        
             if(y>=0&&
                x>=0&&
                y<srcHeight&&
                x<srcHeight) //om den är inuti arrayen
                    srcStream[srcWidth*y + x] = 0xFFFFFF00; 
}
}catch(Exception e){
System.out.println("x." + x + "y." + y + ":S." + (srcWidth*y + x) + "!<>" + srcStream.length + "w." + srcWidth + "h." + srcHeight);
}

*/

//kom på hur dt funkar med g.* sen gör N remake utan g.*


float pos = aRatio/2.0f;
for(int x = 0;x<rszWidth -1;x++){
    for(int y = 0;y<srcHeight -1;y++){
        tmpStream[x + rszWidth*y] = srcStream[x + srcWidth*y];
    }
}


/*
float pos = aRatio/2.0f;
for(int x = 0;x < newWidth; x++){
	g.setClip(x,0,1,srcHeight);     //x = x;y = 0;bredd = 1;höjd = HÖJD;  
	g.drawImage(src, x -(int)pos,0,Graphics.LEFT|Graphics.TOP);   //
	pos+=aRatio;
}

pos = aRatio/2.0f;
for(int y = 0;y < newHeight; y++){
	g.setClip(0,y,newWidth,1);
	g.drawImage(tmp,0, y -(int)pos,Graphics.LEFT|Graphics.TOP);
	pos+=aRatio;
}
*/


Image tmp = Image.createRGBImage(tmpStream,rszWidth,srcHeight,true);


return tmp;
}    
 public Image test2(Image src,float ratio) {
 int srcWidth = src.getWidth();
int srcHeight = src.getHeight();

int[] srcStream = new int[srcWidth*srcHeight]; //den man utgår ifrån




src.getRGB(srcStream,0,src.getWidth(),0,0,src.getWidth(),src.getHeight());
 
Image tmp = Image.createRGBImage(srcStream,src.getWidth(),src.getHeight(),true);


return tmp;
 
 }  
    
    
    
    
public Image resizeImage(Image src, float ratio){ //hur funkar den, vad R det som inte funkar, är det jag som har skrivit nått fel???
//klarar inte av transparens!!! , FUCK!!!
    //man kan inte göra bilden hur liten som helst för då hänger den sig :P (fixa så att den blir spärrad!)
    //blir nullpointer exception använd den inte !
    //optimera den här!!!
    
float  aRatio = 1.0f/ratio; //inversen av ratio!
    
int srcWidth = src.getWidth();
int srcHeight = src.getHeight();

int screenWidth = (int)(ratio*srcWidth);
int screenHeight = (int)(ratio*srcHeight);

//--------------------------------------------------------
// <--fråga hur man löser det på något forum :P
int[] blanks = new int[screenWidth*srcHeight];


for(int i = 0; i < screenWidth*srcHeight; i++){
        blanks[i] = 0xFF000000;//sätter hela arrayen till full transparens
}

Image tmp = Image.createRGBImage(blanks,screenWidth,srcHeight,true);


//--------------------------------------------------------

//Image tmp = Image.createImage(screenWidth,srcHeight);

try{
Graphics test = tmp.getGraphics();
}catch(IllegalStateException ise){
System.out.println("Immutable image: tmp");
}

Graphics g = tmp.getGraphics();
//g.drawRGB(blanks,0,screenWidth,0,0,screenWidth,srcHeight,true);


float pos = aRatio/2.0f;


//horisantall resize
//for(int x = screenWidth;--x>=0){//optimering, funkar dt lr blir dt nå fel eller att den hamnar utanför lr dyl.
for(int x = 0;x < screenWidth; x++){
	g.setClip(x,0,1,srcHeight);       
	g.drawImage(src, x -(int)pos,0,Graphics.LEFT|Graphics.TOP);
	pos+=aRatio;
}

//------------------------------------------------------
//
blanks = null;
blanks = new int[screenWidth*screenHeight];

for(int i = 0; i < screenWidth*screenHeight; i++){
        blanks[i] = 0xFF000000;//sätter hela arrayen till full transparens
}

Image resizedImage = Image.createRGBImage(blanks,screenWidth,screenHeight,true);
//-------------------------------------------

//Image resizedImage = Image.createImage(screenWidth,screenHeight);

g = resizedImage.getGraphics();

//g.drawRGB(blanks,0,screenWidth,0,0,screenWidth,screenHeight,true);


pos = aRatio/2.0f;

//vertical resize
for(int y = 0;y < screenHeight; y++){
	g.setClip(0,y,screenWidth,1);
	g.drawImage(tmp,0, y -(int)pos,Graphics.LEFT|Graphics.TOP);
	pos+=aRatio;
}
return resizedImage;
}    
    
//------------------------------------------------------------------------------    
   public void drawImage(Image image,int x,int y,int p){//ritar bilden
    try{
        game.g.drawImage(image,x,y,p); //rita ut bilden med vald align.
    }catch(Exception e){System.out.println("Error drawing Image: "+e.getMessage());}//få ut ett error meddelande i output    
   }
   
//------------------------------------------------------------------------------
   public void drawImage(Image image,int x,int y){//ritar bilden
    try{
        game.g.drawImage(image,x,y,game.g.TOP|game.g.LEFT);//rita ut bilden med std-inställningar för alignmenten (TOP|LEFT)
    }catch(Exception e){System.out.println("Error drawing Image: "+e.getMessage());}//få ut ett error meddelande i output        
   }
     
//------------------------------------------------------------------------------
   public float rndf(){//slump-flyttal
    return rndm.nextFloat();
  }
 
//------------------------------------------------------------------------------   
   public int rndi(int value){//slump-integer
    return Math.abs(rndm.nextInt())%value;
  }

//------------------------------------------------------------------------------   
   public boolean rndb(){//slumpa en bool
    if(rndm.nextInt()%1000>500){
        return false;
    }else{
        return true;
    }
   }
   
//------------------------------------------------------------------------------   
    public void fps(){//räkna ut och ritar ut fps'n
    int timeToRender = (int)(System.currentTimeMillis() - oldTimeMillis);//räkna hur lång tid i ms en updatering är.
    
    if(fpsDelay++ >= 10){ //räkna ut medel-fps'n var 10:e gång
        fpsDelay = 0;//nollställ delay'n
        fps = sum(cFps)/10.0f;//medelvärdet på 10st samplingar
        //fps = med(cFps);//gör samma sak som ovan
        fps = (int)(10*fps)/10; //sätter dt till en decimal :D
        
    } else{ // om den inte är större eller lika med 10 //sampla
        cFps[fpsDelay-1] = 1000.0f/(float)timeToRender; //från ms till frames/s
    }
    
    oldTimeMillis = System.currentTimeMillis();//sätter det "gamla" värdet som kommer användas nästa frame
    game.g.setColor(200,100,100);
    game.g.drawString(""+fps,game.width,game.height,game.g.BOTTOM|game.g.RIGHT);//skriver ut fps-text 
 }
    
//------------------------------------------------------------------------------
    public int sum(int[] array){//räknar ut summan på alla element i listan
    int integer = 0;
        for(int i = 0; i<array.length;i++){//räkna upp alla element i listan
            integer+=array[i];//addera dom till integern
        }
    return integer; 
    }
    
//------------------------------------------------------------------------------
    public float sum(float[] array){//räknar ut summan på alla element i listan
    float f = 0;
        for(int i = 0; i<array.length;i++){//räkna upp alla element i listan
            f+=array[i];//addera dom till floaten
        }
    return f; 
    }

//------------------------------------------------------------------------------    
    public float med(float[] array){//räknar ut medelvärdet på arrayen
        return (sum(array)/(float)array.length);//medel = sum/(n)element
    }

//------------------------------------------------------------------------------    
    public int med(int[] array){//räknar ut medelvärdet på arrayen
        return (sum(array)/array.length);//medel = sum/(n)element
    }

//------------------------------------------------------------------------------    
    
 public double asin(double a){//newton-rapsody metoden med 10interationer
 double b = 0.0f;
    if(a > 1.0f || a < -1.0f){// om talet blir imaginärt
     System.out.println("out of range: " + a);
     //throw new Exception("Number out of bounds");
     return 0.0f;//throw exception
    }
    
    for(int i = 0;i<10;i++){
        b = b - (Math.sin(b)-a)/Math.cos(b); 
    }    
 return b;
 }    

//----------------------------------------------------------------------------- 
 public double acos(double a){
 double b = 1.0f;    
    if(a > 1.0f || a < -1.0f){// om talet blir imaginärt
     System.out.println("out of range: " + a);   
     return 0.0f;//throw exception
    }
    for(int i = 0;i<10;i++){
        b = b - (Math.cos(b)-a)/(-Math.sin(b)); 
    }
 return b;
 }   
 
//----------------------------------------------------------------------------- 
 public double atan(double a){//inte testad ***WARNING!***
    return asin(a)/acos(a);
 }
 //----------------------------------------------------------------------------- 
public double asin(double a, int interations){
 double b = 0.0f;
    if(a > 1.0f || a < -1.0f){// om talet blir imaginärt
     System.out.println("out of range: " + a);   
     return 0.0f;//throw exception
    }
    
    for(int i = 0;i<interations;i++){
        b = b - (Math.sin(b)-a)/Math.cos(b); 
    }    
 return b;
 }    
    
 //----------------------------------------------------------------------------- 
public double acos(double a, int interations){
 double b = 1.0f;    
    if(a > 1.0f || a < -1.0f){// om talet blir imaginärt
     System.out.println("out of range: " + a);   
     return 0.0f;//throw exception
    }
    for(int i = 0;i<interations;i++){
        b = b - (Math.cos(b)-a)/(-Math.sin(b)); 
    }
 return b;
 }   

//----------------------------------------------------------------------------- 
public double atan(double a, int interations){//inte testad ***WARNING!***
    return asin(a,interations)/acos(a,interations);
 }
//----------------------------------------------------------------------------- 

public void bounceShrimp(Shrimp obj, Shrimp obj2, double normal, float bounceFactor){
 //tvinga den att ha en hastighet bort från objectet //man kan ju inte studsa innåt lr hur man ska säga :P
 //då fastnar den inte iaf men studsar lite konstigt :( hur ska man lösa dt där???    
    
 //mycket som kan optimeras sen!!!!
    
 double oldAngle = getAngle(obj.vx,obj.vy);//vinkeln mellan objecten
 double c = getC(obj.vx,obj.vy);//  |v| = abs(v)
 
 //double newAngle = 2*normal - getAngle(obj.vx,obj.vy); //byt ut från (double oldAngle...),(double newAngle...),(newAngle = 2*...) 
 
 
 double newAngle = 0.0f; //gör en ny vinkel
 newAngle = 2*normal - oldAngle; //räkna ut studsvinkeln (vinkeln ut från collisionen)
 
 double cosA = Math.cos(newAngle);
 double sinA = Math.sin(newAngle);
 
 
if(obj.x < obj2.x) //om cos(newAngle) ska vara större lr mindre än 0 för att studsa bort från objectet
cosA = -Math.abs(cosA);
else
cosA = Math.abs(cosA); 

if(obj.y < obj2.y) //om sin(newAngle) ska vara större lr mindre än 0 för att studsa bort från objectet
sinA = -Math.abs(sinA);
else
sinA = Math.abs(sinA);

 

 obj.vx = (float)(bounceFactor*c*cosA);//sätt hastigheten som dt ska vara
 obj.vy = (float)(bounceFactor*c*sinA);
 

 }
 
 public double getC(float x, float y){//räkna ut hypotenusan (c)
 return Math.sqrt(x*x + y*y);
 }
 
 public double getDistance(Shrimp a, Shrimp b){
 double dx = a.x-b.x;
 double dy = a.y-b.y;
 return Math.sqrt(dx*dx+dy*dy);
 }
 
 
//------------------------------------------------------------------------------------------ 
 public double getAngle(float x, float y){ 
   //tar mindre minne + snabbare, men lite svårare att förstå...(har inte testat om den funkar)
    if(x > 0){
        return asin(y/getC(x,y));
    }else{
        return (Math.PI - asin(y/getC(x,y)));
    }
}


//----------------------------------------------------------------------------- 
 public void clear(){
    game.g.setColor(0,0,0);//färg = svart 
    game.g.fillRect(0,0,game.width,game.height);//ritar ut en ända stor rektangel över hela skärmen
 
 }
 //----------------------------------------------------------------------------- 
 public void update(ShrimpList shrimplist){
    for (int i = 0; i<shrimplist.size(); i++){
     update(shrimplist.getShrimp(i));
    }
 }
 //-----------------------------------------------------------------------------
  public void update(Map map){
    for (int i = 0; i<map.tiles.size(); i++){
     update(map.tiles.getShrimp(i));
    }
 }
 
 
 //----------------------------------------------------------------------------- 
  public void update(Shrimp shrimp){
      
    shrimp.vx+=shrimp.ax;
    shrimp.vy+=shrimp.ay; 
    
    shrimp.x+=shrimp.vx;
    shrimp.y+=shrimp.vy;
    
    if(shrimp.inWorld){
            shrimp.setPosition((int)Shrimp.worldX + (int)shrimp.x - shrimp.width/2,(int)Shrimp.worldY + (int)shrimp.y-shrimp.height/2);
            if((shrimp.animProgress+=shrimp.animSpeed) >= 100){//addera speeden och titta om progress har kommit över 100
                shrimp.nextFrame();//updatera animationern
                shrimp.animProgress = 0;//nollställ progressen
            }//funkar bara om dt inte är en tile eftersom dom använder sig av själva framesen till något annat...    
    }else{
        if(!shrimp.isTile){// om den inte är tile R dt bara att sätta positionen som vanligt annars sätter man den utifrån vart i värden man är
            shrimp.setPosition((int)shrimp.x-shrimp.width/2,(int)shrimp.y-shrimp.height/2);
            if((shrimp.animProgress+=shrimp.animSpeed) >= 100){//addera speeden och titta om progress har kommit över 100
                shrimp.nextFrame();//updatera animationern
                shrimp.animProgress = 0;//nollställ progressen
            }//funkar bara om dt inte är en tile eftersom dom använder sig av själva framesen till något annat...
        }else{
            shrimp.setPosition((int)Map.x + (int)shrimp.x-shrimp.width/2,(int)Map.y + (int)shrimp.y-shrimp.height/2);
        }
    }
   
  }
 //-----------------------------------------------------------------------------  
public void paint(ShrimpList shrimplist){
        for (int i = 0; i<shrimplist.size(); i++){//räkna up alla bollarna
            shrimplist.getShrimp(i).paint(game.g);//ritar i:te bollen där den ska vara 
        }
} 
//----------------------------------------------------------------------------- 
public void paint(Shrimp shrimp){
       shrimp.paint(game.g);//rita ut paddel
}
//----------------------------------------------------------------------------- 
 
 public boolean inputPressed(int button){
 return (keyState == button && lastKeyState != button);
 }
 //----------------------------------------------------------------------------- 
 public boolean inputReleased(int button){
 return (keyState != button && lastKeyState == button);
 }
//----------------------------------------------------------------------------- 
  public void updateKeys(){
    lastKeyState = keyState;
    keyState = game.getKeyStates();
 }
//----------------------------------------------------------------------------- 
  public void clearInput(){
      lastKeyState = 0;
      keyState = 0;
  }
  
public void msg(){
    msg("STOP!");
}
  //----------------------------------------------------------------------------- 
  
public void msg(String message){//för debugging om programmet skulle fastna eller för att man ska se vad som händer
 int errorStatus = 0;
  while(errorStatus == 0){
  updateKeys();
    if(inputReleased(game.FIRE_PRESSED)){
       errorStatus = 1;
    }
  clear();
  game.g.setColor(255,255,255);
  game.g.drawString(message,0,0,game.g.TOP|game.g.LEFT);
  game.flushGraphics();
  }
 errorStatus = 0;
}

public void msg(String[] message){
 int errorStatus = 0;
  while(errorStatus == 0){
  updateKeys();
    if(inputReleased(game.FIRE_PRESSED)){
       errorStatus = 1;
    }
  clear();
  
  game.g.setColor(255,255,255);
  
  for(int i=0;i<message.length;i++){
    game.g.drawString(message[i],0,20*i,game.g.TOP|game.g.LEFT);
  }
  
  game.flushGraphics();
  }
 errorStatus = 0;

}
//----------------------------------------------------------------------------- 
public void displayRoller(){
    roller++;
    game.g.setColor(200,40,34);
    game.g.drawString(rollerText, -(roller%(game.width+520) - 220) ,game.height-20,game.g.TOP|game.g.LEFT);
    game.g.drawString(rollerText, -(roller%(game.width+520) - 220 - 300) ,game.height-20,game.g.TOP|game.g.LEFT);
 }
//----------------------------------------------------------------------------- 
public void collisionRectCirc(Shrimp block,Shrimp ball, float factor){
       float normal = 0.0f;
        if(block.x + block.pointX[0] < ball.x && ball.x < block.x + block.pointX[1]){
            normal = 0.0f;//vågrät studs
        } else if(block.y + block.pointY[0] < ball.y && ball.y < block.y + block.pointY[2]){
            normal = (float)Math.PI/2.0f;//lodrätt studs
        } else{//titta vilket hörn den ligger i :D
            int corner = 0;
                    if(ball.x < block.x){
                        if(ball.y < block.y ){
                        //0
                        corner = 0;
                        }else{
                        //2
                        corner = 2;
                        }
                    }else{
                        if(ball.y < block.y ){
                        //1
                        corner = 1;
                        }else{
                        //3
                        corner = 3;
                        }
                    }
            //räkna vinkeln mellan bollen och hörnet

           double angleToCorner = getAngle(block.x+block.pointX[corner]-ball.x,block.y+block.pointY[corner]-ball.y);
           normal = (float)(angleToCorner + Math.PI/2.0f);
        }
       bounceShrimp(ball, block,normal,factor);
                   
    }
 //----------------------------------------------------------------------------- 

public boolean shrimpCollidesWithMap(Map map, Shrimp shrimp){
    for(int i = 0;i<map.tiles.size();i++){
        if(map.tiles.getShrimp(i).collidesWithShrimp(shrimp))//om den kolliderar med nåt i mapen
            return true;
    }
    return false; //om den har gått igenom allt utan att träffa på något
}

public boolean shrimpCollidesWithMap(Map map, Shrimp shrimp, int border){//den som ska bli den slutgiltiga
 //kordinat systemet R ju upp och ner ju :(
    /*   
   switch(border){//vilka puknter man ska använda och så
       case BOTTOM:
       break;
       case TOP:
       break;
       case LEFT:
       break;
       case RIGHT:
       break;
       default:break;
   }
 */
    //kontrollera att alla på samma rad har samma axel!
    
    
    //shrimpens utgångspositioner för kollisionshanteringen
    float y1 = shrimp.y + shrimp.pointY[2];//nedrevänstra
    float x1 = shrimp.x + shrimp.pointX[2];
    
    float x2 = shrimp.x + shrimp.pointX[3];//nedrehögra
 
        for(int i = 0;i<map.tiles.size();i++){
           Shrimp tile = map.tiles.getShrimp(i);
           float xL =  tile.x + tile.pointX[2];//nedre vänstra
           float xR =  tile.x + tile.pointX[1];//nedre högra
           float yU =  tile.y + tile.pointY[1];
           float yD =  tile.y + tile.pointY[3];//övre högra

                if( //om den kolliderar
                    ((yU <= y1) && (y1 <= yD)) 
                        &&
                    (
                        (xL <= x1 && x1 <= xR) 
                        ||
                        (xL <= x2 && x2 <= xR) 
                        ||
                        (xL <= x1 && x2 <= xR)
                    ) 
                        &&
                    (shrimp.y<tile.y)//och är ovanför
                  ){ 
                    return true;
                   }
        }
    
    return false; //om den har gått igenom allt utan att träffa på något
    

}

public void fpsStopper(){//spärrar fpsen//måste använda sig av startFpsStopper() i början på tråden
readyTime = System.currentTimeMillis();
    try{
        game.thread.sleep(fpsLimit - (readyTime - startTime));//gör så att alla får ungefär samma fps
    }catch(Exception e){}//om den inte kan hålla tiden så strunta i dt, dt R ju bara ett mål man vill följa...

}

public void startFpsStopper(){
    startTime = System.currentTimeMillis();
}

}