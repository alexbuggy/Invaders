import javax.swing.*;
import java.awt.*;

public class newHeart {
    int x;
    float y;
    Image image;
    float velocity=10.f;

    public newHeart(int startX, float startY, String imagePath) {
        this.x = startX;
        this.y = startY;
        this.image = new ImageIcon(imagePath).getImage();
    }

    public void move() {
        y += velocity;
    }




    public int getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public int getWidth(){
        return image.getWidth(null);
    }
    public int getHeight(){
        return image.getHeight(null);
    }




    public void draw(Graphics g) {
        g.drawImage(image, x, (int)y, null);  // Draw the asteroid's image at (x, y)
    }
}

