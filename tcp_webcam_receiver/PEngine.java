import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public class PEngine extends Canvas implements Runnable {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FPS = 60;

    private JFrame frame;
    private int[][] pixels;
    private int ticks;
    private int frames;

    public PEngine() {
        pixels = new int[WIDTH][HEIGHT];
        ticks = 0;
        frames = 0;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame = new JFrame("PE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public synchronized void setPixel(int x, int y, int rgbColor) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            pixels[x][y] = rgbColor;
        }
    }

    public void loadAndDisplayImage() {
        BufferedImage buf_load = main.out_buff;
        buf_load = resizeImage(WIDTH,HEIGHT,buf_load);

                for(int j=0;j<buf_load.getHeight();j++){
                for(int i=0;i<buf_load.getWidth();i++){
                    setPixel(i,j,buf_load.getRGB(i,j));
                }
            }

    }

    public BufferedImage resizeImage(int width, int height,BufferedImage buf) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = resizedImage.createGraphics();
        g.drawImage(buf, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double nsPerTick = 1000000000.0 / FPS;
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                delta--;
                ticks++;
            }

            // Clear pixels array
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    pixels[x][y] = 0;
                }
            }

            ////todo Set pixel colors for the box
            loadAndDisplayImage();

            // rendereing w bufferstrategy
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                continue;
            }

            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    g.setColor(new Color(pixels[x][y]));
                    g.fillRect(x, y, 1, 1);
                }
            }

            g.dispose();
            bs.show();

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle("PE | Ticks: " + ticks + ", FPS: " + frames);
                frames = 0;
                ticks = 0;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
