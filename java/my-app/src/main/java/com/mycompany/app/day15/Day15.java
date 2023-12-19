package com.mycompany.app.day15;

import java.util.Arrays;
import java.util.List;

import com.mycompany.app.utils.InputReader;

public class Day15 {
	public static void main(String[] args) {
		new Day15().solveA();
	}

	public void solveA() {
		List<String> lines = readInput();
		System.out.println(lines);

		int sum = 0;
		for (String line : lines) {
			sum += hash(line);
		}

		System.out.println(sum);
	}

	int hash(String s) {
		int hash = 0;
		for (char c : s.toCharArray()) {
			int ascii = (int) c;

			hash += ascii;
			hash *= 17;
			hash %= 256;
		}

		return hash;

	}

	List<String> readInput() {
		String line = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day15/input.txt").get(0);
		System.out.println(line);
		return Arrays.asList(line.split(","));
	}

}
