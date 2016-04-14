package SGraphics;

/**
 *
 * @author SlimJim xP
 */

import javax.microedition.lcdui.Graphics;

import javax.microedition.lcdui.Font;

public class SGraphics{
    static Graphics g;
    
    public static void setGraphics(Graphics g){
        SGraphics.g = g;
        SGraphics.setFont(Font.getDefaultFont());
    }
    
    public static void fillRect(float  x, float y, float w, float h){
        g.fillRect(ReSizer.reX(x), ReSizer.reY(y), ReSizer.reX(w), ReSizer.reY(h));
    }
    public static void drawRect(int  x, int y, int w, int h){
        g.drawRect(ReSizer.reX(x), ReSizer.reY(y), ReSizer.reX(w), ReSizer.reY(h));
    }
    public static void drawLine(int  x1, int y1, int x2, int y2){
        g.fillRect(ReSizer.reX(x1), ReSizer.reY(y1), ReSizer.reX(x2), ReSizer.reY(y2));
    }
    public static void setStrokeStyle(int strokestyle){
        g.setStrokeStyle(strokestyle);
    }
    public static void setColor(int color){
        g.setColor(color);
    }
    public static void setColor(int r,int g,int b){
        System.out.println("not supported yet!!");
    }
    public static void setFont(Font font){
        g.setFont(font);
    }
       
}