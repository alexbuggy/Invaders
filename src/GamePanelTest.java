import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class GamePanelTest extends JPanel implements ActionListener, KeyListener {

    private Set<Integer> activeKeys = new HashSet<>();


    final int PANEL_WIDTH = 1000;
    final int PANEL_HEIGHT = 1000;

    int MinXforAsteroids=0;
    int MaxXforAsteroids=PANEL_WIDTH-50;
    int lastRandom=1000;
    int randomX;
    float speedRand;

    float minSpeed=1.f;
    float maxSpeed=5.f;

    float incrementValue=0.5f;
    private long lastmodifyTime=0;
    int asteroidspawnrate=100;
    private static final long AsteroidSpeedRate=30000;
    int demonsspawnrate=300;

    int chance;
    private boolean enemySpawned = false;
    public boolean mort=false;
    private long startTime;
    private static final long ENEMY_SPAWN_COOLDOWN = 30000;
    private static final long INITIAL_SPAWN_DELAY = 20000;
    private boolean initialSpawn = true;
    private long lastEnemyDeathTime=0;

    private static final long Laser_SPAWN_DELAY = 1000;
    private long lastLaserTime=0;

    private static final long Bullet_SPAWN_DELAY = 300;
    private long lastBulletTime=0;

    int xdemon;
    float ydemon;

    Timer timer;

    ArrayList<AsteroidTest> asteroids = new ArrayList<>();
    ArrayList<BulletTest> bullets = new ArrayList<>();
    ArrayList<EnemyTest> enemies = new ArrayList<>();
    ArrayList<EnemyBulletTest> enemyammo = new ArrayList<>();
    ArrayList<EnemyType2> demons = new ArrayList<>();
    ArrayList<newHeart> hearts = new ArrayList<>();


    ArrayList<newHeart> heartsToRemove = new ArrayList<>();
    ArrayList<AsteroidTest> asteroidsToRemove = new ArrayList<>();
    ArrayList<BulletTest> bulletsToRemove = new ArrayList<>();
    ArrayList<EnemyTest> enemiesToRemove = new ArrayList<>();
    ArrayList<EnemyBulletTest> lasersToRemove = new ArrayList<>();
    ArrayList<EnemyType2> demonsToRemove = new ArrayList<>();

    heart heart1=new heart(850,50,"heart.png");
    heart heart2=new heart(900,50,"heart.png");
    heart heart3=new heart(950,50,"heart.png");

    Random random = new Random();
    PlayerRocketTest player;

    GamePanelTest(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        player=new PlayerRocketTest("rachetuca.png");


        timer=new Timer(16,this);
        timer.start();
        startTime = System.currentTimeMillis();


    }

    public long getScore(){
        return player.getScore();
    }

    public String getGameScore(){
        long number=player.getScore();;
        int iterator=9;
        String[] GameScore = new String[9];
        for(int i=8;i>=0;i--){
            if(number!=0) {
                GameScore[i] = String.valueOf(number % 10);
                number = number / 10;
                iterator=i;
            }
        }
        for(int i=iterator-1;i>=0;i--){
            GameScore[i] = "0";
        }


        StringBuilder scoreBuilder = new StringBuilder();
        for (String digit : GameScore) {
            scoreBuilder.append(digit);
        }

        return scoreBuilder.toString();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;



        player.draw(g2D);



        for (EnemyTest enemy : enemies) {
            enemy.draw(g2D);
        }
        for (AsteroidTest asteroid : asteroids) {
            asteroid.draw(g2D);
        }
        for (BulletTest bullet : bullets) {
            bullet.draw(g2D);
        }
        for (EnemyBulletTest lasers : enemyammo) {
            lasers.draw(g2D);
        }
        for (EnemyType2 demon : demons) {
            demon.draw(g2D);
        }
        for (newHeart heart : hearts) {
            heart.draw(g2D);
        }


        int lineY = 1000 - player.image.getHeight(null)+40;
        g2D.setColor(Color.RED); // Set the line color to red
        g2D.drawLine(0, lineY, PANEL_WIDTH, lineY);

        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("Monospaced", Font.BOLD, 20));
        g2D.drawString("Score: "+ getGameScore(),800,20);

        if(player.getHp()==3){
            heart1.draw(g2D);
        }
        if(player.getHp()>=2){
            heart2.draw(g2D);
        }
        heart3.draw(g2D);

    }





    public boolean CheckCollision(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2){
        if(x2<=x1 && x1<=x2+width2)
            if(y2<=y1+height1 && y1+height1<=y2+height2)
                return true;
        if(x1<=x2 && x2+width2<=x1+width1)
            if(y1+height1<=y2+height2 && y1+height1>=y2)
                return true;
        if(x2<=x1+width1 && x1+width1<=x2+width2)
            if(y1+height1<=y2+height2 && y1+height1>=y2)
                return true;
        return false;
    }






    @Override
    public void actionPerformed(ActionEvent e) {

        long currentTime = System.currentTimeMillis();



        if (activeKeys.contains(KeyEvent.VK_LEFT) || activeKeys.contains(KeyEvent.VK_A)) {
            player.moveLeft();
        }
        if (activeKeys.contains(KeyEvent.VK_RIGHT) || activeKeys.contains(KeyEvent.VK_D)) {
            player.moveRight();
        }
        if (activeKeys.contains(KeyEvent.VK_SPACE) && currentTime - lastBulletTime >= Bullet_SPAWN_DELAY) {
            bullets.add(new BulletTest(player.x + player.image.getWidth(null) / 2 - 16, 1000 - player.image.getHeight(null) + 30, "bullet.png"));
            activeKeys.remove(KeyEvent.VK_SPACE);
            lastBulletTime = currentTime;
        }




        ///MOVEMENT ASTEROIDS AND DELETE THEM IF THEY LEAVE THE SCREEN
        for (AsteroidTest asteroid : asteroids) {
            asteroid.move();
            if((int)asteroid.y<-20){
                asteroidsToRemove.add(asteroid);
            }
        }

        /// MOVEMENT BULLETS AND DELETE THEM IF THEY LEAVE THE SCREEN
        for (BulletTest bullet : bullets) {
            bullet.move();
            if(bullet.getY()<-20){
                bulletsToRemove.add(bullet);
            }
        }

        /// MOVEMENT HEARTS AND DELETE THEM IF THEY LEAVE THE SCREEN
        for (newHeart heart : hearts) {
            heart.move();
            if(heart.getY()<-20){
                heartsToRemove.add(heart);
            }
        }

        /// MOVEMENT DEMONS, DELETE THEM IF THEY LEAVE THE SCREEN AND CHANGE THE ATTACK STATE IF THEY ARE ABOVE THE PLAYER
        for ( EnemyType2 demon: demons) {
            demon.Move();
            if(demon.getY()<-20){
                demonsToRemove.add(demon);
            }
            if(demon.getX()<=player.getX() && demon.getX()+demon.image.getWidth(null) >= player.getX()+player.image.getWidth(null)){
                demon.setAbove_player();
            }
        }

        /// MOVEMENT SPACESHIPS
        for (EnemyTest enemy : enemies) {
            enemy.Move();
        }

        ///  MOVEMENT LASERS AND DELETE THEM IF THEY LEAVE THE SCREEN
        for (EnemyBulletTest lasers : enemyammo) {
            lasers.move();
            if(lasers.getY()<-20){
                lasersToRemove.add(lasers);
            }
        }

        ///  DEMON ATTACK AND DELETE THEM IF THEY LEAVE THE SCREEN
        for ( EnemyType2 demon: demons) {
            demon.Attack();
            if(demon.getY()<-20){
                demonsToRemove.add(demon);
            }
        }



        ///  COLLISION BETWEEN BULLETS AND ASTEROIDS AND SCORE UP
        for (BulletTest bullet : bullets) {
            for (AsteroidTest asteroid : asteroids) {
                if (CheckCollision(bullet.x, (int)bullet.y, bullet.getWidth(), bullet.getHeight(), asteroid.x, (int)asteroid.y, asteroid.image.getWidth(null), asteroid.image.getHeight(null))) {
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);
                    player.score_Up(100*(int)asteroid.speed);
                }
            }
        }

        ///  COLLISION BETWEEN LASERS AND HEARTS
        for (EnemyBulletTest lasers : enemyammo) {
            for (newHeart heart : hearts) {
                if (CheckCollision(lasers.x, (int)lasers.y, lasers.getWidth(), lasers.getHeight(), heart.x, (int)heart.y, heart.image.getWidth(null), heart.image.getHeight(null)) || CheckCollision(heart.x, (int)heart.y, heart.getWidth(), heart.getHeight(), lasers.x, (int)lasers.y, lasers.image.getWidth(null), lasers.image.getHeight(null))) {
                    lasersToRemove.add(lasers);
                    heartsToRemove.add(heart);
                }
            }
        }



        ///  COLLISION BETWEEN BULLETS AND DEMONS
        for (BulletTest bullet : bullets) {
            for (EnemyType2 demon : demons) {
                if (CheckCollision(bullet.x, (int)bullet.y, bullet.getWidth(), bullet.getHeight(), demon.x, (int)demon.y, demon.image.getWidth(null), demon.image.getHeight(null))) {
                    bulletsToRemove.add(bullet);
                    demon.takeDamage();
                }
            }
        }


        ///  COLISION BETWEEN PLAYER AND ASTEROIDS DELETION OF ASTEROID
        for(AsteroidTest asteroid : asteroids)
            if(CheckCollision( asteroid.x, (int)asteroid.y, asteroid.image.getWidth(null)-20, asteroid.image.getHeight(null)-20,player.x, (int)player.y+15, player.image.getWidth(null)-10, player.image.getHeight(null)-10)){
                asteroidsToRemove.add(asteroid);
                player.takeDamage();
                System.out.println("You got hit! Player HP is "+player.getHp());
            }

        ///  COLISION BETWEEN PLAYER AND DEMONS DELETION OF DEMON
        for( EnemyType2 demon: demons)
            if(CheckCollision( demon.x, (int)demon.y, demon.image.getWidth(null)-20, demon.image.getHeight(null)-20,player.x, (int)player.y+15, player.image.getWidth(null)-10, player.image.getHeight(null)-10)){
                demonsToRemove.add(demon);
                player.takeDamage();
                System.out.println("You got hit! Player HP is "+player.getHp());
            }

        ///  COLISION BETWEEN PLAYER AND LASERS AND DELETION OF LASER
        for(EnemyBulletTest lasers : enemyammo)
            if(CheckCollision( lasers.x, (int)lasers.y, lasers.image.getWidth(null)-10, lasers.image.getHeight(null)-20,player.x, (int)player.y+15, player.image.getWidth(null)-10, player.image.getHeight(null)-10)){
                lasersToRemove.add(lasers);
                player.takeDamage();
                System.out.println("You got hit! Player HP is "+player.getHp());
            }

        ///  COLISION BETWEEN PLAYER AND HEART AND DELETION OF HEART
        for(newHeart heart : hearts)
            if(CheckCollision( heart.x, (int)heart.y, heart.image.getWidth(null), heart.image.getHeight(null),player.x, (int)player.y+15, player.image.getWidth(null)-10, player.image.getHeight(null)-10)){
                heartsToRemove.add(heart);
                player.recoverHp();
                System.out.println("You got hit by a heart <3!!!!! Player HP is "+player.getHp());
            }



        ///  COLISION BETWEEN SPACESHIPS AND BULLETS AND DELETION OF BULLETS
        for(EnemyTest enemy:enemies) {
            for (BulletTest bullet : bullets) {
                if (CheckCollision(bullet.x, (int) bullet.y, bullet.getWidth(), bullet.getHeight(), enemy.x, (int) enemy.y, enemy.image.getWidth(null), enemy.image.getHeight(null))) {
                    bulletsToRemove.add(bullet);
                    enemy.takeDamage();
                }
            }
        }

        ///  THE ENEMY REACHED PLAYERS LEVEL SO GAME OVER BUDDY
        for(EnemyTest enemy:enemies) {
            if (enemy != null && enemy.y + enemy.image.getHeight(null) >= 1000 - player.image.getHeight(null) + 40) {
                System.out.println("Enemy has reached the player's level.");
                player.setHp(0);
            }
        }

        ///  ENEMY DEAD SND SCORE UP
        for(EnemyTest enemy:enemies) {
            if(enemy.getHp()==0){
                player.score_Up(enemy.Value);
                System.out.println("Enemy defeated, updating lastEnemyDeathTime");
                lastEnemyDeathTime = currentTime;
                enemiesToRemove.add(enemy);
            }
        }

        /// DEMON DEAD AND SCORE UP
        for(EnemyType2 demon:demons) {
            if(demon.getHp()==0){
                player.score_Up(demon.Value);
                 xdemon=demon.x;
                 ydemon=demon.y;
                demonsToRemove.add(demon);
                chance=random.nextInt(100);
                if(player.getHp()<3){
                    if(chance<9){
                        hearts.add(new newHeart(xdemon,ydemon,"heart.png"));
                    }
                    System.out.println(chance);
                }
            }

        }



        for(AsteroidTest asteroid : asteroids)
            if(asteroid.y>=PANEL_HEIGHT)
            {
                asteroidsToRemove.add(asteroid);
                if(player.getScore()>=100*(int)asteroid.speed)
                    player.score_Down(100*(int)asteroid.speed);
                else
                    player.score_Down((int)player.getScore());
            }


        ///  DELETION OF THE DESTROYED OBJECTS

        bullets.removeAll(bulletsToRemove);
        asteroids.removeAll(asteroidsToRemove);
        enemies.removeAll(enemiesToRemove);
        enemyammo.removeAll(lasersToRemove);
        demons.removeAll(demonsToRemove);
        hearts.removeAll(heartsToRemove);



        if(currentTime - lastmodifyTime >= AsteroidSpeedRate){
            if(minSpeed<5.f){
                minSpeed+=incrementValue;
            }
            if(maxSpeed<10.f){
                maxSpeed+=incrementValue;
            }
            if(asteroidspawnrate>50) {
                asteroidspawnrate -= 5;
            }
            lastmodifyTime = currentTime;
        }



        if (random.nextInt(demonsspawnrate) == 0) {  // Adjust spawn rate

             randomX = MinXforAsteroids+random.nextInt(MaxXforAsteroids-MinXforAsteroids);

            while(lastRandom==randomX || (randomX+50>lastRandom && randomX<lastRandom) || (randomX<lastRandom+50 && randomX>lastRandom+50)){
                 randomX = MinXforAsteroids+random.nextInt(MaxXforAsteroids-MinXforAsteroids);
            }
            demons.add(new EnemyType2(randomX, 0,"enemy2.png"));
            lastRandom=randomX;
        }



        if (random.nextInt(asteroidspawnrate) == 0) {

            int randomX = MinXforAsteroids+random.nextInt(MaxXforAsteroids-MinXforAsteroids);
            float speedRand=minSpeed+random.nextFloat(maxSpeed-minSpeed);

            while(lastRandom==randomX || (randomX+50>lastRandom && randomX<lastRandom) || (randomX<lastRandom+50 && randomX>lastRandom+50)){
                randomX = MinXforAsteroids+random.nextInt(MaxXforAsteroids-MinXforAsteroids);
            }
            asteroids.add(new AsteroidTest(randomX, 0,speedRand, "ast2.png"));
            lastRandom=randomX;
        }





        if (!enemySpawned && ((initialSpawn && currentTime - startTime >= INITIAL_SPAWN_DELAY) ||
                (!initialSpawn && currentTime - lastEnemyDeathTime >= ENEMY_SPAWN_COOLDOWN))) {

            enemies.add(new EnemyTest("enemy starship.png"));
            enemySpawned = true;
            lastEnemyDeathTime = currentTime;
            initialSpawn = false;
        }



        for(EnemyTest enemy:enemies) {
            if(enemy.x+20<=player.x && enemy.x+enemy.image.getWidth(null)-20>=player.x && currentTime - lastLaserTime >= Laser_SPAWN_DELAY){
                enemyammo.add(new EnemyBulletTest(player.x+player.image.getWidth(null)/2,enemy.y+enemy.image.getHeight(null),"laser.png"));

                lastLaserTime=currentTime;
            }
        }




        if(player.getHp()==0){
            mort=true;
            System.out.println("Player HP is 0. Game over.");
            timer.stop();
        }



        if(enemies.isEmpty()){
            enemySpawned=false;

        }

        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        activeKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        activeKeys.remove(e.getKeyCode());
    }
}



