import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class SmartParticle extends Rectangle{ 
	double dx,dy;
	public Color c1;
	public boolean validIdentity = false;
	public int deleteTimer = 0;
	public static double gravity = .5;
	public static double friction = .1;
	public double reboundConstant = -5;
	public SmartParticle(){
		super(0,0,0,0);
		dx = 0;
		dy = 0;
		c1 = Color.CYAN;
	}
	public SmartParticle(double dx, double dy){
		this(); this.dx=dx; this.dy =dy;
	}
	public SmartParticle(int x, int y, int width, int height, double dx, double dy){
		super(x,y,width,height);this.dy = dy; this.dx = dx; c1 = Color.CYAN;
	}
	public SmartParticle(int x, int y, int width, int height, double dx, double dy, Color c){
		super(x,y,width,height);this.dy = dy; this.dx = dx; c1 = c;
	}
	public SmartParticle(int x,int y,int width,int height){
		super(x,y,width,height);
	}
	public SmartParticle(int x,int y,int width,int height, Color c){
		super(x,y,width,height); c1 = c;
	}
	
	public int sideHit(SmartParticle block, SmartParticle hero){
			int result = -1;
			double wallDx = block.getDx(); //platform
			double heroDy = hero.getDy(); //dy hero
		if( hero.x + hero.height <= block. x && block.x - (hero.x + hero.width) <= hero.getDx() - wallDx && hero.y < block.y+ block.height && hero.y+ hero.height > block.y){//compared is total distance moved before contact
			result =  1;
		}//if hero to left (so smaller x)
		if( hero.x >= block.x + block.width && (block.x + block.width - hero.x) >= hero.getDx() - wallDx && hero.y < block.y+ block.height && hero.y+ hero.height > block.y){
			result = 3;
		}//if hero to right (so greater x), remaining distance is less than projected distance, left corner in bounds, and right corner in bounds of block
		if(hero.y+ hero.height <= block.y && (block.y -(hero.y+hero.height)) <= hero.dy && hero.x < block.x + block.width && hero.x + hero.width > block.x){
			result = 0;
		}//if hero above (so smaller y), distance is less than speed, left corner in bounds, and right corner in bounds of block
		//checked
		if(hero.y >= block.y + block.height && (hero.y - (block.height + block.y) <= -hero.dy && hero.x < block.x + block.width && hero.x + hero.width > block.x)){	
			result = 2;
		}//hero below block (so greater y), distance, and between
		//checked
		return result;
		/*
		if((block.y + block.height - hero.y) <= -heroVerticalConstant){
			return 2;
		}
		*/
		// 0 = top of wall
		// 1 = back of wall
		// 2 = bot
		// 3 = front of watll
	
	}
	
	public boolean hitDetection(SmartParticle contact, SmartParticle proj){ //substitute for intersection
		if(sideHit(contact, proj) ==0 || sideHit(contact,proj) == 1){
			return true;
		}
		if(sideHit(contact, proj) ==2 || sideHit(contact,proj) == 3){
			return true;
		}
		if(proj.intersects(contact)){
			return true;
		}
		return false;
	}
	
	public void collision(SmartParticle contact){//change to class of object
		if(this.intersects(contact)){
			int asset = sideHit(contact, this);
			if(asset == 1){
				this.setDx(0 + contact.dx);
			}else if(asset == 2){
				this.setDy(0);
			}else if(asset == 3){
				this.setDx(0 + contact.dx);
			}else{ //2
				this.setDy(0);//may have to account for gravity
			}
		}
	}
	
	public void heroCollision(SmartParticle contact){//change to class of object
			if(sideHit(contact,this) == 0 ){ //top
				this.move(this.x, contact.y-this.height);
				this.setDy(-gravity); //strength of rebound speed
			}//when contacting a platform, particles are reflected off ground
			
			if(sideHit(contact,this) == 1){ //left
				this.move(contact.x - this.width, this.y);
				this.setDx(0);
			}
			if(sideHit(contact,this) == 2){ //bot
				this.move(this.x, contact.y + contact.height);
				this.setDy(0);
			}
			if(sideHit(contact,this) == 3){ //right
				this.move(contact.x + contact.width, this.y);
				this.setDx(0);
			
			}
	}
	
	public void moveHero(boolean right, boolean left){
		int heroSpeed = 10;
		if(right){
			this.setDx(heroSpeed);
		}else if(left){
			this.setDx(-heroSpeed);
		}else{
			this.setDx(0);
		}
		/*
		if(up){
			this.setDy(-heroSpeed);
		}else if(down){
			//this.setDy(heroSpeed);
		}else{
			this.setDy(0);
		}
		*/
		
		
	}
	
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
	
	public Color getColor(){ return c1;}
	public void setColor(Color col){ c1 = col;}
	public double getDx(){return dx;}
	public void setDx(double value){dx = value;}
	public double getDy(){return dy;} 
	public void eraseWait(){deleteTimer++; }  // times delete
	public void expediteWait(int x){
		deleteTimer += x;
	}
	public int eraseTime(){ return deleteTimer;}
	public void setRebound(double r){reboundConstant +=r;}
	public double getRebound(){return reboundConstant;}
	public void setDy(double value){ dy = value;}
	public void moveAndDraw(Graphics2D win){
		this.translate((int)this.dx, (int)this.dy);
		win.setColor(c1);
		win.fill(this);
	}
	public void setId(boolean change){
		validIdentity = change;
	}
	public boolean getId(){
		return validIdentity;
	}
	public void moveAndDrawHero(Graphics2D win, SmartParticle contact){
		this.setDy(this.getDy() + gravity);
		/*
		if(sideHit(contact,this) == 0 ){ //top
			this.move(this.x, contact.y-this.height);
			this.setDy(-2*gravity); //strength of rebound speed
		}//when contacting a platform, particles are reflected off ground
		
		if(sideHit(contact,this) == 1){ //left
			this.move(contact.x - this.width, this.y);
			this.setDx(0);
		}
		if(sideHit(contact,this) == 2){ //bot
			this.move(this.x, contact.y + contact.height);
			this.setDy(0);
		}
		if(sideHit(contact,this) == 3){ //right
			this.move(contact.x + contact.width, this.y);
			this.setDx(0);
		
		}
		*/
		if(sideHit(contact, this) == 0 && this.y + this.height <= contact.y + this.getDy()){
			this.move(this.x, contact.y-this.height);
			this.translate((int)this.getDx(),0);
		}else{
			this.translate((int)this.getDx(), (int)this.getDy());
		}
		//this.setDy(this.getDy()+gravity);
		win.setColor(this.getColor()); //set color of hero
		win.fill(this);
		
	}
	
	public SmartParticle fire(){
		
			SmartParticle p1 = new SmartParticle(this.x  + (this.width -4)/2, this.y, 4, 10, 0, -15, Color.RED);
			//p1.setColor(Color.RED)
			//int i = this.x + (this.width-4)/2;
		
		return p1;
	}
	
	public void removeColor(){
		this.setColor(null);
	}
}
