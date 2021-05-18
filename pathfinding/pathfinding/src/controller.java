import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;

public class controller {
    public static HashMap<Integer, Node> nodes = new HashMap<>();
    public static  HashMap<Integer, LinkPair> linkHashMap = new HashMap<>();

    public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //parameters//
        String dataFolder = "C:\\Users\\Jeffrey Burt\\Desktop\\Catlin2-allroads-2020\\Catlin2-allroads-2020";

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

}
