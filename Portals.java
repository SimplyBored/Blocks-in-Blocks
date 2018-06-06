import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Portals extends Effects{
	SmartParticle pPortal = new SmartParticle(-100,-100,0,0);
	SmartParticle sPortal = new SmartParticle(-100,-100,0,0);
	Color pColor = Color.CYAN;
	Color sColor = Color.ORANGE;
	Circle p1 = new Circle(pPortal);
	Circle p2 = new Circle(sPortal);
	boolean pGood, sGood, valid;
	
	public Portals(){//portals are destroyed on impact
		
	}
	
	public void paintComponent(Graphics g){
		//g.setColor(Color.BLACK);
		g.fillOval(pPortal.x + pPortal.width/2, pPortal.y + pPortal.height/2,50, 50);
		//g.setColor(Color.WHITE);
		g.fillOval(sPortal.x + sPortal.width/2, sPortal.y + sPortal.height/2,50, 50);
	}
	
	public void genPortals(Arena box){//50 by 50 portals
		double originX;
		double originY;
		double resultX;
		double resultY;
		ArrayList<SmartParticle> arena = box.getArena();
		int dim = 50;//portal dimension
		int symmetry = (int)(Math.random()*2);
		if(symmetry == 0){//reflected along the Y axis
			originX = Math.random()*450 +100;
			originY = Math.random()*400 + 75;
			resultX = Math.random()*400 + 75 + originX;
			resultY = Math.random()*400 +75;
		}else{
			originX = Math.random()*900 + 60;
			originY = Math.random()*200 + 70;
			resultX = Math.random()*900 + 60;
			resultY = Math.random()*200 + 50 + originY;
		}
		pPortal = new SmartParticle((int)originX, (int)originY, 50, 50, pColor);
		sPortal = new SmartParticle((int)resultX, (int)resultY, 50, 50, sColor);
		for(int i = 0; i < arena.size(); i++){
			if(arena.get(i).intersects(pPortal) || arena.get(i).intersects(sPortal)){
				genPortals(box);
			}
		}
	}
	

	public void genPortalsForced(Arena box){//50 by 50 portals
		
		ArrayList<SmartParticle> arena = box.getArena();
		pPortal = new SmartParticle(150, 275, 50, 50, pColor);
		sPortal = new SmartParticle(650, 275, 50, 50, sColor);
	}
	
	public void genPast(boolean past){//50 by 50 portals
		if(past){
		pPortal = new SmartParticle(325, 490, 50, 50, pColor);
		sPortal = new SmartParticle(-100, 275, 50, 50, sColor);
		}else{
			pPortal = new SmartParticle(-100, 500, 50, 50, pColor);
			sPortal = new SmartParticle(325, 490, 50, 50, sColor);
		}
	}
	
	public void genFuture(){
		
	}
	
	public void genStop(boolean both){
		pPortal = new SmartParticle(800, 490, 50, 50, pColor);
		sPortal = new SmartParticle(-1000, 490, 50, 50, new Color(0,0,0,0));
		if(both){
			sPortal = new SmartParticle(470, 490, 50, 50, sColor);
		}
	}
	
	public void genSuction(){
		int distanceY;
		int distanceX;
		double rDx;
		double rDy;
		double speed = 2;// make it so speed increases over time
		int dim = 50; //portal dimension
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
			distanceY = dirY*10 +dirY*(int)(Math.random()*80); //random Y from portal
			distanceX = dirX*10 +dirX*(int)(Math.random()*80);//random X from portal
			dirX *= -1;//changing signs because velocity is opposite position vector
			dirY *=-1;
			rDy = dirY*Math.random()*speed + dirY*1;
			rDx = dirX*Math.random()*speed + dirX*1;
			SmartParticle p1;
			if(i%2 == 0){
				p1 = new SmartParticle(pPortal.x + (int)(pPortal.width/2) + distanceX, pPortal.y + (int)(pPortal.height/2) + distanceY, 2, 2, pPortal.getColor());
			}else{
				p1 = new SmartParticle(sPortal.x + (int)(sPortal.width/2) + distanceX, sPortal.y + (int)(sPortal.height/2) + distanceY, 2, 2, sPortal.getColor());
			}
			p1.setDx(rDx);
			p1.setDy(rDy);
			p1.expediteWait(70);
			particles.add(p1);
		}
	}
	
	public void genExplosion(SmartParticle hero){//generating explosion particles
		int dim = 2;
		int partSpeed = 10;
		for(int i = 0; i < 40; i++){
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
			expediteWait(40);
		}
	}//impact explosion
	
	public boolean teleport(SmartParticle transport){//valid when intersecting either portal to avoid continuous slip
		if(!transport.intersects(sPortal) && !transport.intersects(pPortal)){
			transport.setId(false); //its a method to differentiated teleported particles
		}
		if(transport.intersects(pPortal) && !transport.getId()){
			transport.move(sPortal.x+(sPortal.width - transport.width)/2 , sPortal.y + (sPortal.height - transport.height)/2);
			transport.setId(true);
			return true;
		}
		if(transport.intersects(sPortal) && !transport.getId()){
			transport.move(pPortal.x+(pPortal.width - transport.width)/2 , pPortal.y + (pPortal.height - transport.height)/2);
			transport.setId(true);
			return true;
		}
		return false;
	}
	
	public void moveAndDrawPortals(Graphics2D win){
		genSuction();
		//pPortal.moveAndDraw(win);
		//sPortal.moveAndDraw(win);
		//this.paintComponent(null);
		//p1.paintComponent(null);
		//p2.paintComponent(null);
		//p1.portalReasses(pPortal);
		//p2.portalReasses(sPortal);
		//win.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
		win.setColor(pColor);
		win.fillOval(pPortal.x , pPortal.y -pPortal.height/8, 50, 60);
		win.setColor(sColor);
		win.fillOval(sPortal.x , sPortal.y - sPortal.height/8 , 50, 60);
		//win.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		double dirX;
		double dirY;
		for(int i = 0; i < particles.size(); i++){
			dirX = Math.abs(particles.get(i).dx)/particles.get(i).dx;
			dirY = Math.abs(particles.get(i).dy)/particles.get(i).dy;
			SmartParticle p1 = particles.get(i);
			particles.get(i).setDx(p1.dx + .1*dirX);
			particles.get(i).setDy(p1.dy + .1*dirY);
			particles.get(i).eraseWait();
			particles.get(i).moveAndDraw(win);
		}
		pPortal.eraseWait();
		sPortal.eraseWait();
	}
	
	public boolean removePortals(){
		if(pPortal.deleteTimer > 500){
			pPortal.deleteTimer = -100;
			sPortal.deleteTimer = -100;
			genExplosion(pPortal);
			genExplosion(sPortal);
			pPortal.setBounds(-1000, -1000, 0, 0); //moves it off screen
			sPortal.setBounds(-1000, -1000, 0, 0); //moves it off screen
			return true;
		}
		return false;
	}
	
	public void forceDestroy(){
		pPortal.expediteWait(500);
	}

}
