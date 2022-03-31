import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class End {
    private Clip clip;
    private File file;
    public End(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        file = new File(filepath);
        Scanner scanner = new Scanner(System.in);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
}
