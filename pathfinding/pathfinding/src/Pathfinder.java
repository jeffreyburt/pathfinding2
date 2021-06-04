import java.util.*;

public class Pathfinder {
    //stores pathfinder nodes in order to avoid overlaps
    private HashMap<Node, PathfinderNode> pathfinderNodeHashtable = new HashMap<>();
    //stores pathfinder nodes sorted by distance
    private PriorityQueue<PathfinderNode> edgeNodes = new PriorityQueue<>();
    //stores the path from the end node to home
    private Stack<Link> pathStack = new Stack<>();
    private Node endNode;
    private PathfinderNode endPathfinderNode = null;
    private boolean isEndNodeFound = false;
    private boolean noPath = false;
    private int nodesExplored = 1;
    private boolean isAStar;
    public ArrayList<Node> nodeArrayList = new ArrayList<>();

    /*
    note that this isn't truly A*
    normal A* star uses the same units for guess distance and path to start
    However, in this case our path to home is in miles, but our guess distance is in pixels
    These units are convertible, however the conversion ratio changes
    So depending on the distances dealt with in the map, the weight that our guess has changes
    If we're dealing with thousands of miles a few hundred pixels will have some effect
    but if we're dealing with only 10s of miles the guess distance will be incredibly over weighted :(
    */


    public Stack<Link> pathfinder(Node startNode, Node endNode, Boolean isAstar) {
        this.isAStar = isAstar;
        long startTime = System.currentTimeMillis();
        //setting end node
        this.endNode = endNode;
        //creating and setting guess distance for home node
        PathfinderNode homePathfinderNode = new PathfinderNode(startNode, null, 0, null);
        //adding it to data types
        pathfinderNodeHashtable.put(startNode, homePathfinderNode);
        edgeNodes.add(homePathfinderNode);

        //search loop, if the end hasn't been found are there is still a possible path
        while (!isEndNodeFound && !noPath) {
            extendGraph();
        }
        //no path handling
        if (noPath){
         return null;
        }
        //loop through recording path to start
        PathfinderNode workingNode = endPathfinderNode;
        double pathLength = 0;
        while (workingNode.pointerNode != null) {
            pathStack.add(workingNode.linkToStart);
            //todo below line may be useless
            nodeArrayList.add(workingNode.node);
            pathLength += workingNode.linkToStart.length;
            workingNode = workingNode.pointerNode;
        }
        System.out.println("true length to start; " + pathLength);
        if(isAStar){
            System.out.print("Used A* to explore ");
        }else {
            System.out.print("Used Dijkstra's Algorithm to explore ");
        }
        System.out.println( nodesExplored + " nodes to find a path in: " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Path distance of: " + endPathfinderNode.pathToStart + " miles");
        System.out.println();
        return pathStack;
    }

    //done
    private void expandNode(PathfinderNode pathfinderNode) {
        //loops through links leaving the node to be expanded
        for (Link link : (pathfinderNode.node).awayLinkList) {
            //^^ is the link pointing towards a node that we haven't accessed yet

            //gets the node at the end of the link
            Node edgeNode = link.endNode;
            //checking to see if node had already been accessed
            PathfinderNode oldNode = pathfinderNodeHashtable.get(edgeNode);
            if (oldNode == null) {
                nodesExplored ++;
                //creating a new node for each possible path
                PathfinderNode newNode = new PathfinderNode(edgeNode, pathfinderNode,
                        pathfinderNode.pathToStart + link.length, link);
                pathfinderNodeHashtable.put(edgeNode, newNode);
                edgeNodes.add(newNode);
            }else {
                if(pathfinderNode.pathToStart + link.length < oldNode.pathToStart){
                    oldNode.pointerNode = pathfinderNode;
                    oldNode.pathToStart = pathfinderNode.pathToStart + link.length;
                    oldNode.linkToStart = link;
                    edgeNodes.add(oldNode);
                }
            }
        }
    }

    private void extendGraph() {
        //retrieves and deletes the smallest/shortest node from the path
        //todo check endnode here
        PathfinderNode smallestNode = edgeNodes.poll();

        if (smallestNode != null) {
            if (smallestNode.node == endNode) {
                isEndNodeFound = true;
                endPathfinderNode = smallestNode;
                return;
            }
            expandNode(smallestNode);
        } else {
            System.out.println("ERROR: no  path found");
            noPath = true;
        }
    }


    class PathfinderNode implements Comparable<PathfinderNode> {
        public Node node;
        public PathfinderNode pointerNode;
        //^^ points towards start
        public double pathToStart;
        public double guessDistance;
        public Link linkToStart;
        //NOTE: this link is a reverse link towards home, its start node points towards home


        public PathfinderNode(Node node, PathfinderNode pointerNode, double pathToStart, Link linkToStart) {
            this.node = node;
            this.pointerNode = pointerNode;
            this.pathToStart = pathToStart;
            this.linkToStart = linkToStart;
            //^should not include path distance
            if(isAStar){
                double xDistance = Math.pow(node.x_cord - endNode.x_cord, 2);
                double yDistance = Math.pow(node.y_cord - endNode.y_cord, 2);
                guessDistance = (Math.sqrt(xDistance + yDistance) * controller.pixelsToMiles);
            }
        }

        @Override
        public int compareTo(PathfinderNode obj) {
            if(isAStar) {
                return Double.compare(pathToStart + guessDistance, obj.pathToStart + obj.guessDistance);
            }
                return Double.compare(pathToStart, obj.pathToStart);
        }


    }
}
