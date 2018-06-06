import javax.swing.JFrame;


public class FinalWindow {
	public static void main(String[] args){
		JFrame j1 = new JFrame();
		j1.setSize(1200, 600);
		j1.setTitle("*Window Name*");
		j1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FinalField b1 = new FinalField();
		j1.add(b1);
		j1.setVisible(true);
	}
}
