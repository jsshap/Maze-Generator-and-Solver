import java.util.Random;

public class MazeGenerator {

    public void run(int n, boolean solve) {

		// creates all cells
		Cell[][] mazeMap = new Cell[n][n];
		initializeCells(mazeMap);

		// create a list of all internal walls, and links the cells and walls
		Wall[] walls = getWalls(mazeMap);

		createMaze(walls, mazeMap);
		if (solve){
		 Solver.solve(mazeMap, walls);
		}
		printMaze(mazeMap);

	}

	public void createMaze(Wall[] walls, Cell[][] mazeMap) {
		// FILL IN THIS METHOD
		for (int i = 0; i < mazeMap.length; i++) {
			for (int j = 0; j < mazeMap[0].length; j++) {
				UnionFind.makeset(mazeMap[i][j]);
			}
		}
		Random rand = new Random();
		mazeMap[0][0].left.visible = false;
		mazeMap[mazeMap.length - 1][mazeMap[mazeMap.length - 1].length - 1].right.visible = false;
		int wallsRemoved = 0;
		while (wallsRemoved < mazeMap.length * mazeMap.length - 1) {
			int i = rand.nextInt((walls.length - 1));
			Cell cell1 = walls[i].first;
			Cell cell2 = walls[i].second;
			if (!(cell1 == null || cell2 == null)) {
				if (!(UnionFind.find(cell1).equals(UnionFind.find(cell2)))) {
					walls[i].visible = false;
					wallsRemoved++;
					UnionFind.union(cell1, cell2);
				}
			}
			// BELOW IS FOR DEBUGGING
			/*
			 * for (int k=0; k<mazeMap.length; k++) { for (int j=0; j<mazeMap.length; j++){
			 * System.out.printf("%3d", UnionFind.find(mazeMap[k][j]).id); }
			 * System.out.println(); } System.out.println("\n\n");
			 */
		}
	}

	// print out the maze in a specific format
	public void printMaze(Cell[][] maze) {
		System.out.println(maze.length);
		for (int i = 0; i < maze.length; i++) {
			// print the up walls for row i
			for (int j = 0; j < maze.length; j++) {
				Wall up = maze[i][j].up;
				if (up != null && up.visible)
					System.out.print("+--");
				else if (up.first.inSolution && up.second.inSolution)
					System.out.print("+ *");
				else
					System.out.print("+  ");
			}
			System.out.println("+");

			// print the left walls and the cells in row i
			for (int j = 0; j < maze.length; j++) {
				Wall left = maze[i][j].left;
				if (left != null && left.visible && maze[i][j].inSolution)
					System.out.print("| *");
				else if (maze[i][j].inSolution)
					System.out.print("  *");
				else if (left != null && left.visible && !maze[i][j].inSolution)
					System.out.print("|  ");
				else if (i==0 && j==0 && maze[i][j].inSolution)
					System.out.print("  *");
				else
					System.out.print("   ");
			}

			// print the last wall on the far right of row i
			Wall lastRight = maze[i][maze.length - 1].right;
			if (lastRight != null && lastRight.visible)
				System.out.println("|");
			else
				System.out.println(" ");
		}

		// print the last row's down walls
		for (int i = 0; i < maze.length; i++) {
			Wall down = maze[maze.length - 1][i].down;
			if (down != null && down.visible)
				System.out.print("+--");
			else
				System.out.print("+  ");
		}
		System.out.println("+");

	}

	// create a new Cell for each position of the maze
	public void initializeCells(Cell[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				maze[i][j] = new Cell();
			}
		}
	}

	// create all walls and link walls and cells
	public Wall[] getWalls(Cell[][] mazeMap) {

		int n = mazeMap.length;

		Wall[] walls = new Wall[2 * n * (n + 1)];
		int wallCtr = 0;

		// each "inner" cell adds its right and down walls
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				// add down wall
				if (i < n - 1) {
					walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i + 1][j]);
					mazeMap[i][j].down = walls[wallCtr];
					mazeMap[i + 1][j].up = walls[wallCtr];
					wallCtr++;
				}

				// add right wall
				if (j < n - 1) {
					walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i][j + 1]);
					mazeMap[i][j].right = walls[wallCtr];
					mazeMap[i][j + 1].left = walls[wallCtr];
					wallCtr++;
				}
			}
		}

		// "outer" cells add their outer walls
		for (int i = 0; i < n; i++) {
			// add left walls for the first column
			walls[wallCtr] = new Wall(null, mazeMap[i][0]);
			mazeMap[i][0].left = walls[wallCtr];
			wallCtr++;
			// add up walls for the top row
			walls[wallCtr] = new Wall(null, mazeMap[0][i]);
			mazeMap[0][i].up = walls[wallCtr];
			wallCtr++;

			// add down walls for the bottom row
			walls[wallCtr] = new Wall(null, mazeMap[n - 1][i]);
			mazeMap[n - 1][i].down = walls[wallCtr];
			wallCtr++;

			// add right walls for the last column
			walls[wallCtr] = new Wall(null, mazeMap[i][n - 1]);
			mazeMap[i][n - 1].right = walls[wallCtr];
			wallCtr++;
		}

		return walls;
	}

	public static void main(String[] args) {
	    boolean solve = false;
		if (args.length > 0) {
			int n = Integer.parseInt(args[0]);
			if (args.length>1){
			    solve = Boolean.parseBoolean(args[1]);
			}
			else {
			    solve=false;
			}
			new MazeGenerator().run(n, solve);
		} else
		    new MazeGenerator().run(5, false);
	}

}
