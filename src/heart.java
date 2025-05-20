import javax.swing.*;
import java.awt.*;

public class heart {
    int x;
    int y;
    Image image;  // The image for the asteroid

        public heart(int startX, int startY, String imagePath) {
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




        public void draw(Graphics g) {
            g.drawImage(image, x, y, null);  // Draw the asteroid's image at (x, y)
        }
    }

