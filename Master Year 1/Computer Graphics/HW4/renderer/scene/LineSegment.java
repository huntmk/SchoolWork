/*

*/

package renderer.scene;

/**
   A {@code LineSegment} object has four integers that
   represent the endpoints of the line segment and the
   color at each endpoint. Two of the integers are indices
   into the {@link Vertex} list of a {@link Model} object
   and the other two integers are indices into the {@link Color}
   list of that {@link Model} object.
*/
public class LineSegment
{
   public int[] vIndex = new int[2]; // indices for this line segment's vertices
   public int[] cIndex = new int[2]; // indices for this line segment's colors

   /**
      Construct a {@code LineSegment} object using two integer indices.
      Use the given indices for both the {@link Vertex} and the
      {@link Color} lists.
      <p>
      NOTE: This method does not put any {@link Vertex} or {@link Color}
      objects into this {@code LineSegment}'s {@link Model}. This method
      assumes that the given indices are valid (or will be valid by the
      time this {@code LineSegment} gets rendered).

      @param i0  index of 1st endpoint {@link Vertex} of the new {@code LineSegment}
      @param i1  index of 2nd endpoint {@link Vertex} of the new {@code LineSegment}
   */
   public LineSegment(final int i0, final int i1)
   {
      this(i0, i1, i0, i1);
   }


   /**
      Construct a {@code LineSegment} object using two integer indices
      for the vertices and one integer index for the colors.
      <p>
      NOTE: This method does not put any {@link Vertex} or {@link Color}
      objects into this {@code LineSegment}'s {@link Model}. This method
      assumes that the given indices are valid (or will be valid by the
      time this {@code LineSegment} gets rendered).

      @param i0  index of 1st endpoint {@link Vertex} of the new {@code LineSegment}
      @param i1  index of 2nd endpoint {@link Vertex} of the new {@code LineSegment}
      @param c   index of the {@link Color} of the new {@code LineSegment}
   */
   public LineSegment(final int i0, final int i1, final int c)
   {
      this(i0, i1, c, c);
   }


   /**
      Construct a {@code LineSegment} object using two integer indices
      for the vertices and two integer indices for the colors.
      <p>
      NOTE: This method does not put any {@link Vertex} or {@link Color}
      objects into this {@code LineSegment}'s {@link Model}. This method
      assumes that the given indices are valid (or will be valid by the
      time this {@code LineSegment} gets rendered).

      @param i0  index of 1st endpoint {@link Vertex} of the new {@code LineSegment}
      @param i1  index of 2nd endpoint {@link Vertex} of the new {@code LineSegment}
      @param c0  index of 1st endpoint {@link Color} of the new {@code LineSegment}
      @param c1  index of 2nd endpoint {@link Color} of the new {@code LineSegment}
   */
   public LineSegment(final int i0, final int i1, final int c0, final int c1)
   {
      vIndex[0] = i0;
      vIndex[1] = i1;
      cIndex[0] = c0;
      cIndex[1] = c1;
   }


   /**
      Construct a {@code LineSegment} object with the same two vertex indices
      and the same two color indices from the given {@code LineSegment} object.

      @param ls  {@code LineSegment} to make a copy of
   */
   public LineSegment(final LineSegment ls) // a "copy constructor"
   {
      this(ls.vIndex[0], ls.vIndex[1], ls.cIndex[0], ls.cIndex[1]);
   }


   /**
      Set this {@code LineSegment}'s two integer indices for its colors.
      <p>
      NOTE: This method does not put any {@link Color} objects into this
      {@code LineSegment}'s {@link Model}. This method assumes that the
      given {@link Color} indices are valid (or will be valid by the time
      this {@code LineSegment} gets rendered).

      @param c0  index of 1st endpoint {@link Color} for this {@code LineSegment}
      @param c1  index of 2nd endpoint {@link Color} for this {@code LineSegment}
   */
   public void setColors(final int c0, final int c1)
   {
      cIndex[0] = c0;
      cIndex[1] = c1;
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code LineSegment} object
   */
   @Override
   public String toString()
   {
      return "Line Segment: ([" + vIndex[0] + ", " + vIndex[1] + "], [" + cIndex[0] + ", " + cIndex[1] + "])\n";
   }
}
