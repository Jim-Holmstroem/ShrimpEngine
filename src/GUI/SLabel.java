/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//l�gg till s� att man inte beh�ver recalc()
//ha en liten boolean needRecalc som s�tts till true s� fort man �ndrar ngt men som anropar recalc igen precis innan man ska rita ut den igen..

//t�nk p� att width och s�nt inte r�knas ut f�rrens den ritas ut om man inte har k�rt en recalc innan


package GUI;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;
import java.util.Vector;

import GUI.GUIObject;
import SGraphics.ReSizer;

public class SLabel extends GUIObject{
    private String text = "";//<--hela texten som ska st� d�r, anv�ndas n�r man ska r�kna om maxbredden etc
    private Vector rows = new Vector();//<--varje rad har en egen string
    private int maxWidth=-1;//om -1 s� finns dt ingen maxWidth 
    private int color = 0xFFFFFFFF; 
    private boolean cutWords = false;//om det �r okey att klippa mitt i ord
    private boolean recalc = false;
    
    public SLabel(){
        
    }
    public SLabel(String text){
        setText(text);
    }

    public void setText(String text){//testa; sista radbrytet funkar om det bara �r en char kvar??
        recalc = true;
        this.text = text;
        rows.removeAllElements();
        if(maxWidth==-1){
            rows.addElement(text);
        }else{
            if(cutWords){
                char[] textbuf = new char[text.length()];
                text.getChars(0, textbuf.length, textbuf , 0);
                Font f = g.getFont();
                int rowWidth = 0;
                String tmpstr = "";

                for(int i = 0; i < textbuf.length; i++){
                    char c = textbuf[i];
                    rowWidth+=f.charWidth(c);
                    if(rowWidth<=maxWidth){
                        tmpstr=tmpstr+String.valueOf(c);
                    }else{
                        rows.addElement(new String(tmpstr));
                        tmpstr="";
                        i--;//ta med den som inte fick plats i n�sta
                        rowWidth=0;
                    }             
                }
                rows.addElement(new String(tmpstr));//l�gger till buffert som blir kvar n�r man �r klar med den sista raden, borde kunna g� att g�ra snyggare          
            }else{//h�r ska metoden f�r att �ndra rad utan att kapa ord..har inte gjorts �n..
                char[] textbuf = new char[text.length()];
                text.getChars(0, textbuf.length, textbuf , 0);
                Font f = g.getFont();
                int rowWidth = 0;
                String tmpstr = "";

                for(int i = 0; i < textbuf.length; i++){
                    char c = textbuf[i];
                    rowWidth+=f.charWidth(c);
                    if(rowWidth<=maxWidth){
                        tmpstr = tmpstr+String.valueOf(c);
                    }else{
                        rows.addElement(new String(tmpstr));
                        tmpstr="";
                        i--;//ta med den som inte fick plats i n�sta
                        rowWidth=0;
                    }             
                }
                rows.addElement(new String(tmpstr));//l�gger till buffert som blir kvar n�r man �r klar med den sista raden, borde kunna g� att g�ra snyggare                
            }
        }
    }
    
    public void setColor(int color){
        this.color = color;
    }
    
    public void setCutWords(boolean cutWords){
        this.cutWords = cutWords;
    }
    
    public void setMaxWidth(float maxWidth){
        recalc = true;
        if(maxWidth>0||maxWidth==-1){
            this.maxWidth = ReSizer.reX(maxWidth);
        }else{
            System.out.println("<0 can't be assigned as maxWidth (excp -1)");
        }
    }
    
    public void recalc(){
        setText(text);
        recalcWidth();
        recalcHeight();
        recalc = false;
    }
    
    private void recalcWidth(){
        if(maxWidth==-1)
            w = g.getFont().stringWidth((String)rows.elementAt(0));
        else
            w = Math.min(g.getFont().stringWidth((String)rows.elementAt(0)),maxWidth);
    }
    
    private void recalcHeight(){
        h = g.getFont().getHeight()*rows.size();          
    }
    
    private static boolean isSpace(char a){
        return (" ".compareTo(String.valueOf(a))==0);
    }
    
    public void paint(Graphics g){
        if(recalc)
            recalc();
        if(visible){
            g.setColor(color);
            for (int i = 0; i < rows.size(); i++) {
                g.drawString((String)rows.elementAt(i), x, y+g.getFont().getHeight()*i, Graphics.TOP|Graphics.LEFT);
            } 
            if(marked)
                paintMarking(g);
        }
    }
    public String toString(){
        return text;
    }
    
}