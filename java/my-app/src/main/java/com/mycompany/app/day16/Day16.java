package com.mycompany.app.day16;

import java.util.List;
import com.mycompany.app.utils.InputReader;

public class Day16 {
	public static void main(String[] args) {
		try {
			new Day16().solveB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void solveA() throws InterruptedException {
		char[][] grid = readInput();
		System.out.println(printGrid(grid));

		int[][][] visited = getVisited(grid);
		step(grid, visited, 0, 0, 'r');

		System.out.println(countEnergised(visited));
	}

	public void solveB() throws InterruptedException {
		char[][] grid = readInput();
		System.out.println(printGrid(grid));


		int max = 0;
		for (int i = 0; i < grid.length; i++) {
			System.out.println(i);
			int[][][] visited = getVisited(grid);
			step(grid, visited, i, 0, 'r');
			int e = countEnergised(visited);
			if (e > max) {
				max = e;
			}

		}

		for (int i = 0; i < grid.length; i++) {
			System.out.println(i);
			int[][][] visited = getVisited(grid);
			step(grid, visited, i, grid[0].length-1, 'l');
			int e = countEnergised(visited);
			if (e > max) {
				max = e;
			}

		}

		for (int i = 0; i < grid[0].length; i++) {
			System.out.println(i);
			int[][][] visited = getVisited(grid);
			step(grid, visited, 0, i, 'd');
			int e = countEnergised(visited);
			if (e > max) {
				max = e;
			}

		}

		for (int i = 0; i < grid[0].length; i++) {
			System.out.println(i);
			int[][][] visited = getVisited(grid);
			step(grid, visited, grid.length-1, i, 'u');
			int e = countEnergised(visited);
			if (e > max) {
				max = e;
			}

		}

		System.out.println(max);
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

	void step(char[][] grid, int[][][] visited, int y, int x, char dir) throws InterruptedException {
		Thread.sleep(1);
		if ((y >= grid.length) || (y < 0) || (x >= grid[0].length) || (x < 0)) {
			return;
		}
		if (visited[y][x][dirToInt(dir)] == 1) {
			return;
		}
		visited[y][x][dirToInt(dir)] = 1;

		char c = grid[y][x];

		if (c == '.') {
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

			step(grid, visited, nextY, nextX, dir);
			return;
		} else if (c == '/') {
			char newDir;
			int nextY;
			int nextX;
			if (dir == 'r') {
				newDir = 'u';
				nextY = y-1;
				nextX = x;
			} else if (dir == 'l') {
				newDir = 'd';
				nextY = y+1;
				nextX = x;
			} else if (dir == 'u') {
				newDir = 'r';
				nextY = y;
				nextX = x+1;
			} else {
				newDir = 'l';
				nextY = y;
				nextX = x-1;
			}

			step(grid, visited, nextY, nextX, newDir);
			return;
		} else if (c == '\\') {
			char newDir;
			int nextY;
			int nextX;
			if (dir == 'r') {
				newDir = 'd';
				nextY = y+1;
				nextX = x;
			} else if (dir == 'l') {
				newDir = 'u';
				nextY = y-1;
				nextX = x;
			} else if (dir == 'u') {
				newDir = 'l';
				nextY = y;
				nextX = x-1;
			} else {
				newDir = 'r';
				nextY = y;
				nextX = x+1;
			}

			step(grid, visited, nextY, nextX, newDir);
			return;
		} else if (c == '-') {
			if ((dir == 'r') || (dir == 'l')) {
				int nextY = y;
				int nextX;
				if (dir == 'r') {
					nextX = x+1;
				} else {
					nextX = x-1;
				}
				step(grid, visited, nextY, nextX, dir);
				return;
			} else {
				step(grid, visited, y, x-1, 'l'); 
				step(grid, visited, y, x+1, 'r');
				return;
			}
		} else {
			if ((dir == 'u') || (dir == 'd')) {
				int nextY;
				int nextX = x;
				if (dir == 'u') {
					nextY = y-1;
				} else {
					nextY = y+1;
				}
				step(grid, visited, nextY, nextX, dir);
				return;
			} else {
				step(grid, visited, y-1, x, 'u');
				step(grid, visited, y+1, x, 'd');
				return;
			}

		}

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

	int countEnergised(int[][][] grid) {
		int sum = 0;
		for (int[][] line : grid) {
			for (int[] c : line) {
				int s = 0;
				for (int i : c) {
					if (i > 0) {
						s += 1;
					}
				}
				if (s > 0) {
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
				if (sum > 0) {
					s += "#";
				} else {
					s += ".";
				}
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
