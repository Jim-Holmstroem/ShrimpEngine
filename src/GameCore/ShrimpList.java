package GameCore;

import java.util.*;
import javax.microedition.lcdui.*;


public class ShrimpList extends Vector{
//ladda bara in en bild som �r lika f�ra alla (spara en heldel minne) men hur ska dt l�sas??? 
//ta en och samma bild som reference till alla Shrimparna, allts� innan loopen n�r man l�gger in 
//om laddar man in en bild i en variabel
 public boolean inWorld = false;//g�r s� att alla som l�ggs in i listan efter man s�tter v�rdet blir i worlden lr inte
    
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