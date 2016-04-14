/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

/**
 *
 * @author SlimJim xP
 */
import javax.microedition.lcdui.Graphics;

import GUI.GUIObject;


public class StatusBar extends GUIObject{
    private int value,steps;
    
    
    public StatusBar(){
    
    }
    
    public void setSteps(int steps){
        this.steps = steps;
    }
    
    public void add(){
        if(this.value<this.steps)
            value++;
    }
    
    public void add(int n){    
        value+=n;
        if(this.value>this.steps)
            this.value=this.steps;
        if(this.value<0)
            this.value=0;
        
    }
    
    public void setValue(int value){
        this.value = value;
        if(this.value>this.steps)
            this.value=this.steps;
        if(this.value<0)
            this.value=0;
    }
    
    public int getValue(){
        return value;
    }
    
    public boolean isFull(){
        return (value>=steps);
    }
    
    public void paint(Graphics g){
        g.setColor(0xFF00FF00);
        g.fillRect(x, y,(int)(w*(float)((float)value/(float)steps)),h);
        g.setColor(0xFFFFFFFF);
        g.setStrokeStyle(Graphics.SOLID);
        g.drawRect(x, y, w, h);
        if(marked)
            this.paintMarking(g);
    }
    
}
