/*

*/

package renderer.scene;

/**
   A {@code LineSegment} object has two integers that
   represent the endpoints of the line segment. The
   integers are indices into the {@link Vertex}
   {@link java.util.List} of a {@link Model} object.
*/
public final class LineSegment
{
   public final int[] index = new int[2]; // indices for this line segment's vertices

   /**
      Construct a {@code LineSegment} object with two integer indices.
      <p>
      NOTE: This method does not put any {@link Vertex} objects into
      this {@code LineSegment}'s {@link Model}. This method assumes
      that the given {@link Vertex} indices are valid (or will be
      valid by the time this {@code LineSegment} gets rendered).

      @param i0  index of 1st endpoint {@link Vertex} of the new {@code LineSegment}
      @param i1  index of 2nd endpoint {@link Vertex} of the new {@code LineSegment}
   */
   public LineSegment(final int i0, final int i1)
   {
      index[0] = i0;
      index[1] = i1;
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code LineSegment} object
   */
   @Override
   public String toString()
   {
      return "Line Segment: (" + index[0] + ", " + index[1] + ")\n";
   }
}
