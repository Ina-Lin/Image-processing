package homework1;
import java.awt.BorderLayout;
import java.awt.Graphics;
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
public class RGBFrame extends JFrame {
	JPanel controlPanel; 
	ImagePanel imagePanel;
	JButton btnShow;
	JButton btnR;
	JButton btnG;
	JButton btnB;
	JButton btnWhite;
	JButton btnGray;
	JButton btnReverse;
	JButton btnOpposite;
	JButton btnRight;
	JButton btnLeft;
	JButton btnRotate;
	
	final int[][][] data;
	int height;
	int width;
	BufferedImage img = null;
	
	RGBFrame(){
		setTitle("影像處理: Get R,G, and B Channels");
		
		try {
		    //img = ImageIO.read(new File("file/plate.png"));
			//img = ImageIO.read(new File("file/Munich.png"));
			img = ImageIO.read(new File("/Users/apple/Documents/workspace/ImageProcessing/src/F16.png"));
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
		
		btnShow = new JButton("顯示");
		btnR = new JButton("Get R");
		btnG = new JButton("Get G");
		btnB = new JButton ("Get B");
		btnWhite = new JButton("反白");
		btnGray = new JButton("灰階");
		btnReverse = new JButton("上下顛倒");
		btnOpposite = new JButton("左右相反");
		btnRight = new JButton("右旋90度");
		btnLeft = new JButton("左旋90度");
		btnRotate = new JButton("旋轉180度");
		
		controlPanel = new JPanel();
		controlPanel.add(btnShow);
		controlPanel.add(btnR);
		controlPanel.add(btnG);
		controlPanel.add(btnB);
		controlPanel.add(btnWhite);
		controlPanel.add(btnGray);
		controlPanel.add(btnReverse);
		controlPanel.add(btnOpposite);
		controlPanel.add(btnRight);
		controlPanel.add(btnLeft);
		controlPanel.add(btnRotate);
		setLayout(new BorderLayout());	 
		
	    add(controlPanel, BorderLayout.PAGE_START);
	   
	    imagePanel = new ImagePanel();
	    add(imagePanel);

	    btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, data);
			}});

	    btnR.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [height][width][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, y);
			    			newData[y][x][0] = Util.getR(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });

	    btnG.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [height][width][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, y);
			    			newData[y][x][1] = Util.getG(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
	    
	    btnB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [height][width][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, y);
			    			newData[y][x][2] = Util.getB(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
	    
	    btnWhite.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [height][width][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, y);
			    			newData[y][x][0] = 255 - Util.getR(value);
			    			newData[y][x][1] = 255 - Util.getG(value);
			    			newData[y][x][2] = 255 - Util.getB(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
	    
		btnGray.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [height][width][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, y);
			    			newData[y][x][0] = (int) (0.299 * Util.getR(value)) + (int) (0.587 * Util.getG(value)) + (int) (0.114 * Util.getB(value));
			    			newData[y][x][1] = newData[y][x][0];
			    			newData[y][x][2] = newData[y][x][0];
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
		
		btnReverse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [height][width][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, height-y-1);
			    			newData[y][x][0] = Util.getR(value);
			    			newData[y][x][1] = Util.getG(value);
			    			newData[y][x][2] = Util.getB(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
		
		btnOpposite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [height][width][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(width-x-1, y);
			    			newData[y][x][0] = Util.getR(value);
			    			newData[y][x][1] = Util.getG(value);
			    			newData[y][x][2] = Util.getB(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
		
		btnRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [width][height][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, y);
			    			newData[x][y][0] = Util.getR(value);
			    			newData[x][y][1] = Util.getG(value);
			    			newData[x][y][2] = Util.getB(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
		});
		
		btnLeft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [width][height][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, y);
			    			newData[width-x-1][y][0] = Util.getR(value);
			    			newData[width-x-1][y][1] = Util.getG(value);
			    			newData[width-x-1][y][2] = Util.getB(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
		
		btnRotate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][][] newData = new int [height][width][3];
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		    int value = img.getRGB(x, y);
			    			newData[height-y-1][width-x-1][0] = Util.getR(value);
			    			newData[height-y-1][width-x-1][1] = Util.getG(value);
			    			newData[height-y-1][width-x-1][2] = Util.getB(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
	}

}
