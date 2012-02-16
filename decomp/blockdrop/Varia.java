/*    */ package progevent.blockdrop;
/*    */ 
/*    */ public class Varia
/*    */ {
/*    */   public static int modulo(int a, int b)
/*    */   {
/*  7 */     int rem = a % b;
/*  8 */     return rem < 0 ? b + rem : rem;
/*    */   }
/*    */ 
/*    */   public static String toString(int[][] m) {
/* 12 */     StringBuilder sb = new StringBuilder();
/* 13 */     sb.append("[");
/* 14 */     boolean rwritten = false;
/* 15 */     for (int i = 0; i < m.length; i++) {
/* 16 */       if (rwritten)
/* 17 */         sb.append(",\n");
/*    */       else {
/* 19 */         rwritten = true;
/*    */       }
/* 21 */       boolean cwritten = false;
/* 22 */       if (i > 0)
/* 23 */         sb.append(" [");
/*    */       else {
/* 25 */         sb.append("[");
/*    */       }
/* 27 */       for (int j = 0; j < m[i].length; j++) {
/* 28 */         if (cwritten)
/* 29 */           sb.append(", ");
/*    */         else {
/* 31 */           cwritten = true;
/*    */         }
/* 33 */         sb.append(m[i][j]);
/*    */       }
/* 35 */       sb.append("]");
/*    */     }
/* 37 */     sb.append("]");
/* 38 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           /Users/martinei/blockdrop/
 * Qualified Name:     progevent.blockdrop.Varia
 * JD-Core Version:    0.6.0
 */