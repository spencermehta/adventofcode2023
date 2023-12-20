package com.mycompany.app.day19;

public interface Expression {
	String target();
	boolean eval(int val);

	int category();
}
