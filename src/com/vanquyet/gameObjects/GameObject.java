package com.vanquyet.gameObjects;



public abstract class GameObject {

    private float posX;
    private float posY;
    private GameWorld gameWorld;

    public GameObject (float x,float y,GameWorld gameWorld) {
        this.posX = x;
        this.posY = y;
        this.gameWorld = gameWorld;
    } 

    public void setPosX(float posX)
    {
        this.posX = posX;
    }

    public float getPosX() {
        return this.posX;
    }
    
    public void setPosY(float posY)
    {
        this.posY = posY;
    }

    public float getPosY() {
        return this.posY;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
    public GameWorld getGameWorld(){
        return this.gameWorld;
    }
    
    public abstract void update();



}