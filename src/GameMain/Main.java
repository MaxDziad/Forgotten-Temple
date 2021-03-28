package GameMain;

import javax.swing.*;
import java.awt.*;

public class Main {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Super Student Bros");
		//Center game on screen
		window.setSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
		window.setLocationRelativeTo(null);
		
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
	
}

