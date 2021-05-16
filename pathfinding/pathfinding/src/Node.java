import java.util.ArrayList;

public class Node {
    public int nodeID;
    public int x_cord;
    public int y_cord;
    public ArrayList<Link> awayLinkList;
    public ArrayList<Link> towardsLinkList;
    public Pathfinder.PathfinderNode pathfinderNode;
    //todo store the data here or in a hash table in the pathfinding instance
    // basically just slightly more memory efficient

    public Node(int id, int x_cord, int y_cord){
        nodeID = id;
        this.x_cord = x_cord;
        this.y_cord = y_cord;
    }
}
