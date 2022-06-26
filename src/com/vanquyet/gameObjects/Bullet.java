package com.vanquyet.gameObjects;

import java.awt.Graphics2D;


public abstract class Bullet extends ParticularObject {
	private static final int BLOOD = 10;

	public Bullet(float x,float y,float width,float height, float mass, int damage, GameWorld gameWorld) {
		super(x, y, width, height, mass, BLOOD, gameWorld);
		setDamage(damage);
	}
	
	public abstract void draw(Graphics2D g2);
	
	public void update() {
		super.update();
		setPosX(getPosX() + getSpeedX());
		setPosY(getPosY() + getSpeedY());
		ParticularObject object = getGameWorld().getParticularObjectManager().getCollectionWithEnemyObject(this);
		if(object != null && object.getState() == ALIVE && object.getTeamType() != this.getTeamType()) {
			if(this.getDamage() != 0) {
                            System.out.println(getDamage());
                            object.beHurt(getDamage());
                            setBlood(0);
			}
		}
	}

}
