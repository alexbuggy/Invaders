import javax.swing.*;
import java.awt.*;

public class PlayerRocket {
    int hp=3;
    long Score=0;
    int x=500;
    float y;
    float xVelocity=7.f;
    Image image;


    public PlayerRocket(String imagePath) {

        this.image = new ImageIcon(imagePath).getImage();// Load the asteroid image
        this.y= 1000 - image.getHeight(null) + 45;
    }

    public int getX(){return x;}
    public float getY(){return y;}
    public int getWidth(){return image.getWidth(null);}
    public int getHeight(){return image.getHeight(null);}


    public int getHp() {
        return hp;
    }
    public void setHp(int number) {
        hp=number;
    }
    public void takeDamage(){
        setHp(getHp()-1);
    }
    public void recoverHp(){
        setHp(getHp()+1);
    }

    public void moveLeft(){
        x-=xVelocity;
        if(x<-30){
            x=-30;
        }
    }
    public void moveRight(){
        x+=xVelocity;
        if(x>1000-image.getWidth(null)+30)
        {
            x=1000-image.getWidth(null)+30;
        }
    }
    public void ChangeSpeed(float value){
        xVelocity+=value;
    }
    public long getScore(){
        return Score;
    }
    public void score_Up(int value){
        Score+=value;
    }
    public void score_Down(int value){
        Score-=value;
    }


    public void draw(Graphics g) {
        g.drawImage(image, x, 1000-image.getHeight(null)+30, null);  // Draw the asteroid's image at (x, y)
    }
}

