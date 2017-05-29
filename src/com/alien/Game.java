package com.alien;

import com.alien.util.Log;

public class Game extends Main {
	public static int crew = 3;
	public static int food = 9;
	public static int water = 9;
	public static int event;
	
	public static void event() {
		switch(event) {
		case 0:
			break;
		case 1:
			break;
		default:
			Log.warning("Event number " + event + " is not a know event");
		}
	}

}
