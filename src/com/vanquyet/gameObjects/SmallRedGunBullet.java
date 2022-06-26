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
public class SmallRedGunBullet extends Bullet{
    
    private Animation forwardBulletAnim, backBulletAnim;
    public static final int DAMAGE = 15;
    public static final float WIDTH = 30;
    public static final float HEIGHT = 30;
    public static final float MASS = 1.0f;
    
    public SmallRedGunBullet(float x, float y,GameWorld gameWorld) {
        super(x, y, WIDTH, HEIGHT, MASS, DAMAGE, gameWorld);
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("yellow_flower_bullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("yellow_flower_bullet");
        backBulletAnim.flipAllImage();
    }

    @Override
    public void draw(Graphics2D g2) {
            // TODO Auto-generated method stub
        if(getSpeedX() > 0){          
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void attack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void hurtingCallBack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
