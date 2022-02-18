/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChickenGame.game;

import static ChickenGame.game.Helper.GAME__HEIGHT;
import static ChickenGame.game.Helper.GAME__WIDTH;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
public class Menu extends Canvas implements Runnable {
    
    public static boolean isRunningMenu=true;
    public Menu(){
        setPreferredSize(new Dimension(GAME__WIDTH,GAME__HEIGHT));
    }
    @Override
    protected void onKeyUp(KeyEvent event) {
        
    }

    @Override
    protected void onKeyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_SPACE){
            isRunningMenu=false;
        }else if(event.getKeyCode() == KeyEvent.VK_LEFT){
            if(diff>0) {
                diff--;
            } 
        }else if(event.getKeyCode() == KeyEvent.VK_RIGHT){
            if(diff<2) {
                diff++;
            } 
        }else{
            
        }
       
    }

    @Override
    protected void onDraw(Graphics2D g2D) {
        g2D.setColor(Color.BLACK);
        g2D.drawString("CHOOSE LEVEL DIFFICULTY ", 30,30);
        
        g2D.setColor(Color.BLACK);
        g2D.drawString(Diff[diff], 30,130);
    }

    @Override
    public void run() {
        init();
        while(isRunningMenu) {
        
        long startTime = System.currentTimeMillis();
            
            renderGame();
            
            long endTime = System.currentTimeMillis() - startTime;
            long waitTime = (MILLISECOND / FPS) - endTime / MILLISECOND;
            
            try{
                Thread.sleep(waitTime);
            }catch (Exception e){
           
            }
        }
    }
    
    private void renderGame() {
         repaint();
         System.err.print(Diff[diff]);
         System.err.println("isrunnuninMenu == xd");
    }
    
    private void init() {
        isRunningMenu = true;
    }
}
