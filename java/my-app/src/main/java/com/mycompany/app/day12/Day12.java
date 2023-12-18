package com.mycompany.app.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.app.utils.InputReader;

public class Day12 {

	private Map<Position, Long> cache;
	public static void main(String[] args) {
		new Day12().solveA();
	}

	public void solveA() {
		List<String> lines = readInput();
		List<Record> records = parseToRecords(lines);

		long sum = 0;
		for (Record r : records) {
			cache = new HashMap<>();

			String newP = r.pattern + "?" + r.pattern + "?" + r.pattern + "?" + r.pattern + "?" + r.pattern + ".";
			int[] newN = new int[5*r.numbers.length];

			for (int i = 0; i < 5*r.numbers.length; i++) {
				int j = i % r.numbers.length;
				newN[i] = r.numbers[j];
			}

			System.out.println(r +  newP);

			int[] newNn = new int[newN.length + 1];

			for (int j = 0; j < newN.length; j++) {
				newNn[j] = newN[j];
			}

			newNn[newNn.length-1] = 0;

			long p = evaluateRecord(newP, newNn, 0, 0, 0);
			sum += p;
			System.out.println(p);
		}
		System.out.println("sum " + sum);
	}

	long evaluateRecord(String pattern, int[] blocks, int i, int bi, int l) {
		//System.out.println(String.format("evaluating %s %s %s %s", pattern, i, bi, l));
		Long cachedResult = cache.get(new Position(i, bi, l));
		//System.out.println(cache.size());
		if (cachedResult != null) {
			System.out.println(String.format("pulling cache res %s %s %s", i, bi, l, cachedResult));
			return cachedResult;
		}
		// System.out.println(i + " - " + pattern);
		if (i == pattern.length()) {
			/*
			if (bi == blocks.length && l == 0) {
				System.out.println(pattern + "return 1");
				return 1;
			} 
			*/
			if (bi == (blocks.length - 1) && (l == 0)) {
				//System.out.println(pattern + "return 1");
				return 1;
			}
			if (bi == (blocks.length - 2) && (blocks[bi] == l)) {
				//System.out.println(pattern + "return 1");
				return 1;
			}
			//System.out.println(String.format("%s %s %s %s set 0", pattern, i, bi, l));
			return 0;
		}

		char c = pattern.charAt(i);
		//System.out.println(c);
		long result = 0;


		if (bi >= blocks.length) {
			//System.out.println(String.format("%s %s %s %s set 0 exceed blocks", pattern, i, bi, l));
			result = 0;
		}
		else if (l > blocks[bi]) {
			//System.out.println(String.format("%s %s %s %s set 0 exceeded current block size", pattern, i, bi, l));
			result = 0;
		}

		else if (c != '?') {
			if (c == '#') {
				result = evaluateRecord(pattern, blocks, i+1, bi, l+1);
			}
			else { // .
				if (l != 0 && l < blocks[bi]) {
					result = 0;
				} else {
					int newBi = l != 0 ? bi+1 : bi;
					result = evaluateRecord(pattern, blocks, i+1, newBi, 0);
				}
			}
		} else {
			/*
			result =  
				evaluateRecord(pattern.substring(0, i) + "." + pattern.substring(i+1), blocks, i, bi, l) +
				evaluateRecord(pattern.substring(0, i) + "#" + pattern.substring(i+1), blocks, i, bi, l);
			*/
			result +=  
				evaluateRecord(pattern.substring(0, i) + "#" + pattern.substring(i+1), blocks, i+1, bi, l+1);


				if (l != 0 && l < blocks[bi]) {
					result += 0;
				} else {
					int newBi = l != 0 ? bi+1 : bi;
					result += evaluateRecord(pattern, blocks, i+1, newBi, 0);
				}
		}

		cache.put(new Position(i, bi, l), result);
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
		return new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day12/input.txt");
	}

}
