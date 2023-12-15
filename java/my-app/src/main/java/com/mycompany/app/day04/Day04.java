package com.mycompany.app.day04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Day04 {

	public static void main(String[] args) {
		new Day04().solveB();
	}

	public Day04 () {}

	void solveA() {
		List<String> lines = getInput();

		int sum = 0;

		for (String line : lines) {
			System.out.println(line);
			Card card = Card.fromString(line);
			System.out.println(card);
			System.out.println(card.score());
			sum += card.score();
		}

		System.out.println(sum);
	}

	void solveB() {
		List<String> lines = getInput();


		Map<Integer, Integer> map = new HashMap<>();

		int i = 1;
		for (String line : lines) {
			System.out.println(line);
			Card card = Card.fromString(line);
			int instancesOfCard = map.getOrDefault(i, 0);
			System.out.println(String.format("Already had %s instances of card %s", instancesOfCard, card));
			int instances = instancesOfCard + 1;
			map.put(i, instances);

			int intersectionSize = card.intersection().size();
			System.out.println(String.format("Card %s wins %s times", i, intersectionSize));

			for (int j = i+1; j < i + intersectionSize+1; j++) {
				int instancesOfJ = map.getOrDefault(j, 0);
				System.out.println(String.format("Adding %s of card %s to existing %s", instances, j, instancesOfJ));
				map.put(j, instances + instancesOfJ);
			}

			i++;
		}

		int sum = 0;
		for (int v : map.values()) {
			sum += v;
		}

		System.out.println(sum);


	}

	List<String> getInput() {
		String path = "/home/spencer/projects/hobby/aoc/2023/input/day04/part1.txt";
		return new InputReader().readInput(path);
	}
}
