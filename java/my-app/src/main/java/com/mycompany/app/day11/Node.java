package com.mycompany.app.day11;

public class Node {
	public final int x;
	public final int y;
	public final char c;

	public Node(int x, int y, char c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public String toString() {
		return String.format("%s at (%s, %s)", c, x, y);
	}
}
