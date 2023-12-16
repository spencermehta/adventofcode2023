package com.mycompany.app.day08;

public class Node {
	public final String left;
	public final String right;

	public Node(String left, String right) {
		this.left = left;
		this.right = right;
	}

	public String toString() {
		return String.format("(%s, %s)", left, right);
	}
}
