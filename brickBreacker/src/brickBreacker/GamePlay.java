package brickBreacker;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener
{
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private int life = 3;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballPosX = 120;
	private int ballPosY = 350;
	
	private int ballDirX = -1;
	private int ballDirY = -2;
	
	private MapGenerator map;
	private JButton exit = new JButton();
	
	
	
	public GamePlay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(delay, this);
		timer.start();
		
	}
	public void paint(Graphics g) {
		//Backround
		g.setColor(Color.white);
		g.fillRect(1, 1, 692, 592);
		
		//Border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.gray);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 600, 30);
		
		//Life
		g.setColor(Color.gray);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+life, 30, 30);
		
		// bricks
		map.draw((Graphics2D) g);
		
		//win the game
		if(totalBricks == 0) {
			g.setColor(Color.green);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Yesss you Win. Click Enter to Play Again", 100, 300);
			play = false;
		}
		
		// Game Over
		if (ballPosY > 570) {
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 25));
			
			if (life <= 3 && life > 0) {
				life -= 1;
				ballPosY = 350;
				
			}
			else if 
			(life == 0) {
				play = false;
				ballDirX = 0;
				ballDirY = 0;
				g.setColor(Color.red);
				g.setFont(new Font("serif", Font.BOLD, 25));
				g.drawString("Game Over", 300, 300);
			}
			
		}
		
		//Paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		timer.start();
		if(play) {
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballDirY = -ballDirY;
			}
			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j <map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j*map.brickWith+80;
						int brickY = i*map.brickHeight+50;
						int brickWith = map.brickWith;
						int brickHeight = map.brickHeight;
						Rectangle brick = new Rectangle(brickX, brickY, brickWith, brickHeight);
						Rectangle ball = new Rectangle(ballPosX, ballPosY, 20, 20);
						if (ball.intersects(brick)) {
							map.setBricksValue(0, i, j);
							totalBricks--;
							score += 5;
							if (ballPosX +19 <= brick.x || ballPosX+1 >= brick.x+brick.width) ballDirX = -ballDirX;
							
							else ballDirY = -ballDirY;
							break A;
						}
						
					}
					
				}
			}
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			
			if(ballPosX < 0) ballDirX *=-1; 
			if(ballPosY < 0) ballDirY *=-1;
			if(ballPosX > 670) ballDirX *=-1; 
			//if(ballPosY > 600) ballDirY *=-1; 
		}
		
		repaint();
		
	}

	public void resetGame() {
		score = 0;
		totalBricks = 21;
		life = 3;
		playerX = 310;
		ballPosX = 120;
		ballPosY = 350;
		
		ballDirX = -1;
		ballDirY = -2;
		
		map = new MapGenerator(3,7);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >=600) playerX = 600;
			else moveRight();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <=10) playerX = 10;
			else moveLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			resetGame();
		}
	}
	
	public void moveLeft()
	{
		play = true;
		playerX-=20;
		
	}
	public void moveRight()
	{
		play = true;
		playerX+=20;
		
	}
	@Override
	public void keyTyped(KeyEvent e){}
	@Override
	public void keyReleased(KeyEvent e){}

}
