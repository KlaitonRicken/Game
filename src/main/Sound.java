package main;

//import java.applet.Applet;
//import java.applet.AudioClip;

import java.io.*;
import javax.sound.sampled.*;

//@SuppressWarnings("deprecation")
public class Sound {
	
	public static class Clips {
		public Clip[] clips;
		private int p;
		private int count;
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
			if(buffer == null)
				return;
			
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}
		
		public void play() {
			if(clips == null) return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if(p >= count) p = 0;
		}
		
		public void loop() {
			if(clips == null) return;
			clips[p].loop(300);
		}
		
		public static Clips music = load("/music.wav", 1);
		public static Clips hurt = load("/hurt.wav", 1);
		public static Clips passos = load("/passosGrama.wav", 1);
		public static Clips shoot = load("/shootRifle.wav", 1);
		public static Clips reload = load("/reloadRifle.wav", 1);
		public static Clips missAmo = load("/missAmo.wav", 1);
		public static Clips dropItem = load("/dropItem.wav", 1);
		public static Clips lighter = load("/lighter.wav", 1);
		public static Clips text = load("/text.wav", 1);
		public static Clips selected = load("/selected.wav", 1);
		
		private static Clips load(String name, int count) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataInputStream dis  =new DataInputStream(Sound.class.getResourceAsStream(name));
				
				byte[] buffer = new byte[1024];
				int read = 0;
				
				while((read = dis.read(buffer)) >= 0) {
					baos.write(buffer,0,read);
				}
				dis.close();
				byte[] data = baos.toByteArray();
				return new Clips(data, count);
				
			}catch (Exception e) {
				try {
					return new Clips(null,0);
				}catch (Exception ee) {
					return null;
				}
			}
		}
		
	}
	
//	private AudioClip clip;
//	
//	public static final Sound musicBackground = new Sound("/music.wav");
//	public static final Sound hurtEffect = new Sound("/hurt.wav");
//	public static final Sound passosGrama = new Sound("/passosGrama.wav");
//	public static final Sound shootRifle = new Sound("/shootRifle.wav");
//	public static final Sound ReloadRifle = new Sound("/reloadRifle.wav");
//	public static final Sound missAmo = new Sound("/missAmo.wav");
//	public static final Sound dropItem = new Sound("/dropItem.wav");
//	public static final Sound lighter = new Sound("/lighter.wav");
//	
//	private Sound(String name) {
//		try {
//			clip = Applet.newAudioClip(Sound.class.getResource(name));
//		}catch(Throwable e) {}
//	}
//	
//	public void play() {
//		try {
//			new Thread() {
//				public void run() {
//					clip.play();
//				}
//			}.start();
//		}catch(Throwable e) {}
//	}
//	
//	public void loop() {
//		try {
//			new Thread() {
//				public void run() {
//					clip.loop();
//				}
//			}.start();
//		}catch(Throwable e) {}
//	}
//	
//	public void stop() {
//		try {
//			new Thread() {
//				public void run() {
//					clip.stop();;
//				}
//			}.start();
//			
//		}catch (Throwable e) {
//			System.out.println("Erro ao pausar arquivo de som");
//		}
//	}

}
