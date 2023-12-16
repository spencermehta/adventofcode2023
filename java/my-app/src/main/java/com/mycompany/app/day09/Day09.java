package com.mycompany.app.day09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.mycompany.app.utils.InputReader;

public class Day09 {
	public static void main(String[] args) {
		new Day09().solveB();
	}

	public void solveA() {
		List<List<Long>> sequences = readInput();
		System.out.println(sequences);

		long sum = 0;

		for (List<Long> currSeq : sequences) {
			List<List<Long>> seqs = getSequenceDescent(currSeq);
			for (List<Long> seq : seqs) {
				System.out.println(seq);
			}

			sum += ascendSequence(seqs);
		}

		System.out.println(sum);
	}

	public void solveB() {
		List<List<Long>> sequences = readInput();
		System.out.println(sequences);

		long sum = 0;

		for (List<Long> currSeq : sequences) {
			List<List<Long>> seqs = getSequenceDescent(currSeq);
			for (List<Long> seq : seqs) {
				System.out.println(seq);
			}

			sum += ascendSequenceB(seqs);
		}

		System.out.println(sum);
	}

	public long ascendSequenceB(List<List<Long>> sequences) {
		long prev = 0;
		for (int i = sequences.size() - 1; i >= 0; i--) {
			List<Long> seq = sequences.get(i);
			Collections.reverse(seq);
			// System.out.println("looking at seq " + seq);
			// System.out.println(String.format("Previous value is %s. Last elem is %s", prev, seq.get(seq.size()-1)));
			long next = seq.get(seq.size()-1) - prev;
			//System.out.println("adding " + next);
			seq.add(next);
			prev = next;
		}

		List<Long> firstSeq = sequences.get(0);

		return firstSeq.get(firstSeq.size()-1);
	}


	public long ascendSequence(List<List<Long>> sequences) {
		long prev = 0;
		for (int i = sequences.size() - 1; i >= 0; i--) {
			List<Long> seq = sequences.get(i);
			// System.out.println("looking at seq " + seq);
			// System.out.println(String.format("Previous value is %s. Last elem is %s", prev, seq.get(seq.size()-1)));
			long next = seq.get(seq.size()-1) + prev;
			//System.out.println("adding " + next);
			seq.add(next);
			prev = next;
		}

		List<Long> firstSeq = sequences.get(0);

		return firstSeq.get(firstSeq.size()-1);
	}

	public List<List<Long>> getSequenceDescent(List<Long> sequence) {
		List<List<Long>> sequences = new ArrayList<>();
		sequences.add(sequence);

		if (allZero(sequence)) {
			
		}

		List<Long> currentSequence = sequence;

		while(!allZero(currentSequence)) {
			System.out.println("Curre seq not 0 "+ currentSequence);
			List<Long> nextSequence = new ArrayList<>();

			for (int i = 0; i < currentSequence.size()-1; i += 1) {
				long curr = currentSequence.get(i);
				long next = currentSequence.get(i+1);
				long diff = next - curr;

				nextSequence.add(diff);
			} 

			sequences.add(nextSequence);
			currentSequence = nextSequence;
		}

		return sequences;
	}

	public boolean allZero(List<Long> sequence) {
		for (long num : sequence) {
			if (num != 0) {
				return false;
			}
		}
		return true;
	}

	public List<List<Long>> readInput() {
		List<String> lines = new InputReader().readInput("/home/spencer/projects/hobby/aoc/2023/input/day09/part1.txt");
		List<List<Long>> sequences = new ArrayList<>();

		for (String line : lines) {
			List<String> strings = Arrays.asList(line.split("\\s+"));
			List<Long> longs = new ArrayList<>();
			for (String string : strings) {
				longs.add(Long.parseLong(string));
			}
			sequences.add(longs);
		}

		return sequences;
	}
}
