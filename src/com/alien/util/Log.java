package com.alien.util;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Log {
	static File folder = new File("logs");
	
	static LocalDateTime time = LocalDateTime.now();
	
	public static void out(String string) {
		String origin = new Exception().getStackTrace()[1].getClassName().substring(10);
    	String print = 
    			"[" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "] " + 
    	    			origin + ": " + 
    	    			string;
    	System.out.println(print);
    	
    	log(print);
    }
	
	public static void element(String className, String variable, String string) {
    	String print =
    			"[" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "] " + 
    			className + ": " + 
    			variable + "> " + 
    			string;
    	System.out.println(print);
    	
    	log(print);
    }
	
	public static void error(Exception exception) {
		String origin = new Exception().getStackTrace()[1].getClassName().substring(10);		
		String print =
    			"[" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "] " + 
    			origin + ": " + 
    			exception;
		System.err.println(print);
		exception.printStackTrace();
		log(print);
    }
	
	public static void warning(String string) {
		String origin = new Exception().getStackTrace()[1].getClassName().substring(10);
    	String print =
    			"[" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "] " + 
    			origin + ": " + 
    			string;
    	System.err.println(print);
    	
    	log(print);
    }
	
	public static void dump() {	
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		Path oldFilePath = Paths.get(folder +"/latest.log");
		Path newFilePath = Paths.get(folder + "/" + time.getDayOfMonth() + "." + time.getMonth() + "." + time.getYear() + "_" + time.getHour() + "." + time.getMinute() + "." + time.getSecond() + ".log");
    	
		
    	if(Config.get("keeplogs").equals("true")) {
    		try(BufferedWriter writer = Files.newBufferedWriter(newFilePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
        		@SuppressWarnings("resource")
    			Scanner scanner = new Scanner(oldFilePath);
        		while(scanner.hasNextLine()) {
        			writer.write(scanner.nextLine());
        			writer.newLine();
        		}
        	} catch(IOException e) {
        		e.printStackTrace();
        	}
    	} else if(Config.get("keeplogs").equals("false")) {
    		
    	} else {
    		Log.warning("Unknown argument " + Config.get("log") + " in config.properties. Must be true or false");
    	}
	}
	
	public static void reset(String fileName) {
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
    	Path filePath = Paths.get(folder + "/" + fileName);

    	try(BufferedWriter writer = Files.newBufferedWriter(filePath)) {
    		writer.write("");
    	} catch(IOException e) {
    		Log.error(e);
    	}
	}
	
	public static void log(String print) {  
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
	  	Path filePath = Paths.get(folder + "/latest.log");    	
    	try(BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
    		writer.write(print);
    		writer.newLine();
    	} catch(IOException e) {
    		Log.error(e);
    	}
	}
}
