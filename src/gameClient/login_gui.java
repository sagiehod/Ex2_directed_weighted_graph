package gameClient;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;

public class login_gui implements ActionListener {
	

	private static JLabel idlabel;
	private static JTextField idText;
	private static JLabel levelLabel;
	private static JTextField levelText;
	private static JButton button;
	public boolean flag=false;
	public  int id=0;
	public  int senrio=0;
	
	public void chose() {
		

	JFrame frame= new JFrame();
		
		JPanel panel= new JPanel();
		
		
		frame.setSize(350,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		//frame.setLayout(new FlowLayout(FlowLayout.CENTER));
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

	}

	public static void main(String[] args) {

		

	}
	
//	public void Menu_Panel() {
//	
//		JFrame frame;    
//		JMenuBar menubar;    
//		JMenu Game_Menu;    
//		JMenuItem play;    
//		JTextArea textarea;    
//		
//		frame=new JFrame();    
//		play=new JMenuItem("play");    
//		play.addActionListener(this);    
//
//		menubar=new JMenuBar();    
//		Game_Menu=new JMenu("Game Menu");    
//		Game_Menu.add(play);    
//		menubar.add(Game_Menu);    
//		textarea=new JTextArea();    
//		
//		textarea.setBounds(5,5,360,320);    
//		frame.add(menubar);frame.add(textarea);    
//		frame.setJMenuBar(menubar);  
//		frame.setLayout(null);    
//		
//		frame.setSize(420,420);    
//		frame.setVisible(true);    
//		
//		}     
		
		

	@Override
	public void actionPerformed(ActionEvent e) {

        id = Integer.parseInt(idText.getText());
        senrio = Integer.parseInt(levelText.getText());

       	flag=true;
		}

	}


