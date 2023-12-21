package com.mycompany.app.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.mycompany.app.utils.InputReader;

public class Day20 {

	Map<String, Module> modules = new HashMap<>();
	List<Character> moduleTypes = Arrays.asList( '%', '&' );
	long low = 0;
	long high = 0;
	Queue<Pulse> queue = new LinkedList<>();

	public static void main(String[] args) {
		new Day20().solveB();
	}

	void solveA() {
		readInput();
		System.out.println(modules);

		initialiseInputs();

		int buttons = 0;
		while (buttons < Long.MAX_VALUE) {
			buttons++;
			queue.add(new Pulse("button", "broadcaster", false));
			while (queue.peek() != null) {
				Pulse p = queue.poll();
				pulse(p);
			}
		}

		System.out.println(low);
		System.out.println(high);
		System.out.println(low * high);
	}

	/*
&jq -> rx

&gt -> jq
&vr -> jq
&nl -> jq
&lr -> jq
	*/

	List<String> rxMinusTwo = Arrays.asList("gt", "vr", "nl", "lr");
	Map<String, Integer> loops = new HashMap<>();
	Map<String, Integer> encountered = new HashMap<>();
	List<Integer> loopLengths = new ArrayList<>();

	int buttons = 0;

	void solveB() {
		readInput();
		System.out.println(modules);

		initialiseInputs();

		while (buttons < Long.MAX_VALUE) {
			buttons++;
			System.out.println(buttons);
			queue.add(new Pulse("button", "broadcaster", false));
			while (queue.peek() != null) {
				Pulse p = queue.poll();
				pulse(p);
			}
		}

		System.out.println(low);
		System.out.println(high);
		System.out.println(low * high);
	}

	void pulse(Pulse pulse) {
		if (pulse.high) {
			high++;
		} else {
			low++;
		}

		if (pulse.high) {
			int e = encountered.getOrDefault(pulse.from, 0); 
			if (rxMinusTwo.contains(pulse.from) && (e == 2) && (loops.containsKey(pulse.from))) {
				loopLengths.add(buttons-loops.get(pulse.from));
			} 

			int x = encountered.getOrDefault(pulse.from, 0);
			encountered.put(pulse.from, x+1);
			loops.put(pulse.from, buttons);
		}

		if (loopLengths.size() == rxMinusTwo.size()) {
			System.out.println(loopLengths);
			// paste the above into python math.lcm()
			System.exit(0);
		}

		//System.out.println(pulse);
		if (!modules.containsKey(pulse.to)) {
			return;
		}
		Module m = modules.get(pulse.to);
		List<Pulse> nextPulses = m.pulse(pulse);
		queue.addAll(nextPulses);
	}

	void initialiseInputs() {
		for (Module m : modules.values()) {
			for (String dest : m.destinations) {
				if (!modules.containsKey(dest)) {
					continue;
				}
				Module other = modules.get(dest);
				other.addInput(m.name);
				modules.put(dest, other);
			}
		}
	}

	void readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day20/input.txt");
		for (String line : lines) {
			String lhs = line.split(" -> ")[0];
			String name = (lhs.contains("%") || lhs.contains("&")) ? lhs.substring(1) : lhs;
			int type =(lhs.contains("%") || lhs.contains("&")) ? moduleTypes.indexOf(lhs.charAt(0)) : -1; 



			String rhs = line.split(" -> ")[1];
			String[] destinations = rhs.split(", ");

			modules.put(name, new Module(name, type, destinations));
		}
	}
}
