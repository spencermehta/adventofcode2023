package com.mycompany.app.day22;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.mycompany.app.utils.InputReader;

public class Day22 {
	public static void main(String[] args) {
		new Day22().solveB();
	}

	void solveB() {
		List<InitLine> lines = readInput();

		Queue<InitLine> queue = new PriorityQueue<>();
		queue.addAll(lines);

		Deque<Line> fallen = new LinkedList<>();

		while (queue.peek() != null) {
			InitLine l = queue.poll();
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


		int sum = 0;
		for (Line l : fallen) {
			int wouldFall = chainReaction(l, new HashSet<Line>());
			sum += wouldFall - 1;
		}

		System.out.println("wouldFall " + sum);
	}

	int chainReaction(Line startLine, Set<Line> fallen) {
		Queue<Line> queue = new LinkedList<>();
		fallen.add(startLine);
		queue.add(startLine);

		while (queue.peek() != null) {
			Line line = queue.poll();
			Set<Line> wouldFall = causesToFall(line, fallen);
			queue.addAll(wouldFall);
			fallen.addAll(wouldFall);
		}

		System.out.println("with start line " + startLine + " - " + fallen);
		return fallen.size();
	}

	Set<Line> causesToFall(Line line, Set<Line> fallen) {
		Set<Line> wouldFall = new HashSet<>();
		for (Line supportedLine : line.supporting) {
			int otherSupport = 0;
			for (Line support : supportedLine.supportedBy) {
				if (!fallen.contains(support)) {
					otherSupport++;
				}
			}
			if (otherSupport == 0) {
				wouldFall.add(supportedLine);
			}
		}
		return wouldFall;
	}

	List<InitLine> readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day22/input.txt");
		List<InitLine> parsedLines = new ArrayList<>();
		for (String line : lines) {
			String[] l = line.split("~")[0].split(",");
			String[] r = line.split("~")[1].split(",");

			Coord start = new Coord(Integer.parseInt(l[0]), Integer.parseInt(l[1]), Integer.parseInt(l[2]));
			Coord finish = new Coord(Integer.parseInt(r[0]), Integer.parseInt(r[1]), Integer.parseInt(r[2]));

			parsedLines.add(new InitLine(start, finish));
		}

		return parsedLines;
	}
}
