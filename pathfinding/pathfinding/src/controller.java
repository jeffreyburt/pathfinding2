import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class controller {
    public static HashMap<Integer, Node> nodes = new HashMap<>();
    public static  HashMap<Integer, LinkPair> linkHashMap = new HashMap<>();
    public static Stack<Link> pathLinkStack = null;

    public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //parameters//
        String dataFolder = "C:\\Users\\Jeffrey\\Downloads\\Catlin2-allroads-2020\\Catlin2-allroads-2020";

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
        pathLinkStack = pathfinderObject.pathfinder(startNode, endNode);
    }

}
