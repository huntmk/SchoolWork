/*

*/

import renderer.scene.*;

/**
   A two-dimensional model of the letter W.
*/
public class W extends Model
{
   /**
      The letter W.
   */
   public W()
   {
      super("W");

      addVertex(new Vertex(0.00,1.00,0.0),
				new Vertex(0.2, 0.00,0.0),
				new Vertex(0.4, 0.00,0.0),
				new Vertex(0.5, 0.5, 0.0),
				new Vertex(0.6, 0.00,0.0),
				new Vertex(0.8, 0.00,0.0),
				new Vertex(1.00,1.00,0.0),
				new Vertex(0.8, 1.00,0.0),
				new Vertex(0.7, 0.5, 0.0),
				new Vertex(0.5, 1.00,0.0),
				new Vertex(0.3, 0.5, 0.0),
				new Vertex(0.2, 1.00,0.0));

      addLineSegment(new LineSegment(0,1),
					 new LineSegment(1,2),
					 new LineSegment(2,3),
					 new LineSegment(3,4),
					 new LineSegment(4,5),
					 new LineSegment(5,6),
					 new LineSegment(6,7),
					 new LineSegment(7,8),
					 new LineSegment(8,9),
					 new LineSegment(9,10),
					 new LineSegment(10,11),
					 new LineSegment(11,0));
   }
}
