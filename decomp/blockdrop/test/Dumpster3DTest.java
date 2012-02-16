/*    */ package progevent.blockdrop.test;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import junit.framework.TestCase;
/*    */ import org.junit.Test;
/*    */ import progevent.blockdrop.threed.Block3D;
/*    */ import progevent.blockdrop.threed.Block3D.Pt;
/*    */ import progevent.blockdrop.threed.Dumpster3D;
/*    */ 
/*    */ public class Dumpster3DTest extends TestCase
/*    */ {
/*    */   @Test
/*    */   public void testRandomBlock()
/*    */   {
/* 15 */     Dumpster3D d = new Dumpster3D(5, 5, 10);
/* 16 */     Block3D b = d.randomCube();
/* 17 */     Block3D.Pt mn = b.rotationZero();
/* 18 */     System.out.println(b.toString());
/* 19 */     assertEquals(10, mn.z);
/*    */   }
/* 23 */   @Test
/*    */   public void testDrop() { Dumpster3D d = new Dumpster3D(5, 5, 10);
/*    */     do {
/* 25 */       d.next = d.initialPosition(Dumpster3D.BLOCKS[4]);
/* 26 */       while (d.down());
/* 29 */       if (d.next.rotationZero().z < 10)
/* 30 */         d.set(d.next);
/*    */     }
/* 32 */     while (d.next.rotationZero().z < 10); }
/*    */ 
/*    */   @Test
/*    */   public void testRemoveFilledLayer() {
/* 37 */     Dumpster3D d = new Dumpster3D(5, 5, 4);
/* 38 */     d.setLayer(0, 1);
/* 39 */     d.setLayer(3, 1);
/* 40 */     d.set(0, 0, 1, 1);
/* 41 */     d.set(2, 2, 1, 1);
/* 42 */     System.out.println(d.toString_());
/* 43 */     d.removeFilledLayers(0);
/* 44 */     System.out.println(d.toString_());
/* 45 */     assertTrue(d.get(0, 0, 0) == 1);
/* 46 */     assertTrue(d.get(2, 2, 0) == 1);
/*    */   }
/*    */ }

/* Location:           /Users/martinei/blockdrop/
 * Qualified Name:     progevent.blockdrop.test.Dumpster3DTest
 * JD-Core Version:    0.6.0
 */