package com.mycompany.app.day21;

import java.util.Objects;

public class Point implements Comparable<Point> {
	public final int x;
	public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return String.format("(%s, %s)", x, y);
	}

	public boolean equals(Object other) {
		Point o = (Point) other;

		return ((x == o.x) && (y == o.y));
	}

	public int compareTo(Point other) {
		if (x == other.x && y == other.y) {
			return 0;
		}

		return x - other.x;
	}

	public int hashCode() {
		return Objects.hash(x, y);
	}

}
