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

import java.util.Vector;

public class SAnimatedImage extends SImage {    
    protected int rows,cols;
    protected int currentFrame = 0;
    private boolean updateOnPaint = true;
    
    //different animation types (int:animationType)
    public final static int STATIC = 0,         //current frame until user changes frame 
    // ??                   ROLLING = 1,        //rolling, taking next frame for every update
                            ANIMATIONS = 2,     //user defined animations
                            STATIC_ANIMATIONS = 3;
    protected int animationType = STATIC; 
    
    private Vector animations = new Vector();
    private SAnimation currentAnim;
    
    private SImageData[] framedata;
    
    public SAnimatedImage() {
        rows = 1;
        cols = 1;      
    }

    public SAnimatedImage(String filename,int rows,int cols){
        this.rows = rows;
        this.cols = cols;
        System.out.print("loading framedata...");
        
        framedata = SImage.getImageData(filename).split(rows, cols);
        
        System.out.println("OK");
        w = framedata[0].w;//den förstas höjd och bredd, alla är lika
        h = framedata[0].h;
    }
    
    public SAnimatedImage(SImageData imgdata,int rows,int cols){
        this.imgdata = imgdata;
        this.w = imgdata.w;
        this.h = imgdata.h;
        this.rows = rows;
        this.cols = cols;
    }
    
    public SAnimatedImage(int rows,int cols){
        this.rows = rows;
        this.cols = cols;
    }
        
    public void setImage(SImageData imgdata){
        setImage(imgdata);
        w/=cols;
        h/=rows;
    }
    
    //Animations
    public void addAnimation(SAnimation anim){animations.addElement(anim);}
    public void removeAnimation(int i){animations.removeElementAt(i);}
    public void removeAnimtion(SAnimation anim){animations.removeElement(anim);}
    public void removeAllAnimations(){animations.removeAllElements();}
    public SAnimation getAnimationAt(int i){return ((SAnimation)animations.elementAt(i));}
    public Vector getAllAnimations(){return this.animations;}
    public void setAnimitionType(int animationType){this.animationType = animationType;}
    public void setAnimation(int i){currentAnim = getAnimationAt(i);}
    
    public void setUpdateOnPaint(boolean updateOnPaint){this.updateOnPaint = updateOnPaint;}
    
    //Frames
    public void setFrame(int frame){
        this.currentFrame = frame;
        this.currentFrame%=cols*rows;
    }
    public void setFrameInAnimation(int frame){setFrame(currentAnim.getRealFrame(frame));}
    public int getFrame(){return this.currentFrame;}
    
    public void update(){
        if(animationType==STATIC){
            
        }
        if(animationType==ANIMATIONS){
            currentAnim.update();
            setFrame(currentAnim.getRealFrame());    
        }
        if(animationType==STATIC_ANIMATIONS){
            setFrame(currentAnim.getRealFrame());
        }
    }
    
    public void paint(Graphics g){
        if(visible){
            if(updateOnPaint)
                update();
            if(framedata[currentFrame].data!=null)            
                g.drawRGB(framedata[currentFrame].data, 0, w, x, y, w, h, true);
            if(marked)
                paintMarking(g);
        }
    }
}