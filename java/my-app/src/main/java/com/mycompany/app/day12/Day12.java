package com.mycompany.app.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.app.utils.InputReader;

public class Day12 {

	private Map<Position, Integer> cache;
	public static void main(String[] args) {
		new Day12().solveA();
	}

	public void solveA() {
		List<String> lines = readInput();
		List<Record> records = parseToRecords(lines);

		int sum = 0;
		for (Record r : records) {
			cache = new HashMap<>();

			String newP = r.pattern + "?" + r.pattern + "?" + r.pattern + "?" + r.pattern + "?" + r.pattern + ".";
			int[] newN = new int[5*r.numbers.length];

			for (int i = 0; i < 5*r.numbers.length; i++) {
				int j = i % r.numbers.length;
				newN[i] = r.numbers[j];
			}

			r = new Record(newP, newN);

			System.out.println(r);

			int p = evaluateRecord(r.pattern, r.numbers, 0);
			sum += p;
			System.out.println(p);
		}
		System.out.println("sum " + sum);
	}

	int evaluateRecord(String pattern, int[] blocks, int i) {
		Integer cachedResult = cache.get(new Position(pattern, blocks, i));
		if (cachedResult != null) {
			System.out.println("pulling cache res");
			return cachedResult;
		}
		// System.out.println(i + " - " + pattern);
		if (i == pattern.length()-1) {
			if (validPattern(pattern, blocks)) {
				System.out.println("valid" + pattern);
				return 1;
			} else {
				return 0;
			}
		}

		char c = pattern.charAt(i);
		//System.out.println(c);
		int result;
		if (c != '?') {
			result = evaluateRecord(pattern, blocks, i+1);
		} else {
			result =  
				evaluateRecord(pattern.substring(0, i) + "." + pattern.substring(i+1), blocks, i + 1) +
				evaluateRecord(pattern.substring(0, i) + "#" + pattern.substring(i+1), blocks, i+1);
		}

		cache.put(new Position(pattern, blocks, i), result);
		return result;
	}

	boolean validPattern(String pattern, int[] blocks) {
		int i = 0;
		while (i < pattern.length()) {

			char c = pattern.charAt(i);

			if (c == '#') {
				if (blocks.length == 0) {
					return false;
				}
				int seg = blocks[0];
				//System.out.println("checking # at " + i + " with " + blocks);
				if (seg >= pattern.length() - i) {
					return false;
				}

				for (int j = i; j < i+seg; j++) {
					if (pattern.charAt(j) != '#') {
						return false;
					}
				}
				if (pattern.charAt(i+seg) != '.') {
					return false;
				}

				i+= seg;
				blocks = Arrays.copyOfRange(blocks, 1, blocks.length);
			} else {
				i++;
			}
		}
		if (blocks.length > 0) {
			return false;
		}

		return true;
	}

	List<Record> parseToRecords(List<String> lines) {
		List<Record> records = new ArrayList<>();
		for (String line : lines) {
			records.add(parseLine(line));
		}

		return records;
	}

	Record parseLine(String line) {
		String pattern = line.split("\\s+")[0];
		List<String> numberStrings = Arrays.asList(line.split("\\s+")[1].split(","));
		int[] numbers = new int[numberStrings.size()];
		int i = 0;
		for (String s : numberStrings) {
			numbers[i] = Integer.parseInt(s);
			i++;
		}

		return new Record(pattern, numbers);

	}

	List<String> readInput() {
		return new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day12/test.txt");
	}

}
