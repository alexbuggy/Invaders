import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class MainFrame extends JFrame implements ActionListener {
    GamePanelTest panel;
    Timer timer;
    String NumePlayer;
    Clip clip;
    public MainFrame(String Name) {
        System.out.println("MainFrame created with player name: " + Name);
        ImageIcon icon = new ImageIcon("src/images/asteroid.png");
        this.setIconImage(icon.getImage());
        NumePlayer = Name;
        panel = new GamePanelTest();
        playSound("src/sounds/invadersmusic.wav");
        panel.mort=false;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        timer=new Timer(10,this);
        timer.start();




    }
    public void playSound(String soundFilePath) {
        try {
            File audioFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(panel.mort==true){
            timer.stop();

            //Audio stops playing once you die
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            System.out.println("AI MURIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            new EndGameFrame(NumePlayer,panel.getScore());
            System.out.println("EndGameFrame created.");
            dispose();
            System.out.println("MainFrame deleted.");

        }
    }
}


