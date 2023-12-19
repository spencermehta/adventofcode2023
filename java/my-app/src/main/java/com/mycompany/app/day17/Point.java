package com.mycompany.app.day17;

public class Point implements Comparable<Point> {
	public final int x;
	public final int y;
	public final int numInThisDir;
	public final int dir;
	public int weightSoFar;

	public Point(int x, int y, int numInThisDir, int dir, int weightSoFar) {
		this.x = x;
		this.y = y;
		this.numInThisDir = numInThisDir;
		this.dir = dir;
		this.weightSoFar = weightSoFar;
	}

	public String toString() {
		return String.format("{(%s, %s) d%s id%s w%s}", x, y, dir, numInThisDir, weightSoFar);
	}

	public int compareTo(Point other) {
		return weightSoFar - other.weightSoFar;
	}

}
