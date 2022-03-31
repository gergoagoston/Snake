import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.util.*;
import java.util.Timer;



public class GamePanel extends JPanel  implements ActionListener {
    private int appleX;
    private int appleY;
    private final int size = 25;
    private final int height = 600;
    private final int width = 600;
    private final Snake k;
    private int irany = 4;      //1=jobb, 2=bal, 3=fel, 4=le
    private final Timer t;
    private boolean fut;
    private int score;
    private int period= 80;
    Music hatterZene = null;


    public void setIrany(int irany) {
        this.irany = irany;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getIrany() {
        return irany;
    }

    @Override
    public int getWidth() {
        return width;
    }

    private void move() {
        for (int i = k.getX().size() - 1; i > 0; --i) {
            k.getX().set(i, k.getX().get(i - 1));
            k.getY().set(i, k.getY().get(i - 1));
        }
        if (irany == 1) {
            k.getX().set(0, k.getX().get(1) + size);
            k.getY().set(0, k.getY().get(1));
        } else if (irany == 2) {
            k.getX().set(0, k.getX().get(1) - size);
            k.getY().set(0, k.getY().get(1));
        } else if (irany == 3) {
            k.getX().set(0, k.getX().get(1));
            k.getY().set(0, k.getY().get(1) - size);
        } else {
            k.getX().set(0, k.getX().get(1));
            k.getY().set(0, k.getY().get(1) + size);
        }
    }

    public GamePanel(GameFrame frame) {

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(new Color(116, 176, 19));
        apple();
        k = new Snake(size);
        t = new Timer();
        this.addKeyListener(frame);
        fut = true;
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (fut) {
                    if (checkApple()) {
                        k.grow();
                        score++;
                        period--;
                        try {
                            hatterZene = new Music("kaja.wav");
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                            e.printStackTrace();
                        }
                    }
                    onmaga();
                    move();
                    fal();
                    repaint();

                }
            }

        };
        t.schedule(tt, 100, period)  ;
    }

    private void apple() {
        Random r = new Random();
        appleX = (1+Math.abs(r.nextInt((width - 4 * size))/size))*size;
        appleY = (1+Math.abs(r.nextInt((height - 4 * size))/size))*size;

    }

    private boolean checkApple() {
        if (k.getX().get(0)==appleX && k.getY().get(0)==appleY) {
            apple();

            return true;
        }
        return false;
    }

    private void onmaga() {

        for (int i = k.getX().size()-1 ; i>0; --i) {

            if ((k.getX().get(0).equals(k.getX().get(i))) && (k.getY().get(0).equals(k.getY().get(i)))) {

                fut = false;

                t.cancel();
                return;
            }
        }
    }

    private void fal(){
        if (k.getX().get(0) + size==size || k.getX().get(0)-size ==(width-2*size) || k.getY().get(0)+size==size || k.getY().get(0)==(height-2*size)){
            fut = false;

        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void gameover(Graphics g) {

        try {
            hatterZene = new Music("lose.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> highscores = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("highscore.txt"));
            int i;
            while(scanner.hasNextInt())
            {
                i = scanner.nextInt();
                highscores.add(i);
            }
        } catch (IOException e) {
            System.out.println("hiba");
        }
        int max = highscores.stream().mapToInt(v -> v)
                .max().orElseThrow(NoSuchElementException::new);



        g.setColor(Color.BLACK);
        g.setFont(new Font ("Chiller",Font.BOLD,30));
        FontMetrics s = getFontMetrics(g.getFont());
        g.drawString("Score: " + score,(width - s.stringWidth("Game Over"))/2,height/2+150);
        g.drawString("Length: "+ (score+3),(width - s.stringWidth("Game Over"))/2,height/2+100);
        g.drawString("Highscore: "+ max,(width - s.stringWidth("Game Over"))/2,height/2+200);
        g.setColor(Color.BLACK);
        g.setFont(new Font ("Chiller",Font.BOLD,75));
        FontMetrics k = getFontMetrics(g.getFont());
        g.drawString("Game Over",(width - k.stringWidth("Game Over"))/2,height/2);
        g.drawString("Game Over",(width - k.stringWidth("Game Over"))/2,height/2);
    }

    public void draw(Graphics g) {
        if (fut ) {
            g.setColor(Color.BLACK);
            g.drawLine(size, size, size, height - 2 * size);
            g.drawLine(width - size, size, width - size, height - 2 * size);
            g.drawLine(size, size, width - size, size);
            g.drawLine(size, height - 2 * size, width - size, height - 2 * size);
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, size, size);
            g.setColor(Color.BLACK);
            g.setFont(new Font ("Chiller",Font.BOLD,25));

            g.drawString("Length: "+ (score+3),(width - 150),size-5);

            g.setColor(Color.BLACK);
            for (int i = k.getX().size() - 1; i > 0; --i) {
                g.fillRect(k.getX().get(i), k.getY().get(i), size, size);
            }
            g.setColor(Color.blue);
            g.fillRect(k.getX().get(0), k.getY().get(0), size, size);
        } else {
            try{
            FileWriter fw = new FileWriter("highscore.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(score);
            pw.flush();
            pw.close();
            bw.close();
            fw.close();

            } catch (IOException e) {
                System.out.println("hiba kiiras");
            }
            gameover(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}