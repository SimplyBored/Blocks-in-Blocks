import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class Talking {
	ArrayList<String> convo = new ArrayList<String>();
	int sizeMax = 18;//letters
	Font keepFont;
	Color c;
	Graphics2D win;
	Hero speaker;
	int speakTimer = 0;
	int cue = 0;
	
	public Talking(Color c1, Font font, Hero hero, Graphics2D finalWin){
		c = c1; keepFont = font; speaker = hero; win = finalWin; 
	}
	
	public void talk(String chat){
		int index = 0;
		convo = new ArrayList<String>();
		if(chat.length() > 18){
			for(int i = 17; i < chat.length(); i++){ //allows a word to be finished
				if(chat.substring(i, i+1).equals(" ")){
					break;
				}else{
					index++;
				}
			}
			convo.add(chat.substring(0, 17 + index));
			stillTalking(chat.substring(17 + index));
		}else{
			convo.add(chat.substring(0));
		}
	}
	
	public void stillTalking(String chat){
		int index = 0;
		if(chat.length() > 17){
			for(int i = 17; i < chat.length(); i++){ //allows a word to be finished
				if(chat.substring(i, i+1).equals(" ")){
					break;
				}else{
					index++;
				}
			}
			convo.add(chat.substring(0, 17 + index));
			stillTalking(chat.substring(17 + index));
		}else{
			convo.add(chat.substring(0));
		}
	}
	
	public void mute(){
		convo = new ArrayList<String>();
	}
	
	public void setColor(Color c1){
		c = c1;
	}
	
	public void getConvo(){
			win.setColor(c);
			win.setFont(keepFont);
			for(int i = 0; i < convo.size(); i++){
				win.drawString(convo.get(i), speaker.x - 75, speaker.y -25*convo.size() + 25* i);
			}
	}
	
	public void callTime(){
		speakTimer ++;
	}
}
