package com.mycompany.app.day05;

public class Seed implements Comparable<Seed> {
	public final long start;
	public final long length;

	public Seed(long start, long length) {
		this.start = start;
		this.length = length;
	}

	@Override
    public int compareTo(Seed other) { 
		return Long.valueOf(start).compareTo(Long.valueOf(other.start));
    } 


	public String toString() {
		return String.format("Seed (%s, %s)", start, length);
	}

}
