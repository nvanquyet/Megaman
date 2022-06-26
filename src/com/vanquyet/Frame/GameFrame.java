/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanquyet.Frame;

import com.vanquyet.effect.CacheDataLoader;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Nguyễn Văn Quyết
 */
public class GameFrame extends JFrame {

    public static final int SCREEN_HEIGHT = 600;
    public static final int SCREEN_WIDTH = 1000;
    public GamePanel gamePanel;

    public GameFrame() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(((dimension.width - SCREEN_WIDTH) / 2), ((dimension.height - SCREEN_HEIGHT) / 2), SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            CacheDataLoader.getInstance().LoadData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        gamePanel = new GamePanel(); 
        this.add(gamePanel);
        this.addKeyListener(gamePanel);
    }

    public void startGame() {
        gamePanel.startGame();
    }

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);
        gameFrame.startGame();

    }

}
