import java.awt.Color;


public class Hero extends SmartParticle {
	
	public Hero(){
		
	}
	public Hero(double dx, double dy){
		this(); this.dx=dx; this.dy =dy;
	}
	public Hero(int x, int y, int width, int height, double dx, double dy){
		super(x,y,width,height);this.dy = dy; this.dx = dx; c1 = Color.CYAN;
	}
	public Hero(int x, int y, int width, int height, double dx, double dy, Color c){
		super(x,y,width,height);this.dy = dy; this.dx = dx; c1 = c;
	}
	public Hero(int x,int y,int width,int height){
		super(x,y,width,height);
	}
	public Hero(int x,int y,int width,int height, Color c){
		super(x,y,width,height); c1 = c;
	}
	
	/*
	public void moveBackground(SmartParticle grass, boolean up){
		double tempSpeed = this.getDy();
		if(this.x - grass.x > 300 && this.x - grass.x <900){
			if(up){
				tempSpeed += this.getDy();
				grass.setDy(tempSpeed + gravity);
			}
		}
	}
	*/
	
	public void jump(boolean up, SmartParticle contact){
		int heroSpeed = 10;
		if(up && sideHit(contact, this) == 0){
			this.setDy(-heroSpeed);
		}
		if(up && sideHit(contact, this) == 1){
			this.setDy(-heroSpeed);
		}
		if(up && sideHit(contact, this) == 3){
			this.setDy(-heroSpeed);
		}
	}
	
	public void setColorGone(){
		c1 = new Color(0,0,0, 0);
	}
}
