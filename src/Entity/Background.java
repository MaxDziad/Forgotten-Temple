package Entity;

import GameMain.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage image;
	private double x_position;
	private double y_position;
	
	// how fast background is moving with player
	private double moveScale;
	
	public Background(String s, double ms) {
		
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// mostly used to move background while player is moving
	public void setPosition(double x, double y) {
		this.x_position = (x * moveScale) % GamePanel.WIDTH;
		this.y_position = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	private void loopBackground(Graphics2D g){
		if(x_position < 0) {
			g.drawImage(image, (int) x_position + GamePanel.WIDTH, (int) y_position, null);
		}
		if(x_position > 0) {
			g.drawImage(image, (int) x_position - GamePanel.WIDTH, (int) y_position, null);
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) x_position, (int) y_position, null);
		loopBackground(g);
	}
	
}







