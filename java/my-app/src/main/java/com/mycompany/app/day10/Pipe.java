package com.mycompany.app.day10;

import java.util.Arrays;
import java.util.List;

public class Pipe {
	/*

	| is a vertical pipe connecting north and south.
	- is a horizontal pipe connecting east and west.
	L is a 90-degree bend connecting north and east.
	J is a 90-degree bend connecting north and west.
	7 is a 90-degree bend connecting south and west.
	F is a 90-degree bend connecting south and east.

	*/
	private final List<Character> north = Arrays.asList('|', 'L', 'J', 'S');
	private final List<Character> south = Arrays.asList('|', '7', 'F', 'S');
	private final List<Character> east = Arrays.asList('-', 'L', 'F', 'S');
	private final List<Character> west = Arrays.asList('-', 'J', '7', 'S');


	public final char c;

	public Pipe(char c) {
		this.c = c;
	}

	public String toString() {
		return String.format("%s", c);
	}


	public boolean connectsNorth() {
		return north.contains(c);
	}

	public boolean connectsSouth() {
		return south.contains(c);
	}

	public boolean connectsWest() {
		return west.contains(c);
	}

	public boolean connectsEast() {
		return east.contains(c);
	}
}
