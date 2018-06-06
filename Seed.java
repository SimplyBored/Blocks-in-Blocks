import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Seed extends SmartParticle{
	ArrayList<SmartParticle> obstacle = new ArrayList<SmartParticle>();
	Rectangle limiter = new Rectangle(800, -600, 420, 1);
	int failLimit = 10;
	
	public Seed(){
		
	}
	
	public void drawObstacle(Graphics2D win){
		for(int i = 0; i < obstacle.size(); i++){
			SmartParticle p1 = obstacle.get(i);
			p1.moveAndDraw(win);
		}
	}
	
	public void buildTower(){ //main method
		obstacle.add(new SmartParticle(800,500, 10, 100, Color.BLACK)); //get 0
		obstacle.add(new SmartParticle(1210, -600, 10, 1200, Color.GRAY));//get 1
		obstacle.add(new SmartParticle(800, - 600, 10, 1100, Color.GRAY));// get 2
		obstacle.add(new SmartParticle(800, 600, 420,10)); //get 3 for bottom limit
		obstacle.add(new SmartParticle(800, - 600, 420, 10)); // get 4 for top limit
		//generate sides of tower
		for(int i = 0; i < 6; i++){
			if(i%2 == 0){
				obstacle.add(new SmartParticle(810, 400- i*200, 75, 10, Color.RED));
			}else{
				obstacle.add(new SmartParticle(1135, 400-i*200, 75, 10, Color.RED));
			}
		}//generate base platforms
		/*
		for(int i = 5; i < 11; i++){
			if(i%2 != 0){
				Tower(obstacle.get(i), 1);
			}else{
				Tower(obstacle.get(i), 3);
			}
			 /* Anchor Point:
			  * 0 = Below
			  * 1 = Left
			  * 2 = Above
			  * 3 = Right 
			 */
		//}
		for(int i = 0; i < 100; i++){
			SmartParticle p1 = (new SmartParticle((int)(Math.random()*325 + 810), (int)(Math.random()*(1200)) -600, 75, 10, Color.GRAY));
			if(verify(p1)){
				obstacle.add(p1);
			}
		}
		
	}
	
	public void Tower(SmartParticle anchor, int orientation){//asses point given and rerouted
		/* 
		if(orientation == 0){anchorBelow(anchor,0);}
		 if(orientation == 1){anchorLeft(anchor, 1);}
		 if(orientation == 2){anchorAbove(anchor,2);}
		 if(orientation == 3){anchorRight(anchor,3);}
		 */
		for(int i = 0; i < 1000; i++){
			SmartParticle p1 = (new SmartParticle((int)(Math.random()*325 + 810), (int)((Math.random()*(1200)) - 600), 75, 10, Color.GRAY));
			if(verify(p1)){
				obstacle.add(p1);
			}
		}
	}
	
	public boolean verify(SmartParticle check){
		boolean result = true;
		for(int i = 0; i < obstacle.size(); i++){
			if(check.intersects(obstacle.get(i))){// && !anchor.equals(obstacle.get(i))){
				result = false;
			}
		}
		return result;
		
	}
	/*
	public boolean overlap(SmartParticle check, SmartParticle anchor){
		boolean result = true;
		
		if(check.intersects(obstacle.get(2)) || check.intersects(obstacle.get(3)) || check.intersects(obstacle.get(0))|| check.intersects(obstacle.get(1))){
			result = false;
		}
		for(int i = 0; i < obstacle.size(); i++){
			if(check.intersects(obstacle.get(i))){// && !anchor.equals(obstacle.get(i))){
				result = false;
			}
			SmartParticle p1 = obstacle.get(i);
			//if(Math.abs(p1.x - check.x) < 10 ){
				//result = false;
			//}
		}
		return result;
	}
	
	public void anchorBelow(SmartParticle anchor,int orin){//keeps track of fails for base statement/end statement
		boolean trackOverLap = false;
		SmartParticle pending = new SmartParticle();
		int failCnt = 0;
		int large = 75;
		int small = 10;
		int random = -1;
		while(failCnt < failLimit){ //if fails four times just quit
			if(!trackOverLap){ //if no suitable found continue
				 random = (int)(Math.random()*4);//random 0 - 3
				while(random == 2){//do not allow counterpart or else loop will form
					random = (int)(Math.random()*4);
				}//continue until new random found
				if(random%2 != 0){//odd assessed for up or down
					pending = new SmartParticle(anchor.x - (large - small)*((int)((2-orin)/2)), anchor.y-small, large, small, Color.YELLOW);
				}else{
					pending = new SmartParticle(anchor.x, anchor.y - large, small, large, Color.BLUE);
				}//outwards
				trackOverLap = overlap(pending, anchor); // returns false if overlap and true if vaild
				if(!trackOverLap){
					failCnt ++;
				}
			}else{
				obstacle.add(pending);//adds pending block when confirmed
				Tower(pending, random);//identifies made block as anchor and keeps orientation
				break;
			}//builds if statement is true
		}
	}//0
	public void anchorLeft(SmartParticle anchor,int orin){
		boolean trackOverLap = false;
		SmartParticle pending = new SmartParticle();
		int failCnt = 0;
		int large = 75;
		int small = 10;
		int random = -1;
		while(failCnt < failLimit){ //if fails four times just quit
			if(!trackOverLap){ //if no suitable found continue
				 random = (int)(Math.random()*4);//random 0 - 3
				while(random == 3){//3 is the counterpart of 1
					random = (int)(Math.random()*4);
				}//continue until new random found
				if(random%2 == 0){//even assessed for up or down
					pending = new SmartParticle(anchor.x + anchor.width, anchor.y - (large-small)*((int)(orin/2)), small, large, Color.BLACK);
				}else{
					pending = new SmartParticle(anchor.x + anchor.width, anchor.y, large,small, Color.GREEN);
				}//outwards
				trackOverLap = overlap(pending, anchor); // returns false if overlap and true if vaild
				if(!trackOverLap){
					failCnt ++;
				}
			}else{
				failCnt = failLimit;
				obstacle.add(pending);//adds pending block when confirmed
				Tower(pending, random);//identifies made block as anchor and keeps orientation
				break;
			}//builds if statement is true
		}
		
	}//1
	public void anchorAbove(SmartParticle anchor, int orin){
		boolean trackOverLap = false;
		SmartParticle pending = new SmartParticle();
		int failCnt = 0;
		int large = 75;
		int small = 10;
		int random = -1;
		while(failCnt < failLimit){ //if fails four times just quit
			if(!trackOverLap){ //if no suitable found continue
				 random = (int)(Math.random()*4);//random 0 - 3
				while(random == 0){//counterpart of 2
					random = (int)(Math.random()*4);
				}//continue until new random found
				if(random%2 != 0){//odd assessed for left or right
					pending = new SmartParticle(anchor.x - (large - small)*((int)((2-orin)/2)), anchor.y + large, large, small, Color.ORANGE);
				}else{
					pending = new SmartParticle(anchor.x , anchor.y + large, small, large, Color.MAGENTA);
				}//outwards
				trackOverLap = overlap(pending, anchor); // returns false if overlap and true if valid
				if(!trackOverLap){
					failCnt ++;
				}
			}else{
				failCnt = failLimit;
				obstacle.add(pending);//adds pending block when confirmed
				Tower(pending, random);//identifies made block as anchor and keeps orientation
				break;
			}//builds if statement is true
		}
	}//2
	public void anchorRight(SmartParticle anchor, int orin){
		boolean trackOverLap = false;
		SmartParticle pending = new SmartParticle();
		int failCnt = 0;
		int large = 75;
		int small = 10;
		int random = -1;
		while(failCnt < failLimit){ //if fails four times just quit
			if(!trackOverLap){ //if no suitable found continue
				 random = (int)(Math.random()*4);//random 0 - 3
				while(random == 1){
					random = (int)(Math.random()*4);
				}//continue until new random found
				if(random%2 == 0){//even assessed for up or down
					pending = new SmartParticle(anchor.x - small, anchor.y - (large - small)*((int)(orin/2)), small, large, Color.PINK);
				}else{
					pending = new SmartParticle(anchor.x - large, anchor.y, large,small, Color.GRAY);
				}//outwards
				trackOverLap = overlap(pending, anchor); // returns false if overlap and true if vaild
				if(!trackOverLap){
					failCnt ++;
				}
			}else{
				failCnt = failLimit;
				obstacle.add(pending);//adds pending block when confirmed
				Tower(pending, random);//identifies made block as anchor and keeps orientation
				break;
			}//builds if statement is true
		}
	}//3
	*/
	
	
	
	public void heroCollision(SmartParticle hero){
		for(int i = 1; i < obstacle.size(); i++){
			hero.heroCollision(obstacle.get(i));
		}
	}
	
	public void jump(SmartParticle hero, boolean jump){
		for(int i = 1; i < obstacle.size(); i++){
			hero.jump(jump, obstacle.get(i));
		}
	}
	
	public ArrayList<SmartParticle> getSeed(){
		return obstacle;
	}
}
