/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanquyet.gameObjects;

import java.awt.Rectangle;



/**
 *
 * @author LAPTOP
 */
public abstract class Human extends ParticularObject{
    private boolean isJumping;
    private boolean isDicking;
    private boolean isLanding;
    
    
    public Human(float x,float y,float width,float height,float mass,int blood,GameWorld gameWorld){
        super(x, y, width, height, mass, blood, gameWorld);
        setState(ALIVE);
        this.isJumping = false;
        this.isDicking = false;
        this.isLanding = true;
    }
    
    public abstract void run();
    
    public abstract void jump();
    
    public abstract void dick();
    
    public abstract void standUp();
    
    public abstract void stopRun();

    public boolean getIsJumping() {
        return isJumping;
    }
    
    @Override
    public void update(){
        super.update();
        if(getState() == ALIVE || getState() == NOBEHURT){
            
            if(!isLanding){ 
                setPosX(getPosX() + getSpeedX()); 
                
                if(getDirection() == LEFT_DIR && 
                        getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null)
                {
                    Rectangle rectLeftWall = getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);             
                }
                if(getDirection() == RIGHT_DIR && 
                        getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null)
                {
                    Rectangle rectRightWall = getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap());
                    setPosX(rectRightWall.x - getWidth() / 2);
                    
                }
                
                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
                boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY():2);  
                Rectangle rectLand = getGameWorld().getPhysicalMap().haveCollisionWithLand(boundForCollisionWithMapFuture);
                Rectangle rectTop = getGameWorld().getPhysicalMap().haveCollisionWithTop(boundForCollisionWithMapFuture);
                
                if(rectTop != null){
                    setSpeedY(0);
                    setPosY(rectTop.y + getGameWorld().getPhysicalMap().getTitleSize() + getHeight() /2);
                } else if (rectLand != null){
                    setIsJumping(false);
                    if(getSpeedY() > 0){
                        setIsLanding(true);
                    }
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight() / 2 - 1);
                    
                } else {
                    setIsJumping(true);
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY());
                }
            }
        }
        
    }
    
    
    
    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean getIsDicking() {
        return isDicking;
    }

    public void setIsDicking(boolean isDicking) {
        this.isDicking = isDicking;
    }

    public boolean getIsLanding() {
        return isLanding;
    }
    
    public void setIsLanding(boolean isLanding) {
        this.isLanding = isLanding;
    }
    
}
