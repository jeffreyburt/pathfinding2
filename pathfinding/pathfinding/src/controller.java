import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class controller {
    public static HashMap<Integer, Node> nodes = new HashMap<>();
    public static  HashMap<Integer, LinkPair> linkHashMap = new HashMap<>();
    public static Stack<Link> pathLinkStack = null;
    public static boolean drawNodes = false;
    public static int graphicsBorder = 5;

    public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //parameters//
        String dataFolder = "C:\\Users\\Jeffrey Burt\\Desktop\\US-primary-2020\\US-primary-2020";

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }


        DataImporter dataImporter = new DataImporter();
        dataImporter.dataImporter(dataFolder);
        GUI gui = new GUI();
    }
    public static void pathFind(Node startNode, Node endNode){
        Pathfinder pathfinderObject = new Pathfinder();
        Pathfinder pathfinderObject2 = new Pathfinder();
        pathLinkStack = pathfinderObject.pathfinder(startNode, endNode, true);
        pathfinderObject2.pathfinder(startNode, endNode, false);
    }

}
