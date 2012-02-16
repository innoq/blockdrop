package de.innoq.blockdrop.model;


/**
 * A single Point in 3d Space. See also "Block3d.Pt" in Server
 */
public class Point {
   
     public final int x;
     public final int y;
     public final int z;
 
     public Point(int x, int y, int z)
     {
       this.x = x; this.y = y; this.z = z;
     }
 
     public Point move(int dx, int dy, int dz)
     {
       return new Point(this.x + dx, this.y + dy, this.z + dz);
     }
 
     public boolean equals(Object rhs)
     {
       if ((rhs != null) && (rhs.getClass() == Point.class)) {
         Point p = (Point)rhs;
         return (p.x == this.x) && (p.y == this.y) && (p.z == this.z);
       }
       return false;
     }
 
     public int hashCode()
     {
       return this.x + this.y + this.z;
     }
 
     public String toString()
     {
       return "[" + this.x + ", " + this.y + ", " + this.z + "]";
     }
 
     public int compareTo(Point arg0)
     {
       int d = this.x - arg0.x;
       if (d == 0) {
         d = this.y - arg0.y;
         if (d == 0) {
           d = this.z - arg0.z;
         }
       }
       return d;
     }
     
 	
}
