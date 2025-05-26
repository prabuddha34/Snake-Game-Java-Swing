import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener {
   static final int SCREEN_HEIGHT=600;
   static final int SCREEN_WIDTH=600;
   static final int UNIT_SIZE=25;
   static final int GAME_UNIT=(SCREEN_HEIGHT*SCREEN_WIDTH)/(UNIT_SIZE*UNIT_SIZE);
   final int[]x=new int[GAME_UNIT];
   final int[]y=new int[GAME_UNIT];
   int bodyParts=6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
   Random random=new Random();

    SnakePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        random = new Random();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                        if (direction != 'D') direction = 'U';
                        break;
                    case 's':
                    case 'S':
                        if (direction != 'U') direction = 'D';
                        break;
                    case 'a':
                    case 'A':
                        if (direction != 'R') direction = 'L';
                        break;
                    case 'd':
                    case 'D':
                        if (direction != 'L') direction = 'R';
                        break;
                }
            }
        });

        startGame();
    }

    public void startGame(){
        spawnApple();
        running=true;
        timer = new Timer(Snake_Game_Panel.DELAY, this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten,
                (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        if(running) {
            for (int i = 0; i < bodyParts; i++) {
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
        else{
            Over(g);
        }
    }
    public void spawnApple(){
        appleX= random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY=  random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

    }
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }
    }

    public void checkApple(){
    if(x[0]==appleX && y[0]==appleY){
        bodyParts++;
        applesEaten++;
        spawnApple();
    }
    }
    public void Over(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics gameOverMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",
                (SCREEN_WIDTH - gameOverMetrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }
    public void checkCollision() {

        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
     if(running){
         move();
         checkApple();
         checkCollision();

     }
     repaint();
    }
}
