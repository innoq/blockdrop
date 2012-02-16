/*    */ package progevent.blockdrop.threed;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Block3DDropper extends Thread
/*    */ {
/*    */   long t_snooze;
/*  6 */   long t_snooze_init = 500L;
/*  7 */   double alpha = Math.exp(Math.log(0.1D) / 200.0D);
/*  8 */   boolean blocked = false;
/*    */   Dumpster3DServer server;
/*    */ 
/*    */   public Block3DDropper(Dumpster3DServer server)
/*    */   {
/* 13 */     this.server = server;
/* 14 */     this.t_snooze = this.t_snooze_init;
/*    */   }
/*    */   public synchronized void block() {
/* 17 */     this.blocked = true;
/*    */   }
/*    */   public synchronized void unblock() {
/* 20 */     this.blocked = false;
/* 21 */     notify();
/*    */   }
/*    */ 
/*    */   public void snooze() {
/*    */     try {
/* 26 */       System.out.println("SLEEP " + this.t_snooze);
/* 27 */       Thread.sleep(this.t_snooze);
/*    */     } catch (InterruptedException e) {
/* 29 */       System.out.println("INTERUPTED");
/*    */     }
/* 31 */     System.out.println("AWAKE");
/* 32 */     this.t_snooze = ()(this.alpha * this.t_snooze);
/*    */   }
/*    */ 
/*    */   public void resetSnooze() {
/* 36 */     this.t_snooze = this.t_snooze_init;
/*    */   }
/*    */ 
/*    */   public void resetSnooze(long init)
/*    */   {
/* 41 */     this.t_snooze_init = init;
/* 42 */     this.t_snooze = this.t_snooze_init;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 47 */     while (!this.server.isTerminated()) {
/* 48 */       while (this.blocked) {
/* 49 */         synchronized (this) {
/*    */           try { wait();
/*    */           } catch (InterruptedException e) {
/* 52 */             System.out.println("INTERUPTED");
/* 53 */             return;
/*    */           }
/*    */         }
/*    */       }
/* 57 */       snooze();
/* 58 */       if (!this.blocked)
/* 59 */         this.server.down();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/martinei/blockdrop/
 * Qualified Name:     progevent.blockdrop.threed.Block3DDropper
 * JD-Core Version:    0.6.0
 */