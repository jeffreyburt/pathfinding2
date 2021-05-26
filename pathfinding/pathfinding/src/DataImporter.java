import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class DataImporter {
    public void dataImporter(String dataFolder) {
        HashMap<Integer, Node> nodeHashMap = new HashMap<>();

        HashMap<Integer, LinkPair> linkPairHashMap = new HashMap<>();
        //nodes (intersections)
        try {
            DataInputStream inStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/nodes.bin")));
            int numNodes = inStream.readInt();
            System.out.println("Processed " + numNodes + " nodes");
            for (int i = 0; i < numNodes; i++) {
                int nodeID = inStream.readInt();
                int x = inStream.readInt() + controller.graphicsBorder;
                int y = inStream.readInt() + controller.graphicsBorder;

                nodeHashMap.put(nodeID, new Node(nodeID, x, y));
            }
            inStream.close();
        } catch (IOException e) {
            System.err.println("error on nodes: " + e.getMessage());
        }

        //links (roads)
        try {
            DataInputStream linksStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/links.bin")));
            int numLinks = linksStream.readInt();
            for (int i = 0; i < numLinks; i++) {
                int linkID = linksStream.readInt();
                int startNodeID = linksStream.readInt();
                int endNodeID = linksStream.readInt();
                String name = linksStream.readUTF();
                double length = linksStream.readDouble();
                byte oneway = linksStream.readByte();
                boolean onewayBool = oneway == 1;

                LinkPair linkPair = new LinkPair();
                linkPairHashMap.put(linkID, linkPair);

                Link link = new Link(linkID, nodeHashMap.get(startNodeID), nodeHashMap.get(endNodeID), name, length);
                (nodeHashMap.get(startNodeID)).awayLinkList.add(link);
                nodeHashMap.get(endNodeID).towardsLinkList.add(link);
                linkPair.link1 = link;
                linkPair.linkID = linkID;
                if (oneway != 1) {
                    Link reverseLink = new Link(linkID, nodeHashMap.get(endNodeID), nodeHashMap.get(startNodeID), name, length);
                    ((nodeHashMap.get(endNodeID)).awayLinkList).add(reverseLink);
                    nodeHashMap.get(startNodeID).towardsLinkList.add(reverseLink);
                    linkPair.link2 = reverseLink;
                }
            }
            linksStream.close();
        } catch (IOException e) {
            System.err.println("error on links: " + e.getMessage());
        }
        try {
            DataInputStream waypointsStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/links-waypoints.bin")));

            int numLinks = waypointsStream.readInt();

            LinkPair pair = null;

            for (int i = 0; i < numLinks; i++) {
                int linkID = waypointsStream.readInt();
                pair = linkPairHashMap.get(linkID);
                LinkedList<LinkPair.Coordinate> coordinateLinkedList = new LinkedList<>();
                //note should start with end node
                int numWaypoints = waypointsStream.readInt();
                for (int p = 0; p < numWaypoints; p++) {
                    int x2 = waypointsStream.readInt() + controller.graphicsBorder;
                    int y2 = waypointsStream.readInt() + controller.graphicsBorder;
                    coordinateLinkedList.addFirst(pair.new Coordinate(x2, y2));

                }
                pair.assignWaypoints(coordinateLinkedList);
            }
            waypointsStream.close();
            controller.pixelsToMiles = pixelsToMiles(pair);

        } catch (IOException e) {
            System.err.println("error on waypoints: " + e.getMessage());
        }

        controller.nodes = nodeHashMap;
        controller.linkHashMap = linkPairHashMap;
    }

    private double pixelsToMiles(LinkPair linkPair) {
        double totalPixelDistance = 0;
        LinkPair.Coordinate prevCord = linkPair.coordinateLinkedList.get(0);
        for (LinkPair.Coordinate coordinate : linkPair.coordinateLinkedList) {
            totalPixelDistance += calculateDistance(prevCord.xCord, prevCord.yCord, coordinate.xCord, coordinate.yCord);
            prevCord = coordinate;
        }
        double pixelstoMiles = (linkPair.link1.length / totalPixelDistance);
        System.out.println("p2m ratio:  " + pixelstoMiles);
        return pixelstoMiles;
        //return 1;
    }

    private double calculateDistance(int x1, int y1, int x2, int y2) {
        double xDistance = Math.pow(x1 -x2, 2);
        double yDistance = Math.pow(y1 - y2, 2);
        return Math.sqrt(xDistance + yDistance);
    }

}
