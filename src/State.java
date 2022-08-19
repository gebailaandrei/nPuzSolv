import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class State {
    int size;
    final int[][] board;

    private int spaceX, spaceY; // The positions of the empty space on the board
    int[][] startState;
    int[][] goalState;
    int h; // This holds the heuristics of the board

    // This constructor is used for the initial state only, and it sets the start and goal states and the size of the board
    public State(String filename) {
        startState = new int[5][5];
        goalState = new int[5][5];
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String s = scanner.nextLine();
            String[] arr = s.split("\\D");
            // Size of the board
            size = Integer.parseInt(arr[1]);

            int z = 3;
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++) {
                    startState[i][j] = Integer.parseInt(arr[z]);
                    goalState[i][j] = z - 2;
                    if (Integer.parseInt(arr[z]) == 0) {
                        this.spaceX = i;
                        this.spaceY = j;
                    } // Location of the empty space
                    z++;
                }
            goalState[size - 1][size - 1] = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.board = startState;
    }
    // This constructor is needed upon creation of a new node
    public State(int[][] board, State gs) {
        this.startState = gs.startState;
        this.goalState = gs.goalState;
        this.size = gs.size;
        this.board = board;
        for (int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if (board[i][j] == 0) {
                    this.spaceX = i;
                    this.spaceY = j;
                    break;
                }
    }
    // Returns clone of the State
    public State clone() {
        int[][] clonedBoard = new int[size][size];
        for(int i = 0; i < size; i++)
            System.arraycopy(this.board[i], 0, clonedBoard[i], 0, size);
        return new State(clonedBoard, this);
    }
    // Checks if this is the goal state
    public boolean IsGoal() {
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++)
                if (this.board[i][j] != goalState[i][j]) return false;
        }
        return true;
    }
    // Checks if 2 given boards are the same
    public boolean SameBoard(State gs) {
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++)
                if (this.board[i][j] != gs.board[i][j]) return false;
        }
        return true;
    }
    // Returns the list of possible moves from the current game state
    public ArrayList<State> PossibleMoves(Heuristics heuristic) {
        ArrayList<State> moves = new ArrayList<>();

        for (int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                if (x != this.spaceX || y != this.spaceY)
                {
                    int distance = Math.abs(this.spaceX - x) + Math.abs(this.spaceY - y);
                    if (distance == 1)
                    {
                        State newState = this.clone();
                        newState.board[this.spaceX][this.spaceY] = this.board[x][y];
                        newState.board[x][y] = 0;
                        newState.spaceX = x;
                        newState.spaceY = y;

                        newState.h = Heuristics.Heuristic(newState, heuristic);


                        moves.add(newState);
                    }
                }
            }
        }
        return moves;
    }

    public int GetHeuristics(){
        return this.h;
    }
}
