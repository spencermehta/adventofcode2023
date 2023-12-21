package com.mycompany.app.day21;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mycompany.app.utils.InputReader;

public class Day21 {
	private final int[][] moves = {{1,0}, {-1,0}, {0,1}, {0,-1}};
	char[][] grid;
	Point start;

	public static void main(String[] args) {
		new Day21().solveB();
	}

	void solveA() {
		readInput();
		printGrid();
		System.out.println("start " + start);

		Set<Point> wavefront = new HashSet<>();
		wavefront.add(start);
		System.out.println("wavefront " + wavefront);

		int steps = 1000;
		for (int i = 0; i < steps; i++) {
			wavefront = step(wavefront);

			//System.out.println(wavefront);
			System.out.println(wavefront.size());
		}
	}

	void solveB() {
		readInput();
		printGrid();

		// steps = 65/2 + 131x
		System.out.println(solveForSteps(65));
		System.out.println(solveForSteps(65 + 131));
		System.out.println(solveForSteps(65 + (2*131)));
		// fits to polynomial x^2 + 2x + a 
		// ans is at x = ( S - 65 ) / 131
	}

	int solveForSteps(int steps) {
		Set<Point> wavefront = new HashSet<>();
		wavefront.add(start);

		for (int i = 0; i < steps; i++) {
			wavefront = step(wavefront);
		}

		return wavefront.size();
	}


	Set<Point> step(Set<Point> wavefront) {
		Set<Point> newWavefront = new HashSet<>();

		for (Point p : wavefront) {
			newWavefront.addAll(getNeighbours(p));
		}

		return newWavefront;
	}

	List<Point> getNeighbours(Point p) {
		List<Point> neighbours = new ArrayList<>();

		for (int[] move : moves) {
			int x = (p.x + move[0]);
			int y = (p.y + move[1]);

			int gridx = ((x % grid[0].length) + grid[0].length) % grid[0].length;
			int gridy = ((y % grid.length) + grid.length) % grid.length;

			//System.out.println(String.format(" pos (%s, %s) move (%s, %s) gxy (%s, %s)", p.x, p.x, move[0], move[1], gridx, gridy));
			char c = grid[gridy][gridx];
			if (c != '#') {
				neighbours.add(new Point(x, y));
			}

		}

		return neighbours;

	}

	void printGrid() {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				System.out.print(grid[y][x]);
			}
			System.out.println();
		}
	}

	void readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day21/input.txt");
		grid = new char[lines.size()][lines.get(0).length()];
		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(0).length(); x++) {
				grid[y][x] = lines.get(y).charAt(x);
				if (grid[y][x] == 'S') {
					start = new Point(x, y);
				}
			}
		}
	}
}
