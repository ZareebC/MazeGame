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
    private boolean trapSet = false;
    private boolean right = false, up = false, down = false;
    private ArrayList<Wall> walls;
    private String[][] Parts;
    private ArrayList<MazeCell> cells;
    private ArrayList<MazeTrap> traps;
    private ArrayList<Lava> lava;
    private int dim = 20;
    private int dir = 0;
    private int cycle = 0;
    private int randRow = 0;
    private int rowI = 0;
    private int delay = 0;
    private int score = 0;
    private int millis = 700;
    private int trapPick = 20;
    private boolean mode = true;


    public Maze2() {
        cells = new ArrayList<MazeCell>();
        Parts = new String[52][25];
        randRow = (int)(Math.random()*24)+1;
        frame = new JFrame("Maze");
        frame.add(this);
        createText();
        createMaze("mazeNew");
        //instantiate hero
        hero = new Hero(randRow, 0, dim, dim, Color.CYAN, Color.WHITE);


        frame.addKeyListener(this);
        frame.setSize(1100, 600);
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
        int trapProb = 0;
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
        MazeCell start = new MazeCell(0, randRow, dim, dim);
        int startX = start.getX();
        int startY = start.getY();
        Parts[startX][startY] = "o";
        //randRow = (int)(Math.random()*25);
        MazeCell end = new MazeCell(51, randRow, dim, dim);
        int endX = end.getX();
        int endY = end.getY();
        Parts[endX][endY] = "o";
        cells.add(start);
        cells.add(end);

        //Builds ArrayList for Pathway
        while(startX != endX && cells.size() < 40000){
            System.out.println(cells.size());
            int randDirection = (int)(Math.random()*4)+1;
            System.out.println(randDirection);
            System.out.println(cells.get(cells.size()-1).getX() + " " + cells.get(cells.size()-1).getY());
            switch(randDirection){
                //Up
                case 1:
                    if(startY -1 >= 0  && startY-endY > -15) {
                        System.out.println("inside1");
                        startY--;
                        cells.add(new MazeCell(startX, startY, dim, dim));
                        Parts[startX][startY] = "o";
                    }
                    break;
                //Down
                case 2:
                    if(startY + 1 < 24 && startY-endY < 15) {
                        System.out.println("inside2");
                        startY++;
                        cells.add(new MazeCell(startX, startY, dim, dim));
                        Parts[startX][startY] = "o";
                    }
                    break;
                //Left
                case 3:
                    if(startX - 1 >= 7 ) {
                        System.out.println("inside3");
                        startX--;
                        cells.add(new MazeCell(startX, startY, dim, dim));
                        Parts[startX][startY] = "o";
                    }
                    break;
                //Right
                default:
                    if(startX + 1 < 52) {
                        System.out.println("inside4");
                        startX++;
                        cells.add(new MazeCell(startX, startY, dim, dim));
                        Parts[startX][startY] = "o";
                    }
                    break;
            }

        }

        //Builds Pathways
        //System.out.println(startY);
        //System.out.println(startX);
        //System.out.println(cells.size());
        for(int i = 0; i < Parts.length; i++){
           for(int j = 0; j < Parts[0].length; j++){
                if(Parts[i][j].equals("o") && j-1 >= 0 && i-1 >0){
                    System.out.println("works");
                    Parts[i][j-1] = "X";
                    Parts[i-1][j] = "o";
                }
            }
        }
        //Place Traps
        traps = new ArrayList<MazeTrap>();
        for(int i = 0; i < Parts.length; i++){
            for(int j = 0; j < Parts[0].length; j++){
                trapProb = (int)(Math.random()*trapPick)+1;
                if(trapProb == 4 && Parts[i][j].equals("o")){
                    traps.add(new MazeTrap(i , j, dim, dim));
                    Parts[i][j] = "t";
                }
            }
        }
    }


    public void createMaze(String fileName) {
        File name1 = new File("maze.txt");
        //Convert 2D Array to text file
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(name1));
            BufferedWriter OutputStream=new BufferedWriter(new FileWriter("mazeNew"));
            String text,output="";
            int row = 0;
            while( (text=input.readLine()) != null ) {
                for(int i = 0; i < text.length(); i++){
                    output += Parts[i][row] ;
                }
                output += "X";
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
        walls = new ArrayList<Wall>();
        File name = new File(fileName);
        try {
            BufferedReader input = new BufferedReader(new FileReader(name));
            int row = 0;
            String text;
            while ((text = input.readLine()) != null) {
                for (int col = 0; col < text.length(); col++) {
                    if (text.charAt(col) == 'X' || text.charAt(col) == 'U') {
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
        g2.setColor(Color.decode("#7000e5"));
        for(Lava i : lava){
            g2.fill(i.getRect());
        }
        g2.setColor(new Color(232, 240, 115));
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("Score: " + Integer.toString(score), 60, 40);
        g2.setColor(new Color(36, 233, 32));
        for(int i = 0; i < Parts.length; i++){
            for(int j = 0; j < Parts[0].length; j++){
                if(Parts.length == 51){
                    cells.add(new MazeCell(i,j, dim, dim));
                }
            }
            g2.fill(cells.get(cells.size()-1).getRect());
        }
        g2.setColor(new Color(30, 172, 233));
        for(int i = 0; i < traps.size(); i++){
            g2.fill(traps.get(i).getRect());
            repaint();
        }
        if(mode){
            g2.setColor(new Color(43, 180, 226));
            g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            g2.setColor(Color.ORANGE);
            g2.setFont(new Font("Arial",Font.ITALIC, 50));
            g2.drawString("Blue blocks invert your controls", 180, 300);
            g2.setFont(new Font("Arial",Font.ITALIC, 70));
            g2.drawString("Press 'space' to start", 200, 200);
        }
        repaint();
    }

    public void run() {
        lava = new ArrayList<Lava>();
        while (true) {
            if (gameOn) {
                if (hero.getY() == Parts.length - 1) {
                    cycle = 0;
                    trapPick--;
                    traps.clear();
                    createText();
                    createMaze("mazeNew");
                    lava.clear();
                    millis*=.80;
                    hero = new Hero(randRow, 0, dim, dim, Color.CYAN, Color.WHITE);
                }
                if (hero.getY() > 5 && cycle < Parts.length) {
                    while (rowI < Parts[0].length) {
                        if(Parts[cycle][rowI].equals("o") || Parts[cycle][rowI].equals("t")) {
                            Parts[cycle][rowI] = "U";
                            lava.add(new Lava(rowI, cycle, dim, dim));
                            System.out.println("check" + cycle);
                            rowI++;
                        }
                        if(Parts[cycle][rowI].equals("X"))
                        rowI++;
                    }
                    createMaze("mazeNew");
                    cycle++;
                    rowI = 0;
                    if(cycle >= hero.getY()){
                        gameOn = false;
                        frame.setVisible(false);
                    }
                }
            }
            try {
                thread.sleep(millis);
            } catch (InterruptedException e) {
            }
            repaint();
            if(hero.getY() > 5)
                score++;
        }
    }
    public void keyPressed(KeyEvent e) {
        //Traps
        if (Parts[hero.getY()][hero.getX()].equals("t")) {
            trapSet = true;
            //Parts[hero.getY()][hero.getX()] = "o";
        }
        else if (Parts[hero.getY()][hero.getX()].equals("o")) {
            trapSet = false;
        }
        dir = e.getKeyCode();
        if(trapSet)
            hero.moveShuffle(dir, walls);
        else
            hero.move(dir, walls);
        if(dir == 32)
            mode = false;
        repaint();
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

