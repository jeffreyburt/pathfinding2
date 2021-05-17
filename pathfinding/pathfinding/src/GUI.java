import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Map;

public class GUI {
    GUI() {
        JFrame myJFrame = new JFrame("Pathfinder");           // makes a new framed window with the given title
        JPanel mainPanel = new JPanel(new BorderLayout());
        MapPanel mapPanel = new MapPanel();
        mapPanel.setPreferredSize(new Dimension(1920, 1080));
        myJFrame.add(mainPanel);
        mainPanel.add(mapPanel);



        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.pack();
        myJFrame.setLocationRelativeTo(null);
        myJFrame.setVisible(true);
    }
     public class MapPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            drawNodes(g);
        }
        public void drawNodes(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawLine(50,50,1900,1070);
        }

    }
//test
}
