package com.mycompany.app.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module {
	static List<Character> moduleTypes = Arrays.asList( '%', '&' );

	public final String name;
	public final int type;
	public final String[] destinations;

	boolean state;
	Map<String, Boolean> inputs = new HashMap<>();

	public Module(String name, int type, String[] destinations) {
		this.name = name;
		this.type = type;
		this.destinations = destinations;
		state = false;
	}

	public List<Pulse> pulse(Pulse pulse) {
		if (type == -1) {
			return sendToDest(pulse.high);
		}

		if (moduleTypes.get(type) == '%') {
			if (pulse.high) {
				return Collections.emptyList();
			}

			state = !state;
			return sendToDest(state);
		}

		if (moduleTypes.get(type) == '&') {
			inputs.put(pulse.from, pulse.high);

			boolean sendLow = true;
			for (boolean b : inputs.values()) {
				if (!b) {
					sendLow = false;
				}

			}

			return sendToDest(!sendLow);
		}

		throw new RuntimeException("invalid module type");
	}

	List<Pulse> sendToDest(boolean high) {
		List<Pulse> pulses = new ArrayList<>();

		for (String d : destinations) {
			pulses.add(new Pulse(name, d, high));
		}

		return pulses;
	}

	void addInput(String name) {
		inputs.put(name, false);
	}

	public String toString() {
		if (type == -1) {
			return String.format("%s -> %s", name, Arrays.toString(destinations));
		} else {
			return String.format("%s%s -> %s", moduleTypes.get(type), name, Arrays.toString(destinations));
		}
	}

}
