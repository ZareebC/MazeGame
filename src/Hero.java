import java.awt.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

public class Hero {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private Color out;

    public Hero(int x, int y, int width, int height, Color color, Color out) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.out = out;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void move(int dir, ArrayList<Wall> walls){
        boolean canMove = true;
        switch(dir){
            //left
            case 37:
                for(Wall i: walls){
                    if(collisionBox(x,y-1).intersects(i.getRect()))
                        canMove = false;
                }
                if(canMove)
                y--;
                break;
            //up
            case 38:
                for(Wall i: walls){
                    if(collisionBox(x-1,y).intersects(i.getRect()))
                        canMove = false;
                }
                if(canMove)
                    x--;
                break;
            //right
            case 39:
                for(Wall i: walls){
                    if(collisionBox(x,y+1).intersects(i.getRect()))
                        canMove = false;
                }
                if(canMove)
                y++;
                break;
            //down
            case 40:
                for(Wall i: walls){
                    if(collisionBox(x+1,y).intersects(i.getRect()))
                        canMove = false;
                }
                if(canMove)
                x++;

        }
    }
    //Trap Movement
    public void moveShuffle(int dir, ArrayList<Wall> walls){
        boolean canMove = true;
        switch(dir){
            //left
            case 37:
                for(Wall i: walls){
                    if(collisionBox(x,y-1).intersects(i.getRect()))
                        canMove = false;
                }
                if(canMove)
                    y++;
                break;
            //up
            case 38:
                for(Wall i: walls){
                    if(collisionBox(x-1,y).intersects(i.getRect()))
                        canMove = false;
                }
                if(canMove)
                    x++;
                break;
            //right
            case 39:
                for(Wall i: walls){
                    if(collisionBox(x,y+1).intersects(i.getRect()))
                        canMove = false;
                }
                if(canMove)
                    y--;
                break;
            //down
            case 40:
                for(Wall i: walls){
                    if(collisionBox(x+1,y).intersects(i.getRect()))
                        canMove = false;
                }
                if(canMove)
                    x--;

        }
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public Color getColor(){
        return color;
    }
    public Color getOutColor(){
        return color;
    }
    public Rectangle getRect(){
        return new Rectangle(getY()*getHeight(),getX()*getWidth(), getWidth(), getHeight());
    }
    public void setY(int changeY){
        y-=10;
    }
    public Rectangle collisionBox(int x, int y){
        return new Rectangle(y*getHeight(), x*getWidth(), getWidth(), getHeight());
    }
}