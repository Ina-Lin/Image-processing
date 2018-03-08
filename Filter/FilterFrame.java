package homework6;

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
public class FilterFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelLP = new JPanel();
	JPanel cotrolPanelHP = new JPanel();
	ImagePanel imagePanel;
	ImagePanel imagePanel2;
	JButton btnShow;
	JButton btnLP = new JButton("Blur");
	JButton btnHP = new JButton("Sharp");
	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;

	FilterFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("HW 6: Image Filterings");
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
		
		btnShow = new JButton("Show Original");
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(btnLP);
		cotrolPanelShow.add(btnHP);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(20, 220, 720, 620);
		getContentPane().add(imagePanel);
		imagePanel2 = new ImagePanel();
		imagePanel2.setBounds(650, 220, 1500, 1500);
		getContentPane().add(imagePanel2);
		
		btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, data);
			}
		});
		
		btnLP.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[][][] newdata = new int[height][width][3];
				int[][][] result = new int[height][width][3];
				for(int y=0;y<height;y++){
					for(int x=0;x<width;x++){
						int gray = (data[y][x][0] + data[y][x][1] + data[y][x][2] )/ 3;
						for(int i=0;i<3;i++){
							newdata[y][x][i] = gray;
							result[y][x][i] = gray;
						}
					}
				}

				for(int y=1;y<height-1;y++){
					for(int x=1;x<width-1;x++){
						int sum = 0;
						sum += newdata[y-1][x-1][0];
						sum += newdata[y-1][x][0];
						sum += newdata[y-1][x+1][0];
						sum += newdata[y][x-1][0];
						sum += newdata[y][x][0];
						sum += newdata[y][x+1][0];
						sum += newdata[y+1][x-1][0];
						sum += newdata[y+1][x][0];
						sum += newdata[y+1][x+1][0];
						newdata[y][x][0] = Util.checkPixelBounds(sum/9);
						newdata[y][x][1] = Util.checkPixelBounds(sum/9);
						newdata[y][x][2] = Util.checkPixelBounds(sum/9);
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newdata);
			}
		});

		btnHP.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[][][] newdata = new int[height][width][3];
				int[][][] result = new int[height][width][3];
				for(int y=0;y<height;y++){
					for(int x=0;x<width;x++){
						int gray = (data[y][x][0] + data[y][x][1] + data[y][x][2] )/ 3;
						for(int i=0;i<3;i++){
							newdata[y][x][i] = gray;
							result[y][x][i] = gray;
						}
					}
				}
				for(int y=1;y<height-1;y++){
					for(int x=1;x<width-1;x++){
						int sum = 0;
						sum -= newdata[y-1][x-1][0]/9;
						sum -= newdata[y-1][x][0]/9;
						sum -= newdata[y-1][x+1][0]/9;
						sum -= newdata[y][x-1][0]/9;
						sum += 8*newdata[y][x][0]/9;
						sum -= newdata[y][x+1][0]/9;
						sum -= newdata[y+1][x-1][0]/9;
						sum -= newdata[y+1][x][0]/9;
						sum -= newdata[y+1][x+1][0]/9;
						result[y][x][0] = Util.checkPixelBounds(sum);
						result[y][x][1] = Util.checkPixelBounds(sum);
						result[y][x][2] = Util.checkPixelBounds(sum);
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newdata);
			}
			
		});
	}
}
