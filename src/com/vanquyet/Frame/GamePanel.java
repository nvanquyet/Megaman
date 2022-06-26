/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanquyet.Frame;



import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import com.vanquyet.gameObjects.GameWorld;

/**
 *
 * @author Nguyễn Văn Quyết
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    
    private GameWorld gameWorld;

    InputManager inputManager;
    
    Thread gameThread;

    public boolean isRunning = true;

    public GamePanel(){

    	gameWorld = new GameWorld();
        inputManager = new InputManager(gameWorld);

    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    int a = 0;
    @Override
    public void run() {

        long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;

        long period = 1000000000/80;

        while(isRunning){

            gameWorld.Update();
            gameWorld.Render();
            repaint();

            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);
            try{

                    if(sleepTime > 0)
                            Thread.sleep(sleepTime/1000000);
                    else Thread.sleep(period/2000000);

            }catch(Exception e){}

            previousTime = System.nanoTime();
        }

    }

    public void paint(Graphics g){

        g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);

    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.inputManager.processKeyPress(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.inputManager.processKeyRelease(e.getKeyCode());
		
	}

   
}
