/*            [MenuObject.java]
 *
 *  a menu consisting of menuobjects
 *  
 *
 * 
 *  @Author: Jim 'SlimJim' Holmström for Shrimp Gaming UF
 */

import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class MenuList extends Vector{ 
    
    public Shrimp shrimp = null; //Shrimpen som ritar ut alla menuobjecten
    public int focused = 0; //vilken av objecten som är focuserade
    static int UNFOCUS = 0,
               FOCUS = 1,
               NOTAVALIBLE = 2;
    
    public MenuList(String fileName,int frameHeight,int frameWidth){ //ladda in bilden som man ska ha till menyn
        shrimp = new Shrimp(LoadImage(fileName),frameHeight,frameWidth);
    }
 
    public void add(MenuObject menuObject){//lägg till menyobject
       super.addElement(menuObject);
    }
    public void delete(){//tar bort alla meny object
        super.removeAllElements();
    }
    
    public void draw(Graphics g){//ritar ut menyn
        for (int i = 0; i<super.size();i++){//räkna upp listan
            getMenuObj(i).draw(g,shrimp,i);//ritar ut rätt frame på rätt ställe (alla kommer från samma bild(shrimpen))
        }
    }
    
    public MenuObject getMenuObj(int i){//hämtar ett menyobject
        return (MenuObject)super.elementAt(i);
    }
    
    private Image LoadImage(String filename){//ladda in en bild, när den ska ladda in bilden kan den ju passa på att göra den i rät storlek atomatiskt
    try{
        return Image.createImage("/pic/"+filename); //ladda in bilden
    }catch(Exception e){System.out.println("Error creating Image + \""+ filename +"\" : "+e.getMessage());}//få ut ett error meddelande i output
        return null; //returnera null om dt blir fel, går dt att returnera en tom ruta ist, så slipper hela prgmet hänga sig
   }

}