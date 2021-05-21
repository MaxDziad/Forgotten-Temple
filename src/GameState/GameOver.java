package GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static GameMain.GamePanel.HEIGHT;
import static GameMain.GamePanel.WIDTH;

public class GameOver extends ChoosableMenu {
    
    public GameOver(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void initialize() {
        currentChoiceColor = new Color(255,50,50);
        otherChoiceColor = Color.YELLOW;
        font = new Font("Arial", Font.PLAIN, 60);
        options = new String[] {
                "Restart level",
                "Main menu",
                "Quit"
        };
        x = 320;
        y = 400;
        gap = 80;
    }
    
    @Override
    protected void select() {
        switch(currentChoice) {
            case 0 -> gsm.restartLevel1();
            case 1 -> gsm.setState(GameStateManager.MENU);
            case 2 -> System.exit(0);
        }
    }
    
    private void drawGameOverImage(Graphics2D g){
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Images/gameOver.png"));
            g.drawImage(image,(WIDTH-image.getWidth()) / 2,100,null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g) {
        // black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        drawGameOverImage(g);
        drawOptions(g);
    }

}
