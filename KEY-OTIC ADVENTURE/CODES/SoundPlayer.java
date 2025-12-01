import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundPlayer {

    private static Clip bgmClip;  
    private static Clip sfxClip;  

    // ============================================================
    // PLAY ONE-TIME SOUND 
    // ============================================================
    public static void playSound(String fileName) {
        try {
            URL soundURL = SoundPlayer.class.getResource("/sounds/" + fileName);
            if (soundURL == null) {
                System.out.println("Sound file not found: " + fileName);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundURL);

            if (sfxClip != null) {
                if (sfxClip.isRunning()) sfxClip.stop();
                sfxClip.close();
            }

            sfxClip = AudioSystem.getClip();
            sfxClip.open(audioInput);
            sfxClip.start();  

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing sound " + fileName + ": " + e.getMessage());
        }
    }

    // ============================================================
    // PLAY LOOPING BACKGROUND MUSIC
    // ============================================================
    public static void playLoop(String fileName) {
        stopLoop(); 

        try {
            URL soundURL = SoundPlayer.class.getResource("/sounds/" + fileName);
            if (soundURL == null) {
                System.out.println("Loop sound file not found: " + fileName);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundURL);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioInput);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing loop sound " + fileName + ": " + e.getMessage());
        }
    }

    // ============================================================
    // STOP LOOPING BGM
    // ============================================================
    public static void stopLoop() {
        if (bgmClip != null) {
            if (bgmClip.isRunning()) bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }
}