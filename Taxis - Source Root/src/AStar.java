import java.lang.reflect.Array;
import java.util.*;

/** Executes the Astar algorithm*/
public class AStar {
    /** Finds node closest to the client's coords*/
    public static Node findClosestToClient(ArrayList<Node> nodes, double[] coords) {
        double distance = Double.MAX_VALUE;
        Node result = new Node(-1.0, -1.0, -1, 0);
        for (Node node : nodes) {
            if ((Math.sqrt(Math.pow(node.x - coords[0], 2.0) + Math.pow(node.y - coords[1], 2.0))) < distance) {
                distance = Math.sqrt(Math.pow(node.x - coords[0], 2.0) + Math.pow(node.y - coords[1], 2.0));
                result = node;
            }
        }
        return result;
    }
    /** Finds node closest to the taxi's coords*/
    public static Node findClosestToTaxi(ArrayList<Node> list, Taxi t) {
        double distance = Double.MAX_VALUE;
        Node result = new Node(-1.0, -1.0, -1, -1);

        for (Node tmp: list) {

            if ((Math.sqrt(Math.pow(tmp.x - t.x, 2.0) + Math.pow(tmp.y - t.y, 2.0))) < distance) {
                distance = Math.sqrt(Math.pow(tmp.x - t.x, 2.0) + Math.pow(tmp.y - t.y, 2.0));
                result = tmp;
            }
        }
        return result;
    }
    /** Calculates distance between two nodes*/
    private static double Distance(Node a, Node b) {return Math.sqrt(Math.pow(a.x - b.x, 2.0) + Math.pow(a.y - b.y, 2.0));}

    /** Executes the Astar algorithm for one taxi-client pair*/
    private static Result aStar(ArrayList<Node> list, Node start, Node goal) {
        ArrayList<Node> closedSet = new ArrayList<>();
        ArrayList<Node> openSet   = new ArrayList<>();
        openSet.add(start); // first node in our search
        // we wanna minimize f = g + h (ALL euclidean distances)
        Node current = start; //always a main node
        current.setgScore(0.0);
        int j;
        double fScore = Distance(start, goal); // fScore = 0 + heuristic distance
        while (!openSet.isEmpty()) {
            Node temp = openSet.get(0);
            if (temp.isMain) current = temp;
            else current = list.get(temp.indexMain);
//            current = openSet.get(0); // openSet is gonna be sorted in increasing order of fScore
            ///////
            // current needs to be main... if-check to make it main via index-indexMain correlation
            ///////
            if ((current.x == goal.x) && (current.y == goal.y))     {
                //if ()
                return (new Result(current.getgScore(), reconstructPath(current, goal, start)));
            }
            openSet.remove(0);
            closedSet.add(current);
            if (closedSet.size() == 154404) return new Result(-2.0, null);
            for (int i = 0; i < current.getNeighbours().size(); i++) {
                temp = list.get(current.getNeighbours().get(i));
                Node neighbour;
                if (temp.isMain) neighbour = temp;
                else neighbour = list.get(temp.indexMain);
                boolean flag = false;
                for (Node loop : closedSet)   { // all main nodes in closedSet
                    if ((loop.x == neighbour.x) && (loop.y == neighbour.y))    {
                        flag = true;
                        break;
                    }
                }

                if (flag) continue;
                double tentative_gScore = current.getgScore() + Distance(current, neighbour);
                flag = false;
                for (Node loop : openSet)   {
                    if ((loop.x == neighbour.x) && (loop.y == neighbour.y))    {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    int pos = 0;
                    for (Node k : openSet) {
                        neighbour.setfScore(tentative_gScore + Distance(neighbour, goal));
                        if (neighbour.getfScore() < k.getfScore()) {
                            break;
                        }
                        pos++;
                    }
                    openSet.add(pos, neighbour);
                }
                else if (tentative_gScore >= neighbour.getgScore()) continue; //  not a better path
                neighbour.cameFrom = current;
                neighbour.setgScore(tentative_gScore);
                neighbour.setfScore(tentative_gScore + Distance(neighbour, goal)); // use dat heuristic
//                System.out.println("openset size = " + openSet.size());
            }
        }
        return new Result(-1.0, null);
    }

    /** Constructs path for taxi-client pair*/
    private static ArrayList<Node> reconstructPath(Node curr, Node goal, Node start) {
        ArrayList<Node> path = new ArrayList<>();
        int i = 0;

        while ((curr.x != start.x && curr.y != start.y) || curr.cameFrom != null) {

            Node temp = new Node();
            temp.x = curr.x;
            temp.y = curr.y;
            temp.id = curr.id;
            temp.cameFrom = curr.cameFrom;
            temp.arraylocation = curr.arraylocation;
            temp.indexMain = curr.indexMain;
            temp.isMain = curr.isMain;
            temp.setfScore(curr.getfScore());
            temp.setgScore(curr.getgScore());
            ArrayList<Integer> neg = new ArrayList<Integer>(curr.getNeighbours());
            temp.setNeighbours(neg);
            curr = curr.cameFrom;
            path.add(temp);

            if (curr.x == start.x && curr.y == start.y) break;
        }

        path.add(path.size(), curr);
        return path;
    }
    /** Clears path from previous taxi-client pair*/
    private static void ClearPath(ArrayList<Node> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i++).cameFrom = null;
        }
        return;
    }


    /** Executes the Astar() method for all taxi-client pair*/
    public static Result findSolution(ArrayList<Node> list, List<Taxi> taxis, double[] coords) {
        Result res = new Result(0.0, null);
        Node goal  = findClosestToClient(list, coords);
       // System.out.println("Closest Node to Client :" + goal.x + ", " + goal.y);
        ArrayList<Result> results = new ArrayList<>();
        int f = 1;
        for (Taxi t : taxis) {
            Node start = findClosestToTaxi(list, t);
            //System.out.println("Closest Node to Taxi #" + taxis.indexOf(t) + ": " + start.x + ", " + start.y);
            // execute aStar
            res = aStar(list, start, goal);
            results.add(res);

            System.out.println("\t\t<Placemark>\n" +
                    "\t\t\t<name>Taxi " + f++ + "</name>\n" +
                    "\t\t\t<styleUrl>#red</styleUrl>\n" +
                    "\t\t\t<LineString>\n" +
                    "\t\t\t\t<altitudeMode>relative</altitudeMode>\n" +
                    "\t\t\t\t<coordinates>");

            for (int i = 0; i < res.getPath().size(); i++) {
                System.out.println("\t\t\t\t\t" + res.getPath().get(i).x + "," + res.getPath().get(i).y + "," + res.getPath().get(i).id);
            }

            System.out.println("\t\t\t\t</coordinates>\n" +
                    "\t\t\t</LineString>\n" +
                    "\t\t</Placemark>");
            ClearPath(list);

        }
        res = results.get(0);
        int k = 0;
        for (Result r : results) {
            if (r.getDistance() < res.getDistance()) {
                res = r;
                k = results.indexOf(r);
            }
        }
        System.out.println("\t</Document>\n" +
                "</kml>");
        System.out.println("<!--Manually change Taxi " + ++k + " with distance " + res.getDistance() + " to green colour at the kml file-->");
        return res;
    }
}