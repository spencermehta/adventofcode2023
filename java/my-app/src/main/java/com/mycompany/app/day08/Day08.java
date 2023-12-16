package com.mycompany.app.day08;

import com.mycompany.app.utils.InputReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Day08 {
	public static void main(String[] args) {
		new Day08().solveB();
	}

	public void solveA() {
		List<List<String>> groupedLines = readInput();

		String instructions = groupedLines.get(0).get(0);
		System.out.println(instructions);

		Map<String, Node> map = parseNodes(groupedLines.get(1));
		System.out.println(map);


	}

	public void solveB() {
		List<List<String>> groupedLines = readInput();

		String instructions = groupedLines.get(0).get(0);
		System.out.println(instructions);

		Map<String, Node> map = parseNodes(groupedLines.get(1));
		System.out.println(map);

		List<Integer> cycleLengths = new ArrayList<>();

		List<String> current = new ArrayList<>();
		for (String key : map.keySet()) {
			if (key.charAt(2) == 'A') {
				current.add(key);
			}
		}

		for (String curr : current) {
			cycleLengths.add(doSteps(instructions, map, curr));
		}
		System.out.println(cycleLengths);
		/* pasted into python where it's trivial
		 * math.lcm([...])
		 *
		 * */
	}

	public int doSteps(String instructions, Map<String, Node> map, String start) {
		int i = 0;
		int steps = 0;
		String current = start;
		while (true) {
			if (current.charAt(2) =='Z') {
				System.out.println("found in " + steps);
				return steps;
			}
			char c = instructions.charAt(i);
			Node node = map.get(current);
			current = (c == 'L') ? node.left : node.right;
			System.out.println(current);


			i = (i+1) % instructions.length();
			steps++;
			System.out.println("updating i to " + i);

		}

	}

	public boolean allCurrentEnd(List<String> current) {
		boolean win = true;
		for (String curr : current) {
			if (curr.charAt(2) != 'Z') {
				win = false;
			}
		}
		return win;
	}


	Map<String, Node> parseNodes(List<String> lines) {
		Map<String, Node> map = new HashMap<>();

		for (String line : lines) {
			String key = line.split(" = ")[0];
			String value = line.split(" = ")[1];
			value = value.replaceAll("[()]", "");

			String left = value.split(", ")[0];
			String right = value.split(", ")[1];
			map.put(key, new Node(left, right));
		}

		return map;

	}

	List<List<String>> readInput() {
		return new InputReader().readInputBreakAtBlankLines("/home/spencer/projects/hobby/aoc/2023/input/day08/part1.txt");
	}
}
