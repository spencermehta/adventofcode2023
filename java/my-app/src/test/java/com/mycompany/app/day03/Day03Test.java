package com.mycompany.app.day03;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

public class Day03Test {
	private Day03 day03;

	@Before
	public void setUp() {
		day03 = new Day03();
	}

    @Test
    public void testNoDupeNumbersInSet() {
		Number number1 = new Number(123, 0, 0, 1);
		Number number2 = new Number(123, 0, 0, 1);

		Set<Number> numberSet = new HashSet<>();
		numberSet.add(number1);
		numberSet.add(number2);

        assertEquals(1, numberSet.size());
    }

	@Test
	public void testEndOfLine() throws FileNotFoundException {
		List<String> lines = Arrays.asList("...123...123");

		List<Number> numbers = day03.parseLine(0, lines.get(0));
		assertEquals(2, numbers.size());

		Set<Number> numberSet = day03.getNumberSet(lines);

		assertEquals(2, numberSet.size());
	}

	@Test
	public void testStartOfLine() throws FileNotFoundException {
		List<String> lines = Arrays.asList("123...123...123");

		List<Number> numbers = day03.parseLine(0, lines.get(0));
		assertEquals(3, numbers.size());

		Set<Number> numberSet = day03.getNumberSet(lines);

		assertEquals(3, numberSet.size());
	}

	@Test
	public void testAdjacent() throws FileNotFoundException {
		List<String> lines = Arrays.asList("123-123...123");

		List<Number> numbers = day03.parseLine(0, lines.get(0));
		assertEquals(3, numbers.size());

		Set<Number> numberSet = day03.getNumberSet(lines);

		assertEquals(3, numberSet.size());
	}


	@Test
	public void testGetSurroundingCoords() {
		List<Coord> actual = day03.getSurroundingCoords(0, 0);
		System.out.println(actual);
	}

	@Test
	public void testNumberEquality() {
		Number number1 = new Number(123, 0, 0, 1);
		Number number2 = new Number(123, 0, 3, 4);

		Set<Number> numberSet = new HashSet<>();
		numberSet.add(number1);
		numberSet.add(number2);

        assertEquals(2, numberSet.size());
	}

	@Test
	public void testCoordEquality() {
		Set<Coord> coords = new HashSet<>();
		Coord coord1 = new Coord(0, 0);
		Coord coord2 = new Coord(0, 0);

		coords.add(coord1);
		coords.add(coord2);

		assertEquals(1, coords.size());
	}

	@Test
	public void testCoordEquality2() {
		Set<Coord> coords = new HashSet<>();
		Coord coord1 = new Coord(130, 0);
		Coord coord2 = new Coord(0, 130);

		coords.add(coord1);
		coords.add(coord2);

		assertEquals(2, coords.size());

		System.out.println(coord1.hashCode()+ " - " +  coord2.hashCode());
	}
}
