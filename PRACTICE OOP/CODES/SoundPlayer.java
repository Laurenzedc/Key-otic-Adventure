import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundPlayer {

    private static Clip bgmClip; // for looping background music

    // Play a one-time sound (correct, wrong, level complete, etc.)
    public static void playSound(String fileName) {
        try {
            URL soundURL = SoundPlayer.class.getResource("/sounds/" + fileName);
            if (soundURL == null) {
                System.out.println("Sound file not found: " + fileName);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing sound " + fileName + ": " + e.getMessage());
        }
    }

    // Play looping background music (for traveling / directions)
    public static void playLoop(String fileName) {
        stopLoop(); // stop previous loop if any

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

    // Stop the background music loop
    public static void stopLoop() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }
}
