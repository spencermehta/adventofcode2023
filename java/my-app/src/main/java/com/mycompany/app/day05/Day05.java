package com.mycompany.app.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mycompany.app.utils.InputReader;

public class Day05 {
	public static void main(String[] args) {
		new Day05().solveA();
	}

	public Day05() {}

	public void solveA() {
		List<List<String>> groupedLines = new InputReader()
			.readInputBreakAtBlankLines("/home/spencer/projects/hobby/aoc/2023/input/day05/part1.txt");

		System.out.println(groupedLines);

		List<Long> seeds = parseSeeds(groupedLines.get(0));
		System.out.println(seeds);

		Map<String, ConversionMap> mapOfMaps = parseAllMaps(groupedLines.subList(1, groupedLines.size()));
		System.out.println(mapOfMaps);

		List<String> order = Arrays.asList("seed", "soil", "fertilizer", "water", "light", "temperature", "humidity", "location");

		for (int i = 0; i < order.size() - 1; i++) {
			String one = order.get(i);
			String two = order.get(i+1);
			String conversionMapName = getConversionMapName(one, two);
			System.out.println(String.format("Converting %s", conversionMapName));
			ConversionMap map = mapOfMaps.get(conversionMapName);

			List<Long> newSeeds = new ArrayList<>();
			for (long seed : seeds) {
				long target = map.convert(seed);
				newSeeds.add(target);
			}

			seeds = newSeeds;
			System.out.println(seeds);
		}

		System.out.println(String.format("Final seeds %s", seeds));
		long min = Collections.min(seeds);
		System.out.println("Min seed " + min);
	}

	public void solveB() {

	}

	public String getConversionMapName(String one, String two) {
		return String.format("%s-to-%s", one, two);
	}

	public Map<String, ConversionMap> parseAllMaps(List<List<String>> groupedLines) {
		Map<String, ConversionMap> mapOfMaps = new HashMap<>();

		for (List<String> groupedLine : groupedLines) {
			ConversionMap map = parseMap(groupedLine);
			mapOfMaps.put(map.name, map);
		}

		return mapOfMaps;

	}

	public ConversionMap parseMap(List<String> lines) {
		String name = lines.get(0).split("\\s+")[0];
		System.out.println(name);
		SortedSet<Mapping> mappings = new TreeSet<>();

		for (String line : lines.subList(1, lines.size())) {
			Mapping mapping = parseMapping(line);
			mappings.add(mapping);
		}

		return new ConversionMap(name, mappings);
	}

	public Mapping parseMapping(String line) {
		String[] numbers = line.stripLeading().split("\\s+");
		long destStart = Long.parseLong(numbers[0]);
		long sourceStart = Long.parseLong(numbers[1]);
		long length = Long.parseLong(numbers[2]);

		return new Mapping(destStart, sourceStart, length);
	}

	public List<Long> parseSeeds(List<String> lines) {
		String line = lines.get(0);
		String seedNumbersString = line.split(":")[1];
		String[] seedNumbers = seedNumbersString.stripLeading().split("\\s+");

		List<Long> seeds = new ArrayList<>(); 

		for (int i = 0; i < seedNumbers.length; i++) {
			seeds.add(Long.parseLong(seedNumbers[i]));
		}

		return seeds;
	}

	public List<Seed> parseSeedsB(List<String> lines) {
		String line = lines.get(0);
		String seedNumbersString = line.split(":")[1];
		String[] seedNumbers = seedNumbersString.stripLeading().split("\\s+");

		List<Seed> seeds = new ArrayList<>(); 

		for (int i = 0; i < seedNumbers.length; i+=2) {
			long seed = Long.parseLong(seedNumbers[i]);
			long length = Long.parseLong(seedNumbers[i+1]);
			seeds.add(new Seed(seed, length));
		}

		return seeds;
	}

}
