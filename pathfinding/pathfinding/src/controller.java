import javax.swing.*;
import java.util.HashMap;

public class controller {
    public static HashMap<Integer, Node> nodes = new HashMap<>();

    public static void main(String[] args) {
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


        DataImporter dataImporter = new DataImporter();

        nodes = dataImporter.dataImporter("C:\\Users\\Jeffrey\\Downloads\\Catlin2-allroads-2020\\Catlin2-allroads-2020");
        GUI gui = new GUI();
    }

}
