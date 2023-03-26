package com.sudoku.solver.controllers;

import com.sudoku.solver.services.SudokuSolverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SudokuSolverControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SudokuSolverService sudokuSolverService;

	private String validPuzzleJson;
	private String invalidPuzzleJson;
	private String unsolvablePuzzleJson;

	@BeforeEach
	public void setUp() {
		validPuzzleJson = "{\"puzzle\": [" +
				"[0, 0, 0, 2, 6, 0, 7, 0, 1]," +
				"[6, 8, 0, 0, 7, 0, 0, 9, 0]," +
				"[1, 9, 0, 0, 0, 4, 5, 0, 0]," +
				"[8, 2, 0, 1, 0, 0, 0, 4, 0]," +
				"[0, 0, 4, 6, 0, 2, 9, 0, 0]," +
				"[0, 5, 0, 0, 0, 3, 0, 2, 8]," +
				"[0, 0, 9, 3, 0, 0, 0, 7, 4]," +
				"[0, 4, 0, 0, 5, 0, 0, 3, 6]," +
				"[7, 0, 3, 0, 1, 8, 0, 0, 0]" +
				"]}";


		invalidPuzzleJson = "{\"puzzle\": [" +
				"[5, 5, 0, 0, 7, 0, 0, 0, 0]," +
				"[6, 0, 0, 1, 9, 5, 0, 0, 0]," +
				"[0, 9, 8, 0, 0, 0, 0, 6, 0]," +
				"[8, 0, 0, 0, 6, 0, 0, 0, 3]," +
				"[4, 0, 0, 8, 0, 3, 0, 0, 1]," +
				"[7, 0, 0, 0, 2, 0, 0, 0, 6]," +
				"[0, 6, 0, 0, 0, 0, 2, 8, 0]," +
				"[0, 0, 0, 4, 1, 9, 0, 0, 5]," +
				"[0, 0, 0, 0, 8, 0, 0, 7, 9]" +
				"]}";

		unsolvablePuzzleJson = "{\"puzzle\": [" +
				"[0, 0, 0, 0, 4, 8, 0, 2, 0]," +
				"[0, 0, 0, 0, 1, 9, 0, 0, 7]," +
				"[7, 0, 3, 0, 0, 0, 4, 0, 0]," +
				"[0, 0, 0, 0, 0, 4, 5, 7, 0]," +
				"[1, 8, 0, 0, 0, 0, 0, 3, 9]," +
				"[0, 7, 5, 2, 0, 0, 0, 0, 0]," +
				"[0, 0, 2, 0, 0, 0, 0, 0, 4]," +
				"[3, 0, 0, 9, 7, 0, 0, 0, 6]," +
				"[0, 9, 0, 1, 5, 0, 0, 0, 0]" +
				"]}";


	}

	@Test
	public void testSolve_ValidPuzzle() throws Exception {
		when(sudokuSolverService.solveSudoku(any())).thenReturn(true);

		mockMvc.perform(post("/solve")
						.contentType(MediaType.APPLICATION_JSON)
						.content(validPuzzleJson))
				.andExpect(status().isOk());
	}

	@Test
	public void testSolve_InvalidPuzzle() throws Exception {
		mockMvc.perform(post("/solve")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidPuzzleJson))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("The puzzle is not valid."));
	}

	@Test
	public void testSolve_UnsolvablePuzzle() throws Exception {
		when(sudokuSolverService.solveSudoku(any())).thenReturn(false);

		mockMvc.perform(post("/solve")
						.contentType(MediaType.APPLICATION_JSON)
						.content(unsolvablePuzzleJson))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("The puzzle cannot be solved."));
	}
}