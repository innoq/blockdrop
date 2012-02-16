/*     */ package progevent.blockdrop.threed;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class Block3D
/*     */ {
/*     */   public Pt mn;
/*     */   public Pt mx;
/*     */   public Pt[] data;
/*     */ 
/*     */   public static Pt pt(int x, int y, int z)
/*     */   {
/*  80 */     return new Pt(x, y, z); } 
/*  81 */   public static Pt pt(int x, int y) { return new Pt(x, y, 0);
/*     */   }
/*     */ 
/*     */   public Block3D(int n)
/*     */   {
/*  88 */     this.data = new Pt[n];
/*     */   }
/*     */   public Block3D(Pt[] pts) {
/*  91 */     this.data = pts;
/*     */   }
/*     */ 
/*     */   public Pt minCoords()
/*     */   {
/*  96 */     int mx = 2147483647;
/*  97 */     int my = 2147483647;
/*  98 */     int mz = 2147483647;
/*  99 */     for (Pt p : this.data) {
/* 100 */       if (p.x < mx) {
/* 101 */         mx = p.x;
/*     */       }
/* 103 */       if (p.y < my) {
/* 104 */         my = p.y;
/*     */       }
/* 106 */       if (p.z < mz) {
/* 107 */         mz = p.z;
/*     */       }
/*     */     }
/* 110 */     return pt(mx, my, mz);
/*     */   }
/*     */ 
/*     */   public Pt maxCoords() {
/* 114 */     int mx = -2147483648;
/* 115 */     int my = -2147483648;
/* 116 */     int mz = -2147483648;
/* 117 */     for (Pt p : this.data) {
/* 118 */       if (p.x > mx) {
/* 119 */         mx = p.x;
/*     */       }
/* 121 */       if (p.y > my) {
/* 122 */         my = p.y;
/*     */       }
/* 124 */       if (p.z > mz) {
/* 125 */         mz = p.z;
/*     */       }
/*     */     }
/* 128 */     return pt(mx, my, mz);
/*     */   }
/*     */ 
/*     */   public Pt rotationZero() {
/* 132 */     return minCoords();
/*     */   }
/*     */ 
/*     */   public static Block3D blk(Pt[] pts) {
/* 136 */     return new Block3D(pts);
/*     */   }
/*     */ 
/*     */   public Block3D move(int dx, int dy, int dz) {
/* 140 */     return move(pt(dx, dy, dz));
/*     */   }
/*     */   public Block3D move(Pt d) {
/* 143 */     Block3D res = new Block3D(this.data.length);
/* 144 */     for (int i = 0; i < this.data.length; i++) {
/* 145 */       res.data[i] = Alg.add(this.data[i], d);
/*     */     }
/* 147 */     return res;
/*     */   }
/*     */ 
/*     */   protected Block3D rotate(int[][] m, Pt p0) {
/* 151 */     Block3D res = new Block3D(this.data.length);
/* 152 */     for (int i = 0; i < this.data.length; i++) {
/* 153 */       res.data[i] = Alg.rot(m, this.data[i], p0);
/*     */     }
/* 155 */     return res;
/*     */   }
/*     */ 
/*     */   public Block3D rotateX() {
/* 159 */     return rotate(Alg.rotx[1], rotationZero());
/*     */   }
/*     */ 
/*     */   public Block3D rotateY() {
/* 163 */     return rotate(Alg.roty[1], rotationZero());
/*     */   }
/*     */ 
/*     */   public Block3D rotateZ() {
/* 167 */     return rotate(Alg.rotz[1], rotationZero());
/*     */   }
/*     */ 
/*     */   public Block3D nrotateX() {
/* 171 */     return rotate(Alg.rotx[3], rotationZero());
/*     */   }
/*     */ 
/*     */   public Block3D nrotateY() {
/* 175 */     return rotate(Alg.roty[3], rotationZero());
/*     */   }
/*     */ 
/*     */   public Block3D nrotateZ() {
/* 179 */     return rotate(Alg.rotz[3], rotationZero());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 186 */     StringBuilder sb = new StringBuilder();
/* 187 */     boolean written = false;
/* 188 */     sb.append("[");
/* 189 */     for (int i = 0; i < this.data.length; i++) {
/* 190 */       if (written)
/* 191 */         sb.append(", ");
/*     */       else {
/* 193 */         written = true;
/*     */       }
/* 195 */       sb.append(this.data[i].toString());
/*     */     }
/* 197 */     sb.append("]");
/* 198 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static class Pt
/*     */     implements Comparable<Pt>
/*     */   {
/*     */     public final int x;
/*     */     public final int y;
/*     */     public final int z;
/*     */ 
/*     */     public Pt(int x, int y, int z)
/*     */     {
/*  10 */       this.x = x; this.y = y; this.z = z;
/*     */     }
/*     */ 
/*     */     public Pt move(int dx, int dy, int dz)
/*     */     {
/*  16 */       return new Pt(this.x + dx, this.y + dy, this.z + dz);
/*     */     }
/*     */ 
/*     */     public boolean equals(Object rhs)
/*     */     {
/*  21 */       if ((rhs != null) && (rhs.getClass() == Pt.class)) {
/*  22 */         Pt p = (Pt)rhs;
/*  23 */         return (p.x == this.x) && (p.y == this.y) && (p.z == this.z);
/*     */       }
/*  25 */       return false;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/*  31 */       return this.x + this.y + this.z;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/*  36 */       return "[" + this.x + ", " + this.y + ", " + this.z + "]";
/*     */     }
/*     */ 
/*     */     public int compareTo(Pt arg0)
/*     */     {
/*  41 */       int d = this.x - arg0.x;
/*  42 */       if (d == 0) {
/*  43 */         d = this.y - arg0.y;
/*  44 */         if (d == 0) {
/*  45 */           d = this.z - arg0.z;
/*     */         }
/*     */       }
/*  48 */       return d;
/*     */     }
/*     */ 
/*     */     public static class XYZOrder implements Comparator<Block3D.Pt>
/*     */     {
/*     */       public int compare(Block3D.Pt arg0, Block3D.Pt arg1) {
/*  54 */         int d = arg0.z - arg1.z;
/*  55 */         if (d == 0) {
/*  56 */           d = arg0.y - arg1.y;
/*  57 */           if (d == 0) {
/*  58 */             d = arg0.x - arg1.x;
/*     */           }
/*     */         }
/*  61 */         return d;
/*     */       }
/*     */     }
/*     */ 
/*     */     public static class ZYXOrder implements Comparator<Block3D.Pt> {
/*     */       public int compare(Block3D.Pt arg0, Block3D.Pt arg1) {
/*  67 */         int d = arg0.x - arg1.x;
/*  68 */         if (d == 0) {
/*  69 */           d = arg0.y - arg1.y;
/*  70 */           if (d == 0) {
/*  71 */             d = arg0.z - arg1.z;
/*     */           }
/*     */         }
/*  74 */         return d;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/martinei/blockdrop/
 * Qualified Name:     progevent.blockdrop.threed.Block3D
 * JD-Core Version:    0.6.0
 */