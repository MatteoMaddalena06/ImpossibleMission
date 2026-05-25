package code.view.audio;

//data strcuture import
import java.util.HashMap;
import java.util.Map;
//sound import
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
//model import
import code.model.context.AttackLaunched;
import code.model.context.PlayerDied;
import code.model.context.PlayerFoundSomething;
import code.model.context.PlayerOnPlatform;
import code.model.context.RunnerStartRunning;
import code.model.context.RunnerStopRunning;
import code.model.context.TerminalOpened;
import code.model.gameobjects.Furniture;
import code.model.gameobjects.enemy.AttackerRobot;
import code.view.menu.event.TerminalClosed;
import code.model.context.AttackEnded;
//event import
import code.event.EventDispatcher;

/** Classe per la riproduzione dell'audio */
public class AudioPlayer
{
	/** Istanza per il singleton */
	private static AudioPlayer instance;
	
	/** {@link Clip} per la musica di sottofondo */
	private Clip backgroundClip;
	/** Array associativo Oggetto -> {@link Clip} per le tracce audio in riproduzione */
	private Map<Object, Clip> runningClips;
	
	/**
	 * Restituisce l'istanze della classe
	 * @return
	 * l'istanza della classe
	 */
	public static AudioPlayer getInstance()
	{
		if(instance == null)
			instance = new AudioPlayer();
		
		return instance;
	}
	
	/** Costruisce la classe */
	private AudioPlayer()
	{
		runningClips = new HashMap<Object, Clip>();
		
		EventDispatcher.subscribeAsStatic(AttackLaunched.class, x -> {
			AttackerRobot.Attack.Type attackType = ((AttackLaunched)x).source().getType();
			
			switch(attackType)
			{
				case AttackerRobot.Attack.Type.ISTANT    -> playSample(AudioBank.LASERATTACK_SAMPLE.getAudioData());
				case AttackerRobot.Attack.Type.PROLONGED -> startRunningClip(((AttackLaunched)x).source(), AudioBank.PUNCHATTACK_SAMPLE.getAudioData());
			}	
		});
		
		EventDispatcher.subscribeAsStatic(AttackEnded.class, x -> {
			if(((AttackEnded)x).source().getType() != AttackerRobot.Attack.Type.PROLONGED)
				return;
			
			removeRunningClip(((AttackEnded)x).source());
		});
		
		EventDispatcher.subscribeAsStatic(PlayerOnPlatform.class,   x -> playSample(AudioBank.PLATFORM_SAMPLE.getAudioData()));
		EventDispatcher.subscribeAsStatic(RunnerStartRunning.class, x -> startRunningClip(((RunnerStartRunning)x).robot(), AudioBank.ROBOTRUNNING_SAMPLE.getAudioData()));
		EventDispatcher.subscribeAsStatic(RunnerStopRunning.class,  x -> removeRunningClip(((RunnerStopRunning)x).robot()));
		
		EventDispatcher.subscribeAsStatic(PlayerFoundSomething.class, x -> {
			if(((PlayerFoundSomething)x).source().getContent() == Furniture.LootType.EMPTY)
				playSample(AudioBank.EMPTYLOOT_SAMPLE.getAudioData());
			
			else
				playSample(AudioBank.GOODLOOT_SAMPLE.getAudioData());
		});
		
		EventDispatcher.subscribeAsStatic(TerminalOpened.class, x -> { stopAllRunningClips(); playSample(AudioBank.OPENINGTERMINAL_SAMPLE.getAudioData()); });
		EventDispatcher.subscribeAsStatic(TerminalClosed.class, x -> { playSample(AudioBank.CLOSINGTERMINAL_SAMPLE.getAudioData()); startAllRunningClips(); });
		
		EventDispatcher.subscribeAsStatic(PlayerDied.class, x -> playSample(AudioBank.PLAYERDEATH_SAMPLE.getAudioData()));
	}
	
	/**
	 * Riproduce una traccia audio 
	 * @param audioSample
	 * i dati della traccia audio
	 */
	private void playSample(AudioData audioSample)
	{
		Clip clip = AudioUtils.toClip(audioSample);

		clip.addLineListener(event -> {
		    if(event.getType() == LineEvent.Type.STOP) 
		        clip.close();
		});

		clip.start();
	}
	
	/**
	 * Riproduce in loop e memorizza una traccia audio
	 * @param object
	 * l'oggetto associato alla traccia audio
	 * @param audioSample
	 * i dati della traccia audio
	 * @see runningClips
	 */
	private void startRunningClip(Object object, AudioData audioSample)
	{
		Clip clip = AudioUtils.toClip(audioSample);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		runningClips.put(object, clip);
	}
	
	/**
	 * Interrompe una traccia audio prima riprodotta, la dimentica e libera le risorse di sistema impegnate nella riproduzione
	 * @param object
	 * l'oggetto associato alla traccia audio
	 * @see runningClips
	 */
	private void removeRunningClip(Object object)
	{
		Clip clip = runningClips.get(object);
		clip.stop();
		clip.close();
		runningClips.remove(object);
	}
	
	/** Interrompe tutte le traccie audio in riproduzione tranne quella di sottofondo */
	private void stopAllRunningClips()
	{ runningClips.forEach((k, v) -> v.stop()); }
	
	/** Riproduce tutte le traccie audio tranne quella di sottofondo */
	private void startAllRunningClips()
	{ runningClips.forEach((k, v) -> v.start());}
	
	/** Riproduce la musica di sottofondo */
	public void playBackgroundMusic()
	{
		disposeBackgroundMusic();
	    backgroundClip = AudioUtils.toClip(AudioBank.BACKGROUND_MUSIC.getAudioData());
	    backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/** Libera le risorse di sistema usate per la riproduzione della musica di sottofondo */
	public void disposeBackgroundMusic()
	{
		if(backgroundClip == null)
			return;
		
		backgroundClip.stop();
		backgroundClip.close();
		backgroundClip = null;
	}
	
	/** Libera le risorse di sistema usate per la riprduzione delle tracce audio tranne per quella di sottofondo */
	public void disposeRunningClips()
	{ 
		runningClips.forEach((k, v) -> v.close());
		runningClips.clear();
	}
}
