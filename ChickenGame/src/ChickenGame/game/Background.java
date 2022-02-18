package ChickenGame.game;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;


public class Background extends Sprite {

    private final Image image;
    
    public Background(int x, int y, int speed) {
        super(x, y, speed);
        this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/bg_game.png")).getImage();
    }

    @Override
    protected void draw(Graphics2D g2D) {
        g2D.drawImage(this.image, getX(), getY(), null);
    }
}