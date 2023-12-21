package com.mycompany.app.day20;

public class Pulse {
	public final String from;
	public final String to;
	public final boolean high;

	public Pulse(String from, String to, boolean high) {
		this.from = from;
		this.to = to;
		this.high = high;
	}

	public String toString() {
		return String.format("%s -%s -> %s", from, high, to);
	}

}
