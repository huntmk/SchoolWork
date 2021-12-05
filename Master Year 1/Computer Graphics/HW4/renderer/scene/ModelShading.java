/*

*/

package renderer.scene;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;

/**
   This is a library of static methods that
   add color shading to a {@link Model}.
*/
public class ModelShading
{
   /**
      Set each {@link Color} in the {@link Model}'s color list
      to the same {@link Color}.

      @param model  {@link Model} whose color list is being manipulated
      @param c      {@link Color} for all of this model's {@link Vertex} objects
   */
   public static void setColor(final Model model, final Color c)
   {
      if (model.colorList.isEmpty())
      {
         for (int i = 0; i < model.vertexList.size(); ++i)
         {
            model.colorList.add(c);
         }
      }
      else
      {
         for (int i = 0; i < model.colorList.size(); ++i)
         {
            model.colorList.set(i, c);
         }
      }
   }


   /**
      Set each {@link Color} in the {@link Model}'s color list
      to the same random {@link Color}.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRandomColor(final Model model)
   {
      setColor(model, randomColor());
   }


   /**
      Set each {@link Color} in the {@link Model}'s color list
      to a different random {@link Color}.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRandomColors(final Model model)
   {
      if (model.colorList.isEmpty())
      {
         setRandomVertexColors(model);
      }
      else
      {
         for (int i = 0; i < model.colorList.size(); ++i)
         {
            model.colorList.set(i, randomColor());
         }
      }
   }


   /**
      Set each {@link Vertex} in the {@link Model}
      to a different random {@link Color}.
      <p>
      NOTE: This will destroy whatever "color structure"
      the model might possess.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRandomVertexColors(final Model model)
   {
      model.colorList = new ArrayList<Color>();
      for (int i = 0; i < model.vertexList.size(); ++i)
      {
         model.colorList.add( randomColor() );
      }
      for (final LineSegment ls : model.lineSegmentList)
      {
         ls.cIndex[0] = ls.vIndex[0];
         ls.cIndex[1] = ls.vIndex[1];
      }
   }


   /**
      Set each {@link LineSegment} in the {@link Model}
      to a different (uniform) random {@link Color}.
      <p>
      NOTE: This will destroy whatever "color structure"
      the model might possess.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRandomLineSegmentColors(final Model model)
   {
      model.colorList = new ArrayList<>();
      int index = 0;
      for (final LineSegment ls : model.lineSegmentList)
      {
         model.colorList.add( randomColor() );
         ls.cIndex[0] = index;
         ls.cIndex[1] = index;
         ++index;
      }
   }


   /**
      Set each {@link LineSegment} in the {@link Model}
      to a different random {@link Color} at each endpoint.
      <p>
      NOTE: This will destroy whatever "color structure"
      the model might possess.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRainbowLineSegmentColors(final Model model)
   {
      model.colorList = new ArrayList<>();
      int index = 0;
      for (final LineSegment ls : model.lineSegmentList)
      {
         model.colorList.add( randomColor() );
         model.colorList.add( randomColor() );
         ls.cIndex[0] = index;
         ls.cIndex[1] = index + 1;
         index += 2;
      }
   }


   /**
      Create a {@link Color} object with randomly generated {@code r},
      {@code g}, and {@code b} values.

      @return a reference to a randomly generated {@link Color} object
   */
   public static Color randomColor()
   {
      final Random generator = new Random();
      final float r = generator.nextFloat();
      final float g = generator.nextFloat();
      final float b = generator.nextFloat();
      return new Color(r, g, b);
   }
}
