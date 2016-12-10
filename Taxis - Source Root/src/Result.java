import java.util.ArrayList;

/** Result Node to store the paths*/
public class Result {
    private double distance;
    //    ArrayList<Node> path;
    private ArrayList<Node> path;

    public ArrayList<Node> getPath() {
        return path;
    }

    public double getDistance() {
        return distance;
    }

    public Result(double distance, ArrayList<Node> path) {
        this.distance = distance;
        this.path = path;
    }
}
