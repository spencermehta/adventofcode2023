package com.mycompany.app.day12;

import java.util.Objects;

public class Position {
	public final String pattern;
	public final int[] blocks;
	public final int i;

	public Position(String pattern, int[] blocks, int i) {
		this.pattern = pattern;
		this.blocks = blocks;
		this.i = i;
	}

	public int hashCode() {
		return Objects.hash(pattern, blocks, i);
	}
}
