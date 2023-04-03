import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class main {
    public static String seed = "80t43er[g;l'gp[45wies-0--sdfg";
    public static String ip = "127.0.0.1";
    public static int port = 42060;
    static private Socket socket;
    static private PrintWriter out;
    static private BufferedReader in;
    public static String packet="";


    public static BufferedImage buffer_image;
    public static String string_image;
    public static BufferedImage out_buff;
    public static InputStream out_stream;
    public static void main(String[] args) throws IOException, InterruptedException {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new frame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        while (true){
            connect(ip,port);
            receivePacket();
        }
    }

    public static void connect(String ip,int port){
        try{
            socket = new Socket(ip,port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.out.print("not sent");
        }
    }

    public static void receivePacket()  {

try{
    packet=in.readLine();
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(seed);
    packet = encryptor.decrypt(packet);

    byte[] image_byte = Base64.getDecoder().decode(packet);

    out_stream = new ByteArrayInputStream(image_byte);
    out_buff = ImageIO.read(out_stream);
    
    out.close();
    in.close();
}catch (IOException e){
    System.out.println("a");
}

    }

}
