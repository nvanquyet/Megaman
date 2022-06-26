package com.vanquyet.effect;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Nguyễn Văn Quyết
 */
public class Animation {

    private String name;
    private boolean isRepeated;
    private ArrayList<FrameImage> frameImage;
    private int currentFrame;
    private ArrayList<Boolean> ignoreFrame;
    private ArrayList<Double> delayFrame;
    private long beginTime;
    private boolean drawRectFrame;

    public Animation() {
        this.isRepeated = true ;
        this.frameImage = new ArrayList<FrameImage>() ;
        this.currentFrame = 0 ;
        this.ignoreFrame = new ArrayList<Boolean>() ;
        this.delayFrame = new ArrayList<Double>() ;
        this.beginTime = 0;
        this.drawRectFrame = false ;
    }

    public Animation(Animation animation) {
        this.beginTime = animation.getBeginTime();
        this.isRepeated = animation.getIsRepeated();
        this.currentFrame = animation.getCurrentFrame();
        
        this.frameImage = new ArrayList<FrameImage>() ;
        for(FrameImage f : animation.getFrameImage()){
            this.frameImage.add(new FrameImage(f));
        }
        
        this.ignoreFrame = new ArrayList<Boolean>() ;
        for(Boolean b : animation.getIgnoreFrame() ){
            this.ignoreFrame.add(b);
        }
        
        this.delayFrame = new ArrayList<Double>() ;
        for(Double d : animation.getDelayFrame()){
            this.delayFrame.add(d);
        }
        
    }

//Getter and setter 
//IsRepeated
    public boolean getIsRepeated() {
        return isRepeated;
    }

    public void setIsRepeated(boolean isRepeated) {
        this.isRepeated = isRepeated;
    }

    
//IgnoreFrame
    
    public void setIgnoreFrame(int id) {
        if(id>=0 && id<= ignoreFrame.size() ){
            this.ignoreFrame.add(id, true);
        }
    }
    public void setUnIgnoreFrame(int id) {
        if(id>=0 && id<= ignoreFrame.size() ){
            this.ignoreFrame.add(id, false);
        }
    }
    
    public boolean getIgnoreFrame(int id){
        return ignoreFrame.get(id);
    }
    

    
    public ArrayList<Boolean> getIgnoreFrame() {
        return ignoreFrame;
    }

    
//Name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//FrameImage
    public ArrayList<FrameImage> getFrameImage() {
        return frameImage;
    }

//CurrentFrame
    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        if(currentFrame>=0 && currentFrame<= this.frameImage.size()){
            this.currentFrame = currentFrame;
        }else {
            this.currentFrame = 0;
        }
    }

    //Reset 
    public void reset(){
        this.beginTime = 0;
        this.currentFrame = 0;
    }
    
    
    //Add
    public void add(FrameImage frame,double timeToNextFrame){
        this.ignoreFrame.add(false);
        this.frameImage.add(frame);
        delayFrame.add(new Double(timeToNextFrame));
    }
    
    
    
//DelayFrame
    public ArrayList<Double> getDelayFrame() {
        return delayFrame;
    }
//BeginTime
    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

//DrawRectFrame
    public boolean isDrawRectFrame() {
        return drawRectFrame;
    }

    public void setDrawRectFrame(boolean drawRectFrame) {
        this.drawRectFrame = drawRectFrame;
    }
    
    public BufferedImage getCurrentImage(){
        return frameImage.get(currentFrame).getBufferedImage();
    }
    
    public void Update(long deltaTime){
        
        if(beginTime == 0) beginTime = deltaTime;
        else{
            
            if(deltaTime - beginTime > delayFrame.get(currentFrame)){
                nextFrame();
                beginTime = deltaTime;
            }
        }
        
    }
//Frame tiáº¿p theo
    private void nextFrame(){
        
        if(currentFrame >= frameImage.size() - 1){
            
            if(isRepeated) currentFrame = 0;
        }
        else currentFrame++;
        
        if(ignoreFrame.get(currentFrame)) nextFrame();
        
    }
    
//Láº¥y frame cuá»‘i cÃ¹ng     
    public boolean getLastFrame(){
        if(currentFrame == (frameImage.size() - 1)){
            return true;
        }
        return false;
    }
    
    
//Check xem cÃ³ pháº£i Frame cuá»‘i cÃ¹ng hay khÃ´ng!
   
//Ä�áº£o ngÆ°á»£c hÃ¬nh áº£nh
    
    public void flipAllImage(){
        
        for(int i = 0;i < frameImage.size(); i++){
            
            BufferedImage image = frameImage.get(i).getBufferedImage();
            
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(), 0);

            AffineTransformOp op = new AffineTransformOp(tx,
            AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image, null);
            
            frameImage.get(i).setBufferedImage(image);
        }
        
    }
    
//Draw: váº½
    public void draw(int x, int y, Graphics2D g2){
        
        BufferedImage image = getCurrentImage();
        
        g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
        if(drawRectFrame)
            g2.drawRect(x - image.getWidth()/2, x - image.getWidth()/2, image.getWidth(), image.getHeight());
        
    }
}
