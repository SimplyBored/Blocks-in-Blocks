import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class Laser extends Effects{
	ArrayList<SmartParticle> beams = new ArrayList<SmartParticle>();
	Color laserColor = new Color(201,255,242);
	Arena box;
	public Laser(Arena box){
		this.box = box;
	}
	
	public void genLaser(Arena box){
		int startingX = (int)(Math.random()*950 + 20);
		ArrayList<SmartParticle> keep = box.getArena();
		int yBase = keep.get(3).y + keep.get(3).height;
		int chooseX = (int)(Math.random()*2);
		int dirX;
		if(chooseX == 1){
			dirX = 1;
		}else{
			dirX = -1;
		}
		int laserSpeed = 1;
		for(int i = 0; i < 20; i++){
			SmartParticle p1 = new SmartParticle(startingX + 1*i, yBase, 1, keep.get(2).y - yBase, laserColor);//generates entirety of play arena
			for(int j = 4; j < keep.size(); j++){
				if(p1.intersects(keep.get(j))){
					p1.setBounds(p1.x, p1.y, p1.width, keep.get(j).y - yBase);
				}//sets end point of laser to top of contacted particle
			}
			p1.setDx(dirX*laserSpeed);
			p1.expediteWait(2*i);
			beams.add(p1);
		}
	}
	
	public void genLaserPoint(Arena box, SmartParticle des){
		int startingX = des.x;
		ArrayList<SmartParticle> keep = box.getArena();
		int yBase = keep.get(3).y + keep.get(3).height;
		int chooseX = (int)(Math.random()*2);
		int dirX;
		if(chooseX == 1){
			dirX = 1;
		}else{
			dirX = -1;
		}
		int laserSpeed = 1;
		for(int i = 0; i < 20; i++){
			SmartParticle p1 = new SmartParticle(startingX + 1*i, yBase, 1, keep.get(2).y - yBase, laserColor);//generates entirety of play arena
			for(int j = 4; j < keep.size(); j++){
				if(p1.intersects(keep.get(j))){
					p1.setBounds(p1.x, p1.y, p1.width, keep.get(j).y - yBase);
				}//sets end point of laser to top of contacted particle
			}
			p1.setDx(dirX*laserSpeed);
			p1.expediteWait(2*i);
			beams.add(p1);
		}
	}
	
	public void genLaserExplosion(SmartParticle laser){
		int dim =2;
		double vDy = -10;
		double vDx = 15;
		for(int i = 0; i < 2; i++){
			int chooseX = (int)(Math.random()*2);
			int dirX;
			if(chooseX == 1){
				dirX = 1;
			}else{
				dirX = -1;
			}
			SmartParticle p1 = new SmartParticle((int)(laser.x + laser.width*Math.random()), (int)(laser.y + laser.height),dim,dim);
			p1.expediteWait(85);
			p1.setDy(vDy*Math.random() + 3);
			p1.setDx(dirX*Math.random()*vDx);
			p1.setColor(laserColor);
			particles.add(p1);
		}
	}
	
	public void laserReassessment(Arena activeBox){
		ArrayList<SmartParticle> keep = activeBox.getArena();
		double yBase = keep.get(3).y + keep.get(3).height;
		for(int i = 0; i < beams.size(); i++){
			SmartParticle p1 = beams.get(i);
			p1.setBounds(p1.x,p1.y,(int)p1.width, (int)(keep.get(2).y - yBase));//generates entirety of play arena
			for(int j = 4; j < keep.size(); j++){
				if(p1.intersects(keep.get(j))){
					p1.setBounds(p1.x, p1.y, p1.width,(int) (keep.get(j).y - yBase));
				}//sets end point of laser to top of contacted particle
			}
		}
	}
	
	public void moveAndDrawLaser(Graphics2D win, Arena activeBox){
		for(int i = 0; i < beams.size(); i++){
			beams.get(i).moveAndDraw(win);
			genLaserExplosion(beams.get(i));
			beams.get(i).eraseWait();
			if(beams.get(i).deleteTimer > 200){
				beams.remove(i);
				i--;
			}
		}
		/*
		ArrayList<SmartParticle> keep = activeBox.getArena();
		for(int i = 0; i < keep.size(); i++){
			explodeCollision(keep.get(i));
		}
		moveExplosion(win);
		removeGrenade();
		*/
		laserReassessment(activeBox);
	}
	
	/*
	public void laserRecoil(Graphics2D win, Arena activeBox){
		ArrayList<SmartParticle> box = activeBox.getArena();
		for(int i = 0; i < box.size(); i++){
			laserReflect(box.get(i));
		}
		moveReflection(win);
	}
	*/
	
	public ArrayList<SmartParticle> getBeams(){
		return beams;
	}
	
	public boolean heroContact(Hero hero){
		for(int i = 0; i < beams.size(); i++){
			if(beams.get(i).intersects(hero)){
				return true;
			}
		}
		return false;
	}
}
