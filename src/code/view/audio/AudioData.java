package code.view.audio;

//sound import
import javax.sound.sampled.AudioFormat;

/** Record per i dati di una traccia audio */
public record AudioData(byte[] audioContent, AudioFormat audioFormat) {}
