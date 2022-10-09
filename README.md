# N Puzzle Solver

## Description
Solves any 3x3 or 4x4 puzzle in a decent amount of time. It can also solve some 5x5 boards but it can take a very long time.
A* search is used for looking for the best solution. There are 3 methods that can be selected for calculating the cost of a move:
- Hamming
- Manhattan
- Linear Search

In order to change the cost calculation method it has to be modified in the main method of the Solver class.

## Visual representation
Solver for 4x4 board:

![4x4 Solver](Solver.gif)

Details after solving:

![4x4 Solver](SolverSS.png)