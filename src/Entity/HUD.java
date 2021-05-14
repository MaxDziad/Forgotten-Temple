package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {

    private Player player;
    private BufferedImage image;
    private Font font;


    public HUD(Player p){
        player = p;
        initializeHUDImage();

    }
    
    public void initializeHUDImage(){
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Background/hud.png"));
            font = new Font("Arial",Font.PLAIN,30);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(image, 0, 20, null);
        g.setFont(font);
        g.setColor(Color.BLUE);
        g.drawString(player.getCurrentHealth() +"/" + player.getMaxHealth(),60,50);
        //For testing
        g.drawString("x: " + player.getX(),60,80);
        g.drawString("y: " + player.getY(),60,100);
    }
}
