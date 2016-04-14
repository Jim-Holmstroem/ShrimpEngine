/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;
import java.util.Vector;

import SGraphics.GUIObject;
import SGraphics.ReSizer;

public class SLabel extends GUIObject{
    private String text = "";//<--hela texten som ska stå där, användas när man ska räkna om maxbredden etc
    private Vector rows = new Vector();//<--varje rad har en egen string
    private int maxWidth=-1;//om -1 så finns dt ingen maxWidth 
    private int color; 
    
    public SLabel(){
        
    }
    
    public void setText(String text){//testa; sista radbrytet funkar om det bara är en char kvar??
        this.text = text;
        rows.removeAllElements();
        if(maxWidth==-1){
            rows.addElement(text);
        }else{
            char[] textbuf = new char[text.length()];
            text.getChars(0, textbuf.length, textbuf , 0);
            Font f = g.getFont();
            int rowWidth = 0;
            String tmpstr = "";
            
            for (int i = 0; i < textbuf.length; i++) {
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
        }
    }
    
    public void setColor(int color){
        this.color = color;
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
    
    public void paint(Graphics g){
        g.setColor(color);
        for (int i = 0; i < rows.size(); i++) {
            g.drawString((String)rows.elementAt(i), x, y+g.getFont().getHeight()*i, Graphics.TOP|Graphics.LEFT);
        } 
        if(marked)
            paintMarking(g);
    }
}