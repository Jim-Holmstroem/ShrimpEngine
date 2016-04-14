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
import javax.microedition.lcdui.Image;

import GUI.GUIObject;
import GUI.SImageData;

import SGraphics.ReSizer;
import Core.Core;


public class SMenu extends GUIObject{

    private SMenuObject[] menuobjs;
    private SImageData img;
    
    private int selected;
    
    
    public SMenu(String filename, SMenuObject[] menuobjs){
        img = GUI.SImage.getImageData(filename);
        this.menuobjs = menuobjs;
        
        for(int i = 0;i<menuobjs.length;i++){//kopplar alla så att dom får tillgång till bilden man använder
            menuobjs[i].setImage(img);
        }
        
        setPosition(0.0f,0.0f);
        setPosObjects();
       
    }
    
    public SMenu(SMenuObject[] menuobjs){
        this.menuobjs = menuobjs;
        setPosition(0.0f,0.0f);
        setPosObjects();
    }
    
    public void setPosition(float x,float y){
        super.setPosition(x, y);
        setPosObjects();//flyttar med alla menuobjecten
    }
    
    public void select(int i){
        //unselect old
        menuobjs[selected].setFrameInAnimation(SMenuObject.UNMARKED);
        //select new
        selected = i%menuobjs.length;
        menuobjs[selected].setFrameInAnimation(SMenuObject.MARKED);
    
    }
    
    
    public void setPosObjects(){
        if(menuobjs!=null)
            for(int i = 0;i<menuobjs.length;i++){
                menuobjs[i].setPosition(ReSizer.ireX(x), ReSizer.ireY(y + i*menuobjs[i].getHeight()));  
            }
    }
    public void paint(Graphics g){
        if(visible)
            for(int i = 0;i<menuobjs.length;i++){
                    menuobjs[i].paint(g);
            }
    }
}