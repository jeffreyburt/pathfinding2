import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

public class Pathfinder {
    private HashMap<Node, PathfinderNode> pathfinderNodeHashtable = new HashMap<>();
    private LinkedList<PathfinderNode> edgeNodes = new LinkedList<>();
    //todo change to binary search tree???
//    wait or maybe change it to a priority queue
//    Changing this is going to involve changing how I shortest possible paths
    private Stack<Link> pathStack= new Stack<>();
    private Node endNode;
    private PathfinderNode endPathfinderNode;
    private boolean isEndNodeFound = false;

    public Stack<Link> pathfinder(Node startNode, Node endNode){
        PathfinderNode homePathfinderNode = new PathfinderNode(startNode, null, 0, null);
        edgeNodes.add(homePathfinderNode);
        this.endNode = endNode;
        while (!isEndNodeFound){
            extendGraph();
        }
        //todo where to send final path/how to store it
        PathfinderNode workingNode = endPathfinderNode;
        while (workingNode.pointerNode != null) {
            pathStack.add(workingNode.linkToStart);
            workingNode = workingNode.pointerNode;
        }
        return pathStack;
    }

    //done
    private void expandNode( PathfinderNode pathfinderNode){
        for (Link link: pathfinderNode.node.awayLinkList) {
            //^^ is the link pointing towards a node that we haven't accessed yet
            Node edgeNode = link.endNode;
            if(pathfinderNodeHashtable.get(edgeNode) == null){
                //todo do I really need this^^
                if (edgeNode == endNode){
                    endPathfinderNode = new PathfinderNode(edgeNode, pathfinderNode,
                            pathfinderNode.pathToStart + link.length, link);
                    isEndNodeFound = true;
                    return;
                }
                PathfinderNode newNode = new PathfinderNode(edgeNode, pathfinderNode,
                        pathfinderNode.pathToStart + link.length, link);
                pathfinderNodeHashtable.put(edgeNode, newNode);
                edgeNodes.add(newNode);

                    double xDistance = (newNode.node.x_cord - endNode.x_cord) ^ 2;
                    double yDistance = (newNode.node.y_cord - endNode.y_cord) ^ 2;
                    //todo can this be negative?
                    newNode.guessDistance = (Math.sqrt(xDistance + yDistance)) + newNode.pathToStart;
            }
        }
        edgeNodes.remove(pathfinderNode);
    }

    private void extendGraph(){
        PathfinderNode smallestNode = null;
        double minDistance = Double.MAX_VALUE;
        for (PathfinderNode nodee: edgeNodes) {
            if (nodee.guessDistance == null){
                double xDistance = Math.pow(nodee.node.x_cord - endNode.x_cord, 2);
                double yDistance = Math.pow(nodee.node.y_cord - endNode.y_cord, 2);
                nodee.guessDistance = Math.sqrt(xDistance + yDistance);
            }

            if(nodee.guessDistance <= minDistance){
                minDistance = nodee.guessDistance;
                smallestNode = nodee;
            }
        }
        if (smallestNode != null) {
            expandNode(smallestNode);
        } else {
            System.out.println("ERROR: no  path found");
        }
    }


    class PathfinderNode implements Comparable{
        public Node node;
        public PathfinderNode pointerNode;
        //^^ points towards start
        public double pathToStart;
        public Double guessDistance;
        public Link linkToStart;
        //NOTE: this link is a reverse link towards home, its start node points towards home



        public PathfinderNode(Node node, PathfinderNode pointerNode, double pathToStart, Link linkToStart){
            this.node = node;
            this.pointerNode = pointerNode;
            this.pathToStart = pathToStart;
            this.linkToStart = linkToStart;
        }

        @Override
        //todo double check this
        public int compareTo(PathfinderNode obj) {
            if((obj.pathToStart + obj.guessDistance) == (pathToStart + guessDistance)){
                return 0;
            }else if ((obj.pathToStart + obj.guessDistance) < (pathToStart + guessDistance)){
                return 1;
                //todo check this^^
            }else {
                return -1;
            }
        }

        //todo need to add a compare to function for sorting in heap tree
    }
}
