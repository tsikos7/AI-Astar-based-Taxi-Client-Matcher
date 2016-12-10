import java.util.ArrayList;


/** Node for taxis and nodes of the map*/
public class Node {
    double x, y;
    int id;
    int arraylocation;
    boolean isMain = true;
    int indexMain;
    private ArrayList<Integer> neighbours = new ArrayList<Integer>();
    private double fScore, gScore;
    Node cameFrom;

    public double getfScore() {return fScore;}

    public double getgScore() {return gScore;}

    public void setfScore(double fScore) {this.fScore = fScore;}

    public void setgScore(double gScore) {this.gScore = gScore;}

    public Node () {}

    public Node(double x, double y, int id, int arraylocation) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.arraylocation = arraylocation;
        this.neighbours = new ArrayList<Integer>();
    }

    public void addNeighbours(int index) {this.neighbours.add(index);}

    public void setNeighbours(ArrayList<Integer> neighbours) {this.neighbours = neighbours;}

    public ArrayList<Integer> getNeighbours() {return neighbours;}
}