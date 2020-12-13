package gameClient;

import javax.swing.*;    
	import java.awt.event.*;    
		import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;

	public class Menu_Panel extends JFrame implements ActionListener{

	    JMenuBar menuBar;
	    JMenu GameMenu;
	    JMenu editMenu;
	 
	    JMenuItem playItem;
	    JMenuItem exitItem;
	 
	    ImageIcon playIcon;
	    ImageIcon exitIcon;

	    Menu_Panel(){
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setSize(300,250);
	        this.setLayout(new FlowLayout());

	       
	         playIcon = new ImageIcon("./resources/save.png");
	         exitIcon = new ImageIcon("./resources/exit.jpg");

	      
	        playIcon = scaleImageIcon(playIcon,20,20);
	        exitIcon = scaleImageIcon(exitIcon,20,20);

	        menuBar = new JMenuBar();

	        GameMenu = new JMenu("Menu Game");
	        editMenu = new JMenu("Edit");

	     
	       playItem = new JMenuItem("play");
	        exitItem = new JMenuItem("Exit");



	     
	        playItem.addActionListener(this);
	        exitItem.addActionListener(this);

	    
	        playItem.setIcon(playIcon);
	        exitItem.setIcon(exitIcon);

	        GameMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file
	        editMenu.setMnemonic(KeyEvent.VK_E); // Alt + e for edit
	       
	     
	        playItem.setMnemonic(KeyEvent.VK_S); // s for save
	        exitItem.setMnemonic(KeyEvent.VK_E); // e for exit

	    
	        GameMenu.add(playItem);
	        GameMenu.add(exitItem);

	        menuBar.add(GameMenu);
	        menuBar.add(editMenu);
	       

	        this.setJMenuBar(menuBar);

	        this.setVisible(true);
	    }

	    public static ImageIcon scaleImageIcon(ImageIcon imageIcon,int width,int height){
	        Image image = imageIcon.getImage(); // transform it
	        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
	        return new ImageIcon(newimg);  // transform it back
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {


	        if(e.getSource()==playItem) {
	            System.out.println("*beep boop* you saved a file");
	        }
	        if(e.getSource()==exitItem) {
	            System.exit(0);
	        }
	    }

	    public static void main(String[] args) {
	        new Menu_Panel();
	    }
	}
