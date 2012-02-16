package de.innoq.blockdrop;

import de.innoq.blockdrop.model.Point;

public class Parser {

	// Input String looks like: [next [[2, 1, 10], [2, 2, 10], [2, 3, 10], [2,
	// 4, 10]]]
	public Point[] parseNextBlock(String input) {

		if (!input.startsWith("[next [")) {
			System.out.println("Zu parsendes Next war kein Next! : " + input);
			return new Point[0]; // Lieber ein leeres Array zurückgeben als ne
									// Exception geben.
		}

		input = input.substring(7, input.length() - 2);

		int pos = 0;

		String[] pointStrings = input.split("\\], \\[");
		Point[] result = new Point[pointStrings.length];
		for (int i = 0; i < pointStrings.length; i++) {
			String current = pointStrings[i];
			if (current.startsWith("[")) {
				current = current.substring(1);
			}
			if (current.endsWith("]")) {
				current = current.substring(0, current.length()-1);
			}
			result[i] = parsePoint(current);

		}
		return result;
	}

	private Point parsePoint(String pointString) {
		System.out.println("Parsing Point:" + pointString);
		String[] numbers = pointString.split(", ");
		return Point.point(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));
	}

}
