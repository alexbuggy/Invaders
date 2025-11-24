import javax.naming.Name;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EndGameFrame extends JFrame implements ActionListener {

    String name;
    JButton button3;
    JLabel label,locu1,locu2,locu3;

    boolean hs=false;
    Map<String, Long> scores= new HashMap<>();
    public EndGameFrame(String Nume,long Score) {

        name = Nume;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1000, 1000);
        panel.setBackground(Color.BLACK);

        this.add(panel);
        ImageIcon image=new ImageIcon("src/images/victory.png");
        label = new JLabel(image);

        panel.add(label);
        label.setBounds(245, 100, 500, 200);
        label.setVisible(false);
        JTextArea textArea = new JTextArea();


        ImageIcon image1=new ImageIcon("src/images/first_place.png");
        locu1 = new JLabel(image1);
        ImageIcon image2=new ImageIcon("src/images/second_place.png");
        locu2 = new JLabel(image2);
        ImageIcon image3=new ImageIcon("src/images/third_place.png");
        locu3 = new JLabel(image3);
        panel.add(locu1);
        panel.add(locu2);
        panel.add(locu3);

        JTextArea player1Area = new JTextArea();
        JTextArea player2Area = new JTextArea();
        JTextArea player3Area = new JTextArea();


        panel.add(player1Area);
        panel.add(player2Area);
        panel.add(player3Area);


        JTextArea[] playerAreas = {player1Area, player2Area, player3Area};
        for (JTextArea area : playerAreas) {
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setFont(new Font("Consolas", Font.BOLD, 20));
            area.setForeground(Color.WHITE);
            area.setBackground(Color.BLACK); // Matches the panel color
            area.setCaretColor(Color.BLACK);
        }

        try {
            BufferedReader reader=new BufferedReader(new FileReader("src/Info/users.txt"));
            String Line;

            while((Line=reader.readLine())!=null) {
                String[] parts = Line.split(",");
                if(parts.length==2){
                    try{
                        String name = parts[0];
                        long score = Long.parseLong(parts[1]);
                        scores.put(name, score);
                    }
                    catch(NumberFormatException e){
                        System.out.println("Number format exception");
                    }
                }
                else
                {
                    System.out.println("Number format exception");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        scores.put(Nume,Score);

        ArrayList<Map.Entry<String, Long>> entryList = new ArrayList<>(scores.entrySet());
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        if (!entryList.isEmpty()) {
            Map.Entry<String, Long> highestScoreEntry = entryList.get(0);
            if (highestScoreEntry.getKey().equals(Nume) && highestScoreEntry.getValue().equals(Score)) {
                textArea.setText("New highscore of "+Score+" points! \n");
                textArea.setForeground(Color.ORANGE);
                label.setVisible(true);
                hs=true;
            }
            if (entryList.size() > 0) {
                Map.Entry<String, Long> first = entryList.get(0);
                player1Area.setText(first.getKey() + ": " + first.getValue());
            }
            if (entryList.size() > 1) {
                Map.Entry<String, Long> second = entryList.get(1);
                player2Area.setText(second.getKey() + ": " + second.getValue());
            }
            if (entryList.size() > 2) {
                Map.Entry<String, Long> third = entryList.get(2);
                player3Area.setText(third.getKey() + ": " + third.getValue());
            }
        }



        try {
            BufferedWriter writer= new BufferedWriter(new FileWriter("src/Info/users.txt"));


            for (int i = 0; i < Math.min(3, entryList.size()); i++) {
                Map.Entry<String, Long> entry = entryList.get(i);
                writer.write(entry.getKey() +"," + entry.getValue()+"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




        if(hs==false){
            textArea.setText("Your score is "+Score+" points! \n");
            textArea.setForeground(Color.PINK);
        }
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Consolas", Font.BOLD, 50));
        textArea.setBackground(Color.BLACK);
        textArea.setCaretColor(Color.BLACK);
        textArea.getCaret().setVisible(false);

        JTextArea textArea1 = new JTextArea();
        textArea1.setText("Top 3 players:");
        textArea1.setForeground(Color.WHITE);
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setFont(new Font("Consolas", Font.BOLD, 50));
        textArea1.setBackground(Color.BLACK);
        textArea1.setCaretColor(Color.BLACK);

        panel.add(textArea1);

        button3 = new JButton("PLAY AGAIN");
        button3.setFont(new Font("Consolas",Font.BOLD,40));
        button3.setFocusable(false);
        button3.addActionListener(this);
        button3.setVisible(true);
        button3.setEnabled(true);






        ImageIcon icon = new ImageIcon("src/images/asteroid.png");
        this.setIconImage(icon.getImage());





        panel.add(textArea);
        textArea.setCaretPosition(0);
        textArea.setBounds(60,20,900,100);
        textArea1.setBounds(60,500,900,100);
        locu1.setBounds(50,600,30,30);
        locu2.setBounds(50,635,30,30);
        locu3.setBounds(50,665,30,30);
        player1Area.setBounds(100, 605, 700, 30);
        player2Area.setBounds(100, 640, 700, 30);
        player3Area.setBounds(100, 673, 700, 30);
        panel.add(button3);
        button3.setBounds(345,800,300,70);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button3){



            new IntroFrame();
            System.out.println("IntroFrame created.");
            this.dispose();
            System.out.println("EndFrame deleted.");
            
        }
    }
}
