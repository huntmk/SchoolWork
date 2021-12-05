/*

*/

package renderer.scene;


/**
   A {@code Position} data structure represents a geometric object in a
   distinct position (both location and orientation) in three-dimensional
   space as part of a {@link Scene}.
<p>
   A {@code Position} object holds references to a {@link Model} object
   and a {@link Matrix} object. The {@link Model} represents the geometric
   object in the {@link Scene}. The {@link Matrix} determines the model's
   location and orientation in the {@link Camera}'s view coordinate system.
   The {@code Position}'s matrix helps us solve the problem of placing
   and moving a model in a scene.
<p>
   When the renderer renders this {@code Position}'s {@link Model} into
   a {@link renderer.framebuffer.FrameBuffer}, the first stage of the
   rendering pipeline, {@link renderer.pipeline.Model2View}, multiplies
   every {@link Vertex} in the {@link Model}'s vertex list by this
   {@code Position}'s {@link Matrix}, which converts the coordinates in
   each {@link Vertex} from the model's own local coordinate system to
   the {@link Camera}'s view coordinate system (which is "shared" by all
   the other models in the scene). This matrix multiplication has the effect
   of "placing" the model in view space at an appropriate location (using
   the translation part of the matrix) and in the appropriate orientation
   (using the rotation part of the matrix).
*/
public class Position
{
   public Model  model;
   public Matrix matrix;

   /**
      Construct a default {@code Position} with the identity {@link Matrix}
      and no {@link Model} object.
   */
   public Position()
   {
      this.model = null;
      this.matrix = Matrix.identity(); // identity matrix
   }


   /**
      Construct a {@code Position} with the identity {@link Matrix}
      and the given {@link Model} object.

      @param model  {@link Model} object to place at this {@code Position}
   */
   public Position(final Model model)
   {
      this.model = model;
      this.matrix = Matrix.identity(); // identity matrix
   }


   /**
      Set this {@code Position}'s {@link Model} object.

      @param model  {@link Model} object to place at this {@code Position}
   */
   public void setModel(final Model model)
   {
      this.model = model;
   }


   /**
      Reset this {@code Position}'s {@link Matrix} to the identity matrix.

      @return a reference to this {@code Position}'s {@link Matrix} to facilitate chaining method calls
   */
   public Matrix matrix2Identity()
   {
      this.matrix = Matrix.identity();
      return this.matrix;
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Position} object
   */
   @Override
   public String toString()
   {
      String result = "";
      result += "This Position's Matrix is\n";
      result += matrix;
      result += "This Position's Model is\n";
      result += (null == model) ? "null\n" : model;
      return result;
   }
}
