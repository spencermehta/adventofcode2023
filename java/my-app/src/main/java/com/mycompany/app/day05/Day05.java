package com.mycompany.app.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mycompany.app.utils.InputReader;

public class Day05 {
	public static void main(String[] args) {
		new Day05().solveB();
	}

	public Day05() {}

	public void solveA() {
		List<List<String>> groupedLines = new InputReader()
			.readInputBreakAtBlankLines("/home/spencer/projects/hobby/aoc/2023/input/day05/test.txt");

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
		List<List<String>> groupedLines = new InputReader()
			.readInputBreakAtBlankLines("/home/spencer/projects/hobby/aoc/2023/input/day05/part1.txt");

		System.out.println(groupedLines);

		List<Seed> seeds = parseSeedsB(groupedLines.get(0));
		System.out.println(seeds);

		Map<String, ConversionMap> mapOfMaps = parseAllMaps(groupedLines.subList(1, groupedLines.size()));
		for (ConversionMap map : mapOfMaps.values()) {
			System.out.println("---");
			Iterator<Mapping> it = map.mappingsIterator();
			while (it.hasNext()) {
				System.out.println(it.next());

			}
		}

		List<String> order = Arrays.asList("seed", "soil", "fertilizer", "water", "light", "temperature", "humidity", "location");
		// seeds = seeds.subList(0, 1);

		for (int i = 0; i < order.size() - 1; i++) {
			String one = order.get(i);
			String two = order.get(i+1);
			String conversionMapName = getConversionMapName(one, two);
			System.out.println(String.format("\n---\nConverting %s", conversionMapName));
			ConversionMap map = mapOfMaps.get(conversionMapName);


			List<Seed> newSeeds = new ArrayList<>();

			for (Seed seed : seeds) {
				System.out.println("\nlooking at " + seed);

				Iterator<Mapping> mappings = map.mappingsIterator();

				while (mappings.hasNext()) {
					Mapping mapping = mappings.next();
					long seedEnd = seed.start + seed.length - 1;
					long mappingEnd = mapping.sourceStart + mapping.length - 1;

					long newStart = Math.max(seed.start, mapping.sourceStart);
					long newEnd = Math.min(seedEnd, mappingEnd);
					System.out.println("Mapping " + mapping + " yield " + newStart + ", " + newEnd);

					if ((newEnd - newStart) > 0) {
						long translatedNewStart = mapping.destStart + (newStart - mapping.sourceStart);
						long newLength = newEnd - newStart + 1;
						Seed intersectionSeed = new Seed(translatedNewStart, newLength);
						System.out.println("Creating intersectionSeed "+ intersectionSeed);
						seed = new Seed(newEnd, seedEnd - newEnd);
						System.out.println("Updating seed to " + seed);
						newSeeds.add(intersectionSeed);
					}
				}
			}
			seeds = newSeeds;
		}


		System.out.println(Collections.min(seeds));
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

		Iterator<Mapping> it = mappings.iterator();
		SortedSet<Mapping> inbetweenMappings = new TreeSet<>();
		long last = 0;
		while (it.hasNext()) {
			Mapping mapping = it.next();
			if (mapping.sourceStart > last) {
				inbetweenMappings.add(new Mapping(last, last, (mapping.sourceStart - last)));
				last = mapping.sourceStart + mapping.length - 1; 
			}
		}
		long max = 100;
		if (last < max) {
			inbetweenMappings.add(new Mapping(last, last, max - last + 1));
		}

		mappings.addAll(inbetweenMappings);

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
