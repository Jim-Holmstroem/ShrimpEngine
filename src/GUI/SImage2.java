/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

import Math.Vector2f;
import Math.Vector3f;


import SGraphics.SColor;

/**
 *
 * @author SlimJim xP
 */

public class SImage2 extends GUIObject{
    int[] imgdata;
    
    
    public SImage2(String filename){
        loadImage(filename);
        
        
        resize(2.0f);
    }
    
    private void loadImage(String filename){
    try{
            Image tmpimg = Image.createImage("/pic/"+filename); //ladda in bilden
            w = tmpimg.getWidth();
            h = tmpimg.getHeight();
        
            tmpimg.getRGB(imgdata = new int[w*h], 0, w, 0, 0, w, h);
            
        }catch(Exception e){
            System.out.println("Error creating Image \""+ filename +   "\": "+e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    
    private int[] resize(float ratio){
        int width = (int)(ratio*(float)this.w);
        int height = (int)(ratio*(float)this.h);
        
        System.out.println("Dimension:" + new Vector2f((float)width,(float)height));
        
        int[] resdata = new int[width*height];
              	for(int iy=0; iy<height; iy++){
			for(int ix=0; ix<width; ix++){
                                resdata[safe(width*iy + ix,resdata.length)] = imgdata[safe((this.w*(int)((float)iy/ratio)) + (int)((float)ix/ratio),imgdata.length)];    
                        }
		}
        
                this.w = width;
                this.h = height;
                imgdata = resdata;
        
		return resdata;
    }
    
    public int bilinear(int [] a){
        
        //separate the colors ARGB from int
        
        
        
        return -1;
    }
    
    
    
    
    private int safe(int value, int max){
       while(value<=0)//om den är negativ addera upp den till positiv
           return 0;
       return value%max;//gör så att den hamnar inom max
    }
    
    
    public void paint(Graphics g){
        if(imgdata!=null)
            g.drawRGB(imgdata, 0, w, x, y, w, h, true);
        if(marked)
            paintMarking(g);
    }
}
