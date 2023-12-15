package com.mycompany.app.day05;

public class Mapping implements Comparable<Mapping> {
	public final long destStart;
	public final long sourceStart;
	public final long length;

	public Mapping(long destStart, long sourceStart, long length) {
		this.destStart = destStart;
		this.sourceStart = sourceStart;
		this.length = length;
	}

	public boolean inRange(long x) {
		return (x >= sourceStart) && (x < (sourceStart + length)); 
	}

	public long convert(long source) {
		return destStart + (source - sourceStart);
	}

	@Override
    public int compareTo(Mapping other) { 
		if (sourceStart < other.sourceStart) {
			return -1;
		} else if (sourceStart == other.sourceStart) {
			return 0;
		} else {
			return 1;
		}
    } 

}
