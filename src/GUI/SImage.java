/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

import SGraphics.GUIObject;


/**
 *
 * @author SlimJim xP
 */
public class SImage extends GUIObject{
    private Image img;
    
    public SImage(){
    
    }
    public SImage(String filename){
        loadImage(filename);
    }
    public SImage(Image img){
        setImage(img);
    }
    
    public Image getImage(){
        return img;
    }
     
    public void loadImage(String filename){
        try{
            img = Image.createImage("/pic/"+filename); //ladda in bilden
            w = img.getWidth();
            h = img.getHeight();
            
        }catch(Exception e){
            System.out.println("Error creating Image \""+ filename +   "\": "+e.getMessage());
        }
   }
    
    public void setImage(Image img){
        this.img = img;
    }
    
    public void paint(Graphics g){
        if(img!=null)
            g.drawImage(img, x, y, Graphics.TOP|Graphics.LEFT);
        if(marked)
            paintMarking(g);
    }
    
}