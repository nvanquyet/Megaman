package com.vanquyet.gameObjects;

import com.vanquyet.effect.CacheDataLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Physicalmap extends GameObject {
    private int[][] physmap;
    private int titleSize;

    public GameWorld gameWorld;

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public Physicalmap(float x, float y,GameWorld gameWorld) {
        super(x,y,gameWorld);
        this.physmap = CacheDataLoader.getInstance().getPhysicalMap();
        this.titleSize = 30;
    }

    public void drawPhysMap(Graphics2D g2D) {
        
        Camera camera = getGameWorld().getCamera();
        
        g2D.setColor(Color.GRAY);
        for(int i = 0;i< physmap.length;i++)
            for(int j = 0;j<physmap[0].length;j++)
                if(physmap[i][j]!=0) g2D.fillRect((int) getPosX() + j*titleSize - (int) camera.getPosX(), 
                        (int) getPosY() + i*titleSize - (int) camera.getPosY(), titleSize, titleSize);
        
    }

    public Rectangle haveCollisionWithLand(Rectangle rect) {
        
        int posX1 = rect.x / titleSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width) / titleSize;
        posX2 += 2;

        int posY1 = (rect.y + rect.height) / titleSize;

        if (posX1 < 0) {
            posX1 = 0;
        }

        if (posX2 >= physmap[0].length) {
            posX2 = physmap[0].length - 1;
        }

        for (int y = posY1; y < physmap.length; y++) {
            for (int x = posX1; x <= posX2; x++) {
                if (physmap[y][x] == 1) {
                    Rectangle r = new Rectangle((int) (getPosX() + x * titleSize), (int) (getPosY() + y * titleSize), titleSize, titleSize);
                    if (rect.intersects(r)) {
                        return r;
                    }

                }
            }
        }
        return null;
    }

    
    public Rectangle haveCollisionWithTop(Rectangle rect){
        
        int posX1 = rect.x/titleSize;
        posX1 -= 2;
        
        int posX2 = (rect.x + rect.width)/titleSize;
        posX2 += 2;
        
        
        int posY = rect.y / titleSize;
        
        if(posX1 < 0) {
            posX1 = 0;
        }
        
        if(posX2 >= physmap[0].length) {
            posX2 = physmap[0].length - 1;
        }
        
        for(int y = posY ; y >= 0 ; y--){
            
            for(int x = posX1 ; x<= posX2 ; x++)
            {
                if(physmap[y][x] == 1)
                {
                    Rectangle r = new Rectangle((int) getPosX() + x * titleSize,(int) getPosY() + y* titleSize , titleSize,titleSize);
          
                    if(rect.intersects(r)){
                        return r;
                    }
                }
            }
        }
        return null;
    }
    
    
    
    public Rectangle haveCollisionWithLeftWall(Rectangle rect){
        int posY1 = rect.y/titleSize;
        posY1 -= 2;
        
        int posY2 = (rect.y + rect.height)/titleSize;
        posY2 += 2;
        
        int posX1 = (rect.x + rect.width)/titleSize;
        int posX2 = posX1 - 3;
        
        if(posX2 < 0) {
            posX2 = 0;
        }
        
        if(posY1 < 0) {
            posY1 = 0;
        }
        
        if(posY2 >= physmap.length) {
            posY2 = physmap.length - 1;
        }
        
        for(int x = posX1; x >= posX2; x--){
            for(int y = posY1; y <= posY2;y++){
                if(physmap[y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * titleSize, (int) getPosY() + y * titleSize, titleSize, titleSize);
                    if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
    }
    
   
    public Rectangle haveCollisionWithRightWall(Rectangle rect){
        int posY1 = rect.y/titleSize;
        posY1 -= 2;
        
        int posY2 = (rect.y + rect.height)/titleSize;
        posY2 += 2;
        
        int posX1 = (rect.x + rect.width)/titleSize;
        int posX2 = posX1 + 3;
        
        if(posX2 >= physmap[0].length) 
        {
            posX2 = physmap[0].length - 1;
        }
        
        if(posY1 < 0) {
            posY1 = 0;
        }
        
        if(posY2 >= physmap.length) {
            posY2 = physmap.length - 1;
        }
        
        for(int x = posX1; x <= posX2; x++){
            for(int y = posY1; y <= posY2;y++){
                if(physmap[y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * titleSize, (int) getPosY() + y * titleSize, titleSize, titleSize);
                    if(r.y < (rect.y + rect.height - 1) && rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
    }
    
    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }
    
    
    public boolean haveCollision(Rectangle rectangle) {
		if( this.haveCollisionWithRightWall(rectangle) != null || 
			this.haveCollisionWithLeftWall(rectangle) != null) {
			return true;
		}
		return false;
	}

	public int[][] getPhysmap() {
		return physmap;
	}

	public void setPhysmap(int[][] physmap) {
		this.physmap = physmap;
	}
    
    

}
