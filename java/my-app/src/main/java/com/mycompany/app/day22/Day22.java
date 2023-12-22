package com.mycompany.app.day22;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.mycompany.app.utils.InputReader;

public class Day22 {
	public static void main(String[] args) {
		new Day22().solveA();
	}

	void solveA() {
		List<Line> lines = readInput();
		int maxZ = 0;
		for (Line l : lines) {
			if (l.finish.z > maxZ) {
				maxZ = l.finish.z;
			}
			//System.out.println(l);
		}

		Queue<Line> queue = new PriorityQueue<>();
		queue.addAll(lines);

		Deque<Line> fallen = new LinkedList<>();

		while (queue.peek() != null) {
			Line l = queue.poll();
			int minZ = 1;
			for (Line fallenLine : fallen) {
				if (l.intersects(fallenLine)) {
					if (fallenLine.finish.z >= minZ) {
						minZ = fallenLine.finish.z+1;
					}
				} 
			}

			Coord newStart = new Coord(l.start.x, l.start.y, minZ);
			Coord newFinish = new Coord(l.finish.x, l.finish.y, minZ + l.zLength());
			Line fallenL = new Line(newStart, newFinish);

			for (Line fallenLine : fallen) {
				if (l.intersects(fallenLine)) {
					if (fallenLine.finish.z == (minZ - 1)) {
						fallenLine.supporting.add(fallenL);
						fallenL.supportedBy.add(fallenLine);
					}
				}
			}

			System.out.println(l + " falls to " + fallenL + " - " + fallenL.supportedBy);
			fallen.add(fallenL);
		}

		System.out.println("fallen");
		for (Line l : fallen) {
			System.out.println(l);
		}


		int count = 0;
		for (Line l : fallen) {
			boolean canDisintegrate = true;
			for (Line supportedLine : l.supporting) {
				if (supportedLine.supportedBy.size() == 1) {
					canDisintegrate = false;
				}
			}
			if (canDisintegrate) {
				count++;
			}
		}

		System.out.println(count);
		System.out.println(maxZ);
	}

	List<Line> readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day22/input.txt");
		List<Line> parsedLines = new ArrayList<>();
		for (String line : lines) {
			String[] l = line.split("~")[0].split(",");
			String[] r = line.split("~")[1].split(",");

			Coord start = new Coord(Integer.parseInt(l[0]), Integer.parseInt(l[1]), Integer.parseInt(l[2]));
			Coord finish = new Coord(Integer.parseInt(r[0]), Integer.parseInt(r[1]), Integer.parseInt(r[2]));

			parsedLines.add(new Line(start, finish));
		}

		return parsedLines;
	}
}
