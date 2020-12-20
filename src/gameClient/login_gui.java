package gameClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class login_gui implements ActionListener {


	private static JLabel idlabel;
	private static JTextField idText;
	private static JLabel levelLabel;
	private static JTextField levelText;
	private static JButton button;


	JFrame frame= new JFrame();
	JPanel panel= new JPanel();
	private Thread playerThread;

	/**
	 * build the menu
	 * login : enter the id , level.
	 */
	public void chose() {

		frame.setSize(350,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setLocationRelativeTo(null); // make thr GUI shows in the middle

		panel.setLayout(null);
		idlabel = new JLabel("Enter your id");
		idlabel.setBounds(10, 20, 80, 25);
		panel.add(idlabel);

		idText = new JTextField(20);
		idText.setBounds(100, 20, 165, 25);
		panel.add(idText);

		levelLabel = new JLabel("level:");
		levelLabel.setBounds(10, 50, 80, 25);
		panel.add(levelLabel);

		levelText = new JTextField(20);
		levelText.setBounds(100, 50, 165, 25);
		panel.add(levelText);

		button = new JButton("login");
		button.setBounds(10, 80, 80, 20);
		button.addActionListener(new login_gui());
		panel.add(button);
		button.addActionListener(this);  

		frame.setVisible(true);
		music player = new music("Pokemon.mp3");
		Thread playerThread = new Thread(player);
		playerThread.start();

	}
	/**
	 * Exit as soon as you stop using.
	 */
	public void exit() {
		frame.setVisible(false); //you can't see me!
		frame.dispose(); //Destroy the JFrame object
	}
	/**
	 * Exercise the action
	 * param e 
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		int id = Integer.parseInt(idText.getText());
		int senrio = Integer.parseInt(levelText.getText());
		if(idText.getText().length()==9) {
			playerThread.stop();
			Ex2 start=new Ex2(id,senrio);
			
			Thread client = new Thread(start);
			
			client.start();
			
			exit();
		}
	}

public static void main(String[] args) {

}
}


