package com.mycompany.app.day19;

import java.util.List;

public class Defn {
	public final String name;
	public List<Expression> expressions;

	public Defn(String name, List<Expression> expressions) {
		this.name = name;
		this.expressions = expressions;
	}

	public String toString() {
		return String.format("%s{%s}", name, expressions);
	}
}
