package twodlife;

import com.google.gson.Gson;
import org.jcodec.common.logging.Logger;
import twodlife.environments.Dot;
import twodlife.environments.Jungle;
import twodlife.environments.Snapshot;
import twodlife.food.Grass;
import twodlife.life.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jcodec.api.awt.AWTSequenceEncoder;

public class JungleManager {
    public static int TIME_LIMIT = 10000;
    public static int METRICS_BARS = 200;
    public static int SAMPLE_SKIP = TIME_LIMIT / METRICS_BARS;

    public static String simulationName = "simulation" + System.currentTimeMillis();

    public static void main(String[] args) throws IOException {

        File f = new File(simulationName + ".mp4");
        AWTSequenceEncoder enc = AWTSequenceEncoder.createSequenceEncoder(f, 60);

        JungleManager m = new JungleManager();
        m.init("123", 256, 144, 1000, 200);


        FileWriter metricsFile = new FileWriter(simulationName + "_metrics.csv");
        String metricsHeader = "time,male_count,female_count,food_count";
        metricsFile.write(metricsHeader);
        FileWriter attributesFile = new FileWriter(simulationName + "_attributes.csv");
        String attributesHeader = "time,energy_consumption,smell_distance";
        attributesFile.write(attributesHeader);

        Jungle jungle = m.jungles.get("123");
        while (jungle.time() < TIME_LIMIT) {

            // snapshot Jungle
            BufferedImage bi = jungle.toImage();
            enc.encodeImage(bi);

            if (jungle.time() % SAMPLE_SKIP == 0) {
//                metricsFile.write(jungle.getMetrics());
//                attributesFile.write(jungle.getAttributes());
            }

            System.out.println(m.progress("123"));

            if (jungle.time() % 10 == 0) {
                Logger.info("Time has passed: " + jungle.time());
            }
        }
        metricsFile.flush();
        enc.finish();
    }

    public Map<String, Jungle> jungles = new HashMap<>();

    public void init(String id, int xSize, int ySize, int grass, int bugs) throws IOException {
        Jungle jungle = new Jungle(xSize, ySize);
        for (int i = 0; i < grass; i++) {
            jungle.create(new Grass());
        }
        for (int i = 0; i < bugs; i++) {
            // perhaps jungle should be responsible for the creation.
            jungle.create(BugFactory.makeDefaultBug(jungle));
        }
        jungles.put(id, jungle);
    }

    public String progress(String id) {
        Jungle jungle = jungles.get(id);

        List<Dot> dots = jungle.toPlottable();
        Map<String, Integer> metrics = jungle.getMetrics();
        List<Map<String, Double>> attributes = jungle.getAttributes();

        jungle.move();
        for (int i = 0; i < 50; i++) {
            jungle.create(new Grass());
        }
        
        Map<String, String> metadata = new HashMap<>();
        metadata.put("time", "" + jungle.time());
        return toJson(new Snapshot(metadata, dots, metrics, attributes));
    }

    // todo: make json
    public static String toJson(Snapshot snapshot) {
        Gson gson = new Gson();
        return gson.toJson(snapshot);
    }

}