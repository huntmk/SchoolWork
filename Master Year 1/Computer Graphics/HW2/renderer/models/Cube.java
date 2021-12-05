/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a cube with its center
   at the origin, having edge length 2, and with its
   vertices at {@code (±1, ±1, ±1)}.
<p>
   Here is a picture showing how the cube's eight
   vertices are labeled.
<pre>{@code
                  v4=(-1,1,-1)
                  +---------------------+ v5=(1,1,-1)
                 /|                    /|
                / |                   / |
               /  |                  /  |
              /   |                 /   |
             /    |                /    |
         v7 +---------------------+ v6  |
            |     |               |     |
            |     |               |     |
            |     | v0=(-1,-1,-1) |     |
            |     +---------------|-----+ v1=(1,-1,-1)
            |    /                |    /
            |   /                 |   /
            |  /                  |  /
            | /                   | /
            |/                    |/
            +---------------------+
            v3=(-1,-1,1)          v2=(1,-1,1)
}</pre>
   See <a href="https://en.wikipedia.org/wiki/Cube" target="_top">
                https://en.wikipedia.org/wiki/Cube</a>

   @see Tetrahedron
   @see Octahedron
   @see Icosahedron
   @see Dodecahedron
*/
public class Cube extends Model
{
   /**
      Create a cube with its center at the origin, having edge
      length 2, and with its vertices at {@code (±1, ±1, ±1)}.
   */
   public Cube( )
   {
      super("Cube");

      // Create 8 vertices.
      addVertex(new Vertex(-1, -1, -1), // 4 vertices around the bottom face
                new Vertex( 1, -1, -1),
                new Vertex( 1, -1,  1),
                new Vertex(-1, -1,  1),
                new Vertex(-1,  1, -1), // 4 vertices around the top face
                new Vertex( 1,  1, -1),
                new Vertex( 1,  1,  1),
                new Vertex(-1,  1,  1));

      // Create 12 line segments.
      addLineSegment(new LineSegment(0, 1),  // bottom face
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 0),
                     new LineSegment(4, 5),  // top face
                     new LineSegment(5, 6),
                     new LineSegment(6, 7),
                     new LineSegment(7, 4),
                     new LineSegment(0, 4),  // back face
                     new LineSegment(1, 5),
                     new LineSegment(2, 6),  // front face
                     new LineSegment(3, 7));
   }
}//Cube
