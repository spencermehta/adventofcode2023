package com.mycompany.app.day10;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.mycompany.app.utils.InputReader;

public class Day10 {
	int startX;
	int startY;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static void main(String[] args) {
		new Day10().solveB();
	}

	public void solveA() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day10/input.txt");
		int height = lines.size();
		int width = lines.get(0).length();

		Node[][] grid = buildGrid(lines);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print(grid[y][x] + "\t");
			}
			System.out.println("");
		}
		System.out.println(String.format("%s, %s", startX, startY));

		Queue<Node> queue = new LinkedList<>();
		queue.add(grid[startY][startX]);
		queue.peek().depth = 0;

		while (queue.peek() != null) {
			Node node = queue.poll();
			System.out.println("node: " + node + ", depth " + node.depth);
			List<Node> neighbours = getNeighbours(node, grid);

			for (Node neighbour : neighbours) {
				neighbour.depth = node.depth + 1;
			}

			System.out.println("neighbours: " + neighbours);
			queue.addAll(neighbours);
			node.visited = true;
		}
	}

	public void solveB() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day10/input.txt");
		int height = lines.size();
		int width = lines.get(0).length();

		Node[][] grid = buildGrid(lines);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print(grid[y][x] + "\t");
			}
			System.out.println("");
		}
		System.out.println(String.format("%s, %s", startX, startY));

		Queue<Node> queue = new LinkedList<>();
		queue.add(grid[startY][startX]);
		queue.peek().depth = 0;

		while (queue.peek() != null) {
			Node node = queue.poll();
			System.out.println("node: " + node + ", depth " + node.depth);
			List<Node> neighbours = getNeighbours(node, grid);

			for (Node neighbour : neighbours) {
				neighbour.depth = node.depth + 1;
			}

			System.out.println("neighbours: " + neighbours);
			queue.addAll(neighbours);
			node.visited = true;
		}


		long inside = 0;
		for (int y = 0; y < height; y++) {
			boolean barParity = true;
			char prevParity = '.';
			for (int x = 0; x < width; x++) {
				Node node = grid[y][x];
				char c = node.pipe.c;
				/*
				if (y == 4) {
					System.out.println(String.format("char %s barParity %s, prevParity %s", c, barParity, prevParity));
				}
				*/

				if (c == '|' && node.visited) {
					barParity = !barParity;
					System.out.print(ANSI_CYAN + c + ANSI_RESET);
				} else if (c == 'F' && node.visited) {
					prevParity = 'F';
					System.out.print(c);
				} else if (c == 'J' && node.visited) {
					if (prevParity == 'F') {
						barParity = !barParity;
						prevParity = 'J';
						System.out.print(ANSI_CYAN + c + ANSI_RESET);
					} else {
						prevParity = 'J';
						System.out.print(c);
					}
				} else if (c == 'L' && node.visited) {
					prevParity = 'L';
					System.out.print(c);
				} else if (c == '7' && node.visited) {
					if (prevParity == 'L') {
						barParity = !barParity;
						prevParity = '7';
						System.out.print(ANSI_CYAN + c + ANSI_RESET);
					} else {
						prevParity = '7';
						System.out.print(c);
					}
				} else {
					if (!barParity & !node.visited) {
						inside += 1;
						System.out.print(ANSI_GREEN + 'I' + ANSI_RESET);
					} else {
						if (node.visited) {
							System.out.print(c);
						} else {
							System.out.print('.');
						}
					}
				}

			}
			System.out.println("");
		}
		System.out.println(inside);
	}

	List<Node> getNeighbours(Node node, Node[][] grid) {
		List<Node> neighbours = new ArrayList<>();

		int height = grid.length;
		int width = grid[0].length;

		if (node.y-1 >= 0) {
			Node above = grid[node.y-1][node.x];
			if (!above.visited && node.pipe.connectsNorth() && above.pipe.connectsSouth()) {
				neighbours.add(above);
			}
		}


		if (node.x+1 < width) {
			Node right = grid[node.y][node.x+1];
			if (!right.visited && node.pipe.connectsEast() && right.pipe.connectsWest()) {
				neighbours.add(right);
			}
		}

		if (node.y+1 < height) {
			Node below = grid[node.y+1][node.x];
			if (!below.visited && node.pipe.connectsSouth() && below.pipe.connectsNorth()) {
				neighbours.add(below);
			}
		}

		if (node.x-1 >= 0) {
			Node left = grid[node.y][node.x-1];
			if (!left.visited && node.pipe.connectsWest() && left.pipe.connectsEast()) {
				neighbours.add(left);
			}
		}

		return neighbours;
	}

	public Node[][] buildGrid(List<String> lines) {
		int height = lines.size();
		int width = lines.get(0).length();

		Node[][] grid = new Node[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = lines.get(y).charAt(x);

				if (c == 'S') {
					startX = x;
					startY = y;
				}

				grid[y][x] = new Node(new Pipe(c), x, y);
			}
		}

		return grid;
	}
}
