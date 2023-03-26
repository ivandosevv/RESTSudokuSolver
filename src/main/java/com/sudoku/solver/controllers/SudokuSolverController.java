package com.sudoku.solver.controllers;

import com.sudoku.solver.dtos.Sudoku;
import com.sudoku.solver.services.SudokuSolverService;
import com.sudoku.solver.validators.SudokuValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuSolverController {

	@Autowired
	private SudokuSolverService sudokuSolverService;

	@PostMapping("/solve")
	public ResponseEntity<?> solve(@RequestBody Sudoku sudoku) {
		int[][] puzzle = sudoku.getPuzzle();
		if (puzzle == null || !SudokuValidator.isValidPuzzle(puzzle)) {
			return ResponseEntity.badRequest().body("{\"error\": \"The puzzle is not valid.\"}");
		}
		if (sudokuSolverService.solveSudoku(puzzle)) {
			sudoku.setSolution(puzzle);
			return ResponseEntity.ok(sudoku);
		} else {
			return ResponseEntity.badRequest().body("{\"error\": \"The puzzle cannot be solved.\"}");
		}
	}
}
