import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Solver extends JPanel {
    ArrayList<Node> unexpanded = new ArrayList<>(); // Holds unexpanded node list
    ArrayList<Node> expanded = new ArrayList<>();   // Holds expanded node list
    Node rootNode;                                  // Node representing initial state
    State startState;
    Cell[][] cells;
    int size;

    public Solver(String filename) {
        startState = new State(filename);
        rootNode = new Node(startState);
        this.size = startState.size;
    }
    // This is the method that solves the puzzle using the A* search
    public void Solve(){
        long start = System.nanoTime();
        unexpanded.add(rootNode);
        while (unexpanded.size() > 0) {
            Collections.sort(unexpanded);
            Node n = unexpanded.get(0); // Takes the best node to expand
            expanded.add(n);
            unexpanded.remove(n);
            if (n.state.IsGoal()) {
                System.out.println("Time taken: " + (double) (System.nanoTime() - start) / 1_000_000_000);
                Solution(n);
                return;
            } else {
                ArrayList<State> moveList = n.state.PossibleMoves();
                for (State gs : moveList) {
                    if ((Node.findNodeWithState(unexpanded, gs) == null) &&
                            (Node.findNodeWithState(expanded, gs) == null)) {
                        unexpanded.add(new Node(gs, n, n.getDepth() + 1));
                    }
                }
            }
        }
    }

    //Prints the solution to the file, the console and plays the solution in the GUI
    public void NextMove(Node n){
        if (n.parent != null) NextMove(n.parent);

        try {
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.getStackTrace();
        }

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                cells[i][j].label.setText(Integer.toString(n.state.board[i][j]));

    }

    // Prepares the frame for the GUI
    public void Solution(Node n){
        JFrame frame = new JFrame("Solver");      // Creates the frame for the GUI of the program
        frame.setSize(new Dimension(300, 300));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // This creates a grid in the frame
        JPanel grid = new JPanel(new GridLayout(size, size));
        frame.add(grid);
        cells = new Cell[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                cells[i][j] = new Cell(Integer.toString(startState.startState[i][j]));
                grid.add(cells[i][j]);
            }
        }
        frame.setVisible(true);

        NextMove(n);

        frame.remove(grid);
        frame.setSize(new Dimension(600, 200));
        frame.setLocationRelativeTo(null);
        grid = new JPanel(new GridLayout(3,1));
        frame.add(grid);

        ArrayList<JLabel> labelList = new ArrayList<>();
        labelList.add(new JLabel("Moves: " + n.getDepth(), SwingConstants.CENTER));
        labelList.add(new JLabel("Expanded: " + this.expanded.size(), SwingConstants.CENTER));
        labelList.add(new JLabel("Unexpanded: " + this.unexpanded.size(), SwingConstants.CENTER));

        for(JLabel label : labelList)
        {
            label.setFont(new Font(Font.MONOSPACED,Font.BOLD, 40));
            grid.add(label);
        }

    }

    public static void main(String[] args) {
        Solver solver = new Solver("src\\board.txt");
        System.out.println("Searching...");
        System.out.println("It's going to take a few seconds.");
        solver.Solve();    // Search for and print the solution
    }

}

// This is the class that creates the cells for the puzzle
class Cell extends JPanel{
    Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
    JLabel label;

    Cell(String text){
        setBorder(border);
        label = new JLabel(text, SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(Font.MONOSPACED,Font.BOLD, 40));
        add(label);
    }
}