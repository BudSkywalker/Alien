package com.alien;

import java.util.Random;

import com.alien.window.Window;

public class Tick extends Window {
	private static final long serialVersionUID = 1L;
	//100 ticks = 1 second
	public int tickCount = 0;
	public int eventTick = 0;
	
	public Tick() {
	}
	
	public void tick() {
		tickCount++;
		eventTick++;

		if(eventTick >= 1000) {
			eventTick();
			eventTick = 0;
		}
		
	}
	
	public void eventTick() {
		Random gen = new Random();
		Game.event = gen.nextInt(20);
		Game.event();
	}
	
	public void inputTick() {
		
	}
}
