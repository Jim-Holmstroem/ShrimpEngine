/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

/**
 *
 * @author SlimJim xP
 */

import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

import GUI.GUIObject;
import GUI.SLabel;

import Core.SWindowHandler;
import SGraphics.SGraphics;


public class SWindow extends GameCanvas{    
    protected Vector guiobj = new Vector();
    static SWindowHandler winh;
    
    Question q;boolean asking = false;
    
    
    public SWindow(){
        super(true);
        setFullScreenMode(true);
        q = new Question(getGraphics());
    }
    
    public static void setWindowHandler(SWindowHandler winh){
        SWindow.winh = winh;
    }
    
    public void paint(Graphics g){
        for(int i = 0;i<guiobj.size();i++){
            ((GUIObject)guiobj.elementAt(i)).paint(g);
        }
    }
    public void add(GUIObject obj){
        guiobj.addElement(obj);
    }
    public void remove(int i){
        guiobj.removeElementAt(i);
    }
    public boolean remove(GUIObject obj){
        return guiobj.removeElement(obj);
    }
    public int numberOfObjects(){
        return guiobj.size();
    }
    public void init(){
        System.out.println("WARNING!! - init() must be overriden");
    }
    public void draw(){
        System.out.println("WARNING!! - draw() must be overriden");
    }
    public void keyPressed(int code){
        
        //System.out.println("pushed:" + getKeyName(code));
        
        switch(code){
            case GameCanvas.KEY_NUM2:
                System.out.println("down");
                askMark(-1);
            break;
            case GameCanvas.KEY_NUM8:
                System.out.println("upp");
                askMark(1);
            break;
            default:
            break;
        }
        
    }
    public void keyReleased(int code){
        System.out.println("released");
    }
    public void keyRepeated(int code){
        System.out.println("repeated");
    }
    
    public void ask(String question,String[] options){
        q.ask(question, options);
    }
    
    protected void askMark(int move){
        q.pos += move;//hur mkt man ska röra markören med
    }
    
    class Question{
        Graphics g;
        boolean running = true;
        long oldTime,mspu = 50;
        int pos;
        
        public Question(Graphics g){
            this.g = g;
        }
        
        public void ask(String question,String[] options){
            SLabel[] objs = new SLabel[options.length+1];
        
            objs[0] = new SLabel(question);
        
            for (int i = 1; i < objs.length; i++) {
                objs[i] = new SLabel();
                objs[i].setText(options[i-1]);
                objs[i].recalc();
                
                objs[i].setPosition(0.2f, 0.1f*i);
                System.out.println(objs[i]);
            }

            while(running){
                oldTime  = System.currentTimeMillis();
                SGraphics.setColor(0xFF000000);
                SGraphics.fillRect(0.0f, 0.0f, 1.0f, 1.0f);
    
                for(int i = 0;i<objs.length;i++){
                    objs[i].unmark();
                    objs[i].paint(g);
                }
                objs[pos].mark();
                System.out.println(pos);
                flushGraphics(); //målar buffern p skärmen
                System.gc();//<--rensar skräp //finns dt några nackdelar???(processorkraft?)
                try{
                    Thread.sleep(mspu - (System.currentTimeMillis()-oldTime));
                    oldTime = System.currentTimeMillis();
                }catch(Exception e){}
            }
        }
    }
}