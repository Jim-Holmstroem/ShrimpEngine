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

import SGraphics.GUIObject;

public class SWindow {

    protected Vector guiobj = new Vector();
   
    public void paint(Graphics g){
        for(int i = 0;i<guiobj.size();i++){
            System.out.println("draws:"+guiobj.elementAt(i).getClass().getName());
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
