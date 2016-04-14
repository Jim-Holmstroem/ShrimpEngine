/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

/**
 *
 * @author SlimJim xP
 */

import javax.microedition.lcdui.Image;

public class Core {

    public static Image loadImage(String filename){//inte säker på om den funkar, kolla vad som R fel på den..
        try {
            return Image.createImage(filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    
    }
    
    
}
