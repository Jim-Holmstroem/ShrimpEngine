import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Shrimp extends javax.microedition.lcdui.game.Sprite{
   
   //--------[standard]--------------------------------------
   public float x,y, //positionen f�r Shrimpen, blir inte dt f�rrens den blir updaterad
                x2,y2, //extra position utifall Shrimpen ska rotera lr n�got kring en fast punkt
                vx,vy,  //hastigheten p� shrimpen
                ax,ay;  //accellerationen f�r shrimpen
   
   public int status,status2; //vilken status Shrimpen har ex. NORMAL, DYING, RUNNING, FLYING, 2st statusar, utifall man har tv� olika statusar
   public int height,width;//bredden och h�jden p Shrimpen
   public int counterA;//r�knare till dt mesta, Ex.(if(counterA--<0))
   public double w;//om den ska snurra lr �ka fram � tbx lr dyligt Ex.(shrimp.x = Math.sin((w+=Math.PI/128)); )
   
   //------------[finalstatus]----------------
   final static int BOTH_DIE = 0; //etc. bygg p med mera sen!
   
   //------------[world]--------------------
   public boolean inWorld = false;//om den finns i worlden lr om den R frist�ende
   public static float worldX, //worldens position, finns bara en
                       worldY;
   
   //--------[animering]------------------
   public int animSpeed; //hundradels uppdateringar, allts� 100 medf�r updatering per frame, 50 varannan
   public int animProgress; //hur l�ngt den har kommit i updateringen byter frame varje g�ng den kommer upp till 100
   
   public int[] pointX = new int[4];//dom fyra h�rnens position utifr�n mitten
   public int[] pointY = new int[4];
  
   public boolean bounced = false;
   public boolean freeFalling = true;
  
   
   //-----[tabort?]--------------------------
   public boolean isTile = false;//ska man ens anv�nda sig av dt d�ra? <tabort?>
   
   
   //-------[extra]----------------------//anv�nds bara i just det h�r spelet
   public int points; //hur mycket shrimpen R v�rde i po�ng
   public int life;//hur mycket liv den har
      
  
   public Shrimp(Image image){
       super(image);//�rver fr�n sprite klassen
       height = image.getHeight();//s�tt h�jden och bredden utifr�n bildens storlek
       width = image.getWidth();
       defCollisionRect(); //beh�vs den d�r?? g�r den inte dt automatiskt i super()?
       defPoints();//s�tt vart h�rnen �r
   }  

   public Shrimp(Image image,int frameWidth,int frameHeight){
       super(image,frameWidth,frameHeight);//�rver fr�n sprite klassen
       height = frameHeight;//s�tt bredd och h�jd utifr�n framens bredd och h�jd
       width = frameWidth;
       animSpeed = 100; //s�tt animationen hastighet
       defPoints();//s�tt vart h�rnen �r
   }
   
   
 private void defPoints(){//s�tter ut alla h�rn
    pointX[0] = -width/2; //�vrev�nstra
    pointY[0] = -height/2;
    pointX[1] = width/2;  //�vreh�gra
    pointY[1] = -height/2;
    pointX[2] = -width/2; //nedrev�nstra
    pointY[2] = height/2;
    pointX[3] = width/2;  //nedre h�gra
    pointY[3] = height/2;
}
   
 public void paintDebug(Graphics g){//ritar allt som kan t�nkas hj�lpa// bara att kommentera bort dt som R iv�gen    
    paint(g);//ritar ut bilden
    g.setColor(255,255,255);//vit
    if(inWorld){// om den �r i worlden s� rita den utifr�n det
            g.drawArc((int)(x+worldX)-2,(int)(y+worldY)-2,4,4,0,360);//prick i mitten
            g.drawLine((int)(x+worldX)-width/2,      (int)(y+worldY)-height/2,        (int)(x+worldX)+width-width/2,   (int)(y+worldY)-height/2);       //ritar ut alla kanterna
            g.drawLine((int)(x+worldX)-width/2,      (int)(y+worldY)-height/2,        (int)(x+worldX)-width/2,         (int)(y+worldY)+height-height/2);
            g.drawLine((int)(x+worldX)+width-width/2,(int)(y+worldY)-height/2,        (int)(x+worldX)+width-width/2,   (int)(y+worldY)+height-height/2);
            g.drawLine((int)(x+worldX)-width/2,      (int)(y+worldY)+height-height/2, (int)(x+worldX)+width-width/2,   (int)(y+worldY)+height-height/2);

            g.setColor(100,100,100);//gr�
            for(int i = 0;i<=3;i++){//ritar ut alla h�rnen
                g.drawArc((int)(x+worldX)+pointX[i]-2,(int)(y+worldY)+pointY[i]-2,4,4,0,360);
            }
        }else{
            g.drawArc((int)x-2,(int)y-2,4,4,0,360);//prick i mitten
            g.drawLine((int)x-width/2,      (int)y-height/2,        (int)x+width-width/2,   (int)y-height/2);       //ritar ut alla kanterna
            g.drawLine((int)x-width/2,      (int)y-height/2,        (int)x-width/2,         (int)y+height-height/2);
            g.drawLine((int)x+width-width/2,(int)y-height/2,        (int)x+width-width/2,   (int)y+height-height/2);
            g.drawLine((int)x-width/2,      (int)y+height-height/2, (int)x+width-width/2,   (int)y+height-height/2);

            g.setColor(100,100,100);//gr�
            for(int i = 0;i<=3;i++){//ritar ut alla h�rnen
                g.drawArc((int)x+pointX[i]-2,(int)y+pointY[i]-2,4,4,0,360);
        }
    }
} 
 public void defCollisionRect(){//tror inte att den h�r beh�vs eftersom den g�r dt .sprite g�rdt automatiskt
 defineCollisionRectangle(0,0,width,height);//definer efter hur stor bilden �r om inget v�rde skickas med
 }
 
 public boolean collidesWithShrimp(Shrimp shrimp){//pixelperfect kollision med shrimpen
    return collidesWith((Sprite)shrimp,true);
 }

  public boolean collidesWithShrimp(ShrimpList shrimplist){//pixelperfect kollision med shrimplist
    
      //om n�gon av shrimparna i listan tr�ffar shrimpen s� returnera true
      for(int i = 0;i<shrimplist.size();i++){
            if(collidesWithShrimp(shrimplist.getShrimp(i)))
                return true;
      }
      
      return false;
 }
 
 public void collision(Shrimp shrimp,int collisionType){//g�r en switch-sats med allt som kan h�nda tex. vilken som blir f�rst�rd vilken som studsar lr om n��gon av dom ska stanna osv... (bara f�r att den ska bli enklare f�r nyb�rjare att programmera)
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
