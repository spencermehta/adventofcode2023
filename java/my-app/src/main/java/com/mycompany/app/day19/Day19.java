package com.mycompany.app.day19;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.mycompany.app.utils.InputReader;

public class Day19 {
	private final List<Character> categories = Arrays.asList( 'x', 'm', 'a', 's' );
	private final List<Character> operators = Arrays.asList( '<', '>' );

	Map<String, Defn> defns = new HashMap<>();
	List<int[]> parts = new ArrayList<>();

	public static void main(String[] args) {
		new Day19().solveA();
	}
	
	int sum = 0;

	public void solveA() {
		readInput();
		for (int[] part : parts) {
			boolean accepted = eval("in", part);
			if (accepted) {
				for (int category : part) {
					sum += category;
				}
			}

		}

		System.out.println(sum);

	}

	boolean eval(String name, int[] part) {
		if (name.equals("A")) {
			return true;
		} else if (name.equals("R")) {
			return false;
		}

		int exprI = 0;
		Defn d = defns.get(name);

		Expression expr = d.expressions.get(exprI);
		int category = expr.category();
		int val = part[category];

		while (!expr.eval(val)) {
			exprI++;

			expr = d.expressions.get(exprI);
			category = expr.category();
			val = part[category];
		}

		return eval(expr.target(), part);
	}

	void readInput() {
		List<List<String>> groupedLines = 
			new InputReader().readInputBreakAtBlankLines("/home/spencer/projects/hobby/aoc/2023/input/day19/input.txt");

		List<String> defnsStrings = groupedLines.get(0);

		for (String defnString : defnsStrings) {
			Defn d = parseDefn(defnString);
			defns.put(d.name, d);
		}

		for (Defn d : defns.values()) {
			System.out.println(d);
		}

		List<String> partsStrings = groupedLines.get(1);

		for (String partString : partsStrings) {
			parts.add(parsePart(partString));
		}
	}

	Defn parseDefn(String line) {
		line = line.replace("}", "");
		String name = line.split("[{]")[0];
		String[] workflows = line.split("[{]")[1].split(",");
		List<Expression> exprs = parseWorkflows(workflows);

		return new Defn(name, exprs);
	}

	List<Expression> parseWorkflows(String[] workflows) {
		List<Expression> exprs = new ArrayList<>();
		for (String expr : workflows) {
			if (expr.contains(":")) {
				String condition = expr.split(":")[0];
				String target = expr.split(":")[1];

				exprs.add(new ConditionalExpression(parseCondition(condition), target));
			} else {
				exprs.add(new UnconditionalExpression(expr));
			}
		}

		return exprs;

	}

	Condition parseCondition(String condition) {
		if (condition.contains("<")) {
			char op = '<';
			char category = condition.split("<")[0].charAt(0);
			int comparator = Integer.parseInt(condition.split("<")[1]);

			return new Condition(categories.indexOf(category), operators.indexOf(op), comparator);
		} else {
			char op = '>';
			char category = condition.split(">")[0].charAt(0);
			int comparator = Integer.parseInt(condition.split(">")[1]);

			return new Condition(categories.indexOf(category), operators.indexOf(op), comparator);
		}
	}

	int[] parsePart(String line) {
		String[] split = line.replace("{", "").replace("}", "").split(",");
		int[] part = new int[4];
		for (String s : split) {
			char category = s.split("=")[0].charAt(0);
			int rating = Integer.parseInt(s.split("=")[1]);

			part[categories.indexOf(category)] = rating;
		}

		System.out.println(Arrays.toString(part));
		return part;
	}
}
