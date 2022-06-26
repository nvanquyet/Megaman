package com.vanquyet.gameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.vanquyet.effect.Animation;
import com.vanquyet.effect.CacheDataLoader;

public class BlueFire extends Bullet {
	
	private Animation forwardBulletAnim, backBulletAnim;
	public static final float WIDTH = 40;
	public static final float HEIGHT = 30;
	public static final float MASS = 1.0f;
	public static final int DAMAGE = 50;
	
	
	public BlueFire(float x,float y,GameWorld gameWorld) {
		super(x, y, WIDTH, HEIGHT, MASS, DAMAGE, gameWorld);
		forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnim.flipAllImage();
	}
	
	@Override
	public void update() {
		super.update();
		if(forwardBulletAnim.getIgnoreFrame(0) || backBulletAnim.getIgnoreFrame(0))
            setPosX(getPosX() + getSpeedX());
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
		if(getSpeedX() > 0){
			if(!forwardBulletAnim.getIgnoreFrame(0) && forwardBulletAnim.getCurrentFrame() == 3) {
				forwardBulletAnim.setIgnoreFrame(0);
				forwardBulletAnim.setIgnoreFrame(1);
				forwardBulletAnim.setIgnoreFrame(2);
			}
			forwardBulletAnim.Update(System.nanoTime());
			forwardBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			
		} 
		else
		{
			if(!backBulletAnim.getIgnoreFrame(0) && backBulletAnim.getCurrentFrame() == 3){
                backBulletAnim.setIgnoreFrame(0);
                backBulletAnim.setIgnoreFrame(1);
                backBulletAnim.setIgnoreFrame(2);
            }
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		}
		
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		// TODO Auto-generated method stub
		return getBoundForCollisionWithMap();
	}

	@Override
	public void hurtingCallBack() {
		// TODO Auto-generated method stub
		
	}

}
