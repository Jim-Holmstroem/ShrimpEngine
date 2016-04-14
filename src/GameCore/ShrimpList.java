package GameCore;

import java.util.*;
import javax.microedition.lcdui.*;


public class ShrimpList extends Vector{
//ladda bara in en bild som är lika föra alla (spara en heldel minne) men hur ska dt lösas??? 
//ta en och samma bild som reference till alla Shrimparna, alltså innan loopen när man lägger in 
//om laddar man in en bild i en variabel
 public boolean inWorld = false;//gör så att alla som läggs in i listan efter man sätter värdet blir i worlden lr inte
    
    public ShrimpList() {
        
    }  
    public void addShrimp(Shrimp shrimp){
        shrimp.inWorld = this.inWorld;
        super.addElement(shrimp);
    }  
    public void removeAllShrimps(){
        super.removeAllElements();
    }  
    public Shrimp getShrimp(int i){
        return(Shrimp)super.elementAt(i);
    }
    public void removeShrimp(int i){
        super.removeElementAt(i);
    }
    public void update(){
    
    }
   
    public void paintDebug(Graphics g){
        for (int i = 0; i<size(); i++){
            getShrimp(i).paintDebug(g);//debug ritar ut alla i listan
        }
    }
    public void init(){
        for (int i = 0; i<size(); i++){
                getShrimp(i).init();
        }
    }
    
}