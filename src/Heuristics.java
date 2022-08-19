public enum Heuristics {
    Hamming,
    Manhattan,
    LinearConflict;

    public static int Heuristic(State newState, Heuristics heuristics)
    {
        switch (heuristics){
            case Hamming: return Hamming(newState);
            case Manhattan: return Manhattan(newState);
            case LinearConflict: return LinearConflict(newState);
            default: return 0;
        }
    }

    public static int Hamming(State newState)
    {
        int misplaced = 0;
        for (int i = 0; i < newState.size; i++) {
            for (int j = 0; j < newState.size; j++) {
                if(newState.goalState[i][i] != newState.board[i][j])
                    misplaced++;
            }
        }
        return misplaced;
    }

    public static int Manhattan(State newState)
    {
        for (int i = 0; i < newState.size; i++) {
            for (int j = 0; j < newState.size; j++) {
                for (int x = 0; x < newState.size; x++) {
                    boolean found = false;
                    for (int y = 0; y < newState.size; y++) {
                        if (newState.goalState[x][y] == newState.board[i][j]) {
                            newState.h += Math.abs(x - i) + Math.abs(y - j);
                            found = true;
                            break;
                        }
                    }
                    if(found) break;
                }
            }
        }
        return newState.h;
    }

    public static int LinearConflict(State newState)
    {
        int[] uniBoard = new int[newState.size * newState.size];
        int m = 0;
        for(int i = 0; i < newState.size; i++){
            for(int j = 0; j < newState.size; j++) {
                uniBoard[m] = newState.board[i][j];
                m++;
            }
        }
        int conflicts = 0;
        for(int l = 0; l < newState.size; l++){
            for(int c = 0; c < newState.size; c++){
                int index = l * newState.size + c;
                if(uniBoard[index] == 0)
                    continue;
                int a = (uniBoard[index] - 1) % newState.size, b = (int)Math.floor((uniBoard[index] - 1) / newState.size);
                if(c == a && l == b)
                    continue;

                if(c == a)
                {
                    for(int i = Math.min(b, l); i < Math.max(b, l); i++)
                    {
                        int col = i * newState.size + c;
                        int currCellX = (uniBoard[col]-1) % newState.size;
                        if(currCellX == c) conflicts++;
                    }
                }else if(l == b)
                {
                    for(int j = Math.min(a, c); j < Math.max(a, c); j++)
                    {
                        int lin = l * newState.size + j;
                        int currCellY = (int)Math.floor((uniBoard[lin]-1) / newState.size);
                        if(currCellY == l) conflicts++;
                    }
                }
            }
        }
        return Manhattan(newState) + conflicts * 2;
    }
}

