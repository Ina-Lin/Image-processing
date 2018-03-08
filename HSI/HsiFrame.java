package homework4;

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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class HsiFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelHue = new JPanel();
	JPanel cotrolPanelSat = new JPanel();
	JPanel cotrolPanelInt = new JPanel();
	JPanel cotrolPanelBin = new JPanel();
	JPanel cotrolPanelHSI = new JPanel();

	ImagePanel imagePanel;
	JButton btnShow;
	JButton btnHue = new JButton("H");
	JButton btnSat = new JButton("S");
	JButton btnInt = new JButton("I");
	JButton btnHSI = new JButton("HSI Conversion");
	JButton btnBin = new JButton("Binary");

	JSlider sliderHue;
	JSlider sliderSat;
	JSlider sliderInt;
	JSlider sliderBin;

	JLabel lbHueBeging = new JLabel("-180");
	JLabel lbHueEnd = new JLabel("180");
	JLabel lbSatBeging = new JLabel("-100(%)");
	JLabel lbSatEnd = new JLabel("100(%)");;
	JLabel lbIntBeging = new JLabel("-100(%)");;
	JLabel lbIntEnd = new JLabel("100(%)");;
	JLabel lbBinBeging = new JLabel("0");;
	JLabel lbBinEnd = new JLabel("255");;

	JTextField tfHueValue = new JTextField(3);
	JTextField tfSatValue = new JTextField(3);
	JTextField tfIntValue = new JTextField(3);
	JTextField tfBinValue = new JTextField(3);

	final int[][][] data;
	int height;
	int width;
	BufferedImage img = null;

	HsiFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Hw 4: HSI and Binary Imaging");

		try {
			img = ImageIO.read(new File("/Users/apple/Documents/workspace/ImageProcessing/src/F16.png"));
		} catch (IOException e) {
			System.out.println("IO exception");
		}

		height = img.getHeight();
		width = img.getWidth();
		data = new int[height][width][3];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				data[y][x][0] = Util.getR(rgb);
				data[y][x][1] = Util.getG(rgb);
				data[y][x][2] = Util.getB(rgb);
			}
		}
		
		btnShow = new JButton("Show Original");
		tfHueValue.setText("0");
		tfSatValue.setText("0");
		tfIntValue.setText("0");
		tfBinValue.setText("127");
		
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		
		sliderHue = new JSlider(-180, 180, 0);
		
		cotrolPanelShow.add(btnShow);
		cotrolPanelHue.add(lbHueBeging);
		cotrolPanelHue.add(sliderHue);
		cotrolPanelHue.add(lbHueEnd);
		cotrolPanelHue.add(tfHueValue);
		cotrolPanelHue.add(btnHue);
		
		sliderSat = new JSlider(-100, 100, 0);
		
		cotrolPanelSat.add(lbSatBeging);
		cotrolPanelSat.add(sliderSat);
		cotrolPanelSat.add(lbSatEnd);
		cotrolPanelSat.add(tfSatValue);
		cotrolPanelSat.add(btnSat);
		
		sliderInt = new JSlider(-100, 100, 0);
		
		cotrolPanelInt.add(lbIntBeging);
		cotrolPanelInt.add(sliderInt);
		cotrolPanelInt.add(lbIntEnd);
		cotrolPanelInt.add(tfIntValue);
		cotrolPanelInt.add(btnInt);
		cotrolPanelHSI.add(btnHSI);
		
		sliderBin = new JSlider(0, 255, 127);
		
		cotrolPanelBin.add(lbBinBeging);
		cotrolPanelBin.add(sliderBin);
		cotrolPanelBin.add(lbBinEnd);
		cotrolPanelBin.add(tfBinValue);
		cotrolPanelBin.add(btnBin);
		
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.add(cotrolPanelHue);
		cotrolPanelMain.add(cotrolPanelSat);
		cotrolPanelMain.add(cotrolPanelInt);
		cotrolPanelMain.add(cotrolPanelHSI);
		cotrolPanelMain.add(cotrolPanelBin);
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(0, 220, 1500, 1500);
		getContentPane().add(imagePanel);
		
		btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, data);
			}
		});
		
		btnHue.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[][][] newdata = new int[height][width][3];
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double r = (double)data[y][x][0]/255.0;
						double g = (double)data[y][x][1]/255.0;
						double b = (double)data[y][x][2]/255.0;
						double H = Util.getH(r,g,b) + Double.parseDouble(tfHueValue.getText());
						double S = Util.getS(r,g,b);
						double I = Util.getI(r,g,b);
						if(H > 360){
							H -= 360;
						}
						else if(H < 0){
							H += 360;
						}
						newdata[y][x][0] = Util.checkPixelBounds((int)(Util.R(H,S,I)*255));
						newdata[y][x][1] = Util.checkPixelBounds((int)(Util.G(H,S,I)*255));
						newdata[y][x][2] = Util.checkPixelBounds((int)(Util.B(H,S,I)*255));
					}		
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newdata);
			}
		});
		
		btnSat.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[][][] newdata = new int[height][width][3];
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double r = (double)data[y][x][0]/255.0;
						double g = (double)data[y][x][1]/255.0;
						double b = (double)data[y][x][2]/255.0;
						double H = Util.getH(r,g,b);
						double S = Double.parseDouble(tfSatValue.getText()) / 100.0;
						double I = Util.getI(r,g,b);
						if(S < 0){
							S = 0;
						}
						newdata[y][x][0] = Util.checkPixelBounds((int)(Util.R(H,S,I)*255));
						newdata[y][x][1] = Util.checkPixelBounds((int)(Util.G(H,S,I)*255));
						newdata[y][x][2] = Util.checkPixelBounds((int)(Util.B(H,S,I)*255));
					}		
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newdata);
			}
		});
		
		btnInt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[][][] newdata = new int[height][width][3];
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double r = (double)data[y][x][0]/255.0;
						double g = (double)data[y][x][1]/255.0;
						double b = (double)data[y][x][2]/255.0;
						double H = Util.getH(r,g,b);
						double S = Util.getS(r,g,b);
						double I = Double.parseDouble(tfIntValue.getText()) / 100.0;
						if(I < 0){
							I = 0;
						}
						newdata[y][x][0] = Util.checkPixelBounds((int)(Util.R(H,S,I)*255));
						newdata[y][x][1] = Util.checkPixelBounds((int)(Util.G(H,S,I)*255));
						newdata[y][x][2] = Util.checkPixelBounds((int)(Util.B(H,S,I)*255));
					}		
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newdata);			
			}
		});
		
		btnHSI.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[][][] newdata = new int[height][width][3];
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double r = (double)data[y][x][0]/255.0;
						double g = (double)data[y][x][1]/255.0;
						double b = (double)data[y][x][2]/255.0;
						double H = Util.getH(r,g,b) + Double.parseDouble(tfHueValue.getText());
						double S = Double.parseDouble(tfSatValue.getText()) / 100.0;
						double I = Double.parseDouble(tfIntValue.getText()) / 100.0;
						
						if(H > 360){
							H -= 360;
						}
						else if(H < 0){
							H += 360;
						}if(S < 0){
							S = 0;
						}
						if(I < 0){
							I = 0;
						}
						newdata[y][x][0] = Util.checkPixelBounds((int)(Util.R(H,S,I)*255));
						newdata[y][x][1] = Util.checkPixelBounds((int)(Util.G(H,S,I)*255));
						newdata[y][x][2] = Util.checkPixelBounds((int)(Util.B(H,S,I)*255));
					}		
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newdata);			
			}
		});
		
		btnBin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[][][] newdata = new int[height][width][3];
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double gray = (0.299 * data[y][x][0]) + (0.587 * data[y][x][1]) + (0.114 * data[y][x][2]);
						int bin = Integer.parseInt(tfBinValue.getText());
						if(gray >= bin){
							newdata[y][x][0] = Util.checkPixelBounds(255);
							newdata[y][x][1] = Util.checkPixelBounds(255);
							newdata[y][x][2] = Util.checkPixelBounds(255);
						}
						else{
							newdata[y][x][0] = Util.checkPixelBounds(0);
							newdata[y][x][1] = Util.checkPixelBounds(0);
							newdata[y][x][2] = Util.checkPixelBounds(0);
						}
				
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newdata);
			}
		});
	
		
		sliderHue.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				tfHueValue.setText(String.valueOf(sliderHue.getValue()));
			}
		});
		
		sliderSat.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				tfSatValue.setText(String.valueOf(sliderSat.getValue()));
			}
		});
		
		sliderInt.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				tfIntValue.setText(String.valueOf(sliderInt.getValue()));
			}
		});
		
		sliderBin.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				tfBinValue.setText(String.valueOf(sliderBin.getValue()));
			}
		});
	}

}
