import javax.swing.*;
import java.awt.*;

public class Bullet {
        int x;
        float y;
        float speed=10.f ;  // Adjust speed as needed
        Image image;  // The image for the asteroid

        public Bullet(int startX, float startY, String imagePath) {
            this.x = startX;
            this.y = startY;
            this.image = new ImageIcon(imagePath).getImage();  // Load the asteroid image
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
            y -= speed;  // Move asteroid down the screen
        }


        public void draw(Graphics g) {
            g.drawImage(image, x, (int)y, null);  // Draw the asteroid's image at (x, y)
        }
    }


