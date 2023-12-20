package com.mycompany.app.day18;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mycompany.app.utils.InputReader;

public class Day18 {
	private Grid grid = new Grid();
	private final List<Character> dirs = Arrays.asList( 'R', 'L', 'D', 'U' );
	private final int[][] moves = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
	private BigDecimal area = new BigDecimal(0);
	private BigDecimal steps = new BigDecimal(0);
	private BigDecimal x = new BigDecimal(0);
	private BigDecimal y = new BigDecimal(0);

	public static void main(String[] args) {
		new Day18().solveB();
	}

	public void solveA() {
		/*
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
		//System.out.println(Math.abs((int)area) + (grid.sum()/2) + 1);
		*/
	}

	public void solveB() {
		List<Instr> instrs = readInputB();
		System.out.println(instrs);


		for (Instr instr : instrs) {
			execInstr(instr);
		}

		System.out.println("area " + area);
		System.out.println("steps " + steps);
		// pick's theorem
		System.out.println(area.abs().add(steps.divide(new BigDecimal(2))).add(new BigDecimal(1)));
	}

	void execInstr(Instr instr) {
		int[] move = moves[instr.dir];
		System.out.println(instr);
		System.out.println(x + ", " + y);
		// green's theorem
		if (dirs.get(instr.dir) == 'R') {
			area = area.add(y.multiply(new BigDecimal(instr.steps)));
			System.out.println(area);
		} else if (dirs.get(instr.dir) == 'L') {
			System.out.println(area);
			area = area.subtract(y.multiply(new BigDecimal(instr.steps)));
		}

		int[][] movements = new int[instr.steps][2];
		for (int i = 0; i < instr.steps; i++) {
			movements[i][1] = (1*move[1]);
			movements[i][0] = (1*move[0]);
		}

		for (int[] mv : movements) {
			move(mv);
		}

	}

	void move(int[] mv) {
		x = x.add(new BigDecimal(mv[0]));
		y = y.add(new BigDecimal(mv[1]));
		steps = steps.add(new BigDecimal(1));
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

	List<Instr> readInputB() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day18/input.txt");
		List<Instr> instrs = new ArrayList<>();
		final List<Character> encodedDirs = Arrays.asList( 'R', 'D', 'L', 'U' );
		for (String line : lines) {
			String[] split = line.split(" ");

			String colour = split[2];
			int encDir = colour.charAt(colour.length()-2) - '0';
			int dir = dirs.indexOf(encodedDirs.get(encDir));
			String stepsHex = colour.substring(2, colour.length()-2);
			int steps = Integer.parseInt(stepsHex, 16);

			instrs.add(new Instr(dir, steps, colour));
		}

		return instrs;
	}
}
