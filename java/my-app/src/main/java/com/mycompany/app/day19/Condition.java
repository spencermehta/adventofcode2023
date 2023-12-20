package com.mycompany.app.day19;

import java.util.Arrays;
import java.util.List;

public class Condition {
	private static final List<Character> categories = Arrays.asList( 'x', 'm', 'a', 's' );
	private static final List<Character> operators = Arrays.asList( '<', '>' );

	public final int category;
	public final int op;
	public final int comparator;

	public Condition(int category, int op, int comparator) {
		this.category = category;
		this.op = op;
		this.comparator = comparator;
	}

	public String toString() {
		return String.format("%s%s%s", categories.get(category), operators.get(op), comparator);
	}

	public boolean eval(int val) {
		if (operators.get(op) == '<') {
			return val < comparator;
		} else {
			return val > comparator;
		}
	}

	public int category() {
		return category;
	}
}
