import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class DataImporter {
    public HashMap<Integer, Node> dataImporter(String dataFolder) {
        HashMap<Integer, Node> nodeHashMap = new HashMap<>();
        //todo going to be difficult to iterate through this with just a nodeHashMap
        //wait can just use .values

        HashMap<Integer, LinkPair> linkPairHashMap = new HashMap<>();
        //nodes (intersections)
        try {
            DataInputStream inStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/nodes.bin")));
            int numNodes = inStream.readInt();
            for (int i = 0; i < numNodes; i++) {
                int nodeID = inStream.readInt();
                int x = inStream.readInt();
                int y = inStream.readInt();

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
                //todo should I only do this if I need to??^^
                linkPair.link1 = link;
                linkPair.linkID = linkID;
                //todo figure out reversing links
                //maybe do it after I add the waypoint stream
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
        //todo figure out front end drawing/routing later
        //so I'll draw the links first, then use two one way links.
        //I'll use the one way links to draw the path/directions
        //waypoints
        try {
            DataInputStream waypointsStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/links-waypoints.bin")));

            int numLinks = waypointsStream.readInt();
            for (int i = 0; i < numLinks; i++) {
                int linkID = waypointsStream.readInt();
                LinkPair pair = linkPairHashMap.get(linkID);
                LinkedList<LinkPair.Coordinate> coordinateLinkedList = new LinkedList<>();
                //note should start with end node
                int numWaypoints = waypointsStream.readInt();
                for (int p = 0; p < numWaypoints; p++) {
                    int x2 = waypointsStream.readInt();
                    int y2 = waypointsStream.readInt();
                    coordinateLinkedList.addFirst(pair.new Coordinate(x2,y2));

                }
                pair.assignWaypoints(coordinateLinkedList);
            }
            waypointsStream.close();


        } catch (IOException e) {
            System.err.println("error on waypoints: " + e.getMessage());
        }


        return nodeHashMap;
    }
}
