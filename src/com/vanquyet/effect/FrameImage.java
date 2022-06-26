/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanquyet.effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Nguyễn Văn Quyết
 */
public class FrameImage {

    private String name;
    private BufferedImage bufferedImage;

    //Constructor 
    public FrameImage(String name, BufferedImage bufferedImage) {
        this.name = name;
        this.bufferedImage = bufferedImage;
    }

    public FrameImage() {
        this.name = null;
        this.bufferedImage = null;
    }

    //Constructor copy
    public FrameImage(FrameImage frameImage) {
        bufferedImage = new BufferedImage(frameImage.getWidthImage(), frameImage.getHeightImage(), frameImage.bufferedImage.getType());
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(frameImage.getBufferedImage(), 0, 0, null);
        name = frameImage.getName();
    }


    //Draw
    public void draw(int x, int y, Graphics2D g2) {
        g2.drawImage(bufferedImage, x - bufferedImage.getWidth() / 2, y - bufferedImage.getHeight() / 2, null);
    }

    // Get Width and Height
    public int getWidthImage() {
        return this.bufferedImage.getWidth();
    }

    public int getHeightImage() {
        return this.bufferedImage.getHeight();
    }
    
    //Getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
