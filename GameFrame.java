/*
 * @Java Eclipse 2024-06 (4.32.0)
 * @Created by Rafael Molina - helped by BroCode Tutorial
 * GameFrame creates the panel to play with while using the JFrame class
 * to bring up a playing window 
 * 
 * */

package Sssnake;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	

	private static final long serialVersionUID = 1L;
	
// GameFrame method calls the GamePanel as a new GamePanel is made too
// instances are made to add to the GamePanel for the window 
	GameFrame(){
		
		GamePanel panel = new GamePanel(); // instance for new GamePanel
		this.add(panel);
		this.setTitle("Solid Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);// to appear in the middle of the computer
		
	}

}