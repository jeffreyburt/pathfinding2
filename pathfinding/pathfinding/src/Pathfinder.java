import java.util.*;

public class Pathfinder {
    private HashMap<Node, PathfinderNode> pathfinderNodeHashtable = new HashMap<>();
    private PriorityQueue<PathfinderNode> edgeNodes = new PriorityQueue<>();
    private Stack<Link> pathStack = new Stack<>();
    private Node endNode;
    private PathfinderNode endPathfinderNode;
    private boolean isEndNodeFound = false;
    private boolean noPath = false;
    private int nodesExplored = 1;
    private boolean isAStar;

    //todo questions for Andrew
    //A* and normal search algorithm explore the same amount of nodes
    //lots of no path founds
    //just seems a bit inefficient

    public Stack<Link> pathfinder(Node startNode, Node endNode, Boolean isAstar) {
        this.isAStar = isAstar;
        long startTime = System.currentTimeMillis();
        //setting end node
        this.endNode = endNode;
        //creating and setting guess distance for home node
        PathfinderNode homePathfinderNode = new PathfinderNode(startNode, null, 0, null, endNode);
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
        while (workingNode.pointerNode != null) {
            pathStack.add(workingNode.linkToStart);
            workingNode = workingNode.pointerNode;
        }
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
        for (Link link : pathfinderNode.node.awayLinkList) {
            //^^ is the link pointing towards a node that we haven't accessed yet

            //gets the node at the end of the link
            Node edgeNode = link.endNode;
            //checking to see if node had already been accessed
            if (pathfinderNodeHashtable.get(edgeNode) == null) {
                nodesExplored ++;
                //checking to see if we found the end node
                if (edgeNode == endNode) {
                    endPathfinderNode = new PathfinderNode(edgeNode, pathfinderNode,
                            pathfinderNode.pathToStart + link.length, link, endNode);
                    isEndNodeFound = true;
                    return;
                }
                //creating a new node for each possible path
                PathfinderNode newNode = new PathfinderNode(edgeNode, pathfinderNode,
                        pathfinderNode.pathToStart + link.length, link, endNode);
                pathfinderNodeHashtable.put(edgeNode, newNode);
                edgeNodes.add(newNode);
            }
        }
    }

    private void extendGraph() {
        //retrieves and deletes the smallest/shortest node from the path
        PathfinderNode smallestNode = edgeNodes.poll();
        if (smallestNode != null) {
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
        public Double guessDistance;
        public Link linkToStart;
        //NOTE: this link is a reverse link towards home, its start node points towards home


        public PathfinderNode(Node node, PathfinderNode pointerNode, double pathToStart, Link linkToStart, Node endnodde) {
            this.node = node;
            this.pointerNode = pointerNode;
            this.pathToStart = pathToStart;
            this.linkToStart = linkToStart;
            //^should not include path distance
            if(isAStar){
                double xDistance = Math.pow(node.x_cord - endNode.x_cord, 2);
                double yDistance = Math.pow(node.y_cord - endNode.y_cord, 2);
                guessDistance = (Math.sqrt(xDistance + yDistance));
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
