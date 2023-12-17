package com.mycompany.app.day12;

public class Record {
	public final String pattern;
	public final int[] numbers;

	public Record(String pattern, int[] numbers) {
		this.pattern = pattern;
		this.numbers = numbers;
	}

	public String toString() {
		return String.format("%s %s", pattern, numbersToString());
	}

	public String numbersToString() {
		String s = "[ ";
		for (int i = 0; i < numbers.length; i++) {
			s += numbers[i] + " ";
		}
		s += "]";

		return s;
	}
}
