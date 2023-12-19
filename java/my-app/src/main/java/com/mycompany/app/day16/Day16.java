package com.mycompany.app.day16;

import java.util.List;

import com.mycompany.app.utils.InputReader;

public class Day16 {
	public static void main(String[] args) {
		new Day16().solveA();
	}

	public void solveA() {
		char[][] grid = readInput();
		System.out.println(printGrid(grid));

		int[][] energised = getEnergised(grid);
		int[][][] visited = getVisited(grid);
		int steps = step(grid, energised, visited, 0, 0, 'r');
		System.out.println("steps " + steps);

		System.out.println(printIntGrid(energised));
		System.out.println(printDistinctDirections(visited));
		System.out.println(countEnergised(energised));
	}

	int dirToInt(char dir) {
		if (dir == 'r') {
			return 0;
		} else if (dir == 'l') {
			return 1;
		} else if (dir == 'u') {
			return 2;
		} else {
			return 3;
		}
	}

	int step(char[][] grid, int[][] energised, int[][][] visited, int y, int x, char dir) {
		if (visited[y][x][dirToInt(dir)] > 0) {
			return 0;
		}
		visited[y][x][dirToInt(dir)] = 1;

		System.out.println(String.format("Stepping %s %s %s", y, x, dir));
		energised[y][x] += 1;
		int nextY;
		int nextX;
		if (dir == 'r') {
			nextY = y;
			nextX = x+1;
		} else if (dir == 'l') {
			nextY = y;
			nextX = x-1;
		} else if (dir == 'u') {
			nextY = y-1;
			nextX = x;
		} else {
			nextY = y+1;
			nextX = x;
		}

		if ((nextY >= grid.length) || (nextY < 0) || (nextX >= grid[0].length) || (nextX < 0)) {
			System.out.println("out of bounds");
			return 0;
		}

		char next = grid[nextY][nextX];
		if (next == '.') {
			return 1 + step(grid, energised, visited, nextY, nextX, dir);
		} else if (next == '/') {
			char newDir;
			if (dir == 'r') {
				newDir = 'u';
			} else if (dir == 'l') {
				newDir = 'd';
			} else if (dir == 'u') {
				newDir = 'r';
			} else {
				newDir = 'l';
			}

			return 1 + step(grid, energised, visited, nextY, nextX, newDir);
		} else if (next == '\\') {
			char newDir;
			if (dir == 'r') {
				newDir = 'd';
			} else if (dir == 'l') {
				newDir = 'u';
			} else if (dir == 'u') {
				newDir = 'l';
			} else {
				newDir = 'r';
			}

			return 1 + step(grid, energised, visited, nextY, nextX, newDir);
		} else if (next == '-') {
			if ((dir == 'r') || (dir == 'l')) {
				return 1 + step(grid, energised, visited, nextY, nextX, dir);
			} else {
				System.out.println("splitting on -");
				return 1 + step(grid, energised, visited, nextY, nextX, 'l') + 
					step(grid, energised, visited, nextY, nextX, 'r');
			}
		} else {
			if ((dir == 'u') || (dir == 'd')) {
				return 1 + step(grid, energised, visited, nextY, nextX, dir);
			} else {
				System.out.println("splitting on |");
				return 1 + step(grid, energised, visited, nextY, nextX, 'u') + 
					step(grid, energised, visited, nextY, nextX, 'd');
			}

		}
	}

	int[][] getEnergised(char[][] grid) {
		int[][] energised = new int[grid.length][grid[0].length];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				energised[y][x] = 0;
			}
		}
		return energised;
	}

	int[][][] getVisited(char[][] grid) {
		int[][][] visited = new int[grid.length][grid[0].length][4];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				for (int j = 0; j < 4; j++) {
					visited[y][x][j] = 0;

				}
			}
		}
		return visited;
	}

	int[][][] copy(int[][][] grid) {
		int[][][] newGrid = new int[grid.length][grid[0].length][grid[0][0].length];

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				for (int j = 0; j < grid[0][0].length; j++) {
					newGrid[y][x][j] = grid[y][x][j];
				}

			}
		}

		return newGrid;
	}

	String printGrid(char[][] grid) {
		String s = "";
		for (char[] line : grid) {
			for (char c : line) {
				s += c;
			}
			s += "\n";
		}
		return s;
	}

	String printIntGrid(int[][] grid) {
		String s = "";
		for (int[] line : grid) {
			for (int c : line) {
				s += String.valueOf(c);
			}
			s += "\n";
		}
		return s;
	}

	int countEnergised(int[][] grid) {
		int sum = 0;
		for (int[] line : grid) {
			for (int c : line) {
				if (c != 0) {
					sum += 1;
				}
			}
		}
		return sum;
	}

	String printDistinctDirections(int[][][] grid) {
		String s = "";
		for (int[][] line : grid) {
			for (int[] c : line) {
				int sum = 0;
				for (int i : c) {
					if (i != 0) {
						sum += 1;
					}
				}
				s += String.valueOf(sum);
			}
			s += "\n";
		}
		return s;
	}


	char[][] readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day16/input.txt");
		int height = lines.size();
		int width = lines.get(0).length();
		
		char[][] grid = new char[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				grid[y][x] = lines.get(y).charAt(x);
			}
		}

		return grid;
	}
}
