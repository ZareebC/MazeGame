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
    private ArrayList<MazeCell> cells;
    private int dim = 20;
    private int dir = 0;


    public Maze2() {
        cells = new ArrayList<MazeCell>();
        Parts = new String[98][25];
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
        int walk = 0;
        File name = new File("maze.txt");

        //Convert to 2D Array
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(name));
            String text="";
            int row = 0;
            while( (text=input.readLine()) != null ) {
                //System.out.println(text);
                for(int i = 0; i < text.length(); i++){
                    Parts[i][row] = text.substring(i, i+1);
                }
                row++;


            }

        }

        catch (IOException io)
        {
            System.err.println("File does not exist");
        }
        //Modify Array Here
        int randRow = (int)(Math.random()*24)+1;
        MazeCell start = new MazeCell(0, randRow);
        int startX = start.getX();
        int startY = start.getY();
        Parts[startX][startY] = "o";
        //randRow = (int)(Math.random()*25);
        MazeCell end = new MazeCell(51, randRow);
        int endX = end.getX();
        int endY = end.getY();
        Parts[endX][endY] = "o";
        cells.add(start);
        cells.add(end);

        //Builds ArrayList for Pathway
        while(startX != endX && cells.size() < 4000){
            System.out.println(cells.size());
            int randDirection = (int)(Math.random()*4)+1;
            System.out.println(randDirection);
            System.out.println(cells.get(cells.size()-1).getX() + " " + cells.get(cells.size()-1).getY());
            switch(randDirection){
                //Up
                case 1:
                    if(startY -1 >= 0  && startY-endY > -10) {
                        System.out.println("inside1");
                        startY--;
                        cells.add(new MazeCell(startX, startY));
                        Parts[startX][startY] = "o";
                    }
                    break;
                //Down
                case 2:
                    if(startY + 1 < 24 && startY-endY < 10) {
                        System.out.println("inside2");
                        startY++;
                        cells.add(new MazeCell(startX, startY));
                        Parts[startX][startY] = "o";
                    }
                    break;
                //Left
                case 3:
                    if(startX - 1 >= 7 ) {
                        System.out.println("inside3");
                        startX--;
                        cells.add(new MazeCell(startX, startY));
                        Parts[startX][startY] = "o";
                    }
                    break;
                //Right
                default:
                    if(startX + 1 < 52) {
                        System.out.println("inside4");
                        startX++;
                        cells.add(new MazeCell(startX, startY));
                        Parts[startX][startY] = "o";
                    }
                    break;
            }

        }

        //Builds Pathway
        System.out.println(startY);
        System.out.println(startX);
        System.out.println(cells.size());
        //for(int i = 0; i < cells.size(); i++){
        //    System.out.println(cells.get(i).getX());
          //  System.out.println(cells.get(i).getY());
            //Parts[cells.get(i).getY()][cells.get(i).getX()] = "o";
        //}

        //Convert 2D Array to text file
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(name));
            BufferedWriter OutputStream=new BufferedWriter(new FileWriter("mazeNew"));
            String text,output="";
            int row = 0;
            while( (text=input.readLine()) != null ) {
                for(int i = 0; i < text.length(); i++){
                    output += Parts[i][row];
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

