package homework7;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;



@SuppressWarnings("serial")
public class NoiseFrame  extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	
	ImagePanel imagePanel;
	ImagePanel imagePanel2;
	ImagePanel imagePanel3;
	
	
	JButton btnShow = new JButton("Show Original");
	JButton btnNoise = new JButton("Add Noise");
	JButton btnMedianGray = new JButton("Remove Noise");
	JSlider slider;
	JLabel lbLess = new JLabel("Less Noise");
	JLabel lbMore = new JLabel("More Noise");
	
	int[][][] data;
	int[][][] noisedData;
	int[][][] noiseRemovedData;
	int height;
	int width;
	BufferedImage img = null;
	int thresh;
	
	 NoiseFrame (){
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
	    setTitle("HW 7: Adding and Removing Noise with a Median Filter");
		
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

		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6,1));
		
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(btnNoise);
		cotrolPanelShow.add(btnMedianGray);
		cotrolPanelShow.add(lbLess);
		
		slider = new JSlider(0, 200,100);
		thresh = slider.getValue();
		
		cotrolPanelShow.add(slider);
		cotrolPanelShow.add(lbMore);
		
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0,1200,200);
		
		getContentPane().add(cotrolPanelMain);
	    imagePanel = new ImagePanel();
	    imagePanel.setBounds(20,50, 620,450);
	    getContentPane().add(imagePanel);
	    imagePanel2 = new ImagePanel();
	    imagePanel2.setBounds(630,50, 1230,450);
	    getContentPane().add(imagePanel2);
	    imagePanel3 = new ImagePanel();
	    imagePanel3.setBounds(630,470, 1200,1050);
	    getContentPane().add(imagePanel3);
	
	    btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, data);
			}
		});
	    
	    btnNoise.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				double percent = ((Math.random()*10+1));
				int point = (int) (height*width*(percent/100));
				int[][][] newData = new int[height][width][3];
				
				for (int i=0;i<height;i++){
					for(int j=0;j<width ;j++){
						for(int k=0;k<3;k++){
							newData[i][j][k] = data[i][j][k];
						}	
					}
				}
				
				for(int i=0;i<point;i++){
					int randh = (int) (Math.random()*height);
					int randw = (int) (Math.random()*width);
					for(int j=0;j<3;j++){
						newData[randh][randw][j] = 255;
					}
				}	
				
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
	    });
	    
	    btnMedianGray.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int[][] newData = new int[height][width];
				int[][][] returnData = new int[height][width][3];
				
				for (int i=0;i<height;i++){
					for(int j=0;j<width ;j++){
						for(int k=0;k<3;k++){
							returnData[i][j][k] = data[i][j][k];
						}	
					}
				}
				
				for (int i=0;i<height;i++){
					for(int j=0;j<width ;j++){
						newData[i][j] = (int) (( data[i][j][0] + data[i][j][1] + data[i][j][2] )/ 3.0);
					}
				}
				for (int i=1;i<height-1;i++){
					for(int j=1;j<width-1 ;j++){
						Map<Integer,int[]> map = new HashMap<Integer,int[]>();
						int[] arr = new int[9];
						int med;
						arr[0] = newData[i-1][j-1];
						map.put(arr[0], new int[]{i-1,j-1});
						arr[1] = newData[i-1][j];
						map.put(arr[1], new int[]{i-1,j});
						arr[2] = newData[i-1][j+1];
						map.put(arr[2], new int[]{i-1,j+1});
						arr[3] = newData[i][j-1];
						map.put(arr[3], new int[]{i,j-1});
						arr[4] = newData[i][j];
						map.put(arr[4], new int[]{i,j});
						arr[5] = newData[i][j+1];
						map.put(arr[5], new int[]{i,j+1});
						arr[6] = newData[i+1][j-1];
						map.put(arr[6], new int[]{i+1,j-1});
						arr[7] = newData[i+1][j];
						map.put(arr[7], new int[]{i+1,j});
						arr[8] = newData[i+1][j+1];
						map.put(arr[8], new int[]{i+1,j+1});
						Arrays.sort(arr);
						med = arr[4];
						if (Math.abs(newData[i][j] - med) > slider.getValue()){
							for(int k=0;k<3;k++){
								int x = map.get(med)[0];
								int y = map.get(med)[1];
								returnData[i][j][k] = data[x][y][k];
							}
						}
					}
				}
				Graphics g = imagePanel.getGraphics();
 				imagePanel.paintComponent(g, returnData);
			}
	    });
	 }
}



