# RESTSudokuSolver
SudokuSolver is an application which checks whether a passed Sudoku problem is solvable or not.
In order to run it, you need to create a POST request at /solve on the host of your choice at application.properties file. You also need to pass a 2-dimensional array, containg the sudoku problem.
The application then starts to check whether the given body is valid or not, and after that if it is solvable or not.
If it is, it generates the answer and sends it as a result. If it's not it checks whether the puzzle is invalid or unsolvable, in which cases it will return a 400 error with a message.
The logic behind the solver is simple backtracking. We start with the first number that is not empty and see if its valid or not.
If it is, then we start again with the same logic, while changing the number to a valid one. If we find an unfitting number, then we break, and start from the last point, while changing the value to the next valid one, up until we've tested all variations with that starting number.
Once we find a solution, we simply end all processes and output it as a Requested body.
There are tests with high coverage to test as many as possible cases as to what could occur while running the program itself.
