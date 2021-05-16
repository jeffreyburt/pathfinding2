import java.util.List;

public class Link {
    public int linkID;
    public Node startNode;
    public Node endNode;
    public String name;
    public double length;

    public Link(int linkID, Node startNode, Node endNode, String name, double length){
        this.linkID = linkID;
        this.startNode = startNode;
        this.endNode = endNode;
        this.name = name;
        this.length = length;
    }
}

