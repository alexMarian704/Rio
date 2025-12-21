import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 6122;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to " + host + ":" + port);

            out.write("3\r\n3\r\nSET\r\n4\r\nCeva\r\n3\r\nAlo\r\n\r\n");
            out.flush();
            System.out.println(in.readLine());

            out.write("2\r\n3\r\nGET\r\n4\r\nCeva\r\n\r\n");
            out.flush();

            out.write("1\r\n4\r\nQUIT\r\n\r\n");
            out.flush();
            System.out.println(in.readLine());
        }
    }
}
