package com.mycompany.app.day07;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Hand implements Comparable<Hand> {
	public final String hand;
	public final int bet;

	public Hand(String hand, int bet) {
		this.hand = hand;
		this.bet = bet;
	}

	public String toString() {
		return String.format("(%s, %s)", hand, bet);
	}

	public int getCategory() {
		// 0 = high card
		// ...
		// 6 = five of a kind
		Map<Character, Integer> chars = new HashMap<>();
		for (char c : hand.toCharArray()) {
			int count = chars.getOrDefault(c, 0);
			chars.put(c, ++count);
		}

		int numJokers = chars.getOrDefault('J', 0);
		chars.remove('J');
		int numDiffChars = Math.max(chars.size(), 1);
		System.out.println(chars.values());
		int maxNumSameChar = 0;
		if (chars.values().size() > 0) {
			maxNumSameChar = Collections.max(chars.values()) + numJokers;
		} else {
			maxNumSameChar = numJokers;
		}

		if (numDiffChars == 1) {
			return 6;
		}
		if (numDiffChars == 2) {
			if (maxNumSameChar == 4) {
				return 5;
			} else {
				return 4;
			}
		}
		if (numDiffChars == 3) {
			if (maxNumSameChar == 3) {
				return 3;
			} else {
				return 2;
			}
		}
		if (numDiffChars == 4) {
			return 1;
		}
		if (numDiffChars == 5) {
			return 0;
		}
		throw new RuntimeException();
	}

	public int compareTo(Hand other) {
		int category = getCategory();
		int otherCategory = other.getCategory();

		if (category != otherCategory) {
			return category - otherCategory;
		}

		CardScorer cardScorer = CardScorer.get();

		for (int i = 0; i < hand.length(); i++) {
			char card = hand.charAt(i);
			char otherCard = other.hand.charAt(i);

			int score = cardScorer.getScore(card);
			int otherScore = cardScorer.getScore(otherCard);

			if (score != otherScore) {
				return score - otherScore;
			} 
		}

		return 0;
	}

}
