package com.alien.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
	static File folder = new File("config.properties").getAbsoluteFile().getParentFile();
	static Properties config = new Properties();
	
	public static void add(String value, String variable) { 
        if(!folder.exists()) {
            try {
                folder.mkdirs();
            } catch(SecurityException e) {
                Log.error(e);
            }
        }
        OutputStream output = null;
        Path filePath = Paths.get(folder + "/config.properties");    
  		if (get(value).equals("nulls")) {
  			try {
  				output = new FileOutputStream(filePath + "");
  				  				
  				config.put(value, variable);
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
    }
	
	public static String get(String value) {
		InputStream input = null;
		
		String string = "nulls";
		
		try {
			input = new FileInputStream(folder + "/config.properties");
			
			config.load(input);
			
			if(!config.isEmpty()) {
				if(!(config.contains(value))) {
					string = config.getProperty(value);
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
			output = new FileOutputStream(folder + "/config.properties");
			
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
