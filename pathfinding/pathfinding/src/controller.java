import javax.swing.*;
import java.util.HashMap;
import java.util.Stack;

public class controller {
    public static HashMap<Integer, Node> nodes = new HashMap<>();
    public static  HashMap<Integer, LinkPair> linkHashMap = new HashMap<>();
    public static Stack<Link> pathLinkStack = null;
    public static boolean drawNodes = false;
    public static int graphicsBorder = 5;
    public static double pixelsToMiles;

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
        Pathfinder pathfinderObject2 = new Pathfinder();
        JeffreyFind jeffreyFindObject = new JeffreyFind();
        pathLinkStack = pathfinderObject.pathfinder(startNode, endNode, true);
        pathfinderObject2.pathfinder(startNode, endNode, false);
        //jeffreyFindObject.jeffreyFind(startNode, endNode);

        System.out.println(pathfinderObject.nodeArrayList.size());
        System.out.println(pathfinderObject2.nodeArrayList.size());

//        for (int i = 0; i < pathfinderObject.nodeArrayList.size() ; i++) {
//            if(pathfinderObject.nodeArrayList.get(i) != pathfinderObject2.nodeArrayList.get(i)){
//                System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//            }
//        }
    }

}
