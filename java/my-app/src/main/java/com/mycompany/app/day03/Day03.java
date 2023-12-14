package com.mycompany.app.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

public class Day03 {
	public static void main(String[] args) {
		try {
			new Day03().solveB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Day03() {
	}

	public void solveA() throws FileNotFoundException {
		List<String> lines = readInput();
		for (String line : lines) {
			System.out.println(line);
		}

		Set<Number> numberSet = getNumberSet(lines);

		for (Number number : numberSet) {
			System.out.println(number);
		}

		Map<Coord, Number> table = buildTable(numberSet);

		for (Entry<Coord, Number> entry : table.entrySet()) {
			System.out.println(String.format("Entry %s : %s", entry.getKey(), entry.getValue()));
		}

		Set<Number> allTouchedNumbers = new HashSet<>();

		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(y).length(); x++) {
				Number n = table.get(new Coord(x, y));
				if (n != null) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}

			System.out.println("");
		}

		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(y).length(); x++) {
				String line = lines.get(y);
				char c = line.charAt(x);

				if (isSymbol(c)) {
					System.out.println(String.format("\nFound symbol %s at (%s, %s)", c, x, y));
					List<Coord> surroundingCoords = getSurroundingCoords(x, y);
					System.out.println(String.format("Surrounding: %s", surroundingCoords));
					for (Coord coord : surroundingCoords) {
						System.out.println(String.format("Checking coord %s", coord));

						Number touchedNumber = table.get(coord);
						if (touchedNumber != null) {
							System.out.println(String.format("Touches %s", touchedNumber));
							allTouchedNumbers.add(touchedNumber);

						} else {
							System.out.println("Doesn't touch any numbers");
						}

					}
					System.out.println("finished symbol");
				}
			}
		}

		System.out.println(allTouchedNumbers);

		int sum = 0;
		for (Number number : allTouchedNumbers) {
			sum += number.number;
		}

		System.out.println("Sum: " + sum);

	}


	public void solveB() throws FileNotFoundException {
		List<String> lines = readInput();
		for (String line : lines) {
			System.out.println(line);
		}

		Set<Number> numberSet = getNumberSet(lines);

		for (Number number : numberSet) {
			System.out.println(number);
		}

		Map<Coord, Number> table = buildTable(numberSet);

		for (Entry<Coord, Number> entry : table.entrySet()) {
			System.out.println(String.format("Entry %s : %s", entry.getKey(), entry.getValue()));
		}

		List<Integer> gearRatios = new ArrayList<>();

		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(y).length(); x++) {
				Number n = table.get(new Coord(x, y));
				if (n != null) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}

			System.out.println("");
		}

		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(y).length(); x++) {
				String line = lines.get(y);
				char c = line.charAt(x);

				if (isSymbol(c) && isStar(c)) {
					System.out.println(String.format("\nFound star %s at (%s, %s)", c, x, y));
					List<Coord> surroundingCoords = getSurroundingCoords(x, y);
					System.out.println(String.format("Surrounding: %s", surroundingCoords));

					Set<Number> touchedNumbers = new HashSet<>();

					for (Coord coord : surroundingCoords) {
						System.out.println(String.format("Checking coord %s", coord));

						Number touchedNumber = table.get(coord);
						if (touchedNumber != null) {
							System.out.println(String.format("Touches %s", touchedNumber));
							touchedNumbers.add(touchedNumber);

						} else {
							System.out.println("Doesn't touch any numbers");
						}

					}

					if (touchedNumbers.size() == 2) {
						int gearRatio = ((Number)touchedNumbers.toArray()[0]).number * ((Number)touchedNumbers.toArray()[1]).number;
						gearRatios.add(gearRatio);


					}
					System.out.println("finished symbol");
				}
			}
		}


		int sum = 0;
		for (Integer number : gearRatios) {
			sum += number;
		}

		System.out.println("Sum: " + sum);

	}

	boolean isStar(char c) {
		return c == '*';
	}

	List<Coord> getSurroundingCoords(int x, int y) {
		List<Coord> coords = new ArrayList<>();

		for (int i = x-1; i <= x+1; i++) {
			for (int j = y-1; j <= y+1; j++) {
				Coord coord = new Coord(i, j);
				coords.add(coord);
			}
		}

		return coords;

	}

	Map<Coord, Number> buildTable(Set<Number> numberSet) {
		Map<Coord, Number> table = new HashMap<>();

		for (Number number : numberSet) {
			for (int x = number.start; x < number.end; x++) {
				Coord coord = new Coord(x, number.row);

				table.put(coord, number);
			}
		}

		return table;
	}


	Set<Number> getNumberSet(List<String> lines) {
		Set<Number> numberSet = new HashSet<>();

		for (int i = 0; i < lines.size(); i++) {
			List<Number> numbers = parseLine(i, lines.get(i));
			for (Number number : numbers) {
				numberSet.add(number);
			}
		};

		return numberSet;
	}

	List<Number> parseLine(int row, String line) {
		line += ".";
		List<Number> numbers = new ArrayList<>();


		int start = 0;
		String current = "";
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (isDigit(c)) {
				if (current.length() == 0) {
					start = i;
				}
				current += c;
			} else {
				if (current.length() > 0) {
					Number newNumber = new Number(Integer.parseInt(current), row, start, i);
					numbers.add(newNumber);
					current = "";
				}
			}
		}

		return numbers;
	}

	boolean isDigit(char c) {
		List<Character> digits = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
		return digits.contains(Character.valueOf(c));
	}


	boolean isSymbol(char c) {
		return !isDigit(c) && (c != '.');
	}




	List<String> readInput() throws FileNotFoundException {
		Scanner s = new Scanner(new File("/home/spencer/projects/hobby/aoc/2023/ocaml/input/day03.txt"));
		List<String> list = new ArrayList<>();
		while (s.hasNext()) {
			String line = s.next();
			list.add(line);
		}
		return list;
	}
}
