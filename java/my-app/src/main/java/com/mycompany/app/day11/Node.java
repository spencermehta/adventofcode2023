package com.mycompany.app.day11;

import java.util.Objects;

public class Node implements Comparable<Node> {
	public final int x;
	public final int y;
	public final char c;
	public boolean visited = false;
	public int depth;

	public Node(int x, int y, char c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public String toString() {
		return String.format("%s at (%s, %s)", c, x, y);
	}

	public boolean equals(Object other) {
		Node o = (Node) other;

		return (x == o.x && y == o.y);
	}

	public int compareTo(Node other) {
		if (this.equals(other)) {
			return 0;
		}
		return x - other.x;
	}

	public int hashCode() {
		return Objects.hash(x, y);
	}
}
