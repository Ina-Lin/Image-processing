package homework5;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StretchFrame  extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	
	ImagePanel imagePanel;
	ImagePanel imagePanel2;
	
	JButton btnShow = new JButton("Show Original)");
	JButton btnStretch1 = new JButton("Stretch 1 (min-max)");
	JButton btnStretch2 = new JButton("Stretch 2 (histogram)");
	
	int[][][] data;
	int[][][] newData;
	int height;
	int width;
	BufferedImage img = null;
	
	 StretchFrame (){
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
	    setTitle("HW 5: Stretching");
		
		try {
			
			img = ImageIO.read(new File("/Users/apple/Documents/workspace/ImageProcessing/src/F16.png"));
			//img = ImageIO.read(new File("file/Munich_gray_dark_white_noised.png"));
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		
		height = img.getHeight();
		width = img.getWidth();
		data = new int[height][width][3]; 
		
		for (int y=0; y<height; y++){
	    	for (int x=0; x<width; x++){
	    		int rgb = img.getRGB(x, y);
	    		data[y][x][0] = Util.getR(rgb);
	    		data[y][x][1] = Util.getG(rgb);
	    		data[y][x][2] = Util.getB(rgb);
	    	}
	    }
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6,1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(btnStretch1);
		cotrolPanelShow.add(btnStretch2);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0,1200,200);
		getContentPane().add(cotrolPanelMain);
	    imagePanel = new ImagePanel();
	    imagePanel.setBounds(20,220, 720,620);
	    getContentPane().add(imagePanel);
	    imagePanel2 = new ImagePanel();
	    imagePanel2.setBounds(650,220, 1500,1500);
	    getContentPane().add(imagePanel2);

	    
	    btnShow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, data);
			}});
	    
	    btnStretch1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int height = data.length;
				int width = data[0].length;
				int[][][] newData = new int[height][width][3];
				int[][] cdf = new int[height][width];
				int min=255 ,max=0;
				
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						cdf[i][j] = (int) ((data[i][j][0] + data[i][j][1] + data[i][j][2]) / 3.0);		
						if(cdf[i][j] > max) max = cdf[i][j];
						if(cdf[i][j] < min) min = cdf[i][j];
					}
				}
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						if(cdf[i][j] < min) cdf[i][j] = 0;
						else if(cdf[i][j] > max) cdf[i][j] = 255;
						else cdf[i][j] = Util.checkPixelBounds((int) ((cdf[i][j]-min)/(max-min+0.5) *255));
					}
				}
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						for(int k=0;k<3;k++)
							newData[i][j][k] = cdf[i][j];
					}
				}
				
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}});
	   
	    btnStretch2.addActionListener(new ActionListener(){
	 			@Override
	 			public void actionPerformed(ActionEvent arg0) {
	 				
	 				int height = data.length;
	 				int width = data[0].length;
	 				int area = height*width;
	 				int[][][] newData = new int[height][width][3];
	 				int[][] grayPixel = new int[height][width];
	 				double[] grayRatio = new double[256];
	 				double[] cdf = new double[256];
	 				
	 				for(int i=0;i<height;i++){
	 					for(int j=0;j<width;j++){
	 						int grayValue = (int) ((data[i][j][0] + data[i][j][1] + data[i][j][2]) / 3.0);	
	 						grayPixel[i][j] = grayValue;
	 						grayRatio[grayValue] += 1.0/(area+0.5);
	 		 			}
	 				}
	 				cdf[0] = grayRatio[0];		
	 				for(int i=1;i<grayRatio.length;i++)
	 					cdf[i] = cdf[i-1] + grayRatio[i];
	 				
	 				for(int i=0;i<height;i++){
	 					for(int j=0;j<width;j++){
	 						for(int k=0;k<3;k++)
	 							newData[i][j][k] =Util.checkPixelBounds((int) (cdf[grayPixel[i][j]]*255));
	 					}
	 				}
	 				
	 				Graphics g = imagePanel.getGraphics();
	 				imagePanel.paintComponent(g, newData);
	 			}});
	 }
}
	 