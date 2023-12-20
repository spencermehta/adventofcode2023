package com.mycompany.app.day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Map.Entry;

import com.mycompany.app.utils.InputReader;

public class Day17 {
	public static void main(String[] args) {
		new Day17().solveA();
	}

	public void solveA() {
		int[][] grid = readInput();

		int startX = 0;
		int startY = 0;

		int endX = grid[0].length-1;
		int endY = grid.length-1;

		int x = startX;
		int y = startY;

		Point startPoint = new Point(x, y, 0, 1, 0);
		Queue<Point> q = new PriorityQueue<>();
		q.add(startPoint);
		Map<String, Integer> distance = new HashMap<>();

		int i = 0;
		while (q.peek() != null) {
			/*
			if (i > 10) {
				break;
			}
			*/
			i++;

			Point p = q.poll();
			String key = p.x + "," + p.y + "," + p.dir + "," + p.numInThisDir;
			if (p.x == endX && p.y == endY) {
				System.out.println("end " + p.weightSoFar);
				return;
			}
			if (distance.containsKey(key)) {
				continue;
			}

			System.out.println(p);
			distance.put(key, p.weightSoFar);

			List<Point> neighbours = possiblePoints(grid, p);
			System.out.println("neighbours " + neighbours);
			q.addAll(neighbours);
		}

		int min = Integer.MAX_VALUE;
		for (Entry<String, Integer> entry : distance.entrySet()) {
			if (entry.getKey().startsWith(endX + "," + endY + ",")) {
				if (entry.getValue() < min) {
					min = entry.getValue();
				}
			}
		}
		System.out.println("min " + min);

		System.out.println("end point " + distance.get(endX + "," + endY));

		/*
		for (int p = 0; p < grid.length; p++) {
			for (int r = 0; r < grid[0].length; r++) {
				boolean contains = false;
				for (String k : distance.keySet()) {
					if (k.startsWith(r+","+p)) {
						contains = true;
					}
				}
				if (contains) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}

		*/
	}

	List<Point> possiblePoints(int[][] grid, Point p) {
		List<Point> points = new ArrayList<>();

		int[] dirs = {0,1,2,3};
		int[] moveMultiplier = {1,4};
		int[][] moves = {{0,1},{1,0},{0,-1},{-1,0}};

		//for (int moveMult : moveMultiplier) {
			int dir = 0;
			for (int[] move : moves) {
				int moveMult;
				if ((dir != p.dir) || (p.numInThisDir == 0)) {
					moveMult = moveMultiplier[1];
				} else {
					moveMult = moveMultiplier[0];
				}
				int x = p.x + (moveMult*move[0]);
				int y = p.y + (moveMult*move[1]);
				if ((x >= 0) && (x < grid[0].length)) {
					if ((y >= 0) && (y < grid.length)) {
						if (dir != (dirs[(p.dir+2) % 4])) {
							int cost = 0;
							System.out.println(p.x + ", " + p.y + " - " + x + ", " + y);
							if ((y < p.y) || (x < p.x)) {
								for (int i = p.y + ((move[1]*1)); i >= y; i--) {
									for (int j = p.x + (move[0]*1); j >= x; j--) {
										System.out.println(i + ", " + j);
										cost += grid[i][j];
									}
								}
							} else {
								for (int i = p.y + ((move[1]*1)); i <= y; i++) {
									for (int j = p.x + (move[0]*1); j <= x; j++) {
										cost += grid[i][j];
									}
								}
							}
							//int cost = grid[y][x];
							int numInThisDir = p.dir == dir ? (p.numInThisDir + moveMult) : moveMult;
							System.out.println(numInThisDir);
							if ((numInThisDir <= 10) && (numInThisDir >=4)) {
								Point q = new Point(x, y, numInThisDir, dir, p.weightSoFar + cost);
								points.add(q);
							}
						}
					}
				}
				dir++;
			}
		//}
		return points;
	}

	public int[][] readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day17/input.txt");
		for (String line : lines) {
			System.out.println(line);
		}
		int[][] grid = new int[lines.size()][lines.get(0).length()];

		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(0).length(); x++) {
				grid[y][x] = Integer.parseInt(String.valueOf(lines.get(y).charAt(x)));
			}
		}

		return grid;

	}
}
