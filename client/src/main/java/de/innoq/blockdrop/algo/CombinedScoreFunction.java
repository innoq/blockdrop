package de.innoq.blockdrop.algo;

public class CombinedScoreFunction implements ScoreFunction {

	ScoreFunction height = new HeightScoreFunction();
	ScoreFunction hole = new HolesScoreFunction();

	@Override
	public int score(byte[][][] board) {
		return height.score(board) * 2 + hole.score(board);
	}
}
