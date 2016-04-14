/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

public class Dimensionf {
    public float x,y;
    
    public Dimensionf(){
    }
    public Dimensionf(Dimensionf dimf){
        this.x = dimf.x;
        this.y = dimf.y;
    }
    public Dimensionf(float x,float y){
        this.x=x;
        this.y=y;
    }
}
