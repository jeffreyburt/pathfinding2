import javax.swing.*;
import java.util.HashMap;
import java.util.Stack;

//written by Jeffrey Burt
//I used some basic stackoverflow/googling
//I used this video to understand A*  https://www.youtube.com/watch?v=dQw4w9WgXcQ

public class controller {
    public static HashMap<Integer, Node> nodes = new HashMap<>();
    public static HashMap<Integer, LinkPair> linkHashMap = new HashMap<>();
    public static Stack<Link> aStarPath = null;
    public static Stack<Link> dsPath = null;
    public static Stack<Link> exploredLinkStackAStar = new Stack<>();
    public static Stack<Link> exploredLinkStackDs = new Stack<>();
    public static double pixelsToMiles;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //parameters//

    //folder containing road data
    public static String dataFolder = "C:\\Users\\Jeffrey\\Downloads\\US-primary-2020\\US-primary-2020";

    //whether or not to draw nodes (I recommend not)
    public static boolean drawNodes = false;

    //width of border around nodes
    public static int graphicsBorder = 25;

    //multiplier for pixel to mile ratio. Keep below 1 to ensure optimistic A* guess
    public static double pixelsToMilesAdjustment = 1;
    //it's interesting to turn this value up really high (I like 2 on the US map) and see how much more efficient a* is
    //it make ENORMOUS gains in speed at the cost of finding a longer route

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }

        System.out.println("Welcome to Jeffrey's pathfinding app.");
        System.out.println("Click anywhere on the map to select a node, pathfinder will run automatically once 2 nodes are selected");
        System.out.println("A*'s path is drawn in green, D's path is drawn in blue");
        System.out.println("A*'s explored links are drawn in magenta, D's are drawn in red");
        System.out.println();



        DataImporter dataImporter = new DataImporter();
        dataImporter.dataImporter(dataFolder);
        System.out.println("Pixel to mile ratio:  " + pixelsToMiles);
        pixelsToMiles *= pixelsToMilesAdjustment;
        System.out.println("Pixel to mile adjusted ratio:  " + pixelsToMiles);
        System.out.println();
        GUI gui = new GUI();
    }

    public static void pathFind(Node startNode, Node endNode) {
        Pathfinder aStarPathfinder = new Pathfinder();
        Pathfinder DsAlgorithmPathfinder = new Pathfinder();
        aStarPath = aStarPathfinder.pathfinder(startNode, endNode, true);
        dsPath = DsAlgorithmPathfinder.pathfinder(startNode, endNode, false);
        if ((aStarPath != null && dsPath != null) && aStarPath.equals(dsPath)) {
            System.out.println("Yay! A* successfully found minimum path distance!");
        }

//        System.out.println(aStarPathfinder.nodeArrayList.size());
//        System.out.println(DsAlgorithmPathfinder.nodeArrayList.size());
    }

}
