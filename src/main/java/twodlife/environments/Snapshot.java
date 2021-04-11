package twodlife.environments;

import java.util.List;
import java.util.Map;

public class Snapshot {

    List<Dot> dots;
    Map<String, Integer> metrics;
    List<Map<String, Double>> attributes;
    Map<String, String> metadata;

    public Snapshot(
            Map<String, String> metadata,
            List<Dot> dots,
            Map<String, Integer> metrics,
            List<Map<String, Double>> attributes) {
        this.metadata = metadata;
        this.dots = dots;
        this.metrics = metrics;
        this.attributes = attributes;

    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public List<Dot> getDots() {
        return dots;
    }

    public void setDots(List<Dot> dots) {
        this.dots = dots;
    }

    public Map<String, Integer> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Integer> metrics) {
        this.metrics = metrics;
    }

    public List<Map<String, Double>> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Map<String, Double>> attributes) {
        this.attributes = attributes;
    }

}
