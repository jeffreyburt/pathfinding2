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
        mapPanel.setPreferredSize(new Dimension(1000 + (controller.graphicsBorder*2), 700 + (controller.graphicsBorder * 2)));
        myJFrame.add(mainPanel);
        mainPanel.add(mapPanel);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.pack();
        myJFrame.setLocationRelativeTo(null);
        myJFrame.setVisible(true);
        ////////////////////////////////////////////////////////////////////
        //do click detection here
        //todo add click detection here
        myJFrame.addMouseListener(mapPanel);





    }
     public class MapPanel extends JPanel implements MouseListener {
        private Node click1Node;



        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            drawNodes(g);
            drawPath(g);
        }

         private void drawPath(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setColor(Color.RED);
            if(controller.pathLinkStack != null){
                for (Link link: controller.pathLinkStack) {
                    LinkPair linkPair = controller.linkHashMap.get(link.linkID);
                    LinkPair.Coordinate prevCord = linkPair.coordinateLinkedList.get(0);
                    for(LinkPair.Coordinate coordinate:
                            linkPair.coordinateLinkedList) {
                        graphics2D.drawLine(prevCord.xCord,prevCord.yCord,coordinate.xCord,coordinate.yCord);
                        prevCord = coordinate;
                    }
                }
            }
         }

         public void drawNodes(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            if(controller.drawNodes) {
                for (Node node : controller.nodes.values()) {
                    g2d.fillOval(node.x_cord, node.y_cord, 3, 3);
                }
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
            int smallestDistance = Integer.MAX_VALUE;
            Node closestNode = null;
             for (Node node: controller.nodes.values()) {
                 int distance = (int) Math.sqrt(Math.pow(node.x_cord - clickX, 2) + Math.pow(node.y_cord - clickY, 2));
                 if(distance <= smallestDistance){
                     smallestDistance = distance;
                     closestNode = node;
                 }
             }
             System.out.println("Successfully found Node: " + closestNode);
             return closestNode;
         }

         @Override
         //todo this doesnt work
         public void mouseClicked(MouseEvent e) {

         }

         @Override
         public void mousePressed(MouseEvent e) {
             if(click1Node == null){
                 click1Node = getNodeFromClick(e.getX(), e.getY());
             }else {
                 controller.pathFind(click1Node, getNodeFromClick(e.getX(), e.getY()));
                 click1Node = null;
                 System.out.println("Drawing path...");
                 this.repaint();
             }
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
