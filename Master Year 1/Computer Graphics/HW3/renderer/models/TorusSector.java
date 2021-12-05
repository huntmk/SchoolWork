/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a partial torus.
<p>
   See <a href="https://en.wikipedia.org/wiki/Torus" target="_top">
                https://en.wikipedia.org/wiki/Torus</a>
<p>
   This partial torus is the surface of revolution generated by revolving
   a sector of the circle in the xy-plane with radius {@code r2} and center
   {@code (r1,0,0)} part way around the y-axis. We are assuming that
   {@code r1 > r2}.
<p>
   The whole torus is the surface of revolution generated by revolving
   the whole circle in the xy-plane with radius {@code r2} and center
   {@code (r1,0,0)} all the way around the y-axis.
<p>
   Here are parametric equations for the circle in the xy-plane with
   radius {@code r2} and center {@code (r1,0,0)} and parameterized
   starting from the top, with parameter {@code 0 <= phi <= 2*PI}.
   <pre>{@code
      x(phi) = r1 + r2 * sin(phi)
      y(phi) =      r2 * cos(phi)
      z(phi) = 0
   }</pre>
   Here is the 3D rotation matrix that rotates around the y-axis
   by {@code theta} radians with {@code 0 <= theta <= 2*PI}.
   <pre>{@code
      [ cos(theta)   0   sin(theta)]
      [     0        1       0     ]
      [-sin(theta)   0   cos(theta)]
   }</pre>
   If we multiply the rotation matrix with the circle parameterization,
   we get a parameterization of the torus.
   <pre>{@code
      [ cos(theta)   0   sin(theta)]   [r1 + r2 * sin(phi)]
      [     0        1       0     ] * [     r2 * cos(phi)]
      [-sin(theta)   0   cos(theta)]   [        0         ]

      = ( r1*cos(theta) + r2*cos(theta)*sin(phi).
          r2*cos(phi),
         -r1*sin(theta) - r2*sin(theta)*sin(phi) )

      = ( (r1 + r2*sin(phi)) * cos(theta),
                r2*cos(phi),
         -(r1 + r2*sin(phi)) * sin(theta) )
   }</pre>
   See
     <a href="https://en.wikipedia.org/wiki/Torus#Geometry" target="_top">
              https://en.wikipedia.org/wiki/Torus#Geometry</a>

   @see Torus
*/
public class TorusSector extends Model
{
   /**
      Create a partial torus with half the circle of revolution with radius 3/4
      and a cross section that is half the circle of longitude with radius 1/4.
   */
   public TorusSector( )
   {
      this(0.75, 0.25, Math.PI/2, 3*Math.PI/2, Math.PI, 2*Math.PI, 6, 8);
   }


   /**
      Create a partial torus with a partial circle of revolution
      with radius {@code r1} and a cross section circle (circle of
      longitude) with radius {@code r2}.
   <p>
      If {@code theta1 > 0} or {@code theta2 < 2*PI}, then the (partial)
      circle of revolution is the circular sector from angle {@code theta1}
      to angle {@code theta2}. In other words, the (partial) circles of
      latitude in the model extend from angle {@code theta1} to angle
      {@code theta2}.
   <p>
      The last two parameters determine the number of circles of longitude
      and the number of (partial) circles of latitude in the model.
   <p>
      If there are {@code k} circles of longitude, then each (partial)
      circle of latitude will have {@code k-1} line segments.
      If there are {@code n} (partial) circles of latitude, then each
      circle of longitude will have {@code n} line segments.
   <p>
      There must be at least four circles of longitude and at least
      three circles of latitude.

      @param r1      radius of the circle of revolution
      @param r2      radius of the cross section circle (circle of longitude)
      @param theta1  beginning longitude angle for the circle of revolution
      @param theta2  ending longitude angle for the circle of revolution
      @param n       number of circles of latitude
      @param k       number of circles of longitude
   */
   public TorusSector(final double r1, final double r2,
                      final double theta1, final double theta2,
                      final int n, final int k)
   {
      this(r1, r2, theta1, theta2, 0, 2*Math.PI, n+1, k);
   }


   /**
      Create a partial torus with a partial circle of revolution with
      radius {@code r1} and a partial cross section circle with radius
      {@code r2}.
   <p>
      If {@code phi1 > 0} or {@code phi2 < 2*PI}, then the (partial) cross
      section circle is the circular sector from angle {@code phi1} to angle
      {@code phi2}. In other words, the (partial) circles of logitude in the
      model extend from angle {@code phi1} to angle {@code phi2}.
   <p>
      If {@code theta1 > 0} or {@code theta2 < 2*PI}, then the (partial) circle
      of revolution is the circular sector from angle {@code theta1} to angle
      {@code theta2}. In other words, the (partial) circles of latitude in
      the model extend from angle {@code theta1} to angle {@code theta2}.
   <p>
      The last two parameters determine the number of (partial) circles of
      longitude and the number of (partial) circles of latitude in the model.
   <p>
      If there are {@code k} circles of longitude, then each (partial)
      circle of latitude will have {@code k-1} line segments.
      If there are {@code n} (partial) circles of latitude, then each
      circle of longitude will have {@code n-1} line segments.
   <p>
      There must be at least four circles of longitude and at least
      four circles of latitude.

      @param r1      radius of the circle of revolution
      @param r2      radius of the cross section circle (circle of longitude)
      @param theta1  beginning longitude angle for the circle of revolution
      @param theta2  ending longitude angle for the circle of revolution
      @param phi1    beginning latitude angle for the cross section circle
      @param phi2    ending latitude angle for the cross section circle
      @param n       number of circles of latitude
      @param k       number of circles of longitude
   */
   public TorusSector(final double r1,     final double r2,
                      final double theta1, final double theta2,
                      final double phi1,   final double phi2,
                      int n, int k)
   {
      super("Torus Sector");

      if (n < 4) n = 4;
      if (k < 4) k = 4;

      // Create the torus's geometry.

      final double deltaPhi = (phi2 - phi1) / (n - 1);
      final double deltaTheta = (theta2 - theta1) / (k - 1);

      // An array of vertices to be used to create line segments.
      final Vertex[][] v = new Vertex[n][k];

      // Create all the vertices.
      for (int j = 0; j < k; ++j) // choose a rotation around the y-axis
      {
         final double c1 = Math.cos(theta1 + j * deltaTheta);
         final double s1 = Math.sin(theta1 + j * deltaTheta);
         for (int i = 0; i < n; ++i)  // go around a cross section circle
         {
            final double c2 = Math.cos(phi1 + i * deltaPhi);
            final double s2 = Math.sin(phi1 + i * deltaPhi);
            v[i][j] = new Vertex( (r1 + r2*s2) * c1,
                                        r2*c2,
                                 -(r1 + r2*s2) * s1 );
         }
      }

      // Add all of the vertices to this model.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k; ++j)
         {
            addVertex( v[i][j] );
         }
      }

      // Create the vertical (partial) cross-section circles.
      for (int j = 0; j < k; ++j) // choose a rotation around the y-axis
      {
         for (int i = 0; i < n - 1; ++i) // go around a cross section circle
         {  //                                 v[i][j]      v[i+1][j]
            addLineSegment(new LineSegment( (i * k) + j, ((i+1) * k) + j ));
         }
      }

      // Create all the horizontal (partial) circles around the torus.
      for (int i = 0; i < n; ++i) //choose a rotation around the cross section
      {
         for (int j = 0; j < k - 1; ++j) // go around a horizontal circle
         {  //                                v[i][j]       v[i][j+1]
            addLineSegment(new LineSegment( (i * k) + j, (i * k) + (j+1) ));
         }
      }
   }
}//TorusSector
