package GUI;

import javax.microedition.lcdui.*;

public class BarGraph {
    public int value,max,x,y,height,width;

    public BarGraph() {
        value = 0;
        max = 100;
        height = 10;
        width = 60;
        x = 0;
        y = 0;    
    }

    public void draw(Graphics g){
        g.setColor(255,255,255); //färg = vit
        g.drawRect(x,y,width,height); //ritar ramen
        g.setColor(52,250,100); //byter färg till grönt
        g.fillRect(x+1,y+1,(width-1)*value/max,height-1);   //ritar själva stapeln
    }
}
