package brickBreacker;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main
{

	public static void main(String[] args)
	{
		JFrame obj = new JFrame();
		GamePlay gamePlay = new GamePlay();
		
		
		obj.setBounds(10, 10, 710, 600);
		obj.setTitle("Brackout The Ball");
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setVisible(true);
		obj.setResizable(false);
		obj.add(gamePlay);
		
		
		
	}

}
