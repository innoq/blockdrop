package de.innoq.blockdrop;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import de.innoq.blockdrop.model.Point;

public class ParserTest {

	private Parser p;
	
	@Before
	public void setup() {
		p = new Parser();
	}
	
	@Test
	public void testParseNext() {
		// When
		Point[] result = p.parseNextBlock("[next [[2, 1, 10], [2, 2, 10], [2, 3, 10], [2, 4, 10]]]");
		
		// Then
		assertThat (Arrays.asList (result), hasItem (new Point (2,1,10)));
	}
}
