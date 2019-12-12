package sample.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.util.Duration;
import javafx.scene.media.*;

public class MusicPlayer {
	
	private static MediaPlayer tempMusicPlayer;
	private String path = "src/sample/SystemElements/Sounds/";
	Media musicFile;
	
	//Constructor
	public MusicPlayer(String filename, boolean repeat) throws IOException {
		
		path += filename;
		musicFile = new Media(new File(path).toURI().toString());
		tempMusicPlayer = new MediaPlayer(musicFile);
		
		if(repeat)
		tempMusicPlayer.setOnEndOfMedia(new Runnable() {
	        public void run() {
	            tempMusicPlayer.seek(Duration.ZERO);
	            tempMusicPlayer.play();  
	        }
	    });
		
		BufferedReader reader = new BufferedReader(new FileReader("src/sample/SystemElements/Textfiles/Settings.txt"));
		String[] parts = reader.readLine().split(" ", 4);
        reader.close();
        
        tempMusicPlayer.setVolume(Float.parseFloat(parts[2])/100);
	}
    
	//Function to play the music file
	public void beginPlaying() {
		
		tempMusicPlayer.play();
	}
	
	//Function to stop the music
	public void stopPlaying() {
		
		tempMusicPlayer.stop();
	}
	
	//Function to pause the music
	public void pausePlaying() {
		
		tempMusicPlayer.pause();
	}
	
	//Function to change volume
	public void setVolumeFunction(float volume) {
		
		MusicPlayer.tempMusicPlayer.setVolume(volume);
	}
}
