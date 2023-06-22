import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class main {
    public static String seed = "80t43er[g;l'gp[45wies-0--sdfg";
    public static String ip = "46.41.141.14";
    public static int port = 42060;
    static private Socket socket;
    static private BufferedReader in;
    public static String packet="";

    public static BufferedImage out_buff;
    public static InputStream out_stream;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            PEngine engine = new PEngine();
            new Thread(engine).start();
        });

        while (connect(ip,port)){
            receivePacket();
        }

    }



    public static boolean connect(String ip, int port){
        try{
            socket = new Socket(ip,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //System.out.println("[Receiver] Connection estabilished");
            return true;
        }catch (IOException e){
            System.out.print("[Receiver] Connection failed");
            return false;
        }
    }

    public static String decrypt(String packet){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(seed);
        return encryptor.decrypt(packet);
    }

    public static void receivePacket()  {

try{
    packet=in.readLine(); ; packet = decrypt(packet);

    byte[] image_byte = Base64.getDecoder().decode(packet);

    out_stream = new ByteArrayInputStream(image_byte);
    out_buff = ImageIO.read(out_stream);
    
}catch (NullPointerException | IOException e){
    System.out.println(" & No input found");
}

    }

    }

