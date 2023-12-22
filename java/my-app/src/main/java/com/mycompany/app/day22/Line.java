package com.mycompany.app.day22;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line> {
	public final Coord start;
	public final Coord finish;
	public List<Line> supporting;
	public List<Line> supportedBy;

	public Line(Coord start, Coord finish) {
		this.start = start;
		this.finish = finish;
		supporting = new ArrayList<>();
		supportedBy = new ArrayList<>();
	}

	public String toString() {
		return String.format("%s->%s", start, finish);
	}

	public int compareTo(Line o) {
		return start.z - o.start.z;
	}

	public boolean intersects(Line o) {
		return ((start.x <= o.finish.x) && (o.start.x <= o.finish.x))
			&& ((start.y <= o.finish.y) && (o.start.y <= o.finish.y));
	}

	public int zLength() {

		return finish.z - start.z;
	}
}
