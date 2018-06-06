import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Circle extends JPanel {
	SmartParticle portal;
	
	public Circle(SmartParticle portal){
		this.portal = portal;
	}
	
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		this.getGraphics();
		g.setColor(portal.getColor());
		g.fillOval(portal.x, portal.y, 50, 50);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(portal.getColor());
		g.fillOval(portal.x, portal.y, 50, 50);
	}
	
	public void portalReasses(SmartParticle newPortal){
		this.portal = newPortal;
	}
}
