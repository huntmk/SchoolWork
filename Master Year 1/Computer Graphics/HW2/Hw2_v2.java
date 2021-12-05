/*
      Course: CS 45500 / CS 51580
      Name: Marcellus Hunt
      Email: mkhunt@pnw.edu
      Assignment: 2
*/

import renderer.scene.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;
import java.util.LinkedList;

/**

*/
public class Hw2_v2
{
   final static int WIDTH  = 800;
   final static int HEIGHT = 800;
   final static int LENGTH = 30;

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

      // Create an empty List of FrameBuffers.
      final LinkedList<FrameBuffer> fbList = new LinkedList<>();

      // Initialize the list of FrameBuffers and create the initial segment of frames.
      for (int i = 0; i < LENGTH; ++i)
      {
         // create a new FrameBuffer
         final FrameBuffer fb = new FrameBuffer(WIDTH,HEIGHT);

         // render the current Scene into the new FrameBuffer
         Pipeline.render(scene, fb.vp);

         // add the new FrameBuffer at the front to the list
         fbList.addFirst(fb);

         // save a post processed frame
         postProcess(fbList).dumpFB2File(String.format("PPM_Hw2_v2_Frame%03d.ppm", i));
         // update the Scene
         updateScene(scene, i);
      }

      
      // Now cycle through the list of FrameBuffers, keeping its length constant.
      for (int i = LENGTH; i <= 450; ++i)
      {
         // remove the oldest FrameBuffer from the tail of the list
         FrameBuffer old = fbList.removeLast();
         
         //clear framebuffer
         old.clearFB();

         // render the current Scene into the recycled FrameBuffer
         Pipeline.render(scene, old.vp);
         
         // add the recycled FrameBuffer at the front of the list
         fbList.addFirst(old);

         // save a post processed frame
         postProcess(fbList).dumpFB2File(String.format("PPM_Hw2_v2_Frame%03d.ppm", i));

         // update the Scene
         updateScene(scene, i);
      }

      // Empty out the list and create the last segment of frames.
      for (int i = 451; i < 450 + LENGTH; ++i)
      {
         // remove the oldest FrameBuffer from the list
         fbList.removeLast();

         // save a post processed frame
         postProcess(fbList).dumpFB2File(String.format("PPM_Hw2_v2_Frame%03d.ppm", i));
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


   /**
      Use the List of FrameBuffer objects to compute, and return,
      a new FrameBuffer object.
   */
   private static FrameBuffer postProcess(final LinkedList<FrameBuffer> fbList)
   {
      //counter will be used to see how far I am in the frame buffer list
      int counter =1;
      Color dim = new Color(1.0f,1.0f,1.0f);
      Color white = new Color(255,255,255);
      final FrameBuffer resultFB = new FrameBuffer(WIDTH, HEIGHT);

      // Iterate through the list of source framebuffers and copy
      // every white pixel from a source framebuffer into resultFB,
      for(FrameBuffer B: fbList)
      {
         //if statements that dims the color every time a FrameBuffer is iterated
         if (counter <3)
         {
            dim = new Color(1.0f,1.0f,1.0f);
         }
         else if (counter >=3 && counter <6)
         {
            dim = new Color(0.9f,0.9f,0.9f);
         }
         else if(counter>=6 && counter <9)
         {
            dim = new Color(0.8f,0.8f,0.8f);
         }
         else if(counter>=9 && counter <12)
         {
            dim = new Color(0.7f,0.7f,0.7f);
         }
         else if(counter>=12 && counter <15)
         {
            dim = new Color(0.6f,0.6f,0.6f);
         }
         else if(counter>=15 && counter <18)
         {
            dim = new Color(0.5f,0.5f,0.5f);
         }
         else if(counter>=18 && counter <21)
         {
            dim = new Color(0.4f,0.4f,0.4f);
         }
         else if(counter>=21 && counter <24)
         {
            dim = new Color(0.3f,0.3f,0.3f);
         }
         else if(counter>=24 && counter <27)
         {
            dim = new Color(0.2f,0.2f,0.2f);
         }
         else if(counter>=27 && counter <29)
         {
            dim = new Color(0.1f,0.1f,0.1f);
         }
         //used double nested for-loop to loop through framebuffer
         for (int i = 0; i < WIDTH; i++)
         {
            for (int j=0; j < HEIGHT; j++)
            {
               //if pixel is white then set it
               if (B.getPixelFB(i, j).equals(white))
                  resultFB.setPixelFB(i, j, dim);
            }
         }
         counter++;
      }
      // but reduce the brightness of the white pixel by an amount
      // proportional to how "old" the source frame is. Don't let any
      // "older" pixel overwrite a "newer" pixel already in resultFB.

      return resultFB;
   }

}
