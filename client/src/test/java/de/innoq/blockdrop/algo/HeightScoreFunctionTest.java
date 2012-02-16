package de.innoq.blockdrop.algo;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import de.innoq.blockdrop.model.Point;
import static de.innoq.blockdrop.model.Point.*;

public class HeightScoreFunctionTest {

	HeightScoreFunction scoreFunc = new HeightScoreFunction();

	@Test
	public void testHeight() {
		// Given
		Point[] board = new Point[] { point(0, 0, 3), point(0, 0, 2),
				point(4, 5, 1) };
		// When
		int score = scoreFunc.getHeight(board);

		// Then
		assertThat(score, equalTo(3));

	}

	@Test
	public void testHeightZero() {
		// Given
		Point[] board = new Point[] { point(0, 0, 0), point(3, 0, 0),
				point(4, 5, 0) };
		// When
		int score = scoreFunc.getHeight(board);

		// Then
		assertThat(score, equalTo(0));

	}
}
