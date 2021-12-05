/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a positive x, y, and z axis in 3-dimensional space.
*/
public class Axes3D extends Model
{
   /**
      Create a positive x, y, and z axis
      with one unit length for each axis.
   */
   public Axes3D( )
   {
      this(1.0, 1.0, 1.0);
   }


   /**
      Create a positive x, y, and z axis
      with the given length for each axis.

      @param xMax  length of the x-axis
      @param yMax  length of the y-axis
      @param zMax  length of the z-axis
   */
   public Axes3D(final double xMax, final double yMax, final double zMax)
   {
      this(0.0, xMax, 0.0, yMax, 0.0, zMax);
   }


   /**
      Create an x, y, and z axis with the
      given endpoints for each axis.

      @param xMin  left endpoint of the x-axis
      @param xMax  right endpoint of the x-axis
      @param yMin  bottom endpoint of the y-axis
      @param yMax  top endpoint of the y-axis
      @param zMin  back endpoint of the z-axis
      @param zMax  front endpoint of the z-axis
   */
   public Axes3D(final double xMin, final double xMax,
                 final double yMin, final double yMax,
                 final double zMin, final double zMax)
   {
      super("Axes 3D");

      addVertex(new Vertex(xMin, 0,    0),
                new Vertex(xMax, 0,    0),
                new Vertex( 0,  yMin,  0),
                new Vertex( 0,  yMax,  0),
                new Vertex( 0,   0,   zMin),
                new Vertex( 0,   0,   zMax));

      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(2, 3),
                     new LineSegment(4, 5));
   }
}//Axes3D
