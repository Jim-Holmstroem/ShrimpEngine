/*
 * a SColor that has a weighted  that is taking in consern when calculating a color mix
 * I don't think I neeed the weighted color for now :)
 */

package SGraphics;

/**
 *
 * @author SlimJim xP
 */
import SGraphics.SColor;


public class SWColor extends SColor{
    float weight;
    
    public SWColor(int color, float weight){
        super(color);
        this.weight = weight;
    }
    
    public SWColor(int alfa,int red,int green,int blue, float weight){
        super(alfa,red,green,blue);
        this.weight = weight;
    }
    public SWColor(int cc[], float weight){
        super(cc);
        this.weight = weight;
    }
    
    public float getWeight(){
        return weight;
    }
    public String toString(){
        return "(" + toHex(alfa) + "," + toHex(red) + "," + toHex(green) + ","+ toHex(blue) + ","+ weight + ")";
    }
    
    public SColor getSColor(){
        return new SColor(color);
    }
}
