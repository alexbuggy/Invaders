import javax.swing.*;
import java.awt.*;

public class EnemyBullet {
    int x;
    float y;
    float speed=10.f ;
    Image image;

    public EnemyBullet(int startX, float startY, String imagePath) {
        this.x = startX;
        this.y = startY;
        this.image = new ImageIcon(imagePath).getImage();
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

    public void move() {
        y += speed;  // Move asteroid down the screen
    }


    public void draw(Graphics g) {
        g.drawImage(image, x, (int)y, null);
    }
}


