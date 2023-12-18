package com.mycompany.app.day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mycompany.app.utils.InputReader;

public class Day13 {
	public static void main(String[] args) {
		new Day13().solveA();
	}

	public void solveA() {
		List<List<String>> groupedLines = readInput();

		List<char[][]> grids = parseGrids(groupedLines);

		System.out.println(grids);

		long sum = 0;

		for (char[][] grid : grids) {
			System.out.println(gridToString(grid));
			int mirrorIndexVertical = scanForVerticalLine(grid);
			if (mirrorIndexVertical != 0) {
				System.out.println("vert " + mirrorIndexVertical);
				sum += mirrorIndexVertical;
			} else {
				int mirrorIndexHorizontal = scanForVerticalLine(pivot(grid));
				System.out.println("horiz " + mirrorIndexHorizontal);
				sum += (100 * mirrorIndexHorizontal);
			}
		}

		System.out.println(sum);
	}

	public int scanForVerticalLine(char[][] grid) {
		int height = grid.length;
		int width = grid[0].length;


		int[] possibleIndices = new int[width];
		for (int w = 0; w < width; w++) {
			possibleIndices[w] = 0;
		}

		for (int y = 0; y < height; y++) {
			for (int i_x = 0; i_x < width; i_x++) {
				char[] line = grid[y];

				int sizeToMatch = Math.min(i_x, width-i_x);
				int leftStart = i_x - sizeToMatch;
				int rightStart = i_x;

				char[] left = Arrays.copyOfRange(line, leftStart, leftStart + sizeToMatch);
				char[] right = Arrays.copyOfRange(line, rightStart, rightStart + sizeToMatch);
				int diffs = numDiffChars(left, reverse(right));
				possibleIndices[i_x] = possibleIndices[i_x] + diffs;
				//System.out.println(String.format("%s\ni_x %s size %s left %s right %s\n", Arrays.toString(line), i_x, sizeToMatch, leftStart, rightStart));
				//System.out.println("left " + Arrays.toString(left));
				//System.out.println("right " + Arrays.toString(right));
				//System.out.println(eq + "\n");
			}
			//System.out.println(Arrays.toString(possibleIndices));
		}

		possibleIndices = Arrays.copyOfRange(possibleIndices, 1, possibleIndices.length);
		System.out.println(Arrays.toString(possibleIndices));
		int mirrorIndex = indexOf(possibleIndices, 1) + 1;
		return mirrorIndex; //TODO
	}

	public int numDiffChars(char[] left, char[] right) {
		int diffs = 0;
		for (int i = 0; i < left.length; i++) {
			if (left[i] != right[i]) {
				diffs++;
			}
		}
		return diffs;

	}

	public char[][] pivot(char[][] grid) {
		int height = grid.length;
		int width = grid[0].length;

		char[][] newGrid = new char[width][height];

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < width; x++) {
				newGrid[x][y] = grid[y][x];
			}
		}

		return newGrid;
	}

	public int indexOf(int[] arr, int c) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == c) {
				return i;
			}
		}
		return -1;
	}

	public char[] reverse(char[] arr) {
		for(int i = 0; i < arr.length / 2; i++) {
		    char temp = arr[i];
		    arr[i] = arr[arr.length - i - 1];
		    arr[arr.length - i - 1] = temp;
		}
		return arr;
	}

	public String gridToString(char[][] grid) {
		String s = "";
		for (char[] line : grid) {
			for (char c : line) {
				s += c;
			}
			s += "\n";
		}

		return s;
	}

	public List<char[][]> parseGrids(List<List<String>> groupedLines) {
		List<char[][]> grids = new ArrayList<>();

		for (List<String> lines : groupedLines) {
			grids.add(parseGrid(lines));
		}

		return grids;

	}

	public char[][] parseGrid(List<String> lines) {
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

	public List<List<String>> readInput() {
		return new InputReader().readInputBreakAtBlankLines("/home/spencer/projects/hobby/aoc/2023/input/day13/input.txt");
	}
}
