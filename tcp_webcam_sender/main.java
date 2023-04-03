import com.github.sarxos.webcam.Webcam;
import org.apache.commons.io.FileUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class main {
    public static String seed = "80t43er[g;l'gp[45wies-0--sdfg";
    public static String ip = "127.0.0.1";
    public static int port = 42067;
    static private Socket socket;
    static private PrintWriter out;
    static private BufferedReader in;
    public static String packet="";

    public static Webcam webcam = Webcam.getDefault();
    public static BufferedImage buffer_image;
    public static String string_image;
    public static void main(String[] args) throws IOException, InterruptedException {
        webcam.open();
        System.out.println("Webcam host running");
        while (true){
            buffer_image = webcam.getImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(buffer_image,"jpg",baos);

            byte[] byte_image = baos.toByteArray();

            string_image = Base64.getEncoder().encodeToString(byte_image);
            connect(ip,port);
            sendPacket();
        }
    }

    public static void connect(String ip,int port){
        try{
            socket = new Socket(ip,port);
            out = new PrintWriter(socket.getOutputStream(), false);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.out.print("not sent");
        }
    }

    public static void sendPacket() throws IOException, InterruptedException {
Thread.sleep(17);
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(seed);
        string_image = encryptor.encrypt(string_image);
        out.println(string_image);

        out.close();
        in.close();

    }

}
