/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SGraphics;

/**
 *
 * @author SlimJim xP
 */
public abstract class ReSizer {
    private static int screenWidth,screenHeight;
    
    public static void getScreenResolution(int width,int height){
        screenWidth = width;
        screenHeight = height;
    }
    
    public static int reX(float x){
        return (int)(x*(float)screenWidth);
    }
    public static int reY(float y){
        return (int)(y*(float)screenHeight);
    }
    public static float ireX(int x){
        return (float)(x/screenWidth);
    }
    public static float ireY(int y){
        return (float)(y/screenHeight);
    }
    
}
