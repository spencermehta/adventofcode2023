package com.mycompany.app.day19;

public class UnconditionalExpression implements Expression {
	public final String target;

	public UnconditionalExpression(String target) {
		this.target = target;
	}

	public String toString() {
		return String.format("%s", target);
	}

	public boolean eval(int val) {
		return true;
	}

	public int category() {
		return 0;
	}

	public String target() {
		return target;
	}
}
