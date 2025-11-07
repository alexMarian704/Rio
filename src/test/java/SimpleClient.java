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
             Scanner scanner = new Scanner(System.in)){

            System.out.println("Connected to " + host + ":" + port);

            while (true) {
                System.out.print("> ");
                String line = scanner.nextLine();
                if (line == null) {
                    break;
                }

                out.write(line);
                out.write("\r\n");
                out.flush();

                String res = in.readLine();
                if (res == null) {
                    break;
                }
                System.out.println(res);
                if ("__QUIT__".equals(res)) {
                    break;
                }
            }
        }
    }
}
