import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class State {
    public int size;
    final int[][] board;

    private int emptyX, emptyY; // The positions of the empty space on the board
    public int[][] startState;
    public int[][] goalState;
    private int h; // This holds the heuristics of the board

    // This constructor is used for the initial state only, and it sets the start and goal states and the size of the board
    public State(String filename) {
        startState = new int[5][5];
        goalState = new int[5][5];
        try{
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String s = scanner.nextLine();
            String[] arr = s.split("\\D");
            // Size of the board
            size = Integer.parseInt(arr[1]);

            int z = 3;
            for(int i = 0; i < size; i++)
                for(int j = 0; j < size; j++) {
                    startState[i][j] = Integer.parseInt(arr[z]);
                    goalState[i][j] = z-2;
                    if(Integer.parseInt(arr[z]) == 0) { this.emptyX = i; this.emptyY = j;} // Location of the empty space
                    z++;
                }
            goalState[size - 1][size - 1] = 0;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        this.board = startState;
    }
    // This constructor is called every time we create a state for a node, assigning all the need values to the member variables
    public State(int[][] board, State gs) {
        this.startState = gs.startState;
        this.goalState = gs.goalState;
        this.size = gs.size;
        this.board = board;
        for (int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if (board[i][j] == 0) {
                    this.emptyX = i;
                    this.emptyY = j;
                    break;
                }
    }
    // Clone returns a new GameState with the same board configuration as the current GameState.
    public State clone() {
        int[][] clonedBoard = new int[size][size];
        //System.arraycopy(this.board, 0, clonedBoard, 0, this.board.length);
        // We clone the board element by element
        for(int i = 0; i < size; i++)
            if (size >= 0) System.arraycopy(this.board[i], 0, clonedBoard[i], 0, size);
        return new State(clonedBoard, this);            // instead of using the arraycopy() method
    }
    // Checks if this is the goal state
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++)
                if (this.board[i][j] != goalState[i][j]) return false;
        }
        return true;
    }
    // Checks if 2 given boards are the same
    public boolean sameBoard (State gs) {
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++)
                if (this.board[i][j] != gs.board[i][j]) return false;
        }
        return true;
    }
    // Returns the list of possible moves from the current game state
    public ArrayList<State> possibleMoves() {
        ArrayList<State> moves = new ArrayList<>();

        return moves;
    }
    public void calculateHeuristics(){

    }
}