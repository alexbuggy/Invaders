import java.awt.*;
import javax.swing.ImageIcon;
public class AsteroidTest {


    int x;
    float y;
    float speed ;  // Adjust speed as needed
    Image image;  // The image for the asteroid
    int Value;

    public AsteroidTest(int startX, float startY, float Speed,String imagePath) {
        this.x = startX;
        this.y = startY;
        this.speed = Speed;
        this.image = new ImageIcon(imagePath).getImage();  // Load the asteroid image
        this.Value=100*(int)speed;
    }

    public void move() {
        y += speed;  // Move asteroid down the screen
    }

    public Rectangle getBounds() {
        return new Rectangle(x, (int)y, image.getWidth(null), image.getHeight(null));  // Match size to image
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, (int)y, null);  // Draw the asteroid's image at (x, y)
    }
}
