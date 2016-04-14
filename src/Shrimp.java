import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Shrimp extends javax.microedition.lcdui.game.Sprite{
   
   //--------[standard]--------------------------------------
   public float x,y, //positionen för Shrimpen, blir inte dt förrens den blir updaterad
                x2,y2, //extra position utifall Shrimpen ska rotera lr något kring en fast punkt
                vx,vy,  //hastigheten på shrimpen
                ax,ay;  //accellerationen för shrimpen
   
   public int status,status2; //vilken status Shrimpen har ex. NORMAL, DYING, RUNNING, FLYING, 2st statusar, utifall man har två olika statusar
   public int height,width;//bredden och höjden p Shrimpen
   public int counterA;//räknare till dt mesta, Ex.(if(counterA--<0))
   public double w;//om den ska snurra lr åka fram å tbx lr dyligt Ex.(shrimp.x = Math.sin((w+=Math.PI/128)); )
   
   //------------[finalstatus]----------------
   final static int BOTH_DIE = 0; //etc. bygg p med mera sen!
   
   //------------[world]--------------------
   public boolean inWorld = false;//om den finns i worlden lr om den R fristående
   public static float worldX, //worldens position, finns bara en
                       worldY;
   
   //--------[animering]------------------
   public int animSpeed; //hundradels uppdateringar, alltså 100 medför updatering per frame, 50 varannan
   public int animProgress; //hur långt den har kommit i updateringen byter frame varje gång den kommer upp till 100
   
   public int[] pointX = new int[4];//dom fyra hörnens position utifrån mitten
   public int[] pointY = new int[4];
  
   public boolean bounced = false;
   public boolean freeFalling = true;
  
   
   //-----[tabort?]--------------------------
   public boolean isTile = false;//ska man ens använda sig av dt dära? <tabort?>
   
   
   //-------[extra]----------------------//används bara i just det här spelet
   public int points; //hur mycket shrimpen R värde i poäng
   public int life;//hur mycket liv den har
      
  
   public Shrimp(Image image){
       super(image);//ärver från sprite klassen
       height = image.getHeight();//sätt höjden och bredden utifrån bildens storlek
       width = image.getWidth();
       defCollisionRect(); //behövs den där?? gör den inte dt automatiskt i super()?
       defPoints();//sätt vart hörnen är
   }  

   public Shrimp(Image image,int frameWidth,int frameHeight){
       super(image,frameWidth,frameHeight);//ärver från sprite klassen
       height = frameHeight;//sätt bredd och höjd utifrån framens bredd och höjd
       width = frameWidth;
       animSpeed = 100; //sätt animationen hastighet
       defPoints();//sätt vart hörnen är
   }
   
   
 private void defPoints(){//sätter ut alla hörn
    pointX[0] = -width/2; //övrevänstra
    pointY[0] = -height/2;
    pointX[1] = width/2;  //övrehögra
    pointY[1] = -height/2;
    pointX[2] = -width/2; //nedrevänstra
    pointY[2] = height/2;
    pointX[3] = width/2;  //nedre högra
    pointY[3] = height/2;
}
   
 public void paintDebug(Graphics g){//ritar allt som kan tänkas hjälpa// bara att kommentera bort dt som R ivägen    
    paint(g);//ritar ut bilden
    g.setColor(255,255,255);//vit
    if(inWorld){// om den är i worlden så rita den utifrån det
            g.drawArc((int)(x+worldX)-2,(int)(y+worldY)-2,4,4,0,360);//prick i mitten
            g.drawLine((int)(x+worldX)-width/2,      (int)(y+worldY)-height/2,        (int)(x+worldX)+width-width/2,   (int)(y+worldY)-height/2);       //ritar ut alla kanterna
            g.drawLine((int)(x+worldX)-width/2,      (int)(y+worldY)-height/2,        (int)(x+worldX)-width/2,         (int)(y+worldY)+height-height/2);
            g.drawLine((int)(x+worldX)+width-width/2,(int)(y+worldY)-height/2,        (int)(x+worldX)+width-width/2,   (int)(y+worldY)+height-height/2);
            g.drawLine((int)(x+worldX)-width/2,      (int)(y+worldY)+height-height/2, (int)(x+worldX)+width-width/2,   (int)(y+worldY)+height-height/2);

            g.setColor(100,100,100);//grå
            for(int i = 0;i<=3;i++){//ritar ut alla hörnen
                g.drawArc((int)(x+worldX)+pointX[i]-2,(int)(y+worldY)+pointY[i]-2,4,4,0,360);
            }
        }else{
            g.drawArc((int)x-2,(int)y-2,4,4,0,360);//prick i mitten
            g.drawLine((int)x-width/2,      (int)y-height/2,        (int)x+width-width/2,   (int)y-height/2);       //ritar ut alla kanterna
            g.drawLine((int)x-width/2,      (int)y-height/2,        (int)x-width/2,         (int)y+height-height/2);
            g.drawLine((int)x+width-width/2,(int)y-height/2,        (int)x+width-width/2,   (int)y+height-height/2);
            g.drawLine((int)x-width/2,      (int)y+height-height/2, (int)x+width-width/2,   (int)y+height-height/2);

            g.setColor(100,100,100);//grå
            for(int i = 0;i<=3;i++){//ritar ut alla hörnen
                g.drawArc((int)x+pointX[i]-2,(int)y+pointY[i]-2,4,4,0,360);
        }
    }
} 
 public void defCollisionRect(){//tror inte att den här behövs eftersom den gör dt .sprite gördt automatiskt
 defineCollisionRectangle(0,0,width,height);//definer efter hur stor bilden är om inget värde skickas med
 }
 
 public boolean collidesWithShrimp(Shrimp shrimp){//pixelperfect kollision med shrimpen
    return collidesWith((Sprite)shrimp,true);
 }

  public boolean collidesWithShrimp(ShrimpList shrimplist){//pixelperfect kollision med shrimplist
    
      //om någon av shrimparna i listan träffar shrimpen så returnera true
      for(int i = 0;i<shrimplist.size();i++){
            if(collidesWithShrimp(shrimplist.getShrimp(i)))
                return true;
      }
      
      return false;
 }
 
 public void collision(Shrimp shrimp,int collisionType){//gör en switch-sats med allt som kan hända tex. vilken som blir förstörd vilken som studsar lr om nåågon av dom ska stanna osv... (bara för att den ska bli enklare för nybörjare att programmera)
     switch(collisionType){
         case 0:
         break;
         default:
         break;
     }
 }

 public void init(){
 
 
 }
}
