/*

*/

package renderer.pipeline;
import  renderer.scene.*;
import  renderer.framebuffer.*;

/**
   This renderer takes as its input a {@link Scene} data structure
   and a {@link FrameBuffer.Viewport} within a {@link FrameBuffer}
   data structure. This renderer mutates the {@link FrameBuffer.Viewport}
   so that it is filled in with the rendered image of the geometric
   scene represented by the {@link Scene} object.
<p>
   This implements our first rendering pipeline. It has just two
   pipeline stages.
   <ol>
   <li>The {@link Projection} transformation stage projects each
   {@link Vertex} from a {@link Model} into the {@link Camera}'s
   image plane.
   <li>The {@link Rasterize_Clip} stage converts {@link LineSegment}s
   in the {@link Camera}'s Image Plane into pixels in the
   {@link FrameBuffer.Viewport}, and rasterized pixels from the
   {@link LineSegment} that are not contained in the {@link Camera}'s
   View Rectangle are "clipped off".
   </ol>
   Notice that the first stage acts on the {@link Model}'s vertices, and
   the second stage acts on the {@link Model}'s {@link LineSegment}s.
   That is, we project vertices and we rasterize line segments.
*/
public final class Pipeline
{
   public static boolean debug = false;

   /**
      Mutate the {@link FrameBuffer}'s given {@link FrameBuffer.Viewport}
      so that it holds the rendered image of the {@link Scene} object.

      @param scene  {@link Scene} object to render
      @param vp     {@link FrameBuffer.Viewport} to hold rendered image of the {@link Scene}
   */
   public static void render(final Scene scene, final FrameBuffer.Viewport vp)
   {
      // Render every Model in the Scene.
      for (final Model model : scene.modelList)
      {
         if ( model.visible )
         {
            logMessage(model, "==== Render Model: " + model.name + " ====");

            check(model);

            logVertexList("0. Camera   ", model);

            // 1. Apply the projection transformation.
            final Model model2 = Projection.project(model, scene.camera);

            logVertexList("1. Projected", model2);

            // 2. Rasterize (and possibly clip) each line segment into pixels.
            for (final LineSegment ls : model2.lineSegmentList)
            {
               logLineSegment("2. Rasterize_Clip", model2, ls);

               Rasterize_Clip.rasterize(model2, ls, vp);
            }

            logMessage(model, "==== End Model: " + model.name + " ====");
         }
         else
         {
            logMessage(model, "==== Hidden model: " + model.name + " ====");
         }
      }
   }


   /**
      Determine if there are any obvious problems with the {@link Model}
      being rendered. The purpose of these checks is to make the renderer
      a bit more user friendly. If a user makes a simple mistake and tries
      to render a {@link Model} that is missing vertices or line segments,
      then the user gets a helpful error message.

      @param model  the {@link Model} being rendered
   */
   public static void check(final Model model)
   {
      boolean error = false;
      if (model.vertexList.isEmpty())
      {
         System.err.println("***WARNING: This model does not have any vertices.");
         error = true;
      }
      if (model.lineSegmentList.isEmpty())
      {
         System.err.println("***WARNING: This model does not have any line segments.");
         error = true;
      }
      if (error)
      {
         System.err.println(model);
      }
   }


   /**
      Use the pipeline's and the model's debug variables to determine
      if the given message should be printeed to stderr.

      @param model    the {@link Model} being rendered
      @param message  {@link String} to output to stderr
   */
   public static void logMessage(final Model model, final String message)
   {
      if (Pipeline.debug || model.debug)
         System.err.println( message );
   }


   /**
      This method prints a {@link String} representation of the given
      {@link Model}'s {@link Vertex} list.

      @param stage  name for the pipeline stage
      @param model  the {@link Model} whose vertex list is to be printed
   */
   private static void logVertexList(final String stage, final Model model)
   {
      if (Pipeline.debug || model.debug)
      {
         int i = 0;
         for (final Vertex v : model.vertexList)
         {
            System.err.printf( stage + ": index = %3d, " + v.toString(), i );
            ++i;
         }
      }
   }


   /**
      This method prints a {@link String} representation of the given
      {@link LineSegment}.

      @param stage  name for the pipeline stage
      @param model  {@link Model} that the {@link LineSegment} {@code ls} comes from
      @param ls     {@link LineSegment} whose string representation is to be printed
   */
   private static void logLineSegment(final String stage, final Model model, final LineSegment ls)
   {
      if (Pipeline.debug || model.debug)
      {
         System.err.print( stage + ": " + ls.toString() );
         final int index0 = ls.index[0];
         final int index1 = ls.index[1];
         final Vertex v0 = model.vertexList.get(index0);
         final Vertex v1 = model.vertexList.get(index1);
         System.err.printf("   index = %3d, " + v0.toString(), index0);
         System.err.printf("   index = %3d, " + v1.toString(), index1);
      }
   }
}
