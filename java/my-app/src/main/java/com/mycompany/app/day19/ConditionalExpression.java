package com.mycompany.app.day19;

public class ConditionalExpression implements Expression {
	public final Condition condition;
	public final String target;

	public ConditionalExpression(Condition condition, String target) {
		this.condition = condition;
		this.target = target;
	}

	public String toString() {
		return String.format("%s:%s", condition, target);
	}

	public boolean eval(int val) {
		return condition.eval(val);
	}

	public int category() {
		return condition.category();
	}

	public String target() {
		return target;
	}

}
