/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SGraphics;

import javax.microedition.lcdui.Graphics;

public class GUIObject {
    protected int x,y,w,h;
    protected boolean marked=false;
    protected static Graphics g;//behövs denhära här??
    
    
    public static void setGraphics(Graphics g){
        GUIObject.g = g;
    }
    
    
    public void paint(Graphics g){
       
    }
    
    
    public void setPosition(float x,float y){
        this.x = ReSizer.reX(x);
        this.y = ReSizer.reY(y);
    }
    public void setDimension(float w,float h){
        this.w = ReSizer.reX(w);
        this.h = ReSizer.reY(h);
    }

    public void setMarked(boolean marked){this.marked = marked;}
    public void mark(){this.marked = true;}
    public void unmark(){this.marked = false;}
    
    public void paintMarking(Graphics g){
        g.setColor(0xFFFFFFFF);
        g.setStrokeStyle(Graphics.DOTTED);
        g.drawRect(x-1,y-1,w+2,h+2);
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return w;
    }
    public int getHeight(){
        return h;
    }
    
}