import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Path extends SmartParticle{
	ArrayList<SmartParticle> path = new ArrayList<SmartParticle>();
	double keepDx;
	double keepDy;
	double angle;
	double space = 20;
	
	public Path(Hero hero){
		keepDx = hero.dx; keepDy = hero.dy; getAngle(hero.dx, hero.dy);
	}
	
	public void getAngle(double dx, double dy){
		angle = Math.atan(dy/dx);
	}//help method for constructor
	
	public void getAngle(Hero hero){
		angle = Math.atan(dy/dx);
		
	}
	
	/*
	public void genPath(Hero hero){
		
		for(int i = 0; i < path.length; i++){
			getAngle(hero);
			if(hero.dx > 0){
				path[i] = new SmartParticle((int)(hero.x + hero.width/2 + 20*Math.cos(angle)), (int)(hero.y + hero.height/2 + 20*Math.sin(angle)), 20, 20, Color.RED);
			}else{
				path[i] = new SmartParticle((int)(hero.x + hero.width/2 - 20*Math.cos(angle)), (int)(hero.y + hero.height/2 + 20*Math.sin(angle)), 50, 50, Color.BLUE);
			}
		}
	}
	*/
	
public void genPath(Hero hero){
		
		for(int i = 0; i < 100; i++){
			getAngle(hero);
			if(path.size()<100){
				if(hero.dx > 0){
					path.add(new SmartParticle((int)(hero.x + hero.width/2 ), (int)(hero.y + hero.height/2 ), 20, 20, Color.RED));
				}else{
					path.add(new SmartParticle((int)(hero.x + hero.width/2 ), (int)(hero.y + hero.height/2 ), 20, 20, Color.BLUE));
				}
			}else{
				if(hero.dx > 0){
					path.remove(i);
					path.add(i, new SmartParticle((int)(hero.x + hero.width/2 ), (int)(hero.y + hero.height/2 ), 20, 20)); 
				}else{
					path.remove(i);
					path.add(i, new SmartParticle((int)(hero.x + hero.width/2 ), (int)(hero.y + hero.height/2 ), 20, 20));
				}
			}
		}
	}
	
	public void drawPath(Graphics2D win, Hero hero){
		genPath(hero);
		for(int i = 0; i < path.size(); i++){
			//path[i].setColor(hero.getColor());
			path.get(i).moveAndDraw(win);
		}
	}
	
	public void drawPath(Graphics2D win){
		for(int i = 0; i < path.size(); i++){
			//path[i].setColor(Color.BLUE);
			path.get(i).moveAndDraw(win);
		}
	}
	
	public double getAngle(){
		return angle;
	}
	
}
