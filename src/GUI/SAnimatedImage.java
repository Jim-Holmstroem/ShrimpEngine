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
import javax.microedition.lcdui.Image;

import java.util.Vector;


public class SAnimatedImage extends SImage {    
    private int rows,cols;

    private int currentFrame = 0;
    private boolean updateOnPaint = true;
    
    //different animation types (int:animationType)
    public final static int STATIC = 0,         //current frame until user changes frame 
    // ??                   ROLLING = 1,        //rolling, taking next frame for every update
                            ANIMATIONS = 2;     //user defined animations
            
    private int animationType = STATIC; 
    
    private Vector animations = new Vector();
    private SAnimation currentAnim;
    
    public SAnimatedImage() {
        rows = 1;
        cols = 1;       
    }

    public SAnimatedImage(String filename,int rows,int cols){
        this.rows = rows;
        this.cols = cols;
        
        loadImage(filename);
        
        System.out.println("before;"+"w:"+w+" h:"+h);
        
        w/=cols;
        h/=rows;
        
        System.out.println("w:"+w+" h:"+h);
        
    }
    
    public SAnimatedImage(Image img,int rows,int cols,boolean wantCopy){
        this.rows = rows;
        this.cols = cols;
        if(wantCopy)
            setImage(img);
        else
            setImage(Image.createImage(img));
        w/=cols;
        h/=rows;
    }
    
    public void addAnimation(SAnimation anim){
        animations.addElement(anim);
    }
    public void removeAnimation(int i){
        animations.removeElementAt(i);
    }
    public void removeAnimtion(SAnimation anim){
        animations.removeElement(anim);
    }
    public void removeAllAnimations(){
        animations.removeAllElements();
    }
    public SAnimation getAnimationAt(int i){
        return ((SAnimation)animations.elementAt(i));
    }
    public Vector getAllAnimations(){
        return this.animations;
    }
    
    public void setUpdateOnPaint(boolean updateOnPaint){
        this.updateOnPaint = updateOnPaint;
    }
    
    public void setAnimitionType(int animationType){
        this.animationType = animationType;
    }
    
    public void setFrame(int frame){
        this.currentFrame = frame;
    }
    
    public int getFrame(){
        return this.currentFrame;
    }
    
    public void update(){
        currentFrame++;
    }
    
    public void paint(Graphics g){
        if(updateOnPaint)
            update();
        if(img!=null){    
            g.drawRegion(img, w*(currentFrame%cols), h*(currentFrame/cols), w, h, 0, x, y, Graphics.TOP|Graphics.LEFT);
        }
        if(marked)
            paintMarking(g);
    }
}