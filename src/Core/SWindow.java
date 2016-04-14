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

public class SWindow extends GameCanvas{    
    protected Vector guiobj = new Vector();
   
    public SWindow(){
        super(true);
        setFullScreenMode(true);       
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
        System.out.println("pushed:" + getKeyName(code));
    }
    public void keyReleased(int code){
        System.out.println("released");
    }
    public void keyRepeated(int code){
        System.out.println("repeated");
    }
}