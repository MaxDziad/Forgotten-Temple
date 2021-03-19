package Sprites;
import java.awt.*;

//Every drawable object in this game will implement Sprite
interface Sprite {
	
	// Draw itself
	public void draw(Graphics2D g2d);
	
	// Initialize (or re-initialize) this actor at the start of the game.
	public void regenerate();
	
	// For collision detection
	public boolean intersects(Shape shape);     // intersects with the given shape
	public boolean contains(Shape shape);       // completely encloses the given shape
	public Shape getBounds2D();                 // returns a bounding box
}
