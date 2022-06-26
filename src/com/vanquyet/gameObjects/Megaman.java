/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanquyet.gameObjects;


import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.vanquyet.effect.Animation;
import com.vanquyet.effect.CacheDataLoader;

/**
 *
 * @author Nguyễn Văn Quyết
 */
public class Megaman extends Human {
	
	private static final int RUNSPEED = 4;
	private static final float JUMPSPEED = 5.0f;
	private static final int SHOOTSPEED = 14;
	private static final float WIDTH = 70;
	private static final float HEIGHT = 90;
	private static final float MASS = 0.125f;
	private static final int BLOOD = 100;
	private static final long TIMENOSHOOT = 500*10000000;
	
	
    private Animation runForwardAnim, runBackAnim, runShootingForwarAnim, runShootingBackAnim;
    private Animation idleForwardAnim, idleBackAnim, idleShootingForwardAnim, idleShootingBackAnim;
    private Animation dickForwardAnim, dickBackAnim;
    private Animation flyForwardAnim, flyBackAnim, flyShootingForwardAnim, flyShootingBackAnim;
    private Animation landingForwardAnim, landingBackAnim;
    
    private Animation climWallForward, climWallBack;
    
    private long lastShootingTime;
    private boolean isShooting = false;
    
    private AudioClip hurtingSound;
    private AudioClip shooting1;
    
    public Megaman(float x, float y, GameWorld gameWorld) {
        super(x, y, WIDTH, HEIGHT, MASS, BLOOD, gameWorld);
        
        shooting1 = CacheDataLoader.getInstance().getAudio("bluefireshooting");
        hurtingSound = CacheDataLoader.getInstance().getAudio("megamanhurt");
        
        setTeamType(LEAGUE_TEAM);
        setTimeForNoBeHurt(2000*1000000);
        
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim.flipAllImage();   
        
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim.flipAllImage();
        
        dickForwardAnim = CacheDataLoader.getInstance().getAnimation("dick");
        dickBackAnim = CacheDataLoader.getInstance().getAnimation("dick");
        dickBackAnim.flipAllImage();
        
        flyForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
        flyForwardAnim.setIsRepeated(false);
        flyBackAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
        flyBackAnim.setIsRepeated(false);
        flyBackAnim.flipAllImage();
        
        landingForwardAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim.flipAllImage();
        
        climWallBack = CacheDataLoader.getInstance().getAnimation("clim_wall");
        climWallForward = CacheDataLoader.getInstance().getAnimation("clim_wall");
        climWallForward.flipAllImage();
        
        setBehurtForwarAnim(CacheDataLoader.getInstance().getAnimation("hurting"));
        behurtBackAnim = CacheDataLoader.getInstance().getAnimation("hurting");
        behurtBackAnim.flipAllImage();
        
        idleShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim.flipAllImage();
        
        runShootingForwarAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim.flipAllImage();
        
        flyShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim.flipAllImage();
        
    }

    
    

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        // TODO Auto-generated method stub
        Rectangle rect = getBoundForCollisionWithMap();
        
        if(getIsDicking()){
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 20;
            rect.width = 44;
            rect.height = 65;
        }else{
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 40;
            rect.width = 44;
            rect.height = 80;
        }
        
        return rect;
    }

   
    @Override
    public void run() {
    	if(!getIsDicking()) {
    		if(getDirection() == LEFT_DIR)
                setSpeedX(-RUNSPEED);
            else setSpeedX(RUNSPEED);
    	}
        
    }

    @Override
    public void jump() {
        if( getIsJumping() == false ){
        	setIsJumping(true);
        	setSpeedY(-JUMPSPEED);
            flyBackAnim.reset();
            flyForwardAnim.reset();
        }
        // for clim wall
        else{
            Rectangle rectRightWall = getBoundForCollisionWithMap();
            rectRightWall.x += 1;
            Rectangle rectLeftWall = getBoundForCollisionWithMap();
            rectLeftWall.x -= 1;
            
            if(getGameWorld().getPhysicalmap().haveCollisionWithRightWall(rectRightWall)!=null && getSpeedX() > 0){
                setSpeedY(-JUMPSPEED);
                //setSpeedX(-1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                //setDirection(LEFT_DIR);
            }else if(getGameWorld().getPhysicalmap().haveCollisionWithLeftWall(rectLeftWall)!=null && getSpeedX() < 0){
                setSpeedY(-JUMPSPEED);
                //setSpeedX(1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                //setDirection(RIGHT_DIR);
            }
                
        }
    }

    @Override
    public void dick() {
        if(!getIsJumping()) {
        	setIsDicking(true);
        }
    }
    
    
    public void jumpAndPessDown() {
    	if(getIsJumping()) {
    		setMass(MASS * 2);
    	}
    }
    
    public void jumpAndReleaseDown() {
    	setMass(MASS);
    	this.standUp();
    	
    }
    
    @Override
    public void standUp() {
    	setMass(MASS);
		setIsDicking(false);
        idleForwardAnim.reset();
        idleBackAnim.reset();
        dickForwardAnim.reset();
        dickBackAnim.reset();
    }

    @Override
    public void stopRun() {
        setSpeedX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.setUnIgnoreFrame(0);
        runBackAnim.setUnIgnoreFrame(0);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void attack() {
        if(!isShooting && !getIsDicking()){
            
            shooting1.play();
        	
            Bullet bullet = new BlueFire(getPosX(), getPosY(), getGameWorld());
            if(getDirection() == LEFT_DIR) {
                bullet.setSpeedX(-SHOOTSPEED);
                bullet.setPosX(bullet.getPosX() - 40);
                if(getSpeedX() != 0 && getSpeedY() == 0){
                    bullet.setPosX(bullet.getPosX() - 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }else {
                bullet.setSpeedX(SHOOTSPEED);
                bullet.setPosX(bullet.getPosX() + 40);
                if(getSpeedX() != 0 && getSpeedY() == 0){
                    bullet.setPosX(bullet.getPosX() + 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }
            if(getIsJumping())
                bullet.setPosY(bullet.getPosY() - 20);
            
            bullet.setTeamType(getTeamType());
            getGameWorld().getBulletManager().addObject(bullet);
            lastShootingTime = System.nanoTime();  
            isShooting = true;
        }
    
    }
    @Override
    public void draw(Graphics2D g2) {
        
        switch(getState()){
        
            case ALIVE:
            case NOBEHURT:
                if(getState() == NOBEHURT && (System.nanoTime()/100000000)%2!=1)
                {
//                    System.out.println("Plash...");
                }else{
                    
                    if(getIsLanding()){

                        if(getDirection() == RIGHT_DIR){
                            landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
                             landingForwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height/2 - landingForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }else{
                            landingBackAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height/2 - landingBackAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }

                    }else if(getIsJumping()){
                        if(getDirection() == RIGHT_DIR){
                            flyForwardAnim.Update(System.nanoTime());
                            if(isShooting){
                                flyShootingForwardAnim.setCurrentFrame(flyForwardAnim.getCurrentFrame());
                                flyShootingForwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()) + 10,
                                		(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                            }else {
                            	flyForwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            			(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                            }  
                        }else {
                            flyBackAnim.Update(System.nanoTime());
                            if(isShooting){
                                flyShootingBackAnim.setCurrentFrame(flyBackAnim.getCurrentFrame());
                                flyShootingBackAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()) - 10,
                                		(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                            }else {
                            	flyBackAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            			(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                            }
                        }

                    }else if(getIsDicking()){

                        if(getDirection() == RIGHT_DIR){
                            dickForwardAnim.Update(System.nanoTime());
                            dickForwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height/2 - dickForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }else{
                            dickBackAnim.Update(System.nanoTime());
                            dickBackAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height/2 - dickBackAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }

                    }else{
                        if(getSpeedX() > 0){
                            runForwardAnim.Update(System.nanoTime());
                            if(isShooting){
                                runShootingForwarAnim.setCurrentFrame(runForwardAnim.getCurrentFrame() - 1);
                                runShootingForwarAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                            }else
                                runForwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                            if(runForwardAnim.getCurrentFrame() == 1) runForwardAnim.setIgnoreFrame(0);
                        }else if(getSpeedX() < 0){
                            runBackAnim.Update(System.nanoTime());
                            if(isShooting){
                                runShootingBackAnim.setCurrentFrame(runBackAnim.getCurrentFrame() - 1);
                                runShootingBackAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                            }else
                                runBackAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                            if(runBackAnim.getCurrentFrame() == 1) runBackAnim.setIgnoreFrame(0);
                        }else{
                            if(getDirection() == RIGHT_DIR){
                                if(isShooting){
                                    idleShootingForwardAnim.Update(System.nanoTime());
                                    idleShootingForwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                                }else{
                                    idleForwardAnim.Update(System.nanoTime());
                                    idleForwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                                }
                            }else{
                                if(isShooting){
                                    idleShootingBackAnim.Update(System.nanoTime());
                                    idleShootingBackAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                                }else{
                                    idleBackAnim.Update(System.nanoTime());
                                    idleBackAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                                }
                            }
                        }            
                    }
                }
                
                break;
            
            case BEHURT:
                if(getDirection() == RIGHT_DIR){
                    getBehurtForwarAnim().draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                }else{
                    getBehurtBackAnim().setCurrentFrame(getBehurtForwarAnim().getCurrentFrame());
                    getBehurtBackAnim().draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                }
                break;
             
            case FEY:
                
                break;

        }   
        
        //drawBoundForCollisionWithMap(g2);
        //drawBoundForCollisionWithEnemy(g2);
    }
    @SuppressWarnings("deprecation")
	@Override
	public void hurtingCallBack() {
		// TODO Auto-generated method stub
		System.out.println("Call back hurting");
        hurtingSound.play();
	}
    
    @Override
    public void update() {

        super.update();
        
        if(isShooting){
            if(System.nanoTime() - lastShootingTime > TIMENOSHOOT ){
                isShooting = false;
            }
        }
        
        if(getIsLanding()){
            landingBackAnim.Update(System.nanoTime());
            if(landingBackAnim.getLastFrame()) {
                setIsLanding(false);
                landingBackAnim.reset();
                runForwardAnim.reset();
                runBackAnim.reset();
            }
        }
        
    }

    
    

}
