/*
 * @Java Eclipse 2024-06 (4.32.0)
 * @Created by Rafael Molina - helped by BroCode Tutorial
 * Java Abstract Window Toolkit imported for graphical design along with Swing GUI- * covers different uses
 * Almost all methods reside in GamePanel class including:
 * movement, gameOver, draw, apple placement, x and y axis locating snake and apple
 * 
 */

package Sssnake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	
	/**
	 * instances created: dimensions of the window
	 * keyevents for movement for bodyParts of the snake and apples/ including scoring
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; 
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	
	final int x[] = new int[GAME_UNITS]; //array holds X coordinates of the snake
	final int y[] = new int[GAME_UNITS]; //array holds the Y coordinates of the snake
	
	int bodyParts = 6; 
	int applesEaten;
	int appleX; // coordinates of where the apple is at random appearance
	int appleY; // 
	char direction = 'R'; // R for right, snake starts going right
	boolean running = false; 
	Timer timer; 
	Random random; 
	
	
	// constructor for the game panel 
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	// method starts the game
	public void startGame() {
		newApple(); // creates apples for scoring in the game
		running = true; //set to true because its false at first 
		timer = new Timer(DELAY, this); //dictates how fast the game is played
		timer.start();
	}
	// method to use GUI graphics for window 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	
	// drawings the window to play on and sizing of the snake and apple
	public void draw(Graphics g) {
		
		if(running) {
			// shows grids on the playing field, easier for players to see when to turn, you can comment out the block 
			for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE , SCREEN_HEIGHT);//makes lines up and down 
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH , i * UNIT_SIZE);// makes lines left to right
			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);// creates the size of the apple
		
			for(int i = 0; i < bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.green); //Gives the snake it's color
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45, 180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));//RBG color thats different than green, can comment out
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Sans-Serif", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
		}
		else {
			gameOver(g);//g is Graphics received by parameter
		}
		
	}
	// populates the game with apples 
	public void newApple() {
		appleX = random.nextInt((int)SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE; //creates a random apple through the squares
		appleY = random.nextInt((int)SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
	}
	// method moves the snake
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		// creates a case for each direction of movement 
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	// method makes sure points are allocated for the game
	public void checkApple() {
		if((x[0] == appleX) && y[0] == appleY) {
			bodyParts++;
			applesEaten++;
			newApple(); 
		}
		
	}
	//method checks to see if snake runs into itself, ending the turn
	public void checkCollisions() {
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//checks if head touches the left border
		if(x[0] < 0) {
			running = false;
		}
		//checks if head runs into right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// checks if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		// checks if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
		
	}
	//method ends the turn in the game 
	public void gameOver(Graphics g) {
		//Score Text after game over
		g.setColor(Color.red);
		g.setFont(new Font("Sans-Serif", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
		
		
		//Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("GameOVer")) / 2, SCREEN_HEIGHT / 2); // Centers the Game Over text 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			//Cases for keys being pressed up, down, left, right, with no 180 turns 
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
			
		}
	}

}
