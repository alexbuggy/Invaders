import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroFrame extends JFrame implements ActionListener {

    JTextArea textTitle,textarea1;
    JTextField text1;
    JButton button2,button3;
    String StringuNume;

    IntroFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("ast2.png");
        this.setIconImage(icon.getImage());
                    

        JPanel panel1 = new JPanel();
        this.add(panel1);
        panel1.setBackground(new Color(80,28,12));
        panel1.setLayout(null);
        panel1.setBounds(0,0,1000,1000);


        textTitle = new JTextArea("Invaders Madness");
        textTitle.setPreferredSize(new Dimension(100, 50));
        textTitle.setFont(new Font("Consolas", Font.BOLD, 50));
        textTitle.setForeground(Color.orange);
        textTitle.setEditable(false);
        textTitle.setOpaque(false);
        textTitle.setCaretColor(new Color(80,28,12));


        textarea1 = new JTextArea("Enter your name:");
        textarea1.setPreferredSize(new Dimension(100, 40));
        textarea1.setFont(new Font("Consolas", Font.BOLD, 35));
        textarea1.setForeground(Color.orange);
        textarea1.setEditable(false);
        textarea1.setOpaque(false);
        textarea1.setCaretColor(new Color(80,28,12));




        text1 = new JTextField();
        text1.setPreferredSize(new Dimension(250,40));
        text1.setFont(new Font("Consolas",Font.PLAIN,40));
        text1.setForeground(Color.orange);
        text1.setBackground(Color.black);
        text1.setCaretColor(Color.white);
        text1.setText("username");
        text1.setEditable(true);


        button2 = new JButton("Submit");
        button2.setFont(new Font("Consolas",Font.PLAIN,20));
        button2.setFocusable(false);
        button2.addActionListener(this);
        button2.setEnabled(true);

        button3 = new JButton("PLAY");
        button3.setFont(new Font("Consolas",Font.PLAIN,40));
        button3.setFocusable(false);
        button3.addActionListener(this);
        button3.setVisible(true);
        button3.setEnabled(false);





        panel1.add(textTitle);
        textTitle.setBounds(275,20,700,180);
        panel1.add(textarea1);
        textarea1.setBounds(40,300,700,50);
        panel1.add(text1);
        text1.setBounds(40,370,500,40);
        panel1.add(button2);
        button2.setBounds(550,300,110,110);
        panel1.add(button3);
        button3.setBounds(400,700,150,150);



        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button2){
            System.out.println( text1.getText());
            text1.setEditable(false);
            button3.setVisible(true);
            text1.getCaret().setVisible(false);
            StringuNume=text1.getText();
            button3.setEnabled(true);
        }
        if(e.getSource()==button3){

                new MainFrame(StringuNume);
                dispose();
        }
    }
    }

