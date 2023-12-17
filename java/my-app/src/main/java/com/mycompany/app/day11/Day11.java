package com.mycompany.app.day11;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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

		List<Node> galaxies = findGalaxies(expandedGrid);
		for (Node galaxy : galaxies) {
			System.out.println(galaxy);
		}

		/*
		Set<NodePair> pairs = getGalaxyPairs(galaxies);
		for (NodePair pair : pairs) {
			System.out.println(pair);
		}
		System.out.println(pairs.size());
		*/

		/*
		System.out.println(shortestPath(galaxies.get(0), galaxies.get(6), expandedGrid));
		System.out.println(shortestPath(galaxies.get(0), galaxies.get(6), expandedGrid));
		System.out.println(shortestPath(galaxies.get(0), galaxies.get(6), expandedGrid));
		*/

		/*
		for (Node galaxyLeft : galaxies) {
			for (Node galaxyRight : galaxies.subList(i, galaxies.size())) {
				if (galaxyLeft != galaxyRight) {
					Node[][] newExpandedGrid = expandGrid(createGrid(lines));
					paths.add(shortestPath(galaxyLeft, galaxyRight, newExpandedGrid));
				}
			}
			i++;
		}
		System.out.println(paths);
		*/
		List<Integer> paths = new ArrayList<>();
		int sum = 0;
		for (int i = 0; i < galaxies.size(); i++) {
			for (int j = i+1; j < galaxies.size(); j++) {
				int path = execute(i, j);
				paths.add(path);
				sum += path;
			}
		}

		System.out.println(paths);
		System.out.println(sum);
	}

	int execute(int i, int j) {
		List<String> lines = readInput();
		Node[][] grid = createGrid(lines);

		//printGrid(grid);

		Node[][] expandedGrid = expandGrid(grid);
		//printGrid(expandedGrid);

		List<Node> galaxies = findGalaxies(expandedGrid);
		//for (Node galaxy : galaxies) {
		//	System.out.println(galaxy);
		//}

		/*
		Set<NodePair> pairs = getGalaxyPairs(galaxies);
		for (NodePair pair : pairs) {
			System.out.println(pair);
		}
		System.out.println(pairs.size());
		*/

		return shortestPath(galaxies.get(i), galaxies.get(j), expandedGrid);

	}

	int shortestPath(Node galaxyLeft, Node galaxyRight, Node[][] grid) {
		Node start = galaxyLeft;
		Node finish = galaxyRight;
		// System.out.println(String.format("Searching from %s for %s", start, finish));

		Queue<Node> queue = new LinkedList<>();
		start.depth = 0;
		queue.add(start);

		while (queue.peek() != null) {
			Node node = queue.poll();
			if (node.visited) {
				continue;
			}

			if (node.equals(finish)) {
				System.out.println("found " + node + " in " + node.depth);
				return node.depth;
			}
			// System.out.println("node: " + node + ", depth " + node.depth);
			List<Node> neighbours = getNeighbours(node, grid);

			for (Node neighbour : neighbours) {
				neighbour.depth = node.depth + 1;
			}

			// System.out.println("neighbours " + neighbours);
			queue.addAll(neighbours);
			node.visited = true;
		}

		return -1;
	}

	List<Node> getNeighbours(Node node, Node[][] grid) {
		List<Node> neighbours = new ArrayList<>();

		int height = grid.length;
		int width = grid[0].length;

		if (node.y-1 >= 0) {
			Node above = grid[node.y-1][node.x];
			if (!above.visited) {
				neighbours.add(above);
			}
		}


		if (node.x+1 < width) {
			Node right = grid[node.y][node.x+1];
			if (!right.visited) {
				neighbours.add(right);
			}
		}

		if (node.y+1 < height) {
			Node below = grid[node.y+1][node.x];
			if (!below.visited) {
				neighbours.add(below);
			}
		}

		if (node.x-1 >= 0) {
			Node left = grid[node.y][node.x-1];
			if (!left.visited) {
				neighbours.add(left);
			}
		}

		return neighbours;
	}


	Set<NodePair> getGalaxyPairs(List<Node> galaxies) {
		Set<NodePair> pairs = new HashSet<>();
		int i = 0;
		for (Node galaxyLeft : galaxies) {
			for (Node galaxyRight : galaxies.subList(i, galaxies.size())) {
				if (galaxyLeft != galaxyRight) {
					pairs.add(new NodePair(galaxyLeft, galaxyRight));
				}
			}
			i++;
		}

		return pairs;
	}

	List<Node> findGalaxies(Node[][] grid) {
		List<Node> galaxies = new ArrayList<>();
		int height = grid.length;
		int width = grid[0].length;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Node n = grid[y][x];
				if (n.c == '#') {
					galaxies.add(n);
				}
			}
		}

		return galaxies;
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
		return new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day11/input.txt");
	}
}
