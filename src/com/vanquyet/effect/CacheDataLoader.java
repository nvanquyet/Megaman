/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanquyet.effect;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;

/**
 *
 * @author LAPTOP
 */
public class CacheDataLoader {

    private static CacheDataLoader instance; //Biáº¿n isntance tÄ©nh singletone

    private String frameFile = "data/frame.txt"; //Ä�á»�c dá»¯ liá»‡u cÃ¡c fframe tá»« file
    private String animationFile = "data/animation.txt"; // Ä�á»�c dá»¯ liá»‡u cÃ¡c animation tá»« file
    private String physicalMapFile  = "data/phys_map.txt"; // Ä�á»�c dá»¯ liá»‡u cÃ¡c animation tá»« file
    private String backgroundMapFile  = "data/background_map.txt";
    private String audioFile = "data/sounds.txt";

    private Hashtable<String, FrameImage> frameImage; //Táº¡o 1 table vá»›i cÃ¡c frame kÃ¨m theo KEY
    private Hashtable<String, Animation> animation; //Táº¡o 1 table vá»›i cÃ¡c animation kÃ¨m theo KEY
    private Hashtable<String, AudioClip> sounds;
    private int [][] physmap;
    private int [][] backgroundmap;
     

    private CacheDataLoader() {

//        frameImage = new Hashtable<String, FrameImage>();
//        animation = new Hashtable<String, Animation>();
    }

    public static CacheDataLoader getInstance() {
        if (instance == null) {
            instance = new CacheDataLoader();
        }
        return instance;
    }

    public void LoadFrame() throws IOException {
        frameImage = new Hashtable<String, FrameImage>();

        //Ä�á»�c dá»¯ liá»‡u tá»« file lÃªn
        FileReader fr = new FileReader(frameFile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null) {
            System.out.println("No Data");
            throw new IOException();
        } else {

            fr = new FileReader(frameFile);
            br = new BufferedReader(fr);
//Ä�á»�c dá»¯ liá»‡u tá»«ng dÃ²ng tá»« file txt
            while ((line = br.readLine()).equals(""));

            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i++) {
                FrameImage frame = new FrameImage();

                while ((line = br.readLine()).equals(""));
                frame.setName(line);

                while ((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                String path = str[1];

                while ((line = br.readLine()).equals(""));
                str = line.split(" ");
                int x = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" ");
                int y = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" ");
                int w = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" ");
                int h = Integer.parseInt(str[1]);

                BufferedImage imageData = ImageIO.read(new File(path));
                BufferedImage image = imageData.getSubimage(x, y, w, h);
                frame.setBufferedImage(image);

                instance.frameImage.put(frame.getName(), frame);
                
            }
            br.close();
        }
    }

    //Láº¥y frameimage theo Key 
    public FrameImage getFrameImage(String name) {
        FrameImage fameImage = new FrameImage(instance.frameImage.get(name));
        return fameImage;
    }

    public void LoadAnimation() throws IOException {
        animation = new Hashtable<String, Animation>();

        FileReader fr = new FileReader(animationFile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null) {
            System.out.println("No Data");
            throw new IOException();
        } else {

            fr = new FileReader(animationFile);
            br = new BufferedReader(fr);

            while ((line = br.readLine()).equals(""));
            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i++) {
                Animation ani = new Animation();
                while ((line = br.readLine()).equals(""));
                ani.setName(line);

                while ((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                for (int j = 0; j < str.length; j += 2) {
                    
                    ani.add(getFrameImage(str[j]), Double.parseDouble(str[j + 1]));
                }
                
                instance.animation.put(ani.getName(), ani);
            }

            br.close();
        }

    }
    
    //Láº¥y animation theo Key
    public Animation getAnimation(String name){
        Animation ani = new Animation(instance.animation.get(name));
        return ani;
    }
    
    public void LoadPhysicalMap() throws IOException{
        FileReader fr = new FileReader(physicalMapFile);
        BufferedReader br = new BufferedReader(fr);
       
        //Láº¥y sá»‘ hÃ ng vÃ  cá»™t
        int numberOfRows = Integer.parseInt(br.readLine());
        int numberOfColumns = Integer.parseInt(br.readLine());
        
        //Khá»Ÿi táº¡o máº£ng
        physmap  = new int[numberOfRows][numberOfColumns];

        for(int i=0;i<numberOfRows;i++){
            String [] arr = br.readLine().split(" ");
            for(int j=0;j<numberOfColumns;j++){
                instance.physmap[i][j] = Integer.parseInt(arr[j]);
            }
        }


    }
    
    public int [][] getBackGroundMap(){
    	return instance.backgroundmap;
    }
    
    public void LoadBackGroundMap() throws IOException {
    	
		FileReader fileReader;
		try {
			fileReader = new FileReader(backgroundMapFile);
			BufferedReader br = new BufferedReader(fileReader);
			String line = null;
			line = br.readLine();
			int numberofOfRows = Integer.parseInt(line);
			line = br.readLine();
			int numberofOfColums = Integer.parseInt(line);
			
			instance.backgroundmap = new int[numberofOfRows][numberofOfColums];
			
			for(int i = 0; i < numberofOfRows ; i++) {
				line = br.readLine();
				String arr[] = line.split(" ");
				for(int j = 0; j < numberofOfColums ; j++) {
					instance.backgroundmap[i][j] = Integer.parseInt(arr[j]);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

    
    public void LoadAudio() throws IOException{
        sounds = new Hashtable<String, AudioClip>();
        
        FileReader fr = new FileReader(audioFile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        if(br.readLine()==null) { // no line = "" or something like that
            System.out.println("No data");
            throw new IOException();
        }
        else {
            
            fr = new FileReader(audioFile);
            br = new BufferedReader(fr);
            
            while((line = br.readLine()).equals(""));
            
            int n = Integer.parseInt(line);
            
            for(int i = 0;i < n; i ++){
                
                AudioClip audioClip = null;
                while((line = br.readLine()).equals(""));

                String[] str = line.split(" ");
                String name = str[0];
                
                String path = str[1];

                try {
                   audioClip =  Applet.newAudioClip(new URL("file","",str[1]));

                } catch (MalformedURLException ex) {}
                
                instance.sounds.put(name, audioClip);
            }
            
        }
        
        br.close();
        
    }
    
    
    
    public AudioClip getAudio(String key) {
    	return instance.sounds.get(key);
    }

    //Load data tá»« file lÃªn
    public void LoadData() throws IOException{
        LoadFrame();
        System.out.println("Load Frame: Done!");
        LoadAnimation();
        System.out.println("Load Animation: Done!");
        LoadPhysicalMap();
        System.out.println("Load PhysicalMap: Done!");
        LoadBackGroundMap();
        System.out.println("Load BackGroundMap: Done!");
        LoadAudio();
        System.out.println("Load Audio: Done!");
    }

    public int [][] getPhysicalMap(){
        return instance.physmap;
    }

}
