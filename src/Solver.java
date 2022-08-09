import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Solver extends JPanel {
    ArrayList<Node> unexpanded = new ArrayList<>(); // Holds unexpanded node list
    ArrayList<Node> expanded = new ArrayList<>();   // Holds expanded node list
    Node rootNode;                                  // Node representing initial state
    State initialState;
    int size;

    public Solver(String filename) {
        initialState = new State(filename);
        rootNode = new Node(initialState);
        this.size = initialState.size;
    }
    // This is the method that solves the puzzle using the A* search
    public void solve(){
        unexpanded.add(rootNode);
        while (unexpanded.size() > 0) {
            Collections.sort(unexpanded);
            Node n = unexpanded.get(0); // Takes the best node to expand
            expanded.add(n);
            unexpanded.remove(n);
            // if node is goal show the solution to the puzzle
            // else expand the node
        }
    }


    public static void main(String[] args) {
        Solver solver = new Solver("src\\board.txt");
        //solver.solve();
    }

}