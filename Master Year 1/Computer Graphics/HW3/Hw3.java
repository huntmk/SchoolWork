/*
      Course: CS 45500 / CS 51580
      Name: Marcellus Hunt
      Email: mkhunt@pnw.edu
      Assignment: 3

   */

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.util.ArrayList;

/**

*/
public class Hw3 implements KeyListener, ComponentListener,
                            MouseListener, MouseMotionListener
{
   //instance variables
   private final Scene scene;
   private final JFrame jf;
   private final FrameBufferPanel fbp;
   //toggles mouse information
   private boolean mouseInformation = false;

   //variables to keep track of center
   Vertex square_1;
   private final double sq1Size;
   
   //used to keep know if shape was hit to move/drag it.
   private boolean sq1Cond;

   Vertex square_2;
   private final double sq2Size;

   //used to keep know if shape was hit to move/drag it.
   private boolean sq2Cond;

   Vertex square_3;
   private final double sq3Size;

   //used to keep know if shape was hit to move/drag it.
   private boolean sq3Cond;

   Vertex diamond;
   private final double diamondSize;

   //used to keep know if shape was hit to move/drag it.
   private boolean diamondCond;

   Vertex circle;
   private final double circleSize;

   //used to keep know if shape was hit to move/drag it.
   private boolean circleCond;


   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public Hw3()
   {
      // Create the Scene object that we shall render
      scene = new Scene();

      // Create several Model objects.
      scene.addModel(new Square(1));
      scene.addModel(new Square(2));
      scene.addModel(new Square(3));
      scene.addModel(new Circle(3, 4));
      scene.addModel(new Circle(3, 64));

      //keeps track of sizes
      sq1Size = 1;
      sq2Size = 2;
      sq3Size = 3;
      diamondSize =3; 
      circleSize = 3;
      


      // Give each model a useful name.
      scene.modelList.get(0).name = "Square_1";
      scene.modelList.get(1).name = "Square_2";
      scene.modelList.get(2).name = "Square_3";
      scene.modelList.get(3).name = "Diamond";
      scene.modelList.get(4).name = "Circle";

      // Push the models away from where the camera is.
      for (Model m : scene.modelList)
      {
         moveModel(m, 0, 0, -10);
      }

      // Give each model an initial position in the scene.
      moveModel(scene.modelList.get(0),  0,  0, 0);
      moveModel(scene.modelList.get(1), -5, -5, 0);
      moveModel(scene.modelList.get(2), +5, +5, 0);
      moveModel(scene.modelList.get(3), +5, -5, 0);
      moveModel(scene.modelList.get(4), -5, +5, 0);

      //keep tracks of center
      square_1 = new Vertex(0, 0, -10);
      square_2 = new Vertex(-5, -5, -10);
      square_3 = new Vertex(5, 5, -10);
      diamond = new Vertex(5, -5, -10);
      circle = new Vertex(-5,5,-10);

      // Define initial dimensions for a FrameBuffer.
      final int fbWidth  = 1000;
      final int fbHeight = 1000;

      // Create a FrameBufferPanel that holds a FrameBuffer.
      fbp = new FrameBufferPanel(fbWidth, fbHeight);

      // Create a JFrame that will hold the FrameBufferPanel.
      jf = new JFrame("Hw3");
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //jf.setLocationByPlatform(true);
      // Place the FrameBufferPanel in the JFrame.
      jf.getContentPane().add(fbp, BorderLayout.CENTER);
      jf.pack();
      jf.setVisible(true);

      jf.addKeyListener(this);
      jf.addComponentListener(this);
      fbp.addMouseListener(this);
      fbp.addMouseMotionListener(this);

      // Render.
      FrameBuffer fb = fbp.getFrameBuffer();
      fb.clearFB();
      Pipeline.render(scene, fb);
      fbp.update();
   }

   //hit methods to find if each shape was clicked on 

   public boolean hitSquare1(double Xc, double Yc)
   {
      double rightSide = square_1.x + sq1Size;
      double leftSide = square_1.x - sq1Size ;
      double topSide = square_1.y + sq1Size;
      double bottomSide = square_1.y - sq1Size;

      //if the hit point is on/between the edges then return true
      if(Xc <= rightSide && Xc >= leftSide && Yc <= topSide && Yc >= bottomSide)
      {
         return true;
      }
     
      return false;
   }

   
   public boolean hitSquare2(double Xc, double Yc)
   {
      double rightSide = square_2.x + sq2Size;
      double leftSide = square_2.x - sq2Size ;
      double topSide = square_2.y + sq2Size;
      double bottomSide = square_2.y - sq2Size;


      //if the hit point is on/between the edges then return true
      if(Xc <= rightSide && Xc >= leftSide && Yc <= topSide && Yc >= bottomSide)
      {
         return true;
      }
     
      return false;
   }

   public boolean hitSquare3(double Xc, double Yc)
   {
      double rightSide = square_3.x + sq3Size;
      double leftSide = square_3.x - sq3Size ;
      double topSide = square_3.y + sq3Size;
      double bottomSide = square_3.y - sq3Size;


      if(Xc <= rightSide && Xc >= leftSide && Yc <= topSide && Yc >= bottomSide)
      {
         return true;
      }
     
      return false;
   }
   
   
   public boolean hitDiamond(double Xc, double Yc)
   {
      //cartesian equation
      double dx = Math.abs(Xc - diamond.x);
      double dy = Math.abs(Yc - diamond.y);

      //divide by diamond size so radius is equal to 1
      if(dx/diamondSize + dy/diamondSize <= 1)
      {
         return true;
      }
      return false;
   }

   public boolean hitCircle(double Xc, double Yc)
   {
      //use distance formula
      //x_coordinate - center_x)^2
      double xSq = (Xc - circle.x)*(Xc - circle.x);

      //y_coordinate - center_y^2
      double ySq = (Yc - circle.y)*(Yc - circle.y);

      if((xSq+ySq) <= (circleSize*circleSize))
      {
         return true;
      }

      return false;
   }
   
   // Implement the KeyListener interface.
   @Override public void keyPressed(KeyEvent e){}
   @Override public void keyReleased(KeyEvent e){}
   @Override public void keyTyped(KeyEvent e)
   {
	  //System.out.println( e );

      final char c = e.getKeyChar();
      if ('d' == c)
      {
         Pipeline.debug = ! Pipeline.debug;
         //Rasterize_Clip.debug = ! Rasterize_Clip.debug;
      }
      //else if equals 'i' , toggle mouse debugging information
      else if ('i'== c)
      {
         mouseInformation = ! mouseInformation;
      }
	   else if ('c' == c)
      {
         Rasterize_Clip.doClipping = ! Rasterize_Clip.doClipping;
         System.out.print("Clipping is turned ");
         System.out.println(Rasterize_Clip.doClipping ? "On" : "Off");
      }
      //else if equals 'r/R' reset windows aspect ratio
      else if ('r'== c)
      {
         if(fbp.getWidth() < fbp.getHeight())
         {
            //set height same as width
            // Get the new size of the FrameBufferPanel.
            final int w = fbp.getWidth();
            final int h = fbp.getWidth();
      
            // Create a new FrameBuffer that fits the FrameBufferPanel.
            final FrameBuffer fb = new FrameBuffer(w, h);
            fbp.setFrameBuffer(fb);
            jf.pack();

         }
         else if (jf.getHeight() < jf.getWidth())
         {
            //set width same as height
            //Get the new size of the FrameBufferPanel.
            final int w = fbp.getHeight();
            final int h = fbp.getHeight();
      
            // Create a new FrameBuffer that fits the FrameBufferPanel.
            final FrameBuffer fb = new FrameBuffer(w, h);
            fbp.setFrameBuffer(fb);
            jf.pack();

         }
      }
      else if ('R'== c)
      {
         if (jf.getWidth() > jf.getHeight())
         {
            //set height same as width
            // Get the new size of the FrameBufferPanel.
            final int w = fbp.getWidth();
            final int h = fbp.getWidth();
      
            // Create a new FrameBuffer that fits the FrameBufferPanel.
            final FrameBuffer fb = new FrameBuffer(w, h);
            fbp.setFrameBuffer(fb);
            jf.pack();

         }
         else if (jf.getHeight() > jf.getWidth())
         {
            //set width same as height
            //Get the new size of the FrameBufferPanel.
            final int w = fbp.getHeight();
            final int h = fbp.getHeight();
      
            // Create a new FrameBuffer that fits the FrameBufferPanel.
            final FrameBuffer fb = new FrameBuffer(w, h);
            fbp.setFrameBuffer(fb);
            jf.pack();            
           
         }
      }
	   else if ('h' == c)
      {
         print_help_message();
         return;
      }
      
      // Render again.
      final FrameBuffer fb = fbp.getFrameBuffer();
      fb.clearFB();
      Pipeline.render(scene, fb);
      fbp.update();
      jf.repaint();
	  
   }

   // Implement the MouseListener interface.
   @Override public void mouseEntered(MouseEvent e){}
   @Override public void mouseExited(MouseEvent e)
   {
      if(mouseInformation)
      {
         System.out.println("exited");
      }
   }
   @Override public void mousePressed(MouseEvent e)
   {
      //Pixel to Pixel plane
      double Xvp = e.getX() +1;
      double Yvp = fbp.getHeight()- e.getY();

     
      //Pixel plane to Viewplane
      double Xp = (2.0/fbp.getWidth())* (Xvp-0.5)-1;
      double Yp = (2.0/fbp.getHeight())* (Yvp-0.5)-1;
      double Zp = -1;

      //Viewplane to Camera space
      double Zc = -10;
      double Xc = -Zc*Xp;
      double Yc = -Zc*Yp;

      //variables store these conditions that will be used when dragging the shapes
      //square 1
      sq1Cond = hitSquare1(Xc, Yc);

      //square 2
      sq2Cond = hitSquare2(Xc, Yc);

      //square 3
      sq3Cond = hitSquare3(Xc, Yc);

      //diamond
      diamondCond = hitDiamond(Xc, Yc);

      //circle
      circleCond = hitCircle(Xc, Yc);

      if(mouseInformation)
      {
         //square 1
         if(sq1Cond)
         {
            //output "Square 1"
            System.out.println(scene.getModel(0).name);
         }

         //square 2
         if(sq2Cond)
         {
            //output "Square 2"
            System.out.println(scene.getModel(1).name);
         }

         //square 3
         if(sq3Cond)
         {
            //output "Square 3"
            System.out.println(scene.getModel(2).name);
         }

         
         //diamond
         if(diamondCond)
         {
            System.out.println(scene.getModel(3).name);
         }

         //circle
         if(circleCond)
         {
            System.out.println(scene.getModel(4).name);
         }
         
         System.out.printf("%d , %d%n",e.getX(),e.getY());
         System.out.printf("(%.5f   %.5f   %.5f)%n%n",Xc,Yc,Zc);
        
         //output camera space coordinates
      }
      
   }
   @Override public void mouseReleased(MouseEvent e)
   {
      if(mouseInformation)
      {
         System.out.println("released");
      }
   }
   @Override public void mouseClicked(MouseEvent e){}

   // Implement the MouseMotionListener interface.
   @Override public void mouseMoved(MouseEvent e){}
   @Override public void mouseDragged(MouseEvent e)
   {
      //Pixel to Pixel plane
      double Xvp = e.getX() +1;
      double Yvp = fbp.getHeight()- e.getY();

     
      //Pixel plane to Viewplane
      double Xp = (2.0/fbp.getWidth())* (Xvp-0.5)-1;
      double Yp = (2.0/fbp.getHeight())* (Yvp-0.5)-1;
      double Zp = -1;

      //Viewplane to Camera space
      double Zc = -10;
      double Xc = -Zc*Xp;
      double Yc = -Zc*Yp;

      //holds difference between new and current
      double xDif = 0;
      double yDif = 0;

      
      if (sq1Cond)
      {
         //calculate difference
         //new - current
         xDif = Xc - square_1.x;
         yDif = Yc - square_1.y;

         //update represenation
         square_1 = new Vertex((xDif + square_1.x), (yDif + square_1.y), 0);
         
         //move model
         moveModel(scene.modelList.get(0), xDif, yDif, 0);

         
      }

      if (sq2Cond)
      {
         //calculate difference
         //new - current
         xDif = Xc - square_2.x;
         yDif = Yc - square_2.y;

         //update represenation
         square_2 = new Vertex((xDif + square_2.x), (yDif + square_2.y), 0);

         //move model
         moveModel(scene.modelList.get(1), xDif, yDif, 0);
         
      }

      if (sq3Cond)
      {
         //calculate difference
         //new - current
         xDif = Xc - square_3.x;
         yDif = Yc - square_3.y;

         //update represenation
         square_3 = new Vertex((xDif + square_3.x), (yDif + square_3.y), 0);

         //move model
         moveModel(scene.modelList.get(2), xDif, yDif, 0);
         
      }

      if (diamondCond)
      {
         //calculate difference
         //new - current
         xDif = Xc - diamond.x;
         yDif = Yc - diamond.y;

         //update represenation
         diamond = new Vertex((xDif + diamond.x), (yDif + diamond.y), 0);

         //move model
         moveModel(scene.modelList.get(3), xDif, yDif, 0);
         
      }

      if (circleCond)
      {
         //calculate difference
         //new - current
         xDif = Xc - circle.x;
         yDif = Yc - circle.y;

         //update represenation
         circle = new Vertex((xDif + circle.x), (yDif + circle.y), 0);

         //move model
         moveModel(scene.modelList.get(4), xDif, yDif, 0);
         
      }
      
      if(mouseInformation)
      {
         if(sq1Cond)
         {
            System.out.println(scene.getModel(0).name);
         }

         if(sq2Cond)
         {
            System.out.println(scene.getModel(1).name);
         }

         if(sq3Cond)
         {
            System.out.println(scene.getModel(2).name);
         }

         if(diamondCond)
         {
            System.out.println(scene.getModel(3).name);
         }

         if(circleCond)
         {
            System.out.println(scene.getModel(4).name);
         }

         System.out.printf("%.2f" + "  " + "%.2f%n", xDif,yDif);
         
      }
     
      
      // Render again.
      FrameBuffer fb = fbp.getFrameBuffer();
      fb.clearFB();
      Pipeline.render(scene, fb);
      fbp.update();
      
   }

   // Implement the ComponentListener interface.
   @Override public void componentMoved(ComponentEvent e){}
   @Override public void componentHidden(ComponentEvent e){}
   @Override public void componentShown(ComponentEvent e){}
   @Override public void componentResized(ComponentEvent e)
   {
      if(mouseInformation)
      {
         System.out.printf("JFrame [w = %d, h = %d]: " +
         "FrameBufferPanel [w = %d, h = %d].\n",
         jf.getWidth(), jf.getHeight(),
         fbp.getWidth(), fbp.getHeight());
      }
         // Get the new size of the FrameBufferPanel.
         final int w = fbp.getWidth();
         final int h = fbp.getHeight();
   
         // Create a new FrameBuffer that fits the FrameBufferPanel.
         final FrameBuffer fb = new FrameBuffer(w, h);
         fbp.setFrameBuffer(fb);
         fb.clearFB();
         Pipeline.render(scene, fb);
         fbp.update();
         jf.repaint(); 
      
   }


   /**
      Create an instance of this class which has
      the affect of creating the GUI application.
   */
   public static void main(String[] args)
   {
      print_help_message();

      // We need to call the program's constructor in the
      // Java GUI Event Dispatch Thread, otherwise we get a
      // race condition between the constructor (running in
      // the main() thread) and the very first ComponentEvent
      // (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         () -> new Hw3()   // a lambda expression
      );
   }


   private static void moveModel(Model m, double deltaX,
                                          double deltaY,
                                          double deltaZ)
   {
      for (int i = 0; i < m.vertexList.size(); ++i)
      {
         final Vertex v = m.vertexList.get(i);
         m.vertexList.set(i, new Vertex(v.x + deltaX,
                                        v.y + deltaY,
                                        v.z + deltaZ));
      }
   }


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle renderer debugging information on and off.");
      System.out.println("Use the 'i' key to toggle mouse debugging information on and off.");
      System.out.println("Use the 'c' key to toggle line clipping on and off.");
      System.out.println("Use the 'r/R' keys to reset the window's aspect ratio.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
