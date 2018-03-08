package homework6;
import javax.swing.JFrame;


public class ImageGui {

	public static void main(String[] args) {
		JFrame frame;
		frame = new FilterFrame();
		frame.setSize(1500, 1500);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
