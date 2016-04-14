/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Math;

public class Math {
final static float PI = 3.14159265358979323846f,
                   E  = 2.71828182845904523536f;

//---------------------[  1D  ]-------------------------------------------------

public static float sin(float a){
    return (float)java.lang.Math.sin((double)a);
}
public static float cos(float a){
    return (float)java.lang.Math.cos((double)a);
}
public static float tan(float a){
    return (float)java.lang.Math.tan((double)a);
}

public static float tan2(float a,float b){//returns the angle to (a,b)
    return 0.0f;
}

public static float sqrt(float s){
    return (float)java.lang.Math.sqrt((double)s);
}

public static float powE(){
    
    return 0.0f;
}

public static float ln(float x){
//ln(x) = sum((((-1)^(n+1))/n)(x-1)^n)
    float c = 0.0f;
    System.out.println(System.currentTimeMillis());
    for(int n = 1;n<1000;n++){
        c+= (grd(n+1)/(float)n)*pow(x-1,n);
    }
    System.out.println(System.currentTimeMillis());
    return c;
}

public static float pow(float a,int b){// = a^b;
    float c = a;
    for(int i = 1;i<b;i++){
        c*=a;
    }
    return c;
}

public static float grd(int n){
    return (n%2==0)?(1.0f):(-1.0f);
}
public static int fak(int n){//n!
    switch(n){
        case 0:return 1;
        case 1:return 1;
        case 2:return 2;
        case 3:return 6;
        case 4:return 24;
        case 5:return 120;
        case 6:return 720;
        case 7:return 5040;
        case 8:return 40320;
        case 9:return 362880;
        case 10:return 3628800;
        case 11:return 39916800;
        case 12:return 479001600;

        default:
        int ans = 0;
            if(n>12){
                
                ans=3628800;
                for(int i = 10;i<n;i++){
                   ans*=i; 
                }
                
                System.out.println("very high fakultet calculated(check res):"+n+"! = "+ ans);
            }
            if(n<0){
                System.out.println("n! notdefined for n<0");
                return -1;
            }
        return ans;
    }
}

public static float floor(float a){
    //special case
    if(Float.isInfinite(a))
        return Float.POSITIVE_INFINITY;
    if(Float.isNaN(a))
        return Float.NaN;
    if(a == 0.0f)
        return 0.0f;
    if(a == -0.0f)
        return -0.0f;
    
    //general case
    return (float)((int)a);
}

public static float ceil(float a){
    //special cases
    if(Float.isInfinite(a))
        return Float.POSITIVE_INFINITY;
    if(Float.isNaN(a))
        return Float.NaN;
    if(a == 0.0f)
        return 0.0f;
    if(a == -0.0f)
        return -0.0f;
    if(a<0.0f && a>-1.0f)
        return -0.0f;
    
    //general case
    return (float)((int)(a+1.0f));
}


//---------------------[  2D  ]-------------------------------------------------

public static Vector2f sum(Vector2f a,Vector2f b){
    return new Vector2f(a.x+b.x,a.y+b.y);
}

public static Vector2f sum(Vector2f[] as){
    float x=0.0f,y=0.0f;
    for(int i = 0;i<as.length;i++){
        x += as[i].x;
        y += as[i].y;
    }
    return new Vector2f(x,y);
}

public static Vector2f diff(Vector2f a,Vector2f b){
    return new Vector2f(a.x-b.x,a.y-b.y);
}

public static float dot(Vector2f a,Vector2f b){
    return (a.x*b.x+a.y*b.y);
}

public static float crossZ(Vector2f a,Vector2f b){//har man ens användning för denhära ??
    return det(a.x,a.y,b.x,b.y);
}

//---------------------[  3D  ]-------------------------------------------------

public static Vector3f sum(Vector3f a,Vector3f b){
    return new Vector3f(a.x+b.x,a.y+b.y,a.z+b.z);
}

public static Vector3f sum(Vector3f[] as){
    float x=0.0f,y=0.0f,z=0.0f;
    for(int i = 0;i<as.length;i++){
        x += as[i].x;
        y += as[i].y;
        z += as[i].z;
    }
    return new Vector3f(x,y,z);
}

public static Vector3f diff(Vector3f a,Vector3f b){
    return new Vector3f(a.x-b.x,a.y-b.y,a.z+b.z);
}

public static float dot(Vector3f a,Vector3f b){
    return (a.x*b.x+a.y*b.y);
}

public static Vector3f cross(Vector3f a,Vector3f b){
    return new Vector3f(det(a.y,a.z,b.y,b.z),
                       -det(a.x,a.z,b.x,b.z),
                        det(a.x,a.y,b.x,b.y));
}

//--------------------------[Misc]----------------------------------------------

public static float det(float a,float b,float c,float d){
    return a*d-b*c;
}

}

