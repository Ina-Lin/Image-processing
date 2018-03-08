package homework2;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AffineFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();
	JPanel cotrolPanelBackColor = new JPanel();
	JPanel cotrolPanelTranslate = new JPanel();
	JPanel cotrolPanelScale = new JPanel();
	JPanel cotrolPanelShear = new JPanel();
	JPanel cotrolPaneRotate = new JPanel();
	
	ImagePanel imagePanel;
	JButton btnShow;
	JButton btnTranslate;
	JButton btnScale;
	JButton btnShear;
	JButton btnRotate;
	
	JTextField tfRed = new JTextField(5);
	JTextField tfGreen = new JTextField(5);
	JTextField tfBlue = new JTextField(5);
	JTextField tfDeltaX = new JTextField(5);
	JTextField tfDeltaY = new JTextField(5);
	JTextField tfAmpX = new JTextField(5);
	JTextField tfAmpY = new JTextField(5);
	JTextField  tfShearX= new JTextField(5);
	JTextField  tfShearY= new JTextField(5);
	JTextField  tfRotate= new JTextField(5);
	
	JLabel lbRed = new JLabel("BG (R)");
	JLabel lbGreen = new JLabel("BG (G)");
	JLabel lbBlue = new JLabel("BG (B)");
	JLabel lbDeltaX = new JLabel("x-delta");
	JLabel lbDeltaY = new JLabel("y-delta");
	JLabel lbAmpX = new JLabel("x-ratio");
	JLabel lbAmpY = new JLabel("y-artio");
	JLabel lbShearY = new JLabel("x-ratio");
	JLabel lbShearX = new JLabel("y-ratio ");
	JLabel tbRotate = new JLabel("CountClockwise");
	
	final int[][][] data;
	int height;
	int width;
	BufferedImage img = null;

	AffineFrame(){
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		tfRed.setText("0");
		tfGreen.setText("0");
		tfBlue.setText("0");
	    tfDeltaX.setText("0");
	    tfDeltaY.setText("0");
	    tfAmpX.setText("1.0");
	    tfAmpY.setText("1.0");
	    tfShearX.setText("0.0");
	    tfShearY.setText("0.0");
	    tfRotate.setText("0.0");

	    setTitle("Image Processing Homework 2: Affine Transforms");
		
		try {
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
	    		data[y][x][1] =  Util.getG(rgb);
	    		data[y][x][2] = Util.getB(rgb);
	    	}
	    }
		
		btnShow = new JButton("Show");
		btnTranslate = new JButton("Translation");
		btnScale = new JButton("Scaling");
		btnShear = new JButton("Shearing");
		btnRotate = new JButton("Rotating");
	
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(1,7));
		
		cotrolPanelShow.add(btnShow);
		cotrolPanelMain.add(cotrolPanelShow);
		
		cotrolPanelBackColor.add(lbRed);
		cotrolPanelBackColor.add(tfRed);
		cotrolPanelBackColor.add(lbGreen);
		cotrolPanelBackColor.add(tfGreen);
		cotrolPanelBackColor.add(lbBlue);
		cotrolPanelBackColor.add(tfBlue);
		cotrolPanelMain.add(cotrolPanelBackColor);
		
		cotrolPanelTranslate.add(lbDeltaX);
		cotrolPanelTranslate.add(tfDeltaX);
		cotrolPanelTranslate.add(lbDeltaY);
		cotrolPanelTranslate.add(tfDeltaY);
		cotrolPanelTranslate.add(btnTranslate);
		cotrolPanelMain.add(cotrolPanelTranslate);
		
		cotrolPanelScale.add(lbAmpX);
		cotrolPanelScale.add(tfAmpX);
		cotrolPanelScale.add(lbAmpY);
		cotrolPanelScale.add(tfAmpY);
		cotrolPanelScale.add(btnScale);
		cotrolPanelMain.add(cotrolPanelScale);
		
		cotrolPanelShear.add(lbShearY);
		cotrolPanelShear.add(tfShearY);
		cotrolPanelShear.add(lbShearX);
		cotrolPanelShear.add(tfShearX);
		cotrolPanelShear.add(btnShear);
		cotrolPanelMain.add(cotrolPanelShear);
		
		cotrolPaneRotate.add(tbRotate);
		cotrolPaneRotate.add(tfRotate);
		cotrolPaneRotate.add(btnRotate);
		cotrolPanelMain.add(cotrolPaneRotate);
		
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		 
		cotrolPanelMain.setBounds(0, 0,1200,150);
		getContentPane().add(cotrolPanelMain);
	    imagePanel = new ImagePanel();
	    imagePanel.setBounds(0,180, 1500,1500);
	    getContentPane().add(imagePanel);
	    
	    btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, data);
			}});
	    
		btnTranslate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int w = Integer.parseInt(tfDeltaX.getText());
				int h = Integer.parseInt(tfDeltaY.getText());
				int[][][] newData = new int [height+h][width+w][3];
				for (int y=h; y<height+h; y++){
			    	for (int x=w; x<width+w; x++){
			    		int value = img.getRGB(x-w, y-h);
			    		newData[y][x][0] = Util.getR(value);
			    		newData[y][x][1] = Util.getG(value);
			    		newData[y][x][2] = Util.getB(value);
			    	}
			    }
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData,w,h);
			}});
		
		btnScale.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				double w = Double.parseDouble(tfAmpX.getText());
				double h = Double.parseDouble(tfAmpY.getText());
				int[][][] newData = new int [(int) (height*h)][(int)(width*w)][3];
				for(int y=0;y<newData.length;y++){
					for(int x=0;x<newData[0].length;x++){
						double ky = y/h ;
						double kx = x/w ;
						for(int k=0;k<3;k++){
							newData[y][x][k] = 
							Util.bilinear(data[Util.adjust(height,(int) Math.floor(ky))][Util.adjust(width,(int) Math.floor(kx))][k],
							data[Util.adjust(height,(int) Math.floor(ky))][Util.adjust(width,(int) Math.ceil(kx))][k],
							data[Util.adjust(height,(int) Math.ceil(ky))][Util.adjust(width,(int) Math.floor(kx))][k],
							data[Util.adjust(height,(int) Math.ceil(ky))][Util.adjust(width,(int) Math.ceil(kx))][k],ky,kx);		
						}
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}});
		
		btnShear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				double tX = Double.parseDouble(tfShearX.getText());
				double tY = Double.parseDouble(tfShearY.getText());
				int w = (int)(width+(height*tX));
				int h = (int) (height+(width*tY));
				int R = Integer.parseInt(tfRed.getText());
				int G = Integer.parseInt(tfGreen.getText());
				int B = Integer.parseInt(tfBlue.getText());
				int[][][] newData = new int [h][w][3];
				
				for (int y=0; y<h; y++){
			    	for (int x=0; x<w; x++){
			    		newData[y][x][0] = Util.checkPixelBounds(R);
						newData[y][x][1] = Util.checkPixelBounds(G);
						newData[y][x][2] = Util.checkPixelBounds(B);
			    	}
				}
				
				for (int y=0; y<height; y++){
			    	for (int x=0; x<width; x++){
			    		int value = img.getRGB(x, y);
			    		newData[(int) (y+(x*tY))][(int) (x+(y*tX))][0] = Util.getR(value);
			    		newData[(int) (y+(x*tY))][(int) (x+(y*tX))][1] = Util.getG(value);
			    		newData[(int) (y+(x*tY))][(int) (x+(y*tX))][2] = Util.getB(value);
			    	}
			    }
				
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}});
		
		 btnRotate.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) { 
					double r = Double.parseDouble(tfRotate.getText());
					int height = data.length ;
					int width = data[0].length ;		
					double sin_deg = Math.sin(r / 180 * Math.PI);
					double cos_deg = Math.cos(r / 180 * Math.PI);			
					int newHeight = Math.abs((int)(sin_deg*width + cos_deg*height));
					int newWidth = Math.abs((int) (cos_deg*width + sin_deg*height));		
					int ox = (int) Math.round((-newWidth/2 * cos_deg - newHeight/2 * sin_deg + width/2));
				    int oy =  (int) Math.round((newWidth/2 * sin_deg - newHeight/2 *  cos_deg +  height/2));	
				    int R = Integer.parseInt(tfRed.getText());
				    int G = Integer.parseInt(tfGreen.getText());
				    int B = Integer.parseInt(tfBlue.getText());
				    int[][][] newData = new int [newHeight][newWidth][3];
					
					for(int y=0;y<newHeight;y++){
						for(int x=0;x<newWidth;x++){
							int ky =(int) Math.round((cos_deg*y - sin_deg*x + oy ));
							int kx =(int) Math.round((cos_deg*x + sin_deg*y + ox ));		
							if(ky>=0 && ky<height && kx>=0 && kx<width)
								for(int k=0;k<3;k++){						
									newData[y][x][k] = data[ky][kx][k];
								}
							else{
								newData[y][x][0] = Util.checkPixelBounds(R);
								newData[y][x][1] = Util.checkPixelBounds(G);
								newData[y][x][2] = Util.checkPixelBounds(B);
							}
						}		
					}
				
					Graphics g = imagePanel.getGraphics();
					imagePanel.paintComponent(g,newData);	
				}});
			}
	}
