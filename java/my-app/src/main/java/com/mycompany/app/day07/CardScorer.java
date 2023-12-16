package com.mycompany.app.day07;

import java.util.Map;
import java.util.HashMap;

public class CardScorer {
	private static CardScorer cardScorer;

	public static CardScorer get() {
		if (cardScorer == null) {
			return new CardScorer();
		}
		return cardScorer;
	}

	private final Map<Character, Integer> map;

	private CardScorer() {
		map = new HashMap<>();
		map.put('A', 14);
		map.put('K', 13);
		map.put('Q', 12);
		map.put('J', 11);
		map.put('T', 10);
		map.put('9', 9);
		map.put('8', 8);
		map.put('7', 7);
		map.put('6', 6);
		map.put('5', 5);
		map.put('4', 4);
		map.put('3', 3);
		map.put('2', 2);
		map.put('J', 1);
	}

	public int getScore(char c) {
		return map.get(c);
	}
}
