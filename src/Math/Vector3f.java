/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Math;

/**
 *
 * @author SlimJim xP
 */
public class Vector3f{
    float x,y,z;
    
    public Vector3f(){
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }
    
    public Vector3f(float x,float y,float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void add(Vector3f a){
        this.x += a.x;
        this.y += a.y;
        this.z += a.z;
    }
    public void sub(Vector3f a){
        this.x -= a.x;
        this.y -= a.y;
        this.z -= a.z;
    }
    public void scale(float a){
        this.x*=a;
        this.y*=a;
        this.z*=a;   
    }
    
    public float abs(){
        return Math.sqrt(x*x+y*y+z*z);
    }
    public float arg(){
        return 0.0f;
    }
    
    public String toString(){
        return ("(" + x + "," + y + "," + z + ")");
    }
}
