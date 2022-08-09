import java.util.ArrayList;

public class Node implements Comparable<Node>{
    State state;
    Node parent;
    private final int cost;
    private final int depth;

    public Node(State state, Node parent, int cost, int depth) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.depth = depth;
    }
    // Used for the start node
    public Node(State state) {
        this(state,null,0, 0);
    }

    public int getDepth(){
        return this.depth;
    }
    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.cost, o.cost);
    }

    public static Node findNodeWithState(ArrayList<Node> nodeList, State gs) {
        for (Node n : nodeList) {
            if (gs.sameBoard(n.state)) return n;
        }
        return null;
    }
}
