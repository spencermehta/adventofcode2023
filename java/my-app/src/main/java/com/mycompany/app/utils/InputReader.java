package com.mycompany.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {
	public InputReader() {}


	public List<String> readInput(String path) {
		try {
			Scanner s = new Scanner(new File(path));
			List<String> list = new ArrayList<>();
			while (s.hasNext()) {
				String line = s.nextLine();
				list.add(line);
			}
			return list;
		} catch (FileNotFoundException e) {
			throw new RuntimeException();
		}
	}

	public List<List<String>> readInputBreakAtBlankLines(String path) {
		List<String> lines = readInput(path);
		List<List<String>> groupedLines = new ArrayList<>();

		List<String> currentGroup = new ArrayList<>();
		for (String line : lines) {
			if (line.length() == 0) {
				groupedLines.add(currentGroup);
				currentGroup = new ArrayList<>();
			} else {
				currentGroup.add(line);
			}
		}
		groupedLines.add(currentGroup);
		currentGroup = new ArrayList<>();
		return groupedLines;

	}

}
