package com.mycompany.app.day03;

import java.util.Objects;

public class Number {
	public final int number;
	public final int row;
	public final int start;
	public final int end;

	public Number(int number, int row, int start, int end) {
		this.number = number;
		this.row = row;
		this.start = start;
		this.end = end;
	}


	public String toString() {
		return String.format("Number { %s row %s, %s -> %s }", number, row, start, end);
	}

	@Override
	public int hashCode() {
		return Objects.hash(number, row, start, end);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Number)) {
			return false;
		}

		Number number = (Number) other;

		return (this.number == number.number &&
				this.row == number.row &&
				this.start == number.start &&
				this.end == number.end);
	}
}
