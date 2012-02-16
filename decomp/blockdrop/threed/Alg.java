/*    */ package progevent.blockdrop.threed;
/*    */ 
/*    */ public class Alg
/*    */ {
/*    */   public static final int[][] unit;
/*    */   public static final int[][][] rotx;
/*    */   public static final int[][][] roty;
/*    */   public static final int[][][] rotz;
/*    */ 
/*    */   static
/*    */   {
/* 52 */     unit = new int[][] { { 1 }, 
/* 53 */       { 0, 1 }, 
/* 54 */       { 0, 0, 1 } };
/* 55 */     rotx = new int[][][] { unit, 
/* 56 */       { { 1 }, 
/* 57 */       { 0, 0, -1 }, 
/* 58 */       { 0, 1 } }, 
/* 59 */       { { 1 }, 
/* 60 */       { 0, -1 }, 
/* 61 */       { 0, 0, -1 } }, 
/* 62 */       { { 1 }, 
/* 63 */       { 0, 0, 1 }, 
/* 64 */       { 0, -1 } } };
/* 65 */     roty = new int[][][] { unit, 
/* 66 */       { { 0, 0, 1 }, 
/* 67 */       { 0, 1 }, 
/* 68 */       { -1 } }, 
/* 69 */       { { -1 }, 
/* 70 */       { 0, 1 }, 
/* 71 */       { 0, 0, -1 } }, 
/* 72 */       { { 0, 0, -1 }, 
/* 73 */       { 0, 1 }, 
/* 74 */       { 1 } } };
/* 75 */     rotz = new int[][][] { unit, 
/* 76 */       { { 0, -1 }, 
/* 77 */       { 1 }, 
/* 78 */       { 0, 0, 1 } }, 
/* 79 */       { { -1 }, 
/* 80 */       { 0, -1 }, 
/* 81 */       { 0, 0, 1 } }, 
/* 82 */       { { 0, 1 }, 
/* 83 */       { -1 }, 
/* 84 */       { 0, 0, 1 } } };
/*    */   }
/*    */ 
/*    */   public static Block3D.Pt add(Block3D.Pt a, Block3D.Pt b)
/*    */   {
/*  8 */     return new Block3D.Pt(a.x + b.x, a.y + b.y, a.z + b.z); } 
/*  9 */   public static Block3D.Pt sub(Block3D.Pt a, Block3D.Pt b) { return new Block3D.Pt(a.x - b.x, a.y - b.y, a.z - b.z); }
/*    */ 
/*    */   public static Block3D.Pt mul(int[][] m, Block3D.Pt p) {
/* 12 */     return new Block3D.Pt(m[0][0] * p.x + m[0][1] * p.y + m[0][2] * p.z, 
/* 13 */       m[1][0] * p.x + m[1][1] * p.y + m[1][2] * p.z, 
/* 14 */       m[2][0] * p.x + m[2][1] * p.y + m[2][2] * p.z);
/*    */   }
/*    */ 
/*    */   public static Block3D.Pt rot(int[][] m, Block3D.Pt p, Block3D.Pt p0)
/*    */   {
/* 27 */     return add(mul(m, sub(p, p0)), p0);
/*    */   }
/*    */ 
/*    */   public static int[][] mul(int[][] a, int[][] b) {
/* 31 */     assert (a[0].length == b.length);
/* 32 */     int rows = a.length;
/* 33 */     int cols = b[0].length;
/* 34 */     int N = b.length;
/* 35 */     int[][] res = new int[rows][cols];
/* 36 */     for (int r = 0; r < rows; r++) {
/* 37 */       for (int c = 0; c < cols; c++) {
/* 38 */         for (int i = 0; i < N; i++) {
/* 39 */           res[r][c] += a[r][i] * b[i][c];
/*    */         }
/*    */       }
/*    */     }
/* 43 */     return res;
/*    */   }
/*    */ }

/* Location:           /Users/martinei/blockdrop/
 * Qualified Name:     progevent.blockdrop.threed.Alg
 * JD-Core Version:    0.6.0
 */