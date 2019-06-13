import java.awt.Rectangle;
public class MazeTrap {
    private int x;
    private int y;
    private int width;
    private int height;
    public MazeTrap(int x, int y, int width, int height){
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
        return new Rectangle(getX()*getHeight(),getY()*getWidth(), getWidth(), getHeight());
    }
}
