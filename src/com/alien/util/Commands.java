package com.alien.util;

import java.util.ArrayList;

public class Commands {
	static ArrayList<String> commands = new ArrayList<>();
	static ArrayList<String> commandsUse = new ArrayList<>();
	static ArrayList<String> commandsDesc = new ArrayList<>();
	
	public static void add(String command, String commandUse, String commandDesc) {
		commands.add(command);
		commandsUse.add(commandUse);
		commandsDesc.add(commandDesc);
	}
	
	public static String get(int num, String string) {
		if(string == "command") {
			return commands.get(num);
		} else if(string == "commandUse") {
			return commands.get(num);
		} else if(string == "commandDesc") {
			return commands.get(num);
		} else {
			return null;
		}
	}
	
	public static int getID(String command) {
		int spaces = 0;
		int s1 = command.length();

		for(int j = 0; j < command.length(); j++) {
			if(command.charAt(j) == ' ') {
				spaces++;
				if(spaces == 1) {
					s1 = j;
				}
			}
		}
		
		command = command.substring(0, s1);
		
		if(command.equals(get(0, "command"))) {
			return 0;
		} else if(command.equals(get(1, "command"))) {
			return 1;
		} else {
			return 2;
		}
	}
	
	public static void run(String command) {
		int num = getID(command);
		
		int spaces = 0;
		int s1 = command.length();

		for(int j = 0; j < command.length(); j++) {
			if(command.charAt(j) == ' ') {
				spaces++;
				if(spaces == 1) {
					s1 = j;
				}
			}
		}
		
		String sub = command.substring(s1).trim();
		
		switch(num) {
		case 0:
			for(int i = 0; i < commands.size(); i++) {
				Log.out(commandsUse.get(i) + ": " + commandsDesc.get(i));
			}
			break;
		case 1:
			break;
		default:
			Log.warning("Unkown Command");
			break;
		}
	}
}
