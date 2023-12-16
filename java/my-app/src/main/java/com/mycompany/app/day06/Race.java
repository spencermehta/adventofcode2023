package com.mycompany.app.day06;

public class Race {
	public final long time;
	public final long distanceRecord;

	public Race(long time, long distanceRecord) {
		this.time = time;
		this.distanceRecord = distanceRecord;
	}

	public String toString() {
		return String.format("(%s, %s)", time, distanceRecord);
	}

}
