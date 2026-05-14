package code.view.audio;

//sound import
import javax.sound.sampled.AudioFormat;

public record AudioData(byte[] audioContent, AudioFormat audioFormat) {}
