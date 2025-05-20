import javax.swing.*;
import java.awt.*;


public class EnemyType2 {

    int hp=3;
    int x;
    float y;
    float xVelocity=4.f;
    float yAttackVelocity=10.f;
    float yVelocity=0.5f;
    boolean above_player=false;
    boolean sensEnemy=true;
    int Value=2500;
    Image image;

    public EnemyType2(int x,int y ,String imagePath) {
    this.x=x;
    this.y=y;

        this.image = new ImageIcon(imagePath).getImage();
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

    void setAbove_player(){
        above_player=true;
    }

    public void Attack(){
        if(above_player==true){
            y+=yAttackVelocity;
        }
    }

    public void Move(){
    if(above_player==false){
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

