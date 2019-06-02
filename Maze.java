import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;

public class Maze extends JPanel implements KeyListener,Runnable
{

	private JFrame frame;
	private Hero hero;
	private Thread thread;
	private boolean gameOn=true;
	private boolean right=false;
	private ArrayList<Wall> walls;
	private int dim = 20;

	public Maze()
	{
		frame=new JFrame("Maze");
		frame.add(this);
		createMaze("maze.txt");
		//instantiate hero



		frame.addKeyListener(this);
		frame.setSize(1300,750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		thread=new Thread(this);
		thread.start();
	}

	public void createMaze(String fileName)
	{
		wall = new ArrayList<Wall>();
		File name = new File(fileName);
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			int row=0;
			String text;
			while( (text=input.readLine())!= null)
			{
				for(int col = 0; col < text.length(); col++){
					if(text.charAt(x) == 'X'){
						walls.add(new Wall(row, col, dim, dim))
					}
				}
				row++;
			}
		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,frame.getWidth(),frame.getHeight());
		g2.setColor(new Color(288, 140, 190));
		for(Wall i : walls){
			g2.fill(wall.getRect());
		}

	}
	public void run()
	{
		while(true)
		{
			if(gameOn)
			{

				if(right)
				{
					//move hero (if it can move)
					//pick stuff up if there is stuff to be picked up
				}
				//move other stuff

				//check collisions if stuff is moving

			}
			try
			{
				thread.sleep(20);
			}catch(InterruptedException e)
			{
			}
			repaint();
		}
	}
	public void keyPressed(KeyEvent e)
	{

		if(e.getKeyCode()==39)
			right=true;

	}
	public void keyReleased(KeyEvent e)
	{

		if(e.getKeyCode()==39)
			right=false;

	}
	public void keyTyped(KeyEvent e)
	{
	}

	public static void main(String[] args)
	{
		Maze app=new Maze();
	}

}