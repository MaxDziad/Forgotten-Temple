package TileMap;

import GameMain.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage image;
	private double x_position;
	private double y_position;
	private double x_vector;
	private double y_vector;
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
	
	public void setPosition(double x, double y) {
		this.x_position = (x * moveScale) % GamePanel.WIDTH;
		this.y_position = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.x_vector = dx;
		this.y_vector = dy;
	}
	
	public void update() {
		x_position += x_vector;
		y_position += y_vector;
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int) x_position, (int) y_position, null);
		
		if(x_position < 0) {
			g.drawImage(image, (int) x_position + GamePanel.WIDTH, (int) y_position, null);
		}
		if(x_position > 0) {
			g.drawImage(image, (int) x_position - GamePanel.WIDTH, (int) y_position, null);
		}
	}
	
}







