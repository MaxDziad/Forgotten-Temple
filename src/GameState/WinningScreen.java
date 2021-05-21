package GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static GameMain.GamePanel.HEIGHT;
import static GameMain.GamePanel.WIDTH;

public class WinningScreen extends ChoosableMenu{

    private static final Color BLACK_TRANSPARENT = new Color(0,0,0,0.4f);
    private boolean isBlackTransparentDrawn;

    public WinningScreen(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void initialize() {
        currentChoiceColor = new Color(136, 236, 91);
        otherChoiceColor = new Color(32, 118, 28, 255);
        font = new Font("Arial", Font.PLAIN, 60);
        options = new String[] {
                "Play again",
                "Main menu",
                "Quit"
        };
        isBlackTransparentDrawn = false;
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
    
    private void drawYouWinImage(Graphics2D g){
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Images/youWin.png"));
            g.drawImage(image,(WIDTH-image.getWidth()) / 2,100,null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void drawBlackTransparentBackground(Graphics2D g){
        g.setColor(BLACK_TRANSPARENT);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        isBlackTransparentDrawn = true;
    }
    
    @Override
    public void update() {}
    
    @Override
    public void draw(Graphics2D g) {
        if(!isBlackTransparentDrawn) drawBlackTransparentBackground(g);
        drawYouWinImage(g);
        drawOptions(g);
    }

}
