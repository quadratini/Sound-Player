import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundManager {
    private static File currFile;
    private static Clip currClip;
    private static long clipTime;

    public static long getTime() {
        return clipTime;
    }

    public static Clip getClip() {
        return currClip;
    }

    public static File getFile() {
        return currFile;
    }

    public static void playSound(String fileName) {
        File file = new File(fileName);
        playSound(file);
    }

    public static void stopSound() {
        if (currClip != null) {
            clipTime = 0;
            currClip.stop();
        }
    }

    public static void pauseSound() {
        if (currClip != null) {
            clipTime = currClip.getMicrosecondPosition();
            currClip.stop();
        }
    }

    public static void playSound() {
        if (currClip != null) {
            currClip.setMicrosecondPosition(clipTime);
            currClip.start();
        }
    }

    public static void openFile(File file) {
        try {
            currFile = file;
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            currClip = AudioSystem.getClip();
            currClip.open(audioIn);
            FloatControl gainControl = (FloatControl) currClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30f);

            clipTime = 0;
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public static void playSound(File file) {
            if (currFile != file) {
                openFile(file);
            } else {
                System.out.println("anal bambo: " + clipTime);
            }
            playSound();
    }
}