/*

*/

package renderer.pipeline;
import  renderer.scene.*;

import java.util.List;

/**
   Transform each {@link Vertex} of a {@link Model} from the
   model's (private) local coordinate system to the {@link Camera}'s
   (shared) view coordinate system.
<p>
   For each {@code Vertex} object in a {@code Model} object,
   use a {@link Position}'s model {@link Matrix} to transform the
   {@code Vertex} object's coordinates from the model's coordinate
   system to the camera's view coordinate system.
*/
public class Model2View
{
   /**
      Use a {@link Position}'s model {@link Matrix} to transform each
      {@link Vertex} from a {@link Model}'s coordinate system to the
      {@link Camera}'s view coordinate system.

      @param vertexList   {@link List} of {@link Vertex} objects to transform into view coordinates
      @param modelMatrix  a {@link Position}'s transformation {@link Matrix}
   */
   public static void model2view(List<Vertex> vertexList, Matrix modelMatrix)
   {
      // Mutate each Vertex object so that it contains view coordinates.
      for (Vertex v : vertexList)
      {
         v.set( modelMatrix.times(v) );
      }
   }
}
