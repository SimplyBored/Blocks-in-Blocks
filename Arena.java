import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class Arena extends SmartParticle{
	
	ArrayList<SmartParticle> box = new ArrayList<SmartParticle>();
	Color breakable = new Color(153, 108, 156);
	public Arena(boolean all){
		box.add(new SmartParticle(0,-100, 10, 900, Color.GRAY));//left 0
		box.add(new SmartParticle(1175, -100, 10, 900, Color.GRAY));//right 1
		box.add(new SmartParticle(-100, 550, 1500, 50, Color.GRAY));//bottom 2 
		box.add(new SmartParticle(-100, 0, 1500, 50, Color.GRAY));//top 3
		if(all){
			box.add(new SmartParticle(525, 250, 150, 10, Color.GRAY));//spawn shield 4
			for(int i =0; i < 20; i++){
				boolean check = true;
				SmartParticle pending;
				if(i%2 == 0){
					pending = (new SmartParticle((int)(Math.random()*20)*50 + 20, (int)(Math.random()*10)*50 + 50, 150, 10, Color.GRAY));
				}else{
					pending = (new SmartParticle((int)(Math.random()*20)*50 + 20, (int)(Math.random()*10)*50 + 50, 10, 150, Color.GRAY));
				}
				for(int j = 0; j < box.size(); j++){
					if(hitDetection(box.get(j), pending) || box.get(j).intersects(pending)){
						check = false;
					}
				}
				if(check){
					if(i > 6){
						pending.setColor(breakable);
					}
					box.add(pending);
				}
			}
		}
	}
	
	public void protect(){
		box.add(new SmartParticle(525, 400, 150, 10, Color.GRAY));//spawn shield 4
	}
	
	
	/*
	public void genArena(){
		box.add(new SmartParticle(0,-100, 10, 900, Color.GRAY));//left
		box.add(new SmartParticle(1290, -100, 10, 900, Color.GRAY));//right
		box.add(new SmartParticle(-100, 650, 1500, 50, Color.GRAY));//bottom
		box.add(new SmartParticle(-100, 0, 1500, 10, Color.GRAY));//top
	}
	*/
	
	public int arenaSize(){
		return box.size();
	}
	
	public boolean addPlat(SmartParticle builder){//change to class of object
		for(int i = 0; i < box.size(); i++){
			SmartParticle rem = box.get(i);
			if(sideHit(box.get(i),builder) == 0 ){ //top
				box.add(new SmartParticle(builder.x, rem.y-75+1, 10, 75, builder.getColor()));//+1 prevent ambiguity
				return true;
			}
			if(sideHit(box.get(i),builder) == 1){ //left
				box.add(new SmartParticle(rem.x -75 + 1, builder.y, 75,10, builder.getColor()));
				return true;
			}
			if(sideHit(box.get(i),builder) == 2){ //bot
				box.add(new SmartParticle(builder.x, rem.y + rem.height, 10, 75, builder.getColor()));
				return true;
			}
			if(sideHit(box.get(i),builder) == 3){ //right
				box.add(new SmartParticle(rem.x + rem.width -1, builder.y, 75, 10, builder.getColor()));// -1 prevent space
				return true;
			
			}
		}
		return false;
}
	
	public ArrayList<SmartParticle> getArena(){
		return box;
	}
	
	public void moveAndDrawArena(Graphics2D win){
		for(int i = 0; i < box.size(); i++){
			box.get(i).moveAndDraw(win);
		}
	}
}
