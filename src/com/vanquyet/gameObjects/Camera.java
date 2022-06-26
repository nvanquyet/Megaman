package com.vanquyet.gameObjects;


public class Camera extends GameObject {
	
	private float widthView;
	private float heightView;
	private boolean islock = false;
	
	
	public Camera(float x, float y, float widthView, float heightView, GameWorld gameWorld) {
		super(x, y, gameWorld);
		this.widthView = widthView;
		this.heightView = heightView;
	}
	
	public float getWidthView() {
		return widthView;
	}

	public void setWidthView(float widthView) {
		this.widthView = widthView;
	}

	public float getHeightView() {
		return heightView;
	}

	public void setHeightView(float heightView) {
		this.heightView = heightView;
	}

	public boolean isIslock() {
		return islock;
	}

	public void setIslock(boolean islock) { 
		this.islock = islock;
	}

	@Override
	public void update() {
		
		if(!islock) {
			
			Megaman megaman = getGameWorld().getMegaman();
			
			if(megaman.getPosX() - getPosX() > 400) {
				setPosX(megaman.getPosX() - 400);
			}
			else if(megaman.getPosX() - getPosX() < 200) {
				setPosX(megaman.getPosX() - 200);
			}
			
			if(megaman.getPosY() - getPosY() > 400) {
				setPosY(megaman.getPosY() - 400);
			}
			else if(megaman.getPosY() - getPosY() < 250) {
				setPosY(megaman.getPosY() - 250);
			}
			
		}
	}
	
	
	public void lock() {
		islock = true;
	}

	
	public void unlock() {
		islock = false;
	}
	
}
