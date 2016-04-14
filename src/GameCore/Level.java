package GameCore;

import javax.microedition.lcdui.Image;
//------------------------------------------------------------------------------
public class Level {
    public float[] x,y; //positionen för objecten i leveln
    public int[] object; //vilken av bilderna man ska använda när man gör shrimpen
    public Image[]  images; //vilka bilder som man har att välja mellan när man gör shrimpen
    public int size; //hur många object det R i leveln
//-------------------------------------------------------------------------------    
    public Level(Image[] images,float[] x, float[] y, int[] object) {//laddar in poisitionen och vilka bilder man ska använda vart
        this.images = images;
        this.x = x;
        this.y = y;
        this.object = object;
        this.size = object.length; //storleken utifrån hur många object man har
    }
    
    public ShrimpList putToList(){//lägg in den till Shrimplisten som represnterar alla levelobjecten, därifrån kan man göra update, paint, what ever :P
        ShrimpList levelObjects = new ShrimpList();
        levelObjects.inWorld = true;
            for(int i = 0;i<size;i++){
                Shrimp shrimp = new Shrimp(images[object[i]]);// gör en ny shrimp utifrån bilden
                shrimp.x = x[i]; //sätt den poitionen den ska ha
                shrimp.y = y[i];
                shrimp.inWorld = true; //sätt in den i världen
                levelObjects.addShrimp(shrimp); //lägg till den i listan
            }
        return levelObjects; //returnera hela listan
    }
}
