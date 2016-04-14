/*            [MenuObject.java]
 *
 *  A object in the menu
 *  
 *
 * 
 *  @Author: Jim 'SlimJim' Holmström for Shrimp Gaming UF
 */

import javax.microedition.lcdui.*;

public class MenuObject{  
    public int x,y;
    public int status = 0;
   
    public MenuObject(int x, int y) {//konstruktorn
           this.x = x;
           this.y = y;
    }
    
    public void draw(Graphics g,Shrimp shrimp, int i){
        shrimp.setPosition(x,y);
        shrimp.setFrame(status+3*i);//i är vilken rad, status vilken kolumn (3st i vaje rad)
        shrimp.paint(g);
    }
    
}
