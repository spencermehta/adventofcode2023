package com.mycompany.app.day11;

import java.util.Objects;

public class NodePair implements Comparable<NodePair> {
	public final Node left;
	public final Node right;

	public NodePair(Node left, Node right) {
		this.left = left;
		this.right = right;
	}

	public boolean equals(Object other) {
		NodePair o = (NodePair) other;
		return ((left.equals(o.left)) && (right.equals(o.right)))
			|| ((left.equals(o.right)) && (right.equals(o.left)));
	}


	public String toString() {
		return String.format( "{ %s, %s }", left, right);
	}

	public int compareTo(NodePair other) {
		if (this.equals(other)) {
			return 0;
		}
		return (this.left.x - other.left.x);
	}

	public int hashCode() {
		return Objects.hash(left, right) + Objects.hash(right, left);
	}

}
