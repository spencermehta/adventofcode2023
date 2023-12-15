package com.mycompany.app.day05;

import java.util.Map;

public class ConversionMap {
	public final String name;
	public final Map<Long, Long> map;

	public ConversionMap(String name, Map<Long, Long> map) {
		this.name = name;
		this.map = map;
	}

	public String toString() {
		return String.format("Map %s: %s", name, map);

	}

}
