/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

import Core.Core;


/**
 *
 * @author SlimJim xP
 */
public class SImage extends GUIObject{
    protected Image img;
    
    public SImage(){
    
    }
    public SImage(String filename){//skapar en ny image
        loadImage(filename);
    }
    public SImage(Image img,boolean wantCopy){//lägger in bilden som en kopia eller bara som en referens
        if(wantCopy)
            setImage(img);
        else
            setImage(Image.createImage(img));
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
        w = this.img.getWidth();
        h = this.img.getHeight();
    }
    
    public void paint(Graphics g){
        if(img!=null&&visible)
            g.drawImage(img, x, y, Graphics.TOP|Graphics.LEFT);
        if(marked)
            paintMarking(g);   
    }    
}