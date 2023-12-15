package com.mycompany.app.day05;

import java.util.Iterator;
import java.util.SortedSet;

public class ConversionMap {
	public final String name;
	public final SortedSet<Mapping> mappings;

	public ConversionMap(String name, SortedSet<Mapping> mappings) {
		this.name = name;
		this.mappings = mappings;
	}

	public long convert(long source) {
		Iterator<Mapping> iterator = mappings.iterator();

		while (iterator.hasNext()) {
			Mapping mapping = iterator.next();
			if (mapping.inRange(source)) {
				return mapping.convert(source);
			}
		}

		return source;

	}

	public String toString() {
		return String.format("%s %s", name, mappings);
	}


}
