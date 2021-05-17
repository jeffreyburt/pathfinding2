import javax.swing.*;
import java.util.HashMap;

public class controller {


    public static void main(String[] args){
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //parameters//
        String dataFolder = null;

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        
        HashMap<Integer,Node> nodes = new HashMap<>();
DataImporter dataImporter = new DataImporter();
GUI gui = new GUI();
nodes = dataImporter.dataImporter("C:\\Users\\Jeffrey\\Downloads\\Catlin2-allroads-2020\\Catlin2-allroads-2020");
        for (Node node:
             nodes.values()) {
            //TODO figure out how to pass nodes to gui
        }
    }

}
