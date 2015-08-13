import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.util.Arrays;

/**
 * Created by test on 1/28/15.
 */
public class TargetDataLineAudioFormats {
    public static void main(String[] args) {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfo) {
            System.out.println(info.getDescription());
            Mixer mixer = AudioSystem.getMixer(info);

            System.out.println("\t" + Arrays.toString(mixer.getTargetLines()));
            System.out.println("\t" + Arrays.toString(mixer.getSourceLines()));
        }

    }
}
