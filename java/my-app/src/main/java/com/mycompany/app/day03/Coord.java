package com.mycompany.app.day03;

import java.util.Objects;

public class Coord {
	public final int x;
	public final int y;

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return String.format("(%s, %s)", x, y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Coord)) {
			return false;
		}

		Coord o = (Coord) other;

		return (this.x == o.x && this.y == o.y);
	}
}
