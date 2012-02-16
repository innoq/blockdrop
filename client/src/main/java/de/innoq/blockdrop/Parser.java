package de.innoq.blockdrop;

import de.innoq.blockdrop.model.Point;
// TODO Parser can be made more efficient
public class Parser {

	// Input String looks like: [next [[2, 1, 10], [2, 2, 10], [2, 3, 10], [2,
	// 4, 10]]]
	public Point[] parseNextBlock(String input) {
		return parseInternal(input,"[next ");
	}

	private Point[] parseInternal(String input, String name) {
		if (!input.startsWith(name+"[")) {
			System.out.println("Zu parsendes"+name+"war kein Next! : " + input);
			return new Point[0]; // Lieber ein leeres Array zurückgeben als ne
									// Exception geben.
		}

		input = input.substring(7, input.length() - 2).trim();

		if (input.equals("[]")) {
			return new Point[0];
		}
		
		String[] pointStrings = input.split("\\], *\\[");
		
		Point[] result = new Point[pointStrings.length];
		for (int i = 0; i < pointStrings.length; i++) {
			String current = pointStrings[i].trim();
	 	while (current.startsWith("[")) {
				current = current.substring(1);
			}
		while (current.endsWith("]")) {
				current = current.substring(0, current.length()-1);
			}
			
			
			result[i] = parsePoint(current);

		}
		return result;
	}

	private Point parsePoint(String pointString) {
		String[] numbers = pointString.split(",( )*");
		return Point.point(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));
	}

	public Point[] parseBlocks(String input) {
		return parseInternal(input,"[blocks ");
	}

}
