package code.view.audio;

/** Enumerazione per le tracce audio del gioco */
public enum AudioBank 
{
	BACKGROUND_MUSIC       (AudioUtils.loadAudioSample("/resources/AudioSamples/backgroundMusic.wav")),
	EMPTYLOOT_SAMPLE       (AudioUtils.loadAudioSample("/resources/AudioSamples/emptyLoot.wav")),
	GOODLOOT_SAMPLE        (AudioUtils.loadAudioSample("/resources/AudioSamples/goodLoot.wav")),
	LASERATTACK_SAMPLE     (AudioUtils.loadAudioSample("/resources/AudioSamples/laserAttack.wav")),
	PUNCHATTACK_SAMPLE     (AudioUtils.loadAudioSample("/resources/AudioSamples/punchAttack.wav")),
	PLATFORM_SAMPLE	       (AudioUtils.loadAudioSample("/resources/AudioSamples/platform.wav")),
	ROBOTRUNNING_SAMPLE    (AudioUtils.loadAudioSample("/resources/AudioSamples/robotRunning.wav")),
	OPENINGTERMINAL_SAMPLE (AudioUtils.loadAudioSample("/resources/AudioSamples/openingTerminal.wav")),
	CLOSINGTERMINAL_SAMPLE (AudioUtils.loadAudioSample("/resources/AudioSamples/closingTerminal.wav")),
	PLAYERDEATH_SAMPLE     (AudioUtils.loadAudioSample("/resources/AudioSamples/playerDeath.wav"));

	/** Dati della traccia audio */
	private AudioData audioData;
	
	/**
	 * Costruisce l'istanza enumerativa
	 * @param audioData
	 * i dati della traccia audio
	 */
	private AudioBank(AudioData audioData)
	{ this.audioData = audioData; }
	
	/**
	 * Restituisce i dati della traccia audio
	 * @return
	 * i dati della traccia audio
	 */
	public AudioData getAudioData()
	{ return audioData; }
}
