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
    private int[] animSeq;
    
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
    
}