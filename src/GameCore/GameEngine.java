/*            [GameEngine.java]
 *
 *
 *  Contains commanly used misc. metods and fields
 *
 *   fr�ga Johan Isaksson om det �r b�ttre att ha shrimp.update ist gE.update(shrimp);
 *  @Author: Jim 'SlimJim' Holmstr�m for Shrimp Gaming UF
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
    private float[] cFps = new float[10]; //d�per dom efter vad dom heter utan
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
    public int stdWidth=176,stdHeight=220;//h�jd och bredd man ska r�kna efter
    private int realWidth,realHeight;//h�jd och bredd p sk�rmen //beh�vs dt ens???
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
    public void init(){//ladda in och r�kna ut allt
    
    
    }
   //-----------------------------------------------------------------------------  
    
    public Image loadImage(String filename){//ladda in en bild, n�r den ska ladda in bilden kan den ju passa p� att g�ra den i r�t storlek atomatiskt
    try{
        return Image.createImage("/pic/"+filename); //ladda in bilden
    }catch(Exception e){System.out.println("Error creating Image \""+ filename +   "\": "+e.getMessage());}//f� ut ett error meddelande i output
        return null;//returnera null om dt blir fel, g�r dt att returnera en tom ruta ist, s� slipper hela prgmet h�nga sig
   }
//--------------------------------------------------
    
private void pop(int[] a){//t�m hela arrayen = s�tt full transparenta pixlar
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

int[] srcStream = new int[srcWidth*srcHeight]; //den man utg�r ifr�n
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
                x<srcHeight) //om den �r inuti arrayen
                    srcStream[srcWidth*y + x] = 0xFFFFFF00; 
}
}catch(Exception e){
System.out.println("x." + x + "y." + y + ":S." + (srcWidth*y + x) + "!<>" + srcStream.length + "w." + srcWidth + "h." + srcHeight);
}

*/

//kom p� hur dt funkar med g.* sen g�r N remake utan g.*


float pos = aRatio/2.0f;
for(int x = 0;x<rszWidth -1;x++){
    for(int y = 0;y<srcHeight -1;y++){
        tmpStream[x + rszWidth*y] = srcStream[x + srcWidth*y];
    }
}


/*
float pos = aRatio/2.0f;
for(int x = 0;x < newWidth; x++){
	g.setClip(x,0,1,srcHeight);     //x = x;y = 0;bredd = 1;h�jd = H�JD;  
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

int[] srcStream = new int[srcWidth*srcHeight]; //den man utg�r ifr�n




src.getRGB(srcStream,0,src.getWidth(),0,0,src.getWidth(),src.getHeight());
 
Image tmp = Image.createRGBImage(srcStream,src.getWidth(),src.getHeight(),true);


return tmp;
 
 }  
    
    
    
    
public Image resizeImage(Image src, float ratio){ //hur funkar den, vad R det som inte funkar, �r det jag som har skrivit n�tt fel???
//klarar inte av transparens!!! , FUCK!!!
    //man kan inte g�ra bilden hur liten som helst f�r d� h�nger den sig :P (fixa s� att den blir sp�rrad!)
    //blir nullpointer exception anv�nd den inte !
    //optimera den h�r!!!
    
float  aRatio = 1.0f/ratio; //inversen av ratio!
    
int srcWidth = src.getWidth();
int srcHeight = src.getHeight();

int screenWidth = (int)(ratio*srcWidth);
int screenHeight = (int)(ratio*srcHeight);

//--------------------------------------------------------
// <--fr�ga hur man l�ser det p� n�got forum :P
int[] blanks = new int[screenWidth*srcHeight];


for(int i = 0; i < screenWidth*srcHeight; i++){
        blanks[i] = 0xFF000000;//s�tter hela arrayen till full transparens
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
//for(int x = screenWidth;--x>=0){//optimering, funkar dt lr blir dt n� fel eller att den hamnar utanf�r lr dyl.
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
        blanks[i] = 0xFF000000;//s�tter hela arrayen till full transparens
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
    }catch(Exception e){System.out.println("Error drawing Image: "+e.getMessage());}//f� ut ett error meddelande i output    
   }
   
//------------------------------------------------------------------------------
   public void drawImage(Image image,int x,int y){//ritar bilden
    try{
        game.g.drawImage(image,x,y,game.g.TOP|game.g.LEFT);//rita ut bilden med std-inst�llningar f�r alignmenten (TOP|LEFT)
    }catch(Exception e){System.out.println("Error drawing Image: "+e.getMessage());}//f� ut ett error meddelande i output        
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
    public void fps(){//r�kna ut och ritar ut fps'n
    int timeToRender = (int)(System.currentTimeMillis() - oldTimeMillis);//r�kna hur l�ng tid i ms en updatering �r.
    
    if(fpsDelay++ >= 10){ //r�kna ut medel-fps'n var 10:e g�ng
        fpsDelay = 0;//nollst�ll delay'n
        fps = sum(cFps)/10.0f;//medelv�rdet p� 10st samplingar
        //fps = med(cFps);//g�r samma sak som ovan
        fps = (int)(10*fps)/10; //s�tter dt till en decimal :D
        
    } else{ // om den inte �r st�rre eller lika med 10 //sampla
        cFps[fpsDelay-1] = 1000.0f/(float)timeToRender; //fr�n ms till frames/s
    }
    
    oldTimeMillis = System.currentTimeMillis();//s�tter det "gamla" v�rdet som kommer anv�ndas n�sta frame
    game.g.setColor(200,100,100);
    game.g.drawString(""+fps,game.width,game.height,game.g.BOTTOM|game.g.RIGHT);//skriver ut fps-text 
 }
    
//------------------------------------------------------------------------------
    public int sum(int[] array){//r�knar ut summan p� alla element i listan
    int integer = 0;
        for(int i = 0; i<array.length;i++){//r�kna upp alla element i listan
            integer+=array[i];//addera dom till integern
        }
    return integer; 
    }
    
//------------------------------------------------------------------------------
    public float sum(float[] array){//r�knar ut summan p� alla element i listan
    float f = 0;
        for(int i = 0; i<array.length;i++){//r�kna upp alla element i listan
            f+=array[i];//addera dom till floaten
        }
    return f; 
    }

//------------------------------------------------------------------------------    
    public float med(float[] array){//r�knar ut medelv�rdet p� arrayen
        return (sum(array)/(float)array.length);//medel = sum/(n)element
    }

//------------------------------------------------------------------------------    
    public int med(int[] array){//r�knar ut medelv�rdet p� arrayen
        return (sum(array)/array.length);//medel = sum/(n)element
    }

//------------------------------------------------------------------------------    
    
 public double asin(double a){//newton-rapsody metoden med 10interationer
 double b = 0.0f;
    if(a > 1.0f || a < -1.0f){// om talet blir imagin�rt
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
    if(a > 1.0f || a < -1.0f){// om talet blir imagin�rt
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
    if(a > 1.0f || a < -1.0f){// om talet blir imagin�rt
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
    if(a > 1.0f || a < -1.0f){// om talet blir imagin�rt
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
 //tvinga den att ha en hastighet bort fr�n objectet //man kan ju inte studsa inn�t lr hur man ska s�ga :P
 //d� fastnar den inte iaf men studsar lite konstigt :( hur ska man l�sa dt d�r???    
    
 //mycket som kan optimeras sen!!!!
    
 double oldAngle = getAngle(obj.vx,obj.vy);//vinkeln mellan objecten
 double c = getC(obj.vx,obj.vy);//  |v| = abs(v)
 
 //double newAngle = 2*normal - getAngle(obj.vx,obj.vy); //byt ut fr�n (double oldAngle...),(double newAngle...),(newAngle = 2*...) 
 
 
 double newAngle = 0.0f; //g�r en ny vinkel
 newAngle = 2*normal - oldAngle; //r�kna ut studsvinkeln (vinkeln ut fr�n collisionen)
 
 double cosA = Math.cos(newAngle);
 double sinA = Math.sin(newAngle);
 
 
if(obj.x < obj2.x) //om cos(newAngle) ska vara st�rre lr mindre �n 0 f�r att studsa bort fr�n objectet
cosA = -Math.abs(cosA);
else
cosA = Math.abs(cosA); 

if(obj.y < obj2.y) //om sin(newAngle) ska vara st�rre lr mindre �n 0 f�r att studsa bort fr�n objectet
sinA = -Math.abs(sinA);
else
sinA = Math.abs(sinA);

 

 obj.vx = (float)(bounceFactor*c*cosA);//s�tt hastigheten som dt ska vara
 obj.vy = (float)(bounceFactor*c*sinA);
 

 }
 
 public double getC(float x, float y){//r�kna ut hypotenusan (c)
 return Math.sqrt(x*x + y*y);
 }
 
 public double getDistance(Shrimp a, Shrimp b){
 double dx = a.x-b.x;
 double dy = a.y-b.y;
 return Math.sqrt(dx*dx+dy*dy);
 }
 
 
//------------------------------------------------------------------------------------------ 
 public double getAngle(float x, float y){ 
   //tar mindre minne + snabbare, men lite sv�rare att f�rst�...(har inte testat om den funkar)
    if(x > 0){
        return asin(y/getC(x,y));
    }else{
        return (Math.PI - asin(y/getC(x,y)));
    }
}


//----------------------------------------------------------------------------- 
 public void clear(){
    game.g.setColor(0,0,0);//f�rg = svart 
    game.g.fillRect(0,0,game.width,game.height);//ritar ut en �nda stor rektangel �ver hela sk�rmen
 
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
            if((shrimp.animProgress+=shrimp.animSpeed) >= 100){//addera speeden och titta om progress har kommit �ver 100
                shrimp.nextFrame();//updatera animationern
                shrimp.animProgress = 0;//nollst�ll progressen
            }//funkar bara om dt inte �r en tile eftersom dom anv�nder sig av sj�lva framesen till n�got annat...    
    }else{
        if(!shrimp.isTile){// om den inte �r tile R dt bara att s�tta positionen som vanligt annars s�tter man den utifr�n vart i v�rden man �r
            shrimp.setPosition((int)shrimp.x-shrimp.width/2,(int)shrimp.y-shrimp.height/2);
            if((shrimp.animProgress+=shrimp.animSpeed) >= 100){//addera speeden och titta om progress har kommit �ver 100
                shrimp.nextFrame();//updatera animationern
                shrimp.animProgress = 0;//nollst�ll progressen
            }//funkar bara om dt inte �r en tile eftersom dom anv�nder sig av sj�lva framesen till n�got annat...
        }else{
            shrimp.setPosition((int)Map.x + (int)shrimp.x-shrimp.width/2,(int)Map.y + (int)shrimp.y-shrimp.height/2);
        }
    }
   
  }
 //-----------------------------------------------------------------------------  
public void paint(ShrimpList shrimplist){
        for (int i = 0; i<shrimplist.size(); i++){//r�kna up alla bollarna
            shrimplist.getShrimp(i).paint(game.g);//ritar i:te bollen d�r den ska vara 
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
  
public void msg(String message){//f�r debugging om programmet skulle fastna eller f�r att man ska se vad som h�nder
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
            normal = 0.0f;//v�gr�t studs
        } else if(block.y + block.pointY[0] < ball.y && ball.y < block.y + block.pointY[2]){
            normal = (float)Math.PI/2.0f;//lodr�tt studs
        } else{//titta vilket h�rn den ligger i :D
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
            //r�kna vinkeln mellan bollen och h�rnet

           double angleToCorner = getAngle(block.x+block.pointX[corner]-ball.x,block.y+block.pointY[corner]-ball.y);
           normal = (float)(angleToCorner + Math.PI/2.0f);
        }
       bounceShrimp(ball, block,normal,factor);
                   
    }
 //----------------------------------------------------------------------------- 

public boolean shrimpCollidesWithMap(Map map, Shrimp shrimp){
    for(int i = 0;i<map.tiles.size();i++){
        if(map.tiles.getShrimp(i).collidesWithShrimp(shrimp))//om den kolliderar med n�t i mapen
            return true;
    }
    return false; //om den har g�tt igenom allt utan att tr�ffa p� n�got
}

public boolean shrimpCollidesWithMap(Map map, Shrimp shrimp, int border){//den som ska bli den slutgiltiga
 //kordinat systemet R ju upp och ner ju :(
    /*   
   switch(border){//vilka puknter man ska anv�nda och s�
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
    //kontrollera att alla p� samma rad har samma axel!
    
    
    //shrimpens utg�ngspositioner f�r kollisionshanteringen
    float y1 = shrimp.y + shrimp.pointY[2];//nedrev�nstra
    float x1 = shrimp.x + shrimp.pointX[2];
    
    float x2 = shrimp.x + shrimp.pointX[3];//nedreh�gra
 
        for(int i = 0;i<map.tiles.size();i++){
           Shrimp tile = map.tiles.getShrimp(i);
           float xL =  tile.x + tile.pointX[2];//nedre v�nstra
           float xR =  tile.x + tile.pointX[1];//nedre h�gra
           float yU =  tile.y + tile.pointY[1];
           float yD =  tile.y + tile.pointY[3];//�vre h�gra

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
                    (shrimp.y<tile.y)//och �r ovanf�r
                  ){ 
                    return true;
                   }
        }
    
    return false; //om den har g�tt igenom allt utan att tr�ffa p� n�got
    

}

public void fpsStopper(){//sp�rrar fpsen//m�ste anv�nda sig av startFpsStopper() i b�rjan p� tr�den
readyTime = System.currentTimeMillis();
    try{
        game.thread.sleep(fpsLimit - (readyTime - startTime));//g�r s� att alla f�r ungef�r samma fps
    }catch(Exception e){}//om den inte kan h�lla tiden s� strunta i dt, dt R ju bara ett m�l man vill f�lja...

}

public void startFpsStopper(){
    startTime = System.currentTimeMillis();
}

}