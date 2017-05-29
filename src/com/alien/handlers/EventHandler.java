package com.alien.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.alien.Tick;
import com.alien.gfx.Render;
import com.alien.gfx.Screen;
import com.alien.util.Log;
import com.alien.window.Window;

public class EventHandler {
	static InputHandler inputHandler;
	static Screen screen;
	static Window window;
	static Render render = new Render();
	static Tick tick = new Tick();
	
	public static String saveName = "SAVE";
	
	public static void startup() {
		try {
			FileInputStream propInput = new FileInputStream("resources/levels/" + saveName + ".resources");
	        Properties properties = new Properties();
	        properties.load(propInput);
	        
	        Screen.xOffset = Integer.parseInt(properties.getProperty("xOffset"));
	        Log.element("ge.gfx.Screen", "xOffset", "Set to " + properties.getProperty("xOffset"));
	        Screen.yOffset = Integer.parseInt(properties.getProperty("yOffset"));
	        Log.element("ge.gfx.Screen", "yOffset", "Set to " + properties.getProperty("yOffset"));
	        
	        propInput.close();
		} catch (FileNotFoundException e) {
			Log.error(e);
		} catch (IOException e) {
			Log.error(e);
		}
		
		try {
			File overlayPath = new File("resources/images/overlay.png");
			Window.overlay = ImageIO.read(overlayPath);
		} catch (IOException e) {
			Log.error(e);
		}
	}
	
	public static void restart() {
		Log.out("Restarting");
		
		try {
			File inputLocation = new File("resources/levels/template.png");
			File outputLocation = new File("resources/levels/" + saveName + ".png");
			
			FileInputStream input = new FileInputStream(inputLocation);
			Log.element("ge.handles.EventHandler", "input", "Template image mapped");
			FileOutputStream output = new FileOutputStream(outputLocation);
			Log.element("ge.handles.EventHandler", "output", "Save image mapped");
			
			byte[] buffer = new byte[1024];

	     	int length;
	        while ((length = input.read(buffer)) > 0){
	        	output.write(buffer, 0, length);
	        }
	        
	        Log.out(inputLocation.getAbsolutePath() + " copied to " + outputLocation.getAbsolutePath());
	        
	        FileOutputStream propOutput = new FileOutputStream("resources/levels/" + saveName + ".resources");
	        Properties properties = new Properties();
	        
	        input.close();
	        output.close();
	        propOutput.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			File overlayPath = new File("resources/images/overlay.png");
			Window.overlay = ImageIO.read(overlayPath);
		} catch (IOException e) {
			Log.error(e);
		}
	}
	
	public static void save() {
		try {
	        FileOutputStream propOutput = new FileOutputStream("resources/levels/" + saveName + ".resources");
	        Properties properties = new Properties();
	        
	        properties.setProperty("xOffset", Screen.xOffset + "");
	        Log.out("X Offset stored at " + Screen.xOffset);
	        properties.setProperty("yOffset", Screen.yOffset + "");
	        Log.out("Y Offset stored at " + Screen.yOffset);
	        
	        Screen.xOffset = Integer.parseInt(properties.getProperty("xOffset"));
	        Log.element("ge.gfx.Screen", "xOffset", "Set to " + properties.getProperty("xOffset"));
	        Screen.yOffset = Integer.parseInt(properties.getProperty("yOffset"));
	        Log.element("ge.gfx.Screen", "yOffset", "Set to " + properties.getProperty("yOffset"));
	        
	        properties.store(propOutput, null);
	        propOutput.close();
	        
	        FileOutputStream settingsOutput = new FileOutputStream("resources/settings.properties");
	        Properties settingsProperties = new Properties();
	        
	        settingsProperties.store(settingsOutput, null);
	        settingsOutput.close();
    	} catch(FileNotFoundException e) {
    		Log.error(e);
    	} catch (IOException e) {
			Log.error(e);
		}
	}
	
	public static void shutdown() {
    	save();
    	Log.out("Goodbye!");
    	Log.dump();
    	System.exit(0);
	}
}
