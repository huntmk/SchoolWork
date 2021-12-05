/*

*/

package renderer.scene;

/**
   A {@code Vector} object holds four doubles, which makes it a vector
   in 4-dimensional space.
<p>
   In computer graphics, we use 4-dimensional homogeneous coordinates
   to represent vectors (and points) in 3-dimensional space.
<p>
   Unlike a homogeneous {@link Vertex}, a homogeneous Vector usually
   has its fourth coordinate set to 0.
*/
public class Vector
{
   public double x, y, z, w; // a vector in homogenous coordinates

   /**
      Construct a default {@code Vector}.
   */
   public Vector()
   {
      set(0.0, 0.0, 0.0, 0.0);
   }


   /**
      Construct a new {@code Vector} using the given x, y, and z coordinates.

      @param x  x-coordinate of the new {@code Vector}
      @param y  y-coordinate of the new {@code Vector}
      @param z  z-coordinate of the new {@code Vector}
   */
   public Vector(final double x, final double y, final double z)
   {
      set(x, y, z, 0.0);
   }


   /**
      Construct a new {@code Vector} using the given x, y, z, and w coordinates.
      <p>
      This constructor is used to create the column vectors in a 4-by-4
      homogeneous {@link Matrix}.

      @param x  x-coordinate of the new {@code Vector}
      @param y  y-coordinate of the new {@code Vector}
      @param z  z-coordinate of the new {@code Vector}
      @param w  w-coordinate of the new {@code Vector}
   */
   public Vector(final double x, final double y, final double z, final double w)
   {
      set(x, y, z, w);
   }


   /**
      Construct a new {@code Vector} from a {@link Vertex}.

      @param v  {@link Vertex} object to convert into a {@code Vector}
   */
   public Vector(final Vertex v)
   {
      set(v.x, v.y, v.z, v.w);
   }


   /**
      Construct a new {@code Vector} that is a copy of another {@code Vector}.

      @param v  {@code Vector} to make a copy of
   */
   public Vector(final Vector v) // a "copy constructor"
   {
      set(v.x, v.y, v.z, v.w);
   }


   /**
      Copy the coordinates of {@code Vector} {@code v} to
      this {@code Vector}.

      @param v  {@code Vector} whose coordinates are copied to this {@code Vector}
   */
   public void set(final Vector v)
   {
      set(v.x, v.y, v.z, v.w);
   }


   /**
      Set the {@code x}, {@code y}, and {@code z} coordinates
      of this {@code Vector}.

      @param x  new x-coordinate for this {@code Vector}
      @param y  new y-coordinate for this {@code Vector}
      @param z  new z-coordinate for this {@code Vector}
   */
   public void set(final double x, final double y, final double z)
   {
      set(x, y, z, 0.0);
   }


   /**
      Set the homogeneous coordinates of this {@code Vector}.

      @param x  new x-coordinate for this {@code Vector}
      @param y  new y-coordinate for this {@code Vector}
      @param z  new z-coordinate for this {@code Vector}
      @param w  new w-coordinate for this {@code Vector}
   */
   public void set(final double x, final double y, final double z, final double w)
   {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }


   /**
      A scalar times a {@code Vector} returns a (new) {@code Vector}.

      @param s  number to multiply this {@code Vector} by
      @return a new {@code Vector} object that is the scalar times this {@code Vector}
   */
   public Vector times(final double s)
   {
      return new Vector(s*x, s*y, s*z, s*w);
   }


   /**
      A scalar times a {@code Vector} mutates this {@code Vector}.

      @param s  number to multiply this {@code Vector} by
      @return a reference to this {@code Vector} object (for method chaining)
   */
   public Vector timesEquals(final double s)
   {
      this.x = s * x;
      this.y = s * y;
      this.z = s * z;
      this.w = s * w;
      return this; // for method chaining
   }


   /**
      A {@code Vector} plus a {@code Vector} returns a (new) {@code Vector}.

      @param v  {@code Vector} object to add to this {@code Vector}
      @return a new {@code Vector} object that is the sum of this {@code Vector} and {@code v}
   */
   public Vector plus(final Vector v)
   {
      return new Vector( x+(v.x), y+(v.y), z+(v.z), w+(v.w) );
   }


   /**
      A {@code Vector} minus a {@code Vector} returns a (new) {@code Vector}.

      @param v  {@code Vector} object to subtract from this {@code Vector}
      @return a new {@code Vector} object that is this {@code Vector} minus {@code v}
   */
   public Vector minus(final Vector v)
   {
      return new Vector( x-(v.x), y-(v.y), z-(v.z), w-(v.w) );
   }


   /**
      The cross-product of two {@code Vector}s returns a (new) {@code Vector}.

      @param v  {@code Vector} object to multiply with this {@code Vector}
      @return a new {@code Vector} object that is the cross-product of this {@code Vector} and {@code v}
   */
   public Vector crossProduct(final Vector v)
   {
      return new Vector((y*v.z)-(z*v.y), (z*v.x)-(x*v.z), (x*v.y)-(y*v.x));
   }


   /**
      The dot-product of two {@code Vector}s returns a scalar.

      @param v  {@code Vector} object to multiply with this {@code Vector}
      @return a double that is the dot-product of this {@code Vector} and {@code v}
   */
   public double dotProduct(final Vector v)
   {
      return x*v.x + y*v.y + z*v.z;
   }


   /**
      Return the normalized version of this {@code Vector}.
      <p>
      That is, return the {@code Vector} with length 1 that
      points in the same direction as this {@code Vector}.

      @return a new {@code Vector} that has length one and has the same direction as this {@code Vector}
   */
   public Vector normalize()
   {
      final double norm = Math.sqrt( x*x + y*y + z*z );
      return new Vector(x/norm, y/norm, z/norm);
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Vector} object
   */
   @Override
   public String toString()
   {
      final int precision = 5;  // the default precision for the format string
      return toString(precision);
   }


   /**
      For debugging.
      <p>
      Allow the precision of the formatted output to be specified.

      @param precision  precision value for the format string
      @return {@link String} representation of this {@code Vector} object
   */
   public String toString(final int precision)
   {
      final int iWidth = 3; // default width of integer part of the format string
      return toString(precision, iWidth);
   }


   /**
      For debugging.
      <p>
      Allow the precision and width of the formatted output to be specified.
      By width, we mean the width of the integer part of each number.

      @param precision  precision value for the format string
      @param iWidth     width of the integer part of the format string
      @return {@link String} representation of this {@code Vector} object
   */
   public String toString(final int precision, final int iWidth)
   {
      // Here is one way to get programmable precision and width.
      final int p = precision;      // the precision for the following format string
      final int t = p + iWidth + 2; // the width for the following format string
      final String format = "[x,y,z,w] = [% "+t+"."+p+"f  % "+t+"."+p+"f  % "+t+"."+p+"f  % "+t+"."+p+"f]\n";
      return String.format(format, x, y, z, w);
   }
}
