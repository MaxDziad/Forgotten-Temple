package GameState;

import java.awt.*;

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
        currentChoiceColor = new Color(255,50,50);
        otherChoiceColor = Color.YELLOW;
        font = new Font("Arial", Font.PLAIN, 60);
        options = new String[] {
                "Restart level",
                "Main menu",
                "Quit"
        };
        isBlackTransparentDrawn = false;
        x = 350;
        y = 300;
        gap = 80;
    }
    @Override
    public void update() {}
    
    private void drawGameOver(Graphics2D g){
        g.setFont(new Font("Arial",Font.BOLD,80));
        g.setColor(Color.BLUE);
        g.drawString("GAME OVER", 280,120);
    }

    @Override
    public void draw(Graphics2D g) {
        if(!isBlackTransparentDrawn) {
            g.setColor(BLACK_TRANSPARENT);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            isBlackTransparentDrawn = true;
        }
        drawOptions(g);
        drawGameOver(g);
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
