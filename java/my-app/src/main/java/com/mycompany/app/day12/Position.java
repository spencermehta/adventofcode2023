package com.mycompany.app.day12;

import java.util.Objects;

public class Position implements Comparable<Position> {
	public final int i;
	public final int bi;
	public final int l;

	public Position(int i, int bi, int l) {
		this.i = i;
		this.bi = bi;
		this.l = l;
	}

	public boolean equals(Object other) {
		Position pos = (Position) other;
		return (i == pos.i && bi == pos.bi && l==pos.l);
	}

	public int hashCode() {
		//System.out.println(Objects.hash(i, bi, l));
		return Objects.hash(i, bi, l);
	}

	public int compareTo(Position p) {
		if (i == p.i && bi == p.bi && l == p.l) {
			return 0;
		}
		return i - p.i;

	}
}
