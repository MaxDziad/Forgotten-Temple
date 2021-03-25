package GameMain;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Super Student Bros");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
	
}

