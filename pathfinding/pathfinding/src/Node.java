import java.util.ArrayList;

public class Node {
    public int nodeID;
    public int x_cord;
    public int y_cord;
    public ArrayList<Link> awayLinkList = new ArrayList<>();
    public ArrayList<Link> towardsLinkList = new ArrayList<>();


    public Node(int id, int x_cord, int y_cord){
        nodeID = id;
        this.x_cord = x_cord;
        this.y_cord = y_cord;
    }
}
