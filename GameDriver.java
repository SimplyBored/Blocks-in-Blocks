
/**
 * @(#)GameDriver.java
 *
 *
 * @author Mr. Gonzalez, Nicholas Hernandez
 * @version 1.32 6/2/2015
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
public abstract class GameDriver extends Canvas implements KeyListener {

	protected boolean[] keys;
	protected BufferedImage back;
	protected int FramesPerSecond = 25;
	protected long timer = 1000 / FramesPerSecond;
	protected Timer t1 = new Timer();
	
	public GameDriver() {
		// set up all variables related to the game

		// number of key possibilities
		keys = new boolean[16];

		setBackground(Color.WHITE);
		setVisible(true);
		t1.scheduleAtFixedRate(new ThreadTimer(this), 0, timer);
		addKeyListener(this); // starts the key thread to log key strokes
		setFocusable(true);
	}

	public void update(Graphics window) {
		paint(window);
	}

	public void setTimer(int value) {
		timer = value;
	}

	public void paint(Graphics window) {
		if (back == null)
			back = (BufferedImage) (createImage(getWidth(), getHeight()));
		Graphics2D graphToBack = (Graphics2D) back.createGraphics();

		draw(graphToBack);

		Graphics2D win2D = (Graphics2D) window;
		win2D.drawImage(back, null, 0, 0);

	}

	public abstract void draw(Graphics2D win);

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			keys[0] = true;
			break;
		case KeyEvent.VK_S:
			keys[1] = true;
			break;
		case KeyEvent.VK_A:
			keys[2] = true;
			break;
		case KeyEvent.VK_D:
			keys[3] = true;
			break;
		case KeyEvent.VK_F:
			keys[4] = true;
			break;

		case KeyEvent.VK_8:
			keys[5] = true;
			break;
		case KeyEvent.VK_5:
			keys[6] = true;
			break;
		case KeyEvent.VK_4:
			keys[7] = true;
			break;
		case KeyEvent.VK_6:
			keys[8] = true;
			break;
		case KeyEvent.VK_PLUS:
			keys[9] = true;
			break;
		case KeyEvent.VK_ENTER:
			keys[10] = true;
			break;
		case KeyEvent.VK_SPACE:
			keys[11] = true;
			break;
		case KeyEvent.VK_UP:
			keys[12] = true;
			break;
		case KeyEvent.VK_DOWN:
			keys[13] = true;
			break;
		case KeyEvent.VK_LEFT:
			keys[14] = true;
			break;
		case KeyEvent.VK_RIGHT:
			keys[15] = true;
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			keys[0] = false;
			break;
		case KeyEvent.VK_S:
			keys[1] = false;
			break;
		case KeyEvent.VK_A:
			keys[2] = false;
			break;
		case KeyEvent.VK_D:
			keys[3] = false;
			break;
		case KeyEvent.VK_F:
			keys[4] = false;
			break;

		case KeyEvent.VK_8:
			keys[5] = false;
			break;
		case KeyEvent.VK_5:
			keys[6] = false;
			break;
		case KeyEvent.VK_4:
			keys[7] = false;
			break;
		case KeyEvent.VK_6:
			keys[8] = false;
			break;
		case KeyEvent.VK_PLUS:
			keys[9] = false;
			break;
		case KeyEvent.VK_ENTER:
			keys[10] = false;
			break;
		case KeyEvent.VK_SPACE:
			keys[11] = false;
			break;
		case KeyEvent.VK_UP:
			keys[12] = false;
			break;
		case KeyEvent.VK_DOWN:
			keys[13] = false;
			break;
		case KeyEvent.VK_LEFT:
			keys[14] = false;
			break;
		case KeyEvent.VK_RIGHT:
			keys[15] = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}
	
	private class ThreadTimer extends TimerTask {
		GameDriver driver;

		public ThreadTimer(GameDriver gameDrive) {
			driver = gameDrive;
		}

		@Override
		public void run() {
			driver.repaint();
			System.gc();
		}
	}
	public class timerDriver extends Thread{
		int delay;
		public timerDriver(int _delayInMiliseconds){
			delay= _delayInMiliseconds;
		}
		
		public void run(){
			
		}
		
		
	}
	
	public BufferedImage addImage(String name)  {

		BufferedImage img = null;
		try {
		
			img = ImageIO.read(this.getClass().getResource(name));
		
		} catch (IOException e) {
			System.out.println(e);
		}
		
		return img;

	}

}