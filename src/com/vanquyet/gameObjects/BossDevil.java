package com.vanquyet.gameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Hashtable;

import com.vanquyet.effect.Animation;
import com.vanquyet.effect.CacheDataLoader;

public class BossDevil extends Human {
	
	//Const in game
	private static final long SECOND = 100000000;
	private static final int DAMAGE = 20;
	private static final int RUNSPEED = 10;
	private static final int SHOOTSPEED = 10;
	private static final float WIDTH = 110;
	private static final float HEIGHT = 150;
	private static final int BLOOD = 150;
	private static final float MASS = 0.1f;
	private static final float JUMPSPEED = 5.0f;
	private static final long TIMENOBEHURT = 5 * SECOND;
	private static final int NONE = 2000;
	private static final int SHOOTING = 500;
	private static final int SLIDE = 4000;

	//Animation
	private Animation idleforward, idleback;
        private Animation shootingforward, shootingback;
        private Animation slideforward, slideback;
     
        private String[] attackType = new String[4];
        private int attackIndex = 0;
        private long lastAttackTime;
        private long startTimeForAttacked;
    
    private Hashtable<String, Long> timeAttack = new Hashtable<String, Long>();;
	
	public BossDevil(float x, float y, GameWorld gameWorld) {
		super(x, y, WIDTH, HEIGHT, MASS, BLOOD, gameWorld);
		
		//Load Animation
		idleback = CacheDataLoader.getInstance().getAnimation("boss_idle");
                idleforward = CacheDataLoader.getInstance().getAnimation("boss_idle");
                idleforward.flipAllImage();

                shootingback = CacheDataLoader.getInstance().getAnimation("boss_shooting");
                shootingforward = CacheDataLoader.getInstance().getAnimation("boss_shooting");
                shootingforward.flipAllImage();

                slideback = CacheDataLoader.getInstance().getAnimation("boss_slide");
                slideforward = CacheDataLoader.getInstance().getAnimation("boss_slide");
                slideforward.flipAllImage();

                attackType[0] = "NONE";
                attackType[1] = "shooting";
                attackType[2] = "NONE";
                attackType[3] = "slide";
        
		timeAttack.put("NONE", new Long(NONE));
                timeAttack.put("shooting", new Long(SHOOTING));
                timeAttack.put("slide", new Long(SLIDE));

                setDamage(DAMAGE);
                setTimeForNoBeHurt(TIMENOBEHURT);
        
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jump() {
		setSpeedY(-JUMPSPEED);
	}

	@Override
	public void dick() {
		// TODO Auto-generated method stub
		
	}

	@Override	
	public void standUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRun() {
		// TODO Auto-generated method stub
		
	}

	 @Override
	    public void attack() {
	    
	        // only switch state attack
	        
	        if(System.currentTimeMillis() - lastAttackTime > timeAttack.get(attackType[attackIndex])){
	            lastAttackTime = System.currentTimeMillis();
	            attackIndex ++;
	            if(attackIndex >= attackType.length) attackIndex = 0;
	            if(attackType[attackIndex].equals("slide")){
	                if(getPosX() < getGameWorld().getMegaman().getPosX()) setSpeedX(RUNSPEED);
	                else setSpeedX(-RUNSPEED);
	            }
	            
	        }
	    
	    }
	 
            
	 @Override
	 public void update() {
		super.update();
		 
		if(getGameWorld().getMegaman().getPosX() > getPosX()){
                        setDirection(RIGHT_DIR);
                }else {
                    setDirection(LEFT_DIR);
                }
	        
	        if(startTimeForAttacked == 0){
                    startTimeForAttacked = System.currentTimeMillis();
                }else if(System.currentTimeMillis() - startTimeForAttacked > 300){
	            attack();
	            startTimeForAttacked = System.currentTimeMillis();
	        }
	        
	        if(!attackType[attackIndex].equals("NONE")){
	            if(attackType[attackIndex].equals("shooting")){
	                
	                Bullet bullet = new BossBullet(getPosX(), getPosY() - 50, getGameWorld());
	                if(getDirection() == LEFT_DIR) {
                            bullet.setSpeedX(-SHOOTSPEED);
                        }
	                else {
                            bullet.setSpeedX(SHOOTSPEED);
                        }
	                bullet.setTeamType(getTeamType());
	                getGameWorld().getBulletManager().addObject(bullet);
	                
	            }else if(attackType[attackIndex].equals("slide")){
	                
	                if(getGameWorld().getPhysicalmap().haveCollisionWithLeftWall(getBoundForCollisionWithMap())!=null)
	                    setSpeedX(RUNSPEED);
	                if(getGameWorld().getPhysicalmap().haveCollisionWithRightWall(getBoundForCollisionWithMap())!=null)
	                    setSpeedX(-RUNSPEED);
	                setPosX(getPosX() + getSpeedX());
	            }
	        }else{
	            // stop attack
	            setSpeedX(0);
	        }
	 }

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		 if(attackType[attackIndex].equals("slide")){
	            Rectangle rect = getBoundForCollisionWithMap();
	            rect.y += 100;
	            rect.height -= 100;
	            return rect;
	        }else
	            return getBoundForCollisionWithMap();

	}

	@Override
	public void draw(Graphics2D g2) {
		if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1)
        {
            System.out.println("Boss Plash...");
        }else{
            
            if(attackType[attackIndex].equals("NONE")){
                if(getDirection() == RIGHT_DIR){
                    idleforward.Update(System.nanoTime());
                    idleforward.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                }else{
                    idleback.Update(System.nanoTime());
                    idleback.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                }
            }else if(attackType[attackIndex].equals("shooting")){
                
                if(getDirection() == RIGHT_DIR){
                    shootingforward.Update(System.nanoTime());
                    shootingforward.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                }else{
                    shootingback.Update(System.nanoTime());
                    shootingback.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                }
                
            }else if(attackType[attackIndex].equals("slide")){
                if(getSpeedX() > 0){
                    slideforward.Update(System.nanoTime());
                    slideforward.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY() + 50, g2);
                }else{
                    slideback.Update(System.nanoTime());
                    slideback.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY() + 50, g2);
                }
            }
        }
		
	}

	@Override
	public void hurtingCallBack() {
		// TODO Auto-generated method stub
		
	}

}
