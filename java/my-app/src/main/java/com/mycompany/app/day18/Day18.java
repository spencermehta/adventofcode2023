package com.mycompany.app.day18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mycompany.app.utils.InputReader;

public class Day18 {
	private Grid grid = new Grid();
	private final List<Character> dirs = Arrays.asList( 'R', 'L', 'D', 'U' );
	private final int[][] moves = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
	private double area = 0;

	public static void main(String[] args) {
		new Day18().solveA();
	}

	public void solveA() {
		List<Instr> instrs = readInput();
		System.out.println(instrs);

		int[] startpos = new int[]{0, 0};
		//grid.put(startpos, '#');

		for (Instr instr : instrs) {
			execInstr(startpos, instr);
			//grid.printGrid();
		}
		grid.printGrid();

		System.out.println(area);
		System.out.println(grid.sum());
		// pick's theorem
		System.out.println(Math.abs((int) area) + (grid.sum()/2) + 1);
	}

	void execInstr(int[] pos, Instr instr) {
		int[] move = moves[instr.dir];
		System.out.println(instr);
		// green's theorem
		if (dirs.get(instr.dir) == 'R') {
			area += pos[1] * instr.steps;
		} else if (dirs.get(instr.dir) == 'L') {
			area -= pos[1] * instr.steps;
		}

		int[][] movements = new int[instr.steps][2];
		for (int i = 0; i < instr.steps; i++) {
			movements[i][1] = (1*move[1]);
			movements[i][0] = (1*move[0]);

		}

		for (int[] mv : movements) {
			move(pos, mv);
		}

	}

	void move(int[] pos, int[] mv) {
		pos[0] += mv[0];
		pos[1] += mv[1];
		grid.put(pos, '#');
	}


	List<Instr> readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day18/input.txt");
		List<Instr> instrs = new ArrayList<>();
		for (String line : lines) {
			String[] split = line.split(" ");

			int dir = dirs.indexOf(split[0].charAt(0));
			int steps = Integer.parseInt(split[1]);
			String colour = split[2];
			instrs.add(new Instr(dir, steps, colour));
		}

		return instrs;
	}
}
