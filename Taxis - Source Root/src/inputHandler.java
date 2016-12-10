//import com.sun.prism.image.Coords;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;


/** Stores all the input from files*/
public class inputHandler {
    String filename;
    Scanner scanner;
    public inputHandler(String filename) throws FileNotFoundException {
        try {
            scanner = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e) {
            if (filename.equals("client.csv")) System.out.println("Client file not found!");
            else if (filename.equals("taxis.csv")) System.out.println("Taxis file not found!");
            else if (filename.equals("nodes.csv")) System.out.println("Nodes file not found!");
            System.exit(1);
        }
    }

    public double[] getClientPosition() throws InputMismatchException {
        double [] coords = new double[2];
        try {
//            scanner.nextLine();
            scanner.useDelimiter(",|\n");
            scanner.next();
            scanner.next();
            coords[0] = scanner.nextDouble();
//            System.out.println(coords[0]);
//            if (scanner.hasNext()) System.out.println("ok");
            coords[1] = scanner.nextDouble();
//            System.out.println(coords[1]);
        }
        catch (InputMismatchException e) {
            System.out.println("Incompatible Input File!");
            System.exit(1);
        }
        return coords;
    }
    public ArrayList<Taxi> getTaxisPositions() throws Exception {
        double [] coords = new double[2];
        ArrayList<Taxi> t = new ArrayList<>();
        int id;
        try {
            scanner.nextLine();
            scanner.useDelimiter(",|\n");
            while (scanner.hasNextLine()) {
                coords[0] = scanner.nextDouble();
                coords[1] = scanner.nextDouble();
                id = scanner.nextInt();
                t.add(new Taxi(coords[0], coords[1], id));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return t;
    }
    public ArrayList<Node> getNodesPositions() throws Exception {
        double [] coords = new double[2];
        ArrayList<Node> t = new ArrayList<>();

        int id, arraylocation = 0;
        try {
            scanner.nextLine();
            scanner.useDelimiter(",|\n");
            int i = 0;
            while (scanner.hasNextLine()) {
                coords[0] = scanner.nextDouble();
                coords[1] = scanner.nextDouble();
                id = scanner.nextInt();
                scanner.nextLine();
                Node temp = new Node(coords[0], coords[1], id, arraylocation);
                temp.indexMain = i++;
                temp.setgScore(Double.MAX_VALUE);
                temp.setfScore(Double.MAX_VALUE);
                temp.cameFrom = null;
                t.add(temp);
                arraylocation++;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return t;
    }

}