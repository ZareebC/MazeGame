
import java.awt.Rectangle;
public class Wall {
    private int x;
    private int y;
    private int width;
    private int height;
    public Wall(int x, int y, int width, int height){
        this.x =  x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Rectangle getRect(){
        return new Rectangle(getY()*getHeight(),getX()*getWidth(), getWidth(), getHeight());
    }
    public Rectangle collisionBox(int x, int y){
        return new Rectangle(y*getHeight(), x*getWidth(), getWidth(), getHeight());
    }
}
