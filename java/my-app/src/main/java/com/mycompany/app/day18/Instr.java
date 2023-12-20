package com.mycompany.app.day18;

import java.util.Arrays;
import java.util.List;

public class Instr {
	private final List<Character> dirs = Arrays.asList( 'R', 'L', 'D', 'U' );
	public final int dir;
	public final int steps;
	public final String colour;

	public Instr(int dir, int steps, String colour) {
		this.dir = dir;
		this.steps = steps;
		this.colour = colour;
	}

	public String toString() {
		return String.format("%s %s %s", dirs.get(dir), steps, colour);
	}

}
