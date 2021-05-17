import java.util.LinkedList;

public class LinkPair {
    public Link link1;
    public Link link2;
    //^^reverse link
    public double linkID;
    public LinkedList<Coordinate> coordinateLinkedList;
    //note should start with end node

    public void assignWaypoints(LinkedList<LinkPair.Coordinate> linkedList){
        linkedList.addLast(new Coordinate(link1.startNode.x_cord,link1.startNode.y_cord));
        linkedList.addFirst(new Coordinate(link1.endNode.x_cord, link1.endNode.y_cord));
        coordinateLinkedList = linkedList;

    }
    class Coordinate{
        public int xCord;
        public int yCord;
        public Coordinate(int x, int y){
            xCord = x;
            yCord = y;
        }
    }
}
