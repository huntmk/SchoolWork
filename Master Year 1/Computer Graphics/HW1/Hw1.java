/*
      Course: CS 45500 / CS 51580
      Name: Marcellus Hunt
      Email: mkhunt@pnw.edu
      Assignment: 1


*/

import framebuffer.FrameBuffer;
import java.awt.Color;

/**


*/
public class Hw1
{
   public static void main(String[] args)
   {
     // Check for a file name on the command line.
     
      if ( 0 == args.length )
      {
         System.err.println("Usage: java Hw1 <PPM-file-name>");
         System.exit(-1);
      }
      
      
      // This framebuffer holds the image that will be embedded
      // within two viewports of our larger framebuffer.
      FrameBuffer fbEmbedded = new FrameBuffer(args[0] );

      /******************************************/

      // Your code goes here.
      // Create a framebuffer. Fill it with the checkerboard pattern.
	  final int w = 1000;
	  final int h = 600;
	  FrameBuffer fb = new FrameBuffer(w, h);
	  //fill with Yellow(255,189,96), fill with Red(192,56,14); XY: every 100x100 pixels
	  Color yellow = new Color(255,189,96);
	  Color red = new Color (192,56,14);
	  
	  for (int i = 0; i < h; i++) {
		  for(int j = 0; j < w; j++) {
            if( (i/100)%2 ==0 && (j/100)%2==0)
               fb.setPixelFB(j,i,yellow);
            else if((i/100)%2 !=0 && (j/100)%2!=0)
               fb.setPixelFB(j, i, yellow);
            else
               fb.setPixelFB(j, i, red);
		  }
	  }
	  	
      // Create a viewport and fill it with a flipped copy of the PPM file.
      //flip vertically
      FrameBuffer.Viewport rebel = fb.new Viewport(75,125,fbEmbedded);

      for (int i = 0; i < fbEmbedded.height ; i++)
         for (int j = 0; j < fbEmbedded.width; j++)
            rebel.setPixelVP(j, fbEmbedded.height-i, fbEmbedded.getPixelFB(j, i) );
      
      // Create another viewport and fill it with another flipped copy of the PPM file.
      //flip horizontally
      FrameBuffer.Viewport secRebel = fb.new Viewport (330,125,fbEmbedded);

      for (int i = 0; i < fbEmbedded.height ; i++)
         for (int j = 0; j < fbEmbedded.width; j++)
            secRebel.setPixelVP(fbEmbedded.width-j, i, fbEmbedded.getPixelFB(j, i) );
      
      // Create another viewport and fill it with the striped pattern.
      FrameBuffer.Viewport striped = fb.new Viewport(580,420,420,120);
      
      Color [] stripes = {new Color(241,95,116),new Color(152,203,74), new Color(84,129,230) };

      int col = 299;
      int row = 120;

      int shift = 0;
      
      for (int i= 0; i <= row; i++)
      {
         for (int j= 0; j<= col; j++)
         {
            striped.setPixelVP(j + shift, i,stripes[(j/30)%3] );

         }
         shift++;
         
      }

      // Create another viewport that covers the selected region of the framebuffer.
         //what's the selected region - where the portion is pasted or the area it's being put
      
      Color gray = new Color(192,192,192);
      FrameBuffer.Viewport area = fb.new Viewport(725,25,250,350,gray);
      area.clearVP();

      FrameBuffer.Viewport selectedRegion = fb.new Viewport(500,200,200,300);

      // Create another viewport to hold a copy of the selected region.

      FrameBuffer.Viewport copyRegion = fb.new Viewport (750,50,selectedRegion);
      
      // Give this viewport a grayish background color.      
	   
      // Create another viewport inside the last one.
      // Copy the selected region's viewport into this last viewport.
      // Draw the disk.
      Color purple = new Color(180,100,255);
      int x = 300;
      int y = 450;
      int r = 50;

      //used for distance formula
      int dist;
      
      for (int i= x-r; i <= x+r;i++) 
         
         for (int j = y-r; j <= y+r; j++)
         {
            //if the distance from center(x,y) to (i,j) is less than radius, change color
            dist = ((j-y)*(j-y))+((i-x)*(i-x));
            if (dist <=(r*r))
               fb.setPixelFB(i,j,purple);
         }
        
      //FrameBuffer fb = null;


      /******************************************/
      // Save the resulting image in a file.
      String savedFileName = "Hw1.ppm";
      fb.dumpFB2File( savedFileName );
      System.err.println("Saved " + savedFileName);
   }
}
