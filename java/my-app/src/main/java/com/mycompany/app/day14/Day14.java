package com.mycompany.app.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.app.utils.InputReader;

public class Day14 {
	private int height;
	private int width;

	public static void main(String[] args) {
		new Day14().solveB();
	}

	public void solveA() {
		char[][] grid = readInput();

		int moves = 1;
		while (moves != 0) {
			moves = moveOneNorth(grid);
		}

		System.out.println(printGrid(grid));
		int load = getLoad(grid);
		System.out.println(load);
	}


	public void solveB() {
		Map<String, String> state = new HashMap<>();
		Map<String, Integer> steps = new HashMap<>();

		char[][] grid = readInput();

		height = grid.length;
		width = grid[0].length;

		int cycles = 1000000000;
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < cycles; i++) {
			System.out.print(((float)i) / ((float)cycles) + "\r");

			String startStateString = gridToState(grid);
			String stateString = null;
			int j = 0;
			int iStart = i;
			if (state.containsKey(startStateString)) {
				j++;
				stateString = state.get(startStateString);
				while (state.containsKey(stateString) && (!(stateString.equals(startStateString)))) {
					list.add(getLoad(stateToGrid(stateString)));
					stateString = state.get(stateString);
					j++;
					i++;
				}
				list.add(getLoad(stateToGrid(stateString)));
				System.out.println("breaking cycle starting at " + iStart + " of length " + j);
				System.out.println(list);
				return;
			} else {
				stateString = startStateString;
			}

			grid = stateToGrid(stateString);

			char[][] oldGrid = copy(grid);
			cycle(grid);
			state.put(gridToState(oldGrid), gridToState(grid));
			//list.add(getLoad(grid));
		}

		int load = getLoad(grid);
		System.out.println(load);
	}

	String gridToState(char[][] grid) {
		String s = "";
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				s += grid[y][x];
			}
		}
		return s;
	}

	char[][] stateToGrid(String state) {
		char[][] grid = new char[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				grid[y][x] = state.charAt(y*width + x);
			}
		}
		return grid;
	}

	void cycle(char[][] grid) {
		int moves;

		moves = 1;
		while (moves != 0) {
			moves = moveOneNorth(grid);
		}

		moves = 1;
		while (moves != 0) {
			moves = moveOneWest(grid);
		}

		moves = 1;
		while (moves != 0) {
			moves = moveOneSouth(grid);
		}

		moves = 1;
		while (moves != 0) {
			moves = moveOneEast(grid);
		}
	}

	int getLoad(char[][] grid) {
		int height = grid.length;
		int width = grid[0].length;
		int load = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = grid[y][x];
				if (c == 'O') {
					load += (height - y);
				}
			}
		}

		return load;
	}

	int moveOneNorth(char[][] grid) {
		int height = grid.length;
		int width = grid[0].length;
		int moves = 0;


		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = grid[y][x];
				if (c == 'O') {
					if (y > 0) {
						char above = grid[y-1][x];
						if (above == '.') {
							grid[y-1][x] = 'O';
							grid[y][x] = '.';
							moves += 1;
						}
					}

				}

			}
		}

		return moves;
	}

	int moveOneSouth(char[][] grid) {
		int height = grid.length;
		int width = grid[0].length;
		int moves = 0;


		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = grid[y][x];
				if (c == 'O') {
					if (y < height-1) {
						char below = grid[y+1][x];
						if (below == '.') {
							grid[y+1][x] = 'O';
							grid[y][x] = '.';
							moves += 1;
						}
					}

				}

			}
		}

		return moves;
	}

	int moveOneWest(char[][] grid) {
		int height = grid.length;
		int width = grid[0].length;
		int moves = 0;


		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				char c = grid[y][x];
				if (c == 'O') {
					if (x > 0) {
						char left = grid[y][x-1];
						if (left == '.') {
							grid[y][x-1] = 'O';
							grid[y][x] = '.';
							moves += 1;
						}
					}

				}

			}
		}

		return moves;
	}

	int moveOneEast(char[][] grid) {
		int height = grid.length;
		int width = grid[0].length;
		int moves = 0;


		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				char c = grid[y][x];
				if (c == 'O') {
					if (x < width-1) {
						char right = grid[y][x+1];
						if (right == '.') {
							grid[y][x+1] = 'O';
							grid[y][x] = '.';
							moves += 1;
						}
					}

				}

			}
		}

		return moves;
	}

	char[][] copy(char[][] grid) {
		char[][] newGrid = new char[grid.length][grid[0].length];

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				newGrid[y][x] = grid[y][x];

			}
		}

		return newGrid;
	}

	boolean equals(char[][] left, char[][] right) {
		for (int y = 0; y < left.length; y++) {
			for (int x = 0; x < left[0].length; x++) {
				if (left[y][x] != right[y][x]) {
					return false;
				}
			}
		}

		return true;
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

	public char[][] readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day14/input.txt");
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
