package com.vanquyet.Frame;

import java.awt.event.KeyEvent;
import com.vanquyet.gameObjects.GameWorld;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LAPTOP
 */
public class InputManager{

	private GameWorld gameWorld;
	
	public InputManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		
	}

	public void processKeyPress(int code) {
		switch(code){
	      case KeyEvent.VK_DOWN:
	      case KeyEvent.VK_S:
	      {
	    	  this.gameWorld.getMegaman().jumpAndPessDown();
	      	break;
	      }
	          
	      case KeyEvent.VK_RIGHT:
	      case KeyEvent.VK_D:
	      {
	    	  this.gameWorld.getMegaman().setDirection(this.gameWorld.getMegaman().RIGHT_DIR);
	    	  this.gameWorld.getMegaman().run();
	          break;
	      }
	      case KeyEvent.VK_LEFT:
	      case KeyEvent.VK_A:
	      {
	    	  this.gameWorld.getMegaman().setDirection(this.gameWorld.getMegaman().LEFT_DIR);
	    	  this.gameWorld.getMegaman().run();
	          break;
	      }
	          
	          
	      case KeyEvent.VK_ENTER:
	      {
	      	if(this.gameWorld.state == GameWorld.INIT_GAME){
	              if(this.gameWorld.previousState == GameWorld.GAMEPLAY)
	            	  this.gameWorld.switchState(GameWorld.GAMEPLAY);
	              else this.gameWorld.switchState(GameWorld.TUTORIAL);
	              
	              this.gameWorld.bgMusic.loop();
	          }
	          if(this.gameWorld.state == GameWorld.TUTORIAL && this.gameWorld.storyTutorial >= 1){
	              if(this.gameWorld.storyTutorial<=3){
	            	  this.gameWorld.storyTutorial ++;
	            	  this.gameWorld.currentSize = 1;
	            	  this.gameWorld.textTutorial = this.gameWorld.texts1[this.gameWorld.storyTutorial-1];
	              }else{
	            	  this.gameWorld.switchState(GameWorld.GAMEPLAY);
	              }
	              
	              // for meeting boss tutorial
	              if(this.gameWorld.tutorialState == GameWorld.MEETFINALBOSS){
	            	  this.gameWorld.switchState(GameWorld.GAMEPLAY);
	              }
	          }
	          break;
	      }

	      case KeyEvent.VK_UP:
	      case KeyEvent.VK_W:
	      {
	    	  this.gameWorld.getMegaman().jump();
	          break;
	      }
	          
	          
	      case KeyEvent.VK_SPACE:
	      {
	    	  this.gameWorld.getMegaman().attack();
	          break;
	      }
	          
	  }
		
	}

	
	public void processKeyRelease(int keyCode){
	       switch (keyCode) {
	        case KeyEvent.VK_DOWN:
	        case KeyEvent.VK_S:
	        {
	        	this.gameWorld.getMegaman().jumpAndReleaseDown();
	        	break;
	        }
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
            {
                if(this.gameWorld.getMegaman().getSpeedX() > 0)
                	this.gameWorld.getMegaman().stopRun();
                break;
            }
                
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            {
            	if(this.gameWorld.getMegaman().getSpeedX() < 0)
            		this.gameWorld.getMegaman().stopRun();
                break;
            }
                
                
            case KeyEvent.VK_ENTER:
                if(this.gameWorld.state == this.gameWorld.GAMEOVER || this.gameWorld.state == this.gameWorld.GAMEWIN) {
                } else if(this.gameWorld.state == this.gameWorld.PAUSEGAME) {
                	this.gameWorld.state = this.gameWorld.lastState;
                }
                break;
            case KeyEvent.VK_ESCAPE:
            	this.gameWorld.lastState = this.gameWorld.state;
            	this.gameWorld.state = this.gameWorld.PAUSEGAME;
                break;
                 
        }
	}
    
	
    
}

