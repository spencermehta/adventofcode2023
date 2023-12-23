package com.mycompany.app.day23;

import java.util.*;

public class Node implements Comparable<Node> {
    public final int x;
    public final int y;
    public final char c;
    public int depth = 0;
    public boolean visited = false;

    public Node(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public boolean equals(Object other) {
        Node o = (Node) other;
        return (x == o.x) && (y == o.y);
    }

    public String toString() {
        return String.format("(%s, %s)", x, y);
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int compareTo(Node o) {
        if (this.equals(o)) {
            return 0;
        }
        return x- o.x;
    }
}
