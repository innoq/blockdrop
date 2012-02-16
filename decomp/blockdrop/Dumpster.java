package progevent.blockdrop;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static progevent.blockdrop.Block.FREE;
import static progevent.blockdrop.Block.get;
import static progevent.blockdrop.Block.getRandom;

public class Dumpster {
	char cells[][];
	int  nRows;
	int  nCols; 

	int rowsCompleted;
	int blocksDropped;
	
	public Dumpster(int nRows, int nCols) {
		this.nRows=nRows;
		this.nCols=nCols;
		cells=new char[nRows][];
		for(int r=0; r<nRows; ++r) {
			cells[r]=new char[nCols];
			free(cells[r]);
		}
	}
	
	public void reset() {
		for(char[] row  : cells) { free(row); }
		rowsCompleted=0;
		blocksDropped=0;
			
	}
	
	static void free(char[] row) {
		Arrays.fill(row, FREE);
	}
	
	static boolean freeQ(char[] row) {
		for(char c: row) { 
			if(c!=FREE) return false;
		}
		return true;			
	}

	static boolean completeQ(char[] row) {
		for(char c: row) { 
			if(c==FREE) return false;
		}
		return true;			
	}

	public boolean dropBlock(char[][] blck, int colIdx) {
		Block.print(blck, System.out);
		System.out.println(colIdx+"  +++++++++++++++++++++");

		if(colIdx<0)
			return false; // Über linken Rand
		int[] blockBottom=bottomProfile(blck);	
		int[] wellTop=topProfile(cells);
		if(colIdx+blockBottom.length>nCols)
			return false; // Über rechten Rand
		int rowOffset=Integer.MIN_VALUE;
		for(int c=0; c<blockBottom.length; ++c) {
			rowOffset=max(wellTop[colIdx+c]-blockBottom[c], rowOffset);
		}
		rowOffset+=1;
		if(rowOffset+blck.length>nRows) 
			return false; // Über oberen Rand
		insertBlock(blck, colIdx, rowOffset);
		++blocksDropped;
		removeCompleted(rowOffset, max(rowOffset+blck.length, rowMax(wellTop)));
		print(System.out);
		return true;
	}
	
	void insertBlock(char[][] blck, int colIdx, int rowOffset) {
		for(int r=0; r<blck.length; ++r) {
			char[] row=blck[r];
			for(int c=0; c<row.length; ++c) {
				if(blck[r][c]!=FREE) {
					cells[rowOffset+r][colIdx+c]=blck[r][c];
				}
			}
		}		
	}
	
	void swapRows(int a, int b) {
		char[] aux=cells[a];
		cells[a]=cells[b];
		cells[b]=aux;
	}
	void removeCompleted(int mnRow, int mxRow) {
		for(int r=mnRow; r<mxRow; ++r) {
			while(completeQ(cells[r])) {
				free(cells[r]);
				++rowsCompleted;
				int rr=r;
				while(rr<mxRow && rr+1<nRows && !freeQ(cells[rr+1])) {
					swapRows(rr, rr+1);
					++rr;
				}
			}
		}
	}
	
	static int[] bottomProfile(char[][] data) {
		int maxLen=Integer.MIN_VALUE;
		for(char row[] : data) {
			maxLen=max(maxLen, row.length);
		}
		int[] profile=new int[maxLen];
		Arrays.fill(profile, data.length);
		for(int r=0; r<data.length; ++r) {
			char[] row=data[r];
			for(int c=0; c<row.length; ++c) {
				if(row[c]!=FREE) { 
					profile[c]=min(profile[c], r);
				}
			}
		}
		return profile;		
	}
	
	static int rowMax(int[] row) { 
		int mx=Integer.MIN_VALUE;
		for(int v : row) { if(v>mx) mx=v; }
		return mx;
			
	}
	static int[] topProfile(char[][] data) {
		int maxLen=Integer.MIN_VALUE;
		for(char[] row : data) {
			maxLen=max(maxLen, row.length);
		}
		int[] profile=new int[maxLen];
		Arrays.fill(profile, -1);
		for(int r=0; r<data.length; ++r) {
			char[] row=data[r];
 			for(int c=0; c<row.length; ++c) {
				if(row[c]!=FREE) { 
					profile[c]=max(profile[c], r);
				}
			}
		}
		return profile;		
	}


	
	char[] lines;
	private char[] bottomLine() {
		if(lines==null) {
			lines=new char[nCols+2];
			lines[0]=lines[nCols+1]=' ';
			for(int i=1; i<nCols+1; ++i) {
				lines[i]=(char)((int)('0')+(i-1)%10);
			}
			
		}
		return lines;
	}
	
	public void print(PrintStream os) {
		char[] line=new char[nCols+2];
		Arrays.fill(line, '-');
		Block.print(cells, os);
		os.println(line);	
		os.println(bottomLine());
	}

	
	public void print(PrintWriter pw) {
		char[] line=new char[nCols+2];
		Arrays.fill(line, '-');
		Block.print(cells, pw);
		pw.println(line);	
		pw.println(bottomLine());
	}

	
	public static void main(String[] args) {
		Dumpster w0=new Dumpster(2, 14);
		boolean ok=w0.dropBlock(get(0, 0), 0);
		ok=w0.dropBlock(get(0, 0), 2);
		
		Dumpster w=new Dumpster(10, 16);
		java.util.Random rnd=new Random();
		
		while(w.dropBlock(getRandom(), rnd.nextInt(4))) {
		}
		w.dropBlock(get(0, 0), 0);
		w.dropBlock(get(0, 0), 2);
		w.dropBlock(get(1, 1), 4);
		w.dropBlock(get(1, 1), 5);
		w.dropBlock(get(3, 0), 0);
		w.dropBlock(get(1, 1), 3);
		w.dropBlock(get(0, 0), 1);
		w.dropBlock(get(1, 0), 0);
		w.dropBlock(get(1, 1), 5);
		
		Dumpster test=new Dumpster(15, 10);
		test.dropBlock(
				Block.makeBlock
				(" y        ",
			     " y        ",
                 "ryy    o  ",
                 "rryy yyoo ",
                 "royy yyoob",
                 "ooyy yyoob"), 0);
		
		test.dropBlock(get(1,1), 4);

	}

}



