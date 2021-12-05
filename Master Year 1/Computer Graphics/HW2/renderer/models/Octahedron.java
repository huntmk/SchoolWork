/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a regular octahedron
   with its center at the origin, having side length
   {@code  sqrt(2) = 1.4142},with its center plane given
   by the four vertices {@code  (±1, 0, ±1)}. and with
   the top and bottom vertices being {@code  (0, ±1, 0)}.
<p>
   See <a href="https://en.wikipedia.org/wiki/Octahedron" target="_top">
                https://en.wikipedia.org/wiki/Octahedron</a>

   @see Tetrahedron
   @see Cube
   @see Icosahedron
   @see Dodecahedron
*/
public class Octahedron extends Model
{
   /**
      Create a regular octahedron with its center at the
      origin, having side length {@code  sqrt(2) = 1.4142},
      with its center plane given by the four vertices
      {@code  (±1, 0, ±1)}. and with the top and bottom
      vertices being {@code  (0, ±1, 0)}.
   */
   public Octahedron()
   {
      super("Octahedron");

      // Create the octahedron's geometry.
      // It has 6 vertices and 12 edges.
      addVertex(new Vertex( 1,  0,  0), // 4 vertices around the center plane
                new Vertex( 0,  0, -1),
                new Vertex(-1,  0,  0),
                new Vertex( 0,  0,  1),
                new Vertex( 0,  1,  0), // vertex at the top
                new Vertex( 0, -1,  0)); // vertex at the bottom
/*
      // These vertices create an Octahedron with side length 1.
      final double sqrt3 = Math.sqrt(3.0);
      final double sqrt2 = Math.sqrt(2.0);
      addVertex(new Vertex( 0.5, 0,  0.5), // 4 vertices around the center plane
                new Vertex(-0.5, 0,  0.5),
                new Vertex(-0.5, 0, -0.5),
                new Vertex( 0.5, 0, -0.5),
                new Vertex( 0,  1/sqrt2, 0), // vertex at the top
                new Vertex( 0, -1/sqrt2, 0)); // vertex at the bottom
*/
      // Create 12 line segments.
      // four line segments around the center plane
      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 0));
      // edges going to the top vertex
      addLineSegment(new LineSegment(0, 4),
                     new LineSegment(1, 4),
                     new LineSegment(2, 4),
                     new LineSegment(3, 4));
      // edges going to the bottom vertex
      addLineSegment(new LineSegment(0, 5),
                     new LineSegment(1, 5),
                     new LineSegment(2, 5),
                     new LineSegment(3, 5));
   }
}//Octahedron
