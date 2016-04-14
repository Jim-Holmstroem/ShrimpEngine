import java.lang.String;
import javax.microedition.lcdui.Font;

public class Question { 
    private String message = "";//fr�gan
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
        if(game.gE.inputReleased(game.FIRE_PRESSED)){//om man trycker p� fire hoppar man ur mainLoop och returnerar vilket alternativ som R focuserat
            break mainLoop;
        }
       
       game.gE.clear();//rensa sk�rmen
        game.g.setColor(200,100,100); //r�daktig
        game.g.setFont(game.gE.SYSTEM_LARGE_FONT);//s�tt stor font
       game.g.drawString(message,game.width/2 - 40,10,game.g.TOP|game.g.LEFT);//rita ut fr�gan
       game.g.setFont(game.gE.SYSTEM_SMALL_FONT);//s�tt liten font
        for(int i = 0; i < options.length;i++){//ritar ut optionsen
            if(focused == i){//highlighta och g�r en markering �ver den om dt R den som R focuserad
                
                game.g.setColor(250,150,150); //r�daktig
                game.g.fillRect(0, 20*i + 40 -3, game.width, 20);
                
                
                
                game.g.setColor(200,100,100); //r�daktig
                
                game.g.drawLine(0, 20*i + 40-3, game.width, 20*i + 40-3); //markerings str�ck
                game.g.drawLine(0, 20*(i+1) + 40-3, game.width, 20*(i+1) + 40-3); //markerings str�ck

                game.g.setColor(0,0,0); //ljus gr�
                
            }else{
                game.g.setColor(100,100,100); //m�rk gr�
            }
                game.g.drawString(options[i],game.width/2 - 50,40 + 20*i,game.g.TOP|game.g.LEFT);//ritar ut alla svarsallernativen
        }
       game.flushGraphics();
       
      }
        game.g.setFont(game.gE.SYSTEM_MEDIUM_FONT);//�terst�ller till standard fonten
        return focused; // om loopen avslutas s� returneras indexet p� den focuserade texten :p
    }
}
