import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;

public class GUI  {
    GUI() {
        JFrame myJFrame = new JFrame("Pathfinder");           // makes a new framed window with the given title
        JPanel mainPanel = new JPanel(new BorderLayout());
        MapPanel mapPanel = new MapPanel();
        mapPanel.setPreferredSize(new Dimension(1000, 700));
        myJFrame.add(mainPanel);
        mainPanel.add(mapPanel);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.pack();
        myJFrame.setLocationRelativeTo(null);
        myJFrame.setVisible(true);
        ////////////////////////////////////////////////////////////////////
        //do click detection here
        //todo add click detection here




    }
     public class MapPanel extends JPanel implements MouseListener {
        private Node click1Node;



        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            drawNodes(g);
        }
        public void drawNodes(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            for (Node node: controller.nodes.values()) {
                g2d.fillOval( node.x_cord, node.y_cord, 3, 3);
            }
            for (LinkPair linkPair: controller.linkHashMap.values()) {
                LinkPair.Coordinate prevCord = linkPair.coordinateLinkedList.get(0);
                for(LinkPair.Coordinate coordinate:
                    linkPair.coordinateLinkedList) {
                    g2d.drawLine(prevCord.xCord,prevCord.yCord,coordinate.xCord,coordinate.yCord);
                    prevCord = coordinate;
                }

            }
        }

         private Node getNodeFromClick(int clickX, int clickY){
            int closestX;

             for (Node node: controller.nodes.values()) {
                 if(node.x_cord == cl)
             }
         }

         @Override
         public void mouseClicked(MouseEvent e) {
            if(click1Node == null){

            }
         }

         @Override
         public void mousePressed(MouseEvent e) {

         }

         @Override
         public void mouseReleased(MouseEvent e) {

         }

         @Override
         public void mouseEntered(MouseEvent e) {

         }

         @Override
         public void mouseExited(MouseEvent e) {

         }
     }
}
