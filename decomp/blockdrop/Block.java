package progevent.blockdrop;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Block {

	public static final char FREE=' ';
	
	int shape;
	int orientation;
	static Random rnd=new Random(System.nanoTime());

	
	private static int modulo(int a, int b) {
		int rem=a%b;
		return rem<0?b+rem:rem;
		
	}

	public Block(int blockno) {
		this.shape=blockno%NSHAPES;
		this.orientation=0;
	}
	public Block(int blockno, int orientation) {
		this.shape=blockno%NSHAPES;
		this.orientation=modulo(orientation, 4);
	}

	public static Block randomBlock() {
		return new Block(rnd.nextInt(NSHAPES), rnd.nextInt(4));
	}
	
	public static char[][] get(int blockno, int orientation) {
		return BLOCKS[blockno%NSHAPES][modulo(orientation, 4)];
	}
	
	public static char[][] getRandom() {
		return get(rnd.nextInt(NSHAPES), rnd.nextInt(4));
	}

	public Block rotate(int n) {
		orientation=modulo(orientation+n, 4);
		return this;
	}

	public Block rotateLeft() {
		return rotate(1);
	}
	public Block rotateRight() {
		return rotate(-1);
	}

	public char[][] data() {
		return BLOCKS[shape][orientation];
	}
	
	static char [][] makeBlock(String ... rows) {
		char[][] blk;
		int bwd=Integer.MIN_VALUE;
		for (String r : rows) {
			bwd=Math.max(bwd, r.length());
		}	
		int bht=rows.length;
		blk=new char[bht][];
		for (int r=0; r<bht; ++r) {
			String row=rows[r];
			blk[bht-r-1]=new char[bwd];
			Arrays.fill(blk[bht-r-1], FREE);
			for(int c=0; c<row.length(); ++c) {
				blk[bht-r-1][c]=row.charAt(c);
			}
		}
		return blk;
	}

	public String toString() {
		return toString(get(shape, orientation));
	}
	static String toString(char[][] data) {
		StringBuilder sb=new StringBuilder();
		int r=data.length;
		while(--r>=0) {
			sb.append('|').append(data[r]).append("| "+r+"\n");
		}
		return sb.toString();
	}
	
	static void print(char[][] data, PrintStream os) {
		int r=data.length;
		while(--r>=0) {
			os.print('|');
			os.print(data[r]);
			os.println("| "+r);
		}
	}

	static void print(char[][] data, PrintWriter pw) {
		int r=data.length;
		while(--r>=0) {
			pw.print('|');
			pw.print(data[r]);
			pw.println("| "+r);
		}
	}

// Blöcke, Orientierungen, matrix
	public static final char[][][][] BLOCKS=
		{/*0*/ 
		 {makeBlock("gg", 
		            "gg"),		
		  makeBlock("gg",	
		            "gg"),
		  makeBlock("gg",
		            "gg"),
		  makeBlock("gg",
		            "gg")}, 
		 {/*1*/
		  makeBlock("bbbb"),
		  makeBlock("b", "b", "b" ,"b"),
		  makeBlock("bbbb"),
		  makeBlock("b", "b", "b" ,"b")},
		 {/*2*/
		  makeBlock(" r ",
		            "rrr"),
		  makeBlock("r", "rr", "r"),
		  makeBlock("rrr", " r"),      
		  makeBlock(" r", "rr", " r")},
		 {/*3*/
		  makeBlock("yyy",
		            "y"),
		  makeBlock("yy",
		            " y",
		            " y"),
		  makeBlock("  y",
		            "yyy"),
		  makeBlock("y",
		            "y",
		            "yy")},
		 {/*4*/
		  makeBlock("yyy",
		            "  y"),
		  makeBlock(" y",
		            " y",
		            "yy"),
		  makeBlock("y",
		            "yyy"),
		  makeBlock("yy",
		            "y",
		            "y")},
		 {/*5*/
		  makeBlock(" oo",
		            "oo"),
		  makeBlock("o",
		            "oo",
		            " o"),
		  makeBlock(" oo",
		            "oo"),
		  makeBlock("o",
		            "oo",
		            " o")},
		 {/*6*/
		  makeBlock("oo",
		            " oo"),
		  makeBlock(" o",
		            "oo",
		            "o"),
		  makeBlock("oo",
		            " oo"),
		  makeBlock(" o",
		            "oo",
		            "o")}};


	public static final int NSHAPES=BLOCKS.length;
	
	
	public static void main(String[] args) {

		for(int i=-8; i<9; ++i) {
			System.out.println(i+" ------------ ");
			print(get(4, i), System.out);
		}
		
		
	}
}
