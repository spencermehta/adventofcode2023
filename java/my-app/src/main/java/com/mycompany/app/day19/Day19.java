package com.mycompany.app.day19;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
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
		new Day19().solveB();
	}
	

	public void solveA() {
		int sum = 0;
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

	public void solveB() {
		readInput();

		int[][] ranges = {{1,4000}, {1,4000}, {1,4000}, {1,4000}};

		BigDecimal sum = evalRange("in", ranges);

		System.out.println(sum);
	}

	BigDecimal combinations(int[][] ranges) {
		int xWidth = ranges[0][1] - ranges[0][0] + 1;
		int mWidth = ranges[1][1] - ranges[1][0] + 1;
		int aWidth = ranges[2][1] - ranges[2][0] + 1;
		int sWidth = ranges[3][1] - ranges[3][0] + 1;
		System.out.println(String.format("%s %s %s %s", xWidth, mWidth, aWidth, sWidth));
		return new BigDecimal(xWidth).multiply(new BigDecimal(mWidth)).multiply(new BigDecimal(aWidth)).multiply(new BigDecimal(sWidth));

	}

	BigDecimal evalRange(String name, int[][] ranges) {

		for (int[] r : ranges) {
			if (r[0] >= r[1]) {
				System.out.println("rejecting " + rangesToStr(ranges));
				return new BigDecimal(0);
			}
		}

		if (name.equals("A")) {
			System.out.println("accepting " + combinations(ranges) + " for " + rangesToStr(ranges));
			return combinations(ranges); 
		} else if (name.equals("R")) {
			System.out.println("rejecting " + rangesToStr(ranges));
			return new BigDecimal(0);
		}


		Defn d = defns.get(name);
		System.out.println("\n" + d);
		BigDecimal sum = new BigDecimal(0);
		for (Expression expr : d.expressions) {
			if (expr instanceof UnconditionalExpression) {
				System.out.println("uncond " + rangesToStr(ranges));
				sum = sum.add(evalRange(expr.target(), copy(ranges)));
			}
			else if (expr instanceof ConditionalExpression) {
				Condition condition = ((ConditionalExpression)expr).condition;

				if (operators.get(condition.op) == '<') {
					int[][] newRanges = copy(ranges);
					newRanges[condition.category][1] = condition.comparator-1;
					ranges[condition.category][0] = condition.comparator;

					System.out.println("cond " + rangesToStr(newRanges));
					sum = sum.add(evalRange(expr.target(), newRanges));
				} else { 
					int[][] newRanges = copy(ranges);
					newRanges[condition.category][0] = condition.comparator+1;
					ranges[condition.category][1] = condition.comparator;
					System.out.println("cond " + rangesToStr(ranges));
					sum = sum.add(evalRange(expr.target(), newRanges));
				}
			}

		}

		return sum;
	}

	String rangesToStr(int[][] ranges) {
		String s = "";
		for (int[] r : ranges) {
			s += String.format("(%s, %s)", r[0], r[1]);
		}

		return s;
	}

	int[][] copy(int[][] arr) {
		int[][] n = new int[arr.length][arr[0].length];
		for (int y = 0; y < arr.length; y++) {
			for (int x=0; x < arr[0].length; x++) {
				n[y][x] = arr[y][x];
			}
		}

		return n;
	}


	String hash(String name, int[] part) {
		return String.format("%s,%s,%s,%s,%s", name, part[0], part[1], part[2], part[3]);
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

		boolean res = eval(expr.target(), part);
		return res;
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
