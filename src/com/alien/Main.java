package com.alien;

import java.awt.event.MouseEvent;

import com.alien.handlers.InputHandler;
import com.alien.handlers.InputHandler.Key;
import com.alien.util.Commands;
import com.alien.util.Config;
import com.alien.util.Console;
import com.alien.util.Log;
import com.alien.window.Window;

public class Main {
static Runnable console = new Console();

	static Window window = new Window();
	static InputHandler inputHandler;
		
	public static void main(String[] args) {
		Log.reset("latest.log");
		addConfig();
		Log.out("Configuration loaded");
		addCommands();
		Log.out("Commands loaded");
		addBindings();
		Log.out("Bindings Added");
		if(Config.get("displayconsole").equals("true")) {
			console.run();
			Log.element("Console", "frmConsole", "Displayed");
		} else if(Config.get("displayconsole").equals("false")) {
		} else {
			Log.warning("Unknow argument " + Config.get("displayconsole") + " in config.properties. Must be true or false");
		}
		window.init();
	}
	
	public static void addCommands() {
		Commands.add("help", "help", "Displays all commands");
		Log.out("Help command added");
	}
	
	public static void addConfig() {
		Config.add("keeplogs", "true");
		Config.add("displayconsole", "false");
		Config.add("showfps", "true");
		Config.store();
	}
	
	public static void addBindings(){
		Key.setBinding("select", MouseEvent.BUTTON1);
		Key.setBinding("option", MouseEvent.BUTTON2);
		Key.store();
	}
}
