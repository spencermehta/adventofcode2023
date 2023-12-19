package com.mycompany.app.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mycompany.app.utils.InputReader;

public class Day15 {
	public static void main(String[] args) {
		new Day15().solveB();
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

	public void solveB() {
		Map<Integer, Map<String, String>> map = new HashMap<>();
		List<String> lines = readInput();

		for (String line : lines) {
			if (line.contains("=")) {
				System.out.println("eq " + line);
				String key = line.split("=")[0];
				String val = line.split("=")[1];

				int hash = hash(key);
				Map<String, String> bucket = map.getOrDefault(hash, new LinkedHashMap<String, String>());

				/*
				if (bucket.containsKey(val)) {
					bucket.remove(key);
				}
				*/
				bucket.put(key, val);
				map.put(hash, bucket);

				


			} else {
				System.out.println("del " + line);
				String key = line.split("-")[0];
				int hash = hash(key);

				Map<String, String> bucket = map.getOrDefault(hash, new LinkedHashMap<String, String>());
				bucket.remove(key);
				map.put(hash, bucket);
			}
		}

		System.out.println(map);
		int sum = 0;

		for (Entry<Integer, Map<String, String>> entry : map.entrySet()) {
			int boxN = entry.getKey() + 1;

			int i = 1;
			for (Entry<String, String> subEntry : entry.getValue().entrySet()) {
				int slot = i;
				int focalLength = Integer.parseInt(subEntry.getValue());
				int product = boxN * slot * focalLength;
				System.out.println("product of " + subEntry.getKey() + " " + product);
				sum += product;

				i++;
					
			}

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
