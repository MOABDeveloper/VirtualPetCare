package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private static Clip backgroundMusic;
    private static boolean isPlaying = false;
    private static float volume = 0.5f; // Default volume (50%)

    public static void playBackgroundMusic(String filePath) {
        try {
            if (backgroundMusic != null && backgroundMusic.isRunning()) {
                backgroundMusic.stop();
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File(filePath).getAbsoluteFile());
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);

            // Set volume before playing
            setVolume(volume);

            // Loop continuously
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaying = true;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void setVolume(float volumeLevel) {
        // Ensure volume is between 0 and 1
        volume = Math.max(0.0f, Math.min(1.0f, volumeLevel));

        if (backgroundMusic != null && backgroundMusic.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);

            // Convert linear volume to decibels
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public static float getVolume() {
        return volume;
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            isPlaying = false;
        }
    }

    public static void toggleBackgroundMusic() {
        if (isPlaying) {
            stopBackgroundMusic();
        } else {
            if (backgroundMusic != null) {
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                isPlaying = true;
            }
        }
    }

    public static boolean isPlaying() {
        return isPlaying;
    }
}