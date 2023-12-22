package com.mycompany.app.day22;

public class Coord {
	public final int x;
	public final int y;
	public final int z;

	public Coord(int x, int y, int z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}

	public String toString() {
		return String.format("(%s, %s, %s)", x, y, z);
	}
}

