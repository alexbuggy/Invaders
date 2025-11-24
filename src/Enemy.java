import javax.swing.*;
import java.awt.*;

public class Enemy {

    int hp=15;
    int x=500;
    float y=0.f;
    float xVelocity=4.f;
    float yVelocity=0.5f;
    boolean sensEnemy=true;
    int Value=15000;
    Image image;

    public Enemy(String imagePath) {

        this.image = new ImageIcon(imagePath).getImage();  // Load the asteroid image
    }

    public int getX(){return x;}
    public float getY(){return y;}
    public int getWidth(){return image.getWidth(null);}
    public int getHeight(){return image.getHeight(null);}

    public  int getHp() {
        return hp;
    }
    public  void setHp(int number) {
        hp=number;
    }
    public void takeDamage(){
        setHp(getHp()-1);
    }


    public void Move(){

        y+=yVelocity;

        if(x>=1000 - image.getWidth(null)){
            sensEnemy=true;
        }
        else
        if(x<=0){
            sensEnemy=false;
        }
        if(sensEnemy){
            x-=xVelocity;
        }
        else {
            x+=xVelocity;
        }
    }

    public void ChangeHorizontalSpeed(float value){
        xVelocity+=value;
    }
    public void ChangeVerticalSpeed(float value){
        yVelocity+=value;
    }


    public void draw(Graphics g) {
        g.drawImage(image, x, (int)y, null);  // Draw the asteroid's image at (x, y)
    }
}

