package com.mycompany.app.day07;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mycompany.app.utils.InputReader;

public class Day07 {
	public static void main(String[] args) {
		new Day07().solveA();
	}

	public Day07() {}

	public void solveA() {
		List<Hand> hands = readInput();
		System.out.println(hands);
		SortedSet<Hand> sortedHands = new TreeSet<>(hands);
		Iterator<Hand> it = sortedHands.iterator();

		int i = 1;
		int winnings = 0;
		while (it.hasNext()) {
			Hand hand = it.next();
			int handWinnings = hand.bet * i;
			winnings += handWinnings;
			i++;
		}

		System.out.println(winnings);
		

	}

	public List<Hand> readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day07/part1.txt");

		List<Hand> hands = new ArrayList<>();
		for (String line : lines) {
			String[] split = line.split("\\s+");
			String hand = split[0];
			int bet = Integer.parseInt(split[1]);

			hands.add(new Hand(hand, bet));
		}

		return hands;
	}
}
