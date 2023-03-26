package com.sudoku.solver.services;

import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class SudokuSolverService {

	public static final int SIZE = 9;

	public boolean solveSudoku(int[][] puzzle) {
		int row = -1;
		int col = -1;
		boolean isEmpty = true;

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (puzzle[i][j] == 0) {
					row = i;
					col = j;
					isEmpty = false;
					break;
				}
			}
			if (!isEmpty) {
				break;
			}
		}

		if (isEmpty) {
			return true;
		}

		for (int num = 1; num <= SIZE; num++) {
			if (isValidNumber(puzzle, row, col, num)) {
				puzzle[row][col] = num;
				if (solveSudoku(puzzle)) {
					return true;
				} else {
					puzzle[row][col] = 0;
				}
			}
		}
		return false;
	}
	public static boolean isValidNumber(int[][] puzzle, int row, int col, int num) {
		return checkRow(puzzle, row, num) && checkCol(puzzle, col, num) && checkBox(puzzle, row - row % 3, col - col % 3, num);
	}

	private static boolean checkRow(int[][] puzzle, int row, int num) {
		for (int i = 0; i < SIZE; i++) {
			if (puzzle[row][i] == num) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkCol(int[][] puzzle, int col, int num) {
		for (int i = 0; i < SIZE; i++) {
			if (puzzle[i][col] == num) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkBox(int[][] puzzle, int row, int col, int num) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzle[i + row][j + col] == num) {
					return false;
				}
			}
		}
		return true;
	}
}
