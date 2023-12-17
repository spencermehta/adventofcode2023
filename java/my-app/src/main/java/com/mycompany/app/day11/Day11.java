package com.mycompany.app.day11;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.utils.InputReader;

public class Day11 {
	public static void main(String[] args) {
		new Day11().solveA();
	}

	public void solveA() {
		List<String> lines = readInput();
		Node[][] grid = createGrid(lines);

		printGrid(grid);

		Node[][] expandedGrid = expandGrid(grid);
		printGrid(expandedGrid);
	}

	Node[][] expandGrid(Node[][] grid) {
		int height = grid.length;
		int width = grid[0].length;

		List<Integer> rowsToBeExpanded = new ArrayList<>();

		for (int y = 0; y < height; y++) {
			boolean rowContainsGalaxy = false;
			for (int x = 0; x < width; x++) {
				if (grid[y][x].c == '#') {
					rowContainsGalaxy = true;
				}

			}

			if (!rowContainsGalaxy) {
				rowsToBeExpanded.add(y);
			}
		}

		List<Integer> columnsToBeExpanded = new ArrayList<>();

		for (int x = 0; x < width; x++) {
			boolean columnContainsGalaxy = false;
			for (int y = 0; y < height; y++) {
				if (grid[y][x].c == '#') {
					columnContainsGalaxy = true;
				}
			}

			if (!columnContainsGalaxy) {
				columnsToBeExpanded.add(x);
			}
		}

		int newHeight = height + rowsToBeExpanded.size();
		int newWidth = width + columnsToBeExpanded.size();
		System.out.println(rowsToBeExpanded);
		System.out.println(columnsToBeExpanded);
		System.out.println(newHeight + ", " + newWidth);

		Node[][] newGrid = new Node[newHeight][newWidth];

		int newYs = 0;

		for (int y = 0; y < height; y++) {
			int newXs = 0;
			if (rowsToBeExpanded.contains(y)) {
				for (int x = 0; x < newWidth; x++) {
					newGrid[y + newYs][x + newXs] = new Node(x + newXs, y + newYs, '.');
				}
				newYs++;
				for (int x = 0; x < newWidth; x++) {
					newGrid[y + newYs][x + newXs] = new Node(x + newXs, y + newYs, '.');
				}
			}
			for (int x = 0; x < width; x++) {
				newGrid[y + newYs][x + newXs] = new Node(x + newXs, y + newYs, grid[y][x].c);
				if (columnsToBeExpanded.contains(x)) {
					newGrid[y + newYs][x + newXs] = new Node(x + newXs, y + newYs, '.');
					newXs++;
					newGrid[y + newYs][x + newXs] = new Node(x + newXs, y + newYs, '.');
				}
			}
		}



		return newGrid;
	}

	void printGrid(Node[][] grid) {
		int height = grid.length;
		int width = grid[0].length;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (grid[y][x] == null) {
					System.out.print('!');
				} else {
					System.out.print(grid[y][x].c);
				}
			}
			System.out.println();
		}
	}

	Node[][] createGrid(List<String> lines) {
		int height = lines.size();
		int width = lines.get(0).length();

		Node[][] grid = new Node[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = lines.get(y).charAt(x);

				grid[y][x] = new Node(x, y, c);
			}
		}

		return grid;
	}


	List<String> readInput() {
		return new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day11/test.txt");
	}
}
