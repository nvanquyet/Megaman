package com.vanquyet.gameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.vanquyet.effect.Animation;
import com.vanquyet.effect.CacheDataLoader;

public class EnemyBullet extends Bullet {

	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private static final float MASS = 1.0f;
	private static final int DAMAGE = 10;
	
	
	private Animation forwardBulletAnim , backBulletAnim;
	
	
	public EnemyBullet(float x, float y, GameWorld gameWorld) {
		super(x, y, WIDTH, HEIGHT, MASS, DAMAGE, gameWorld);
		forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("redeyebullet");
		backBulletAnim = CacheDataLoader.getInstance().getAnimation("redeyebullet");
		backBulletAnim.flipAllImage();
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
	public void draw(Graphics2D g2) {
		 if(getSpeedX() > 0){          
	            forwardBulletAnim.Update(System.nanoTime());
	            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
	        }else{
	            backBulletAnim.Update(System.nanoTime());
	            backBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
	        }
	}
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void hurtingCallBack() {
		// TODO Auto-generated method stub
		
	}
 
}
