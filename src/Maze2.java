import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;

public class Maze2 extends JPanel implements KeyListener,Runnable {

    private JFrame frame;
    private Hero hero;
    private Thread thread;
    private boolean gameOn = true;
    private boolean right = false, up = false, down = false;
    private ArrayList<Wall> walls;
    private String[][] Parts;
    private int dim = 20;
    private int dir = 0;


    public Maze2() {
        Parts = new String[25][98];
        frame = new JFrame("Maze");
        frame.add(this);
        createText();
        createMaze("mazeNew");
        //instantiate hero
        hero = new Hero(0, 0, dim, dim, Color.CYAN, Color.WHITE);


        frame.addKeyListener(this);
        frame.setSize(1300, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        thread = new Thread(this);
        thread.start();
    }
    public void createText(){
        int num = 0;
        int steps = 0;
        int total = 0;
        File name = new File("maze.txt");

        try
        {
            BufferedReader input = new BufferedReader(new FileReader(name));
            String text="";
            int row = 0;
            while( (text=input.readLine()) != null ) {
                //System.out.println(text);
                for(int i = 0; i < text.length(); i++){
                    Parts[row][i] = text.substring(i, i+1);
                }
                row++;


            }

        }

        catch (IOException io)
        {
            System.err.println("File does not exist");
        }
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(name));
            BufferedWriter OutputStream=new BufferedWriter(new FileWriter("mazeNew"));
            String text,output="";
            int row = 0;
            while( (text=input.readLine()) != null ) {
                for(int i = 0; i < text.length(); i++){
                    output += Parts[row][i];
                }
                row++;

                OutputStream.write(output);
                output = "";
                OutputStream.newLine();
            }


            OutputStream.close();

        }

        catch (IOException io)
        {
            System.err.println("File does not exist");
        }

    }


    public void createMaze(String fileName) {
        walls = new ArrayList<Wall>();
        File name = new File(fileName);
        try {
            BufferedReader input = new BufferedReader(new FileReader(name));
            int row = 0;
            String text;
            while ((text = input.readLine()) != null) {
                for (int col = 0; col < text.length(); col++) {
                    if (text.charAt(col) == 'X') {
                        walls.add(new Wall(row, col, dim, dim));
                    }
                }
                row++;
            }
        } catch (IOException io) {
            System.err.println("File does not exist");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        g2.setColor(new Color(240, 10, 10));
        for (Wall i : walls) {
            g2.fill(i.getRect());
        }
        g2.setColor(hero.getColor());
        g2.fill(hero.getRect());
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.draw(hero.getRect());

    }

    public void run() {
        while (true) {
            if (gameOn) {

            }
            try {
                thread.sleep(20);
            } catch (InterruptedException e) {
            }
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {
        dir = e.getKeyCode();
        hero.move(dir, walls);


    }

    public void keyReleased(KeyEvent e) {
        dir = 0;

    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        Maze2 app = new Maze2();
    }
}

