 /*
  Course: CS 45500 / CS 51580
  Name: Marcellus Hunt
  Email: mkhunt@pnw.edu
  Assignment: 4

   */

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**

*/
public class OfflinePNW
{
   public static void main(String[] args)
   {
      // Create the Scene object that we shall render.
      final Scene scene = new Scene();

      final double right  = 4;
      final double left   = -right;
      final double top    = 3;
      final double bottom = -top;
      final double near = 3;
      scene.camera.projPerspective(left, right, bottom, top, near);

      // Create a set of x and y axes.
      Model axes = new Axes2D(-2, +2, -2, +4, 8, 12);
      ModelShading.setColor(axes, Color.red);
      Position axes_p = new Position( axes );
      scene.addPosition( axes_p );
      // Push the axes away from where the camera is.
      axes_p.matrix = Matrix.translate(0, 0, -near);

      // Add the letters to the Scene.
      scene.addPosition(new Position( new P() ),
                        new Position( new N() ),
                        new Position( new W() ));

      // Give the letters random colors.
      ModelShading.setRandomColor(scene.getPosition(1).model); // P
      ModelShading.setRandomColor(scene.getPosition(2).model); // N
      ModelShading.setRandomColor(scene.getPosition(3).model); // W

      // Create a FrameBuffer to render our scene into.
      int width  = 1200;
      int height =  900;
      FrameBuffer fb = new FrameBuffer(width, height);

      double firstDown = 0.0;
      double iterationUp = 0.0;
      double secondDown = 0.0;
      // Create the animation frames.
      for (int i = 0; i < 360; i++)
      {
         // Push the letters away from the camera.
         scene.getPosition(1).matrix = Matrix.translate(0, 0, -near); // P
         scene.getPosition(2).matrix = Matrix.translate(0, 0, -near); // N
         scene.getPosition(3).matrix = Matrix.translate(0, 0, -near); // W

         // do P

         //rotate around z axis
         scene.getPosition(1).matrix.mult(Matrix.rotateZ(-1*i));

         //postion P
         scene.getPosition(1).matrix.mult(Matrix.translate(-2, 0, 0));
         


         // do N

         // Rotate N around its center axis.

         //rotation camera coordinate system (u,v,w)
         scene.getPosition(2).matrix.mult( Matrix.translate(0, 0, 0) );

         //rotation around Y axis
         scene.getPosition(2).matrix.mult( Matrix.rotateY(i) );     

         //model coordinat system(-a,-b,-c)
         scene.getPosition(2).matrix.mult( Matrix.translate(-0.5, 0, 0) );

         //move down  y axis for 90 frames
         if (i < 90)
         {
            //move 2 units down within 90 frames
            double move = -2.0/90.0;

            scene.getPosition(2).matrix.mult( Matrix.translate(0, move*firstDown, 0) );
            firstDown++;
         }

         //move up y axis for 180 frames
         else if(i >= 90 & i < 270)
         {
            //move up 5 units within 180 frames
            double move = 5.0/180.0;
            
            scene.getPosition(2).matrix.mult( Matrix.translate(0,-2.0 + (move*iterationUp), 0) );
            iterationUp++;
         }

         //move down y axis for 90 frames
         else if( i>=270)
         {
            //move 3 down units in 90 frames
            double move = -3.0/90.0;

            scene.getPosition(2).matrix.mult( Matrix.translate(0,3.0 +(move*secondDown), 0) );
            secondDown++;
         }
         

         // do W

         //Rotation camera coordinate (u,v,w)
         scene.getPosition(3).matrix.mult(Matrix.translate(2, 1, 0));
         
         //Rotation around Z axis
         scene.getPosition(3).matrix.mult(Matrix.rotateZ(i));

         //model coordinate system (-a,-b,-c)
         scene.getPosition(3).matrix.mult(Matrix.translate(-1, -1, 0));



         // Render again.
         fb.clearFB(Color.black);
         Pipeline.render(scene, fb);
         fb.dumpFB2File(String.format("PPM_PNW_Frame%03d.ppm", i));
      }
   }
}
