/*

*/

package renderer.scene;

import java.util.List;
import java.util.ArrayList;

/**
   A {@code Model} object represents a distinct geometric object in a
   {@link Scene}. A {@code Model} data structure is mainly a {@link List}
   of {@link Vertex} objects and another {@link List} of {@link LineSegment}
   objects.
<p>
   The {@link Vertex} objects represents points from the geometric object
   that we are modeling. In the real world, a geometric object has an infinite
   number of points. In 3D graphics, we "approximate" a geometric object by
   listing just enough points to adequately describe the object. For example,
   in the real world, a rectangle contains an infinite number of points, but
   it can be adequately modeled by just its four corner points. (Think about
   a circle. How many points does it take to adequately model a circle? Look
   at the {@link renderer.models.Circle} model.)
<p>
   Each {@link LineSegment} object contains two integers that are the indices
   of two {@link Vertex} objects from the {@code Model}'s vertex list. Each
   {@link Vertex} object contains the xyz-coordinates, in the camera coordinate
   system, for one of the line segment's two endpoints.
<p>
   We use the {@link renderer.scene.LineSegment} objects to "fill in" some of
   the space between the model's vertices. For example, while a rectangle can
   be approximated by its four corner points, those same four points could also
   represent just two parallel line segments. By using four line segments that
   connect around the four points, we get a good representation of a rectangle.
<p>
   If we modeled a circle using just points, we would probably need to draw
   hundreds of points. But if we connect every two adjacent points with a
   short line segment, we can get a good model of a circle with just a few
   dozen points.
<p>
   Our {@code Model}'s represent geometric objects as a "wire-frame" of line
   segments, that is, a geometric object is drawn as a collection of "edges".
   This is a fairly simplistic way of doing 3D graphics and we will improve
   this in later renderers.
<p>
   See
<br> <a href="http://en.wikipedia.org/wiki/Wire-frame_model" target="_top">
              http://en.wikipedia.org/wiki/Wire-frame_model</a>
<br>or
<br> <a href="https://www.google.com/search?q=computer+graphics+wireframe&tbm=isch" target="_top">
              https://www.google.com/search?q=computer+graphics+wireframe&tbm=isch</a>
*/
public class Model
{
   public final List<Vertex> vertexList;
   public final List<LineSegment> lineSegmentList;

   public String name;
   public boolean visible;
   public boolean debug;

   /**
      Construct an empty {@code Model} object.
   */
   public Model()
   {
      vertexList = new ArrayList<>();
      lineSegmentList = new ArrayList<>();
      name = "";
      visible = true;
      debug = false;
   }


   /**
      Construct an empty {@code Model} object with the given name.

      @param name  a {link String} that is a name for this {@code Model}
   */
   public Model(final String name)
   {
      this();
      this.name = name;
   }


   /**
      Construct a {@code Model} object with all the given data.

      @param vertexList       a {@link Vertex} {link List} for this {@code Model}
      @param lineSegmentList  a {@link LineSegment} {link List} for this {@code Model}
      @param name             a {link String} that is a name for this {@code Model}
      @param visible          a {@code boolean} that determines this {@code Model}'s visibility
      @param debug            a {@code boolean} that turns debugging off/on for this {@code Model}
   */
   public Model(final List<Vertex> vertexList,
                final List<LineSegment> lineSegmentList,
                final String name,
                final boolean visible,
                final boolean debug)
   {
      this.vertexList = vertexList;
      this.lineSegmentList = lineSegmentList;
      this.name = name;
      this.visible = visible;
      this.debug = debug;
   }


   /**
      Add a {@link Vertex} (or vertices) to this {@code Model}'s
      {@link List} of vertices.

      @param vArray  array of {@link Vertex} objects to add to this {@code Model}
   */
   public final void addVertex(final Vertex... vArray)
   {
      for (final Vertex v : vArray)
      {
         vertexList.add( v );
      }
   }


   /**
      Get a {@link LineSegment} from this {@code Model}'s
      {@link List} of line segments.

      @param index  integer index of a {@link LineSegment} from this {@code Model}
      @return the {@link LineSegment} object at the given index
   */
   public final LineSegment getLineSegment(final int index)
   {
      return lineSegmentList.get(index);
   }


   /**
      Add a {@link LineSegment} (or LineSegments) to this {@code Model}'s
      {@link List} of line segments.
      <p>
      NOTE: This method does not add any vertices to the {@code Model}'s
      {@link Vertex} list. This method assumes that the appropriate vertices
      have been added to the {@code Model}'s {@link Vertex} list.

      @param lsArray  array of {@link LineSegment} objects to add to this {@code Model}
   */
   public final void addLineSegment(final LineSegment... lsArray)
   {
      for (final LineSegment ls : lsArray)
      {
         lineSegmentList.add(ls);
      }
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Model} object
   */
   @Override
   public String toString()
   {
      String result = "";
      result += "Model: " + name + "\n";
      result += "This Model's visibility is: " + visible + "\n";
      result += "Model has " + vertexList.size() + " vertices.\n";
      result += "Model has " + lineSegmentList.size() + " line segments.\n";
      int i = 0;
      for (final Vertex v : this.vertexList)
      {
         result += i + ": " + v.toString();
         ++i;
      }
      //result = "Printing out this Model's " + lineSegmentList.size() + " Line segments:\n";
      i = 0;
      for (final LineSegment ls : this.lineSegmentList)
      {
         result += i + ": " + ls.toString();
         ++i;
      }
      //result += "Done printing out Model\n";
      return result;
   }
}
