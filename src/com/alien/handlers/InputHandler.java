package com.alien.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.alien.util.Log;
import com.alien.window.Window;

public class InputHandler implements KeyListener, MouseListener {
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key select = new Key();
	public Key option = new Key();
	public Key escape = new Key();
	
	public int x;
	public int y;
	public static float mouseRatioX;
	public static float mouseRatioY;
	
	public InputHandler(Window window) {
		window.addKeyListener(this);
		window.addMouseListener(this);
		Log.out("Listening");
	}
	
	public static class Key {
		
		private int numTimesPressed = 0;
		private boolean pressed = false;
		
		public int getNumTimesPressed() {
			return numTimesPressed;
		}
		
		public boolean isPressed() {
			return pressed;
		}
		
		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if(isPressed) {
				numTimesPressed++;
			}
		}
		
		//Below this line is key-binding
		
		static File folder = new File("bindings.properties").getAbsoluteFile().getParentFile();
		static Properties config = new Properties();
		
		public static void setBinding(String key, int me) {			
			 if(!folder.exists()) {
		            try {
		                folder.mkdirs();
		            } catch(SecurityException e) {
		                Log.error(e);
		            }
	        }
	        OutputStream output = null;
	        Path filePath = Paths.get(folder + "/bindings.properties");    
	        if (getBinding(key).equals("nulls")) {
	  			try {
	  				output = new FileOutputStream(filePath + "");
	  				  				
	  				config.put(key, me + "");
	  			} catch(IOException e) {
	  				Log.error(e);
	  			} finally {
	  				if(output !=null) {
	  					try {
	  						output.close();
	  					} catch(IOException e) {
	  						Log.error(e);
	  					}
	  				}
	  			}
	  		}
		        Log.out("Binding " + me + " added for key " + key);
		}
		
		public static String getBinding(String key) {			
			InputStream input = null;
			
			String string = "nulls";
			
			try {
				input = new FileInputStream(folder + "/bindings.properties");
				
				config.load(input);
				
				if(!config.isEmpty()) {
					if(!(config.contains(key))) {
						string = config.getProperty(key + "");
						if(string == null) {
							string = "nulls";
						}
					}
				}

			} catch(IOException e) {
				Log.error(e);
			}

			return string;
		}
		
		public static void store() {    	
			OutputStream output = null;
		  	
			try {
				output = new FileOutputStream(folder + "/bindings.properties");
				
				config.store(output, null);
			} catch(IOException e) {
				Log.error(e);
			} finally {
				if(output != null) {
					try {
						output.close();
					} catch(IOException e) {
						Log.error(e);
					}
				}
			}
		}
	}
	
	public void keyPressed(KeyEvent event) {
		toggleKey(event.getKeyCode(), true);
	}
	
	public void keyReleased(KeyEvent event) {
		toggleKey(event.getKeyCode(), false);
	}
	
	public void keyTyped(KeyEvent event) {
		//null
	}
	
	public void mouseClicked(MouseEvent event) {
		x = event.getX();
		y = event.getY();
		mouseRatioX = (float) x / (float) Window.screenWidth;
		mouseRatioY = (float) y / (float) Window.screenHeight;
		
		toggleKey(event.getButton(), true);
	}
	
	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}

	public void mousePressed(MouseEvent event) {
	}

	public void mouseReleased(MouseEvent event) {
	}
	
	public void toggleKey(int keyCode, boolean isPressed) {
		if(keyCode == Integer.parseInt(Key.getBinding("select"))) {
			select.toggle(isPressed);
		}
		if(keyCode == Integer.parseInt(Key.getBinding("option"))) {
			option.toggle(isPressed);
		}
	}
}
