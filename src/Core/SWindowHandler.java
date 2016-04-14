/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

/**
 *
 * @author SlimJim xP
 */
import Game.Game;


import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.Graphics;

public class SWindowHandler extends GameCanvas implements Runnable{
    private SWindow[] winds = new SWindow[10];
    private int currentWindow = 0;
    protected Shrimplet parent;
    private Thread thread;
    public Graphics g = getGraphics(); //h�mntar graphics'n
    
    
    //fps handler
    private int mspu = 50;//MilliSecounds Per Update, is a speedlimit, and the program tries to hold it as far as possible
    private long oldTime;
    
    SWindowHandler(Shrimplet parent){
       super(true);
       this.parent = parent;
       SWindow.setWindowHandler(this);
       
       
       winds[0] = new Game();
       
       parent.setDisplay(winds[0]);
    
       thread = new Thread(this); //skapar en ny tr�d
       thread.start(); //startar tr�den
    }
    
    public void paint(Graphics g){
      winds[currentWindow].paint(g);
    }
    
    public void setWindow(int i){
        currentWindow = i;
    }
    public void setWindow(SWindow win){
        parent.setDisplay(win);
    }
    public int currentWindow(){
        return currentWindow;
    }
    public SWindow getCurrentWindow(){
        return winds[currentWindow];
    }
    public SWindow getWindow(int i){
        return winds[i];
    }
    
    public void run(){        
        SWindow win = winds[currentWindow];
        win.init(); //k�r initsieringen ingenting f�re den om det inte m�ste, kontrollera flera g�nger om s� �r faller            
        while(true){
            oldTime  = System.currentTimeMillis();
            try{
                
            win.draw();
            win.flushGraphics(); //m�lar buffern p sk�rmen
            System.gc();//<--rensar skr�p //finns dt n�gra nackdelar???(processorkraft?)
            
                Thread.sleep(mspu - (System.currentTimeMillis()-oldTime));
                oldTime = System.currentTimeMillis();
            }catch(Exception e){
                System.out.println("error in window #" + currentWindow + ": "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
