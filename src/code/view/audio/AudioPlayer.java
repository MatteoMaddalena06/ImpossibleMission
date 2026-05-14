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

public class AudioPlayer
{
	private static AudioPlayer istance;
	
	private Clip backgroundClip;
	private Map<Object, Clip> runningClips;
	
	public static AudioPlayer getIstance()
	{
		if(istance == null)
			istance = new AudioPlayer();
		
		return istance;
	}
	
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
	
	private void playSample(AudioData audioSample)
	{
		Clip clip = AudioUtils.toClip(audioSample);

		clip.addLineListener(event -> {
		    if(event.getType() == LineEvent.Type.STOP) 
		        clip.close();
		});

		clip.start();
	}
	
	private void startRunningClip(Object object, AudioData audioSample)
	{
		Clip clip = AudioUtils.toClip(audioSample);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		runningClips.put(object, clip);
	}
	
	private void removeRunningClip(Object object)
	{
		Clip clip = runningClips.get(object);
		clip.stop();
		clip.close();
		runningClips.remove(object);
	}
	
	private void stopAllRunningClips()
	{ runningClips.forEach((k, v) -> v.stop()); }
	
	private void startAllRunningClips()
	{ runningClips.forEach((k, v) -> v.start());}
	
	public void playBackgroundMusic()
	{
		disposeBackgroundMusic();
	    backgroundClip = AudioUtils.toClip(AudioBank.BACKGROUND_MUSIC.getAudioData());
	    backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
		
	public void disposeBackgroundMusic()
	{
		if(backgroundClip == null)
			return;
		
		backgroundClip.stop();
		backgroundClip.close();
		backgroundClip = null;
	}
	
	public void disposeRunningClips()
	{ 
		runningClips.forEach((k, v) -> v.close());
		runningClips = new HashMap<Object, Clip>();
	}
}
