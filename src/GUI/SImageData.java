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
    
    public int size(){
        return data.length;
    }
    
}
