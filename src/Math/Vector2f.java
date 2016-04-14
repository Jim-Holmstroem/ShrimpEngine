/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Math;

/**
 *
 * @author SlimJim xP
 */
public class Vector2f {
    public float x,y;
    
    public Vector2f(){
        this.x = 0.0f;
        this.y = 0.0f;
    }
    
    public Vector2f(float x,float y){
        this.x = x;
        this.y = y;
    }
    
    public Vector2f(Vector2f a){
        this.x = a.x;
        this.y = a.y;
    }
    
    
    public void add(Vector2f a){
        this.x += a.x;
        this.y += a.y;
    }
    public void sub(Vector2f a){
        this.x -= a.x;
        this.y -= a.y;
    }
    public void scale(float a){
        this.x*=a;
        this.y*=a;
    }
    
    public float abs(){
        return Math.sqrt(x*x+y*y);
    }
    public float arg(){
        return 0.0f;
    }
    
    public String toString(){
        return ("(" + x + "," + y + ")");
    }
    
}
