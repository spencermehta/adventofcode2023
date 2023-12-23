package com.mycompany.app.day23;

import com.mycompany.app.utils.InputReader;

import java.util.*;

public class Day23 {
	Node[][] grid;
	List<Integer> lengths = new ArrayList<>();
	Set<Node> vertices = new HashSet<>();
	Map<Node, Map<Node, Integer>> edges = new HashMap<>();
	int xStart;
	int xEnd;

	private final int[][] moves = { {1,0}, {-1,0}, {0,1}, {0,-1} };

	public static void main(String[] args) {
		new Day23().solveB();
	}

	void solveA() {
		readInput();

		step(grid[0][xStart], 0);

		printGrid();

		System.out.println(lengths);
		System.out.println(getMax(lengths));
	}

	void solveB() {
		readInput();

		buildDag();
		System.out.println(edges);
		setUnvisited();
		step2(grid[0][xStart], 0);

		printGrid();

		System.out.println(getMax(lengths));
	}

	void step(Node n, int d) {
		if (n.visited) {
			return;
		}
		if (n.x == xEnd && n.y == grid.length-1) {
			lengths.add(d);
		}

		n.visited = true;

		for (Node neighbour : getNeighbours(n)) {
			step(neighbour, d+1);
		}

		n.visited = false;
	}

	void step2(Node n, int d) {
		if (n.visited) {
			return;
		}
		if (n.x == xEnd && n.y == grid.length-1) {
			lengths.add(d);
		}

		n.visited = true;

		Map<Node, Integer> m = edges.getOrDefault(n, new HashMap<>());
		for (Map.Entry<Node, Integer> e : m.entrySet()) {
			step2(e.getKey(), d+e.getValue());
		}

		n.visited = false;
	}

	void buildDag() {
		for (Node[] row : grid) {
			for (Node node : row) {
				List<Node> neighbours = getNeighbours(node);
				if (neighbours.size() > 2) {
					vertices.add(node);
				}
			}
		}

		vertices.add(grid[0][xStart]);
		vertices.add(grid[grid.length-1][xEnd]);

		for (Node[] row : grid) {
			for (Node v : row) {
				setUnvisited();
				bfs(v);
			}
		}
	}

	void setUnvisited() {
		for (Node[] row : grid) {
			for (Node n : row) {
				n.visited = false;
			}
		}
	}

	void bfs(Node v) {
		Queue<Node> queue = new LinkedList<>();
		queue.add(v);
		v.depth = 0;
		while ((queue.peek() != null)) {
			Node n = queue.poll();
			if (n.visited) {
				continue;
			}
			n.visited = true;
			List<Node> neighbours = getNeighbours(n);
			for (Node neighbour : neighbours) {
				if (!neighbour.visited && vertices.contains(neighbour)) {
					Map<Node, Integer> map = edges.getOrDefault(v, new HashMap<>());
					map.put(neighbour, n.depth + 1);
					edges.put(v, map);
					System.out.println("found " + neighbour);
				} else {
					neighbour.depth = n.depth + 1;
					queue.add(neighbour);
				}
			}
		}
	}

	int getMax(List<Integer> ints) {
		int max = 0;
		for (int length : ints) {
			if (length > max) {
				max = length;
			}
		}
		return max;
	}

	void printGrid() {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				char c = grid[y][x].c;
				boolean v = grid[y][x].visited;

				if (v) {
					System.out.print("O");
				} else {
					System.out.print(c);
				}
			}
			System.out.println();
		}
	}

	List<Node> getNeighbours(Node n) {
		List<Node> neighbours = new ArrayList<>();
		for (int[] mv : moves) {
			int x = n.x + mv[0];
			int y = n.y + mv[1];

			if ((x >= 0) && (x < grid[0].length) && (y >= 0) && (y < grid.length)) {
				Node neighbour = grid[y][x];
				if ((neighbour.c == '.') || (neighbour.c == 'v') || (neighbour.c == '>')) {
						neighbours.add(neighbour);
				}
			}
		}

		return neighbours;

	}

	void readInput() {
		List<String> lines = new InputReader().readInput("/Users/spencer/projects/hobby/aoc/2023/input/day23/input.txt");
		grid = new Node[lines.size()][lines.get(0).length()];

		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(0).length(); x++) {
				grid[y][x] = new Node(x, y, lines.get(y).charAt(x));
			}
		}

		xStart = lines.get(0).indexOf('.');
		xEnd = lines.get(lines.size()-1).indexOf('.');
	}
}
