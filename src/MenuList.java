/*            [MenuObject.java]
 *
 *  a menu consisting of menuobjects
 *  
 *
 * 
 *  @Author: Jim 'SlimJim' Holmstr�m for Shrimp Gaming UF
 */

import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class MenuList extends Vector{ 
    
    public Shrimp shrimp = null; //Shrimpen som ritar ut alla menuobjecten
    public int focused = 0; //vilken av objecten som �r focuserade
    static int UNFOCUS = 0,
               FOCUS = 1,
               NOTAVALIBLE = 2;
    
    public MenuList(String fileName,int frameHeight,int frameWidth){ //ladda in bilden som man ska ha till menyn
        shrimp = new Shrimp(LoadImage(fileName),frameHeight,frameWidth);
    }
 
    public void add(MenuObject menuObject){//l�gg till menyobject
       super.addElement(menuObject);
    }
    public void delete(){//tar bort alla meny object
        super.removeAllElements();
    }
    
    public void draw(Graphics g){//ritar ut menyn
        for (int i = 0; i<super.size();i++){//r�kna upp listan
            getMenuObj(i).draw(g,shrimp,i);//ritar ut r�tt frame p� r�tt st�lle (alla kommer fr�n samma bild(shrimpen))
        }
    }
    
    public MenuObject getMenuObj(int i){//h�mtar ett menyobject
        return (MenuObject)super.elementAt(i);
    }
    
    private Image LoadImage(String filename){//ladda in en bild, n�r den ska ladda in bilden kan den ju passa p� att g�ra den i r�t storlek atomatiskt
    try{
        return Image.createImage("/pic/"+filename); //ladda in bilden
    }catch(Exception e){System.out.println("Error creating Image + \""+ filename +"\" : "+e.getMessage());}//f� ut ett error meddelande i output
        return null; //returnera null om dt blir fel, g�r dt att returnera en tom ruta ist, s� slipper hela prgmet h�nga sig
   }

}