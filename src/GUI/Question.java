/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

/**
 *
 * @author SlimJim xP
 */
import Core.SWindow;

import GUI.SLabel;
import GUI.GUIObject;


public class Question{
    
    static{
        
    }
    
    static int ask(String question,String[] options){    //ej testad!!
        
        GUIObject[] objs = new GUIObject[options.length+1];
        
        objs[0] = new SLabel(question);
        
        for (int i = 1; i < objs.length; i++) {
            objs[i] = new SLabel(options[i-1]);
            
        }
        
        
        new SLabel(question);
        return -1;
    }
}