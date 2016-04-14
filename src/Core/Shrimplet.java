package Core;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class Shrimplet extends MIDlet {
        public SWindowHandler winhand;

        public Shrimplet() {
            
        }
        public void startApp() {
            System.out.println("startApp()");
            winhand = new SWindowHandler(this);//skickar iv�g sig sj�lv s� att game kan plocka upp den
        }
        
        public void setDisplay(SWindow disp){
            Display.getDisplay(this).setCurrent(disp);
        }        
        public void pauseApp() {
            System.out.println("pauseApp()");
        }
        public void destroyApp(boolean unconditional)throws MIDletStateChangeException{
            System.out.println("destroyApp()");
        }
        
        public void vibrate(int duration){
            Display.getDisplay(this).vibrate(duration);//vibrera
        }
        public void flashScreen(int duration){
            Display.getDisplay(this).flashBacklight(duration);
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