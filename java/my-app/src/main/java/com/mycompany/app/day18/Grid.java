package com.mycompany.app.day18;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

public class Grid {
	Map<Integer, Map<Integer, Character>> grid = new HashMap<>();

	public void put(int x, int y, char c) {
		put(new int[]{x, y}, c);
	}

	public void put(int[] pos, char c) {
		Map<Integer, Character> ys = grid.getOrDefault(pos[1], new HashMap<Integer, Character>());
		ys.put(pos[0], '#');
		grid.put(pos[1], ys);
	}

	public char get(int[] pos) {
		Map<Integer, Character> ys = grid.get(pos[1]);
		if (ys == null) {
			return '.';
		}
		Character x = ys.get(pos[0]);
		return x == null ? '.' : x;
	}

	public char get(int x, int y) {
		return get(new int[]{x, y});
	}

	public int sum() {
		int[] dimensions = dimensions();
		int width = dimensions[0];
		int height = dimensions[1];
		int[] mins = minDimensions();
		int sum = 0;
		for (int y = mins[1]; y <= height; y++) {
			for (int x = mins[0]; x <= width; x++) {
				if ((grid.get(y) != null) && (grid.get(y).get(x) != null)) {
					sum += 1;
				} 
			}
		}
		return sum;
	}

	public void printGrid() {
		int[] dimensions = dimensions();
		int[] mins = minDimensions();
		int width = dimensions[0];
		int height = dimensions[1];
		for (int y = mins[1]; y <= height; y++) {
			for (int x = mins[0]; x <= width; x++) {
				if ((grid.get(y) != null) && (grid.get(y).get(x) != null)) {
					System.out.print(grid.get(y).get(x));
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}

	public int[] dimensions() {
		int height = 0;
		int width = 0;
		for (Entry<Integer, Map<Integer, Character>> entry : grid.entrySet()) {
			if (entry.getKey() > height) {
				height = entry.getKey();
			}

			for (int key : entry.getValue().keySet()) {
				if (key > width) {
					width = key;
				}
			}
		}

		return new int[]{width, height};

	}

	public int[] minDimensions() {
		int height = Integer.MAX_VALUE;
		int width = Integer.MAX_VALUE;
		for (Entry<Integer, Map<Integer, Character>> entry : grid.entrySet()) {
			if (entry.getKey() < height) {
				height = entry.getKey();
			}

			for (int key : entry.getValue().keySet()) {
				if (key < width) {
					width = key;
				}
			}
		}

		return new int[]{width, height};

	}

}
