public enum Heuristics {
    HAMMING,
    MANHATTAN,
    HAMMING_AND_MANHATTAN,
    LINEAR_CONFLICT;

    public static int Heuristic(State newState, Heuristics heuristics)
    {
        return switch (heuristics) {
            case HAMMING -> CalculateHeuristics.Hamming.CalculateHeuristics(newState);
            case MANHATTAN -> CalculateHeuristics.Manhattan.CalculateHeuristics(newState);
            case HAMMING_AND_MANHATTAN -> CalculateHeuristics.HammingAndManhattan.CalculateHeuristics(newState);
            case LINEAR_CONFLICT -> CalculateHeuristics.LinearConflict.CalculateHeuristics(newState);
        };
    }
}

