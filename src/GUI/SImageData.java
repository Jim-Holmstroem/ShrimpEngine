/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

/**
 *
 * @author SlimJim xP
 */
public class SImageData {//göra det till en privat class i image ist??
    public int[] data;
    public int w,h;
    
    public SImageData(int[] data, int width, int height){//constructs image data from int[] data width specified size
        this.data = data;
        this.w = width;
        this.h = height;
    }
    public SImageData(int width, int height){//constructs empty data width specified size
        this.data = new int[width*height];
        this.w = width;
        this.h = height;
    }
    
    public SImageData[] split(int rows, int cols){
        return split(this,rows,cols);
    }
    public static SImageData[] split(SImageData imgdata, int rows, int cols){
        SImageData[] resdata = new SImageData[rows*cols];
        
        int fw = imgdata.w/cols; //frame dimensions
        int fh = imgdata.h/rows;
            
        
        for (int i = 0; i < rows*cols; i++) {
            resdata[i] = new SImageData(fw,fh);
        }
        System.out.print("starting to split..");
        for (int i = 0; i < resdata.length; i++){ //  col = i%cols; row = i/cols
            SImageData idata = resdata[i];int next = 0;//next - a counter som håller rätt på vilken som man ska lägga in pixeln i
            int[] framedata = new int[fh*fw];
            
            //first pixelrow = frame.height*row = idata.h*row = idata.h*(i/cols)
            //last pixelrow =  frame.height*(row+1)-1 = idata.h*(row+1)-1 = idata.h*(i/cols + 1) - 1 ={when using <}=idata.h*(i/cols + 1)
            //first pixelcol = frame.width*col = idata.w*col = idata.w*(i%cols)
            //last pixelcol =  frame.width*(col+1)-1 = idata.w*(col+1)-1 = idata.w*(i%cols + 1) - 1 ={when using <}=idata.w*(i%cols + 1)
            
            System.out.println(fh*(i/cols) + "->" + fh*(i/cols+1));
            
            for (int j = fh*(i/cols); j < fh*(i/cols+1); j++) {//loop trou the choosen pixelrows,(from the first too the last)
                for (int k = fw*(i%cols); k < fw*(i%cols+1); k++) {
                    framedata[next++] = imgdata.data[j*fw + k];
                }
            }
    
            idata.data = framedata;
        }
        System.out.println("OK");
        return resdata;
    }
    
    public int getPixel(int x,int y){
        return data[y*w+x];
    }
    
    public int size(){
        return data.length;
    }
    
}
