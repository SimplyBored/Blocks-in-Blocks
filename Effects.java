import java.awt.Color;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class Effects extends SmartParticle {
	ArrayList<SmartParticle> particles = new ArrayList<SmartParticle>();
	public static double gravity = .5;
	public static double friction = .2;
	
	public Effects(){
		
	}
	
	public void genExplosion(SmartParticle hero){//generating explosion particles
		int dim = 5;
		int partSpeed = 5;
		for(int i = 0; i < 75; i++){
			int chooseY = (int)(Math.random()*2);
			int dirY;
			if(chooseY == 1){
				dirY = 1;
			}else{
				dirY = -1;
			}//for random Y direction
			int chooseX = (int)(Math.random()*2);
			int dirX;
			if(chooseX == 1){
				dirX = 1;
			}else{
				dirX = -1;
			}
			particles.add(new SmartParticle(hero.x+hero.width/2, hero.y+hero.height/2, dim, dim, (dirX*((Math.random()*3+.1))) + hero.dx, (dirY*((partSpeed*Math.random())))+hero.dy, hero.getColor()));
		}
	}//impact explosion
	
	public void genTears(SmartParticle hero, Color c2){//generating explosion particles
		int dim = 5;
		int partSpeed = 2;
		for(int i = 0; i < 2; i++){
			int chooseY = (int)(Math.random()*2);
			int dirY;
			if(chooseY == 1){
				dirY = 1;
			}else{
				dirY = -1;
			}//for random Y direction
			int chooseX = (int)(Math.random()*2);
			int dirX;
			if(chooseX == 1){
				dirX = 1;
			}else{
				dirX = -1;
			}
			particles.add(new SmartParticle(hero.x+hero.width/2, hero.y+hero.height/2, dim, dim, (dirX*((Math.random()*3+.1))) + hero.dx, (dirY*((partSpeed*Math.random())))+hero.dy, c2));
			particles.get(particles.size()-1).expediteWait(85);
		}
	}//impact explosion
	
	public void genTears(SmartParticle hero, int x, int y, Color c2){//generating explosion particles
		int dim = 5;
		int partSpeed = 2;
		for(int i = 0; i < 30; i++){
			int chooseY = (int)(Math.random()*2);
			int dirY;
			if(chooseY == 1){
				dirY = 1;
			}else{
				dirY = -1;
			}//for random Y direction
			int chooseX = (int)(Math.random()*2);
			int dirX;
			if(chooseX == 1){
				dirX = 1;
			}else{
				dirX = -1;
			}
			particles.add(new SmartParticle(x+hero.width/2, y+hero.height/2, dim, dim, (dirX*((Math.random()*3+.1))) + hero.dx, (dirY*((partSpeed*Math.random())))+hero.dy, c2));
			particles.get(particles.size()-1).expediteWait(10);
		}
	}//impact explosion
	
	public void genFragExplosion(SmartParticle hero){//generating explosion particles
		int dim = 5;
		int partSpeed = 25;
		for(int i = 0; i < 75; i++){
			int chooseY = (int)(Math.random()*2);
			int dirY;
			if(chooseY == 1){
				dirY = 1;
			}else{
				dirY = -1;
			}//for random Y direction
			int chooseX = (int)(Math.random()*2);
			int dirX;
			if(chooseX == 1){
				dirX = 1;
			}else{
				dirX = -1;
			}
			particles.add(new SmartParticle(hero.x+hero.width/2, hero.y+hero.height/2, dim, dim, (dirX*partSpeed*Math.random()), dirY*(partSpeed*Math.random())+hero.dy, hero.getColor()));
		}
	}//impact explosion
	
	public void genBoxExplosion(SmartParticle proj, SmartParticle boxPart){
		SmartParticle contact = boxPart;
		int partSpeed = 7;
		int dim = 4;
		SmartParticle p1 = proj;
		double vfDy = p1.dy; //vf for final velocity
		double vfDx = p1.dx;
		for(int i = 0; i < 75; i++){
			SmartParticle p2 = new SmartParticle((int)(contact.x+contact.width*Math.random()), (int)(contact.y+contact.height*Math.random()), dim, dim, Math.random()*vfDx, Math.random()*vfDy, contact.getColor());
			p2.expediteWait(40);
			particles.add(p2);
		}
	}
	
	public ArrayList<SmartParticle> getExplosion(){
		return particles;
	}
	
	public void moveExplosion(Graphics2D win){
		for(int i = 0; i < particles.size(); i++){
	
			particles.get(i).translate((int)particles.get(i).getDx(),(int)(particles.get(i).getDy()));
			particles.get(i).setDy(particles.get(i).getDy()+gravity);
			win.setColor(particles.get
					(i).getColor()); //set color of hero
			win.fill(particles.get(i));
			particles.get(i).eraseWait(); //counts live time of particle
		}
	}
	public void moveReflection(Graphics2D win){
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).translate((int)particles.get(i).getDx(),(int)(particles.get(i).getDy()));
			particles.get(i).setDy(particles.get(i).getDy());
			win.setColor(particles.get
					(i).getColor()); //set color of hero
			win.fill(particles.get(i));
			particles.get(i).eraseWait(); //counts live time of particle
		}
	}
	public void moveGrenade(Graphics2D win){
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).setDy(particles.get(i).getDy()+gravity);
			particles.get(i).translate((int)particles.get(i).getDx(),(int)(particles.get(i).getDy()));
			//particles.get(i).setDy(particles.get(i).getDy());
			win.setColor(particles.get(i).getColor()); //set color of hero
			win.fill(particles.get(i));
			particles.get(i).eraseWait(); //counts live time of particle
		}
	}
	public void explodeCollision(SmartParticle contact){//change to class of object
		for(int i = 0; i < particles.size(); i++){
			SmartParticle p1 = particles.get(i);
			double flip = -20;
			/*
			if(p1.intersects(contact)){
				int asset = sideHit(contact, p1);
				if(asset == 1){
					p1.setDx(0 + contact.dx);
				}else if(asset == 2){
					p1.setDy(-(this.getDy()+gravity));
				}else if(asset == 3){
					p1.setDx(0 + contact.dx);
				}else{ //2
					p1.setDy(-(this.getDy()+gravity));//may have to account for gravity
				}
			}
			*/
			//if(p1.intersects(contact)){
			if(sideHit(contact,p1) == 0 ){ //top
				p1.setRebound(gravity);
				p1.move(p1.x, contact.y-p1.height);
				p1.setDy(p1.reboundConstant); //strength of rebound speed
				p1.setDx(p1.getDx()*(Math.random()+ .1));
			}//when contacting a platform, particles are reflected off ground
			
			if(sideHit(contact,p1) == 1){ //left
				p1.move(contact.x - p1.width, p1.y);
				p1.setDx(-p1.getDx());
			}
			if(sideHit(contact,p1) == 2){ //bot
				p1.move(p1.x, contact.y + contact.height);
				p1.setDy(0);
			}
			if(sideHit(contact,p1) == 3){ //right
				p1.move(contact.x + contact.width, p1.y);
				p1.setDx(-p1.getDx());

			}
			
		}
	}
	
	public void laserReflect(SmartParticle contact){
		for(int i = 0; i < particles.size(); i++){
			SmartParticle p1 = particles.get(i);
			double flip = -20;
			if(sideHit(contact,p1) == 0 ){ //top
				p1.move(p1.x, contact.y-p1.height);
				p1.setDy(-p1.dy); //strength of rebound speed
				p1.setColor(contact.getColor());
			}//when contacting a platform, particles are reflected off ground
			
			if(sideHit(contact,p1) == 1){ //left
				p1.move(contact.x - p1.width, p1.y);
				p1.setDx(-p1.getDx());
				p1.setColor(contact.getColor());
			}
			if(sideHit(contact,p1) == 2){ //bot
				p1.move(p1.x, contact.y + contact.height);
				p1.setDy(-p1.dy);
				p1.setColor(contact.getColor());
			}
			if(sideHit(contact,p1) == 3){ //right
				p1.move(contact.x + contact.width, p1.y);
				p1.setDx(-p1.getDx());
				p1.setColor(contact.getColor());

			}
		}
	}
	
	public void explodeParticleReturn(SmartParticle contact){//change to class of object
		for(int i = 0; i < particles.size(); i++){
			SmartParticle p1 = particles.get(i);
			double flip = -20;
			if(sideHit(contact,p1) == 0 ){ //top
				p1.move(p1.x, contact.y-p1.height);
				p1.setDy(-p1.dy); //strength of rebound speed
			}//when contacting a platform, particles are reflected off ground
			
			if(sideHit(contact,p1) == 1){ //left
				p1.move(contact.x - p1.width, p1.y);
				p1.setDx(-p1.getDx());
			}
			if(sideHit(contact,p1) == 2){ //bot
				p1.move(p1.x, contact.y + contact.height);
				p1.setDy(-p1.dy);
			}
			if(sideHit(contact,p1) == 3){ //right
				p1.move(contact.x + contact.width, p1.y);
				p1.setDx(-p1.getDx());

			}
			
		}
	}
	
	public boolean removeParticle(){//slow
		for(int i = 0; i < particles.size(); i++){
			SmartParticle p2 = particles.get(i);
			if(p2.deleteTimer >= 90){
				particles.remove(i);
				i--;
			}
		}
		if(particles.size()==0){
			return false;
		}
		return true;//returns true if not all particles are gone
	}//removes particles after some time
	
	public boolean removeModerate(){
		for(int i = 0; i < particles.size(); i++){
			SmartParticle p2 = particles.get(i);
			if(p2.deleteTimer >= 50){
				particles.remove(i);
				i--;
			}
		}
		if(particles.size()==0){
			return false;
		}
		return true;//returns true if not all particles are gone
	}//removes particles after some time
	
	public boolean removeGrenade(){//fast
		for(int i = 0; i < particles.size(); i++){
			SmartParticle p2 = particles.get(i);
			if(p2.deleteTimer >= 8){//don't want to long of a grenade effect
				particles.remove(i);
				i--;
			}
		}
		if(particles.size()==0){
			return false;
		}
		return true;//returns true if not all particles are gone
	}//removes particles after some time
	
	
	
}