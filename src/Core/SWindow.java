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

    static protected Vector winds;
    
    protected Vector guiobj = new Vector();
   
    public SWindow(){
        super(true);
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
    
    
}
