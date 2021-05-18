import java.util.*;

public class Pathfinder {
    private HashMap<Node, PathfinderNode> pathfinderNodeHashtable = new HashMap<>();
    private PriorityQueue<PathfinderNode> edgeNodes = new PriorityQueue<>();
    private Stack<Link> pathStack = new Stack<>();
    private Node endNode;
    private PathfinderNode endPathfinderNode;
    private boolean isEndNodeFound = false;

    public Stack<Link> pathfinder(Node startNode, Node endNode) {
        PathfinderNode homePathfinderNode = new PathfinderNode(startNode, null, 0, null, null);
        double xDistance = (homePathfinderNode.node.x_cord - endNode.x_cord) ^ 2;
        double yDistance = (homePathfinderNode.node.y_cord - endNode.y_cord) ^ 2;
        homePathfinderNode.guessDistance = (Math.sqrt(xDistance + yDistance));
        pathfinderNodeHashtable.put(startNode, homePathfinderNode);
        edgeNodes.add(homePathfinderNode);
        this.endNode = endNode;
        while (!isEndNodeFound) {
            extendGraph();
        }
        PathfinderNode workingNode = endPathfinderNode;
        while (workingNode.pointerNode != null) {
            pathStack.add(workingNode.linkToStart);
            workingNode = workingNode.pointerNode;
        }
        return pathStack;
    }

    //done
    private void expandNode(PathfinderNode pathfinderNode) {
        for (Link link : pathfinderNode.node.awayLinkList) {
            //^^ is the link pointing towards a node that we haven't accessed yet
            Node edgeNode = link.endNode;
            if (pathfinderNodeHashtable.get(edgeNode) == null) {
                //checking to see if node had already been accessed
                if (edgeNode == endNode) {
                    endPathfinderNode = new PathfinderNode(edgeNode, pathfinderNode,
                            pathfinderNode.pathToStart + link.length, link, pathfinderNode.pathToStart + link.length);
                    isEndNodeFound = true;
                    return;
                }

                PathfinderNode newNode = new PathfinderNode(edgeNode, pathfinderNode,
                        pathfinderNode.pathToStart + link.length, link, null);
                pathfinderNodeHashtable.put(edgeNode, newNode);
                edgeNodes.add(newNode);
                double xDistance = (newNode.node.x_cord - endNode.x_cord) ^ 2;
                double yDistance = (newNode.node.y_cord - endNode.y_cord) ^ 2;
                newNode.guessDistance = (Math.sqrt(xDistance + yDistance));
            }
        }
    }

    private void extendGraph() {
        PathfinderNode smallestNode = edgeNodes.poll();
        if (smallestNode != null) {
            expandNode(smallestNode);
        } else {
            System.out.println("ERROR: no  path found");
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


        public PathfinderNode(Node node, PathfinderNode pointerNode, double pathToStart, Link linkToStart, Double guessDistance) {
            this.node = node;
            this.pointerNode = pointerNode;
            this.pathToStart = pathToStart;
            this.linkToStart = linkToStart;
            this.guessDistance = guessDistance;
            //^should not include path distance
        }

        @Override
        public int compareTo(PathfinderNode obj) {
            return Double.compare(pathToStart + guessDistance, obj.pathToStart + obj.guessDistance);
        }


    }
}
