package src;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MusicPlayer {
    private static Clip backgroundMusic;
    private static boolean isPlaying = false;
    private static float volume = 0.1f;
    private static float sfxVolume = 0.1f;
    private static Map<String, Clip> soundEffects = new HashMap<>(); // Cache for sound effects

    // Background music methods (existing)
    public static void playBackgroundMusic(String filePath) {
        try {
            if (backgroundMusic != null && backgroundMusic.isRunning()) {
                backgroundMusic.stop();
            }

            InputStream raw = MusicPlayer.class.getResourceAsStream("/" + filePath);
            if (raw == null) {
                System.out.println("Background music file not found: " + filePath);
                return;
            }

            BufferedInputStream buffered = new BufferedInputStream(raw);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(buffered);

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);

            setVolume(volume);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaying = true;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Could not play background music: " + e.getMessage());
        }
    }

//    public static void playBackgroundMusic(String filePath) {
//        try {
//            if (backgroundMusic != null && backgroundMusic.isRunning()) {
//                backgroundMusic.stop();
//            }
//
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
//            backgroundMusic = AudioSystem.getClip();
//            backgroundMusic.open(audioInputStream);
//
//            setVolume(volume);
//            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
//            isPlaying = true;
//
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }

    public static void playSoundEffect(String filePath) {
        try {
            Clip clip = soundEffects.get(filePath);

            if (clip == null) {
                // Load from classpath
                InputStream raw = MusicPlayer.class.getResourceAsStream("/" + filePath);
                if (raw == null) {
                    System.out.println("Sound not found: " + filePath);
                    return;
                }

                BufferedInputStream buffered = new BufferedInputStream(raw);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(buffered);

                clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                // Cache it for reuse
                soundEffects.put(filePath, clip);
            }

            // Set SFX volume
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(sfxVolume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }

            clip.setFramePosition(0);
            clip.start();

        } catch (Exception e) {
            System.out.println("Error playing sound effect: " + filePath + " (" + e.getMessage() + ")");
        }
    }

    // New methods for sound effects
//    public static void playSoundEffect(String filePath) {
//        try {
//            Clip clip = soundEffects.get(filePath);
//
//            if (clip == null) {
//                // Load the sound if not already cached
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
//                        new File(filePath).getAbsoluteFile());
//                clip = AudioSystem.getClip();
//                clip.open(audioInputStream);
//                soundEffects.put(filePath, clip);
//            }
//
//            // Set SFX volume
//            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
//                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//                float dB = (float) (Math.log(sfxVolume) / Math.log(10.0) * 20.0);
//                gainControl.setValue(dB);
//            }
//
//            clip.setFramePosition(0); // Rewind to start
//            clip.start();
//
//        } catch (Exception e) {
//            System.out.println("Error playing sound effect: " + e.getMessage());
//        }
//    }

    public static void setSfxVolume(float volumeLevel) {
        sfxVolume = Math.max(0.0f, Math.min(0.5f, volumeLevel));
    }

    public static float getSfxVolume() {
        return sfxVolume;
    }

    // Existing methods remain unchanged
    public static void setVolume(float volumeLevel) {
        volume = Math.max(0.0f, Math.min(1.0f, volumeLevel));
        if (backgroundMusic != null && backgroundMusic.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
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
}