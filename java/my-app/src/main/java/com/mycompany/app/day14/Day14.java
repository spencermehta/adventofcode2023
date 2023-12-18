package com.mycompany.app.day14;

import java.util.Arrays;
import java.util.List;

import com.mycompany.app.utils.InputReader;

public class Day14 {
	public static void main(String[] args) {
		new Day14().solveA();
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
