package code.view.audio;

//data structure import
import java.util.HashMap;
import java.util.Map;
//IO import
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
//sound import
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class AudioUtils
{
	private static final int BUFFER_SIZE = 4096;
	
	private static final Map<String, AudioData> AUDIO_CACHE = new HashMap<String, AudioData>();
	
	public static AudioData loadAudioSample(String pathname)
	{ return AUDIO_CACHE.computeIfAbsent(pathname, x -> loadRaw(pathname)); }
	
	public static Clip toClip(AudioData audioSample)
	{
		try 
		{
			Clip clip = AudioSystem.getClip();
			clip.open(audioSample.audioFormat(), audioSample.audioContent(), 0, audioSample.audioContent().length);
			return clip;
	    } 
		catch (LineUnavailableException e) 
		{ throw new IllegalStateException("Unable to properly read the audio sample " + audioSample + " to convert it into a Clip"); }
	}
	
	private static AudioData loadRaw(String pathname) 
	{
		InputStream input = AudioUtils.class.getResourceAsStream(pathname);
		
		if(input == null)
			throw new IllegalStateException("Unable to load the audio sample: " + pathname + " not found.");
		
		try( 
				BufferedInputStream bufferedInput = new BufferedInputStream(input);
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedInput); 
				ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream()
		)
		{
			byte[] buffer = new byte[BUFFER_SIZE];
			int read;
			
	        while ((read = audioStream.read(buffer)) != -1) {
	        	bytesOutput.write(buffer, 0, read);
	        }

	        return new AudioData(bytesOutput.toByteArray(), audioStream.getFormat());
		}
		catch(IOException exp)
		{ throw new IllegalStateException("Unable to load the audio sample " + pathname); }
		catch(UnsupportedAudioFileException exp)
		{ throw new IllegalStateException("Bad audio sample format " + pathname); }
	}
}
