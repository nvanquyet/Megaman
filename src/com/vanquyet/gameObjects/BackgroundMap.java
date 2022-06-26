package com.vanquyet.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.vanquyet.Frame.GameFrame;
import com.vanquyet.effect.CacheDataLoader;

public class BackgroundMap extends GameObject {

	

	private int [][] map;
	private final int titleSize = 30;
	
	
	public BackgroundMap(float x, float y, GameWorld gameWorld) {
		super(x, y, gameWorld);
		map = CacheDataLoader.getInstance().getBackGroundMap();
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics2D g2) {
		Camera camera = getGameWorld().getCamera();
        g2.setColor(Color.RED);
        for(int i = 0;i< map.length;i++)
            for(int j = 0;j<map[0].length;j++)
                if(map[i][j]!=0 && j*titleSize - camera.getPosX() > -30 && j*titleSize - camera.getPosX() < GameFrame.SCREEN_WIDTH
                        && i*titleSize - camera.getPosY() > -30 && i*titleSize - camera.getPosY() < GameFrame.SCREEN_HEIGHT){ 
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled"+map[i][j]).getBufferedImage(), (int) getPosX() + j*titleSize - (int) camera.getPosX(), 
                        (int) getPosY() + i*titleSize - (int) camera.getPosY(), null);
                }
	}
	public int[][] getMap() {
		return map;
	}
	public void setMap(int[][] map) {
		this.map = map;
	}
  
	
	
}
