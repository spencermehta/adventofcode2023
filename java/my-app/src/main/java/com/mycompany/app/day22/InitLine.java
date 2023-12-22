package com.mycompany.app.day22;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InitLine implements Comparable<InitLine> {
	public final Coord start;
	public final Coord finish;
	public List<Line> supporting;
	public List<Line> supportedBy;

	public InitLine(Coord start, Coord finish) {
		this.start = start;
		this.finish = finish;
		supporting = new ArrayList<>();
		supportedBy = new ArrayList<>();
	}

	public String toString() {
		return String.format("%s->%s", start, finish);
	}

	public int compareTo(InitLine o) {
		return start.z - o.start.z;
	}

	public boolean intersects(Line o) {
		return ((start.x <= o.finish.x) && (o.start.x <= finish.x))
			&& ((start.y <= o.finish.y) && (o.start.y <= finish.y));
	}

	public int zLength() {
		return finish.z - start.z;
	}

	public boolean equals(Object other) {
		Line o = (Line) other;
		return (start.x == o.start.x) && (start.y == o.start.y) && (start.z == o.start.z) &&
			(finish.x == o.finish.x) && (finish.y == o.finish.y) && (finish.z == o.finish.z);
	}

	public int hashCode() {
		return Objects.hash(start.x, start.y, start.z, finish.x, finish.y, finish.z);
	}
}
