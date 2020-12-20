package gameClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//In this class we created the user login by id and select the game stage

public class login_gui extends JFrame implements ActionListener {

	private static JLabel idlabel;
	private static JTextField idText;
	private static JLabel levelLabel;
	private static JTextField levelText;
	private static JButton button;
	public boolean flag=false;
	public  int id=0;
	public  int senrio=0;

	/**
	 * build the menu
	 * login : enter the id , level.
	 */
	public void chose() {


		JPanel panel= new JPanel();
		this.setSize(350,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.setLocationRelativeTo(null); // make thr GUI shows in the middle

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

		this.setVisible(true);

		//playerThread.start();

	}
	/**
	 * Exit as soon as you stop using.
	 */
	public void exit() {
		this.setVisible(false); //you can't see me!
		this.dispose(); //Destroy the JFrame object
	}
	/**
	 * Exercise the action
	 * param e 
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if(idText.getText().length()==9) {
			id = Integer.parseInt(idText.getText());
			senrio = Integer.parseInt(levelText.getText());

			flag=true;
		}

	}



}


