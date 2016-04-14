package Core;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import Game.Game;

public class Shrimplet extends MIDlet {
        public Game canvas;
 
        public Shrimplet() {
            canvas = new Game(this);//skickar iv�g sig sj�lv s� att game kan plocka upp den
        }
        public void startApp() {
            
            System.out.println("Oscar �r b�st!!start");
            
            Display.getDisplay(this).setCurrent(canvas);//starta spelet
        }
        public void pauseApp() {
            
            System.out.println("pause");
        }
        public void destroyApp(boolean unconditional)throws MIDletStateChangeException{
            System.out.println("destroy");
        }
        
        public void vibrate(int duration){
            Display.getDisplay(this).vibrate(duration);//vibrera
        }
        public int numColors(){
            return Display.getDisplay(this).numColors();//h�mnta antalet f�rger i mobilsk�rmen
        }       
        
        public void terminateGame() {//st�nger av spelet                        
            Display.getDisplay(this).setCurrent(null);//laddar in null
            try{
                destroyApp(true);  //k�r det som ska k�ras n�r spelet st�ngs av
            }catch(MIDletStateChangeException MIDex){}
            notifyDestroyed (); //s�g till alla andra att den har st�ngts av
        }   
}