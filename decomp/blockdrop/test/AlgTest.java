/*     */ package progevent.blockdrop.test;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import progevent.blockdrop.Varia;
/*     */ import progevent.blockdrop.threed.Alg;
/*     */ import progevent.blockdrop.threed.Block3D;
/*     */ import progevent.blockdrop.threed.Block3D.Pt;
/*     */ import progevent.blockdrop.threed.Block3D.Pt.XYZOrder;
/*     */ import progevent.blockdrop.threed.Block3D.Pt.ZYXOrder;
/*     */ 
/*     */ public class AlgTest
/*     */ {
/*     */   public static void check(boolean expr, String msg)
/*     */   {
/*  18 */     if (!expr)
/*  19 */       System.out.println("Failed test: " + msg);
/*     */   }
/*     */ 
/*     */   static Block3D.Pt print(Block3D.Pt p)
/*     */   {
/*  25 */     return p;
/*     */   }
/*     */ 
/*     */   public static Block3D.Pt[] pts(Block3D.Pt[] ps) {
/*  29 */     return ps;
/*     */   }
/*     */ 
/*     */   public static boolean equal(int[][] a, int[][] b) {
/*  33 */     if (a.length != b.length) {
/*  34 */       return false;
/*     */     }
/*  36 */     for (int i = 0; i < a.length; i++) {
/*  37 */       if (a[i].length != b[i].length) {
/*  38 */         return false;
/*     */       }
/*  40 */       for (int j = 0; j < a[i].length; j++) {
/*  41 */         if (a[i][j] != b[i][j]) {
/*  42 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  50 */     Block3D.Pt zero = Block3D.pt(0, 0, 0);
/*  51 */     Block3D.Pt px = Block3D.pt(1, 0, 0);
/*  52 */     Block3D.Pt py = Block3D.pt(0, 1, 0);
/*  53 */     Block3D.Pt pz = Block3D.pt(0, 0, 1);
/*  54 */     Block3D.Pt npx = Block3D.pt(-1, 0, 0);
/*  55 */     Block3D.Pt npy = Block3D.pt(0, -1, 0);
/*  56 */     Block3D.Pt npz = Block3D.pt(0, 0, -1);
/*     */ 
/*  59 */     check(Alg.rot(Alg.rotx[1], px, zero).equals(px), "RotateXx");
/*  60 */     check(Alg.rot(Alg.rotx[1], py, zero).equals(pz), "RotateXy");
/*  61 */     check(Alg.rot(Alg.rotx[1], pz, zero).equals(npy), "RotateXz");
/*     */ 
/*  63 */     check(Alg.rot(Alg.roty[1], px, zero).equals(npz), "RotateYx");
/*  64 */     check(Alg.rot(Alg.roty[1], py, zero).equals(py), "RotateYy");
/*  65 */     check(Alg.rot(Alg.roty[1], pz, zero).equals(px), "RotateYz");
/*     */ 
/*  67 */     check(Alg.rot(Alg.rotz[1], px, zero).equals(py), "RotateZx");
/*  68 */     check(Alg.rot(Alg.rotz[1], py, zero).equals(npx), "RotateZy");
/*  69 */     check(Alg.rot(Alg.rotz[1], pz, zero).equals(pz), "RotateZz");
/*     */ 
/*  72 */     Block3D.Pt off = Block3D.pt(3, 1, -2);
/*  73 */     check(Alg.add(off, off).equals(Block3D.pt(6, 2, -4)), "Sum");
/*  74 */     check(print(Alg.rot(Alg.rotx[1], Alg.add(px, off), off)).equals(print(Alg.add(px, off))), "RotateOffXx");
/*  75 */     check(print(Alg.rot(Alg.rotx[1], Alg.add(py, off), off)).equals(print(Alg.add(pz, off))), "RotateOffXy");
/*  76 */     check(print(Alg.rot(Alg.rotx[1], Alg.add(pz, off), off)).equals(print(Alg.add(npy, off))), "RotateOffXz");
/*     */ 
/*  78 */     check(print(Alg.rot(Alg.roty[1], Alg.add(px, off), off)).equals(print(Alg.add(npz, off))), "RotateOffYx");
/*  79 */     check(print(Alg.rot(Alg.roty[1], Alg.add(py, off), off)).equals(print(Alg.add(py, off))), "RotateOffYy");
/*  80 */     check(print(Alg.rot(Alg.roty[1], Alg.add(pz, off), off)).equals(print(Alg.add(px, off))), "RotateOffYz");
/*     */ 
/*  82 */     check(print(Alg.rot(Alg.rotz[1], Alg.add(px, off), off)).equals(print(Alg.add(py, off))), "RotateOffZx");
/*  83 */     check(print(Alg.rot(Alg.rotz[1], Alg.add(py, off), off)).equals(print(Alg.add(npx, off))), "RotateOffZy");
/*  84 */     check(print(Alg.rot(Alg.rotz[1], Alg.add(pz, off), off)).equals(print(Alg.add(pz, off))), "RotateOffZz");
/*     */ 
/*  88 */     Block3D.Pt[] sorted = pts(new Block3D.Pt[] { Block3D.pt(0, 0, 0), Block3D.pt(0, 1, 0), Block3D.pt(1, 0, 0), Block3D.pt(1, 1, 0), 
/*  88 */       Block3D.pt(0, 0, 1), Block3D.pt(0, 1, 1), Block3D.pt(1, 0, 1), Block3D.pt(1, 1, 1) });
/*     */ 
/*  91 */     Block3D.Pt[] shuffeled = pts(new Block3D.Pt[] { Block3D.pt(0, 0, 1), Block3D.pt(1, 1, 1), Block3D.pt(0, 1, 1), Block3D.pt(1, 0, 1), 
/*  91 */       Block3D.pt(0, 0, 0), Block3D.pt(1, 1, 0), Block3D.pt(0, 1, 0), Block3D.pt(1, 0, 0) });
/*     */ 
/*  93 */     Arrays.sort(shuffeled, new Block3D.Pt.XYZOrder());
/*  94 */     for (int i = 0; i < sorted.length; i++) {
/*  95 */       System.out.println(i + ":" + shuffeled[i]);
/*     */     }
/*  97 */     Arrays.sort(shuffeled, new Block3D.Pt.ZYXOrder());
/*  98 */     for (int i = 0; i < sorted.length; i++) {
/*  99 */       System.out.println(i + ":" + shuffeled[i]);
/*     */     }
/*     */ 
/* 102 */     System.out.println(Varia.toString(Alg.rotz[1]));
/* 103 */     int[][] rotz2 = Alg.mul(Alg.rotz[1], Alg.rotz[1]);
/* 104 */     System.out.println(Varia.toString(rotz2));
/*     */ 
/* 109 */     check(equal(Alg.mul(Alg.rotx[1], Alg.rotx[1]), Alg.rotx[2]), "x112");
/* 110 */     check(equal(Alg.mul(Alg.rotx[1], Alg.rotx[2]), Alg.rotx[3]), "x123");
/* 111 */     check(equal(Alg.mul(Alg.rotx[2], Alg.rotx[1]), Alg.rotx[3]), "x213");
/* 112 */     check(equal(Alg.mul(Alg.rotx[1], Alg.rotx[3]), Alg.rotx[0]), "x130");
/* 113 */     check(equal(Alg.mul(Alg.rotx[3], Alg.rotx[1]), Alg.rotx[0]), "x310");
/*     */ 
/* 115 */     check(equal(Alg.mul(Alg.roty[1], Alg.roty[1]), Alg.roty[2]), "y112");
/* 116 */     check(equal(Alg.mul(Alg.roty[1], Alg.roty[2]), Alg.roty[3]), "y123");
/* 117 */     check(equal(Alg.mul(Alg.roty[2], Alg.roty[1]), Alg.roty[3]), "y213");
/* 118 */     check(equal(Alg.mul(Alg.roty[1], Alg.roty[3]), Alg.roty[0]), "y130");
/* 119 */     check(equal(Alg.mul(Alg.roty[3], Alg.roty[1]), Alg.roty[0]), "y310");
/*     */ 
/* 121 */     check(equal(Alg.mul(Alg.rotz[1], Alg.rotz[1]), Alg.rotz[2]), "z112");
/* 122 */     check(equal(Alg.mul(Alg.rotz[1], Alg.rotz[2]), Alg.rotz[3]), "z123");
/* 123 */     check(equal(Alg.mul(Alg.rotz[2], Alg.rotz[1]), Alg.rotz[3]), "z213");
/* 124 */     check(equal(Alg.mul(Alg.rotz[1], Alg.rotz[3]), Alg.rotz[0]), "z130");
/* 125 */     check(equal(Alg.mul(Alg.rotz[3], Alg.rotz[1]), Alg.rotz[0]), "z310");
/*     */   }
/*     */ }

/* Location:           /Users/martinei/blockdrop/
 * Qualified Name:     progevent.blockdrop.test.AlgTest
 * JD-Core Version:    0.6.0
 */