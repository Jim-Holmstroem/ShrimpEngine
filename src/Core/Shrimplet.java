package Core;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class Shrimplet extends MIDlet {
        public SWindowHandler winhand;

        public Shrimplet() {
            
        }
        public void startApp() {
            System.out.println("startApp()");
            winhand = new SWindowHandler(this);//skickar iväg sig själv så att game kan plocka upp den
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
            return Display.getDisplay(this).numColors();//hämnta antalet färger i mobilskärmen
        }       
        public void terminateGame() {//stänger av spelet                        
            Display.getDisplay(this).setCurrent(null);//laddar in null
            try{
                destroyApp(true);  //kör det som ska köras när spelet stängs av
            }catch(MIDletStateChangeException MIDex){}
            notifyDestroyed (); //säg till alla andra att den har stängts av
        }   
}