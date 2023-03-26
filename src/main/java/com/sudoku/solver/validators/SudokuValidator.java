package com.sudoku.solver.validators;

import static com.sudoku.solver.services.SudokuSolverService.isValidNumber;

public class SudokuValidator {
	public static final int SIZE = 9;

	public static boolean isValidPuzzle(int[][] puzzle) {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				int num = puzzle[row][col];
				if (num != 0) {
					puzzle[row][col] = 0;
					if (!isValidNumber(puzzle, row, col, num)) {
						puzzle[row][col] = num;
						return false;
					}
					puzzle[row][col] = num;
				}
			}
		}
		return true;
	}
}
