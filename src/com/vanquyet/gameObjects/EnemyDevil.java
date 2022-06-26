package com.vanquyet.gameObjects;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.vanquyet.effect.Animation;
import com.vanquyet.effect.CacheDataLoader;

public class EnemyDevil extends ParticularObject {

	private static final int WIDTH = 127;
	private static final int HEIGHT = 89;
	private static final float MASS = 0;
	private static final int BLOOD = 60;
	private static final int SHOOTSPEED = 8;
	private static final long TIMENOSHOOT = 1000*10000000;
	private Animation forwardAnim, backAnim;
	
	private AudioClip shooting;
	
	private long startTimeToShoot = 0;
	
	public EnemyDevil(float x, float y, GameWorld gameWorld) {
		super(x, y, WIDTH, HEIGHT, MASS, BLOOD, gameWorld);
		backAnim = CacheDataLoader.getInstance().getAnimation("redeye");
		forwardAnim = CacheDataLoader.getInstance().getAnimation("redeye");
		forwardAnim.flipAllImage();
		setDamage(10);
		setTimeForNoBeHurt(300*1000000);
		shooting = CacheDataLoader.getInstance().getAudio("redeyeshooting");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void attack() {
		shooting.play();
		Bullet bullet = new EnemyBullet(getPosX(), getPosY(), getGameWorld());
		if(getDirection() == LEFT_DIR) {
			bullet.setSpeedX(-SHOOTSPEED);
		}
		else {
			bullet.setSpeedX(SHOOTSPEED);	 
		}
		bullet.setTeamType(getTeamType());
		getGameWorld().getBulletManager().addObject(bullet);
	}

	

	@Override
	public void draw(Graphics2D g2) {
		if(!isObjectOutOfCameraView()) {
			if(getState() == NOBEHURT && (System.nanoTime() / 100000000) %2 != 1) {
				System.out.println("PLASH");
			}else {
				if(getDirection() == LEFT_DIR) {
					backAnim.Update(System.nanoTime());
					backAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
							(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
				}else{
					forwardAnim.Update(System.nanoTime());
					forwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
							(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
				}
			}
		}
	}
	
	@Override
	public void update() {
		super.update();
		if(System.nanoTime() - startTimeToShoot > TIMENOSHOOT) {
			attack();
			startTimeToShoot = System.nanoTime();
		}if(!this.isObjectOutOfCameraView()) {
			if(getGameWorld().getMegaman().getPosX() > this.getPosX()) {
				this.setDirection(RIGHT_DIR);
			}else if(getGameWorld().getMegaman().getPosX() < this.getPosX()) {
				this.setDirection(LEFT_DIR);
			} else {
				getGameWorld().getMegaman().beHurt(this.getDamage());
			}
		}
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;
        return rect;
	}
	
	@Override
	public void hurtingCallBack() {
		System.out.println("Get damage");
		System.out.println(getBlood());
	}
	
}
