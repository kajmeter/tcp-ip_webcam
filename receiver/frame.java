import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class frame extends JFrame {
    Timer timer;
    JPanel panel = new JPanel();

    public frame() throws IOException {
        super("Webcam viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,400);
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        setMinimumSize(new Dimension(335,235));

        BufferedImage myPicture = main.out_buff;
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        
        int INTERVAL = 200;

        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BufferedImage myPicture = main.out_buff;
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                remove(panel);
                panel = new JPanel();
                panel.add(picLabel);
                add(panel);
                revalidate();repaint();
            }
        });
        
        timer.start();
    }
    
    public void refreshScreen() {
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.setRepeats(true);
        // Aprox. 60 FPS
        timer.setDelay(17);
        timer.start();
    }
}
