package com.mycompany.app.day04;

import java.util.HashSet;
import java.util.Set;

public class Card {
	public final int number;
	public final Set<Integer> winning;
	public final Set<Integer> having;

	public Card (int number, Set<Integer> winning, Set<Integer> having) {
		this.number = number;
		this.winning = winning;
		this.having = having;
	}

	public static Card fromString(String string) {
		String[] splitres = string.split("[:|]");

		int number = Integer.parseInt(splitres[0].split("\\s+")[1]);


		Set<Integer> winning = new HashSet<Integer>();
		String[] splitwinning = splitres[1].split("\\s+");
		for (int i = 1; i < splitwinning.length; i ++) {
			winning.add(Integer.parseInt(splitwinning[i]));
		}


		Set<Integer> having = new HashSet<Integer>();
		String[] splithaving = splitres[2].split("\\s+");
		for (int i = 1; i < splithaving.length; i ++) {
			having.add(Integer.parseInt(splithaving[i]));
		}

		return new Card(number, winning, having);
	}

	static int getNumber(String string) {
		return Integer.parseInt(string.split(" ")[1]);
	}

	static Set<Integer> getWinning(String string) {
		String[] splitres = string.split(" ");
		Set<Integer> winning = new HashSet<Integer>();

		for (int i = 1; i < splitres.length; i ++) {
			System.out.println(splitres[i]);
			winning.add(Integer.parseInt(splitres[i]));
		}

		return winning;
	}

	public Set<Integer> intersection() {
		Set<Integer> s = new HashSet<>(winning);
		s.retainAll(having);
		return s;

	}

	public int score() {
		int size = intersection().size();
		int score = 0;
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				score = 1;
			} else {
				score *= 2;
			}

		}
		return score;
	}

	public String toString() {
		return String.format("Card %s: %s | %s", number, winning, having);
	}

}
