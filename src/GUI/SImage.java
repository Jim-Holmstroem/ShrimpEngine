/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

import Math.Vector2f; 
import Math.Vector3f;
import Math.Math;

import SGraphics.SColor;

/**
 *
 * @author SlimJim xP
 */

public class SImage extends GUIObject{
    //int[] imgdata;
    public SImageData imgdata;
    
    public SImage(){
    
    }
    
    public SImage(String filename, boolean billinear){
        loadImage(filename);
        
        //ta tid
        long time = System.currentTimeMillis();
        if(billinear){
           // resize2(1.5f);
            System.out.print("Billinear:");
        }
        else{
          //  resize(1.5f);
            System.out.print("NearestNeigbour:");
        }
        System.out.println(System.currentTimeMillis()-time + "ms");
    }
    
    public SImage(SImageData imgdata){
            setImage(imgdata);
    }
    
    public void setImage(SImageData imgdata){
        this.imgdata = imgdata;
        this.w = imgdata.w;
        this.h = imgdata.h;
    }
    
    
    public void loadImage(String filename){
    try{
            Image tmpimg = Image.createImage("/pic/"+filename); //ladda in bilden
            w = tmpimg.getWidth();
            h = tmpimg.getHeight();
        
            imgdata = new SImageData(w,h);
            
            tmpimg.getRGB(imgdata.data, 0, w, 0, 0, w, h);
            
        }catch(Exception e){
            System.out.println("Error creating Image \"" + filename +   "\": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static SImageData getImageData(String filename){
    try{
            Image tmpimg = Image.createImage("/pic/"+filename); //ladda in bilden
            int tw = tmpimg.getWidth();
            int th = tmpimg.getHeight();
        
            SImageData idata = new SImageData(tw,th);
            
            tmpimg.getRGB(idata.data, 0, tw, 0, 0, tw, th);
            return idata;
        }catch(Exception e){
            System.out.println("Error creating Image \"" + filename +   "\": "+e.getMessage());
            e.printStackTrace();
            return new SImageData(1,1);//just return a empty image
        }
    }
    
    
    
    private void resize(float ratio){
        int width = (int)(ratio*(float)this.w);
        int height = (int)(ratio*(float)this.h);
        
        System.out.println("Dimension:" + new Vector2f((float)width,(float)height));
        
        SImageData resdata = new SImageData( width, height);
        
              	for(int iy=0; iy<height; iy++){
			for(int ix=0; ix<width; ix++){
                                resdata.data[safe(width*iy + ix,resdata.size())] 
                                        =                                         
                                imgdata.data[safe((this.w*(int)((float)iy/ratio)) + (int)((float)ix/ratio),imgdata.size())];    
                        }
		}
        
                this.w = width;
                this.h = height;
                imgdata = resdata;
        
    }
    
    public void resize2(float ratio){//convert from http://www.codeproject.com/KB/GDI-plus/imageprocessing4.aspx
        
        int width = (int)((float)this.w*ratio);//the new width and height
        int height = (int)((float)this.h*ratio);
        
        SImageData resdata = new SImageData( width, height);
               
        float fraction_x,fraction_y,one_minus_x,one_minus_y;
        int ceil_x,ceil_y,floor_x,floor_y;
        
        SColor c1 = new SColor();
        SColor c2 = new SColor();
        SColor c3 = new SColor();
        SColor c4 = new SColor();
        
        int alfa,red,green,blue;
        int b1,b2;
        
        for (int ix = 0; ix < width; ++ix){
            for (int iy = 0; iy < height; ++iy){
                //setup
                floor_x = (int)Math.floor(ix / ratio);
                floor_y = (int)Math.floor(iy / ratio);
                
                ceil_x = floor_x + 1;
                if (ceil_x >= width) ceil_x = floor_x;
                    ceil_y = floor_y + 1;
                if (ceil_y >= height) ceil_y = floor_y;
                    fraction_x = ix / ratio - floor_x;
                fraction_y = iy / ratio - floor_y;
                one_minus_x = 1.0f - fraction_x;
                one_minus_y = 1.0f - fraction_y;
                
                c1.setColor(imgdata.data[safe(floor_x + floor_y*w,imgdata.size())]);
                c2.setColor(imgdata.data[safe(ceil_x + floor_y*w,imgdata.size())]);
                c3.setColor(imgdata.data[safe(floor_x + ceil_y*w,imgdata.size())]);
                c4.setColor(imgdata.data[safe(ceil_x + ceil_y*w,imgdata.size())]);
                
                //alfa
                b1 = (int)(one_minus_x * c1.getAlfa() + fraction_x * c2.getAlfa());
                b2 = (int)(one_minus_x * c3.getAlfa() + fraction_x * c4.getAlfa());           
                alfa = (int)(one_minus_y * (double)(b1) + fraction_y * (double)(b2));                
                
                //red
                b1 = (int)(one_minus_x * c1.getRed() + fraction_x * c2.getRed());
                b2 = (int)(one_minus_x * c3.getRed() + fraction_x * c4.getRed());           
                red = (int)(one_minus_y * (double)(b1) + fraction_y * (double)(b2));
                
                //green
                b1 = (int)(one_minus_x * c1.getGreen() + fraction_x * c2.getGreen());
                b2 = (int)(one_minus_x * c3.getGreen() + fraction_x * c4.getGreen());           
                green = (int)(one_minus_y * (double)(b1) + fraction_y * (double)(b2));
               
                //blue
                b1 = (int)(one_minus_x * c1.getBlue() + fraction_x * c2.getBlue());
                b2 = (int)(one_minus_x * c3.getBlue() + fraction_x * c4.getBlue());           
                blue = (int)(one_minus_y * (double)(b1) + fraction_y * (double)(b2));
                
                SColor mc = new SColor(alfa,red,green,blue);
                
               // System.out.println(c1+"&"+c2+"&"+c3+"&"+c4 + "=" + mc);
                
                resdata.data[safe(width*iy + ix,resdata.size())] = mc.getColor();                                        
            }    
        }
        this.w = width;
        this.h = height;
        imgdata = resdata;
    }
    
    private int safe(int value, int max){
       while(value<=0)//om den är negativ addera upp den till positiv
           return 0;
       return value%max;//gör så att den hamnar inom max
    }
       
    public void paint(Graphics g){
        if(imgdata!=null)
            g.drawRGB(imgdata.data, 0, w, x, y, w, h, true);
        if(marked)
            paintMarking(g);
    }
}