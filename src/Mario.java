import java.awt.*;
import javax.swing.*;

public class Mario extends JFrame {
	
	//Constructor, it sets up window on the screen
	public Mario() {
		
		//Sticks game to this window
		add(new Board());
		
		//Sets size of the frame to be equal user's screen
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		//Title for the game
		setTitle("Super Student Bros");
		
		//Stops the game after exiting the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Sets frame to be centered on the screen
		setLocationRelativeTo(null);
		
		//Image Icon
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("SmallStudentIcon.png"));;
		this.setIconImage(imageIcon.getImage());
		
	}
	
	//Start the game
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			Mario ex = new Mario();
			ex.setVisible(true);
		});
	}
}