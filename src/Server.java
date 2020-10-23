import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    public static void main(String[] args) {
        // Variable to store port
        int port = 5555;

        try {
            if (args.length > 0)
                port = Integer.parseInt(args[0]);

            //Create server socket
            System.out.println("Binding socket to port " + Integer.toString(port));
            ServerSocket serverSocket = new ServerSocket(port);

            //Accept connection from client
            System.out.println("Awaiting connection...");
            Socket socket = serverSocket.accept();

            //Notify about established connection
            System.out.println("Connection established with " + socket.getInetAddress().toString());

            //Create reader to read contents of socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Create print writer to send messages back through socket
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //Run until the socket is closed
            while (true) {
                String line = in.readLine();
                if (line.length() == 0)
                    break;
                System.out.println("Received '" + line + "'. Echoing.");
                out.println(line);
            }

            //Close our socket objects
            socket.close();
            serverSocket.close();

        } catch (IOException | NullPointerException e) {
            System.err.println("Connection terminated by remote host");
            System.exit(-1);
        } catch (Exception e) {
            System.err.println("Usage: Server <port>");
            System.exit(-1);
        }
    }
}
