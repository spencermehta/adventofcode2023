package com.mycompany.app.day10;

public class Node {
	public final Pipe pipe;
	public final int x;
	public final int y;
	public boolean visited = false;
	public int depth;

	public Node(Pipe pipe, int x, int y) {
		this.pipe = pipe;
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return String.format("(%s at %s, %s)", pipe, x, y);
	}
}
