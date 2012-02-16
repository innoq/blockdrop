package de.innoq.blockdrop;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static de.innoq.blockdrop.model.Point.*;

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
		assertThat (Arrays.asList (result), contains(
				point (2,1,10),
				point (2,2,10),
				point (2,3,10),
				point (2,4,10)
				));
	}
	
	@Test
	public void testParseBlocks() {
		// When
		Point[] result = p.parseBlocks("[blocks [[2,2,0],[2,2,1],[3,2,1],[2,2,2]]]");
		
		// Then
		assertThat (Arrays.asList (result), contains(
				point (2,2,0),
				point (2,2,1),
				point (3,2,1),
				point (2,2,2)
				));		
		
		
	}
	
	@Test
	public void testParseEmtpyBlocks() {
		Point[] result = p.parseBlocks("[blocks []]]");
		// Then
		assertThat (result.length, is(0));
	}
}
