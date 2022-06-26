package com.vanquyet.gameObjects;

import com.vanquyet.effect.Animation;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class ParticularObject extends GameObject {
    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;

    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;

    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int FEY = 2;
    public static final int DEATH = 3;
    public static final int NOBEHURT = 4;
    
    private int state = ALIVE;

    private float width;
    private float height;
    private float mass;
    private float speedX;
    private float speedY;
    private int blood;

    private int damage;

    private int direction;

    protected Animation behurtForwarAnim, behurtBackAnim;

    private int teamType;

    private long startTimeNoBeHurt;
    private long timeForNoBeHurt;

    public ParticularObject(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
        super(x, y, gameWorld);
        setWidth(width);
        setHeight(height);
        setMass(mass);
        setBlood(blood);
        this.direction = RIGHT_DIR;
        setState(ALIVE);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Animation getBehurtForwarAnim() {
        return behurtForwarAnim;
    }

    public void setBehurtForwarAnim(Animation behurtForwarAnim) {
        this.behurtForwarAnim = behurtForwarAnim;
    }

    public Animation getBehurtBackAnim() {
        return behurtBackAnim;
    }

    public void setBehurtBackAnim(Animation behurtBackAnim) {
        this.behurtBackAnim = behurtBackAnim;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setTeamType(int teamType) {
        this.teamType = teamType;
    }

    public long getStartTimeNoBeHurt() {
        return startTimeNoBeHurt;
    }

    public void setStartTimeNoBeHurt(long startTimeNoBeHurt) {
        this.startTimeNoBeHurt = startTimeNoBeHurt;
    }

    public long getTimeForNoBeHurt() {
        return timeForNoBeHurt;
    }

    public void setTimeForNoBeHurt(long timeForNoBeHurt) {
        this.timeForNoBeHurt = timeForNoBeHurt;
    }

    public abstract void attack();

    public Rectangle getBoundForCollisionWithMap() {
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth() / 2));
        bound.y = (int) (getPosY() - (getHeight() / 2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }
    
    public void beHurt(int dameEat){
        setBlood(getBlood() - dameEat);
        state = BEHURT;
    }
    
    @Override
    public void update(){
        switch(state){
            case ALIVE: 
            {
            	ParticularObject object = getGameWorld().particularObjectManager.getCollectionWithEnemyObject(this);
                if(object!=null){
                    if(object.getDamage() > 0){
                    	System.out.println(object.getDamage());
                        //System.out.println("eat damage.... from collision with enemy........ "+object.getDamage());
                        beHurt(object.getDamage());
                    }
                     
                }
                break;
            }
            case BEHURT:
            {
                if(behurtBackAnim == null || behurtForwarAnim == null){
                    this.state = NOBEHURT;
                    startTimeNoBeHurt = System.nanoTime();
                    if(getBlood() == 0){
                        this.state = FEY;
                    }
                } else {
                	behurtForwarAnim.Update(System.nanoTime());
                    if(behurtForwarAnim.getLastFrame()){
                        behurtForwarAnim.reset();
                        this.state = NOBEHURT;
                        if(getBlood() == 0){
                            this.state = FEY;
                        }
                        startTimeNoBeHurt = System.nanoTime();
                    }
                }
                break;
            }
            
            case FEY:
            {
                state = DEATH;
                break;
            }
            case NOBEHURT:
            {
                if(System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt){
                    state = ALIVE;
                }
                break;
            }
        }
    }
    
    public boolean isObjectOutOfCameraView(){
        if(getPosX() - getGameWorld().getCamera().getPosX() > getGameWorld().getCamera().getWidthView() ||
                getPosX() - getGameWorld().getCamera().getPosX() < -50
            ||getPosY() - getGameWorld().getCamera().getPosY() > getGameWorld().getCamera().getHeightView()
                    ||getPosY() - getGameWorld().getCamera().getPosY() < -50)
            return true;
        else return false;
    }
    
    	
//    public void drawBoundForCollisionWithMap(Graphics2D g2){
//        Rectangle rect = getBoundForCollisionWithMap();
//        //g2.setColor(Color.blue);
//        g2.drawRect(rect.x - (int) getGameWorld().getCamera().getPosX(), rect.y - (int) getGameWorld().getCamera().getPosY(), rect.width, rect.height);
//    }
//    
//    public void drawBoundForCollisionWithEnemy(Graphics2D g2){
//    	 Rectangle rect = getBoundForCollisionWithEnemy();
//         //g2.setColor(Color.red);
//         g2.drawRect(rect.x - (int) getGameWorld().getCamera().getPosX(), rect.y - (int) getGameWorld().getCamera().getPosY(), rect.width, rect.height);
//    }
//    
    public abstract Rectangle getBoundForCollisionWithEnemy();
    
    public abstract void draw(Graphics2D g2);

    public abstract void hurtingCallBack();

}
