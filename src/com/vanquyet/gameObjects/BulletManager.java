package com.vanquyet.gameObjects;

import java.awt.Rectangle;
public class BulletManager extends ParticularObjectManager {
	
	public BulletManager(GameWorld gameWorld) {
		super(gameWorld);
	}
	
	@Override
	public void updateObjects() {
		super.updateObjects();
		synchronized (particularObjects) {
			for(int id=0;id< particularObjects.size() ; id++) {
				ParticularObject object = particularObjects.get(id);
				if( object.isObjectOutOfCameraView() || object.getState() == ParticularObject.DEATH ||
                                        this.getGameWorld().getPhysicalmap().haveCollision(object.getBoundForCollisionWithMap())) {
					particularObjects.remove(id);
				}
			}
			
		}
	}

}