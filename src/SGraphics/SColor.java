/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SGraphics;

/**
 *
 * @author SlimJim xP
 */
public class SColor {
    int alfa,red,green,blue;
    int color;
    
    public SColor(int color){        
          // color =  ARGB (hh,hh,hh,hh) = (A,R,G,B)
         alfa =  (color    )>>>24; //(ARGB    )>>>24 = 000A
         red =   (color<<8 )>>>24; //(ARGB<<8 )>>>24 = RGB0>>>24 = 000R
         green = (color<<16)>>>24; //(ARGB<<16)>>>24 = GB00>>>24 = 000G
         blue =  (color<<24)>>>24; //(ARGB<<24)>>>24 = B000>>>24 = 000B  
         this.color = color;
    }
    
    public SColor(int alfa,int red,int green,int blue){
         this.alfa =  alfa;
         this.red =   red;
         this.green = green;
         this.blue =  blue;
         this.color = alfa<<24 | red<<16 | green<<8 | blue;
    }
    
    public SColor(int[] cc){//colorcomponents in array
        if(cc.length == 4){
            this.alfa =  cc[0];
            this.red =   cc[1];
            this.green = cc[2];
            this.blue =  cc[3];
            this.color = cc[0]<<24 + cc[1]<<16 + cc[2]<<8 + cc[3];
        }else{
            System.out.println("must have 4 color components. Color not inited.");
        }
    }
    
    public SColor(){
        alfa = 0;red = 0;green = 0;blue = 0;
    }
    
    public int getColor(){
        return color;
    }
    public int[] getComponents(){
        return new int[]{alfa,red,green,blue};
    }
    
    public static SColor mix(SColor a,SColor b){
        int[] ac = a.getComponents();
        int[] bc = b.getComponents();
        int[] sc = new int[4];//the sum of the components
        for (int i = 0; i < sc.length; i++) {
            sc[i] = (ac[i] + bc[i])/2;
        }
        return new SColor(sc);
    }
    
    public static SColor bilinearmix(SColor a,SColor b,SColor c, SColor d){//the med value of all channels ARGB
        return new SColor((a.alfa+b.alfa+c.alfa+d.alfa)/4,(a.red+b.red+c.red+d.red)/4,(a.green+b.green+c.green+d.green)/4,(a.blue+b.blue+c.blue+d.blue)/4);
    }
    public static SColor bilinearmix(SWColor[] a){
        SColor s = new SColor();
        
        for (int i = 0; i < a.length; i++) {
            if(a[i].weight==0)
                return a[i].getSColor();
            
        }
        
        
        
        
        return s;
    }
    
    
    
    public String toString(){
        return "(" + toHex(alfa) + "," + toHex(red) + "," + toHex(green) + ","+ toHex(blue) + ")";
    }
    
    public static String toHex(int a){//only support numbers up to FF
        String ahex = Integer.toHexString(a);
        if(ahex.length()==1)//om den är för kort
            return ("0" + ahex);
        else
            return ahex;
    }

}
