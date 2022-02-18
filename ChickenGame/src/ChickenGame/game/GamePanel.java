package ChickenGame.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;

public class GamePanel extends Canvas implements Runnable{
    private Image image; 
    private Image lifeImage; 
    private Image heartImage; 
    private boolean menustage=true;
    private Thread gameThread;
    private final Sprite background = new Background(0, 0, 0);
    private final Chicken tank = new Chicken(GAME__WIDTH/2 - 60, GAME__HEIGHT -550, 3);
    private boolean isRunning;
    private final ArrayList<Egg> eggs = new ArrayList<>();
    private final ArrayList<Cat> cats = new ArrayList<>();
    private final ArrayList<Target> targets = new ArrayList<>();
    public static int which_way = 1;
    public int  LEVEL_COOFICIENT=1;
    private int life=3;
    private int score=0;
    private boolean LevelFinished = false;
    private boolean YouAreDead = false;
    private boolean isrunning2 = false;
    
    public GamePanel(){
        setPreferredSize(new Dimension(GAME__WIDTH,GAME__HEIGHT));
        this.lifeImage = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/life.png")).getImage();
        this.heartImage = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/heart.png")).getImage();
    }
    @Override
    public void addNotify(){
        super.addNotify();
        if(gameThread == null){
            gameThread = new Thread(GamePanel.this);
        }
        gameThread.start();
        
    }
    @Override
    protected void onKeyUp(KeyEvent event) {
        tank.speedReset();
    }

    @Override
    protected void onKeyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_SPACE){
            if(menustage){
                menustage=false;
                isrunning2 = true;
            }
            else{
                eggs.add(new Egg(tank.getX(), tank.getY(),5,which_way));
            }
        }else if(event.getKeyCode() == KeyEvent.VK_LEFT){
            if(menustage){
                if(diff>0) {
                diff--;
            } 
            }
            else{
            tank.moveleft();
            which_way = 1;
            }
        }else if(event.getKeyCode() == KeyEvent.VK_RIGHT){
            if(menustage){
                if(diff<2) {
                diff++;
            }  
            }
            else{
                tank.moveright();
            which_way = -1;
            }
            
        }else if(event.getKeyCode() == KeyEvent.VK_ENTER){
             if (LevelFinished){
                LevelFinished=false;
                isrunning2 = true;
                tank.setX(GAME__WIDTH/2 - 60);
                tank.setY(GAME__HEIGHT -550);
              if(LEVEL_COOFICIENT==4){
                  isRunning=false;
                  System.exit(0);
              }
            }
        }
    }

    @Override
    protected void onDraw(Graphics2D g2D) {
       if(menustage){
        this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/menu.png")).getImage();
        g2D.setFont(new Font("default", Font.BOLD, 30));
        g2D.drawImage(this.image,0 ,0, null);
            if(diff==0){
            g2D.drawString(Diff[diff], 180,400);
        }
        else if(diff==1){
            g2D.drawString(Diff[diff], 160,400);
        }
        else if(diff==2){
            g2D.drawString(Diff[diff], 180,400);
        }
        
       
        
       }
       else if (LevelFinished){
           if(LEVEL_COOFICIENT==2){
               this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/level_1.png")).getImage();
               g2D.drawImage(this.image,0 ,0, null);
           }
           else if(LEVEL_COOFICIENT==3){
               this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/level_2.png")).getImage();
               g2D.drawImage(this.image,0 ,0, null);
           }
           else if(LEVEL_COOFICIENT==4){
               this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/kazandınknk.png")).getImage();
               g2D.drawImage(this.image,0 ,0, null);
           }
           
       }
       else if(YouAreDead){
           this.image = new ImageIcon(getClass().getResource("/Chicken/game/asset/images/youdied_bg.png")).getImage();
           g2D.drawImage(this.image,0 ,0, null);
           
       }
       else{
       g2D.setColor(Color.red);
        background.draw(g2D);
        tank.draw(g2D);
        
        for(Cat cat: cats){
            cat.draw(g2D);
        }
        for(Target target: targets){
            target.draw(g2D);
        }
        
        if(eggs != null){
            for(Egg egg: eggs){
            egg.draw(g2D);
    }
        }
        g2D.setColor(Color.red);
        g2D.setFont(new Font("default", Font.BOLD, 15));
        g2D.drawString("Score: "+ score+"/" +Top_Score[LEVEL_COOFICIENT-1], GAME__WIDTH-110,45); 
        g2D.setColor(Color.white);
        g2D.drawString("Level: "+LEVEL_COOFICIENT,GAME__WIDTH/2, 45);
        g2D.drawImage(lifeImage,  30,30,null);
        for(int i=0;i<life;i++){
            g2D.drawImage(heartImage,85+25*i,30,null);
        }
       }
       }

    @Override
    public void run() {
        init();
        while(isRunning){
            
            long startTime = System.currentTimeMillis();
            
            updateGame();
            renderGame();
            
            long endTime = System.currentTimeMillis() - startTime;
            long waitTime = (MILLISECOND / FPS) - endTime / MILLISECOND;
            
            try{
                Thread.sleep(waitTime);
            }catch (Exception e){
           
            }
        }
    }

    private void init() {
        isRunning = true;
    }

    private void updateGame() {
        
        if(cats.size()<3+LEVEL_COOFICIENT+diff && isrunning2){
            cats.add(new Cat(ThreadLocalRandom.current().nextInt(40,GAME__WIDTH -112),GAME__HEIGHT, 
                    (int)(ThreadLocalRandom.current().nextInt(3,AUTO_SPEED-5)*LEVEL_COOFICIENT*0.3 *(diff+1) )));
            
        }
        
        if(targets.size()<3 && isrunning2){
            int coor = ThreadLocalRandom.current().nextInt(0,GAME__WIDTH-50 );
            while(!(coor <= 0 || coor >= GAME__WIDTH-51)) {
                coor = ThreadLocalRandom.current().nextInt(0,GAME__WIDTH-50 );
            }
            targets.add(new Target(coor,GAME__HEIGHT,
                    (int)(ThreadLocalRandom.current().nextInt(3,AUTO_SPEED-5)*(LEVEL_COOFICIENT)*0.6),
                    ThreadLocalRandom.current().nextInt(1,3)));
            
        }
        for(int i=0; i<targets.size();i++){
            Target target = targets.get(i);
            target.update();
            
         
           for(int j=0; j<eggs.size();j++){
            Egg egg = eggs.get(j);
            egg.update();
            
                if(target.getBound().intersects(egg.getBound())){
                    if(score<Top_Score[LEVEL_COOFICIENT-1]){
                        score+=25*target.getSize();
                        targets.remove(target);
                        eggs.remove(egg);
                    }
                    if(score>=Top_Score[LEVEL_COOFICIENT-1] && isrunning2){
                        isrunning2 = false;
                        LevelFinished = true;
                        Level_Finished();
                        tank.setX(GAME__WIDTH/2 - 60);
                        tank.setY(GAME__HEIGHT -550);
                        LEVEL_COOFICIENT++;
                        score=0;
                        life=3;
                    }
                }
            
                if(egg.getX() < -50 || egg.getX() > GAME__WIDTH) {
                    eggs.remove(egg);
            }
        } 
            
           
            if(target.getY()<-50) {
                targets.remove(target);
            }
        }
        
        for(int i=0; i<cats.size();i++){
            Cat cat = cats.get(i);
            cat.update();
            
            if(cat.getBound().intersects(tank.getBound()) && isrunning2){   
                if(life>1 && !LevelFinished){
                    life--;
                    cats.remove(cat);
                }
                
                else{
                    life--;
                    YouAreDead = true;
                    isrunning2 = false;
                }
            }
            if(cat.getY()<-50) {
                cats.remove(cat);
            }
        }
        
        
        
        
        
    }

    private void renderGame() {

       repaint();
    }

     private void Level_Finished() {
        for(int i=0; i<cats.size();i++){
        Cat cat = cats.get(i);
        cats.remove(cat);
        }
        
        for(int i=0; i<targets.size();i++){
        Target target = targets.get(i);
        targets.remove(target);
        }
        
        for(int i=0; i<eggs.size();i++){
        Egg egg = eggs.get(i);
        eggs.remove(egg);
        }
    }
    
}
