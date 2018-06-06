import java.awt.Color;
import java.awt.Graphics2D;


public class SmartBullet extends SmartParticle {
		int reflectCnt = 0;
		int stayCnt = 0;
		
		public SmartBullet(){
		}	
		public SmartBullet(double dx, double dy){
			this(); this.dx=dx; this.dy =dy;
		}
		public SmartBullet(int x, int y, int width, int height, double dx, double dy){
			super(x,y,width,height);this.dy = dy; this.dx = dx; c1 = Color.CYAN;
		}
		public SmartBullet(int x, int y, int width, int height, double dx, double dy, Color c){
			super(x,y,width,height);this.dy = dy; this.dx = dx; c1 = c;
		}
		public SmartBullet(int x,int y,int width,int height){
			super(x,y,width,height);
		}
		public SmartBullet(int x,int y,int width,int height, Color c){
			super(x,y,width,height); c1 = c;
		}
		
		public void bulletReflect(){
			reflectCnt++;
		}
		
		public int getReflect(){
			return reflectCnt;
		}
		
		public boolean bulletCollision(SmartParticle contact){//change to class of object
			if(sideHit(contact,this) == 0 ){ //top
				this.move(this.x, contact.y-this.height);
				this.setDy(-this.getDy()); //strength of rebound speed
				bulletReflect();
				return true;
			}//when contacting a platform, particles are reflected off ground
			
			if(sideHit(contact,this) == 1){ //left
				this.move(contact.x - this.width, this.y);
				this.setDx(-this.getDx());
				bulletReflect();
				return true;
			}
			if(sideHit(contact,this) == 2){ //bot
				this.move(this.x, contact.y + contact.height);
				this.setDy(-this.getDy());
				bulletReflect();
				return true;
			}
			if(sideHit(contact,this) == 3){ //right
				this.move(contact.x + contact.width, this.y);
				this.setDx(-this.getDx());
				bulletReflect();
				return true;
			
			}
			return false;
	}
	
	public void moveAndDraw(Graphics2D win){
		this.translate((int)this.dx, (int)this.dy);
		win.setColor(c1);
		win.fill(this);
		stayCnt++;
	}
	
	public void moveAndDrawGrenade(Graphics2D win){
		this.setDy(this.dy + gravity);
		if(Math.abs(this.dx) > .3){
			if(this.dx > 0){
				this.setDx(this.dx - friction);//works in direction opposite of motion
			}else{
				this.setDx(this.dx + friction);//works in positive while going negatvie
			}
		}
		this.translate((int)this.dx,  (int)this.dy);
		win.setColor(c1);
		win.fill(this);
		stayCnt ++;
	}
		
	public boolean wornBullet(){
		if(this.reflectCnt > 10 || stayCnt > 200){
			return true;
		}
		return false;
	}
	
	public boolean frag(){
		if(stayCnt > 150){
			return true;
		}
		return false;
	}
		
}

