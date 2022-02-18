package ChickenGame.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import ChickenGame.game.GamePanel;

public class Chicken extends Sprite{
private Image image;
    
    public Chicken(int x, int y, int speed) {
        super(x, y, speed*3);
        if(GamePanel.which_way==1)
        this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/chickentoleft.png")).getImage();
        else{
            this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/chickentoright.png")).getImage();
        }
    }

    @Override
    protected void draw(Graphics2D g2D) {
        if(GamePanel.which_way==1)
        this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/chickentoleft.png")).getImage();
        else{
            this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/chickentoright.png")).getImage();
        }
        g2D.drawImage(this.image,getX() ,getY() , null);
    }
    
    void moveleft() {
        incSpeed();
        if((getX() - getSpeed() ) < 50)
            setX(50);
        else
        setX(getX() - getSpeed());
    }

    void moveright() {
        incSpeed();
        if((getX() + getSpeed()) > GAME__WIDTH - 78)
            setX(GAME__WIDTH - 78);
        else
            setX(getX() + getSpeed());
    }
    
    private void incSpeed(){
        if(getSpeed() < AUTO_SPEED){
            setSpeed(getSpeed() + 3);
        }
    }

    void speedReset() {
        setSpeed(0);
    }
    
    public Rectangle getBound(){
        return new Rectangle(getX(),getY(),this.image.getWidth(null),this.image.getHeight(null));
    } 
}