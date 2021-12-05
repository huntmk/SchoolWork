/*
      Course: CS 45500 / CS 51580
      Name: Marcellus Hunt
      Email: mkhunt@pnw.edu
      Assignment: 2

*/

import renderer.scene.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

/**

*/
public class Hw2_v1
{
   final static int WIDTH  = 800;
   final static int HEIGHT = 800;

   public static void main(String[] args)
   {
      // Turn on clipping in the rasterizer.
      Rasterize_Clip.doClipping = true;

      Scene scene = new Scene();

      scene.addModel( new P(), new N(), new W() );

      // Push the models away from where the camera is.
      for (Model m : scene.modelList)
      {
         for (int i = 0; i < m.vertexList.size(); ++i)
         {
            final Vertex v = m.vertexList.get(i);
            m.vertexList.set(i, new Vertex(v.x,
                                           v.y,
                                           v.z - 2));
         }
      }

      // Give each model an initial location.
      moveModel(scene.getModel(0), -1.6, 0); // P
      moveModel(scene.getModel(1), -0.5, 0); // N
      moveModel(scene.getModel(2),  0.6, 0); // W

      final FrameBuffer fb = new FrameBuffer(WIDTH, HEIGHT);

      // Create the frames of an animation.
      for (int i = 0; i < 450; ++i)
      {
         fb.clearFB();
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_Hw2_v1_Frame%03d.ppm", i));
         updateScene(scene, i);
      }
   }


   private static void updateScene(Scene scene, int frameNumber)
   {
      //There are six movements that need to be performed. There are 450 frames to account for
      //The movemnts that move 1 unit takes up 37 frames each rounding to 150. 
      //Three units down-right takes up 225 frames. Two units left takes up 75 frames

            
      //one units up
      if (frameNumber < 38)
      {
         moveModels(scene, 0, (1.0/37.5));
      }
      
      //three units down-right
      else if (frameNumber >= 37 && frameNumber < 262)
      {
         moveModels(scene,(3.0/225.0),-1.0*(3.0/225.0));
      }     

      //one unit left
      else if (frameNumber >= 262 && frameNumber < 300)
      {
         moveModels(scene, -1.0*(1.0/37.5), 0);
      }
      
      
      //one unit up-left
      else if(frameNumber>= 300 && frameNumber <338)
      {
         moveModels(scene, -1.0*(1.0/37.5), 1.0/37.5);
      }

      //two units left
      else if(frameNumber >=338 && frameNumber < 412)
      {
         moveModels(scene, -1.0*(2.0/75), 0);
      }

      //one unit up-right
      else if(frameNumber >= 412 && frameNumber < 450)
      {
         moveModels(scene, 1.0/37.5, 1.0/37.5);
      }
      
   }

   private static void moveModels(Scene scene, double deltaX, double deltaY)
   {
      //Move all models in the scene by calling moveModel functions
      for (Model x: scene.modelList)
      {
         moveModel(x, deltaX, deltaY);
      }
         
   }

   private static void moveModel(Model model, double deltaX, double deltaY)
   {
      //Move Model by adding delta x and y to the model's current vertex
      for (int i = 0; i < model.vertexList.size(); i++)
      {
         final Vertex v = model.vertexList.get(i);
         model.vertexList.set(i,new Vertex(v.x+deltaX, v.y+deltaY,v.z));
      }
      
   }

}
