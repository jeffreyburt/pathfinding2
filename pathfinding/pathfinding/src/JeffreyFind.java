import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class JeffreyFind {
    private final HashMap<Node, PathfinderNode> pathfinderNodeHashtable = new HashMap<>();
    private final Stack<Link> pathStack = new Stack<>();
    private Node endNode;
    private PathfinderNode endPathfinderNode;
    private boolean isEndNodeFound = false;
    private final boolean noPath = false;
    private int nodesExplored = 1;
    private PathfinderNode workingPathfinderNode;

    /*
    note that this isn't truly A*
    normal A* star uses the same units for guess distance and path to start
    However, in this case our path to home is in miles, but our guess distance is in pixels
    These units are convertible, however the conversion ratio changes
    So depending on the distances dealt with in the map, the weight that our guess has changes
    If we're dealing with thousands of miles a few hundred pixels will have some effect
    but if we're dealing with only 10s of miles the guess distance will be incredibly over weighted :(
    */


    public Stack<Link> jeffreyFind(Node startNode, Node endNode) {
        System.out.println("running");
        long startTime = System.currentTimeMillis();
        //setting end node
        this.endNode = endNode;
        //creating and setting guess distance for home node
        PathfinderNode homePathfinderNode = new PathfinderNode(startNode, null, 0, null);
        //adding it to data types
        pathfinderNodeHashtable.put(startNode, homePathfinderNode);
        workingPathfinderNode = homePathfinderNode;

        //search loop, if the end hasn't been found are there is still a possible path
        while (!isEndNodeFound && !noPath) {
            expandNode(workingPathfinderNode);
        }
        //no path handling
        if (noPath) {
            return null;
        }
        //loop through recording path to start
        PathfinderNode workingNode = endPathfinderNode;
        while (workingNode.pointerNode != null) {
            pathStack.add(workingNode.linkToStart);
            workingNode = workingNode.pointerNode;
        }

        System.out.print("Used JeffreyFind to explore ");
        System.out.println(nodesExplored + " nodes to find a path in: " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Path distance of: " + endPathfinderNode.pathToStart + " miles");
        System.out.println();
        return pathStack;
    }

    //done
    private void expandNode(PathfinderNode pathfinderNode) {
        //loops through links leaving the node to be expanded
        Node smallestNode;
        double shortestDistance = Double.MAX_VALUE;
        for (Link link : pathfinderNode.node.awayLinkList) {
            //^^ is the link pointing towards a node that we haven't accessed yet

            //gets the node at the end of the link
            Node edgeNode = link.endNode;
            //checking to see if node had already been accessed
            if (pathfinderNodeHashtable.get(edgeNode) == null) {
                nodesExplored++;
                //checking to see if we found the end node
                if (edgeNode == endNode) {
                    endPathfinderNode = new PathfinderNode(edgeNode, pathfinderNode,
                            pathfinderNode.pathToStart + link.length, link);
                    isEndNodeFound = true;
                    return;
                }
                //creating a new node for each possible path
                if (guessDistance(edgeNode) < shortestDistance) {
                    PathfinderNode newNode = new PathfinderNode(edgeNode, pathfinderNode,
                            pathfinderNode.pathToStart + link.length, link);
                    pathfinderNodeHashtable.put(edgeNode, newNode);
                    workingPathfinderNode = newNode;
                }
            }
        }
    }

    private double guessDistance(Node node) {
        double xDistance = Math.pow(node.x_cord - endNode.x_cord, 2);
        double yDistance = Math.pow(node.y_cord - endNode.y_cord, 2);
        return Math.sqrt(xDistance + yDistance);
    }


    class PathfinderNode {
        public Node node;
        public PathfinderNode pointerNode;
        //^^ points towards start
        public double pathToStart;
        public Double guessDistance;
        public Link linkToStart;
        //NOTE: this link is a reverse link towards home, its start node points towards home


        public PathfinderNode(Node node, PathfinderNode pointerNode, double pathToStart, Link linkToStart) {
            this.node = node;
            this.pointerNode = pointerNode;
            this.pathToStart = pathToStart;
            this.linkToStart = linkToStart;
        }

    }
}
