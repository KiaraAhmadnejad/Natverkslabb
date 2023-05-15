import java.io.*;
import java.net.*;

public class NetworkingServer {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;

        // Deafult port number that we are going to use
        int portnumber = 1234;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        // create Server side socket

        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket" + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created" + server);
        while (true) {
            //wait for the data from the client and reply
            try {
                // listens for a connection to be made
                // this socket and accepts it.Method blocks util
                // a connection is made
                System.out.println("Waiting for connect request..");
                client = server.accept();

                System.out.println("Connect request is accepted..");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + "Client port = " + clientPort);

                // Read data from the client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("message recevied from client =" + msgFromClient);

                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("Bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello," + msgFromClient;
                    printWriter.println(ansMsg);
                }
                // close sockets
                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("Bye")) {
                    server.close();
                    client.close();
                    break;
                }

            } catch (IOException ie) {

            }
        }
    }
}
