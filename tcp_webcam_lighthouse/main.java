import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class main {
    public static int port_receiver = 42067;
    public static int port_sender = 42060;
    private static ServerSocket socket_receiver;
    private static ServerSocket socket_sender;
    public static String inputLine;

    public static void main(String[] args) throws IOException {
        System.out.println("Running");
        socket_receiver = new ServerSocket(port_receiver);
        socket_sender = new ServerSocket(port_sender);
        while(true){
            new receiver(socket_receiver.accept()).start();
            new sender(socket_sender.accept()).start();
        }
    }

    private static class receiver extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public receiver(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {

            }

            try {
                inputLine = in.readLine();
             //   System.out.println(inputLine);

                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException r) {}

        }

    }

    private static class sender extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public sender(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {

            }

            try {
                out.println(inputLine);

                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException r) {

            }
        }
    }
}
