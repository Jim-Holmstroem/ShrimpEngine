/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//lägg till så att man inte behöver recalc()
//ha en liten boolean needRecalc som sätts till true så fort man ändrar ngt men som anropar recalc igen precis innan man ska rita ut den igen..

package GUI;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;
import java.util.Vector;

import GUI.GUIObject;
import SGraphics.ReSizer;

public class SLabel extends GUIObject{
    private String text = "";//<--hela texten som ska stå där, användas när man ska räkna om maxbredden etc
    private Vector rows = new Vector();//<--varje rad har en egen string
    private int maxWidth=-1;//om -1 så finns dt ingen maxWidth 
    private int color = 0xFFFFFFFF; 
    private boolean cutWords = false;//om det är okey att klippa mitt i ord
        
    public SLabel(){
        
    }
    public SLabel(String text){
        setText(text);
        recalc();
    }
    
    public void setText(String text){//testa; sista radbrytet funkar om det bara är en char kvar??
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
                        i--;//ta med den som inte fick plats i nästa
                        rowWidth=0;
                    }             
                }
                rows.addElement(new String(tmpstr));//lägger till buffert som blir kvar när man är klar med den sista raden, borde kunna gå att göra snyggare          
            }else{//ändrar rad automatiskt utan att kapa ord..
                char[] textbuf = new char[text.length()];
                text.getChars(0, textbuf.length, textbuf , 0);
                Font f = g.getFont();
                int rowWidth = 0;
                int wordWidth = 0;
                String tmpstr = "";
                String tmpwrd = "";
                
                //dela upp allt i ord
                Vector words = new Vector();    
                for(int i = 0; i < textbuf.length; i++){
                    char c = textbuf[i];
                    if(c!=' '){
                        tmpwrd = tmpwrd + String.valueOf(c);
                    }
                    else{
                        words.addElement(new String(tmpwrd));
                        //System.out.print(words.lastElement());
                        tmpwrd = "";
                    }
                }
        
                for(int i = 0; i < words.size(); i++){
                    rowWidth+=getPixelLength(f,(String)words.elementAt(i)+" ");
                    if(rowWidth <= maxWidth){
                        tmpstr = tmpstr + " " + (String)words.elementAt(i);
                    }else{
                       // System.out.println(rowWidth-getPixelLength(f,(String)words.elementAt(i)) + "/" + maxWidth + "  >" + getPixelLength(f,(String)words.elementAt(i)));
                        rows.addElement(new String(tmpstr));
                        tmpstr="";
                        i--;//ta med den som inte fick plats i nästa
                        rowWidth=0;
                    }
                }
                rows.addElement(new String(tmpstr));//lägger till buffert som blir kvar när man är klar med den sista raden, borde kunna gå att göra snyggare                
            }
        }
    }
        
    public int getPixelLength(Font f, String a){
        int len=0;
        for (int i = 0; i < a.length(); i++) {
            len+=f.charWidth(a.charAt(i));
        }
        return len;
    }
     
    public void setColor(int color){
        this.color = color;
    }
    
    public void setCutWords(boolean cutWords){
        this.cutWords = cutWords;
    }
    
    public void setMaxWidth(float maxWidth){
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