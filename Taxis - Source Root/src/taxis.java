
/** Main program*/
import java.util.*;
import java.lang.*;

public class taxis {
    /** Creates HashMap and stores neighbours*/
    private static Map<String, ArrayList<Node>> CreateHashMap(ArrayList<Node> list) {
        Map<String, ArrayList<Node>> map = new HashMap<>();
        int i = 0;
        for (Node object : list) {
            String s = Double.toString(object.x) + "," + Double.toString(object.y);

            //System.out.println("Location of neighbour: " + map.get(s));
            boolean flag = false;
            int j = 0;
            if (map.get(s) != null )    {
                for (Node loop : map.get(s)) {
                    if (s.equals(Double.toString(map.get(s).get(j).x) + "," + Double.toString(map.get(s).get(j).y))) {
                        flag = true;
                        object.isMain = false;
                        object.indexMain = loop.indexMain;
                        break;
                    }
                    j++;
                }
            }
            if (flag) {
                if ((i >= 0) && (i < 1000)) {
//                    System.out.println("Main node: " + (map.get(s).get(j).arraylocation+2));
//                    System.out.println("Location of neighbour: " + (i+2));  //+2 due to csv_line - array difference
//                    System.out.println("X: " + map.get(s).get(j).x);
//                    System.out.println("Y: " + map.get(s).get(j).y);
//                    System.out.println();
                }
                if ((i > 0) && (list.get(i-1).id == list.get(i).id))    {
                    map.get(s).get(j).addNeighbours(i-1);


                }
                if ((i < list.size() - 1) && (list.get(i+1).id == list.get(i).id)) {
                    map.get(s).get(j).addNeighbours(i + 1);
                }

            }
            else {
                ArrayList<Node> temp = new ArrayList<>();
                temp.add(object);
                if ((i > 0) && (list.get(i-1).id == list.get(i).id))    {
                    object.addNeighbours(i-1);
                }
                if ((i < list.size() - 1) && (list.get(i+1).id == list.get(i).id)) {
                    object.addNeighbours(i + 1);
                }
                map.put(s, temp);

                // System.out.println(i + ": " + map.get(s).get(0).x + ", " + map.get(s).get(0).y);
            }
            i++;
        }
        //System.out.println(i);
        return map;
    }

    public static void completeNeighbours(ArrayList<Node> list)    {
        for (Node object : list)    {
            if (!object.getNeighbours().isEmpty())  {

                for (int i = 0; i < object.getNeighbours().size(); i++) {
                    // if (object.getNeighbours().contains) list.get(object.getNeighbours(i)).addNeighbours(list.indexOf(object));
                }
            }
        }
    }

    /** Main Program*/
    public static void main(String[] Args) throws Exception {
        double[] ClientCoords = (new inputHandler("client.csv")).getClientPosition();
        List<Taxi> taxis = (new inputHandler("taxis.csv")).getTaxisPositions();
        ArrayList<Node> nodes = (new inputHandler("nodes.csv")).getNodesPositions();
        Map<String, ArrayList<Node>> heuristic = CreateHashMap(nodes);
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\t<kml xmlns=\"http://earth.google.com/kml/2.1\">\n" +
                "\t<Document>\n" +
                "\t\t<name>Taxi Routes</name>\n" +
                "\t\t<Style id=\"green\">\n" +
                "\t\t\t<LineStyle>\n" +
                "\t\t\t\t<color>ff009900</color>\n" +
                "\t\t\t\t<width>4</width>\n" +
                "\t\t\t</LineStyle>\n" +
                "\t\t</Style>\n" +
                "\t\t<Style id=\"red\">\n" +
                "\t\t\t<LineStyle>\n" +
                "\t\t\t\t<color>ff0000ff</color>\n" +
                "\t\t\t\t<width>4</width>\n" +
                "\t\t\t</LineStyle>\n" +
                "\t\t</Style>");
        Result res = AStar.findSolution(nodes, taxis, ClientCoords);

    }
    int i = 2;


}