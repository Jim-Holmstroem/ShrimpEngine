/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

/**
 *
 * @author SlimJim xP
 */

public class SAnimation {
    private int upf;    //upf - updates per framechange
    private int counter = 0;
    private int[] animSeq;
    private int frame;
    
    public SAnimation(int[] animSeq,int upf){
        this.animSeq = animSeq;
        this.upf = upf;
    }
    
    public void setUPF(int upf){
        this.upf = upf;
    }
    public int getUPF(){
        return this.upf;
    }
    
    public void setAnimationSeq(int[] animSeq){
        this.animSeq = animSeq;
    }
    public int[] getAnimationSeq(){
        return this.animSeq;
    }
    public int getFrame(){//get the frame in the animaition SE ALSP int:getRealFrame()
        return frame;
    }
    public int getRealFrame(){//get the frame represented int the animatedimage
        return animSeq[this.frame];
    }
    public int getRealFrame(int frame){
        return animSeq[frame%=animSeq.length];
    }
    public int getAnimLenght(){
        return animSeq.length;
    }
    
    public void setFrame(int frame){
        frame%=animSeq.length;
        this.frame = frame;
    }
    
    public void nextFrame(){
        frame++;
        frame%=animSeq.length;
    }
    
    public void update(){
        if(++counter>=upf){
            nextFrame();
            counter = 0;
        }
    }
    
}