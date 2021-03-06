/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanquyet.gameObjects;

import com.vanquyet.effect.Animation;
import com.vanquyet.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author LAPTOP
 */
public class SmallRedGun extends ParticularObject{
    
    public static final float WIDTH = 127;
    public static final float HEIGHT = 89;
    public static final int BLOOD = 100;
    public static final float MASS = 0;
    public static final int DAMAGE = 10;
    
    public static final int SPEED_ATTACK = 3;
    
    private Animation forwardAnim, backAnim;
    private long startTimeToShoot;
    
    public SmallRedGun(float x, float y, GameWorld gameWorld) {
        super(y, y, WIDTH, HEIGHT, MASS, BLOOD, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("smallredgun");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("smallredgun");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        this.setTimeForNoBeHurt(300000000);
    }

    @Override
    public void attack() {
    
        Bullet bullet = new SmallRedGunBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(-SPEED_ATTACK);
        bullet.setSpeedY(SPEED_ATTACK);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);
        
        bullet = new SmallRedGunBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(SPEED_ATTACK);
        bullet.setSpeedY(SPEED_ATTACK);
        bullet.setTeamType(getTeamType());
         getGameWorld().bulletManager.addObject(bullet);
    }

    public void update(){
        super.update();
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*2.0){
            attack();
            startTimeToShoot = System.nanoTime();
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
    public void draw(Graphics2D g2) {
        if(!isObjectOutOfCameraView()){
            if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
                if(getDirection() == LEFT_DIR){
                    backAnim.Update(System.nanoTime());
                    backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }else{
                    forwardAnim.Update(System.nanoTime());
                    forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void hurtingCallBack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
