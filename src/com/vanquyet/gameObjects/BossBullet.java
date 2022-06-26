package com.vanquyet.gameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.vanquyet.effect.Animation;
import com.vanquyet.effect.CacheDataLoader;

public class BossBullet extends Bullet{
	
        private final long second = 100000000;
	private static final float WIDTH = 30;
	private static final float HEIGHT = 30;
	private static final int DAMAGE = 30;
	private static final float MASS = 1.0f;
	
	private Animation forwardBulletAnimUp, forwardBulletAnimDown, forwardBulletAnim;
	private Animation backBulletAnimUp, backBulletAnimDown, backBulletAnim;

	private long startTimeForChangeSpeedY;

	public BossBullet(float x, float y,GameWorld gameWorld) {
	        
	        super(x, y, WIDTH, HEIGHT, MASS, DAMAGE, gameWorld);
	        
	        backBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
	        backBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
	        backBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");
	        
	        forwardBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
	        forwardBulletAnimUp.flipAllImage();
	        forwardBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
	        forwardBulletAnimDown.flipAllImage();
	        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");
	        forwardBulletAnim.flipAllImage();

	    }

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		// TODO Auto-generated method stub
		return getBoundForCollisionWithMap();
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		if (getSpeedX() > 0) {
			if (getSpeedY() > 0) {
				forwardBulletAnimDown.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
						(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			} else if (getSpeedY() < 0) {
				forwardBulletAnimUp.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
						(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			} else
				forwardBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
						(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		} else {
			if (getSpeedY() > 0) {
				backBulletAnimDown.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
						(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			} else if (getSpeedY() < 0) {
				backBulletAnimUp.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
						(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			} else
				backBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
						(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		}
		// drawBoundForCollisionWithEnemy(g2);
	}
        
	private void changeSpeedY() {
		if (System.currentTimeMillis() % 3 == 0) {
			setSpeedY(getSpeedX());
		} else if (System.currentTimeMillis() % 3 == 1) {
			setSpeedY(-getSpeedX());
		} else {
			setSpeedY(0);
		}
	}

	@Override
	    public void update() {
	            // TODO Auto-generated method stub
	        super.update();
	        if(System.nanoTime() - startTimeForChangeSpeedY > 1.5 * this.second){
                    changeSpeedY();
	            startTimeForChangeSpeedY = System.nanoTime();
	            
	        }
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hurtingCallBack() {
		// TODO Auto-generated method stub
		
	}
}
