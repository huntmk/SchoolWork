/*

*/

package renderer.scene;

/**
   A {@code Matrix} object has four {@link Vector} objects.
<p>
   The four {@link Vector} objects represent the four column vectors
   of the 4-by-4 matrix (as in a Linear Algebra course).
<p>
   In computer graphics, the points and vectors of 3-dimensional space
   are represented using 4-dimensional homogeneous coordinates.
   So each transformation of 3-dimensional space is represented by
   a 4-by-4 (homogeneous) matrix.
<p>
   A 4-by-4 matrix represents a transformation of 3-dimensional space.
   The most common transformations are translation, rotation, and
   scaling. A 4-by-4 matrix can also represent a projection transformation.
*/
public class Matrix
{
   private Vector v1, v2, v3, v4; // these are column vectors


   /**
      Construct an empty {@code Matrix}.
      <p>
      Notice that this is a private constructor. Other
      objects should use the static facory methods to
      create new {@code Matrix} objects.
   */
   private Matrix()
   {
      this.v1 = null;
      this.v2 = null;
      this.v3 = null;
      this.v4 = null;
   }


   /**
      A "copy constructor". This constructor should make a deep copy
      of the given {@code Matrix} object's four column {@link Vector}s.

      @param matrix  {@code Matrix} to make a copy of
   */
   public Matrix(final Matrix matrix) // a "copy constructor"
   {
      final Matrix m = Matrix.build(new Vector(matrix.v1),
                                    new Vector(matrix.v2),
                                    new Vector(matrix.v3),
                                    new Vector(matrix.v4));
      this.v1 = m.v1;
      this.v2 = m.v2;
      this.v3 = m.v3;
      this.v4 = m.v4;
   }


   /**
      This is a static facory method.
      <p>
      Construct an arbitrary 4-by-4 {@code Matrix}
      with the given column {@link Vector}s.

      @param v1  1st column {@link Vector} for the new {@code Matrix}
      @param v2  2nd column {@link Vector} for the new {@code Matrix}
      @param v3  3rd column {@link Vector} for the new {@code Matrix}
      @param v4  4th column {@link Vector} for the new {@code Matrix}
      @return a new {@code Matrix} object
   */
   public static Matrix build(final Vector v1, final Vector v2, final Vector v3, final Vector v4)
   {
      final Matrix m = new Matrix();
      m.v1 = v1;  // Notice that we are not making
      m.v2 = v2;  // copies of the column vectors,
      m.v3 = v3;  // We are just making references
      m.v4 = v4;  // to them.
      return m;
   }


   /**
      This is a static facory method.
      <p>
      Construct an identity {@code Matrix}.

      @return a new {@code Matrix} object containing an identity {@code Matrix}
   */
   public static Matrix identity()
   {
      return scale(1.0, 1.0, 1.0);
   }


   /**
      This is a static facory method.
      <p>
      Construct a translation {@code Matrix} that translates by the
      given amounts in the {@code x}, {@code y}, and {@code z} directions..

      @param x  translation factor for the x-direction
      @param y  translation factor for the y-direction
      @param z  translation factor for the z-direction
      @return a new {@code Matrix} object containing a translation {@code Matrix}
   */
   public static Matrix translate(final double x, final double y, final double z)
   {
      return build( new Vector(1.0, 0.0, 0.0, 0.0),
                    new Vector(0.0, 1.0, 0.0, 0.0),
                    new Vector(0.0, 0.0, 1.0, 0.0),
                    new Vector(  x,   y,   z, 1.0) );
   }


   /**
      This is a static facory method.
      <p>
      Construct a diagonal {@code Matrix} with the given number
      on the diagonal.
      <p>
      This is also a uniform scaling matrix.

      @param d  the diagonal value for the new {@code Matrix}
      @return a new {@code Matrix} object containing a scaling {@code Matrix}
   */
   public static Matrix scale(final double d)
   {
      return scale(d, d, d);
   }


   /**
      This is a static facory method.
      <p>
      Construct a (diagonal) {@code Matrix} that scales in
      the x, y, and z directions by the given factors.

      @param x  scale factor for the x-direction
      @param y  scale factor for the y-direction
      @param z  scale factor for the z-direction
      @return a new {@code Matrix} object containing a scaling {@code Matrix}
   */
   public static Matrix scale(final double x, final double y, final double z)
   {
      return build( new Vector(  x, 0.0, 0.0, 0.0),
                    new Vector(0.0,   y, 0.0, 0.0),
                    new Vector(0.0, 0.0,   z, 0.0),
                    new Vector(0.0, 0.0, 0.0, 1.0) );
   }


   /**
      This is a static facory method.
      <p>
      Construct a rotation {@code Matrix} that rotates around
      the x-axis by the angle {@code theta}.

      @param theta  angle (in degrees) to rotate by around the x-axis
      @return a new {@code Matrix} object containing a rotation {@code Matrix}
   */
   public static Matrix rotateX(final double theta)
   {
      return rotate(theta, 1,0,0);
   }


   /**
      This is a static facory method.
      <p>
      Construct a rotation {@code Matrix} that rotates around
      the y-axis by the angle {@code theta}.

      @param theta  angle (in degrees) to rotate by around the y-axis
      @return a new {@code Matrix} object containing a rotation {@code Matrix}
   */
   public static Matrix rotateY(final double theta)
   {
      return rotate(theta, 0,1,0);
   }


   /**
      This is a static facory method.
      <p>
      Construct a rotation {@code Matrix} that rotates around
      the z-axis by the angle {@code theta}.

      @param theta  angle (in degrees) to rotate by around the z-axis
      @return a new {@code Matrix} object containing a rotation {@code Matrix}
   */
   public static Matrix rotateZ(final double theta)
   {
      return rotate(theta, 0,0,1);
   }


   /**
      This is a static facory method.
      <p>
      Construct a rotation {@code Matrix} that rotates around
      the axis vector {@code (x,y,z)} by the angle {@code theta}.
      <p>
      See
      <a href="https://www.opengl.org/sdk/docs/man2/xhtml/glRotate.xml" target="_top">
               https://www.opengl.org/sdk/docs/man2/xhtml/glRotate.xml</a>

      @param theta  angle (in degrees) to rotate by around the axis vector
      @param x      x-component of the axis vector for the rotation
      @param y      y-component of the axis vector for the rotation
      @param z      z-component of the axis vector for the rotation
      @return a new {@code Matrix} object containing a rotation {@code Matrix}
   */
   public static Matrix rotate(final double theta, final double x, final double y, final double z)
   {
      final double norm = Math.sqrt(x*x + y*y + z*z);
      final double ux = x/norm;
      final double uy = y/norm;
      final double uz = z/norm;

      final double c = Math.cos( (Math.PI/180.0)*theta );
      final double s = Math.sin( (Math.PI/180.0)*theta );

      return build(
        new Vector(ux*ux*(1-c)+c,      uy*ux*(1-c)+(uz*s), uz*ux*(1-c)-(uy*s), 0.0),
        new Vector(ux*uy*(1-c)-(uz*s), uy*uy*(1-c)+c,      uz*uy*(1-c)+(ux*s), 0.0),
        new Vector(ux*uz*(1-c)+(uy*s), uy*uz*(1-c)-(ux*s), uz*uz*(1-c)+c,      0.0),
        new Vector(0.0,                0.0,                0.0,                1.0));
   }


   /**
      This {@code Matrix} times {@code Matrix} {@code m} returns a new {@code Matrix}.

      @param m  {@code Matrix} value to be multiplied on the right of this {@code Matrix}
      @return new {@code Matrix} object containing this {@code Matrix} times {@code Matrix} {@code m}
   */
   public Matrix times(final Matrix m)
   {
      return build( this.times(m.v1),   // 1st column vector of the result
                    this.times(m.v2),   // 2nd column vector of the result
                    this.times(m.v3),   // 3rd column vector of the result
                    this.times(m.v4) ); // 4th column vector of the result
   }


   /**
      Mutate this {@code Matrix} to contain the product of this {@code Matrix}
      with {@code Matrix} {@code m}.

      @param m  {@code Matrix} value to be multiplied on the right of this {@code Matrix}
      @return a reference to this {@code Matrix} object to facilitate chaining method calls
   */
   public Matrix mult(final Matrix m)
   {
      final Vector vec1 = this.times(m.v1);  // 1st column vector of the result
      final Vector vec2 = this.times(m.v2);  // 2nd column vector of the result
      final Vector vec3 = this.times(m.v3);  // 3rd column vector of the result
      final Vector vec4 = this.times(m.v4);  // 4th column vector of the result

      // Now mutate this Matrix object.
      this.v1 = vec1;
      this.v2 = vec2;
      this.v3 = vec3;
      this.v4 = vec4;
      return this;  // for method chaining
   }


   /**
      Mutate this {@code Matrix} to contain the product of {@code Matrix} {@code m}
      with this {@code Matrix}.

      @param m  {@code Matrix} value to be multiplied on the left of this {@code Matrix}
      @return a reference to this {@code Matrix} object to facilitate chaining method calls
   */
   public Matrix multLeft(final Matrix m)
   {
      final Vector vec1 = m.times(this.v1);  // 1st column vector of the result
      final Vector vec2 = m.times(this.v2);  // 2nd column vector of the result
      final Vector vec3 = m.times(this.v3);  // 3rd column vector of the result
      final Vector vec4 = m.times(this.v4);  // 4th column vector of the result

      // Now mutate this Matrix object.
      this.v1 = vec1;
      this.v2 = vec2;
      this.v3 = vec3;
      this.v4 = vec4;
      return this;  // for method chaining
   }


   /**
      A scalar times this {@code Matrix} returns a new {@code Matrix}.

      @param s  scalar value to multiply this {@code Matrix} by
      @return a new {@code Matrix} object containing the scalar s times this {@code Matrix}
   */
   public Matrix times(final double s)
   {
      return build(v1.times(s), v2.times(s), v3.times(s), v4.times(s));
   }


   /**
      A scalar times this {@code Matrix} mutates this {@code Matrix}.

      @param s  scalar value to multiply this {@code Matrix} by
      @return a reference to this {@code Matrix} object (for method chaining)
   */
   public Matrix timesEquals(final double s)
   {
      v1.timesEquals(s);
      v2.timesEquals(s);
      v3.timesEquals(s);
      v4.timesEquals(s);
      return this; // for method chaining
   }


   /**
      This {@code Matrix} times a {@link Vector} returns a new {@link Vector}.

      @param v  {@link Vector} to be multiplied by this {@code Matrix}
      @return new {@link Vector} object containing this {@code Matrix} times the {@link Vector} v
   */
   public Vector times(final Vector v)
   {
      return v1.times(v.x).plus(v2.times(v.y).plus(v3.times(v.z).plus(v4.times(v.w))));
   /*
      // Here is what this works out to be.
      final Vector v1x = this.v1.times(v.x);
      final Vector v2y = this.v2.times(v.y);
      final Vector v3z = this.v3.times(v.z);
      final Vector v4w = this.v4.times(v.w);
      final Vector sum1 = v1x.plus(v2y);
      final Vector sum2 = sum1.plus(v3z);
      final Vector sum3 = sum2.plus(v4w);
      return sum3;
   */
   /*
      // dot product of the first row of this matrix with the vector v
      double x = (v1.x * v.x) + (v2.x * v.y)  + (v3.x * v.z) + (v4.x * v.w);
      // dot product of the second row of this matrix with the vector v
      double y = (v1.y * v.x) + (v2.y * v.y)  + (v3.y * v.z) + (v4.y * v.w);
      // dot product of the third row of this matrix with the vector v
      double z = (v1.z * v.x) + (v2.z * v.y)  + (v3.z * v.z) + (v4.z * v.w);
      // dot product of the fourth row of this matrix with the vector v
      double w = (v1.w * v.x) + (v2.w * v.y)  + (v3.w * v.z) + (v4.w * v.w);
      return new Vector(x, y, z, w);
   */
   }


   /**
      This {@code Matrix} times a {@link Vertex} returns a new {@link Vertex}.

      @param v  {@link Vertex} to be multiplied by this {@code Matrix}
      @return new {@link Vertex} object containing this {@code Matrix} times the {@link Vertex} v
   */
   public Vertex times(final Vertex v)
   {
      final Vector vec = v1.times(v.x).plus(v2.times(v.y).plus(v3.times(v.z).plus(v4.times(v.w))));
      return new Vertex(vec.x, vec.y, vec.z, vec.w);
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Matrix} object
   */
   @Override
   public String toString()
   {
      String result = "";
      final int p = 5;      // the precision for the following format string
      final int w = p + 4;  // the width for the following format string
      final String format = "% "+w+"."+p+"f  % "+w+"."+p+"f  % "+w+"."+p+"f  % "+w+"."+p+"f";
      result += String.format("[[" + format + " ]\n",  v1.x, v2.x, v3.x, v4.x);
      result += String.format(" [" + format + " ]\n",  v1.y, v2.y, v3.y, v4.y);
      result += String.format(" [" + format + " ]\n",  v1.z, v2.z, v3.z, v4.z);
      result += String.format(" [" + format + " ]]\n", v1.w, v2.w, v3.w, v4.w);
    //result += String.format("[[% .5f  % .5f  % .5f  % .5f ]\n",  v1.x, v2.x, v3.x, v4.x);
    //result += String.format(" [% .5f  % .5f  % .5f  % .5f ]\n",  v1.y, v2.y, v3.y, v4.y);
    //result += String.format(" [% .5f  % .5f  % .5f  % .5f ]\n",  v1.z, v2.z, v3.z, v4.z);
    //result += String.format(" [% .5f  % .5f  % .5f  % .5f ]]\n", v1.w, v2.w, v3.w, v4.w);
      return result;
   }
}
