public enum Heuristics {
    HAMMING,
    MANHATTAN,
    LINEAR_CONFLICT;

    public static int Heuristic(State newState, Heuristics heuristics)
    {
        return switch (heuristics) {
            case HAMMING -> CalculateHeuristics.Hamming.CalculateHeuristics(newState);
            case MANHATTAN -> CalculateHeuristics.Manhattan.CalculateHeuristics(newState);
            case LINEAR_CONFLICT -> CalculateHeuristics.LinearConflict.CalculateHeuristics(newState);
        };
    }
}

