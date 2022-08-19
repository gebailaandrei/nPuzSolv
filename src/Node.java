import java.util.ArrayList;

public class Node implements Comparable<Node>{
    State state;
    Node parent;
    int cost;
    private final int depth;

    // Used for the start node
    public Node(State state) {
        this(state,null, 0, null);
    }

    public Node(State state, Node parent, int depth, Heuristics heur) {
        this.state = state;
        this.parent = parent;
        this.depth = depth;
        // if(manhattan heuristics) do this
        if(state.size == 3) {
            if(heur == Heuristics.Hamming)
                cost = (int) Math.pow(state.GetHeuristics(), 4) * depth;
            else if (heur == Heuristics.Manhattan)
                cost = state.GetHeuristics() * depth;
            else if (heur == Heuristics.LinearConflict)
                cost = state.GetHeuristics() + depth;
        }else if(state.size == 4 || state.size == 5) {
            if(heur == Heuristics.Hamming)
                cost = (int) Math.pow(state.GetHeuristics(), 4) * depth;
            else if(heur == Heuristics.Manhattan)
                cost = (int) Math.pow(state.GetHeuristics(), 4) * depth;
            else if(heur == Heuristics.LinearConflict)
                cost = (int) Math.pow(state.GetHeuristics(), 2) * depth;
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
