import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    public static void main(String[] args) {
        // Variables to store host and port
        String host = "127.0.0.1";
        int port = 5555;

        try {
            if (args.length > 0)
                host = args[0];
            if (args.length > 1)
                port = Integer.parseInt(args[1]);

            //Create our client socket
            System.out.println("Connecting to " + host + ":" + Integer.toString(port));
            Socket socket = new Socket(host, port);

            //Notify of successful connection
            System.out.println("Connection established.");
            System.out.println("Enter an empty line to close the connection.");

            //Create reader to read contents of socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Create print writer to send messages back through socket
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //Create a buffered reader to read the contents of the terminal
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //Allow client to send messages until an empty message is sent
            while (true) {
                //Get the line entered, if it is empty end the program
                System.out.print("> ");
                String toSend = reader.readLine();
                if (toSend.length() == 0)
                    break;

                //Send the string through the socket
                out.println(toSend);
                String response = in.readLine();
                System.out.println("Response: " + response);
            }

            //Close socket
            socket.close();
            System.out.println("Connection terminated, goodbye.");

        } catch (SocketException e) {
            System.err.println("Connection failed");
            System.exit(-1);
        } catch (Exception e) {
            System.err.println("Usage: Client <host> <port>");
            System.exit(-1);
        }
    }
}
