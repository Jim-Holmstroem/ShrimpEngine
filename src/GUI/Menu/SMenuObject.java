/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Menu;


import javax.microedition.lcdui.Image;

import GUI.SAnimatedImage;
import GUI.SAnimation;


/**
 *
 * @author SlimJim xP
 */
public class SMenuObject extends SAnimatedImage{
    
    public final static int UNMARKED=0,MARKED=1,UNAVALIBLE=2;
    private int status = UNMARKED;
    
    
    public SMenuObject(String filename,int rows,int cols,SAnimation anim){//the image is just a reference to the big image, all menuobjects have the same
        super(filename, rows,cols);
        animationType = STATIC_ANIMATIONS;
        
        addAnimation(anim);
        setAnimation(0);
        setUpdateOnPaint(false);
        update();
    }
    
    public SMenuObject(int rows,int cols,SAnimation anim){//the image is just a reference to the big image, all menuobjects have the same
        super(rows,cols);
        
        animationType = STATIC_ANIMATIONS;
        
        addAnimation(anim);
        setAnimation(0);
        
        setUpdateOnPaint(false);
        update();
    }
    
    
    
    
    public void setStatus(int status){
        this.status = status;
        setFrameInAnimation(status);
        update();
    }
    
    
}