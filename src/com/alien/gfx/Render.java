package com.alien.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alien.Game;
import com.alien.util.Config;
import com.alien.util.Log;
import com.alien.window.Window;

public class Render extends Window {
	private static final long serialVersionUID = 1L;
	
	public static int tileX = (int) ((-1) * frame.getWidth() * 0.3);
	
	public void render(Graphics graphics, BufferStrategy bufferStrategy) throws IOException {
		background(graphics);
		counters(graphics);
		fps(graphics);
		
		graphics.dispose();
		bufferStrategy.show();
	}

	private void background(Graphics graphics) throws IOException {
		BufferedImage background = ImageIO.read(new File("resources/images/background.png"));
		graphics.drawImage(background, 0, 0, screenWidth, screenHeight, null);
	}
	
	private void counters(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Chiller", 0, screenHeight / 30));
		graphics.drawString("Crew Remaning: " + Game.crew, screenWidth - (screenWidth / 3) + (screenWidth / 23), screenHeight / 30);
		graphics.drawString("Food: " + Game.food, screenWidth - (screenWidth / 6) + (screenWidth / 100), screenHeight / 30);
		graphics.drawString("Water: " + Game.water, screenWidth - (screenWidth / 12), screenHeight / 30);
	}
	
	private void fps(Graphics graphics) {
		if(Config.get("showfps").equals("true")) {
			graphics.setColor(Color.GREEN);
			graphics.setFont(new Font("Century Gothic", 0, screenHeight / 50));
			graphics.drawString("FPS: " + Window.fps, screenWidth - (screenWidth / 50) - (screenWidth / 50), screenHeight);
		} else if(Config.get("showfps").equals("false")) {
		} else {
			Log.warning("Unknow argument " + Config.get("showfps") + " in config.properties. Must be true or false");
		}
	}
	
	/**private void renderTile(Graphics graphics, Tile tile, int x, int y) {
		graphics.drawImage(SpriteSheet.getSprite(Tile.getTilePosX(tile), Tile.getTilePosY(tile)), 
				(WIDTH * x - Screen.xOffset), 
				HEIGHT * y - Screen.yOffset, WIDTH, HEIGHT, null);
	}*/
}
