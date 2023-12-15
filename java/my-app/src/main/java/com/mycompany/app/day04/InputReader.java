package com.mycompany.app.day04;

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
}
