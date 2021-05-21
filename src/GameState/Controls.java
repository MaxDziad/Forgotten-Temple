package GameState;

import Entity.MapObject;
import TileMap.Background;
import TileMap.TileMap;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public class Controls extends GameState  {

    private Background bg;
    private BufferedImage imageA,imageD,imageDown,imageE,imageS,imageShift,imageUp,imageW;

    private String[] options = {
            "LEFT",
            "RIGHT",
            "JUMP",
            "DOWN",
            "ATTACK",
            "RUN",
    };

    private Color fontColor;
    private Font font;


    public Controls(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void initialize() {
        try {
            bg = new Background("/Background/menu.png", 1);

            fontColor = new Color(0,0,0);
            font = new Font("Arial", Font.PLAIN, 75);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        bg.update();
    }

    public void drawBackground(Graphics2D g){
        bg.draw(g);
    }

    public void drawOptions(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(font);

        try {
            imageA = ImageIO.read(getClass().getResourceAsStream("/Controls/a.png"));
            imageD = ImageIO.read(getClass().getResourceAsStream("/Controls/d.png"));
            imageDown = ImageIO.read(getClass().getResourceAsStream("/Controls/down.png"));
            imageE = ImageIO.read(getClass().getResourceAsStream("/Controls/e.png"));
            imageS = ImageIO.read(getClass().getResourceAsStream("/Controls/s.png"));
            imageShift = ImageIO.read(getClass().getResourceAsStream("/Controls/shift.png"));
            imageUp = ImageIO.read(getClass().getResourceAsStream("/Controls/up.png"));
            imageW = ImageIO.read(getClass().getResourceAsStream("/Controls/w.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for(int i = 0; i < options.length; i++){
            g.drawString(options[i], 500, 200 + i * 80);
        }
        g.drawImage(imageA,300,132,null);
        g.drawImage(imageD,300,212,null);
        g.drawImage(imageW,250,292,null);
        g.drawImage(imageUp,330,292,null);
        g.drawImage(imageS,215,372,null);
        g.drawImage(imageDown,280,372,null);
        g.drawImage(imageE,300,452,null);
        g.drawImage(imageShift,250,532,null);

        g.setFont(new Font("Arial",Font.BOLD,40));
        g.setColor(Color.RED);
        g.drawString("Click ESC to back to the menu.",400,700);
    }

    public void draw(Graphics2D g){
        drawBackground(g);
        drawOptions(g);
    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ESCAPE){
            gsm.fromPauseToMenu();
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
