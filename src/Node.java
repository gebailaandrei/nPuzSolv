import java.util.ArrayList;

public class Node implements Comparable<Node>{
    State state;
    Node parent;
    int cost;
    private final int depth;

    // Used for the start node
    public Node(State state) {
        this(state,null, null);
    }

    public Node(State state, Node parent, Heuristics heuristics) {
        this.state = state;
        this.parent = parent;
        depth = (this.parent == null) ? 0 : this.parent.depth + 1;

        if(heuristics == null) {
            cost = 0;
            return;
        }

        if(state.size == 3) {
            switch (heuristics){
                case HAMMING ->                     cost = (int) Math.pow(state.GetHeuristics(), state.size+1) * depth;
                case MANHATTAN, LINEAR_CONFLICT ->  cost = state.GetHeuristics() + depth;
            }
        }else if(state.size == 4) {
            switch (heuristics){
                case HAMMING ->         cost = (int) Math.pow(state.GetHeuristics(), 6) * depth;
                case MANHATTAN ->       cost = (int) Math.pow(state.GetHeuristics(), 2) * depth;
                case LINEAR_CONFLICT -> cost = (int) Math.pow(state.GetHeuristics(), 3) * depth;
            }
        }
    }

    public int getDepth(){
        return this.depth;
    }
    @Override
    public int compareTo(Node o) {
        //return Integer.compare((int)(Math.pow(this.state.GetHeuristics(), 4) * this.depth), (int)(Math.pow(o.state.GetHeuristics(), 4) * o.getDepth()));
        return Integer.compare(this.cost, o.cost);
    }
    // Find state in array of nodes
    public static Node findNodeWithState(ArrayList<Node> nodeList, State gs) {
        for (Node n : nodeList) {
            if (gs.SameBoard(n.state)) return n;
        }
        return null;
    }
}
