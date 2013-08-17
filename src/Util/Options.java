package Util;

import Util.Math.Int2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author carlos
 */
public class Options {
    
    private Int2 resolution;
    private boolean fullscreen;
    private boolean vsync;
    
    private static Options _instance;
    
    public static Options getInstance() {
        if (_instance == null) {
            _instance = new Options();
        }
        return _instance;
    }
    
    private Options() {
        resolution = new Int2();
    }
    
    public void readFile() {
        File file = new File(this.getClass().getResource("/options/options.txt").getPath());
        
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] split = line.split("=");
                String option = split[0];
                String value = split[1];
                
                switch (option) {
                    case "width":
                        resolution.setX(Integer.parseInt(value));
                        break;
                        
                    case "height":
                        resolution.setY(Integer.parseInt(value));
                        break;
                        
                    case "fullscreen":
                        fullscreen = Boolean.parseBoolean(value);
                        break;
                        
                    case "vsync":
                        vsync = Boolean.parseBoolean(value);
                        break;
                }
            }
            
            buffer.close();
            
        } catch (IOException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public int getWidth() {
        return resolution.getX();
    }

    public int getHeight() {
        return resolution.getY();
    }
    
    public Int2 getResolution() {
        return resolution;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public boolean isVsync() {
        return vsync;
    }
}
