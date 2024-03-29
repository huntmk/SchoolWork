/*

*/

package renderer.models;

/**
   Create a wireframe model of a right circular cylinder
   with its axis along the y-axis.
<p>
   See <a href="https://en.wikipedia.org/wiki/Cylinder" target="_top">
                https://en.wikipedia.org/wiki/Cylinder</a>
<p>
   This model can also be used to create right k-sided polygonal prisms.
<p>
   See <a href="https://en.wikipedia.org/wiki/Prism_(geometry)" target="_top">
                https://en.wikipedia.org/wiki/Prism_(geometry)</a>

   @see CylinderSector
*/
public class Cylinder extends CylinderSector
{
   /**
      Create a right circular cylinder with radius 1 and its
      axis along the y-axis from {@code y = -1} to {@code y = 1}.
   */
   public Cylinder( )
   {
      this(1, 1, 15, 16);
   }


   /**
      Create a right circular cylinder with radius {@code r} and
      its axis along the y-axis from {@code y = -h} to {@code y = h}.

      @param r  radius of the cylinder
      @param h  height of the cylinder (from -h to h along the y-axis)
   */
   public Cylinder(final double r, final double h)
   {
      this(r, h, 15, 16);
   }


   /**
      Create a right circular cylinder with radius {@code r} and
      its axis along the y-axis from {@code y = -h} to {@code y = h}.
   <p>
      The last two parameters determine the number of lines of longitude
      and the number of circles of latitude in the model.
   <p>
      If there are {@code n} circles of latitude in the model (including
      the top and bottom edges), then each line of longitude will have
      {@code n+1} line segments. If there are {@code k} lines of longitude,
      then each circle of latitude will have {@code k} line segments.
   <p>
      There must be at least three lines of longitude and at least
      two circles of latitude.
   <p>
      By setting {@code k} to be a small integer, this model can also be
      used to create k-sided polygonal prisms.

      @param r  radius of the cylinder
      @param h  height of the cylinder (from -h to h along the y-axis)
      @param n  number of circles of latitude around the cylinder
      @param k  number of lines of longitude
   */
   public Cylinder(final double r, final double h, final int n, final int k)
   {
      super(r, h, 0, 2*Math.PI, n, k);
      name = "Cylinder";
   }
}//Cylinder
