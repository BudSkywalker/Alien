package com.alien.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	public static Clip clip;
	
	public static void play(String fileName) {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(("/resources/sounds/" + fileName))));
			clip.start();
			Log.element("Sound", fileName, "Sound played");
		} catch(UnsupportedAudioFileException e) {
			Log.error(e);
		} catch(LineUnavailableException e) {
			Log.error(e);
		} catch(IOException e) {
			Log.error(e);
		} catch(Exception e) {
			Log.error(e);
		}
	}
	
	public static void stop() {
		clip.stop();
		clip.close();
		Log.out("Sound Stopped");
	}
}
