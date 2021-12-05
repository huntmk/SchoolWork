/*

*/

package renderer.scene;

import java.util.List;
import java.util.ArrayList;

/**
   A {@code Scene} data structure is a {@link List} of {@link Model}
   data structures and a {@link Camera} data structure.
<p>
   Each {@link Model} object represents a distinct geometric object
   in the scene.
<p>
   The {@link Camera} object determines a "view volume", which
   determines how much of the scene is actually visible (to the
   camera) and gets rendered into the framebuffer.
*/
public final class Scene
{
   public final List<Model> modelList;

   public Camera camera;


   /**
      Construct a {@code Scene} with a default {@link Camera} object.
   */
   public Scene()
   {
      modelList = new ArrayList<>();
      camera = new Camera();
   }


   /**
      Construct a {@code Scene} with the given {@link Camera} object.

      @param camera  {@link Camera} object for this {@code Scene}
   */
   public Scene(final Camera camera)
   {
      modelList = new ArrayList<>();
      this.camera = camera;
   }


   /**
      Construct a {@code Scene} with the given {@link Camera} object
      and {@link List} of {@link Model} objects.

      @param camera     {@link Camera} object for this {@code Scene}
      @param modelList  {@link List} of {@link Model} objects
   */
   public Scene(final Camera camera, final List<Model> modelList)
   {
      this.modelList = modelList;
      this.camera = camera;
   }


   /**
      Get a reference to this {@code Scene}'s {@link Camera} object.

      @return {@link Camera} object from this {@code Scene}
   */
   public Camera getCamera()
   {
      return camera;
   }


   /**
      Change this {@code Scene}'s {@link Camera} to the given {@link Camera} object.

      @param camera  new {@link Camera} object for this {@code Scene}
   */
   public void setCamera(final Camera camera)
   {
      this.camera = camera;
   }


   /**
      Get a reference to the {@link Model} at the given index in this {@code Scene}'s
      {@link List} of {@link Model}s.

      @param index  index of the {@link Model} to return
      @return {@link Model} at the specified index in the {@link List} of {@link Model}s
      @throws IndexOutOfBoundsException if the index is out of range
              {@code (index < 0 || index >= size())}
   */
   public Model getModel(final int index)
   {
      return modelList.get(index);
   }


   /**
      Set a reference to the given {@link Model} object at the given index in this {@code Scene}'s
      {@link List} of {@link Model}s.

      @param index  index of the {@link Model} to set
      @param model  {@link Model} object to place at the specified index in the {@link List} of {@link Model}s
      @throws IndexOutOfBoundsException if the index is out of range
              {@code (index < 0 || index >= size())}
   */
   public void setModel(final int index, final Model model)
   {
      modelList.set(index, model);
   }


   /**
      Add a {@link Model} (or Models) to this {@code Scene}.

      @param mArray  array of {@link Model}s to add to this {@code Scene}
   */
   public void addModel(final Model... mArray)
   {
      for (final Model model : mArray)
      {
         modelList.add(model);
      }
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Scene} object
   */
   @Override
   public String toString()
   {
      String result = "";
      result += camera.toString();
      result += "This Scene has " + modelList.size() + " models\n";
      int i = 0;
      for (final Model m : modelList)
      {
         result += "Model " + (++i) + "\n";
         result += m.toString();
      }
      return result;
   }
}
