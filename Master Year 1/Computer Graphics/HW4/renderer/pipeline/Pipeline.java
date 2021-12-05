/*

*/

package renderer.pipeline;
import  renderer.scene.*;
import  renderer.framebuffer.*;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
   This renderer takes as its input a {@link Scene} data structure
   and a {@link FrameBuffer.Viewport} within a {@link FrameBuffer}
   data structure. This renderer mutates the {@link FrameBuffer.Viewport}
   so that it is filled in with the rendered image of the scene
   represented by the {@link Scene} object.
<p>
   This implements our seventh rendering pipeline. It adds a vertex
   transformation stage, {@link Model2View}, that converts vertex
   coordinates from the {@link Model}'s (private) coordinate system
   to the {@link Camera}'s (shared) view coordinate system. There are
   five pipeline stages.
*/
public class Pipeline
{
   public static boolean debug = false;

   /**
      Mutate the {@link FrameBuffer}'s default {@link FrameBuffer.Viewport}
      so that it holds the rendered image of the {@link Scene} object.

      @param scene  {@link Scene} object to render
      @param fb     {@link FrameBuffer} to hold rendered image of the {@link Scene}
   */
   public static void render(final Scene scene, final FrameBuffer fb)
   {
      render(scene, fb.vp); // render into the default viewport
   }


   /**
      Mutate the {@link FrameBuffer}'s given {@link FrameBuffer.Viewport}
      so that it holds the rendered image of the {@link Scene} object.

      @param scene  {@link Scene} object to render
      @param vp     {@link FrameBuffer.Viewport} to hold rendered image of the {@link Scene}
   */
   public static void render(final Scene scene, final FrameBuffer.Viewport vp)
   {
      // For every Position in the Scene, render the Position's Model.
      for (Position position : scene.positionList)
      {
         if ( position.model.visible )
         {
            logMessage(position.model, "==== Render Model: " + position.model.name + " ====");

            check(position.model);

            // 0. Make a deep copy of the Position's Model.
            Model model2 = new Model(position.model);

            logVertexList("0. Model    ", model2);

            // 1. Apply the Position's model-to-view coordinate transformation.
            Model2View.model2view(model2.vertexList, position.matrix);

            logVertexList("1. View     ", model2);

            // 2. Apply the Camera's normalizing view-to-camera coordinate transformation.
            View2Camera.view2camera(model2.vertexList, scene.camera);

            logVertexList("2. Camera   ", model2);

            // 3. Apply the Camera's projection transformation.
            Projection.project(model2.vertexList, scene.camera);

            logVertexList("3. Projected", model2);

            // 4. Clip each line segment to the camera's view rectangle.
            List<LineSegment> lineSegmentList2 = new ArrayList<>();
            for (LineSegment ls : model2.lineSegmentList)
            {
               logLineSegment("4. Clipping", model2, ls);

               if ( Clip.clip(model2, ls) )
               {
                  // Keep the line segments that are visible.
                  lineSegmentList2.add(ls);

                  logLineSegment("4. Clipping (accept)", model2, ls);
               }
               else
               {
                  logLineSegment("4. Clipping (reject)", model2, ls);
               }
            }
            // Replace the model's original list of line segments
            // with the list of clipped line segments.
            model2.lineSegmentList = lineSegmentList2;

            logVertexList("4. Clipped  ", model2);
            logLineSegmentList("4. Clipped  ", model2);

            // 5. Rasterize each visible line segment into pixels.
            for (LineSegment ls : model2.lineSegmentList)
            {
               logLineSegment("5. Rasterize", model2, ls);

               RasterizeAntialias.rasterize(model2, ls, vp);
            }

            logMessage(model2, "==== End Model: " + model2.name + " ====");
         }
         else
         {
            logMessage(position.model, "==== Hidden model: " + position.model.name + " ====");
         }
      }
   }


   /**
      Determine if there are any obvious problems with the {@link Model}
      being rendered. The purpose of these checks is to make the renderer
      a bit more user friendly. If a user makes a simple mistake and tries
      to render a {@link Model} that is missing vertices, line segments,
      or colors, then the user gets a helpful error message.

      @param model  the {@link Model} being rendered
   */
   public static void check(Model model)
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
      if (model.colorList.isEmpty())
      {
         System.err.println("***WARNING: This model does not have any colors.");
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
   public static void logMessage(Model model, String message)
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
   private static void logVertexList(String stage, Model model)
   {
      if (Pipeline.debug || model.debug)
      {
         int i = 0;
         for (Vertex v : model.vertexList)
         {
            System.err.printf( stage + ": index = %3d, " + v.toString(), i );
            ++i;
         }
      }
   }


   /**
      This method prints a {@link String} representation of the given
      {@link Model}'s {@link LineSegment} list.

      @param stage  name for the pipeline stage
      @param model  the {@link Model} whose line segment list is to be printed
   */
   private static void logLineSegmentList(String stage, Model model)
   {
      if (Pipeline.debug || model.debug)
      {
         for (LineSegment ls : model.lineSegmentList)
         {
            System.err.printf( stage + ": " + ls.toString() );
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
   private static void logLineSegment(String stage, Model model, LineSegment ls)
   {
      if (Pipeline.debug || model.debug)
      {
         System.err.print( stage + ": " + ls.toString() );
         int index0 = ls.vIndex[0];
         int index1 = ls.vIndex[1];
         Vertex v0 = model.vertexList.get(index0);
         Vertex v1 = model.vertexList.get(index1);
         System.err.printf("   vIndex = %3d, " + v0.toString(), index0);
         System.err.printf("   vIndex = %3d, " + v1.toString(), index1);

         int cIndex0 = ls.cIndex[0];
         int cIndex1 = ls.cIndex[1];
         Color c0 = model.colorList.get(cIndex0);
         Color c1 = model.colorList.get(cIndex1);
         System.err.printf("   cIndex = %3d, " + c0.toString() + "\n", cIndex0);
         System.err.printf("   cIndex = %3d, " + c1.toString() + "\n", cIndex1);
      }
   }
}
