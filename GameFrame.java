
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameFrame extends JFrame implements KeyListener {
    GamePanel g = new GamePanel(this);
    public GameFrame(){
        this.setBounds(300,200,g.getWidth() + 13,g.getHeight() + 13);
        this.add(g);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        System.out.println("itt");
        addKeyListener(this);

    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:                   //1=jobb, 2=bal, 3= fel, 4=le
                if (g.getIrany() != 1) {
                    g.setIrany(2);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (g.getIrany() != 2) {
                    g.setIrany(1);
                }
                break;
            case KeyEvent.VK_UP:
                if (g.getIrany() != 4) {
                    g.setIrany(3);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (g.getIrany() != 3) {
                    g.setIrany(4);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
