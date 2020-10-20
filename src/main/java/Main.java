import twodlife.environments.Jungle;
import twodlife.food.Grass;
import twodlife.life.Bug;
import twodlife.life.Movable;
import twodlife.utils.RandomnessOfLife;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jcodec.api.awt.AWTSequenceEncoder;

public class Main {
    public static int TIME_LIMIT = 3000;

    public static void main(String[] args) throws IOException {

        Jungle jungle = new Jungle(250, 250);
        Long time = 0l;
        File f = new File("simulation.mp4");
        AWTSequenceEncoder enc = AWTSequenceEncoder.createSequenceEncoder(f, 60);

        while (time < TIME_LIMIT) {
            time += 1;

            // snapshot Jungle
            BufferedImage bi = jungle.toImage(time.toString());
            enc.encodeImage(bi);

            if (RandomnessOfLife.random.nextInt() % 10 == 0) {
                jungle.create(new Grass());
            } else if (RandomnessOfLife.random.nextInt() % 10 == 1) {
                jungle.create(new Bug(jungle));
            }

            List<Movable> allMovables = jungle.getAllMovables();
            for (Movable m : allMovables) {
                m.move();
            }
        }
        enc.finish();
    }

}