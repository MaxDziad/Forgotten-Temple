package GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static GameMain.GamePanel.HEIGHT;
import static GameMain.GamePanel.WIDTH;

public class GameOver extends ChoosableMenu {
    
    private static final Color BLACK_TRANSPARENT = new Color(0,0,0,0.4f);
    private boolean isBlackTransparentDrawn;

    public GameOver(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void initialize() {
        currentChoiceColor = new Color(248, 7, 7);
        otherChoiceColor = new Color(149, 37, 37);
        font = new Font("Arial", Font.PLAIN, 60);
        options = new String[] {
                "Restart level",
                "Main menu",
                "Quit"
        };
        isBlackTransparentDrawn = false;
        x = 320;
        y = 400;
        gap = 80;
    }
    @Override
    public void update() {}
    
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
    public void draw(Graphics2D g) {
        if(!isBlackTransparentDrawn) {
            g.setColor(BLACK_TRANSPARENT);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            isBlackTransparentDrawn = true;
        }
        drawGameOverImage(g);
        drawOptions(g);
    }

    @Override
    protected void select() {
        switch(currentChoice) {
            case 0 -> gsm.restartLevel1();
            case 1 -> gsm.setState(GameStateManager.MENU);
            case 2 -> System.exit(0);
        }
    }

}
