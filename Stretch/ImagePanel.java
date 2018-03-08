package homework5;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	 public void paintComponent(Graphics g, int data [][][]){
         
		 super.paintComponent(g);         
         for (int y=0; y<data.length; y++){
		    	for (int x=0; x< data[y].length; x++){
		    		int r = data[y][x][0];
		    		int green =  data[y][x][1];
		            int b =  data[y][x][2];
		            g.setColor(new Color(r, green, b));
		    	    g.drawLine(x, y, x, y);		
		    	} 
		  	}
	 }
}
