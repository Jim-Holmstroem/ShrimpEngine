import java.lang.String;
import javax.microedition.lcdui.Font;

public class Question { 
    private String message = "";//frågan
    private String[] options = null;//alternativen
    private int focused = 0;//vilken av alternativen som R focuserade
    private Game game = null;//reference till spelet
    
    public Question(Game game, String message, String[] options) {
        this.message = message;
        this.options = options;
        this.game = game;
    }

    public int draw(){
    
     mainLoop: while(true){
       game.gE.updateKeys(); //updatera knapparna
        
        if(game.gE.inputReleased(game.DOWN_PRESSED)){//ner ett steg bland alternativen
            if(focused < options.length - 1)
                focused++;
        }
        if(game.gE.inputReleased(game.UP_PRESSED)){//upp ett steg bland alternativen
            if(focused > 0)
                focused--;
        }
        if(game.gE.inputReleased(game.FIRE_PRESSED)){//om man trycker på fire hoppar man ur mainLoop och returnerar vilket alternativ som R focuserat
            break mainLoop;
        }
       
       game.gE.clear();//rensa skärmen
        game.g.setColor(200,100,100); //rödaktig
        game.g.setFont(game.gE.SYSTEM_LARGE_FONT);//sätt stor font
       game.g.drawString(message,game.width/2 - 40,10,game.g.TOP|game.g.LEFT);//rita ut frågan
       game.g.setFont(game.gE.SYSTEM_SMALL_FONT);//sätt liten font
        for(int i = 0; i < options.length;i++){//ritar ut optionsen
            if(focused == i){//highlighta och gör en markering över den om dt R den som R focuserad
                
                game.g.setColor(250,150,150); //rödaktig
                game.g.fillRect(0, 20*i + 40 -3, game.width, 20);
                
                
                
                game.g.setColor(200,100,100); //rödaktig
                
                game.g.drawLine(0, 20*i + 40-3, game.width, 20*i + 40-3); //markerings sträck
                game.g.drawLine(0, 20*(i+1) + 40-3, game.width, 20*(i+1) + 40-3); //markerings sträck

                game.g.setColor(0,0,0); //ljus grå
                
            }else{
                game.g.setColor(100,100,100); //mörk grå
            }
                game.g.drawString(options[i],game.width/2 - 50,40 + 20*i,game.g.TOP|game.g.LEFT);//ritar ut alla svarsallernativen
        }
       game.flushGraphics();
       
      }
        game.g.setFont(game.gE.SYSTEM_MEDIUM_FONT);//återställer till standard fonten
        return focused; // om loopen avslutas så returneras indexet på den focuserade texten :p
    }
}
