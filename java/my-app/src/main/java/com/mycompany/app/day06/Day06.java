package com.mycompany.app.day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 {
	public static void main(String[] args) {
		new Day06().solveA();
	}

	public Day06() {}

	public void solveA() {
		System.out.println("hi");
		//List<Race> races = getTestInput();
		List<Race> races = getInputB();

		System.out.println(races);

		List<Long> numbersOfWins = new ArrayList<>();
		for (Race race : races) {
			long wins = numberOfWins(race);
			numbersOfWins.add(wins);
		}

		System.out.println(numbersOfWins);

		long product = 1;
		for (long w : numbersOfWins) {
			product *= w;
		}

		System.out.println(product);
	}

	public long numberOfWins(Race race) {
		long wins = 0;
		for (long i = 0; i <= race.time; i++) {
			if (winsRace(race, i)) {
				wins++;
			}
		}
		return wins;
	}

	public boolean winsRace(Race race, long hold) {
		return runRace(race, hold) > race.distanceRecord;
	}

	public long runRace(Race race, long hold) {
		long secondsRemaining = race.time - hold;
		long distance = secondsRemaining * hold;

		return distance;
	}

	public List<Race> getTestInput() {
		Race race0 = new Race(7, 9);
		Race race1 = new Race(15, 40);
		Race race2 = new Race(30, 200);

		List<Race> races = Arrays.asList(race0, race1, race2);
		return races;
	}

	public List<Race> getTestInputB() {
		Race race0 = new Race(71530, 940200);

		List<Race> races = Arrays.asList(race0);
		return races;
	}


	public List<Race> getInput() {
		Race race0 = new Race(35, 212);
		Race race1 = new Race(93, 2060);
		Race race2 = new Race(73, 1201);
		Race race3 = new Race(66, 1044);

		List<Race> races = Arrays.asList(race0, race1, race2, race3);
		return races;
	}

	public List<Race> getInputB() {
		long l = 212206012011044l;
		Race race0 = new Race(35937366, l);

		List<Race> races = Arrays.asList(race0);
		return races;
	}
}
