package com.alien.window;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFrame;

import com.alien.Main;
import com.alien.Tick;
import com.alien.gfx.Render;
import com.alien.gfx.Screen;
import com.alien.handlers.EventHandler;
import com.alien.handlers.InputHandler;
import com.alien.handlers.InputHandler.Key;
import com.alien.util.Log;

public class Window extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 80;
	public static final int HEIGHT = WIDTH;
	public static final String name = "Spacemaster";
	
	public static int screenWidth;
	public static int screenHeight;
	
	public static int yOffset = 0;
	public static int xOffset = 0;
	
	public static JFrame frame;
	
	public boolean running = false;
	
	public static int researchTick = 0;
	public static int importTick = 0;
	public static int foodTick = 0;
	public static int airTick = 0;
	
	public Screen screen;
	public InputHandler inputHandler;
	public Render render;
	public Tick tick;
	
	public static boolean pause = false;
	
	public static int scrollOffset = 0;
	public static File saveFolder = new File("resources/levels");
	public static FilenameFilter filter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(".resources");
		}
	};
	public static File[] saveList = saveFolder.listFiles(filter);
	
	public static int fps;
	
	public static WindowListener exitListener;

	public static BufferedImage overlay;

	public void init() {
		frame = new JFrame(name);
		Log.element("window.Window", "frame", "Created");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Log.element("window.Window", "frame", "Default Close Operation set");
		frame.setLayout(new GridLayout());
		Log.element("window.Window", "frame", "Layout set");
		frame.add(this, BorderLayout.CENTER);
		Log.element("window.Window", "frame", "Bordering Center");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setMinimumSize(new Dimension(1366, 768));
		Log.element("window.Window", "frame", "Set extended layout");
		frame.setUndecorated(true);
		Log.element("window.Window", "frame", "Set borderless");		
		frame.pack();
		frame.setResizable(false);
		Log.element("window.Window", "frame", "Resizable set to false");
		frame.setVisible(true);
		Log.element("window.Window", "frame", "Set to invisible");
		
		screenWidth = frame.getWidth();
		screenHeight = frame.getHeight();
		
		screenHeight = ((frame.getWidth() / 16) * 9);
		
		//screenWidth = 1366;
		//screenHeight = 768;

		frame.setBounds(0, 0, screenWidth, screenHeight);
		
		inputHandler = new InputHandler(this);
		screen = new Screen(Window.screenWidth, Window.screenHeight);	
		Log.out("Screen initialized");
		
		Window.exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent event) {
		    	EventHandler.shutdown();
		    }
		};
		
		Window.frame.addWindowListener(Window.exitListener);
		Log.out("Exit Listener initialized");
		
		screen.setOffset((9 * (Window.screenWidth / Window.WIDTH)) * -1, 0);
		
		render = new Render();
		tick = new Tick();
		
		start();
	}
	
	public synchronized void start() {
		running = true;
		run();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		double tickLimiter = 1000000000D/100D;
		double tickReset = 0;
		int frames = 0;
		
		Log.out("Run");
		while(running) {
			long now = System.nanoTime();
			tickReset += (now - lastTime) / tickLimiter;
			lastTime = now;
			boolean shouldRender = false;
			
			while(tickReset >= 1) {
				if(pause == false) {
					tick.tick();
				}
				tickReset--;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				Log.error(e);
			}
			
			if(shouldRender) {
				frames++;
				
				BufferStrategy bufferStrategy;
				Graphics graphics;
				try{
					bufferStrategy = getBufferStrategy();
					if(bufferStrategy == null) {
						createBufferStrategy(3);
					}

					graphics = bufferStrategy.getDrawGraphics();
					graphics.setColor(Color.WHITE);
					graphics.fillRect(0, 0, getWidth(), getHeight());
					
					yOffset++;
					xOffset++;
					
					render.render(graphics, bufferStrategy);
				} catch(NullPointerException e) {
					Log.error(e);
				} catch(Exception e) {
					Log.error(e);
				}
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				fps = frames;
				frames = 0;
			}
		}
	}
}