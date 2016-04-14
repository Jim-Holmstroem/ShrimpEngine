/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Menu;

/**
 *
 * @author SlimJim xP
 */


import javax.microedition.lcdui.Graphics;


import GUI.GUIObject;
import SGraphics.ReSizer;


public class SMenu extends GUIObject{

    private SMenuObject[] menuobjs;
    
    public SMenu(SMenuObject[] menuobjs){
        this.menuobjs = menuobjs;
        setPosition(0.0f,0.0f);
        setPosObjects();
    }
    
    public void setPosition(float x,float y){
        super.setPosition(x, y);
        setPosObjects();//flyttar med alla menuobjecten
    }
    
    public void setPosObjects(){
        if(menuobjs!=null)
            for(int i = 0;i<menuobjs.length;i++){
                menuobjs[i].setPosition(ReSizer.ireX(x), ReSizer.ireY(y + i*menuobjs[i].getHeight()));
               // menuobjs[i].setPosition(0.5f,0.1f*i);
               // System.out.println("h:"+menuobjs[i].getHeight());
               // System.out.println(i + ':' + menuobjs[i].getX() + "." + menuobjs[i].getY());
                System.out.println("x:"+x);
                System.out.println("i*h:"+i*menuobjs[i].getHeight());
                
                System.out.println(ReSizer.ireX(x) + "---" + ReSizer.ireY(y + i*menuobjs[i].getHeight()));
                
            }
    }
    public void paint(Graphics g){
        for(int i = 0;i<menuobjs.length;i++){
                menuobjs[i].paint(g);
        }
    }
    
}
