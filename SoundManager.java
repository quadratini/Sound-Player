import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundManager {
    public static void playSound(String fileName) {
        File file = new File(fileName);
        playSound(file);
    }
    public static void playSound(File file) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30f);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }
}