package Core;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import Game.Game;

public class Shrimplet extends MIDlet {
        public Game canvas;
 
        public Shrimplet() {
            canvas = new Game(this);//skickar iväg sig själv så att game kan plocka upp den
        }
        public void startApp() {
            Display.getDisplay(this).setCurrent(canvas);//starta spelet
        }
        public void pauseApp() {
            canvas.gameStatus = Game.PAUSE;
        }
        public void destroyApp(boolean unconditional)throws MIDletStateChangeException{
        }
        
        public void vibrate(int duration){
            Display.getDisplay(this).vibrate(duration);//vibrera
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