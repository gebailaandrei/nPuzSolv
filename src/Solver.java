import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Solver extends JPanel {
    ArrayList<Node> unexpanded = new ArrayList<>();
    ArrayList<Node> expanded = new ArrayList<>();
    Node rootNode;      // Node representing the start state
    State startState;
    Cell[][] cells;
    int size;

    public Solver(String filename) {
        startState = new State(filename);
        rootNode = new Node(startState);
        this.size = startState.size;
    }
    // This is the method that solves the puzzle using the A* search
    public void Solve(Heuristics heuristic){
        long start = System.nanoTime();
        unexpanded.add(rootNode);
        while (unexpanded.size() > 0) {
            Collections.sort(unexpanded);
            Node n = unexpanded.get(0); // Node with the lowest cost
            expanded.add(n);
            unexpanded.remove(n);
            if (n.state.IsGoal()) {
                System.out.println("Time taken: " + String.format("%,.2f", ((double) (System.nanoTime() - start) / 1_000_000_000)) + "s.");
                Solution(n);
                return;
            }
            for (State gs : n.state.PossibleMoves(heuristic)) {
                if ((Node.findNodeWithState(unexpanded, gs) == null) &&
                        (Node.findNodeWithState(expanded, gs) == null)) {
                    unexpanded.add(new Node(gs, n, heuristic));
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
            for(int j = 0; j < size; j++) {
                if (n.state.board[i][j] == 0)
                    cells[i][j].setBackground(Color.red);
                else
                    cells[i][j].setBackground(Color.WHITE);
                cells[i][j].label.setText(Integer.toString(n.state.board[i][j]));
            }

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

        grid.add(new JLabel("Moves: " + n.getDepth(), SwingConstants.CENTER)).setFont(new Font(Font.MONOSPACED,Font.BOLD, 40));
        grid.add(new JLabel("Expanded: " + this.expanded.size(), SwingConstants.CENTER)).setFont(new Font(Font.MONOSPACED,Font.BOLD, 40));
        grid.add(new JLabel("Unexpanded: " + this.unexpanded.size(), SwingConstants.CENTER)).setFont(new Font(Font.MONOSPACED,Font.BOLD, 40));
    }

    public static void main(String[] args) {
        Solver solver = new Solver("src\\board.txt");
        System.out.println("Searching...");
        System.out.println("It's going to take a few seconds.");
        solver.Solve(Heuristics.LINEAR_CONFLICT);
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