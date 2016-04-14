package GameCore;

import javax.microedition.lcdui.Image;
//------------------------------------------------------------------------------
public class Level {
    public float[] x,y; //positionen f�r objecten i leveln
    public int[] object; //vilken av bilderna man ska anv�nda n�r man g�r shrimpen
    public Image[]  images; //vilka bilder som man har att v�lja mellan n�r man g�r shrimpen
    public int size; //hur m�nga object det R i leveln
//-------------------------------------------------------------------------------    
    public Level(Image[] images,float[] x, float[] y, int[] object) {//laddar in poisitionen och vilka bilder man ska anv�nda vart
        this.images = images;
        this.x = x;
        this.y = y;
        this.object = object;
        this.size = object.length; //storleken utifr�n hur m�nga object man har
    }
    
    public ShrimpList putToList(){//l�gg in den till Shrimplisten som represnterar alla levelobjecten, d�rifr�n kan man g�ra update, paint, what ever :P
        ShrimpList levelObjects = new ShrimpList();
        levelObjects.inWorld = true;
            for(int i = 0;i<size;i++){
                Shrimp shrimp = new Shrimp(images[object[i]]);// g�r en ny shrimp utifr�n bilden
                shrimp.x = x[i]; //s�tt den poitionen den ska ha
                shrimp.y = y[i];
                shrimp.inWorld = true; //s�tt in den i v�rlden
                levelObjects.addShrimp(shrimp); //l�gg till den i listan
            }
        return levelObjects; //returnera hela listan
    }
}
