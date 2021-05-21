package Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;

public enum Sounds {
	whipAttack("/Soundtracks/whipAttack.wav",63548),
	bossFightMusic("/Soundtracks/bossfight.wav"),
	ouch("/Soundtracks/ouch.wav",69460),
	menu("/Soundtracks/menu.wav",69460),
	win("/Soundtracks/win.wav",1798676),
	jump("/Soundtracks/jump.wav", 114674),
	gameover("/Soundtracks/gameover.wav",  2021488),
	slimeHit("/Soundtracks/slimeHit.wav",   80758),
	golemHit("/Soundtracks/golemHit.wav", 140568),
	golemAttack("/Soundtracks/golemAttack.wav", 190712),
	golemCharge("/Soundtracks/golemCharge.wav", 646564),
	level1("/Soundtracks/level1.wav");
	
	public final AudioInputStream AUDIO_INPUT_STREAM;
	public final String PATH;
	
	public AudioInputStream getAudioInputStream() {
		AudioInputStream tryAudioInputStream = null;
		try {
			tryAudioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(PATH)));
		}
		catch(Exception e) {
			System.out.println("Couldn't load sound file.");
			e.printStackTrace();
		}
		return tryAudioInputStream;
	}
	
	// for sounds to be played once at a time
	Sounds(String path, int bytes) {
		this.PATH = path;
		this.AUDIO_INPUT_STREAM = getAudioInputStream();
		this.AUDIO_INPUT_STREAM.mark(bytes);
	}
	
	// for soundtracks as a background
	Sounds(String path) {
		this.PATH = path;
		this.AUDIO_INPUT_STREAM = getAudioInputStream();
	}
}
