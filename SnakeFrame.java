import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {
    SnakeFrame(){
        this.add(new SnakePanel());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        this.setTitle("Snake Game");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
